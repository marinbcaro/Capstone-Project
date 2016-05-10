package com.example.carolinamarin.stylestumble.categories;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.carolinamarin.stylestumble.R;
import com.example.carolinamarin.stylestumble.util.StyleStumbleApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;

import java.util.concurrent.TimeUnit;

//import com.example.carolinamarin.stylestumble.categories.Injection;

public class CategoriesActivity extends AppCompatActivity {
    private CategoriesContract.UserActionsListener mCategoriesListener;
    TagManager mTagManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //        Toolbar toolbarBottom = (Toolbar) findViewById(R.id.toolbar_bottom);
//        toolbarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch(item.getItemId()){
//                    case R.id.action_settings:
//                        // TODO
//                        break;
//                    // TODO: Other cases
//                }
//                return true;
//            }
//        });
//        // Inflate a menu to be displayed in the toolbar
//
//        toolbarBottom.inflateMenu(R.menu.menu_main);


      //  mCategoriesListener = new CategoriesPresenter(Injection.provideCategoriesRepository(), this);

        if (null == savedInstanceState) {
            initFragment(CategoriesFragment.newInstance());
        }

        // Send a hit to Analytics
        // Create a tracker
        Tracker tracker = ((StyleStumbleApplication) getApplication()).getTracker();
        tracker.setScreenName("Categories");
        // Send an event to Google Analytics
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Categories")
                .setAction("Show list of categories")
                .setLabel("Categories Label")
                .build());


    }

    public void loadGTMContainer () {
        // TODO Get the TagManager
        mTagManager = ((StyleStumbleApplication) getApplication()).getTagManager();

        // Enable verbose logging
        mTagManager.setVerboseLoggingEnabled(true);

        // Load the container
        PendingResult pending =

                mTagManager.loadContainerPreferFresh("GTM-5W92R4",
                        R.raw.stylestumble_tag);

        // Define the callback to store the loaded container
        pending.setResultCallback(new ResultCallback<ContainerHolder>() {
            @Override
            public void onResult(ContainerHolder containerHolder) {

                // If unsuccessful, return
                if (!containerHolder.getStatus().isSuccess()) {
                    // Deal with failure
                    return;
                }

                // Manually refresh the container holder
                // Can only do this once every 15 minutes or so
                containerHolder.refresh();

                // Set the container holder, only want one per running app
                // We can retrieve it later as needed
                ((StyleStumbleApplication) getApplication()).setContainerHolder(
                        containerHolder);

            }
        }, 2, TimeUnit.SECONDS);
    }

    private void initFragment(Fragment categoriesFragment) {
        // Add the NotesFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, categoriesFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
