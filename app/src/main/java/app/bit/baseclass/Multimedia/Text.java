package app.bit.baseclass.Multimedia;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Scanner;



/**
 * Text is a subclass from abstract class StoryPart used to handle Text input by the user into the Story
 *
 * Created by bdatd on 3/12/2016.
 */
public class Text extends StoryPart {

    private String text;
    private String fileName;
    private File txtFile;

    /**
     * The constructor of Text will need the text inputted and a fileName to create a txt file to store
     * @param content the text inputted
     * @param fName the file name
     *
     */

    public Text(String content, String fName){
        //initialize variables
        text = content;
        fileName = fName;
        createFile();
    }

    /**
     *
     * @return the content that user inputted
     */
    public String getText(){
        return text;
    }

    /**
     * This will create a File so we could store Text into memory
     */

    private void createFile(){
        //using printwriter in order to put a string into a file
        txtFile = new File(fileName);
        try {
            PrintWriter out = new PrintWriter(fileName);
            out.println(text);
            out.close();
        } catch (FileNotFoundException e){
            e.getMessage();
        }
    }


    @Override
    public View createView() {
        //create a textView to show what user have inputted
        TextView textView = new TextView(this.getContext());
        textView.setText(text);
        textView.setTextSize(30);
        textView.setTextColor(Color.BLACK);
        return textView;
    }

    @Override
    public File getFile() {
        return txtFile;
    }

    @Override
    public void setfileDir(String newDir) {
        fileName = newDir;
        text = "";
        try {
            Scanner in = new Scanner(new FileReader(fileName));
            while (in.hasNext()){
               text = text + in.next();
            }
            in.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getfileDir() {
        return fileName;
    }

    @Override
    public String getExtension() {
        return ".txt";
    }


}
