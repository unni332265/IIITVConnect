package saurabhjn76.com.iiitvconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateBoard extends AppCompatActivity {

    private EditText eventName,description;
    private Button btnCreateEvent;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_board);
        eventName =(EditText)findViewById(R.id.board_title);
        description=(EditText)findViewById(R.id.description);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
       // DatabaseReference userRef = mDatabase.child("board/" + firebaseAuth.getCurrentUser().getUid());

        btnCreateEvent = (Button) findViewById(R.id.btn_create_event);
        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference userRef = mDatabase.child("board/" +eventName.getText().toString());
                DatabaseReference userRefBoard = mDatabase.child("userboards/" + firebaseAuth.getCurrentUser().getUid());
                //userRef.child("Description").setValue(description.getText().toString());
                userRefBoard.child(eventName.getText().toString()).setValue(eventName.getText().toString());
                //Todo : Launch activity with top value as event name and intent

            }
        });


    }
}
