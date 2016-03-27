package com.example.carolinamarin.stylestumble.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by carolinamarin on 2/25/16.
 */


public class InMemoryCategoriesRepository implements CategoriesRepository {

    private final CategoriesServiceApi mCategoriesServiceApi;


    @VisibleForTesting
    List<Category> mCachedCategories;

    public InMemoryCategoriesRepository(@NonNull CategoriesServiceApi categoriesServiceApi) {
        mCategoriesServiceApi = checkNotNull(categoriesServiceApi);
    }

    @Override
    public void getCategories(@NonNull final LoadCategoriesCallback callback) {
        checkNotNull(callback);
        // Load from API only if needed.
        if (mCachedCategories == null) {
            mCategoriesServiceApi.getAllCategories(new CategoriesServiceApi.CategoriesServiceCallback<List<Category>>() {
                @Override
                public void onLoaded(List<Category> categories) {
                    mCachedCategories = ImmutableList.copyOf(categories);
                    callback.onCategoriesLoaded(mCachedCategories);
                }
            });
        } else {
            callback.onCategoriesLoaded(mCachedCategories);
        }
    }



    @Override
    public void refreshData() {
        mCachedCategories = null;
    }

}