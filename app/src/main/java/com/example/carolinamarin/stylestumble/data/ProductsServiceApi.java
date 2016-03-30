package com.example.carolinamarin.stylestumble.data;

import java.util.List;

/**
 * Created by carolinamarin on 3/16/16.
 */
public interface ProductsServiceApi {
    interface ProductsServiceCallback<T> {

        void onLoaded(T products);
    }

    void getProductsCategories(String catId,String search,ProductsServiceCallback<List<Product>> callback);
}
