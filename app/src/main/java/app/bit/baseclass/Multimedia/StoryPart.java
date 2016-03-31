package app.bit.baseclass.Multimedia;


import android.app.Activity;
import android.content.Context;
import android.view.View;

import java.io.File;

import app.bit.baseclass.ListofPlayer;
import app.bit.baseclass.Player;

public abstract class StoryPart {
    private Context context;
    private Activity activity;
    private Player assignedPlayer;
    public StoryPart(){
        assignedPlayer = ListofPlayer.getInstance().getcurrentPlayer();
    }
    public abstract View createView();
    public void setActivity(Activity act){activity = act;}
    public void setContext(Context ctx){
        context = ctx;
    }
    public Context getContext(){
        return context;
    }
    public abstract File getFile();
    public abstract void setfileDir(String newDir);
    public abstract String getfileDir();
    public abstract String getExtension();
    public Activity getActivity(){return activity;}
    public Player getAssignedPlayer(){
        return assignedPlayer;
    }
}

    /**
     * Created by bdatd on 3/12/2016.
     */



