package saurabhjn76.com.iiitvconnect;

import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by saurabh on 18/10/16.
 */

public class TilesAdapter extends RecyclerView.Adapter<TilesAdapter.MyViewHolder>{
    private List<Tile> tilesList;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, date;
        public Switch aSwitch;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user = firebaseAuth.getCurrentUser();
            mDatabase = FirebaseDatabase.getInstance().getReference();

            description = (TextView) view.findViewById(R.id.description);
            date = (TextView) view.findViewById(R.id.time);
            aSwitch =(Switch) view.findViewById(R.id.switch2);
            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   DatabaseReference userRef = mDatabase.child("personal/" + firebaseAuth.getCurrentUser().getUid()+ "/"+tilesList.get(getAdapterPosition()).getTitle());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.e("snaaaa",dataSnapshot.getValue().toString());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    if(b) {
                        userRef.child("completed").setValue(true);
                        Log.e("HEHEH", "completed");
                    }
                    else
                        userRef.child("completed").setValue(false);
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   Intent intent = new Intent(v.getContext(), TodoListActivity.class);
                    v.getContext().startActivity(intent);
                    Toast.makeText(v.getContext(), "os version is: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public TilesAdapter(List<Tile> moviesList) {
        this.tilesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tile_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Tile tile = tilesList.get(position);
        holder.title.setText(tile.getTitle());
        holder.description.setText(tile.getDescription());
        holder.date.setText(tile.getDate());
    }

    @Override
    public int getItemCount() {
        return tilesList.size();
    }
}

