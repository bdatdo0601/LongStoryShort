package app.bit.baseclass.Multimedia;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

import java.io.File;
import java.net.URI;

/**
 * Created by bdatd on 3/12/2016.
 */
public class Clips extends StoryPart {

    private String fileName;
    private boolean isTouched = false;


    public Clips(String location){
        fileName = location;
    }



    @Override
    public View createView() {
        final VideoView clipView = new VideoView(getContext());
        clipView.setVideoURI(Uri.parse(fileName));
        final Handler mHandler = new Handler();
        clipView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!isTouched) {
                    isTouched = true;
                    clipView.seekTo(0);
                    clipView.start();
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            isTouched = false;
                        }
                    }, 100);
                }
                return true;
            }
        });
        return clipView;
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
        return ".mp4";
    }


}
