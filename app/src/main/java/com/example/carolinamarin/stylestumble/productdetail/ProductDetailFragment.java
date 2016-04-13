package com.example.carolinamarin.stylestumble.productdetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    private ImageView mDetailImage;

    public ProductDetailFragment() {
    }

    public static ProductDetailFragment newInstance(String productId) {
        Bundle arguments = new Bundle();
        arguments.putString(PRODUCT_ID, productId);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public void setProgressIndicator(boolean active) {
        if (active) {
            mDetailTitle.setText("");
           // mDetailDescription.setText(getString(R.string.loading));
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionsListener = new ProductDetailPresenter(Injection.provideProductsRepository(),
                this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product_detail, container, false);
        mDetailTitle = (TextView) root.findViewById(R.id.note_detail_title);
    //    mDetailDescription = (TextView) root.findViewById(R.id.note_detail_description);
      //  mDetailImage = (ImageView) root.findViewById(R.id.note_detail_image);


        return inflater.inflate(R.layout.fragment_product_detail, container, false);
    }

    @Override
    public void showTitle(String title) {
        mDetailTitle.setVisibility(View.VISIBLE);
        mDetailTitle.setText(title);
    }

    @Override
    public void hideTitle() {
        mDetailTitle.setVisibility(View.GONE);
    }
}
