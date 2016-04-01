package app.bit.baseclass;

import java.util.ArrayList;

import app.bit.baseclass.Multimedia.StoryPart;

/**
 * Created by bdatd on 3/12/2016.
 */
public class currentStory {
    private ArrayList<StoryPart> story = new ArrayList<StoryPart>();
    private static currentStory ourInstance = new currentStory();

    public static currentStory getInstance() {
        return ourInstance;
    }

    private currentStory() {
    }


    public void addStoryPart(StoryPart part){
        story.add(part);
    }

    public int getSize(){
        return story.size();
    }

    public void setStory(ArrayList<StoryPart> newStory){
        story  = newStory;
    }

    public ArrayList<StoryPart> getStory(){
        return story;
    }
}
