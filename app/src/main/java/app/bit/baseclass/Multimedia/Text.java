package app.bit.baseclass.Multimedia;

import android.view.View;
import android.widget.TextView;

import app.bit.gameplay.StoryActivity;

/**
 * Created by bdatd on 3/12/2016.
 */
public class Text extends StoryPart {

    private String text;

    public Text(){
        text = "";
    }
    public Text(String content){
        text = content;
    }
    public String getText(){
        return text;
    }

}
