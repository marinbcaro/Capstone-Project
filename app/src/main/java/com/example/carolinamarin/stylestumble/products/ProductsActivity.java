package com.example.carolinamarin.stylestumble.products;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.util.Log;

import com.example.carolinamarin.stylestumble.Injection;
import com.example.carolinamarin.stylestumble.R;
import com.example.carolinamarin.stylestumble.data.Product;
import com.example.carolinamarin.stylestumble.util.EspressoIdlingResource;
import com.example.carolinamarin.swipecards.model.CardModel;
import com.example.carolinamarin.swipecards.view.CardContainer;
import com.example.carolinamarin.swipecards.view.SimpleCardStackAdapter;

import java.util.List;

public class ProductsActivity extends Activity implements ProductsContract.View {
    public static final String CAT_ID = "CAT_ID";
    private CardContainer mCardContainer;
    private SimpleCardStackAdapter adapter;
    private ProductsContract.UserActionsListener mActionsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        // Set up the toolbar.
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);
//        ab.setDisplayShowHomeEnabled(true);
        mCardContainer = (CardContainer) findViewById(R.id.layoutview);
        // Get the requested note id
        String categoryId = getIntent().getStringExtra(CAT_ID);
        adapter = new SimpleCardStackAdapter(this);
        mActionsListener = new ProductsPresenter(Injection.provideProductsRepository(), this);

        mActionsListener.loadProducts(categoryId, false);

    }

    @Override
    public void showProducts(List<Product> products) {

        Resources r = getResources();


//        ArrayList arra=new ArrayList();
//        arra.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));

        for (Product product : products) {
            // adapter.add(product.getId(), product.getDescription(),product.getName(),product.getUrl(),product.getBrand(),product.getPrice());
            //  addProduct(product.getId(), product.getmDescription(),product.getName(),product.getUrl(),product.getBrand(),product.getPrice());

            CardModel card = new CardModel(product.getId(), product.getDescription(), product.image.sizes.IPhoneSmall.url);
            card.setOnCardDismissedListener(onCardDismissedListener);

            adapter.add(card);
        }


        //   adapter.add(cardModel);


        mCardContainer.setAdapter(adapter);
    }

    CardModel.OnClickListener onCardClickListener = new CardModel.OnClickListener() {
        @Override
        public void OnClickListener() {
            Log.i("Swipeable Cards", "I am pressing the card" + adapter.getCount());

        }
    };

    CardModel.OnCardDismissedListener onCardDismissedListener = new CardModel.OnCardDismissedListener() {
        @Override
        public void onLike() {
            if (mCardContainer.isEmpty()) {
                String categoryId = getIntent().getStringExtra(CAT_ID);
                mActionsListener.loadProducts(categoryId, false);
            }
            Log.i("Swipe Card", "Right" + mCardContainer.isEmpty());
        }

        @Override
        public void onDislike() {
            Log.i("Swipe Card ", "Left");
        }
    };

    @Override
    public void setProgressIndicator(final boolean active) {

//        if (getView() == null) {
//            return;
//        }
//        final SwipeRefreshLayout srl =
//                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);
//
//        // Make sure setRefreshing() is called after the layout is done with everything else.
//        srl.post(new Runnable() {
//            @Override
//            public void run() {
//                srl.setRefreshing(active);
//            }
//        });
    }
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

//    private void initFragment(Fragment detailFragment) {
//        // Add the NotesDetailFragment to the layout
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.add(R.id.contentFrame, detailFragment);
//        transaction.commit();
//    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }


}
