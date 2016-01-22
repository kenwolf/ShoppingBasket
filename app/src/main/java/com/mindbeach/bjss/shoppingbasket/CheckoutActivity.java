package com.mindbeach.bjss.shoppingbasket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.mindbeach.bjss.shoppingbasket.model.ShoppingItem;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private static final String TAG = "CheckoutActivity";

    private TextView mTotalPriceTxt;

    private List<ShoppingItem> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent() != null) {
            mItems = getIntent().getParcelableArrayListExtra(Constants.EXTRA_SHOPPING_ITEMS);
            Log.d(TAG, "got items: " + mItems);
        }

        mTotalPriceTxt = (TextView) findViewById(R.id.total_price);
    }

}
