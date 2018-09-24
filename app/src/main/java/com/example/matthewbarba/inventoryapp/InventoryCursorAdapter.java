package com.example.matthewbarba.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import data.InventoryContract;

import static data.InventoryProvider.LOG_TAG;

public class InventoryCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link InventoryCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the inventory data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current item can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        // Find the individual views that we want to modify in the list item layout
        TextView productNameTextView = view.findViewById(R.id.list_product_name);
        TextView priceTextView = view.findViewById(R.id.list_price);
        final TextView quantityTextView = view.findViewById(R.id.list_quantity);

        // Find the columns of the item details that we are interested in
        int productIdColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry._ID);
        int productNameColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_QUANTITY);

        // Read the item details from the Cursor for the current item
        final long id = cursor.getLong(productIdColumnIndex);
        final String productName = cursor.getString(productNameColumnIndex);
        final String price = cursor.getString(priceColumnIndex);
        final String quantity = cursor.getString(quantityColumnIndex);

        //update the TextViews with the attributes for the current item
        productNameTextView.setText(productName);
        priceTextView.setText(price);
        quantityTextView.setText(quantity);

        Button button = view.findViewById(R.id.list_sale_button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri currentInventoryUri = ContentUris.withAppendedId(InventoryContract.InventoryEntry.CONTENT_URI, id);

                ContentValues values = new ContentValues();
                values.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME, productName);
                values.put(InventoryContract.InventoryEntry.COLUMN_PRICE, price);


                if (Integer.valueOf(quantity) > 0) {
                    values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, Integer.valueOf(quantity) - 1);
                    context.getContentResolver().update(currentInventoryUri, values, null, null);

                }
            }
        });
    }
}
