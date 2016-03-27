package com.example.carolinamarin.stylestumble.categories;

import android.support.annotation.NonNull;

import com.example.carolinamarin.stylestumble.data.CategoriesRepository;
import com.example.carolinamarin.stylestumble.data.Category;
import com.example.carolinamarin.stylestumble.util.EspressoIdlingResource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by carolinamarin on 2/22/16.
 */
public class CategoriesPresenter implements CategoriesContract.UserActionsListener{

    private CategoriesRepository mCategoriesRepository;
    private CategoriesContract.View mCategoriesView;
    public CategoriesPresenter(@NonNull CategoriesRepository categoriesRepository,   @NonNull CategoriesContract.View categoriesView ){

        mCategoriesRepository=checkNotNull(categoriesRepository,"categories cannot be null");
        mCategoriesView=checkNotNull(categoriesView,"categoriesview cannot be null");
    }



    @Override
    public void loadCategories(boolean forceUpdate) {
    //    categoriesView.setProgressIndicator(true);
        if (forceUpdate) {
            mCategoriesRepository.refreshData();
        }

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice

        mCategoriesRepository.getCategories(new CategoriesRepository.LoadCategoriesCallback() {
            @Override
            public void onCategoriesLoaded(List<Category> categories) {
                EspressoIdlingResource.decrement(); // Set app as idle.

                mCategoriesView.showCategories(categories);
            }
        });
    }

    @Override
    public void showProducts(Category category){
       // mCategoriesView.showAllProducts();
        checkNotNull(category, "category cannot be null!");
        mCategoriesView.showAllProducts(category.name);

    }

}
