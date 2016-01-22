package com.mindbeach.bjss.shoppingbasket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mindbeach.bjss.shoppingbasket.R;
import com.mindbeach.bjss.shoppingbasket.model.ShoppingItem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShoppingItemAdapter extends BaseAdapter {

    private static final String TAG = "ShoppingItemAdapter";
    private static NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.UK);

    private Context mContext;
    private LayoutInflater mInflater;

    private List<ShoppingItem> mItems = new ArrayList<ShoppingItem>();

    public ShoppingItemAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setShoppingItems(ArrayList<ShoppingItem> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public int getCount() {
        return mItems.size();
    }

    public Object getItem(int position) {
        return mItems.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            row = mInflater.inflate(R.layout.row_shopping_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) row.findViewById(R.id.name);
            viewHolder.price = (TextView) row.findViewById(R.id.price);
            viewHolder.less = (Button) row.findViewById(R.id.less_btn);
            viewHolder.more = (Button) row.findViewById(R.id.more_btn);
            viewHolder.amount = (TextView) row.findViewById(R.id.amount);
            viewHolder.container = (TextView) row.findViewById(R.id.container);
            row.setTag(viewHolder);
        }

        final ShoppingItem shoppingItem = (ShoppingItem) getItem(position);

        if (shoppingItem != null) {
            ViewHolder viewHolder = (ViewHolder) row.getTag();
            viewHolder.name.setText(shoppingItem.getNameResId());
            viewHolder.price.setText(numberFormat.format((double) shoppingItem.getPriceInPence()/100d));
            viewHolder.container.setText(shoppingItem.getContainerNameResId());
            viewHolder.amount.setText(String.valueOf(shoppingItem.getAmount()));
            viewHolder.less.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shoppingItem.decrementAmount();
                    notifyDataSetChanged();
                }
            });
            viewHolder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shoppingItem.incrementAmount();
                    notifyDataSetChanged();
                }
            });

        }

        return row;
    }

    static class ViewHolder {
        private TextView name;
        private TextView price;
        private TextView container;
        private Button less;
        private Button more;
        private TextView amount;

    }

}
