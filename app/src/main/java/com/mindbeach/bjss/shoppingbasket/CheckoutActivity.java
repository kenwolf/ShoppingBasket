package com.mindbeach.bjss.shoppingbasket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.mindbeach.bjss.shoppingbasket.model.ShoppingItem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private static final String TAG = "CheckoutActivity";
    private static NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.UK);

    private TextView mTotalPriceTxt;

    private List<ShoppingItem> mItems = new ArrayList<>();

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
        calculateTotal();
    }

    private void calculateTotal() {
        if (mItems.size() == 0)
            Log.e(TAG, "No items in basket");
        else {
            int totalInPence = 0;
            for (ShoppingItem item : mItems) {
                totalInPence += item.getPriceInPence() * item.getAmount();
            }
            mTotalPriceTxt.setText(numberFormat.format((double) totalInPence / 100d));
        }

    }

}
