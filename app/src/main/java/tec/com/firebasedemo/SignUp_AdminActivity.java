package tec.com.firebasedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.ui.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SignUp_AdminActivity extends AppCompatActivity implements View.OnClickListener{

    //defining view objects
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextconfirmPass;
    private Button buttonSignUp;
    private ProgressDialog progressDialog;

    private TextView textViewSignIn;

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref;

    String adminName;
    String email;
    String password;
    String confirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__admin);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseRef = FirebaseDatabase.getInstance();
        //ref = ref.child("admin").child("admin_accounts");

        /*
        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), Main_AdminActivity.class));
        }
        */

        //initializing views
        //editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        //editTextconfirmPass = (EditText) findViewById(R.id.editTextconfirmPass);
        buttonSignUp = (Button) findViewById(R.id.buttonSignup);
        textViewSignIn  = (TextView) findViewById(R.id.textViewSignin);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignUp.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
    }

    private void registerUser()
    {
        //getting email and password from edit texts
        //adminName = editTextName.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        password  = editTextPassword.getText().toString().trim();
        //confirmPass = editTextconfirmPass.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        //progressDialog.show(); //commented it because it causes an error in log

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        /*
                        if(password == confirmPass)
                        {

                        }
                        else
                        {
                            editTextPassword.setText("");
                            editTextconfirmPass.setText("");
                            Toast.makeText(SignUp_AdminActivity.this, "Password not the same.", Toast.LENGTH_SHORT).show();
                        }
                        */

                        //checking if success
                        if(task.isSuccessful())
                        {
                            //display some message here
                            saveAdminAccount();
                            //onAuthSuccess(firebaseAuth.getCurrentUser());
                            finish();
                            startActivity(new Intent(getApplicationContext(), LogIn_AdminActivity.class));
                        }
                        else
                        {
                            Toast.makeText(SignUp_AdminActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    //Gets database reference
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();


    public void saveAdminAccount()
    {
        //Gets the unique user id from the verified auth
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //adminName = editTextName.getText().toString();
        String adminEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        //String adminNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

        AdminAccount adminAccount = new AdminAccount(adminEmail); //adminName, adminNumber
        rootRef.child("admin").child("admin_account").child(uid).setValue(adminAccount);
    }

    /*
    public void saveAdminAccount()
    {
        //Gets the verified phone number
        //String userNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        String adminEmail = FirebaseUser.getEmail();

        //Gets the java object
        AdminAccount adminAccount = new AdminAccount(adminEmail);

        //Gets the unique user id from the verified auth
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Creates the child on the database
        rootRef.child("admin").child("admin_accounts").setValue(adminAccount); //used setValue() to save data to a specified reference
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = user.getEmail();
        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());
        writeNewUser(String email);
    }

    // [START basic_write]
    private void writeNewUser(String email) { //String userId, String name,
        AdminAccount adminUser = new AdminAccount(email); //name,
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        rootRef.child("admin").child("admin_account").child(uid).setValue(adminUser);
    }
    // [END basic_write]
    */

    @Override
    public void onClick(View view)
    {
        if(view == buttonSignUp){
            registerUser();
        }

        if(view == textViewSignIn){
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, LogIn_AdminActivity.class));
            //startActivity(new Intent(SignUp_AdminActivity.this, LogIn_AdminActivity.class));
        }
    }
}
