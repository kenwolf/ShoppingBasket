package com.mindbeach.bjss.shoppingbasket;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mindbeach.bjss.shoppingbasket.adapter.ShoppingItemAdapter;
import com.mindbeach.bjss.shoppingbasket.model.ShoppingItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private ListView mListView;
    private Button mCheckoutBtn;
    private Button mClearBtn;

    private ShoppingItemAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list_view);
        mCheckoutBtn = (Button) findViewById(R.id.checkout_btn);
        mClearBtn = (Button) findViewById(R.id.clear_btn);

        mAdapter = new ShoppingItemAdapter(this);
        mAdapter.setShoppingItems(Constants.ITEMS_DATABASE);
        mListView.setAdapter(mAdapter);

        mCheckoutBtn.setOnClickListener(this);
        mClearBtn.setOnClickListener(this);
    }

    private void checkout() {
        ArrayList<ShoppingItem> items = mAdapter.getItems();
        if (isBasketEmpty(items)) {
            Toast.makeText(this, R.string.msg_no_items, Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, CheckoutActivity.class);
            intent.putParcelableArrayListExtra(Constants.EXTRA_SHOPPING_ITEMS, items);
            startActivity(intent);
        }
    }

    private void clear() {
        mAdapter.clearAmounts();
    }

    private boolean isBasketEmpty(List<ShoppingItem> items) {
        for (ShoppingItem item : items) {
            if (item.getAmount() > 0)
                return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkout_btn:
                checkout();
                break;

            case R.id.clear_btn:
                clear();
                break;
        }
    }


}
