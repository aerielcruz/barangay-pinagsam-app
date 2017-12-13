package tec.com.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by mehulkanzariya on 13/07/17.
 */

public class BlankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);

        Button chatbutton = (Button) findViewById(R.id.chatbtn);
        chatbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(BlankActivity.this, Login.class));
            }
        });
        Button orgbutton = (Button) findViewById(R.id.orgbtn);
        orgbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(BlankActivity.this, OrgActivity.class));
            }
        });
        Button mapbutton = (Button) findViewById(R.id.mapbtn);
        mapbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(BlankActivity.this, MapsActivity.class));
            }
        });
        Button docbutton = (Button) findViewById(R.id.docbtn);
        docbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(BlankActivity.this, DocumentsActivity.class));
            }
        });
        Button contactbutton = (Button) findViewById(R.id.contactbtn);
        contactbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(BlankActivity.this, ContactActivity.class));
            }
        });
        Button newsbutton = (Button) findViewById(R.id.newsbtn);
        newsbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(BlankActivity.this, DisplayImagesActivity.class));
            }
        });
        Button emerbutton = (Button) findViewById(R.id.emerbtn);
        emerbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(BlankActivity.this, EmerActivity.class));
            }
        });



    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.adminbtn:
                startActivity(new Intent(BlankActivity.this, CodeLanding_AdminActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
