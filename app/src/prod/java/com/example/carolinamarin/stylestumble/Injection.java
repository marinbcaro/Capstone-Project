package com.example.carolinamarin.stylestumble;

import com.example.carolinamarin.stylestumble.data.CategoriesRepository;
import com.example.carolinamarin.stylestumble.data.CategoriesServiceApiImpl;
import com.example.carolinamarin.stylestumble.data.CategoryRepositories;
import com.example.carolinamarin.stylestumble.data.ProductRepositories;
import com.example.carolinamarin.stylestumble.data.ProductsRepository;
import com.example.carolinamarin.stylestumble.data.ProductsServiceApiImpl;

/**
 * Created by carolinamarin on 2/25/16.
 */
public class Injection {

    public static CategoriesRepository provideCategoriesRepository() {
        return CategoryRepositories.getInMemoryRepoInstance(new CategoriesServiceApiImpl());
    }
    public static ProductsRepository provideProductsRepository() {
        return ProductRepositories.getInMemoryRepoInstance(new ProductsServiceApiImpl());
    }
}
