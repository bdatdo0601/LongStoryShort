package app.bit.longstoryshort;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.bit.baseclass.Singleton.ListofPlayer;
import app.bit.baseclass.Singleton.ListofStory;
import app.bit.baseclass.Singleton.currentStory;
import app.bit.baseclass.Story;
import app.bit.gameplay.StoryActivity;


public class Mainscreen extends AppCompatActivity {
    private HashMap<String, Story> stories;
    private ListView storyList;
    private ArrayList<String> storiesName;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListofStory.getInstance().reinitialize();
        ListofStory.getInstance().read();
        stories = ListofStory.getInstance().getStoryList();
        // If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.content_mainscreen);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.menu_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStory.getInstance().isPlayback = false;
                currentStory.getInstance().reinitialize();
                Intent amtscr = new Intent(Mainscreen.this, amountscreen.class);
                startActivity(amtscr);
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.deleteall);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListofStory.getInstance().reinitialize();
                ListofStory.getInstance().write();
                ListofStory.getInstance().read();
                stories = ListofStory.getInstance().getStoryList();
                storiesName = ListofStory.getInstance().getStoryNames();
                adapter.notifyDataSetChanged();
            }
        });
        storiesName = ListofStory.getInstance().getStoryNames();
        storyList = (ListView) findViewById(R.id.storyList);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, storiesName);
        storyList.setAdapter(adapter);

        storyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String storyName = (String) parent.getItemAtPosition(position);
                currentStory.getInstance().isPlayback = true;
                currentStory.getInstance().setStory(stories.get(storyName).getStory());
                ListofPlayer.getInstance().setList(stories.get(storyName).getPlayers());
                Intent intent = new Intent(Mainscreen.this, StoryActivity.class);
                startActivity(intent);
            }
        });


    }


}
