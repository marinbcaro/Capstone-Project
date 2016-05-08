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
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.example.carolinamarin.stylestumble.R;
import com.example.carolinamarin.stylestumble.addProductWishList.WishListActivity;
import com.example.carolinamarin.stylestumble.addProductWishList.WishListFragment;
import com.example.carolinamarin.stylestumble.addsaleProducts.ProductSaleFragment;
import com.example.carolinamarin.stylestumble.util.EspressoIdlingResource;
import com.example.carolinamarin.stylestumble.util.StyleStumbleApplication;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;

//import android.support.v7.widget.Toolbar;


public class ProductsActivity extends AppCompatActivity {
    public static final String CAT_ID = "CAT_ID";

    private ProductsContract.UserActionsListener mActionsListener;
    private int number_products = 0;
    private int offset = 0;
    private String searchQuery;
    private Context mContext;
    private BroadcastReceiver mReceiver;
    private  String categoryId;
    private Menu menu;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        mContext=this;
         categoryId = getIntent().getStringExtra(CAT_ID);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_launcher);
        setActionBar(toolbar);
        //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setVisibility(View.VISIBLE);
        getActionBar().setDisplayShowTitleEnabled(false);

        final CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.tabanim_viewpager);
        if (viewPager != null) {
            setUpViewPager(viewPager);
            viewPager.setPagingEnabled(false);
        }


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);


        int position = getIntent().getIntExtra("POSITION_KEY",0);
        if(position!=0){
        viewPager.setCurrentItem(position);
        }

//
//        // Send data to Tag Manager
//        // Get the data layer
        TagManager tagManager = ((StyleStumbleApplication) getApplication()).getTagManager();
        DataLayer dl = tagManager.getDataLayer();

        // Push an event into the data layer
        // which will trigger sending a hit to Analytics

        dl.pushEvent("loadProducts",
                DataLayer.mapOf(
                        "screen-name", "Products"));




//        // Send a hit to Analytics
//        // Create a tracker
//        Tracker tracker = ((StyleStumbleApplication) getApplication()).getTracker();
//        tracker.setScreenName("Products");
//        // Send an event to Google Analytics
//        tracker.send(new HitBuilders.EventBuilder()
//                .setCategory("Products")
//                .setAction("Show list of products")
//                .setLabel("Products hi")
//                .build());


    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//    }



//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//       super.onCreateOptionsMenu(menu);
//        this.menu = menu;
//        inflater.inflate(R.menu.menu_products, menu);
//
//        final MenuItem searchItem = menu.findItem(R.id.search);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        return true;
//    }
    private void setUpViewPager(final CustomViewPager viewPager) {
       final  TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ProductsFragment.newInstance(categoryId), "Hot Products");
       adapter.addFragment(WishListFragment.newInstance(), "WishList");
        adapter.addFragment(ProductSaleFragment.newInstance(), "Sales");
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {


                if(position==0){
                    getActionBar().show();
                }if(position==1){

                  //  menu.findItem(R.id.search).setVisible(false);
                    getActionBar().hide();
                }
                if(position==2) {
                  //  getActionBar().hide();
                    //MenuItem item = menu.findItem(R.id.addAction);
                    adapter.getItem(position).onResume();
                }
//
////                    FragmentManager fragmentManager = getSupportFragmentManager();
////                    FragmentTransaction transaction = fragmentManager.beginTransaction();
////                    transaction.replace(R.id.contentProductSale, ProductSaleFragment.newInstance());
////                    transaction.commit();
//                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
