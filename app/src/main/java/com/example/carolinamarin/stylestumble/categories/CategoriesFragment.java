package com.example.carolinamarin.stylestumble.categories;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.carolinamarin.stylestumble.Injection;
import com.example.carolinamarin.stylestumble.R;
import com.example.carolinamarin.stylestumble.data.Category;
import com.example.carolinamarin.stylestumble.products.ProductsActivity;
import com.example.carolinamarin.stylestumble.util.ThreeTwoImageView;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoriesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment implements CategoriesContract.View  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_LOAD_PRODUCTS = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;
    private CategoriesContract.UserActionsListener mCategoriesListener;
    private CategoriesAdapter mCategoryAdapter;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static CategoriesFragment newInstance(String param1, String param2) {
//        CategoriesFragment fragment = new CategoriesFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    public static CategoriesFragment newInstance() {
        return new CategoriesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryAdapter = new CategoriesAdapter(getActivity(),new ArrayList<Category>(0), mItemListener);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mCategoriesListener.loadCategories(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);

        mCategoriesListener = new CategoriesPresenter(Injection.provideCategoriesRepository(), this);
      //  mCategoriesListener.loadCategories(true);

    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_blank, container, false);
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categories, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.categories_list);

        recyclerView.setAdapter(mCategoryAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        int numColumns = getContext().getResources().getInteger(R.integer.num_categories_columns);
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));



        // Pull-to-refresh
//        SwipeRefreshLayout swipeRefreshLayout =
//                (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
//        swipeRefreshLayout.setColorSchemeColors(
//                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
//                ContextCompat.getColor(getActivity(), R.color.colorAccent),
//                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
             //   mCategoriesListener.loadCategories(true);
//            }
//        });
        return root;
    }


    /**
     * Listener for clicks on notes in the RecyclerView.
     */
    CategoryItemListener mItemListener = new CategoryItemListener() {
        @Override
        public void onCategoryClick(Category clickedCategory) {
            mCategoriesListener.showProducts(clickedCategory);
        }
    };


    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
    @Override
    public void showAllProducts(String catId){
        Intent intent = new Intent(getContext(), ProductsActivity.class);
        intent.putExtra(ProductsActivity.CAT_ID, catId);
        startActivity(intent);
    }

    @Override
    public void showCategories(List<Category> categories){

        ArrayList<Category> cateList=new ArrayList<>();
        Category women=new Category("womens-fashion","Women", "womens-fashion");
        Category men=new Category("mens-clothes","Men", "mens-clothes");
        Category kids=new Category("kids-and-baby","Kids", "kids-and-baby");

        cateList.add(women);
        cateList.add(men);
        cateList.add(kids);


        mCategoryAdapter.replaceData(cateList);

    }


    private static class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

        private List<Category> mCategories;
        private CategoryItemListener mItemListener;
        Context mContext;

        public CategoriesAdapter(Context context,List<Category> categories, CategoryItemListener itemListener) {
            setList(categories);
            mItemListener = itemListener;
            mContext=context;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View categoryView = inflater.inflate(R.layout.item_note, parent, false);

            return new ViewHolder(categoryView, mItemListener);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Category category = mCategories.get(position);


            viewHolder.title.setText(category.getDescription());
       //     viewHolder.description.setText(category.getDescription());

            if(category.name.equals("womens-fashion")){
                viewHolder.image.setImageResource(R.drawable.woman_category);
            }
            if(category.name.equals("mens-clothes")){
                viewHolder.image.setImageResource(R.drawable.man_category);

            }
            if(category.name.equals("kids-and-baby")){
                viewHolder.image.setImageResource(R.drawable.kids_category);
            }

        }

        public void replaceData(List<Category> categories) {
            setList(categories);
            notifyDataSetChanged();
        }

        private void setList(List<Category> categories) {
            mCategories = checkNotNull(categories);
        }

        @Override
        public int getItemCount() {
            return mCategories.size();
        }

        public Category getItem(int position) {
            return mCategories.get(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView title;

            public TextView description;
            private CategoryItemListener mItemListener;
            private ThreeTwoImageView image;

            public ViewHolder(View itemView, CategoryItemListener listener) {
                super(itemView);
                mItemListener = listener;
                title = (TextView) itemView.findViewById(R.id.category_detail_title);
                description = (TextView) itemView.findViewById(R.id.category_detail_description);
                image=(ThreeTwoImageView)itemView.findViewById(R.id.image_category);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Category category = getItem(position);
                mItemListener.onCategoryClick(category);

            }
        }
    }
    public interface CategoryItemListener {

        void onCategoryClick(Category clickedCategory);
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
