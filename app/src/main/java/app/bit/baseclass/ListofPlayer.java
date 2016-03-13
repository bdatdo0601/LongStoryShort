package app.bit.baseclass;

import java.util.ArrayList;

/**
 * Created by bdatd on 3/11/2016.
 */
public class ListofPlayer {
    private ArrayList<Player> list ;
    private static int position = 0;
    private static ListofPlayer ourInstance = new ListofPlayer();

    public static ListofPlayer getInstance() {
        return ourInstance;
    }

    private ListofPlayer() {
    }

    public final void setList(ArrayList<Player> list){

        this.list = list;
        position = 0;
    }
    public ArrayList<Player> getList(){
        position++;
        return list;
    }
    public int getPosition(){
        return position;
    }
    public Player getcurrentPlayer(){
        Player currentPlayer = list.get(position);
        return currentPlayer;
    }
}
