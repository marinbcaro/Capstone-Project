package com.example.carolinamarin.stylestumble.products;

import android.support.annotation.NonNull;


import com.example.carolinamarin.stylestumble.data.Product;
import com.example.carolinamarin.stylestumble.data.ProductsRepository;
import com.example.carolinamarin.stylestumble.util.EspressoIdlingResource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by carolinamarin on 2/24/16.
 */
public class ProductsPresenter implements  ProductsContract.UserActionsListener{

    private ProductsRepository mProductssRepository;
    private ProductsContract.View mProductsView;
    public ProductsPresenter(@NonNull ProductsRepository productsRepository,   @NonNull ProductsContract.View productsView ){

        mProductssRepository=checkNotNull(productsRepository,"products cannot be null");
        mProductsView=checkNotNull(productsView,"productsview cannot be null");
    }



    @Override
    public void loadProducts(String catId,String search,int offset,boolean forceUpdate) {

      //  mProductsView.setProgressIndicator(true);
        if (forceUpdate) {
            mProductssRepository.refreshData();
        }

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice

        mProductssRepository.getProducts(catId,search,offset,new ProductsRepository.LoadProductsCallback() {
            @Override
            public void onProductsLoaded(List<Product> categories) {
                EspressoIdlingResource.decrement(); // Set app as idle.
               // mProductsView.setProgressIndicator(false);
                mProductsView.showProducts(categories);
            }
        });
    }

//    @Override
//    public void showProducts(Product category){
//        // mCategoriesView.showAllProducts();
//        checkNotNull(category, "category cannot be null!");
//        mProductsView.showAllProducts(category.getId());
//
//    }


}
