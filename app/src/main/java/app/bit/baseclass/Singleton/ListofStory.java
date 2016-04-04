package app.bit.baseclass.Singleton;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.bit.baseclass.Story;

/**
 *
 * Created by datbacdo on 3/31/16.
 */

public class ListofStory{
    private static ListofStory ourInstance = new ListofStory();
    private HashMap<String, Story> storyList = new HashMap<>();
    private ArrayList<String> storyNames = new ArrayList<>();
    private String fileName;
    public static ListofStory getInstance() {
        return ourInstance;
    }
    public void reinitialize(){
        storyList = new HashMap<>();
        storyNames = new ArrayList<>();
    }
    public void write(){
        try {
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            for(Map.Entry<String, Story>entrySet:storyList.entrySet()){
                out.writeUTF(entrySet.getKey());
                Story setValue = entrySet.getValue();
                out.writeObject(setValue);
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void read(){
        try {
                FileInputStream fIn = new FileInputStream(new File(fileName));
                ObjectInputStream in = new ObjectInputStream(fIn);
                while (in.available() > 0) {
                    storyList.put(in.readUTF(), (Story) in.readObject());
                    System.out.print("?");
                }
            for(Map.Entry<String, Story>entrySet:storyList.entrySet()){
                storyNames.add(entrySet.getKey());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private ListofStory() {
        fileName = "/storage/emulated/0/Android/data/app.bit.longstoryshort/files/TEMPORARY_FILE/listofstory.bin";
    }
    public void addStory(String s, Story story){
        storyList.put(s, story);
        storyNames.add(s);
    }

    public ArrayList<String> getStoryNames(){
        return storyNames;
    }
    public HashMap<String, Story> getStoryList() {
        return storyList;
    }


}
