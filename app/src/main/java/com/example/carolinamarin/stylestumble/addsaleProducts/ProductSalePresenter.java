package com.example.carolinamarin.stylestumble.addsaleProducts;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.carolinamarin.stylestumble.data.ProductDetail;
import com.example.carolinamarin.stylestumble.data.ProductsRepository;
import com.example.carolinamarin.stylestumble.util.EspressoIdlingResource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by carolinamarin on 4/24/16.
 */
public class ProductSalePresenter implements ProductSaleContract.UserActionsListener {

    private ProductsRepository mProductssRepository;
    private ProductSaleContract.View mProductsDetailView;
    private  ProductDetail pro;

    public ProductSalePresenter(@NonNull ProductsRepository productsRepository,  @NonNull ProductSaleContract.View productsView  ){

        mProductssRepository=checkNotNull(productsRepository,"products cannot be null");
        mProductsDetailView=checkNotNull(productsView,"productsview cannot be null");

    }


    public void showProduct(ProductDetail product) {
        mProductsDetailView.showNotification(product);
    }

    @Override
    public void loadProduct(@Nullable String productId) {


        mProductssRepository.getProduct(productId, new ProductsRepository.GetProductCallback() {

            @Override
            public void onProductLoaded(ProductDetail product) {

                showProduct(product);

            }

        });

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
                mProductsDetailView.showDetailProduct(product);
            }
        });
    }

}
