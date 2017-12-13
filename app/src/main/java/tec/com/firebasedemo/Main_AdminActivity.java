package tec.com.firebasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main_AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__admin);

        Button smsbutton = (Button) findViewById(R.id.smsbtn);
        smsbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Main_AdminActivity.this, SendMessage_AdminActivity.class));
            }
        });
        Button changeCodeButton = (Button) findViewById(R.id.changeCodeBtn);
        changeCodeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Main_AdminActivity.this, Code_AdminActivity.class));
            }
        });
        Button uploadbutton = (Button) findViewById(R.id.uploadbtn);
        uploadbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Main_AdminActivity.this, NewsUpload_AdminActivity.class));
            }
        });
        Button adminChatBtn = (Button) findViewById(R.id.adminChatBtn);
        adminChatBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Main_AdminActivity.this, Users.class));
            }
        });
        Button homebutton = (Button) findViewById(R.id.homeBtn);
        homebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Main_AdminActivity.this, BlankActivity.class));
            }
        });
    }
}
