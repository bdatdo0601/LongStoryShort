package app.bit.gameplay.Record;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.view.View;

import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.bit.baseclass.Multimedia.Audio;
import app.bit.baseclass.currentStory;
import app.bit.gameplay.OptionActivity;
import app.bit.gameplay.StoryActivity;
import app.bit.longstoryshort.R;

public class AudioRecord extends DialogFragment {
    private static final String LOG_TAG = "AudioRecord";
    private static String mFileName = null;
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private Boolean prepareCheck = true;
    private Button playbackButton;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setTitle("Hold to record your voice");

        View dialogView = inflater.inflate(R.layout.activity_audio_record,null);

        ImageButton recordButton = (ImageButton) dialogView.findViewById(R.id.recordButton);
        playbackButton = (Button) dialogView.findViewById(R.id.playButton);

        recordButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startRecording();
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    stopRecording();
                    return true;
                }
                return false;
            }
        });

        playbackButton.setOnClickListener(new View.OnClickListener() {
            boolean mStartPlaying = true;

            @Override
            public void onClick(View v) {
                onPlay(mStartPlaying);
                if (prepareCheck) {
                    if (mStartPlaying) {
                        playbackButton.setText("Pause");
                    } else {
                        playbackButton.setText("Play");
                    }
                    mStartPlaying = !mStartPlaying;
                }
            }
        });

        builder.setView(dialogView);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println(mFileName);
                //TODO: do something with mFileName
                currentStory.getInstance().addStoryPart(new Audio(mFileName));
                Intent intent = new Intent(getActivity(),StoryActivity.class);
                startActivity(intent);
            }
        });

        return builder.create();
    }



    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            prepareCheck = false;
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        setmFileName();
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        try {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        } catch (RuntimeException e){

        }
    }

    private void setmFileName() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String audioFileName = "3gp_" + timeStamp + "_";

        // Save a file: path for use with ACTION_VIEW intents
        mFileName = getActivity().getExternalFilesDir("DIRECTORY_AUDIO").getAbsolutePath();
        mFileName += "/" +audioFileName + ".3gp";
    }

}
