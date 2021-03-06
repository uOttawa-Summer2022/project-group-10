package com.app.coursebooking.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRODUCT_NAME = "name";
    private static final String COLUMN_PRODUCT_PRICE = "price";
    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_cmd = "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_PRODUCT_NAME + " TEXT, " +
                COLUMN_PRODUCT_PRICE + " DOUBLE " + ")";

        db.execSQL(create_table_cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null); // returns "cursor" all products from the table
    }

    public ArrayList<String> findProductStartWith(String prefix){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_PRODUCT_NAME +
                " LIKE " + "\"" + prefix + "%\" ";
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<String> productList = new ArrayList<>();
        while(cursor.moveToNext()){
            productList.add(cursor.getString(1) + " (" +cursor.getString(2)+")");
        }
        cursor.close();
        db.close();

        return productList;
    }

    public ArrayList<String> findProductPrice(double price){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_PRODUCT_PRICE +
                " = " + price;
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<String> productList = new ArrayList<>();
        while(cursor.moveToNext()){
            productList.add(cursor.getString(1) + " (" +cursor.getString(2)+")");
        }
        cursor.close();
        db.close();

        return productList;
    }

    public ArrayList<String> findProductStartWithAndPrice(String prefix, double price){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_PRODUCT_NAME +
                " LIKE " + "\"" + prefix + "%\" AND " + COLUMN_PRODUCT_PRICE +
                " = " + price;
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<String> productList = new ArrayList<>();
        while(cursor.moveToNext()){
            productList.add(cursor.getString(1) + " (" +cursor.getString(2)+")");
        }
        cursor.close();
        db.close();

        return productList;
    }

    public boolean deleteProduct(String productName){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_PRODUCT_NAME + " = \"" +
                productName + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToNext()){
            int idStr = cursor.getInt(0);
            db.delete(TABLE_NAME, COLUMN_ID + " = " + idStr, null);
            cursor.close();
            result = true;
        }
        db.close();
        return  result;
    }

    /*
    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_PRODUCT_NAME, product.getProductName());
        values.put(COLUMN_PRODUCT_PRICE, product.getProductPrice());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    */

}
