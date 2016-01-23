package com.mindbeach.bjss.shoppingbasket.model;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.mindbeach.bjss.shoppingbasket.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Ken on 22/01/2016.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class ExchangeRateTest {

    private ExchangeRate mExchangeRate;

    @Before
    public void createExchangeRate() {
        mExchangeRate = new ExchangeRate("USD", "1.5");
    }

    @Test
    public void exchangeRate_ParcelableWriteRead() {
        // Write the data.
        Parcel parcel = Parcel.obtain();
        mExchangeRate.writeToParcel(parcel, mExchangeRate.describeContents());

        // After you're done with writing, you need to reset the parcel for reading.
        parcel.setDataPosition(0);

        // Read the data.
        ExchangeRate createdFromParcel = ExchangeRate.CREATOR.createFromParcel(parcel);
        String code = createdFromParcel.getCode();
        BigDecimal rate = createdFromParcel.getRate();

        // Verify that the received data is correct.
        assertThat(code, is("USD"));
        assertThat(rate, is(new BigDecimal("1.5")));

    }
}
