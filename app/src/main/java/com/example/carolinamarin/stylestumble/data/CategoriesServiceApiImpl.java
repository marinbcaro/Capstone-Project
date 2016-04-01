package com.example.carolinamarin.stylestumble.data;

import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by carolinamarin on 2/25/16.
 */
public class CategoriesServiceApiImpl  implements CategoriesServiceApi{

    private static final int SERVICE_LATENCY_IN_MILLIS = 2000;
    private static final String BASE_URL = "/api/v2/categories?pid=";
   
    private static final String API_URL = BASE_URL + API_KEY;



    private final static ArrayMap<String, Category> DATA=new ArrayMap(2);


    public interface shopStyleService {
        @GET(API_URL)
        Call<ListCategories> listRepos();
    }


    @Override
    public void getAllCategories(final CategoriesServiceCallback callback) {
        getData(callback);
    }


   public void getData(final CategoriesServiceCallback callback){
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//                Log.d("MyTAG", "OkHttp: " + message);
//            }
//        });
//        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .client(httpClient)
//                .baseUrl("http://api.shopstyle.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//
//        GitHubService mService = retrofit.create(GitHubService.class);
//
//        Call<ListCategories> call = mService.listRepos();
//        call.enqueue(new Callback<ListCategories>() {
//            @Override
//            public void onResponse(Call<ListCategories> call, Response<ListCategories> response) {
//                int statusCode = response.code();
//
//                if (response.isSuccess()) {
//                    Log.d("ENTRA", response.toString());
//                    List<Category> contributors = response.body().categories;
//                    for (Category contributor : contributors) {
//                        // addCategory("1",contributor.name,contributor.name);
//                        addCategory("1", contributor.name, "11");
//                        addCategory("2", contributor.name, "11");
//                    }
//                    List<Category> categories = new ArrayList<>(DATA.values());
//                    callback.onLoaded(categories);
//
//                } else {
//                    Log.d("TOTAL ERROR", response.message());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ListCategories> call, Throwable t) {
//                // Log error here since request failed
//                Log.d("Error", t.getMessage());
//            }
//        });

         addCategory("1", "womens-clothes","womens-clothes");
        addCategory("2", "mens-clothes","mens-clothes");
       List<Category> categories = new ArrayList<>(DATA.values());
                    callback.onLoaded(categories);
    }


    private static void addCategory(String id, String description, String name) {
        Category newCategory = new Category(id, description, name);
        DATA.put(newCategory.getId(), newCategory);
    }



}
