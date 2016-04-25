package com.example.carolinamarin.stylestumble.addsaleProducts;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

/**
 * Created by carolinamarin on 4/24/16.
 */
public class ProductSaleTaskService extends GcmTaskService {

    private Context mContext;
    public static final String ACTION_DONE = "GcmTaskService#ACTION_DONE";
    public static final String EXTRA_TAG = "extra_tag";
    public static final String EXTRA_RESULT = "extra_result";
    public ProductSaleTaskService(Context context){
        mContext=context;
    }

    public ProductSaleTaskService(){

    }

    @Override
    public int onRunTask(TaskParams taskParams) {


// Create Intent to broadcast the task information.
        Intent intent = new Intent();
        intent.setAction(ACTION_DONE);
        intent.putExtra(EXTRA_TAG, "products");
        intent.putExtra(EXTRA_RESULT, 1);

        // Send local broadcast, running Activities will be notified about the task.
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.sendBroadcast(intent);
        return GcmNetworkManager.RESULT_SUCCESS;
    }
}
