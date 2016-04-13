package com.example.carolinamarin.stylestumble.products;

import android.support.annotation.NonNull;

import com.example.carolinamarin.stylestumble.data.Product;

import java.util.List;

/**
 * Created by carolinamarin on 2/24/16.
 */
public interface ProductsContract {


    interface View {
        void setProgressIndicator(boolean active);
        void showProducts(List<Product> categories);
        void showDetailProduct(String id);
       // void showAllProducts(String catId);
    }

    interface UserActionsListener {

        void loadProducts(String catId,String search,int offset,boolean forceUpdate);
        void openProductDetails(@NonNull Product requestedProduct);
       // void showProducts(@NonNull Product requestedCategory );

    }


   // interface View {

//        void setProgressIndicator(boolean active);
//
//        void showMissingNote();
//
//        void hideTitle();
//
//        void showTitle(String title);
//
//        void showImage(String imageUrl);
//
//        void hideImage();
//
//        void hideDescription();
//
//        void showDescription(String description);
    //}

    //interface UserActionsListener {

     //   void openNote(@Nullable String noteId);
    //}


}
