package com.example.carolinamarin.stylestumble.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by carolinamarin on 2/23/16.
 */
public interface CategoriesRepository {

    interface LoadCategoriesCallback{

        void onCategoriesLoaded(List<Category> categories);

    }
    void getCategories(@NonNull LoadCategoriesCallback callback);
    void refreshData();
}
