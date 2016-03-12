package app.bit.baseclass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by bdatd on 3/8/2016.
 */
public class Player {
    private String name;
    private Bitmap avatar;
    private static int numberofPlayer=0;
    private int playerNumber;

    public Player(){
        name = "Player " + (numberofPlayer+1);
        avatar = BitmapFactory.decodeFile("@drawable/ic_profile.png");
        numberofPlayer++;
        playerNumber = numberofPlayer;
    }
    public Player(String n, Bitmap i){
        name = n;
        avatar = i;
        numberofPlayer++;
        playerNumber = numberofPlayer;
    }
    public String getName(){
        return name;
    }
    public Bitmap getAvatar(){
        return avatar;
    }
    public String toString(){
        return String.format("Name: %s%n Icon: %s%n +",name,avatar);
    }
}
