package com.handi.project2_1572006;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.handi.project2_1572006.Adapter.BarangAdapter;
import com.handi.project2_1572006.Adapter.UserAdapter;
import com.handi.project2_1572006.Entity.Barang;
import com.handi.project2_1572006.Entity.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,UserAdapter.UserDataListener,BarangAdapter.BarangDataListener {

    private DatabaseReference userRef;
    private ArrayList<User> users;
    private ArrayList<Barang> barangs;

    private UserAdapter userAdapter;
    private UserListFragment userListFragment;

    private BarangAdapter barangAdapter;
    private BarangListFragment barangListFragment;

    @BindView(R.id.rvListUser)
    RecyclerView rvListUser;

    public BarangListFragment getBarangListFragment() {
        if(barangListFragment == null){
            barangListFragment = new BarangListFragment();
            barangListFragment.getBarangAdapter().setBarangDataClickedListener(this);
        }
        return barangListFragment;
    }

    public UserListFragment getUserListFragment() {
        if(userListFragment == null){
            userListFragment = new UserListFragment();
            userListFragment.getUserAdapter().setUserDataClickedListener(this);
        }
        return userListFragment;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        users = new ArrayList<>();
        barangs = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.finish();
                MainActivity.this.startActivity(intent);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        populateBarangData();
        populateUserData();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.finish();
            MainActivity.this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_kasir) {

        } else if (id == R.id.nav_laporan) {

        } else if (id == R.id.nav_user) {
            UserFragment userFragment = new UserFragment();
            FragmentTransaction userTransaction = getSupportFragmentManager().beginTransaction();
            userTransaction.replace(R.id.leftFrame, userFragment);
            userTransaction.commit();

            FragmentTransaction userListTransaction = getSupportFragmentManager().beginTransaction();
            userListTransaction.replace(R.id.rightFrame, getUserListFragment());
            userListTransaction.commit();
            populateUserData();
        } else if (id == R.id.nav_barang) {
            BarangFragment barangFragment = new BarangFragment();
            FragmentTransaction barangTransaction = getSupportFragmentManager().beginTransaction();
            barangTransaction.replace(R.id.leftFrame, barangFragment);
            barangTransaction.commit();

            FragmentTransaction barangLisTransaction = getSupportFragmentManager().beginTransaction();
            barangLisTransaction.replace(R.id.rightFrame, getBarangListFragment());
            barangLisTransaction.commit();
            populateBarangData();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //RECYCLE VIEW
    public UserAdapter getUserAdapter() {
        if(userAdapter == null){
            userAdapter = new UserAdapter();
            userAdapter.setUserDataClickedListener(this);
        }
        return userAdapter;
    }
    public BarangAdapter getBarangAdapter() {
        if (barangAdapter == null) {
            barangAdapter = new BarangAdapter();
            barangAdapter.setBarangDataClickedListener(this);
        }
        return barangAdapter;
    }

    public void populateUserData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userRef = database.getReference();
        userRef.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    User user = new User(noteDataSnapshot.getValue(User.class));
                    System.out.println(user.toString());
                    users.add(user);
                }
                getUserAdapter().setUsers(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });
        getUserListFragment().getUserAdapter().setUsers(users);
    }
    public void populateBarangData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userRef = database.getReference();
        userRef.child("Barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                barangs.clear();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Barang barang = new Barang(noteDataSnapshot.getValue(Barang.class));
                    System.out.println(barang.toString());
                    barangs.add(barang);
                }
                getBarangAdapter().setBarangs(barangs);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });
        getBarangListFragment().getBarangAdapter().setBarangs(barangs);
    }

    @Override
    public void onBarangClicked(Barang barang) {
        if(findViewById(R.id.leftFrame)!=null){
            Bundle bundle = new Bundle();
            bundle.putParcelable(BarangFragment.ARG_Barang,barang);
            BarangFragment barangFragment = new BarangFragment();
            barangFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.leftFrame,barangFragment);
            transaction.commit();
        }
        else
        {
            Bundle bundle = new Bundle();
            bundle.putParcelable(BarangFragment.ARG_Barang,barang);
            BarangFragment barangFragment = new BarangFragment();
            barangFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.rightFrame,barangFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onUserClicked(User user) {
        if(findViewById(R.id.leftFrame)!=null){
            Bundle bundle = new Bundle();
            bundle.putParcelable(UserFragment.ARG_User,user);
            UserFragment userFragment = new UserFragment();
            userFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.leftFrame,userFragment);
            transaction.commit();
        }
        else
        {
            Bundle bundle = new Bundle();
            bundle.putParcelable(UserFragment.ARG_User,user);
            UserFragment userFragment = new UserFragment();
            userFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.rightFrame,userFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }
    }
}
