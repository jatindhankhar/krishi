package in.jatindhankhar.krishi;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,MarketFragment.itemInteractionListener,ColdStoreFragment.OnFragmentInteractionListener {
    private Button button;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<DataModel> mResponseList;
    private FloatingSearchView floatingSearchView;
    private boolean searchEnabled = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(savedInstanceState == null)
        {
            Fragment fragment = null;
            Class fragmentClass= null;
            fragmentClass = MarketFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        }
        //button = (Button) findViewById(R.id.button);
        //mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        //button.setOnClickListener(handleClick);
        floatingSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toolbar.setVisibility(View.GONE);
                //((FloatingSearchView) (findViewById(R.id.floating_search_view))).setVisibility(View.VISIBLE);
                //fab.hide();
                if(!searchEnabled)
                {
                    assert toolbar != null;
                    toolbar.setVisibility(View.GONE);
                    floatingSearchView.setVisibility(View.VISIBLE);
                    fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.ic_clear));
                    fab.animate().rotation(180)
                            .setDuration(1000)
                            .start();
                    searchEnabled = true;
                }
                else
                {

                    floatingSearchView.setVisibility(View.GONE);
                    assert toolbar != null;
                    toolbar.setVisibility(View.VISIBLE);
                    fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.ic_search));
                    fab.animate().rotation(0).setDuration(1000).start();
                    searchEnabled = false;

                }

            }
        });

        //mResponseList = new ArrayList<DataModel>();

        //mLayoutManager = new LinearLayoutManager(this);
        //mRecyclerView.setLayoutManager(mLayoutManager);
        //mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.animate();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private View.OnClickListener handleClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            button.setVisibility(View.GONE);
            ((ProgressBar)findViewById(R.id.progressBar)).setVisibility(View.VISIBLE);
            String key = BuildConfig.MyGovToken;
            Ion.with(getApplicationContext()).load("https://data.gov.in/api/datastore/resource.json?resource_id=9ef84268-d588-465a-a308-a864a43d0070&api-key=533060153c848cf636069b6cbcb3e841").
                    asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    ((ProgressBar)findViewById(R.id.progressBar)).setVisibility(View.GONE);
                    if(e == null)
                    {
                        JsonArray records = result.getAsJsonArray("records");
                        for(JsonElement el : records)
                        {

                            Gson gson = new Gson();
                            mResponseList.add(gson.fromJson(el,DataModel.class));

                        }
                        ((ProgressBar)findViewById(R.id.progressBar)).setVisibility(View.GONE);
                        //mAdapter = new AdvancedAdapter(mResponseList);
                        mRecyclerView.setAdapter(mAdapter);

                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Sorry, there was some error parsing response!", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    };

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        Class fragmentClass = MarketFragment.class;
        if (id == R.id.nav_camera) {
            fragmentClass = MarketFragment.class;
        }
        else if (id == R.id.nav_gallery){
            fragmentClass = SeedFragment.class;
        }
        else if(id == R.id.nav_slideshow)
        {
            fragmentClass = ColdStoreFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void itemInteraction(DataModel dataModel) {
        Toast.makeText(MainActivity.this, dataModel.mcommodity, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onFragmentInteraction(ColdStoreModel coldStoreModel) {
        Toast.makeText(MainActivity.this, coldStoreModel.store_name, Toast.LENGTH_SHORT).show();
    }
}
