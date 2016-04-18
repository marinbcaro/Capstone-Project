package com.example.carolinamarin.stylestumble.data;

import java.util.List;

/**
 * Created by carolinamarin on 3/16/16.
 */
public interface ProductsServiceApi {
    interface ProductsServiceCallback<T> {

        void onLoaded(T products);
    }

    void getProductsCategories(String catId,String search,int offset,ProductsServiceCallback<List<Product>> callback);

    interface GetProductServiceCallback<T> {

        void onProductLoaded(T product);
    }

    void getProduct(String productId,GetProductServiceCallback<ProductDetail> callback);
}
