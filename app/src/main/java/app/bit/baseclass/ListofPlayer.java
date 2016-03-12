package app.bit.baseclass;

import java.util.ArrayList;

/**
 * Created by bdatd on 3/11/2016.
 */
public class ListofPlayer {
    private ArrayList<Player> list ;
    private static ListofPlayer ourInstance = new ListofPlayer();

    public static ListofPlayer getInstance() {
        return ourInstance;
    }

    private ListofPlayer() {
    }
    public void setList(ArrayList<Player> list){
        this.list = list;
    }
    public ArrayList<Player> getList(){
        return list;
    }
}
