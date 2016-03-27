package com.example.carolinamarin.stylestumble.products;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carolinamarin.stylestumble.Injection;
import com.example.carolinamarin.stylestumble.R;
import com.example.carolinamarin.stylestumble.data.Product;
import com.example.carolinamarin.swipecards.model.Orientations;
import com.example.carolinamarin.swipecards.view.CardContainer;
import com.example.carolinamarin.swipecards.view.SimpleCardStackAdapter;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductsFragment extends Fragment implements ProductsContract.View  {

    public static final String ARGUMENT_CAT_ID = "CATEGORY_ID";
    private CardContainer mCardContainer;

    private ProductsContract.UserActionsListener mActionsListener;

    private TextView mDetailTitle;

    private TextView mDetailDescription;

    private ImageView mDetailImage;
    private SimpleCardStackAdapter adapter;

    public static ProductsFragment newInstance(String categoryId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_CAT_ID, categoryId);
        ProductsFragment fragment = new ProductsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          adapter = new SimpleCardStackAdapter(getContext());
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public void setProgressIndicator(final boolean active) {

        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        String catId = getArguments().getString(ARGUMENT_CAT_ID);
        mActionsListener.loadProducts(catId,false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionsListener = new ProductsPresenter(Injection.provideProductsRepository(), this);


    }
    @Override
    public void showProducts(List<Product> products){
      //  mCategoryAdapter.replaceData(categories);

//
// Log.d("products####",categories.get(0).name);
//

        Resources r = getResources();


//        ArrayList arra=new ArrayList();
//        arra.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
//
//        for (Product product : products) {
//           // addProduct(product.getId(), product.getmDescription(),product.getName(),product.getUrl(),product.getBrand(),product.getPrice());
//            adapter.add(new CardModel(product.getId(), product.getDescription(), r.getDrawable(R.drawable.picture1)));
//        }


//        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));

//        CardModel cardModel = new CardModel("Title1", "Description goes here","123");
//        cardModel.setOnClickListener(new CardModel.OnClickListener() {
//            @Override
//            public void OnClickListener() {
//                Log.i("Swipeable Cards", "I am pressing the card");
//            }
//        });
//
//        cardModel.setOnCardDismissedListener(new CardModel.OnCardDismissedListener() {
//            @Override
//            public void onLike() {
//                Log.i("Swipeable Cards", "I like the card");
//
//            }
//
//            @Override
//            public void onDislike() {
//                Log.i("Swipeable Cards", "I dislike the card");
//            }
//        });

    //    adapter.add(cardModel);

    //    if(adapter!=null) {
            mCardContainer.setAdapter(adapter);
     //   }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_products, container, false);
//        mDetailTitle = (TextView) root.findViewById(R.id.note_detail_title);
//        mDetailDescription = (TextView) root.findViewById(R.id.note_detail_description);
//        mDetailImage = (ImageView) root.findViewById(R.id.note_detail_image);

        Resources r = getResources();


//        ArrayList arra=new ArrayList();
//        arra.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
//
//        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture2)));
//        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3)));
//        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
//        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));

        mCardContainer = (CardContainer) root.findViewById(R.id.layoutview);
        mCardContainer.setOrientation(Orientations.Orientation.Ordered);


//        CardModel cardModel = new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1));
//        cardModel.setOnClickListener(new CardModel.OnClickListener() {
//            @Override
//            public void OnClickListener() {
//                Log.i("Swipeable Cards", "I am pressing the card");
//            }
//        });
//
//        cardModel.setOnCardDismissedListener(new CardModel.OnCardDismissedListener() {
//            @Override
//            public void onLike() {
//                Log.i("Swipeable Cards", "I like the card");
//
//            }
//
//            @Override
//            public void onDislike() {
//                Log.i("Swipeable Cards", "I dislike the card");
//            }
//        });
//
//        adapter.add(cardModel);
//
//        if(adapter!=null) {
//            mCardContainer.setAdapter(adapter);
//        }
        return root;
    }
}
