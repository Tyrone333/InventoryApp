package com.example.android.inventoryapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.inventoryapp.Product;

import java.util.ArrayList;

/**
 * Created by tyrone3 on 03.12.16.
 */

public class InventoryDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "inventory.db";

    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_HABIT_TABLE = "CREATE TABLE " +
                InventoryContract.InventoryEntry.TABLE_NAME + " (" +
                InventoryContract.InventoryEntry._ID + " INTEGER PRIMARY KEY," +
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, " +
                InventoryContract.InventoryEntry.COLUMN_CURRENT_QUANTITY + " INTEGER NOT NULL, " +
                InventoryContract.InventoryEntry.COLUMN_PRICE + " INTEGER NOT NULL, " +
                InventoryContract.InventoryEntry.COLUMN_IMAGE + " BLOB NOT NULL);";
        sqLiteDatabase.execSQL(SQL_CREATE_HABIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // The database is still at version 1, so there's nothing to be done here.
    }

    public ArrayList<Product> getAllData() {
        ArrayList<Product> productList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor getList = db.rawQuery("SELECT * FROM " + InventoryContract.InventoryEntry.TABLE_NAME, null);
        getList.moveToFirst();
        while (getList.isAfterLast() == false) {
            String productName = getList.getString(getList.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME));
            int quantity = getList.getInt(getList.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_CURRENT_QUANTITY));
            int price = getList.getInt(getList.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRICE));
            byte[] image = getList.getBlob(getList.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_IMAGE));
            productList.add(new Product(productName, quantity, price, image));
            getList.moveToNext();
        }
        return productList;
    }

    // Insert data into the table
    public boolean insertData(String name, int quantity, int price, byte[] image, boolean newProduct) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME, name);
        values.put(InventoryContract.InventoryEntry.COLUMN_CURRENT_QUANTITY, quantity);
        values.put(InventoryContract.InventoryEntry.COLUMN_PRICE, price);
        values.put(InventoryContract.InventoryEntry.COLUMN_IMAGE, image);
        db.insert(InventoryContract.InventoryEntry.TABLE_NAME, null, values);
        return true;
    }

    // Delete all items from the table
    public int deleteAllProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(InventoryContract.InventoryEntry.TABLE_NAME, null, null);
    }

    // Delete one row in the table
    public boolean deleteOneProduct(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(InventoryContract.InventoryEntry.TABLE_NAME, "name=?", new String[]{name}) > 0;
    }

    //Update the database
    public void updateItem(String name, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + InventoryContract.InventoryEntry.TABLE_NAME + " SET quantity = "
                + quantity + " WHERE name = \"" + name + "\"";
        db.execSQL(query);
        db.close();
    }

}
