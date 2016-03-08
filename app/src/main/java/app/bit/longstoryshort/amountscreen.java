package app.bit.longstoryshort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class amountscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amountscreen);
        final EditText amtppl = (EditText) findViewById(R.id.editText2);
        Button submit = (Button) findViewById(R.id.button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nums = amtppl.getText().toString();
                if (nums.equals("")) {
                    Toast.makeText(amountscreen.this,"Please enter the number of players", Toast.LENGTH_SHORT).show();
                } else {
                    int number = Integer.parseInt(nums);
                    if ((number > 1) && (number <= 15)) {

                    } else {
                        Toast.makeText(amountscreen.this, "There should be 2-15 players", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
