package saurabhjn76.com.iiitvconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private Toolbar toolbar;
    private TextView fname,lname,username,emailid,phoneNumber;
// ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        fname =(TextView)findViewById(R.id.fname);
        lname = (TextView) findViewById(R.id.lname);
        username = (TextView) findViewById(R.id.username);
        emailid =(TextView) findViewById(R.id.email);
        phoneNumber =(TextView) findViewById(R.id.phone);
        

    }
}
