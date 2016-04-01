package app.bit.baseclass.Listadapter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;


import app.bit.longstoryshort.PlayerActivity;
import app.bit.longstoryshort.R;

/**
 * Created by bdatd on 3/10/2016.
 */
public class PlayerList extends ArrayAdapter<String> {
    private final PlayerActivity context;
    private String[] name;
    private Bitmap[] icon;
    private ImageButton plypic;
    private EditText plyname;
    public PlayerList(PlayerActivity context, String[] name){
        super(context, R.layout.player_list,name);
        this.context = context;
        this.name = name;
        this.icon = new Bitmap[this.name.length];
        for (int i = 0; i<this.name.length;i++){
            this.icon[i] = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_profile);
        }
    }
    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.player_list, null, true);
        if (view == null) {
            plyname = (EditText) rowView.findViewById(R.id.editText4);
            plypic = (ImageButton) rowView.findViewById(R.id.imageButton2);

            plyname.setText(name[position]);
            plypic.setImageBitmap(icon[position]);

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

    public String[] getName() {
        return name;
    }
    public Bitmap[] getIcon() {
        return icon;
    }

}
