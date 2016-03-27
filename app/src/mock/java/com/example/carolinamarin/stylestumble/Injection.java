package com.example.carolinamarin.stylestumble;

import com.example.carolinamarin.stylestumble.data.CategoriesRepository;
import com.example.carolinamarin.stylestumble.data.CategoryRepositories;
import com.example.carolinamarin.stylestumble.data.FakeCategoriesServiceApiImpl;

/**
 * Created by carolinamarin on 3/5/16.
 */
public class Injection {



    public static CategoriesRepository provideCategoriesRepository() {
        return CategoryRepositories.getInMemoryRepoInstance(new FakeCategoriesServiceApiImpl());
    }
}
