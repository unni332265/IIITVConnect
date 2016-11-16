package saurabhjn76.com.iiitvconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
       // getActionBar().setDisplayHomeAsUpEnabled(true);
        fname =(TextView)findViewById(R.id.fname);
        lname = (TextView) findViewById(R.id.lname);
        username = (TextView) findViewById(R.id.username);
        emailid =(TextView) findViewById(R.id.email);
        phoneNumber =(TextView) findViewById(R.id.phone);
        DatabaseReference userRef = mDatabase.child("users/" + firebaseAuth.getCurrentUser().getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // ...
             //   Toast.makeText(ProfileActivity.this,dataSnapshot.getValue().toString(),Toast.LENGTH_SHORT).show();

               User user = dataSnapshot.getValue(User.class);
                fname.setText(user.getFirstName());
                lname.setText(user.getLastName());
                username.setText(user.getUserName());
                emailid.setText(user.getEmailid());
                phoneNumber.setText(user.getPhone());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
                Toast.makeText(ProfileActivity.this,"Error Occured: Try again Later",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
