package app.bit.gameplay;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.FrameLayout;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import app.bit.baseclass.Player;
import app.bit.baseclass.Singleton.ListofPlayer;
import app.bit.baseclass.Singleton.ListofStory;
import app.bit.baseclass.Multimedia.StoryPart;
import app.bit.baseclass.Story;
import app.bit.baseclass.Singleton.currentStory;
import app.bit.longstoryshort.Mainscreen;
import app.bit.longstoryshort.R;

public class StoryActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private FloatingActionButton completeStory;
    private String result;
    private FloatingActionMenu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        FloatingActionButton nextPlayer = (FloatingActionButton) findViewById(R.id.nextButton);
        completeStory = (FloatingActionButton) findViewById(R.id.completedButton);

        nextPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoryActivity.this, WaitScreen.class);
                startActivity(intent);
            }
        });

        completeStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStory();

            }
        });

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    private void saveStory(){
        AlertDialog.Builder builder = new AlertDialog.Builder(StoryActivity.this);
        LayoutInflater inflater = StoryActivity.this.getLayoutInflater();

        builder.setTitle("Name Your Story")
                .setView(inflater.inflate(R.layout.storynamesubmit, null))
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog f = (Dialog) dialog;
                        EditText contentText = (EditText) f.findViewById(R.id.storyName);
                        if (!contentText.equals("")){
                            result = contentText.getText().toString();
                            ArrayList<Player> players = ListofPlayer.getInstance().getList();
                            Story tempStory = new Story(result, players, getApplicationContext());
                            ListofStory.getInstance().addStory(result, tempStory);
                            ListofStory.getInstance().write();
                            ListofPlayer.getInstance().clear();
                            currentStory.getInstance().clear();
                            Intent intent = new Intent(StoryActivity.this, Mainscreen.class);
                            startActivity(intent);
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    @Override
    protected void onStart(){
        super.onStart();
        menu = (FloatingActionMenu) findViewById(R.id.optionButton);
        if (currentStory.getInstance().isPlayback){
            menu.setVisibility(View.GONE);
        } else {
            menu.setVisibility(View.VISIBLE);
            if (ListofPlayer.getInstance().getNumberofPlayer() > currentStory.getInstance().getSize()){
                completeStory.setVisibility(View.GONE);
            } else {
                completeStory.setVisibility(View.VISIBLE);
            }
        }
    }
    @Override
    public void onBackPressed(){
        if (!currentStory.getInstance().isPlayback) {
            Intent intent = new Intent(StoryActivity.this, WaitScreen.class);
            startActivity(intent);
        } else {
            ListofPlayer.getInstance().clear();
            currentStory.getInstance().clear();
            Intent intent = new Intent(StoryActivity.this, Mainscreen.class);
            startActivity(intent);
        }
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_story, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private int position;
        private static final String ARG_SECTION_NUMBER = "section_number";
        private FrameLayout test;
        private ArrayList<StoryPart> story = currentStory.getInstance().getStory();
        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_story, container, false);
            test = (FrameLayout) rootView.findViewById(R.id.multiView);
            StoryPart part = story.get(position);
            part.setContext(this.getContext());
            part.setActivity(this.getActivity());
            View temp = part.createView();
            temp.setLayoutParams(test.getLayoutParams());
            test.addView(temp);

            return rootView;
        }

        public void setPosition(int pos){
            position = pos;
            System.out.println(position);
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<StoryPart> story = currentStory.getInstance().getStory();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            PlaceholderFragment fragment = PlaceholderFragment.newInstance(position);
            fragment.setPosition(position);
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragment;
        }


        @Override
        public int getCount() {
            return story.size();
        }
    }
}
