package com.example.carolinamarin.stylestumble.data;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.example.carolinamarin.stylestumble.data.Product.Brand;
import com.example.carolinamarin.stylestumble.data.Product.Image;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by carolinamarin on 2/25/16.
 */
public class ProductsServiceApiImpl implements ProductsServiceApi {

    private static final int SERVICE_LATENCY_IN_MILLIS = 2000;
    private static final String BASE_URL = "/api/v2/products?pid=";
   
    private static final String API_URL = BASE_URL + API_KEY;
    private  ArrayMap<String, Product> DATA = new ArrayMap(2);

//    private static void addProduct(String id, String description, String name, String url, Brand brand, Double price, Image image) {
//        Product product = new Product(id, description, name, url, brand, price, image);
//
//        DATA.put(product.getId(), product);
//    }

    @Override
    public void getProductsCategories(String catId, String search,final ProductsServiceCallback callback) {
        getData(catId, search,callback);
    }


    public void getData(String catId, String search,final ProductsServiceCallback callback) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("MyTAG", "OkHttp: " + message);
            }
        });
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl("http://api.shopstyle.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        shopStyleService mService = retrofit.create(shopStyleService.class);

        Call<ListProducts> call = mService.listProducts(catId,search);
        call.enqueue(new Callback<ListProducts>() {
            @Override
            public void onResponse(Call<ListProducts> call, Response<ListProducts> response) {
                int statusCode = response.code();

                if (response.isSuccess()) {
                    DATA.clear();

                    List<Product> contributors = response.body().products;
                    for (Product contributor : contributors) {
                        Brand h = contributor.getBrand();
                        Image ima = contributor.getImage();
                      //  addProduct(contributor.getId(), contributor.getName(), contributor.getName(), contributor.getUrl(), h, contributor.getPrice(), ima);
                        Product product = new Product(contributor.getId(), contributor.getName(), contributor.getName(), contributor.getUrl(), h, contributor.getPrice(), ima);

                        DATA.put(product.getId(), product);
                    }
                    List<Product> products = new ArrayList<>(DATA.values());
                    callback.onLoaded(products);

                } else {
                    Log.d("TOTAL ERROR", response.message());
                }

            }

            @Override
            public void onFailure(Call<ListProducts> call, Throwable t) {
                // Log error here since request failed
                Log.d("Error", t.getMessage());
            }
        });

    }


    public interface shopStyleService {
        @GET(API_URL + "&sort=Popular")
        Call<ListProducts> listProducts(@Query("cat") String catId,@Query("fts") String search );
    }


}
