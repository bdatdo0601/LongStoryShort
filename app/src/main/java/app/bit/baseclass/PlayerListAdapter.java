package app.bit.baseclass;

import android.widget.ArrayAdapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import app.bit.longstoryshort.R;

/**
 * Created by bdatd on 3/8/2016.
 */
public class PlayerListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] itemname;

    public PlayerListAdapter(Activity context, String[] itemname) {
        super(context, R.layout.player_list, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.player_list, null,true);


        ImageButton imageView = (ImageButton) rowView.findViewById(R.id.imageButton2);
        EditText extratxt = (EditText) rowView.findViewById(R.id.editText4);

        extratxt.setText("Player"+itemname[position]);
        return rowView;

    };
}
