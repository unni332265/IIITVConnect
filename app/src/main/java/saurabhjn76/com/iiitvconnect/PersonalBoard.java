package saurabhjn76.com.iiitvconnect;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PersonalBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
   static private RecyclerView recyclerView;
   static private TextView emptyView;
   static private List<Tile> tileList = new ArrayList<>();
   static private TilesAdapter tilesAdapter;
    static  private FloatingActionButton fab;
    private Spinner spinner;
    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    private Button deadline;
    private boolean reminder=false;
    private  String tle,desc;
    private int day=5,month=12,yar=2006;
    static private RecyclerView recyclerViewCompleted;
    static private TextView emptyViewCompleted;
    static private List<Tile> tileListCompleted = new ArrayList<>();
    static private TilesAdapter tilesAdapterCompleted;
    ArrayAdapter<String> spinnerAdapter;
    private FirebaseAuth.AuthStateListener authListener;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_board_main);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = mDatabase.child("personal/" + firebaseAuth.getCurrentUser().getUid());
        /*userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tileList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.e("postsnap", postSnapshot.getValue().toString());
                    if (postSnapshot.getValue().toString()!="false") {
                        Tile tile = new Tile(postSnapshot.child("title").getValue(String.class), postSnapshot.child("description").getValue(String.class), postSnapshot.child("id").getValue(Integer.class), postSnapshot.child("date").getValue(String.class), postSnapshot.child("completed").getValue(boolean.class));
                        tileList.add(tile);
                        tilesAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // Log.e("postsnap",dataSnapshot.getValue().toString());
                tileList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                  //  Log.e("postsnap", postSnapshot.getValue().toString());
                    if (postSnapshot.getValue().toString()!="false") {
                        Tile tile = new Tile(postSnapshot.child("title").getValue(String.class), postSnapshot.child("description").getValue(String.class), postSnapshot.child("id").getValue(Integer.class), postSnapshot.child("date").getValue(String.class), postSnapshot.child("completed").getValue(boolean.class));
                        tileList.add(tile);

                        if (tileList.size()==0) {
                            recyclerView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                        }
                        else {
                            recyclerView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);
                        }
                        tilesAdapter.notifyDataSetChanged();
                        Log.e("poap", postSnapshot.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tilesAdapter.notifyDataSetChanged();
                if (position == 0) {
                    FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
                    floatingActionButton.setVisibility(View.VISIBLE);
                }
                else
                {
                    FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
                    floatingActionButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        spinner =(Spinner) findViewById(R.id.spinner);
        spinnerAdapter = new ArrayAdapter<String>(PersonalBoard.this, R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.boards));
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PersonalBoard.this,spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

         fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(PersonalBoard.this);
                builder.setTitle("Create task tile");
                // Get the layout inflater
                LayoutInflater inflater = PersonalBoard.this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(inflater.inflate(R.layout.dialog_create_tile, null))
                        // Add action buttons
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // sign in the user ...
                                Dialog f = (Dialog) dialog;
                                final EditText title,description;
                                Switch switched;
                                switched =(Switch) f.findViewById(R.id.switch1);
                                title= (EditText) f.findViewById(R.id.title);
                                description = (EditText) f.findViewById(R.id.description);
                                deadline =(Button) f.findViewById(R.id.deadline);
                              //  switched.setChecked(false);
                                switched.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                        if(b){
                                            reminder=true;
                                            Toast.makeText(getApplicationContext(),"IN",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                            reminder =false;
                                    }
                                });
                                builder.setCancelable(false);
                                tle=title.getText().toString();
                                desc=description.getText().toString();

                                // Toast.makeText(getApplicationContext(),title.getText()+" ",Toast.LENGTH_SHORT).show();
                                Tile tile = new Tile(title.getText().toString(),description.getText().toString(),tileList.size()+1,day+"/"+month+"/"+yar,false);
                                tileList.add(tile);


                                if (tileList.size()==0) {
                                    recyclerView.setVisibility(View.GONE);
                                    emptyView.setVisibility(View.VISIBLE);
                                }
                                else {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    emptyView.setVisibility(View.GONE);
                                }

                                tilesAdapter.notifyDataSetChanged();
                                if(reminder){
                                    Calendar beginTime = Calendar.getInstance();
                                    beginTime.set(yar, month, day, 13, 4);
                                    Calendar endTime = Calendar.getInstance();
                                    endTime.set(yar, month, day+1, 8, 30);
                                    Intent intent = new Intent(Intent.ACTION_INSERT)
                                            .setData(CalendarContract.Events.CONTENT_URI)
                                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                                            .putExtra(CalendarContract.Events.TITLE, tle)
                                            .putExtra(CalendarContract.Events.DESCRIPTION, desc)
                                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                                            .putExtra(Intent.EXTRA_EMAIL, firebaseAuth.getCurrentUser().getEmail());
                                    startActivity(intent);
                                    reminder=false;
                                }
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference();
                                DatabaseReference userRef = myRef.child("personal/" + firebaseAuth.getCurrentUser().getUid()+"/"+tileList.size()+1);
                                userRef.setValue(new Tile(title.getText().toString(),description.getText().toString(),tileList.size()+1,day+"/"+month+"/"+yar,false));

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public  void Datepicker(View v){
        // Toast.makeText(getApplicationContext(),"sdsadas",Toast.LENGTH_SHORT).show();
        // Toast.makeText(v.getContext(),"sasdasd",Toast.LENGTH_SHORT).show();
        final Calendar c = Calendar.getInstance();

        DatePickerDialog dpd = new DatePickerDialog(v.getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        day=dayOfMonth;
                        month=monthOfYear;
                        yar=year;

                    }
                }, c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE));
        dpd.show();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            // Handle the camera action
            Intent intent = new Intent(PersonalBoard.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.changeEmail) {
           /* Intent intent = new Intent(PersonalBoard.this, AddRoomActivity.class);
            startActivity(intent);*/
        } else if (id == R.id.reset_password) {

        } else if (id == R.id.delete_profile) {

        } else if (id == R.id.nav_update_profile) {

        } else if (id == R.id.nav_logout) {
                firebaseAuth.signOut();
            Intent intent = new Intent(PersonalBoard.this, WelcomeActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personal_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void toggle(View view) {
        if(reminder==true)
            reminder=false;
        else
            reminder =true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_personal_board, container, false);
           // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //TODO: make swtich case here:
            switch(getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                    recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
                    emptyView = (TextView) rootView.findViewById(R.id.empty_view);
                    tilesAdapter = new TilesAdapter(tileList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());

                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(tilesAdapter);
                    if (tileList.size()==0) {
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                    }

                    else {
                        recyclerView.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                    }
                    tilesAdapter.notifyDataSetChanged();
                    break;
                case 2: //fab.setVisibility(View.INVISIBLE);
                    recyclerViewCompleted = (RecyclerView) rootView.findViewById(R.id.recycler_view);
                    emptyViewCompleted = (TextView) rootView.findViewById(R.id.empty_view);
                    tilesAdapterCompleted = new TilesAdapter(tileListCompleted);
                    RecyclerView.LayoutManager mLayoutManagerCompleted = new LinearLayoutManager(rootView.getContext());

                    recyclerViewCompleted.setLayoutManager(mLayoutManagerCompleted);
                    recyclerViewCompleted.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewCompleted.setAdapter(tilesAdapterCompleted);
                    if (tileListCompleted.size()==0) {
                        recyclerViewCompleted.setVisibility(View.GONE);
                        emptyViewCompleted.setVisibility(View.VISIBLE);
                    }

                    else {
                        recyclerViewCompleted.setVisibility(View.VISIBLE);
                        emptyViewCompleted.setVisibility(View.GONE);
                    }
                    break;

            }
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Pending";
                case 1:
                    return "Completed";
            }
            return null;
        }
    }
}
