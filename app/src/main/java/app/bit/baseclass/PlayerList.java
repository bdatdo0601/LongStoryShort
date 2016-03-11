package app.bit.baseclass;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.app.Activity;
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
    private final int[] num;
    private final String[] name;
    private int temp_pos;
    private Bitmap[] icon;
    private ImageButton plypic;

    public PlayerList(PlayerActivity context, String[] name,int[] num){
        super(context, R.layout.player_list,name);
        this.context = context;
        this.name = name;
        this.num = num;
        this.icon = new Bitmap[this.name.length];
        for (int i = 0; i<this.name.length;i++){
            this.icon[i] = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_profile);
        }
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.player_list, null, true);

        EditText plyname = (EditText) rowView.findViewById(R.id.editText4);
        plypic = (ImageButton) rowView.findViewById(R.id.imageButton2);

        plyname.setText(name[position]);
        plypic.setImageBitmap(icon[position]);

        plypic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp_pos = position;
                context.dispatchTakePictureIntent();
            }
        });
        return rowView;
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public void setCurrentIcon(Bitmap icon){
        icon = getResizedBitmap(icon,230,230);
        this.icon[temp_pos] = icon;
        plypic.setImageBitmap(icon);
    }
    public String[] getName() {
        return name;
    }


}
