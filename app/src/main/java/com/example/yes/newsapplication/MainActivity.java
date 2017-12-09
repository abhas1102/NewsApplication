package com.example.yes.newsapplication;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private static final int NEWS_LOADER_ID = 1;
    public static final String LOG_TAG = MainActivity.class.getName();
    public String GAURDIAN_REQUEST_URL = "http://content.guardianapis.com/search?api-key=709a5fc2-f326-4d66-b017-ddacf05adc4c&page-size=10&order-by=newest&show-fields=standfirst,starRating,headline,thumbnail,short-url,lastModified,byline"; ;
    private NewsAdapter mAdapter;
    private DrawyerLayout drawyerLayout;
    public ListView newsListView;
    public int length = 10;
    ConnectivityManager connectivityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        newsListView = (ListView) findViewById(R.id.list);
        ImageView noInternet = (ImageView) findViewById(R.id.no_net_image);
        ImageView noResults = (ImageView) findViewById(R.id.no_result);

        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        noInternet.setVisibility(View.GONE);
        noResults.setVisibility(View.GONE);

        // Replace the default action bar with a Toolbar so the navigation drawer appears above it
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.rgb(228, 80, 79));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                drawerOptionClick();
                refreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        drawerOptionClick();
                    }
                }, 1000);
            }
        });

        drawerOptionClick();

        //Now Navigation view will be initialised

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        Menu menu = navigationView.getMenu();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked())
                    menuItem.setChecked(false);
                else
                    menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and open the Appopriate news accordingly
                switch (menuItem.getItemId()) {

                    case R.id.world:
                        GAURDIAN_REQUEST_URL = "http://content.guardianapis.com/search?api-key=709a5fc2-f326-4d66-b017-ddacf05adc4c&section=world";
                        setTitle("World News");
                        return true;

                    case R.id.fashion:
                        GAURDIAN_REQUEST_URL = "http://content.guardianapis.com/search?api-key=709a5fc2-f326-4d66-b017-ddacf05adc4c&section=fashion";
                        setTitle("Fashion News");
                        return true;

                    case R.id.sports:
                        GAURDIAN_REQUEST_URL = "http://content.guardianapis.com/search?api-key=709a5fc2-f326-4d66-b017-ddacf05adc4c&section=sport";
                        setTitle("Sports News");
                        return true;

                    case R.id.business:
                        GAURDIAN_REQUEST_URL = "http://content.guardianapis.com/search?api-key=709a5fc2-f326-4d66-b017-ddacf05adc4c&section=business";
                        setTitle("Business News");
                        return true;

                    case R.id.technology:
                        GAURDIAN_REQUEST_URL = "http://content.guardianapis.com/search?api-key=709a5fc2-f326-4d66-b017-ddacf05adc4c&section=technology";
                        setTitle("Technology News");
                        return true;


                    default:
                        Toast.makeText(getApplicationContext(), "Check , Is everything right?", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);

            }
        };

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    private void drawerOptionClick() {

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Log.v("mainActivity", "Connectivity check");
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.i(LOG_TAG, "Net Is connected");
            doSearch();
        } else {
            Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

        private void doSearch() {

        mAdapter = new NewsAdapter(MainActivity.this, new ArrayList<News>());
        newsListView.setAdapter(mAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current news that was clicked on
                News currentNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getUrl());

                // Create a new intent to view the new URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

         connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Log.v("mainActivity", "Connectivity check");
        if (networkInfo != null && networkInfo.isConnected()) {

            Log.i(LOG_TAG, "If network is there");
            newsListView.setVisibility(View.VISIBLE);

            LoaderManager loaderManager = getLoaderManager();
            Log.i(LOG_TAG, "Calling init loader");

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, MainActivity.this);
            loaderManager.restartLoader(NEWS_LOADER_ID, null, MainActivity.this);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        Log.i(LOG_TAG, "onCreateLoader called");

        Uri baseUri = Uri.parse(GAURDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("page-size", String.valueOf(length));
        uriBuilder.appendQueryParameter("show-fields", "standfirst,starRating,headline,thumbnail,short-url,bodyText,lastModified,byline");

        Log.v("uriBuilder", String.valueOf(uriBuilder));
        return new NewsLoader(this, uriBuilder.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {

        Log.i(LOG_TAG, "onLoadFinished called");

        mAdapter.clear();

        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        } else {
            Toast.makeText(getApplicationContext(), "No Results Found", Toast.LENGTH_SHORT).show();
    }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

        Log.i(LOG_TAG, "onLoadReset called");
        mAdapter.clear();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            GAURDIAN_REQUEST_URL = "http://content.guardianapis.com/search?api-key=709a5fc2-f326-4d66-b017-ddacf05adc4c&page-size=10&order-by=newest&show-fields=standfirst,starRating,headline,thumbnail,short-url,lastModified,byline";
            setTitle("News Mart");
            drawerOptionClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
