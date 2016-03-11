package app.bit.longstoryshort;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.bit.baseclass.Player;
import app.bit.baseclass.PlayerList;


public class PlayerActivity extends AppCompatActivity {
    private int NUMBER_OF_PLAYER ;
    private PlayerList playerAdapter;
    private String mCurrentPhotoPath;
    private String[] playername;
    private int[] num;
    private Bitmap[] profile ;
    private ArrayList<Player> players;
    private EditText editText;
    private ListView playerList;
    private View childView;
    private ImageButton imageButton;
    static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Intent plyamt = getIntent();
        NUMBER_OF_PLAYER = plyamt.getIntExtra("number",2);
        playername = new String[NUMBER_OF_PLAYER];
        num = new int[NUMBER_OF_PLAYER];
        profile = new Bitmap[NUMBER_OF_PLAYER];
        for (int i = 0; i<playername.length; i++){
            num[i] = i+1;
            playername[i]= "Player "+ Integer.toString(i+1);
            profile[i] = BitmapFactory.decodeResource(PlayerActivity.this.getResources(),R.drawable.ic_profile);
        }
        playerAdapter = new PlayerList(PlayerActivity.this,playername);
        playerList = (ListView) findViewById(R.id.listView2);
        playerList.setAdapter(playerAdapter);
        playerList.setItemsCanFocus(true);


        FloatingActionButton completedButton = (FloatingActionButton) findViewById(R.id.fab);
        completedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playername = getPlayername();
                profile = playerAdapter.getIcon();
                players = new ArrayList<Player>();
                for (int i=0;i<NUMBER_OF_PLAYER;i++){
                    players.add(new Player(playername[i],profile[i]));
                }
                System.out.println(players);
            }
        });
    }
    private String[] getPlayername(){


        int listLength = playerList.getChildCount();
        for (int i = 0; i < listLength; i++)
        {
            childView = playerList.getChildAt(i);
            editText = (EditText) childView.findViewById(R.id.editText4);
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

    public void dispatchTakePictureIntent() {
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
        int pos = playerAdapter.getPosition();
        childView = playerList.getChildAt(pos);
        imageButton = (ImageButton) childView.findViewById(R.id.imageButton2);
        profile[pos] = playerAdapter.getResizedBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath),250,250);
        imageButton.setImageBitmap(profile[pos]);
    }


}
