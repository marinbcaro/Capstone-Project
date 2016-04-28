package com.example.carolinamarin.stylestumble.addsaleProducts;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.carolinamarin.stylestumble.Injection;
import com.example.carolinamarin.stylestumble.data.ProductDetail;
import com.example.carolinamarin.stylestumble.data.provider.ProductProvider;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import java.util.ArrayList;

/**
 * Created by carolinamarin on 4/24/16.
 */
public class ProductSaleTaskService extends GcmTaskService implements ProductSaleContract.View{

    private Context mContext;
    public static final String ACTION_DONE = "GcmTaskService#ACTION_DONE";
    public static final String EXTRA_TAG = "extra_tag";
    public static final String EXTRA_RESULT = "extra_result";
    private ProductSaleContract.UserActionsListener mActionsListener;
    private  boolean showMessage=false;
    private static ArrayList arra=new ArrayList();
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
        intent.putExtra(EXTRA_RESULT, "0");

        if (mContext == null){
            mContext = this;
        }
        Cursor c = mContext.getContentResolver().query(ProductProvider.WishList.WISHLIST,
                null, null, null, null);


        c.moveToFirst();
        mActionsListener = new ProductSalePresenter(Injection.provideProductsRepository(),
                this);

        for(int i=0;i<c.getCount();i++){
           String id= c.getString(c.getColumnIndex("_id"));
            String price= c.getString(c.getColumnIndex("price"));
            mActionsListener.loadProduct(id);



            Log.d("count", "cursor count: " + id);

            c.moveToNext();
        }

        if(arra.size()>0){
            intent.putExtra(EXTRA_RESULT, "display");
        }else{
            intent.putExtra(EXTRA_RESULT,"hide");
        }
//

            LocalBroadcastManager manager = LocalBroadcastManager.getInstance(mContext);
            manager.sendBroadcast(intent);



        // Send local broadcast, running Activities will be notified about the task.

        return GcmNetworkManager.RESULT_SUCCESS;
    }
    public void showNotification(ProductDetail product){




        if(product.getSalePrice()!=null){
            arra.add(product.getId());
            showMessage=true;
        }

        Log.d("count", "cursor count: " + product.getId()+"price:"+product.getSalePrice());


    }
}
