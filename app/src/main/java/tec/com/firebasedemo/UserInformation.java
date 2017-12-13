package tec.com.firebasedemo;

/**
 * Created by aeriel on 06/12/2017.
 */

public class UserInformation
{
    //public String name;
    //public String value;
    public String userNumber;

    public UserInformation()
    {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserInformation(String userNumber)
    {
        //this.name = name;
        //this.value = value;
        this.userNumber = userNumber;
    }
}
