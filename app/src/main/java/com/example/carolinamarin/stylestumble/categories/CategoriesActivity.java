package com.example.carolinamarin.stylestumble.categories;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import com.example.carolinamarin.stylestumble.categories.Injection;

import com.example.carolinamarin.stylestumble.R;

public class CategoriesActivity extends AppCompatActivity {
    private CategoriesContract.UserActionsListener mCategoriesListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbarTop);



//
//        Toolbar toolbarBottom = (Toolbar) findViewById(R.id.toolbar_bottom);
//        toolbarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch(item.getItemId()){
//                    case R.id.action_settings:
//                        // TODO
//                        break;
//                    // TODO: Other cases
//                }
//                return true;
//            }
//        });
//        // Inflate a menu to be displayed in the toolbar
//
//        toolbarBottom.inflateMenu(R.menu.menu_main);


      //  mCategoriesListener = new CategoriesPresenter(Injection.provideCategoriesRepository(), this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mCategoriesListener.showProducts();
            }
        });

        if (null == savedInstanceState) {
            initFragment(CategoriesFragment.newInstance());
        }

    }

    private void initFragment(Fragment categoriesFragment) {
        // Add the NotesFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, categoriesFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
