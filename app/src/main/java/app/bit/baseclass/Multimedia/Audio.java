package app.bit.baseclass.Multimedia;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageButton;


import java.io.IOException;

import app.bit.longstoryshort.R;


/**
 * Created by bdatd on 3/13/2016.
 */
public class Audio extends StoryPart {

    private String fileName;
    private MediaPlayer mPlayer;
    private Resources res;
    private Boolean isPlaying = false;
    private Boolean failedPrepare = false;
    private ImageButton playButton;
    private Bitmap playIcon;
    private Bitmap pauseIcon;

    public Audio(String location) {
        fileName = location;
    }

    private void play(){
        mPlayer =  new MediaPlayer();
        try {
            mPlayer.setDataSource(fileName);
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playButton.setImageBitmap(playIcon);
                }
            });
        } catch (IOException e) {
            failedPrepare = true;
        }
    }

    private void pause(){
        mPlayer.release();
        mPlayer = null;
    }

    private void toggle(){
        if (!isPlaying){
            play();
            if (!failedPrepare) {
                playButton.setImageBitmap(pauseIcon);
            }
        } else {
            pause();
                playButton.setImageBitmap(playIcon);
        }
        isPlaying = !isPlaying;
    }

    @Override
    public View createView() {
        res = getContext().getResources();
        playIcon = BitmapFactory.decodeResource(res, R.drawable.ic_play);
        pauseIcon = BitmapFactory.decodeResource(res,R.drawable.ic_pause);
        playButton = new ImageButton(this.getContext());
        playButton.setImageBitmap(playIcon);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        return playButton;
    }


}
