package com.example.carolinamarin.stylestumble.productdetail;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.carolinamarin.stylestumble.Injection;
import com.example.carolinamarin.stylestumble.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductDetailFragment extends Fragment implements ProductDetailContract.View{


    public static final String PRODUCT_ID = "PRODUCT_ID";

    private ProductDetailContract.UserActionsListener mActionsListener;

    private TextView mDetailTitle;

    private TextView mDetailDescription;
    private TextView mDetailPrice;
    private TextView mDetailBrand;
    private TextView mDetailRetailer;

    private ImageView mDetailImage;


    public static ProductDetailFragment newInstance(String productId) {
        Bundle arguments = new Bundle();
        arguments.putString(PRODUCT_ID, productId);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }



    @Override
    public void onResume() {
        super.onResume();
        String noteId = getArguments().getString(PRODUCT_ID);
        mActionsListener.openProduct(noteId);
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//       // String productId = getActivity().getIntent().getStringExtra(PRODUCT_ID);
//        super.onSaveInstanceState(outState);
//
//        CharSequence title=mDetailTitle.getText();
//        outState.putString("title", "hola");
//
//    }

    @Override
    public void setProgressIndicator(boolean active) {
        if (active) {
           // mDetailTitle.setText("");
           // mDetailDescription.setText(getString(R.string.loading));
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);
   //     setRetainInstance(true);
        mActionsListener = new ProductDetailPresenter(Injection.provideProductsRepository(),
                this);


    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        //Save the fragment's state here
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//
//
//
//
//        mActionsListener = new ProductDetailPresenter(Injection.provideProductsRepository(),
//                this);
//
//        if (savedInstanceState == null) {
//      //   String   noteId = savedInstanceState.getString(PRODUCT_ID);
//            mActionsListener.openProduct("229365895");
//
//        }
//        //String noteId = getArguments().getString(PRODUCT_ID);
//
//        super.onCreate(savedInstanceState);
//        this.setRetainInstance(true);
//
//        //  }
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product_detail, container, false);


       mDetailTitle = (TextView) root.findViewById(R.id.note_detail_title);
        mDetailPrice = (TextView) root.findViewById(R.id.product_detail_price);
        mDetailDescription = (TextView) root.findViewById(R.id.note_detail_description);
       // mDetailBrand = (TextView) root.findViewById(R.id.product_detail_brand);
        mDetailRetailer = (TextView) root.findViewById(R.id.product_detail_retailer);

      //  mDetailImage = (ImageView) root.findViewById(R.id.note_detail_image);
        mDetailImage=  (ImageView) getActivity().findViewById(R.id.backdrop);



//        if(savedInstanceState!=null) {
//            mDetailPrice.setText("20");
//            mDetailTitle.setText(savedInstanceState.getString("title"));
//            mDetailDescription.setText(savedInstanceState.getString("title"));
//        }

        return root;
    }



    @Override
    public void showTitle(String title,String image) {

        Glide.with(getContext()).load(image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mDetailImage);


        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setCollapsedTitleTextColor(Color.rgb(0, 0, 0));




        collapsingToolbar.setTitle(title);
        collapsingToolbar.setExpandedTitleColor(00000000);
        collapsingToolbar.setCollapsedTitleTextColor(Color.rgb(0, 0, 0));

        mDetailTitle.setText(title);

    }

    @Override
    public void showDescription(String desc) {

        // regex matches every opening tag that contains 'mso-' in an attribute name
        // or value, the contents and the corresponding closing tag
        String regex = "<(\\S+)[^>]+?-[^>]*>.*?</\\1>";
        String replacement = "";
      String finaldesc= desc.replaceAll("<[^>]*>", "");

        mDetailDescription.setText(finaldesc);
    }

    @Override
    public void showPrice(Double price) {

        mDetailPrice.setText(price.toString());
    }

    @Override
    public void showBrand(String brand) {


        mDetailBrand.setText(brand);
    }
    @Override
    public void showRetailer(String retailer) {


        mDetailRetailer.setText(retailer);
    }
    @Override
    public void hideTitle() {
//        mDetailTitle.setVisibility(View.GONE);
    }

    @Override
    public void showShop(String urlProduct){

     final   String url=urlProduct;

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });


    }
}
