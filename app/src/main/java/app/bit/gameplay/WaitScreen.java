package app.bit.gameplay;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import app.bit.baseclass.Singleton.ListofPlayer;
import app.bit.baseclass.Player;
import app.bit.longstoryshort.R;

public class WaitScreen extends AppCompatActivity {
    private String mPlayerName;
    private Bitmap mPlayerProfile;
    private TextView countDown;
    private Boolean isDone=false;
    private static Resources res;
    public static Resources thisResources(){
        return res;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = this.getResources();
        setContentView(R.layout.activity_wait_screen);
        final Player currentPlayer = ListofPlayer.getInstance().getcurrentPlayer();
        mPlayerName = currentPlayer.getName();
        mPlayerProfile = currentPlayer.getAvatar();
        TextView playerName = (TextView) findViewById(R.id.playerName);
        ImageView playerProfile = (ImageView) findViewById(R.id.imageView);
        playerName.setText(mPlayerName);
        playerProfile.setImageBitmap(mPlayerProfile);

        countDown = (TextView) findViewById(R.id.countDown);

        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                countDown.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                if (!isDone) {
                    startChoosing();
                }
            }
        }.start();

        Button readyButton = (Button) findViewById(R.id.readyButton);
        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChoosing();

            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(this,"Please wait!",Toast.LENGTH_SHORT).show();
    }

    private void startChoosing(){
        Intent choosingScreen = new Intent (WaitScreen.this, OptionActivity.class);
        startActivity(choosingScreen);
        isDone = true;
        finish();
    }
}
