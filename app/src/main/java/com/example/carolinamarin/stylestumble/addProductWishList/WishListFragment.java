package com.example.carolinamarin.stylestumble.addProductWishList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.carolinamarin.stylestumble.R;
import com.example.carolinamarin.stylestumble.data.Category;
import com.example.carolinamarin.stylestumble.data.provider.ProductColumns;
import com.example.carolinamarin.stylestumble.data.provider.ProductProvider;
import com.example.carolinamarin.stylestumble.util.CursorRecyclerViewAdapter;
import com.example.carolinamarin.stylestumble.util.ItemTouchHelperAdapter;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A placeholder fragment containing a simple view.
 */
public class WishListFragment extends Fragment implements WishListContract.View, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CURSOR_LOADER_ID = 0;
    private WishListAdapter mListAdapter;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_wish_list, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.products_wish_list);

        mListAdapter = new WishListAdapter(getActivity(), null);
        recyclerView.setAdapter(mListAdapter);
        //  recyclerView.setAdapter(mListAdapter);

        int numColumns = 1;
        // recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;

    }


    // private static class WishListAdapter extends CursorRecyclerViewAdapter<WishListAdapter.ViewHolder>

    public interface ProductItemListener {

        // void onNoteClick(Note clickedNote);
    }

    private static class WishListAdapter extends CursorRecyclerViewAdapter<WishListAdapter.ViewHolder> implements ItemTouchHelperAdapter {

        Context mContext;
        ViewHolder mVh;
        private List<Category> mCategories;

        public WishListAdapter(Context context, Cursor cursor) {
            super(context, cursor);
            mContext = context;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View wishListView = inflater.inflate(R.layout.item_wishlist, parent, false);

            ViewHolder vh = new ViewHolder(wishListView);
            mVh = vh;
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
            //   DatabaseUtils.dumpCursor(cursor);
            viewHolder.title.setText(cursor.getString(
                    cursor.getColumnIndex(ProductColumns.NAME)));

            //   viewHolder.description.setText(cursor.getColumnIndex(ProductColumns.DESCRIPTION));

//Log.d("URL",cursor.getString(
//        cursor.getColumnIndex(ProductColumns.URL)));
            Glide.with(viewHolder.itemView.getContext()).load(cursor.getString(
                    cursor.getColumnIndex(ProductColumns.URL)))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.image);

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


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView title;

            public TextView description;
            private ProductItemListener mItemListener;
            private ImageView image;


            public ViewHolder(View itemView) {
                super(itemView);


                title = (TextView) itemView.findViewById(R.id.product_detail_title);
                //      description = (TextView) itemView.findViewById(R.id.product_detail_description);

                image = (ImageView) itemView.findViewById(R.id.product_image);
                //   itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
//                int position = getAdapterPosition();
//                Category category = getItem(position);
//                mItemListener.onCategoryClick(category);

            }
        }
    }


}
