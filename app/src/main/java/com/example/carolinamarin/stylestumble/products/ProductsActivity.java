package com.example.carolinamarin.stylestumble.products;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toolbar;

import com.example.carolinamarin.stylestumble.R;
import com.example.carolinamarin.stylestumble.addProductWishList.WishListActivity;
import com.example.carolinamarin.stylestumble.util.EspressoIdlingResource;



public class ProductsActivity extends AppCompatActivity  {
    public static final String CAT_ID = "CAT_ID";

    private ProductsContract.UserActionsListener mActionsListener;
    private int number_products=0;
    private int offset=0;
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        // Set up the toolbar.
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbarTop);
      //  mCardContainer = (CardContainer) findViewById(R.id.acti);

        // Get the requested note id
        String categoryId = getIntent().getStringExtra(CAT_ID);
//        adapter = new SimpleCardStackAdapter(this);
//        mActionsListener = new ProductsPresenter(Injection.provideProductsRepository(), this);
//        mActionsListener.loadProducts(categoryId,"",offset, false);
//

        if (null == savedInstanceState) {
            initFragment(ProductsFragment.newInstance(categoryId));
        }

//        Intent intent=getIntent();
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            //use the query to search your data somehow
//
////            adapter = new SimpleCardStackAdapter(this);
////            mActionsListener = new ProductsPresenter(Injection.provideProductsRepository(), this);
////            mActionsListener.loadProducts(categoryId,query, true);
//
//
//          //  String categoryId = getIntent().getStringExtra(CAT_ID);
//            mActionsListener.loadProducts("mens-clothes",query, false);
//        }else{
//
//
//            mActionsListener.loadProducts(categoryId,"", false);
//        }

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

    public void showWishList(View view){

        Intent intent = new Intent(this, WishListActivity.class);
        startActivity(intent);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_products, menu);
//
//      final  MenuItem  searchItem = menu.findItem(R.id.search);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // Fetch the data remotely
//              //  adapter = new SimpleCardStackAdapter(getApplicationContext());
//
//             //    adapter = new SimpleCardStackAdapter(getApplicationContext());
//                searchQuery=query;
//              //
//              // mActionsListener = new ProductsPresenter(Injection.provideProductsRepository(), getApplicationContext());
//        mActionsListener.loadProducts("mens-clothes",query,offset, true);
//                // Reset SearchView
//                searchView.clearFocus();
//                searchView.setQuery("", false);
//                searchView.setIconified(true);
//                searchItem.collapseActionView();
//                // Set activity title to search query
//              //  BookListActivity.this.setTitle(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });





        // Associate searchable configuration with the SearchView
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));

//
//        SearchManager searchManager =
//                (SearchManager) getSystemService(getApplicationContext().SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));





      //  return true;
   // }

//    @Override
//    public void showProducts(List<Product> products) {
//
//        Resources r = getResources();
//
//
////        ArrayList arra=new ArrayList();
////        arra.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
//
//        for (Product product : products) {
//            // adapter.add(product.getId(), product.getDescription(),product.getName(),product.getUrl(),product.getBrand(),product.getPrice());
//            //  addProduct(product.getId(), product.getmDescription(),product.getName(),product.getUrl(),product.getBrand(),product.getPrice());
//
//            String name="";
//            Brand h=product.brand;
//            if(h!=null) {
//                 name = h.name;
//            }
//
//            CardModel card = new CardModel(product.getId(), product.getName(), product.image.sizes.IPhone.url,name,product.getPrice());
//            card.setOnCardDismissedListener(onCardDismissedListener);
//
//            adapter.add(card);
//        }
//
//
//        //   adapter.add(cardModel);
//
//
//        mCardContainer.setAdapter(adapter);
//    }

//    CardModel.OnClickListener onCardClickListener = new CardModel.OnClickListener() {
//        @Override
//        public void OnClickListener() {
//            Log.i("Swipeable Cards", "I am pressing the card" + adapter.getCount());
//
//        }
//    };

//    CardModel.OnCardDismissedListener onCardDismissedListener = new CardModel.OnCardDismissedListener() {
//        @Override
//        public void onLike() {
//            if (mCardContainer.isEmpty()) {
//                String categoryId = getIntent().getStringExtra(CAT_ID);
//                offset++;
//                adapter = new SimpleCardStackAdapter(getApplicationContext());
//                mActionsListener.loadProducts(categoryId,searchQuery,offset, true);
//
//
//            }
//         //  number_products++;
//            TextView products=   (TextView) findViewById(R.id.number_products);
//
//            products.setText(Integer.toString(number_products));
//            Log.i("Swipe Card", "Right" + mCardContainer.isEmpty());
//        }
//
//        @Override
//        public void onDislike() {
//            Log.i("Swipe Card ", "Left");
//        }
//    };

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

    private void initFragment(Fragment detailFragment) {
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
