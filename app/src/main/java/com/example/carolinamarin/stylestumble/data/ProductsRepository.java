package com.example.carolinamarin.stylestumble.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by carolinamarin on 3/15/16.
 */
public interface ProductsRepository {
    interface LoadProductsCallback{

        void onProductsLoaded(List<Product> products);

    }
    void getProducts(@NonNull String catId,String search,int offset,@NonNull LoadProductsCallback callback);

    interface GetProductCallback{

        void onProductLoaded(ProductDetail product);

    }
    void getProduct(@NonNull String productId,@NonNull GetProductCallback callback);

    void refreshData();
}
