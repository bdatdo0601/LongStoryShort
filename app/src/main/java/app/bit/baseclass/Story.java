package app.bit.baseclass;

import android.content.Context;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import app.bit.baseclass.Multimedia.StoryPart;
import app.bit.baseclass.Singleton.currentStory;

/**
 * Created by datbacdo on 3/24/16.
 */
public class Story  {
    private ArrayList<StoryPart> story;
    private String storyName;
    private Context ctx;


    public Story(String s, Context context){
        story = currentStory.getInstance().getStory();
        storyName = s;
        ctx = context;
        reorganizeStory();

    }

    public void reorganizeStory(){

        for (int i = 0; i<story.size(); i++){
            String newDir = ctx.getExternalFilesDir(storyName).getAbsolutePath() + "/" + i + story.get(i).getExtension();
            try {
                FileUtils.moveDirectory(story.get(i).getFile(), new File(newDir));
                story.get(i).setfileDir(newDir);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String getStoryName(){
        return storyName;
    }
    public ArrayList<StoryPart> getStory(){
        return story;
    }

}
