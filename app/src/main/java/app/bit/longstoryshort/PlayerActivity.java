package app.bit.longstoryshort;


import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ListView;
import android.content.Intent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.bit.baseclass.PlayerList;


public class PlayerActivity extends AppCompatActivity {
    private int NUMBER_OF_PLAYER ;
    private PlayerList playerAdapter;
    private String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Intent plyamt = getIntent();
        NUMBER_OF_PLAYER = plyamt.getIntExtra("number",2);
        String[] defaultplayername = new String[NUMBER_OF_PLAYER];
        int[] num = new int[NUMBER_OF_PLAYER];
        for (int i = 0; i<defaultplayername.length; i++){
            num[i] = i+1;
            defaultplayername[i]= "player "+ Integer.toString(i+1);
        }
        playerAdapter = new PlayerList(PlayerActivity.this,defaultplayername,num);
        ListView playerList = (ListView) findViewById(R.id.listView2);
        playerList.setAdapter(playerAdapter);
    }

    public String getPhotoPath(){
        return mCurrentPhotoPath;
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

        playerAdapter.setCurrentIcon(BitmapFactory.decodeFile(mCurrentPhotoPath));
    }


}
