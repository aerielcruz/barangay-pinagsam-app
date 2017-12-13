package tec.com.firebasedemo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.Arrays;


public class PhoneNumberAuthentication extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123; //the request code '123'

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isOnline(); //To check if the device is connected to the wifi or not

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            //DEAUTH for testing
            //FirebaseAuth.getInstance().signOut();

            // already signed in
            startActivity(new Intent(PhoneNumberAuthentication.this, BlankActivity.class));
            finish();

        } else {

            // not signed in
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(
                                    Arrays.asList(
                                            new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()
                                            ))
                            .build(),
                    RC_SIGN_IN);

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {

                //MainActivity mActivity = new MainActivity();
                //mActivity.saveUserInformation();
                saveUserInformation();

                //DEAUTH for testing
                //FirebaseAuth.getInstance().signOut();

                Toast.makeText(getApplicationContext(),"Authentication Successful!",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(PhoneNumberAuthentication.this,BlankActivity.class));
                finish();
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Log.e("Login","Login canceled by User");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Log.e("Login","No Internet Connection");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Log.e("Login","Unknown Error");
                    return;
                }
            }

            Log.e("Login","Unknown sign in response");
        }
    }

    //Gets database reference
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    public void saveUserInformation()
    {
        //Gets the verified phone number
        String userNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

        //Gets the java object
        UserInformation userInformation = new UserInformation(userNumber);

        //Gets the unique user id from the verified auth
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Creates the child on the database
        rootRef.child("residents").child(uid).setValue(userInformation); //used setValue() to save data to a specified reference
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

}
