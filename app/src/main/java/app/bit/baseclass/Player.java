package app.bit.baseclass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

import app.bit.gameplay.WaitScreen;
import app.bit.longstoryshort.R;

/**
 * Player class is used to create player, including their name and their profile picture
 *
 * instances of Player class will also be stored in memory when a story session is finished
 *
 * Created by bdatd on 3/8/2016.
 */
public class Player  implements Serializable {
    private final String name;
    private final String avatarLocation;
    private static int numberofPlayer=0;
    private final int playerNumber;

    public Player(){
        name = "Player " + (numberofPlayer+1);
        avatarLocation = "";
        numberofPlayer++;
        playerNumber = numberofPlayer;
    }
    public Player(String n, String i){
        name = n;
        avatarLocation = i;
        numberofPlayer++;
        playerNumber = numberofPlayer;
    }
    public String getName(){
        return name;
    }
    public Bitmap getAvatar(){
        Bitmap avatar;
        if (avatarLocation.equals("")) {
            avatar = BitmapFactory.decodeResource(WaitScreen.thisResources(), R.drawable.ic_profile);
        }else {
            avatar = BitmapFactory.decodeFile(avatarLocation);
        }
            return avatar;
    }
}
