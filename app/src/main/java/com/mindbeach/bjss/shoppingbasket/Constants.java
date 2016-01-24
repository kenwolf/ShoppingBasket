package com.mindbeach.bjss.shoppingbasket;

import com.mindbeach.bjss.shoppingbasket.model.ShoppingItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ken on 22/01/2016.
 */
public class Constants {

    // Fixed.io endpoint to call to get exchange rates
    public static final String FIXER_ENDPOINT = "http://api.fixer.io/latest?base=GBP";

    // Intent extra for the list of ShoppingItems we pass
    public static final String EXTRA_SHOPPING_ITEMS = "extra_shopping_items";

    // The list of ShoppingItems available for purchase
    // TODO move this to an sqlite db
    public static ArrayList<ShoppingItem> ITEMS_DATABASE = new ArrayList<>();
    static {
        ITEMS_DATABASE.add(new ShoppingItem(R.string.peas, R.string.bag, 95));
        ITEMS_DATABASE.add(new ShoppingItem(R.string.eggs, R.string.dozen, 210));
        ITEMS_DATABASE.add(new ShoppingItem(R.string.milk, R.string.bottle, 130));
        ITEMS_DATABASE.add(new ShoppingItem(R.string.beans, R.string.can, 73));
    }

}
