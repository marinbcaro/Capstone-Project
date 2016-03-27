package com.example.carolinamarin.stylestumble.data;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by carolinamarin on 2/25/16.
 */
public class CategoryRepositories {
    private static CategoriesRepository repository = null;

    private CategoryRepositories() {

    }


    public synchronized static CategoriesRepository getInMemoryRepoInstance(@NonNull CategoriesServiceApi categoriesServiceApi) {
        checkNotNull(categoriesServiceApi);
        if (null == repository) {
            repository = new InMemoryCategoriesRepository(categoriesServiceApi);
        }
        return repository;
    }
}