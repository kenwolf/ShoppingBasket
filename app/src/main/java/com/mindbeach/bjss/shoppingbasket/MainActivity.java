package com.mindbeach.bjss.shoppingbasket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.mindbeach.bjss.shoppingbasket.adapter.ShoppingItemAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ListView mListView;

    private ShoppingItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListView = (ListView) findViewById(R.id.list_view);

        mAdapter = new ShoppingItemAdapter(this);
        mAdapter.setShoppingItems(Constants.ITEMS_DATABASE);
        mListView.setAdapter(mAdapter);
    }


}
