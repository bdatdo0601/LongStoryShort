package app.bit.baseclass.Singleton;

import java.util.ArrayList;

import app.bit.baseclass.Player;

/**
 * ListofPlayer used to store a list of player during creating story session
 *
 */
public class ListofPlayer implements Session {
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
        return list;
    }
    public int getPosition(){
        return position;
    }

    public int getNumberofPlayer(){
        return list.size();
    }

    public Player getcurrentPlayer(){
        int pos = position % getNumberofPlayer();
        position++;
        Player currentPlayer = list.get(pos);
        return currentPlayer;
    }
    public Player getAssignedPlayer(){
        int pos = (position - 1) % getNumberofPlayer();
        return list.get(pos);
    }

    @Override
    public void clear() {
        position = 0;
        list = null;

    }

}
