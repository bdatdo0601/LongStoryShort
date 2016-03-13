package app.bit.gameplay.Record;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import app.bit.baseclass.Multimedia.StoryPart;
import app.bit.baseclass.Multimedia.Text;
import app.bit.baseclass.currentStory;
import app.bit.gameplay.StoryActivity;
import app.bit.longstoryshort.R;

public class WriteRecord extends AppCompatActivity {
    private EditText contentText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_record);
        contentText = (EditText) findViewById(R.id.writeEditText);
        Button btn = (Button) findViewById(R.id.submit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!contentText.equals(R.string.hint)) {
                    String content = contentText.getText().toString();
                    //TODO: Do something with content
                    currentStory.getInstance().addStoryPart(new Text(content));
                    Intent intent = new Intent(WriteRecord.this,StoryActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
