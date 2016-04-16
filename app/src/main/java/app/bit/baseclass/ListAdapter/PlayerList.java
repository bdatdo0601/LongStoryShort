package app.bit.baseclass.ListAdapter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.Serializable;

import app.bit.longstoryshort.PlayerActivity;
import app.bit.longstoryshort.R;

/**
 *  PlayerList Class is a subclass of ArrayAdapter<> class, which is used to help the application view a list of item.
 *
 *  Nevertheless, PlayerList have some modication on creating a custom view including a editText view and ImageButton in order for user to input players name and choose profile icons.
 *
 *  This class is used to create a list of user on PlayerActivity screen
 *
 *  PlayerList Class also implement Serializable interface in order to be able to be stored in a file so we can store an instance in memory later
 *
 * Created by bdatd on 3/10/2016.
 */

public class PlayerList extends ArrayAdapter<String>  implements Serializable {
    private final PlayerActivity context;
    private String[] name;
    private Bitmap[] icon;
    private ImageButton plypic;
    private EditText plyname;

    /**
     * PlayerList constructor take two parameter, current context and list of names of user
     * @param context the context of current PlayerActivity instance
     * @param name list of player names
     */
    public PlayerList(PlayerActivity context, String[] name){
        super(context, R.layout.player_list,name);
        // initialize variables in PlayerList
        this.context = context;
        this.name = name;
        this.icon = new Bitmap[this.name.length];
        for (int i = 0; i<this.name.length;i++){
            this.icon[i] = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_profile);
        }
    }

    /**
     * getView override the original getView from ArrayAdapter in order to customize with more content rather than just String
     *
     * @param position the position of a certain item
     * @param view
     * @param parent
     *
     * @return the view of the specified position
     */

    @Override
    public View getView(final int position, View view, ViewGroup parent){

        //get the layout for an itemView through player_list.xml layout file

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.player_list, null, true);
        //check to make sure view is empty
        if (view == null) {
            //Initialize editText and Button
            plyname = (EditText) rowView.findViewById(R.id.editText4);
            plypic = (ImageButton) rowView.findViewById(R.id.imageButton2);

            plyname.setText(name[position]);
            plypic.setImageBitmap(icon[position]);

            //this is called when user want to take their own picture rather than the stock profile image
            plypic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.dispatchTakePictureIntent(position);
                }
            });
        } else {
            return (view);
        }

        return rowView;
    }

    /** getName used to retrieve the name user enter from input to editText
     *
     * @return A list of name that user input
     */
    public String[] getName() {
        return name;
    }

}
