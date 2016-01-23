package com.mindbeach.bjss.shoppingbasket;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Spinner;
import android.widget.TextView;

import com.mindbeach.bjss.shoppingbasket.model.ExchangeRate;
import com.mindbeach.bjss.shoppingbasket.model.ShoppingItem;

import java.util.ArrayList;

/**
 * Created by Ken on 23/01/2016.
 */
public class CheckoutActivityTest extends ActivityInstrumentationTestCase2 {

    private Activity mActivity;

    private TextView mTotalTxt;
    private Spinner mSpinner;
    private ArrayList<ShoppingItem> mItems;
    private ArrayList<ExchangeRate> mRates;


    public CheckoutActivityTest() {
        super(CheckoutActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);

        ArrayList<ShoppingItem> items = new ArrayList<>();
        ShoppingItem item = new ShoppingItem(R.string.peas, R.string.bag, 95);
        item.setAmount(1);
        items.add(item);

        Intent intent = new Intent(getInstrumentation().getContext(), CheckoutActivity.class);
        intent.putParcelableArrayListExtra(Constants.EXTRA_SHOPPING_ITEMS, items);
        setActivityIntent(intent);

        mActivity = getActivity();

        mTotalTxt = (TextView) mActivity.findViewById(R.id.total_price);
        mSpinner = (Spinner) mActivity.findViewById(R.id.rates_spinner);
        mItems = mActivity.getIntent().getParcelableArrayListExtra(Constants.EXTRA_SHOPPING_ITEMS);
    }

    public void testPreConditions() {
        assertTrue(mTotalTxt != null); // TotalTxt should not be null
        assertTrue(mSpinner != null); // Spinner  button should not be null
        assertTrue(mItems != null); // Items shouldn't be null
        assertEquals(mItems.size(), 1); // Items size should be 1
        assertEquals(mSpinner.getAdapter().getCount(), 1); // We just have 1 rate
    }

    public void testCalculations() {
        assertEquals(mTotalTxt.getText().toString(), "£0.95"); // If we have just one pea item this is what we expect
    }


}
