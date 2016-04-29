package com.example.carolinamarin.stylestumble.addsaleProducts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carolinamarin.stylestumble.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductSaleFragment extends Fragment {

    public ProductSaleFragment() {
    }


    public static ProductSaleFragment newInstance() {
        ProductSaleFragment fragment = new ProductSaleFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_sale, container, false);
    }
}
