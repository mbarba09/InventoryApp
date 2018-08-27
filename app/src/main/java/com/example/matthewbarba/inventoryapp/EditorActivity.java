package com.example.matthewbarba.inventoryapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import data.InventoryContract;
import data.InventoryDbHelper;
import data.InventoryContract.InventoryEntry;


/**
 * Allows user to create a new inventory item or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity{

    /** EditText field to enter the product name **/
    private EditText mProductNameEditText;

    /** EditText field to enter the supplier name **/
    private EditText mSupplierNameEditText;

    /** EditText field to enter the supplier phone number **/
    private EditText mSupplierPhoneNumberEditText;

    /** EditText field to enter the product price **/
    private EditText mProductPriceEditText;

    /** EditText field to enter the quantity of the product in the inventory **/
    private EditText mQuantityEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mProductNameEditText = findViewById(R.id.edit_product_name);
        mSupplierNameEditText = findViewById(R.id.edit_supplier_name);
        mSupplierPhoneNumberEditText = findViewById(R.id.edit_supplier_phone);
        mProductPriceEditText = findViewById(R.id.edit_price);
        mQuantityEditText = findViewById(R.id.edit_quantity);
    }

    private void insertItem()   {

        String productNameString = mProductNameEditText.getText().toString().trim();
        String supplierNameString = mSupplierNameEditText.getText().toString().trim();
        String supplierPhoneString = mSupplierPhoneNumberEditText.getText().toString().trim();
        String priceString = mProductPriceEditText.getText().toString().trim();
        int priceInt = Integer.parseInt(priceString);
        String quantityString = mQuantityEditText.getText().toString().trim();
        int quantityInt = Integer.parseInt(quantityString);

        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);

        //        gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, productNameString);
        values.put(InventoryEntry.COLUMN_SUPPLIER, supplierNameString);
        values.put(InventoryEntry.COLUMN_SUPPLIER_NUMBER, supplierPhoneString);
        values.put(InventoryEntry.COLUMN_PRICE, priceInt);
        values.put(InventoryEntry.COLUMN_QUANTITY, quantityInt);

        long newRowId = db.insert(InventoryEntry.TABLE_NAME, null, values);
        Log.v("Editor Activity", "New Row ID " + newRowId);
        if (newRowId == -1)  {
            Toast toastError = Toast.makeText(this, "Error with saving item!", Toast.LENGTH_LONG);
            toastError.show();
        } else {
            Toast toastSuccess = Toast.makeText(this, "Item saved with ID: " + newRowId, Toast.LENGTH_LONG);
            toastSuccess.show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // save pet to the database
                insertItem();
                // exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
