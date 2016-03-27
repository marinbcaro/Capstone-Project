package com.example.carolinamarin.stylestumble.data;

import java.util.List;

/**
 * Created by carolinamarin on 2/25/16.
 */
public interface CategoriesServiceApi {

    interface CategoriesServiceCallback<T> {

        void onLoaded(T categories);
    }

    void getAllCategories(CategoriesServiceCallback<List<Category>> callback);

}
