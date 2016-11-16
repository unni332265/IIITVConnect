package saurabhjn76.com.iiitvconnect;

import com.google.firebase.database.DataSnapshot;

import java.util.PriorityQueue;

/**
 * Created by saurabh on 16/11/16.
 */

public class User {
    private String FirstName;
    private String LastName;
    private String Emailid;
    private String UserName;
    private String Phone;
    private  String UID;
    User() {
    }

    User(String F ,String L, String E, String U, String P,String UID){
        this.FirstName =F;
        this.LastName =L;
        this.Emailid =E;
        this.UserName =U;
        this.Phone =P;
        this.UID=UID;
    }

    public String getEmailid() {
        return Emailid;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getPhone() {
        return Phone;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUID() {
        return UID;
    }
}
