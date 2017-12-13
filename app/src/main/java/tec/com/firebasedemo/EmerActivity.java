package tec.com.firebasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class EmerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emer);
        //for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button emer1button = (Button) findViewById(R.id.emerbtn_1);
        emer1button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(EmerActivity.this, Emer1Activity.class));
            }
        });

        Button emer2button = (Button) findViewById(R.id.emerbtn_2);
        emer2button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(EmerActivity.this, Emer2Activity.class));
            }
        });

        Button emer3button = (Button) findViewById(R.id.emerbtn_3);
        emer3button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(EmerActivity.this, Emer3Activity.class));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
