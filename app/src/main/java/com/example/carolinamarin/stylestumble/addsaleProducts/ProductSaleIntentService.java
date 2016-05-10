package com.example.carolinamarin.stylestumble.addsaleProducts;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.TaskParams;

/**
 * Created by carolinamarin on 4/24/16.
 */
public class ProductSaleIntentService extends IntentService {

    private Context mContext;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ProductSaleIntentService(String name) {
        super(name);
    }

    public ProductSaleIntentService() {
        super(ProductSaleIntentService.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(ProductSaleIntentService.class.getSimpleName(), "Product Intent Service");
        ProductSaleTaskService stockTaskService = new ProductSaleTaskService(this);
        Bundle args = new Bundle();
      //  if (intent.getStringExtra("tag").equals("add")){
            args.putString("product", "product");
      //  }
        // We can call OnRunTask from the intent service to force it to run immediately instead of
        // scheduling a task.
        stockTaskService.onRunTask(new TaskParams("product", args));


    }



}
