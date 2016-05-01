package com.example.carolinamarin.stylestumble.addProductWishList;

import android.support.annotation.NonNull;

import com.example.carolinamarin.stylestumble.data.ProductDetail;
import com.example.carolinamarin.stylestumble.data.ProductsRepository;
import com.example.carolinamarin.stylestumble.util.EspressoIdlingResource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by carolinamarin on 4/4/16.
 */
public class WishListPresenter implements WishListContract.UserActionsListener {

    private ProductsRepository mProductssRepository;
    private WishListContract.View mProductsView;


    public WishListPresenter(@NonNull ProductsRepository productsRepository,   @NonNull WishListContract.View productsView ){

        mProductssRepository=checkNotNull(productsRepository,"products cannot be null");
        mProductsView=checkNotNull(productsView,"productsview cannot be null");
    }



    @Override
    public void openProductDetails(final String product) {

      //  mProductsView.setProgressIndicator(true);


        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice

        mProductssRepository.getProduct(product, new ProductsRepository.GetProductCallback() {
            @Override
            public void onProductLoaded(ProductDetail categories) {
                EspressoIdlingResource.decrement(); // Set app as idle.
              //  mProductsView2.setProgressIndicator(false);
                mProductsView.showDetailProduct(product);
            }
        });
    }



}

