package app.bit.baseclass.Multimedia;


import android.content.Context;
import android.view.View;

public abstract class StoryPart {
    private Context context;
    public abstract View createView();
    public void setContext(Context ctx){
        context = ctx;
    }
    public Context getContext(){
        return context;
    }
}

    /**
     * Created by bdatd on 3/12/2016.
     */



