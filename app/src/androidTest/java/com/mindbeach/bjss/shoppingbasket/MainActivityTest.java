package com.mindbeach.bjss.shoppingbasket;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.mindbeach.bjss.shoppingbasket.model.ShoppingItem;

/**
 * Created by Ken on 23/01/2016.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2 {

    private Activity mActivity;
    private ListView mListView;
    private Button mCheckoutBtn;

    // There should only be 4 items in the list
    public static final int ADAPTER_COUNT = 4;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        mActivity = getActivity();
        mListView = (ListView) mActivity.findViewById(R.id.list_view);
        mCheckoutBtn = (Button) mActivity.findViewById(R.id.checkout_btn);
    }

    public void testPreConditions() {
        assertTrue(mListView != null); // ListView should not be null
        assertTrue(mCheckoutBtn != null); // Checkout button should not be null
        assertEquals(mListView.getAdapter().getCount(), ADAPTER_COUNT); // There should be 4 items in the list
    }

    public void testListClick() {
        mActivity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        View row = getViewByPosition(0, mListView);
                        ImageButton moreBtn = (ImageButton) row.findViewById(R.id.more_btn);
                        MainActivityTest.assertTrue(moreBtn != null); // More button should not be null

                        ShoppingItem shoppingItem = (ShoppingItem) mListView.getAdapter().getItem(0);
                        MainActivityTest.assertEquals(shoppingItem.getAmount(), 0); // At the start we should have 0 peas

                        moreBtn.performClick(); // Click the more btn

                        MainActivityTest.assertEquals(shoppingItem.getAmount(), 1); // After 1 click we should have 1 peas
                    }
                }
        );

    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

}
