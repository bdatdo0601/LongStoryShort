package app.bit.baseclass.Multimedia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.TextView;

/**
 * Created by bdatd on 3/12/2016.
 */
public class Picture extends StoryPart {

    private Bitmap picture;

    public Picture(){
        picture = null;
    }
    public Picture(String location){
        picture = BitmapFactory.decodeFile(location);

    }


}
