package app.bit.baseclass.Multimedia;

import android.net.Uri;
import android.view.View;

/**
 * Created by bdatd on 3/12/2016.
 */
public class Clips extends StoryPart {

    private Uri fileName;

    public Clips(){
        fileName = null;
    }

    public Clips(Uri location){
        fileName = location;
    }


}
