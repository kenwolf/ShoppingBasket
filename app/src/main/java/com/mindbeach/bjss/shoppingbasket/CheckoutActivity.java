package com.mindbeach.bjss.shoppingbasket;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mindbeach.bjss.shoppingbasket.model.ExchangeRate;
import com.mindbeach.bjss.shoppingbasket.model.ShoppingItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ken on 22/01/2016.
 * Displays the total price in GBP of the items passed to this activity.
 * Calls fixed.io endpoint and parses response into ExchangeRates.
 * Populates a Spinner which allows the user to see the total in different
 * currencies.
 */
public class CheckoutActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "CheckoutActivity";
    private static final String RATES = "rates";
    private static NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.UK);

    private TextView mTotalPriceTxt;
    private Spinner mRatesSpinner;

    private List<ShoppingItem> mItems = new ArrayList<>();
    private ArrayList<ExchangeRate> mRates = new ArrayList<>();
    private BigDecimal mGBPTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        if (getIntent() != null) {
            mItems = getIntent().getParcelableArrayListExtra(Constants.EXTRA_SHOPPING_ITEMS);
            Log.d(TAG, "got items: " + mItems);
        }

        mTotalPriceTxt = (TextView) findViewById(R.id.total_price);
        mRatesSpinner = (Spinner) findViewById(R.id.rates_spinner);

        mRatesSpinner.setOnItemSelectedListener(this);

        if (savedInstanceState == null) {
            // TODO check for internet connectivity, show error msg, etc.
            new GetRatesTask().execute(Constants.FIXER_ENDPOINT);
        } else {
            mRates = savedInstanceState.getParcelableArrayList(RATES);
            populateSpinner();
        }

        calculateGBPTotal();
    }

    /**
     * Calculate the total price in GBP. We should always be able to do this
     * since we should be getting the items array in our intent extras. This is
     * *not* dependent on getting exchange rates.
     */
    private void calculateGBPTotal() {
        if (mItems == null || mItems.size() == 0)
            Log.e(TAG, "No items in basket");
        else {
            int totalInPence = 0;
            for (ShoppingItem item : mItems) {
                totalInPence += item.getPriceInPence() * item.getAmount();
            }
            mTotalPriceTxt.setText(numberFormat.format((double) totalInPence / 100d));
            mGBPTotal = new BigDecimal(totalInPence / 100d);
            Log.d(TAG, "Got GBPTotal: " + mGBPTotal);
        }
    }

    /**
     * Populate the spinner with items from the mRates list
     */
    private void populateSpinner() {
        ArrayAdapter<ExchangeRate> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, mRates);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRatesSpinner.setAdapter(adapter);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(RATES, mRates);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Sets the total price text based on the GBPTotal and the currently selected
     * exchange rate
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (mGBPTotal == null) return;
        ExchangeRate selected = mRates.get(position);
        Log.d(TAG, "Selected rate: " + selected);
        BigDecimal convertedTotal = mGBPTotal.multiply(selected.getRate());
        Log.d(TAG, "Got conversion: " + convertedTotal);

        Currency curr = Currency.getInstance(selected.getCode());
        DecimalFormat df = new DecimalFormat("\u00A40.00");
        df.setCurrency(curr);
        mTotalPriceTxt.setText(df.format(convertedTotal));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    /**
     * Parse the JSON content of the Fixer.io response into a list of
     * Exchange Rates
     * @param content the content received from the http call
     * @return true if successful
     */
    private boolean parse(String content) {
        boolean success = false;
        // let's inject GBP as the first rate
        mRates.add(new ExchangeRate("GBP", "1"));
        try {
            JSONObject json = new JSONObject(content);
            String base = json.getString("base");
            String date = json.getString("date"); // TODO do something with this?
            JSONObject rates = json.getJSONObject("rates");
            for (Iterator<String> keys = rates.keys(); keys.hasNext(); ) {
                String code = keys.next();
                String rate = rates.getString(code);
                ExchangeRate exchangeRate = new ExchangeRate(code, rate);
                mRates.add(exchangeRate);
            }
            success = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return success;
    }

    public GetRatesTask getRatesTask() {
        return new GetRatesTask();
    }

    /**
     * AsyncTask that makes http call to Fixer.io and parses JSON into the
     * mRates list
     */
    public class GetRatesTask extends AsyncTask<String, Integer, Boolean> {
        protected Boolean doInBackground(String... urls) {
            boolean success = false;
            try {
                String content = Util.getContent(new URL(urls[0]));
                success = parse(content);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return success;
        }

        protected void onPostExecute(Boolean success) {
            if (success) {
                populateSpinner();
            }
        }
    }
}
