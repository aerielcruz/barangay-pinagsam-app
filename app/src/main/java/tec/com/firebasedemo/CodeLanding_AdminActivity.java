package tec.com.firebasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CodeLanding_AdminActivity extends AppCompatActivity {

    //RETRIEVE DATA FROM FIREBASE DATABASAE
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("admin").child("admin_code");
    //Set an ArrayList for the phone numbers to be stored
    //String codeList;
    //ArrayList codeList = new ArrayList<String>();
    //String listString = "";
    //String toCodeString;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_landing__admin);

        ref.child("code").setValue("pass"); //admin code initialized for testing

        ref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                //AdminCode code = dataSnapshot.getValue(AdminCode.class);
                code = (String) dataSnapshot.child("code").getValue();
                //codeList.setText(String.valueOf(name));
                //codeList.setText(name);
                ref.child("code").setValue(code);

                System.out.println("Admin Code: " + code); //Displays the admin code entered
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //handle databaseError
            }
        });

        Button submitButton = (Button) findViewById(R.id.submitbtn);
        final EditText enterAdminArea = (EditText) findViewById(R.id.enterAdminText);
        //Starts
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                //Convert EditText to string to be used to send
                String codeText = enterAdminArea.getText().toString();
                //toCodeString = TextUtils.join(";", codeList); //Converts ArrayList to String
                String code = enterAdminArea.getText().toString();
                System.out.println("CodeList: " + code);

                //if(codeText != toCodeString)
                //if(codeText != code)
                if(codeText.equals(code))
                {
                    //Clears the EditText field area
                    enterAdminArea.setText("");

                    //Simple popup feedback
                    Toast.makeText(getApplicationContext(),"ACCESS GRANTED!",Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(), LogIn_AdminActivity.class)); //Was LogIn_AdminActivity
                    //startActivity(new Intent(CodeLanding_AdminActivity.this, LogIn_AdminActivity.class));
                    finish();
                }
                else
                {
                    //Admin Code Attempts: x
                    Toast.makeText(getApplicationContext(),"ACCESS DENIED!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
