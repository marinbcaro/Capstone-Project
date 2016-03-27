package com.example.carolinamarin.stylestumble.data;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by carolinamarin on 2/25/16.
 */
public class CategoriesServiceApiEndpoint {


    private static final String BASE_URL = "https://api.forecast.io/forecast/";
    private static final String API_KEY = "8a338517fc5caff55f3b737c2ab27bd3";
    private static final String API_URL = BASE_URL + API_KEY;


    private static final String GITHUB_ENDPOINT = "https://api.github.com";

//    public interface GitHubService {
//        @GET("/api/v2/products?pid=uid9049-30800243-85")
//        Call<ListCategories> listRepos(@Query("sort") String sort);
//    }


    public interface GitHubService {
        @GET("/api/v2/categories?pid=uid9049-30800243-85")
        Call<ListCategories> listRepos();
    }


    static {

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
//                //  Log.d(response.body().toString(), "[DEBUG]" + " RestApi onResponse Number of repositories- ");
//
//                if (response.isSuccess()) {
//                    Log.d("ENTRA", response.toString());
//                    List<Category> contributors = response.body().products;
//                    for (Category contributor : contributors) {
//                        // addCategory("1",contributor.name,contributor.name);
//                        addCategory("1", contributor.name, "11");
//                        addCategory("2", contributor.name, "11");
//                    }
//
//                } else {
//                    Log.d("TOTAL ERROR", response.message());
//                }
//                //  User user = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<ListCategories> call, Throwable t) {
//                // Log error here since request failed
//                Log.d("Error", t.getMessage());
//            }
//        });
//
//
//        DATA = new ArrayMap(2);
        //  addCategory("1", "I demand trial by Unit testing","11");
        // addCategory("2", "UI Testing for Android","11");
    }

  //  private final static ArrayMap<String, Category> DATA;

//    private static void addCategory(String id, String description, String name) {
//        Category newNote = new Category(id, description, name);
//        DATA.put(newNote.getId(), newNote);
//    }

    /**
     * @return the Notes to show when starting the app.
     */
   // public static ArrayMap<String, Category> loadPersistedCategories() {
      //  return DATA;
    //}
}
