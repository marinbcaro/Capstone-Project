package com.example.carolinamarin.stylestumble.addProductWishList;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.carolinamarin.stylestumble.Injection;
import com.example.carolinamarin.stylestumble.R;
import com.example.carolinamarin.stylestumble.data.Category;
import com.example.carolinamarin.stylestumble.data.provider.ProductColumns;
import com.example.carolinamarin.stylestumble.data.provider.ProductProvider;
import com.example.carolinamarin.stylestumble.productdetail.ProductDetailActivity;
import com.example.carolinamarin.stylestumble.util.CursorRecyclerViewAdapter;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A placeholder fragment containing a simple view.
 */
public class WishListFragment extends Fragment implements WishListContract.View, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CURSOR_LOADER_ID = 0;
    public static  WishListAdapter mListAdapter;
    private static WishListContract.UserActionsListener mActionsListener;
   // private static ProductsContract.UserActionsListener mActionsListener;

    public WishListFragment() {
    }

    public static WishListFragment newInstance() {
        WishListFragment fragment = new WishListFragment();
        return fragment;
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
        mActionsListener = new WishListPresenter(Injection.provideProductsRepository(), this);
        Cursor c = getActivity().getContentResolver().query(ProductProvider.WishList.WISHLIST,
                null, null, null, null);
        Log.i("count", "cursor count: " + c.getCount());
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ProductProvider.WishList.WISHLIST,
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
    public void showProducts() {


    }

    @Override
    public void showDetailProduct(String productId) {
        Intent intent = new Intent(getContext(), ProductDetailActivity.class);
        intent.putExtra(ProductDetailActivity.PRODUCT_ID, productId);
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_wish_list, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.products_wish_list);


        mListAdapter = new WishListAdapter(getActivity(), null,mItemListener);
        recyclerView.setAdapter(mListAdapter);
        //  recyclerView.setAdapter(mListAdapter);

        int numColumns = 2;
        // recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));
      //  recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;

    }

    ProductItemListener mItemListener = new ProductItemListener() {
        @Override
        public void onProductClick(String productId) {
            mActionsListener.openProductDetails(productId);
        }
    };
    // private static class WishListAdapter extends CursorRecyclerViewAdapter<WishListAdapter.ViewHolder>


    private static class WishListAdapter extends CursorRecyclerViewAdapter<WishListAdapter.ViewHolder> {

        Context mContext;
        ViewHolder mVh;
        private ProductItemListener mItemListener;
        private List<Category> mCategories;

        public WishListAdapter(Context context, Cursor cursor,ProductItemListener itemListener) {
            super(context, cursor);
            mContext = context;
            mItemListener = itemListener;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View wishListView = inflater.inflate(R.layout.item_wishlist, parent, false);

            ViewHolder vh = new ViewHolder(wishListView,mItemListener);
            mVh = vh;
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
            //   DatabaseUtils.dumpCursor(cursor);

            viewHolder.name.setText(cursor.getString(
                    cursor.getColumnIndex(ProductColumns.NAME)));

            //   viewHolder.description.setText(cursor.getColumnIndex(ProductColumns.DESCRIPTION));

//Log.d("URL",cursor.getString(
//        cursor.getColumnIndex(ProductColumns.URL)));
            Glide.with(viewHolder.itemView.getContext()).load(cursor.getString(
                    cursor.getColumnIndex(ProductColumns.URL)))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.image);

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

            public TextView name;

            public TextView description;
            public TextView price;
            private ProductItemListener mItemListener;
            private ImageView image;
            private Button button;
            private ImageButton buttonDelete;

            public ViewHolder(View view, ProductItemListener listener) {
                super(view);
                mItemListener = listener;
                name = (TextView) view.findViewById(R.id.product_name);

                //      description = (TextView) itemView.findViewById(R.id.product_detail_description);

                image = (ImageView) view.findViewById(R.id.product_image);
                price=(TextView)view.findViewById(R.id.product_price);
//                button=(Button)view.findViewById(R.id.view_item_wishlist);
//                button.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        Log.d("the view","id"+v.getId());
//                        int pos=getAdapterPosition();
//                        getCursor().moveToPosition(pos);
//                        int currentPosition = getCursor().getPosition();
//                        Cursor c = getCursor();
//                        c.moveToPosition(currentPosition);
//                        String id = c.getString(c.getColumnIndex(ProductColumns._ID));
//                        mItemListener.onProductClick(id);
//                    }
//                });

                buttonDelete=(ImageButton)view.findViewById(R.id.delete_item_wishlist);
              //  buttonDelete.setOnClickListener(this);
                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Log.d("the delete", "id" + v.getId());
                        int pos=getAdapterPosition();
                        getCursor().moveToPosition(pos);
                        int currentPosition = getCursor().getPosition();
                        Cursor c = getCursor();
                        c.moveToPosition(currentPosition);
                        String id = c.getString(c.getColumnIndex(ProductColumns._ID));
                        long cursorId = mListAdapter.getItemId(pos);
                        mContext.getContentResolver().delete(ProductProvider.WishList.withId(cursorId),
                                null, null);

                        mListAdapter.notifyItemRemoved(currentPosition);
                      //  mItemListener.onProductClick(id);
                    }
                });
             //   itemView.setOnClickListener(this);
            }

//            @Override
//            public void onClick(View v) {
//
//            }
        }
    }
    public interface ProductItemListener {

         void onProductClick(String clickedNote);
    }


}
