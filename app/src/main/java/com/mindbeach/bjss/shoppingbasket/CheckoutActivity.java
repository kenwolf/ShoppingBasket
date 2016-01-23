package com.mindbeach.bjss.shoppingbasket;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.mindbeach.bjss.shoppingbasket.model.ExchangeRate;
import com.mindbeach.bjss.shoppingbasket.model.ShoppingItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private static final String TAG = "CheckoutActivity";
    private static final String RATES = "rates";
    private static NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.UK);

    private TextView mTotalPriceTxt;

    private List<ShoppingItem> mItems = new ArrayList<>();
    private ArrayList<ExchangeRate> mRates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        if (savedInstanceState == null) {
            new GetRatesTask().execute(Constants.FIXER_ENDPOINT);
        }
        else {
            mRates = savedInstanceState.getParcelableArrayList(RATES);
        }

        if (getIntent() != null) {
            mItems = getIntent().getParcelableArrayListExtra(Constants.EXTRA_SHOPPING_ITEMS);
            Log.d(TAG, "got items: " + mItems);
        }

        mTotalPriceTxt = (TextView) findViewById(R.id.total_price);
        calculateTotal();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(RATES, mRates);
        super.onSaveInstanceState(savedInstanceState);
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

    private void parse(String content) {
        try {
            JSONObject json = new JSONObject(content);
            String base = json.getString("base");
            String date = json.getString("date");
            JSONObject rates = json.getJSONObject("rates");
            for (Iterator<String> keys = rates.keys(); keys.hasNext();){
                String code = keys.next();
                String rate = rates.getString(code);
                ExchangeRate exchangeRate = new ExchangeRate(code, rate);
                mRates.add(exchangeRate);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private class GetRatesTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            try {
                return Util.getContent(new URL(urls[0]));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String content) {
            Log.d(TAG, "Got content: " + content);
            parse(content);
        }
    }
}
