package app.bit.baseclass.Multimedia;

import android.view.View;
import android.widget.TextView;


/**
 * Created by bdatd on 3/12/2016.
 */
public class Text extends StoryPart {

    private String text;

    public Text(String content){
        text = content;
    }
    public String getText(){
        return text;
    }

    @Override
    public View createView() {
        TextView textView = new TextView(this.getContext());
        textView.setText(text);
        textView.setTextSize(30);
        return textView;
    }


}
