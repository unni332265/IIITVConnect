package saurabhjn76.com.iiitvconnect;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by saurabh on 18/10/16.
 */

public class TilesAdapterr extends RecyclerView.Adapter<TilesAdapterr.MyViewHolder>{
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
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   Intent intent = new Intent(v.getContext(), TodoListActivity.class);
                    intent.putExtra("TITLE",tilesList.get(getAdapterPosition()).getTitle());
                    intent.putExtra("DESC",tilesList.get(getAdapterPosition()).getDescription());
                    intent.putExtra("ID",tilesList.get(getAdapterPosition()).getId());
                    intent.putExtra("DATE",tilesList.get(getAdapterPosition()).getDate());
                    intent.putExtra("STATUS",tilesList.get(getAdapterPosition()).getcompleted());
                    v.getContext().startActivity(intent);
                    //Toast.makeText(v.getContext(), "os version is: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public TilesAdapterr(List<Tile> moviesList) {
        this.tilesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tileeerow, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Tile tile = tilesList.get(position);
        holder.title.setText(tile.getTitle());
        holder.description.setText(tile.getDescription());
        holder.date.setText(tile.getDate());
        holder.aSwitch.setChecked(tile.getcompleted());
        if(tile.getcompleted()){
            holder.aSwitch.setText("Mark it as Incomplete ");
        }
        else
            holder.aSwitch.setText("Mark it as Complete ");

    }

    @Override
    public int getItemCount() {
        return tilesList.size();
    }
}

