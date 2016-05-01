package com.example.carolinamarin.stylestumble.products;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.carolinamarin.stylestumble.Injection;
import com.example.carolinamarin.stylestumble.R;
import com.example.carolinamarin.stylestumble.addsaleProducts.ProductSaleIntentService;
import com.example.carolinamarin.stylestumble.addsaleProducts.ProductSaleTaskService;
import com.example.carolinamarin.stylestumble.data.Product;
import com.example.carolinamarin.stylestumble.data.provider.ProductColumns;
import com.example.carolinamarin.stylestumble.data.provider.ProductProvider;
import com.example.carolinamarin.stylestumble.data.provider.WishListColumns;
import com.example.carolinamarin.stylestumble.productdetail.ProductDetailActivity;
import com.example.carolinamarin.stylestumble.util.CursorRecyclerViewAdapter;
import com.example.carolinamarin.stylestumble.util.ItemTouchHelperAdapter;
import com.example.carolinamarin.stylestumble.util.ItemTouchHelperViewHolder;
import com.example.carolinamarin.stylestumble.util.SimpleItemTouchHelperCallback;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.OneoffTask;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductsFragment extends Fragment implements ProductsContract.View, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ARGUMENT_CAT_ID = "CATEGORY_ID";
    private static final int CURSOR_LOADER_ID = 0;
    public      SwipeRefreshLayout swipeRefreshLayout;

    private static ProductsContract.UserActionsListener mActionsListener;

    private TextView mDetailTitle;

    private TextView mDetailDescription;

    private ImageView mDetailImage;
    private BroadcastReceiver mReceiver;

    private ItemTouchHelper mItemTouchHelper;
    private ProductsAdapter mListAdapter;
    private static String searchQuery;
    private String category;
    private static String cat_id;
    private Context mContext;
    private static int offset = 0;
    List<Product> mProducts;
    public static final String ARG_OBJECT = "object";

    public static ProductsFragment newInstance(String categoryId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_CAT_ID, categoryId);
        ProductsFragment fragment = new ProductsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //   mListAdapter = new ProductsAdapter(getActivity(),null,new ArrayList<Product>(0), mItemListener);
        setHasOptionsMenu(true);



        Intent intent = new Intent(getContext(), ProductsActivity.class);
        intent.putExtra("POSITION_KEY",1);
        int requestID = (int) System.currentTimeMillis(); //unique requestID to differentiate between various notification with same NotifId
        int flags = PendingIntent.FLAG_CANCEL_CURRENT; // cancel old intent and create new one
        PendingIntent pIntent = PendingIntent.getActivity(getContext(), requestID, intent, flags);



        final   Notification mBuilder =
                new NotificationCompat.Builder(getContext())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Sale Alert")
                        .setContentText("Some products in your wishlist are on Sale! grab them before they are gone!")
                        .setContentIntent(pIntent)
                        .build();







        if (savedInstanceState == null) {

            setUpGcmTask(getActivity());
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(ProductSaleTaskService.ACTION_DONE)) {
                        String tag = intent.getStringExtra(ProductSaleTaskService.EXTRA_TAG);
                     //   int result = intent.getIntExtra(ProductSaleTaskService.EXTRA_RESULT, -1);
                        String result = intent.getStringExtra(ProductSaleTaskService.EXTRA_RESULT);
                        if(result.equals("display")) {

                           // String msg = String.format("DONE: %s (%d)", tag, result);

                            NotificationManager mNotificationManager =
                                    (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                            // mId allows you to update the notification later on.
                            mNotificationManager.notify(1, mBuilder);
                        }
                    }
                }
            };
        }


    }

    @Override
    public void setProgressIndicator(final boolean active) {

        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
//        String catId = getArguments().getString(ARGUMENT_CAT_ID);
//        mActionsListener.loadProducts(catId, "", 0, false);
        getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {


        mActionsListener = new ProductsPresenter(Injection.provideProductsRepository(), this);

        Cursor c = getActivity().getContentResolver().query(ProductProvider.Products.PRODUCTS,
                null, null, null, null);

        Cursor c2 = getActivity().getContentResolver().query(ProductProvider.UserPreferences.USERPREFERENCES,
                null, null, null, null);
        String catId = getArguments().getString(ARGUMENT_CAT_ID);
        Log.i("count", "cursor count: " + c.getCount());
        if (c == null || c.getCount() == 0) {
            if (savedInstanceState == null) {
                cat_id = catId;
                mActionsListener.loadProducts(catId, "", 0, false);
                getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
            }
        } else {
            if (savedInstanceState == null) {
                getActivity().getContentResolver().delete(ProductProvider.Products.PRODUCTS,
                        null, null);
                mActionsListener.loadProducts(catId, searchQuery, 0, true);
                getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);

            }
        }


        IntentFilter filter = new IntentFilter();
        filter.addAction(ProductSaleTaskService.ACTION_DONE);

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getActivity());
        manager.registerReceiver(mReceiver, filter);


        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);

    }


    private void setUpGcmTask(Context mContext){

        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        Intent mServiceIntent = new Intent(getContext(), ProductSaleIntentService.class);

        if(isConnected){

            getActivity().startService(mServiceIntent);



            OneoffTask myTask = new OneoffTask.Builder()
                    .setService(ProductSaleTaskService.class)
                    .setExecutionWindow(0L, 60L)
                    .setTag("product")
                    .build();
            GcmNetworkManager.getInstance(getContext()).schedule(myTask);

            //NO DEBUBBING
//            ProductSaleTaskService stockTaskService = new ProductSaleTaskService(getActivity());
//            Bundle args = new Bundle();
//            args.putString("product", "product");
//            stockTaskService.onRunTask(new TaskParams("product", args));
            //NO DEBUBBING


//        //    long period = 3600L;
//            long period=5L;
//            long flex = 10L;
//            String periodicTag = "periodic";
//
//            // create a periodic task to pull stocks once every hour after the app has been opened. This
//            // is so Widget data stays up to date.
//            PeriodicTask periodicTask = new PeriodicTask.Builder()
//                    .setService(ProductSaleTaskService.class)
//                    .setPeriod(period)
//                    .setFlex(flex)
//                    .setTag(periodicTag)
//                    .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED)
//                    .setRequiresCharging(false)
//                    .build();
//            // Schedule task with tag "periodic." This ensure that only the stocks present in the DB
//            // are updated.
//            GcmNetworkManager.getInstance(this).schedule(periodicTask);
//            Bundle args =new Bundle();
//            args.putString("product","hola");
//
//            ProductSaleTaskService serv=new ProductSaleTaskService();
//
//            serv.onRunTask(new TaskParams())

        }
        else{
            networkToast();
        }



    }
    public void networkToast(){
        Toast.makeText(mContext, "not connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ProductProvider.Products.PRODUCTS,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mListAdapter.swapCursor(data);



    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mListAdapter.swapCursor(null);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_products, menu);

        final MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch the data remotely
                //  adapter = new SimpleCardStackAdapter(getApplicationContext());

                //    adapter = new SimpleCardStackAdapter(getApplicationContext());
                searchQuery = query;
                String catId = getArguments().getString(ARGUMENT_CAT_ID);
                // mActionsListener = new ProductsPresenter(Injection.provideProductsRepository(), getApplicationContext());
              //  if(getContext().getContentResolver()!=null) {
                    getContext().getContentResolver().delete(ProductProvider.Products.PRODUCTS,
                            null, null);
                //}
                mActionsListener.loadProducts(catId, query, offset, true);
                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                // Set activity title to search query
                //  BookListActivity.this.setTitle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


    }

    @Override
    public void showDetailProduct(String productId) {
        Intent intent = new Intent(getContext(), ProductDetailActivity.class);
        intent.putExtra(ProductDetailActivity.PRODUCT_ID, productId);
        startActivity(intent);
    }


    @Override
    public void showProducts(List<Product> products) {
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(products.size());

        for (Product product : products) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                    ProductProvider.Products.PRODUCTS);
            builder.withValue(ProductColumns._ID, product.getId());
            builder.withValue(ProductColumns.NAME, product.getName());
            builder.withValue(ProductColumns.DESCRIPTION, product.getDescription());
            if (product.getBrand() != null) {
                builder.withValue(ProductColumns.BRAND, product.getBrand().name);
            }
            builder.withValue(ProductColumns.SALEPRICE, product.getSalePrice());
            builder.withValue(ProductColumns.PRICE, product.getPrice());
            builder.withValue(ProductColumns.URL, product.getImage().sizes.IPhoneSmall.url);
            batchOperations.add(builder.build());
        }

        try {
            getActivity().getContentResolver().applyBatch(ProductProvider.AUTHORITY, batchOperations);
        } catch (RemoteException | OperationApplicationException e) {
            Log.e("ERROR Provider", "Error applying batch insert", e);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_products, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.products_list);

        mListAdapter = new ProductsAdapter(getActivity(), null, new ArrayList<Product>(0), mItemListener);
        recyclerView.setAdapter(mListAdapter);

        //  recyclerView.setAdapter(mListAdapter);


        int numColumns = 1;

        // recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


         swipeRefreshLayout =
                (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String catId = getArguments().getString(ARGUMENT_CAT_ID);
                offset++;
                int totalPages = offset * 50;
                mActionsListener.loadProducts(catId, searchQuery, totalPages, true);



            }
        });

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int totalItemCount = linearLayoutManager.getItemCount();
                int visibleItemCount = linearLayoutManager.getChildCount();
                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                boolean enable=false;

                if(totalItemCount>0){
                    // check if the first item of the list is visible
                    boolean firstItemVisible = visibleItemCount == 3;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = firstVisibleItem== 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                    swipeRefreshLayout.setEnabled(enable);

                }
            }
        });


                ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mListAdapter);

        mItemTouchHelper = new ItemTouchHelper(callback);

        mItemTouchHelper.attachToRecyclerView(recyclerView);

        return root;

    }

    ProductItemListener mItemListener = new ProductItemListener() {
        @Override
        public void onProductClick(String productId) {
            mActionsListener.openProductDetails(productId);
        }
    };

    // private static class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    private static class ProductsAdapter extends CursorRecyclerViewAdapter<ProductsAdapter.ViewHolder> implements ItemTouchHelperAdapter {
        private List<Product> mProducts;
        private ProductItemListener mItemListener;
        private Context mContext;
        ViewHolder mVh;

        public ProductsAdapter(Context context, Cursor cursor, List<Product> products, ProductItemListener itemListener) {
            super(context, cursor);
             setList(products);
            mContext = context;
            mItemListener = itemListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView = inflater.inflate(R.layout.item_product, parent, false);


            ViewHolder vh = new ViewHolder(noteView, mItemListener);
            mVh = vh;
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
            //   DatabaseUtils.dumpCursor(cursor);
            viewHolder.title.setText(cursor.getString(
                    cursor.getColumnIndex(ProductColumns.NAME)));

            Glide.with(viewHolder.itemView.getContext()).load(cursor.getString(
                    cursor.getColumnIndex(ProductColumns.URL)))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.image);


        }

        public void replaceData(List<Product> notes) {
            setList(notes);
            notifyDataSetChanged();
        }

        private void setList(List<Product> products) {
            mProducts = checkNotNull(products);
        }

//        @Override
//        public int getItemCount() {
//            return mProducts.size();
//        }

        public Product getItem(int position) {
            return mProducts.get(position);
        }


        public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {

            public TextView title;

            public TextView description;
            private ProductItemListener mItemListener;
            private ImageView image;

            public ViewHolder(View view, ProductItemListener listener) {
                super(view);
                mItemListener = listener;
                title = (TextView) view.findViewById(R.id.product_detail_title);
                //      description = (TextView) itemView.findViewById(R.id.product_detail_description);

                image = (ImageView) view.findViewById(R.id.product_image);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int pos=getAdapterPosition();
                getCursor().moveToPosition(pos);
                int currentPosition = getCursor().getPosition();
                Cursor c = getCursor();
                c.moveToPosition(currentPosition);

                String id = c.getString(c.getColumnIndex(ProductColumns._ID));

//                ProductProvider.Products.withId(_id);
           //     getActivity().getContentResolver().insert(

//                long cursorId = getItemId(pos);
//
//                Cursor c = getCursor();
//                ContentValues cv = new ContentValues();
//               // int position = getAdapterPosition();
//                Cursor cursor=getCursor();
//                int position=cursor.getPosition();
//          //      Product product = getItem(position);
                        mItemListener.onProductClick(id);

            }

            @Override
            public void onItemSelected() {

               //    itemView.setBackgroundColor(Color.LTGRAY);
            }

            @Override
            public void onItemClear() {
                //   itemView.setBackgroundColor(0);
            }
        }


        @Override
        public void onItemDismiss(int position, int orientation) {
            long cursorId = getItemId(position);
            Cursor c = getCursor();
            ContentValues cv = new ContentValues();


            if (orientation == 32) {

                cv.put(WishListColumns._ID,
                        c.getString(c.getColumnIndex(ProductColumns._ID)));
                cv.put(WishListColumns.NAME,
                        c.getString(c.getColumnIndex(ProductColumns.NAME)));

                cv.put(WishListColumns.DESCRIPTION,
                        c.getString(c.getColumnIndex(ProductColumns.DESCRIPTION)));

                cv.put(WishListColumns.BRAND,
                        c.getString(c.getColumnIndex(ProductColumns.BRAND)));

                cv.put(WishListColumns.PRICE,
                        c.getString(c.getColumnIndex(ProductColumns.PRICE)));
                cv.put(WishListColumns.SALEPRICE,
                        c.getString(c.getColumnIndex(ProductColumns.SALEPRICE)));

                cv.put(WishListColumns.URL,
                        c.getString(c.getColumnIndex(ProductColumns.URL)));

                mContext.getContentResolver().delete(ProductProvider.Products.withId(cursorId),
                        null, null);
                mContext.getContentResolver().insert(ProductProvider.WishList.withId(cursorId),
                        cv);

            } else {
                mContext.getContentResolver().delete(ProductProvider.Products.withId(cursorId),
                        null, null);
            }


            notifyItemRemoved(position);

            if (c.getCount() == 1) {
                String catId = cat_id;
                offset++;
                int totalPages = offset * 50;
                mActionsListener.loadProducts(catId, searchQuery, totalPages, true);
            }

        }
    }

    public interface ProductItemListener {

         void onProductClick(String idProduct);
    }


}
