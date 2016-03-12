package app.bit.longstoryshort;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class amountscreen extends AppCompatActivity {
    private Intent plyscr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amountscreen);
        final EditText amtppl = (EditText) findViewById(R.id.editText2);
        Button submit = (Button) findViewById(R.id.button);
        plyscr = new Intent(this, PlayerActivity.class);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nums = amtppl.getText().toString();
                if (nums.equals("")||nums.equals("-")) {
                    Toast.makeText(amountscreen.this,"Please enter the number of players", Toast.LENGTH_SHORT).show();
                } else {
                    int number = Integer.parseInt(nums);
                    if ((number > 1) && (number <= 6)) {
                        plyscr.putExtra("number",number);
                        startActivity(plyscr);
                    } else {
                        Toast.makeText(amountscreen.this, "There should be 2-6 players", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
