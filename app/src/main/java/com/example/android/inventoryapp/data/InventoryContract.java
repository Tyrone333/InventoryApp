package com.example.android.inventoryapp.data;

import android.provider.BaseColumns;

/**
 * Created by tyrone3 on 03.12.16.
 */

public class InventoryContract {

    private InventoryContract() {
    }

    public static final class InventoryEntry implements BaseColumns {


        public final static String TABLE_NAME = "inventory";


        public final static String _ID = BaseColumns._ID;


        public final static String COLUMN_PRODUCT_NAME = "name";


        public final static String COLUMN_CURRENT_QUANTITY = "quantity";


        public final static String COLUMN_PRICE = "price";


        public final static String COLUMN_IMAGE = "image";

    }
}
