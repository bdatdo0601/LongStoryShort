package app.bit.baseclass.Singleton;

import java.util.HashMap;

import app.bit.baseclass.Story;

/**
 *
 * Created by datbacdo on 3/31/16.
 */

public class ListofStory {
    private static ListofStory ourInstance = new ListofStory();
    private HashMap<String, Story> storyList = new HashMap<>();
    public static ListofStory getInstance() {
        return ourInstance;
    }

    private ListofStory() {
    }
    public void addStory(String s, Story story){
        storyList.put(s, story);
    }

}
