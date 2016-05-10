package com.example.carolinamarin.stylestumble.addsaleProducts;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.carolinamarin.stylestumble.Injection;
import com.example.carolinamarin.stylestumble.R;
import com.example.carolinamarin.stylestumble.data.CategoriesServiceApiImpl;
import com.example.carolinamarin.stylestumble.data.Category;
import com.example.carolinamarin.stylestumble.data.ProductDetail;
import com.example.carolinamarin.stylestumble.data.ProductsRepository;
import com.example.carolinamarin.stylestumble.data.ProductsServiceApiImpl;
import com.example.carolinamarin.stylestumble.data.provider.PreferenceColumns;
import com.example.carolinamarin.stylestumble.data.provider.ProductColumns;
import com.example.carolinamarin.stylestumble.data.provider.ProductProvider;
import com.example.carolinamarin.stylestumble.productdetail.ProductDetailActivity;
import com.example.carolinamarin.stylestumble.util.CursorRecyclerViewAdapter;
import com.example.carolinamarin.stylestumble.util.ItemTouchHelperAdapter;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductSaleFragment extends Fragment implements ProductSaleContract.View, LoaderManager.LoaderCallbacks<Cursor> {


    private static final int CURSOR_LOADER_ID = 0;
    private static  ProductSaleAdapter mListAdapter;
    private static ProductSaleContract.UserActionsListener mActionsListener;
    public ProductSaleFragment() {

    }


    public static ProductSaleFragment newInstance() {
        ProductSaleFragment fragment = new ProductSaleFragment();

        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        mActionsListener = new ProductSalePresenter((ProductsRepository) new ProductsServiceApiImpl(), this);

        Cursor c = getActivity().getContentResolver().query(ProductProvider.WishList.PRODUCTSALE,
        null, null, null, null);
        Log.i("count", "cursor count: " + c.getCount());
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);

    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ProductProvider.WishList.PRODUCTSALE,
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showDetailProduct(String productId) {
        Intent intent = new Intent(getContext(), ProductDetailActivity.class);
        intent.putExtra(ProductDetailActivity.PRODUCT_ID, productId);
        startActivity(intent);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mListAdapter.swapCursor(null);
    }

@Override
public void showNotification(ProductDetail p){

}

    @Override
    public void onResume() {
        super.onResume();
//        String catId = getArguments().getString(ARGUMENT_CAT_ID);
//        mActionsListener.loadProducts(catId, "", 0, false);
        getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root=  inflater.inflate(R.layout.fragment_product_sale, container, false);



        final CheckBox checkboxvariable=(CheckBox)root.findViewById(R.id.notification_show);


        Button save=(Button)root.findViewById(R.id.save_notification);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.products_sale_list);
        mListAdapter = new ProductSaleAdapter(getActivity(), null,mItemListener);

        Cursor c = getContext().getContentResolver().query(ProductProvider.UserPreferences.USERPREFERENCES,
                null, null, null, null);
        if(c.getCount()==0) {
            ContentValues cv = new ContentValues();
            cv.put(PreferenceColumns.SHOWNOTIFICATION, 0);
            getContext().getContentResolver().insert(ProductProvider.UserPreferences.USERPREFERENCES,
                    cv);
            Log.d("countprefrences", "conta" + c.getCount());

        }

        for(int i=0;i<c.getCount();i++){
            c.moveToFirst();
            int status=c.getInt(c.getColumnIndex("showNotification"));
            if(status==0){
                checkboxvariable.setChecked(false);
            }else{
                checkboxvariable.setChecked(true);
            }
            c.moveToNext();
        }

        DatabaseUtils.dumpCursor(c);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                int value=0;
                if(checkboxvariable.isChecked()){
                    value = 1;
                    cv.put(PreferenceColumns.SHOWNOTIFICATION, value);
                    Log.d("message", "checked");

                }else{
                    value = 0;
                    cv.put(PreferenceColumns.SHOWNOTIFICATION, value);
                    Log.d("message", "NOT checked");

                }
                Log.d("the value","val"+value);
                Toast.makeText("Settings saved");
                try {
                    getContext().getContentResolver().update(ProductProvider.UserPreferences.USERPREFERENCES, cv, null, null);
                }catch (Exception e){
                    Log.d("message", e.getMessage());
                }
            }
        });




        recyclerView.setAdapter(mListAdapter);
       // recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        int numColumns = 2;
        // recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));
       return root;
    }
    ProductItemListener mItemListener = new ProductItemListener() {
        @Override
        public void onProductClick(String productId) {
            mActionsListener.openProductDetails(productId);
        }
    };



    private static class ProductSaleAdapter extends CursorRecyclerViewAdapter<ProductSaleAdapter.ViewHolder> implements ItemTouchHelperAdapter {

        Context mContext;
        ViewHolder mVh;
        private ProductItemListener mItemListener;
        private List<Category> mCategories;

        public ProductSaleAdapter(Context context, Cursor cursor,  ProductItemListener itemListener) {
            super(context, cursor);
            mContext = context;
            mItemListener = itemListener;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View wishListView = inflater.inflate(R.layout.item_product_sale, parent, false);

            ViewHolder vh = new ViewHolder(wishListView,mItemListener);
            mVh = vh;
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
            //   DatabaseUtils.dumpCursor(cursor);

            viewHolder.price.setText("");
            viewHolder.salePrice.setText("");
            viewHolder.name.setText(cursor.getString(
                    cursor.getColumnIndex(ProductColumns.NAME)));

            viewHolder.price.setText("$" + cursor.getString(
                    cursor.getColumnIndex(ProductColumns.PRICE)));


            if(!cursor.getString(
                    cursor.getColumnIndex(ProductColumns.SALEPRICE)).equals("0")){

                viewHolder.price.setText("Reg $"+cursor.getString(
                        cursor.getColumnIndex(ProductColumns.PRICE)));
                viewHolder.salePrice.setVisibility(View.VISIBLE);

                viewHolder.salePrice.setText("Sale $"+cursor.getString(
                        cursor.getColumnIndex(ProductColumns.SALEPRICE)));
            }

            //   viewHolder.description.setText(cursor.getColumnIndex(ProductColumns.DESCRIPTION));

//Log.d("URL",cursor.getString(
//        cursor.getColumnIndex(ProductColumns.URL)));
            Glide.with(viewHolder.itemView.getContext()).load(cursor.getString(
                    cursor.getColumnIndex(ProductColumns.URL)))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.image);

        }

        public void showProductDetails(int pos){

            getCursor().moveToPosition(pos);
            int currentPosition = getCursor().getPosition();
            Cursor c = getCursor();
            c.moveToPosition(currentPosition);

            String id = c.getString(c.getColumnIndex(ProductColumns._ID));

            mItemListener.onProductClick(id);
        }

        @Override
        public void onItemDismiss(int position, int dir) {

            long cursorId = getItemId(position);
            Cursor c = getCursor();
            ContentValues cv = new ContentValues();
//            cv.put(ArchivedPlanetColumns.NAME,
//                    c.getString(c.getColumnIndex(PlanetColumns.NAME)));
//            cv.put(ArchivedPlanetColumns.DIST_FROM_SUN,
//                    c.getDouble(c.getColumnIndex(PlanetColumns.DIST_FROM_SUN)));
//            cv.put(ArchivedPlanetColumns.IMAGE_RESOURCE,
//                    c.getInt(c.getColumnIndex(PlanetColumns.IMAGE_RESOURCE)));


            mContext.getContentResolver().delete(ProductProvider.WishList.withId(cursorId),
                    null, null);

            notifyItemRemoved(position);

//            if(c.getCount()==1){
//                String catId =cat_id;
//                offset++;
//                int totalPages = offset * 50;
//                mActionsListener.loadProducts(catId, searchQuery, totalPages, true);
//            }

        }
//        @Override
//        public void onBindViewHolder(ViewHolder viewHolder, int position) {
//
//
//            viewHolder.title.setText(category.getDescription());
//            viewHolder.description.setText(category.getDescription());
//        }


        private void setList(List<Category> categories) {
            mCategories = checkNotNull(categories);
        }

//        @Override
//        public int getItemCount() {
//         //   return mCategories.size();
//        }


        public class ViewHolder extends RecyclerView.ViewHolder  {

            public TextView title;

            public TextView description;
         //   private ProductItemListener mItemListener;
            private ImageView image;
            private TextView name;
            private TextView price;
            private TextView salePrice;


            public ViewHolder(View itemView,ProductItemListener listener) {
                super(itemView);

                mItemListener = listener;




                name = (TextView) itemView.findViewById(R.id.product_sale_name);

                //      description = (TextView) itemView.findViewById(R.id.product_detail_description);

                image = (ImageView) itemView.findViewById(R.id.product_image);
                price=(TextView)itemView.findViewById(R.id.product_regular_price);
                salePrice=(TextView)itemView.findViewById(R.id.product_sale_price);




                image.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        int pos=getAdapterPosition();
                        showProductDetails(pos);

                    }
                });

                name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        int pos=getAdapterPosition();
                        showProductDetails(pos);

                    }
                });



            }


        }
    }
    public interface ProductItemListener {

        void onProductClick(String clickedNote);
    }
}
