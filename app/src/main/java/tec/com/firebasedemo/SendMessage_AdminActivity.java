package tec.com.firebasedemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.ui.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class SendMessage_AdminActivity extends AppCompatActivity
{
    //Set an ArrayList for the phone numbers to be stored
    ArrayList Userlist = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        //RETRIEVE DATA FROM FIREBASE DATABASAE
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("residents"); //Get datasnapshot at child "residents"
        ref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot child: dataSnapshot.getChildren()) //loop each child data
                {
                    //Gets the structure from the UserInformation class
                    UserInformation user = child.getValue(UserInformation.class);

                    //Originally --> System.out.println(user.userNumber); then added it to arraylist
                    Userlist.add(String.valueOf(user.userNumber));
                }
                System.out.println("Userlist Array: " + Userlist); //Displays the phone number array
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //handle databaseError
            }
        });

        Button sendButton = (Button) findViewById(R.id.sendbtn);
        final EditText messageArea = (EditText) findViewById(R.id.message);
        //Starts
        sendButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                //Convert EditText to string to be used to send
                String messageText = messageArea.getText().toString();

                //Better way than to for loop Userlist
                String toNumbers = TextUtils.join(";", Userlist);

                //To send SMS automatically on button click
                SmsManager.getDefault().sendTextMessage(toNumbers, null, messageText, null,null);

                //Clears the EditText field area
                messageArea.setText("");

                //Simple popup feedback
                Toast.makeText(getApplicationContext(),"Message Sent!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
