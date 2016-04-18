package com.example.carolinamarin.stylestumble.productdetail;

import android.support.annotation.Nullable;

/**
 * Created by carolinamarin on 4/11/16.
 */
public interface ProductDetailContract {

    interface View {

        void setProgressIndicator(boolean active);
//
//        void showMissingNote();
//
        void hideTitle();

        void showTitle(String title,String image);

//        void showImage(String imageUrl);
//
//        void hideImage();
//
//        void hideDescription();
//
        void showDescription(String description);
    }

    interface UserActionsListener {

        void openProduct(@Nullable String productId);
    }
}
