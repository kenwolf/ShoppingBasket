package com.mindbeach.bjss.shoppingbasket.model;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.mindbeach.bjss.shoppingbasket.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Ken on 22/01/2016.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class ShoppingItemTest {

    private ShoppingItem mShoppingItem;

    @Before
    public void createShoppingItem() {
        mShoppingItem = new ShoppingItem(R.string.peas, R.string.bag, 95);
    }

    @Test
    public void shoppingItem_ParcelableWriteRead() {
        // Write the data.
        Parcel parcel = Parcel.obtain();
        mShoppingItem.writeToParcel(parcel, mShoppingItem.describeContents());

        // After you're done with writing, you need to reset the parcel for reading.
        parcel.setDataPosition(0);

        // Read the data.
        ShoppingItem createdFromParcel = ShoppingItem.CREATOR.createFromParcel(parcel);
        int nameResId = createdFromParcel.getNameResId();
        int containerResId = createdFromParcel.getContainerNameResId();
        int priceInPence = createdFromParcel.getPriceInPence();

        // Verify that the received data is correct.
        assertThat(nameResId, is(R.string.peas));
        assertThat(containerResId, is(R.string.bag));
        assertThat(priceInPence, is(95));

    }
}
