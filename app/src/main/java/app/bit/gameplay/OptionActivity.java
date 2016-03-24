package app.bit.gameplay;

import android.app.Dialog;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.bit.baseclass.Multimedia.Clips;
import app.bit.baseclass.Multimedia.Text;
import app.bit.baseclass.currentStory;
import app.bit.gameplay.Record.AudioRecord;
import app.bit.longstoryshort.R;
import app.bit.longstoryshort.amountscreen;

public class OptionActivity extends AppCompatActivity {

    static final int REQUEST_VIDEO_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        final Bundle args = savedInstanceState;

        ImageButton cameraButton = (ImageButton) findViewById(R.id.cameraButton);
        ImageButton videoButton = (ImageButton) findViewById(R.id.videoButton);
        ImageButton voiceButton = (ImageButton) findViewById(R.id.voiceButton);
        ImageButton writeButton = (ImageButton) findViewById(R.id.writeButton);

        cameraButton.requestFocus();

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Make a simple drawing applet
            }
        });

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakeVideoIntent();

            }
        });

        voiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new AudioRecord();
                dialog.show(getSupportFragmentManager(),"AudioRecord");
            }
        });

        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = CreateTextDialog();
                dialog.show();
            }
        });
    }

    @Override
    public void onBackPressed(){
        if (currentStory.getInstance().getSize() == 0){
            Dialog dialog = recreateDialog();
            dialog.show();
        } else {
            Intent intent = new Intent(OptionActivity.this, StoryActivity.class);
            finish();
        }
    }

    private void dispatchTakeVideoIntent() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
        }
    }

    private String getVideofilename() {
        // Create an image file name
        String mFileName;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String audioFileName = "mp4_" + timeStamp + "_";

        // Save a file: path for use with ACTION_VIEW intents
        mFileName = getExternalFilesDir("DIRECTORY_VIDEO").getAbsolutePath();
        mFileName += "/" +audioFileName + ".mp4";
        return mFileName;
    }

    private void deleteFileFromMediaStore(final ContentResolver contentResolver, final File file) {
        String canonicalPath;
        try {
            canonicalPath = file.getCanonicalPath();
        } catch (IOException e) {
            canonicalPath = file.getAbsolutePath();
        }
        final Uri uri = MediaStore.Files.getContentUri("external");
        final int result = contentResolver.delete(uri,
                MediaStore.Files.FileColumns.DATA + "=?", new String[]{canonicalPath});
        if (result == 0) {
            final String absolutePath = file.getAbsolutePath();
            if (!absolutePath.equals(canonicalPath)) {
                contentResolver.delete(uri,
                        MediaStore.Files.FileColumns.DATA + "=?", new String[]{absolutePath});
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            try {
                File originFile = new File(getRealPathFromURI(data.getData()));
                FileInputStream fis = new FileInputStream(originFile);
                //this is where you set whatever path you want to save it as:

                File tmpFile = new File(getVideofilename());

                //save the video to the File path
                FileOutputStream fos = new FileOutputStream(tmpFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = fis.read(buf)) > 0) {
                    fos.write(buf, 0, len);
                }
                fis.close();
                fos.close();

                System.out.println(tmpFile.getAbsolutePath());

                currentStory.getInstance().addStoryPart(new Clips(Uri.fromFile(tmpFile)));
                Intent intent = new Intent(OptionActivity.this,StoryActivity.class);
                startActivity(intent);
                if (originFile.exists()){
                    deleteFileFromMediaStore(this.getContentResolver(),originFile);
                }
            } catch (IOException io_e) {
                // TODO: handle error
            }

            finish();
        }

    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    private Dialog recreateDialog() {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(OptionActivity.this);
        builder.setMessage("Do you want to change your profile?")
                .setPositiveButton("Yah!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(OptionActivity.this, amountscreen.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private Dialog CreateTextDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OptionActivity.this);
        LayoutInflater inflater = OptionActivity.this.getLayoutInflater();

        builder.setTitle("Type in your part here!")
                .setView(inflater.inflate(R.layout.activity_write_record, null))
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog f = (Dialog) dialog;
                        EditText contentText = (EditText) f.findViewById(R.id.writeEditText);
                        if (!contentText.equals(R.string.hint)) {
                            currentStory.getInstance().addStoryPart(new Text(contentText.getText().toString()));
                            Intent intent = new Intent(OptionActivity.this, StoryActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
