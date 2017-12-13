package tec.com.firebasedemo;

/**
 * Created by aeriel on 07/12/2017.
 */

public class AdminCode
{
    public String code; //admin_



    public AdminCode()
    {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public AdminCode(String code) {
        this.code = code;
    }
}

