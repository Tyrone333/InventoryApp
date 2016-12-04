package com.example.android.inventoryapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryDbHelper;

import java.util.ArrayList;

/**
 * Created by tyrone3 on 03.12.16.
 */

public class InventoryListAdapter extends ArrayAdapter<Product> {

    private AdapterView.OnItemClickListener onClickListener;

    Activity a;

    public InventoryListAdapter(Activity context, ArrayList<Product> list, AdapterView.OnItemClickListener onClickListener) {
        super(context, 0, list);
        this.onClickListener = onClickListener;
        a = context;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

        }
        final Product currentItem = getItem(position);

        TextView name = (TextView) listItemView.findViewById(R.id.text_view_name);
        name.setText(currentItem.getName());
        TextView quantity = (TextView) listItemView.findViewById(R.id.text_view_quantity);
        quantity.setText(a.getString(R.string.quantity_) + String.valueOf(currentItem.getQuantity()));
        TextView price = (TextView) listItemView.findViewById(R.id.text_view_price);
        price.setText(a.getString(R.string.price_) + String.valueOf(currentItem.getPrice()));
        ImageView image = (ImageView) listItemView.findViewById(R.id.image_view);
        Bitmap bitmap = BitmapFactory.decodeByteArray(currentItem.getImage(), 0, currentItem.getImage().length);
        image.setImageBitmap(bitmap);


        Button soldButton = (Button) listItemView.findViewById(R.id.sold_button);
        soldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mQuantity = currentItem.getQuantity();
                if (mQuantity <= 0) {
                    Toast.makeText(getContext(), R.string.no_items_left, Toast.LENGTH_SHORT).show();
                } else {
                    InventoryDbHelper db = new InventoryDbHelper(getContext());
                    db.updateItem(currentItem.getName(), mQuantity - 1);
                    a.recreate();
                }
            }
        });

        Button listButton = (Button) listItemView.findViewById(R.id.item_click);
        listButton.setTag(position);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onItemClick(null, view, position, 0);
                }
            }
        });

        return listItemView;
    }
}
