package data;

import android.provider.BaseColumns;

public final class InventoryContract {

    public static final class InventoryEntry implements BaseColumns {

        public static final String TABLE_NAME = "inventory";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "product";
        public static final String COLUMN_SUPPLIER = "supplierName";
        public static final String COLUMN_SUPPLIER_NUMBER = "supplierPhoneNumber";
        public static final String COLUMN_PRICE = "price" ;
        public static final String COLUMN_QUANTITY = "quantity";
    }
}
