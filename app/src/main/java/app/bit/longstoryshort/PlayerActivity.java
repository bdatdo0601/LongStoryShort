package app.bit.longstoryshort;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.content.Intent;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.bit.baseclass.Singleton.ListofPlayer;
import app.bit.baseclass.Player;
import app.bit.baseclass.Listadapter.PlayerList;
import app.bit.gameplay.WaitScreen;


public class PlayerActivity extends AppCompatActivity {
    private int NUMBER_OF_PLAYER;
    private PlayerList playerAdapter;
    private String mCurrentPhotoPath;
    private String[] playername;
    private Bitmap[] profile;
    private ArrayList<Player> players;
    private ListView playerList;
    private int position;
    static final int REQUEST_TAKE_PHOTO = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Intent plyamt = getIntent();
        NUMBER_OF_PLAYER = plyamt.getIntExtra("number", 2);
        playername = new String[NUMBER_OF_PLAYER];
        profile = new Bitmap[NUMBER_OF_PLAYER];
        for (int i = 0; i < playername.length; i++) {

            playername[i] = "Player " + Integer.toString(i + 1);
            profile[i] = BitmapFactory.decodeResource(PlayerActivity.this.getResources(), R.drawable.ic_profile);
        }
        playerAdapter = new PlayerList(PlayerActivity.this, playername);
        playerList = (ListView) findViewById(R.id.listView2);
        assert playerList != null;
        playerList.setAdapter(playerAdapter);
        playerList.setItemsCanFocus(true);


        FloatingActionButton completedButton = (FloatingActionButton) findViewById(R.id.fab);
        assert completedButton != null;
        completedButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                playername = getPlayername();
                players = new ArrayList<>();
                for (int i = 0; i < NUMBER_OF_PLAYER; i++) {
                    players.add(new Player(playername[i], profile[i]));
                }
                ListofPlayer.getInstance().setList(players);
                Intent toWaitScreen = new Intent(PlayerActivity.this, WaitScreen.class);
                startActivity(toWaitScreen);

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private String[] getPlayername() {

        View childView;
        int listLength = playerList.getChildCount();
        for (int i = 0; i < listLength; i++) {
            childView = playerList.getChildAt(i);
            EditText editText = (EditText) childView.findViewById(R.id.editText4);
            playername[i] = editText.getText().toString();
        }
        return playername;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void dispatchTakePictureIntent(int pos) {
        position = pos;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        View childView = playerList.getChildAt(position);
        if (childView != null ) {
            Bitmap pic = BitmapFactory.decodeFile(mCurrentPhotoPath);
            if (pic != null) {
                ImageButton imageButton = (ImageButton) childView.findViewById(R.id.imageButton2);
                profile[position] = BitmapFactory.decodeFile(mCurrentPhotoPath);
                imageButton.setImageBitmap(profile[position]);
                imageButton.setScaleType(ImageButton.ScaleType.CENTER_INSIDE);
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Player Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://app.bit.longstoryshort/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Player Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://app.bit.longstoryshort/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
