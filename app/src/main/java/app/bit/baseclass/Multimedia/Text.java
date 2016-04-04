package app.bit.baseclass.Multimedia;

import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


/**
 * Created by bdatd on 3/12/2016.
 */
public class Text extends StoryPart {

    private String text;
    private String fileName;
    private File txtFile;

    public Text(String content, String fName){
        text = content;
        fileName = fName;
        createFile();
    }
    public String getText(){
        return text;
    }
    private void createFile(){

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
        TextView textView = new TextView(this.getContext());
        textView.setText(text);
        textView.setTextSize(30);
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
