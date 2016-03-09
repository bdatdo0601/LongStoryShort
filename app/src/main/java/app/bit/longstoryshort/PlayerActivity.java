package app.bit.longstoryshort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;

import app.bit.baseclass.PlayerListAdapter;

public class PlayerActivity extends AppCompatActivity {
    private int NUMBER_OF_PLAYER ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Intent plyamt = getIntent();
        NUMBER_OF_PLAYER = plyamt.getIntExtra("number",2);
        String[] defaultplayername = new String[NUMBER_OF_PLAYER];
        for (int i = 0; i<defaultplayername.length; i++){
            defaultplayername[i]= "player"+ Integer.toString(i+1);
        }
        PlayerListAdapter test = new PlayerListAdapter(this, defaultplayername);
        ListView playerList = (ListView) findViewById(R.id.listView);



    }
}
