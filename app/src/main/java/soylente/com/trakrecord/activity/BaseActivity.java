package soylente.com.trakrecord.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import soylente.com.trakrecord.DAO.Camp;
import soylente.com.trakrecord.R;

import soylente.com.trakrecord.estimote.BeaconID;
import soylente.com.trakrecord.fragments.BadgeFragment;
import soylente.com.trakrecord.fragments.CampFragment;
import soylente.com.trakrecord.fragments.HomeFragment;
import soylente.com.trakrecord.fragments.MapsFragment;
import soylente.com.trakrecord.fragments.StatsFragment;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Camp> camps = new ArrayList<>();
    ArrayList<BeaconID> beacons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("HOME!");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Firebase.setAndroidContext(this);
        loadFirebase();
        loadFragment(new HomeFragment());


    }

    private void loadFirebase() {
        Firebase ref = new Firebase("https://trakrecord.firebaseio.com/Camps");
        // Attach an listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(DataSnapshot snapshot) {
                                          for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                              camps.add(postSnapshot.getValue(Camp.class));

                                          }
                                      }

                                      @Override
                                      public void onCancelled(FirebaseError firebaseError) {
                                          System.out.println("The read failed: " + firebaseError.getMessage());
                                      }
                                  }

        );
        ref = new Firebase("https://trakrecord.firebaseio.com/Beacons");
        ref.addValueEventListener(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(DataSnapshot snapshot) {
                                          for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                              beacons.add(postSnapshot.getValue(BeaconID.class));

                                          }
                                      }

                                      @Override
                                      public void onCancelled(FirebaseError firebaseError) {
                                          System.out.println("The read failed: " + firebaseError.getMessage());
                                      }
                                  }

        );    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentManager fm = getSupportFragmentManager();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle bundle = new Bundle();

        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
        } else if (id == R.id.nav_map) {
            fragment = new MapsFragment();
            bundle.putParcelableArrayList("CAMPS", camps);
        }/* else if (id == R.id.nav_camps) {
            fragment = new CampFragment();
            bundle.putParcelable("CAMP", camps.get(2));}*/
        else if (id == R.id.nav_schedule) {

        } else if (id == R.id.nav_stats) {
            fragment = new StatsFragment();
        } else if (id == R.id.nav_badges) {
            fragment = new BadgeFragment();
            bundle.putParcelableArrayList("CAMPS", camps);
            bundle.putParcelableArrayList("BEACONS", beacons);
        }
        if (fragment != null) {
            fragment.setArguments(bundle);
            loadFragment(fragment);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(Fragment fragment) {
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();

    }
}
