package app.bit.baseclass.Multimedia;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageButton;


import java.io.File;
import java.io.IOException;

import app.bit.longstoryshort.R;


/**
 * Audio is a subclass from abstract class StoryPart used to handle Voice record by the user into the Story
 *
 * Created by bdatd on 3/13/2016.
 */
public class Audio extends StoryPart {

    private String fileName;
    private Boolean isPlaying = false;
    private Boolean failedPrepare = false;

    /**
     * The constructor of Audio will need the file Location of the audio file created.
     * @param location
     */

    public Audio(String location) {
        fileName = location;
    }

    /**
     * play method is called to play the audio file. it will automatically stop when done.
     *
     * @param mPlayer MediaPlayer used to handle and play the audio file.
     *
     */

    private void play(MediaPlayer mPlayer){
        try {
            final Resources res = getContext().getResources();
            final ImageButton playButton = new ImageButton(this.getContext());
            mPlayer.setDataSource(fileName);
            mPlayer.prepare();
            mPlayer.start();
            //this is to handle when the file is done
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Bitmap playIcon = BitmapFactory.decodeResource(res, R.drawable.ic_play);
                    playButton.setImageBitmap(playIcon);
                }
            });
        } catch (IOException e) {
            failedPrepare = true;
        }
    }

    /**
     * pause method is called to pause the audio file
     *
     * @param mPlayer used to handle audio file
     */

    private void pause(MediaPlayer mPlayer){
        mPlayer.release();
    }

    /**
     * toggle is called to switch between play and pause. It will play if audio is currently pause and vice versa
     *
     * @param mediaPlayer used to handle audio file
     */
    private void toggle(MediaPlayer mediaPlayer){
        Resources res = getContext().getResources();
        ImageButton playButton = new ImageButton(this.getContext());
        Bitmap playIcon = BitmapFactory.decodeResource(res, R.drawable.ic_play);
        Bitmap pauseIcon = BitmapFactory.decodeResource(res, R.drawable.ic_pause);
        if (!isPlaying){
            play(mediaPlayer);
            if (!failedPrepare) {
                playButton.setImageBitmap(pauseIcon);
            }
        } else {
            pause(mediaPlayer);
                playButton.setImageBitmap(playIcon);
        }
        isPlaying = !isPlaying;
    }

    @Override
    public View createView() {
        //create a button for user to interact with the audio file
        Resources res = getContext().getResources();
        Bitmap playIcon = BitmapFactory.decodeResource(res, R.drawable.ic_play);
        ImageButton playButton = new ImageButton(this.getContext());
        playButton.setImageBitmap(playIcon);
        //this called when the button is clicked
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                toggle(mediaPlayer);
            }
        });
        return playButton;
    }


    @Override
    public File getFile() {
        return new File(fileName);
    }


    @Override
    public void setfileDir(String newDir) {
        fileName = newDir;
    }


    @Override
    public String getfileDir() {
        return fileName;
    }

    @Override
    public String getExtension() {
        return ".3gp";
    }


}
