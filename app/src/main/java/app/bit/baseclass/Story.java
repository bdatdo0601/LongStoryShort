package app.bit.baseclass;

import android.content.Context;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import app.bit.baseclass.Multimedia.StoryPart;
import app.bit.baseclass.Singleton.currentStory;

/**
 * Story class will create one story with multiple story parts.
 *
 * Created by datbacdo on 3/24/16.
 */
public class Story implements Serializable{
    private ArrayList<StoryPart> story;
    private String storyName;
    private transient Context ctx;
    private ArrayList<Player> players;

    public Story(String s, ArrayList<Player> players1 ,Context context){
        story = currentStory.getInstance().getStory();
        players = players1;
        storyName = s;
        ctx = context;
        reorganizeStory();
    }

    private void reorganizeStory(){

        for (int i = 0; i<story.size(); i++){
            String newDir = ctx.getExternalFilesDir(storyName).getAbsolutePath() + "/" + i + story.get(i).getExtension();
            System.out.println(newDir);
            try {
                new File(newDir).delete();
                FileUtils.moveFile(story.get(i).getFile(), new File(newDir));
                story.get(i).setfileDir(newDir);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public ArrayList<Player> getPlayers(){return players;}
    public String getStoryName(){
        return storyName;
    }
    public ArrayList<StoryPart> getStory(){
        return story;
    }

}
