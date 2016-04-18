package com.example.carolinamarin.stylestumble.productdetail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.carolinamarin.stylestumble.data.ProductDetail;
import com.example.carolinamarin.stylestumble.data.ProductsRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by carolinamarin on 4/11/16.
 */
public class ProductDetailPresenter implements ProductDetailContract.UserActionsListener {

    private ProductsRepository mProductssRepository;
    private ProductDetailContract.View mProductsDetailView;

    public ProductDetailPresenter(@NonNull ProductsRepository productsRepository,   @NonNull ProductDetailContract.View productsView ){

        mProductssRepository=checkNotNull(productsRepository,"products cannot be null");
        mProductsDetailView=checkNotNull(productsView,"productsview cannot be null");
    }

    private void showProduct(ProductDetail product) {
        String title = product.getName();
        String description = product.getDescription();
        String imageUrl = product.getImage().sizes.IPhone.url;



        if (title != null && title.isEmpty()) {
            mProductsDetailView.hideTitle();
        } else {
            mProductsDetailView.showTitle(title,imageUrl);
        }

//        if (description != null && description.isEmpty()) {
//            mNotesDetailView.hideDescription();
//        } else {
//            mNotesDetailView.showDescription(description);
//        }
//
//        if (imageUrl != null) {
//            mNotesDetailView.showImage(imageUrl);
//        } else {
//            mNotesDetailView.hideImage();
//        }

    }


    @Override
    public void openProduct(@Nullable String productId) {
        mProductsDetailView.setProgressIndicator(true);
        mProductssRepository.getProduct(productId, new ProductsRepository.GetProductCallback() {
            @Override
            public void onProductLoaded(ProductDetail product) {
                mProductsDetailView.setProgressIndicator(false);
                    showProduct(product);

            }
        });
    }
}
