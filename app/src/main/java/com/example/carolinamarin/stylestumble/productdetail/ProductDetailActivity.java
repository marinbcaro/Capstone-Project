package com.example.carolinamarin.stylestumble.productdetail;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.carolinamarin.stylestumble.R;
import com.example.carolinamarin.stylestumble.util.EspressoIdlingResource;

public class ProductDetailActivity extends AppCompatActivity {
    public static final String PRODUCT_ID = "PRODUCT_ID";
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private ProductDetailFragment fragmenttest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the requested note id
        String productId = getIntent().getStringExtra(PRODUCT_ID);
        initFragment(ProductDetailFragment.newInstance(productId));



//        if (savedInstanceState != null) { // saved instance state, fragment may exist
//            // look up the instance that already exists by tag
//            fragmenttest = (ProductDetailFragment)
//                    getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
//        } else {
//            // only create fragment if they haven't been instantiated already
//            fragmenttest=new ProductDetailFragment();
//
//        //    if (!fragmenttest.isInLayout()) {
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.productDetailFrame, fragmenttest,DETAILFRAGMENT_TAG)
////                        .addToBackStack(null)
//                        .commit();
//          //  }
//        }



      //  if (savedInstanceState ==null){
//
//            FragmentManager fragmentManager =  getSupportFragmentManager();
//            ProductDetailFragment fragment = (ProductDetailFragment) fragmentManager
//                    .findFragmentByTag(DETAILFRAGMENT_TAG);
//            if(fragment==null){
//                ProductDetailFragment frag=ProductDetailFragment.newInstance(productId);
//                getSupportFragmentManager().beginTransaction()
//                        .add(frag,DETAILFRAGMENT_TAG).commit();
//                        //.add(R.id.productDetailFrame, frag,DETAILFRAGMENT_TAG).commit();
//            }


         //   initFragment(ProductDetailFragment.newInstance(productId));


    //    }
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
      //  collapsingToolbar.setTitle(cheeseName);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        String productId = getIntent().getStringExtra(PRODUCT_ID);
//        outState.putString(PRODUCT_ID, productId);
//        super.onSaveInstanceState(outState);
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    private void initFragment(Fragment detailFragment) {
        // Add the NotesDetailFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.productDetailFrame, detailFragment);
        transaction.commit();
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
