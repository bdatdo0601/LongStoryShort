package app.bit.gameplay;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.bit.baseclass.Multimedia.Clips;
import app.bit.baseclass.Multimedia.Picture;
import app.bit.baseclass.Multimedia.StoryPart;
import app.bit.baseclass.currentStory;
import app.bit.gameplay.Record.AudioRecord;
import app.bit.gameplay.Record.WriteRecord;
import app.bit.longstoryshort.R;

public class OptionActivity extends AppCompatActivity {
    private String mCurrentPhotoPath;
    private Uri videoUri;
    private Intent intent;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_VIDEO_CAPTURE = 1;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        ImageButton cameraButton = (ImageButton) findViewById(R.id.cameraButton);
        ImageButton videoButton = (ImageButton) findViewById(R.id.videoButton);
        ImageButton voiceButton = (ImageButton) findViewById(R.id.voiceButton);
        ImageButton writeButton = (ImageButton) findViewById(R.id.writeButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                currentStory.getInstance().addStoryPart(new Picture(mCurrentPhotoPath));
                intent = new Intent(OptionActivity.this,StoryActivity.class);
                startActivity(intent);
            }
        });
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakeVideoIntent();
                currentStory.getInstance().addStoryPart(new Clips(videoUri));
                intent = new Intent(OptionActivity.this,StoryActivity.class);
                startActivity(intent);

            }
        });
        voiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(OptionActivity.this, AudioRecord.class);
                startActivity(intent);
            }
        });
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(OptionActivity.this, WriteRecord.class);
                startActivity(intent);
            }
        });
    }
    private void dispatchTakeVideoIntent() {
        intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
        }
    }
    private void dispatchTakePictureIntent() {
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            videoUri = intent.getData();
        }
        else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

        }
    }
}
