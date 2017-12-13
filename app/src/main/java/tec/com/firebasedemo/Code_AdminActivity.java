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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Code_AdminActivity extends AppCompatActivity
{
    //This is where the admin could edit the admin code in CodeLanding_AdminActivity

    String codeText;
    //RETRIEVE DATA FROM FIREBASE DATABASAE
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("admin").child("admin_code");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_code);

        Button updateButton = (Button) findViewById(R.id.updatebtn);
        final EditText codeArea = (EditText) findViewById(R.id.codeUpdate);

        updateButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                //Convert EditText to string to be used to send
                codeText = codeArea.getText().toString();

                ref.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        //codeText = (String) dataSnapshot.child("code").getValue();

                        ref.child("code").setValue(codeText);

                        System.out.println("Admin Code: " + codeText); //Displays the admin code entered
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

                //Better way than to for loop Userlist
                //String toNumbers = TextUtils.join(";", Userlist);

                //Simple popup feedback
                Toast.makeText(getApplicationContext(),"New Admin Code [" + codeText + "] Updated!",Toast.LENGTH_SHORT).show();
                sendEmail();


                //Clears the EditText field area
                codeArea.setText("");


            }
        });

        //String admin_code = FirebaseAuth.getInstance().getCurrentUser().child("admin").child("admin_code");
        //Gets the java object
        //AdminCode admin_code = new AdminCode();


    }

    DatabaseReference emailRef = FirebaseDatabase.getInstance().getReference();
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String adminName;
    //String code;
    DataSnapshot codeSnap;

    //ArrayList Adminlist = new ArrayList<String>();
    List<String> Adminlist = new ArrayList<String>();


    protected void sendEmail() {
        emailRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                //codeSnap = (DataSnapshot) dataSnapshot.child("admin").child("admin_code").getValue();
                //adminName = (String) dataSnapshot.child("admin").child("admin_accounts").child(uid).child("name").getValue();

                for (DataSnapshot child: dataSnapshot.child("admin").child("admin_account").getChildren()) //loop each child data
                {
                    AdminAccount adminUser = child.getValue(AdminAccount.class); //gets the structure
                    Adminlist.add(String.valueOf(adminUser.email)); //puts it in a list
                }
                System.out.println("Admin List: " + Adminlist);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //handle databaseError
            }
        });
        //Log.i("Send email", "");
        //String[] TO = {""};
        //String[] CC = {""};

        String toAdminNum = TextUtils.join(",", Adminlist); //delimiter was ";"

        //List<Adminlist> list = new ArrayList<Adminlist>();
        //Adminlist[] countries = list.toArray(new Adminlist[list.size()]);

        //String[] adminArray = (String[]) Adminlist.toArray(new String[Adminlist.size()]);
        //System.out.println("String Admin List: " + adminArray);

        System.out.println("String Admin List: " + toAdminNum);

        Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);

        //emailIntent.setData(Uri.parse("mailto:"));
        //emailIntent.setType("text/plain");
        emailIntent.setType("message/rfc822"); //or the chooser will look for many applications
        emailIntent.putExtra(Intent.EXTRA_EMAIL, toAdminNum);
        //emailIntent.putExtra(Intent.EXTRA_EMAIL, sb.toString());
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New Admin Passcode (PINAGSAM-APP!)");

        emailIntent.putExtra(Intent.EXTRA_TEXT, "New Admin Passcode: " + codeText);

        try {
            startActivity(Intent.createChooser(emailIntent, "Choose an email client:"));
            finish();
            Log.i("Email sent!", "");
            Toast.makeText(getApplicationContext(),"Please check your email.",Toast.LENGTH_LONG).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Code_AdminActivity.this, "No email addresses found.", Toast.LENGTH_SHORT).show();
        }
    }
}
