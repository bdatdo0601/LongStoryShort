package app.bit.baseclass.Multimedia;


import android.app.Activity;
import android.content.Context;
import android.view.View;

import java.io.File;
import java.io.Serializable;

import app.bit.baseclass.Singleton.ListofPlayer;
import app.bit.baseclass.Player;

/**
 * Abstract Class StoryPart is a template to store each part of the story
 *
 * this is implements with Serializable in order to store the instances to a file and to memory
 *
 * Created by bdatd on 3/12/2016.
 */

public abstract class StoryPart implements Serializable{
    private transient Context context;
    private transient Activity activity;
    private Player assignedPlayer;

    /**
     * This will get the player who have entered a specific story part
     */
    public StoryPart(){
        assignedPlayer = ListofPlayer.getInstance().getAssignedPlayer();
    }

    /**
     * this will create view so user can see either text, audio or clips from the story. This will be rewritten to view different specific type of input
     * @return the view to show on applicaiton
     */
    public abstract View createView();
    public void setActivity(Activity act){activity = act;}
    public void setContext(Context ctx){
        context = ctx;
    }
    public Context getContext(){
        return context;
    }

    /**
     *
     * @return the File of the part
     */
    public abstract File getFile();

    /**
     * change the file directory of a part to another file
     * @param newDir
     */
    public abstract void setfileDir(String newDir);

    /**
     *
     * @return the file directory of a specific part
     */
    public abstract String getfileDir();

    public abstract String getExtension();
    public Activity getActivity(){return activity;}
    public Player getAssignedPlayer(){
        return assignedPlayer;
    }
}




