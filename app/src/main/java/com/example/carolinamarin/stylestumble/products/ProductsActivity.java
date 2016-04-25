package com.example.carolinamarin.stylestumble.products;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.TabLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toolbar;

import com.example.carolinamarin.stylestumble.R;
import com.example.carolinamarin.stylestumble.addProductWishList.WishListActivity;
import com.example.carolinamarin.stylestumble.addProductWishList.WishListFragment;
import com.example.carolinamarin.stylestumble.util.EspressoIdlingResource;

//import android.support.v7.widget.Toolbar;


public class ProductsActivity extends AppCompatActivity {
    public static final String CAT_ID = "CAT_ID";

    private ProductsContract.UserActionsListener mActionsListener;
    private int number_products = 0;
    private int offset = 0;
    private String searchQuery;
    private Context mContext;
    private BroadcastReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        mContext=this;
        String categoryId = getIntent().getStringExtra(CAT_ID);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.tabanim_viewpager);
        if (viewPager != null) {
            setUpViewPager(viewPager);
            viewPager.setPagingEnabled(false);
        }


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);



    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//    }



    private void setUpViewPager(CustomViewPager viewPager) {
        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ProductsFragment.newInstance("womens-clothes"), "Category 1");
        adapter.addFragment(new WishListFragment(), "Category 2");
        adapter.addFragment(new WishListFragment(), "Category 3");
        viewPager.setAdapter(adapter);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow

        }
    }

    public void showWishList(View view) {

        Intent intent = new Intent(this, WishListActivity.class);
        startActivity(intent);
    }


    public void initFragment(Fragment detailFragment) {
        // Add the NotesDetailFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame2, detailFragment);
        transaction.commit();
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }


}
