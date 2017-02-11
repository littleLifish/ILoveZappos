package myapp.findyouritem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XIAO on 2017/2/5.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "itemManager";
    private static final String TABLE_ITEMS = "items";

    private static final String KEY_ID = "_id";
    private static final String KEY_BRAND = "brand";
    private static final String KEY_DISCRIPTION = "discription";
    private static final String KEY_PRICE = "price";
    private static final String KEY_TYPE = "Type";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + KEY_ID + "  INTEGER PRIMARY KEY ," + KEY_BRAND + " TEXT,"
                + KEY_DISCRIPTION + " TEXT," + KEY_PRICE + " INTEGER," + KEY_TYPE
                + " TEXT" + ")";
        db.execSQL(CREATE_ITEMS_TABLE);
        Log.d("Create: ", "Create ..");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);

            // Create tables again
            onCreate(db);
        }
    }

    public void onUpdate(){
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    // Adding new Items
    public void addItems(Items item){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(KEY_BRAND, item.getBrand());
            values.put(KEY_DISCRIPTION, item.getDisc());
            values.put(KEY_PRICE, item.getPrice());
            values.put(KEY_TYPE, item.getType());

            db.insertOrThrow(TABLE_ITEMS, null, values);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("TAG", "Error while trying to add post to database");
        } finally {

            db.endTransaction();
        }
    }

    // Getting single contact
    public Items getItems(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ITEMS, new String[] { KEY_ID,
                        KEY_BRAND, KEY_DISCRIPTION, KEY_PRICE, KEY_TYPE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Items items = new Items(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),Integer.parseInt(cursor.getString(3)),cursor.getString(4));
        // return contact
        return items;
    }

    //find by type
    public List<Items> findByType(String type){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + KEY_BRAND + ", " + KEY_DISCRIPTION + ", "
                + KEY_PRICE + ", " + KEY_TYPE + " FROM " + TABLE_ITEMS + " WHERE " + KEY_TYPE + "=?", new String[]{type.toString()});
//        Cursor cursor = db.rawQuery("SELECT brand,discription,price,Type FROM items WHERE id=?" , new String[]{type.toString()});

        List<Items> lItem = new ArrayList<Items>();
        while (cursor.moveToNext()) {
            String brand = cursor.getString(0);
            String discription = cursor.getString(1);
            Integer price = cursor.getInt(2);
            String sType = cursor.getString(3);
            lItem.add(new Items(brand, discription, price, sType));
        }
        cursor.close();
        db.close();
        return lItem;
    }

    // Getting All Contacts
    public List<Items> getAllItems(){
        List<Items> itemtList = new ArrayList<Items>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Items items = new Items();
                items.setID(Integer.parseInt(cursor.getString(0)));
                items.setBrand(cursor.getString(1));
                items.setDisc(cursor.getString(2));
                items.setPrice(Integer.parseInt(cursor.getString(3)));
                items.setType(cursor.getString(4));
                // Adding contact to list
                itemtList.add(items);
            } while (cursor.moveToNext());
        }
        // return contact list
        return itemtList;
    }

    // Getting items Count
    public int getItemsCount(){
        String countQuery = "SELECT  * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    // Updating single contact
    public int updateItems(Items item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BRAND, item.getBrand());
        values.put(KEY_DISCRIPTION, item.getDisc());
        values.put(KEY_PRICE, item.getPrice());
        values.put(KEY_TYPE, item.getType());

        // updating row
        return db.update(TABLE_ITEMS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getID()) });
    }

    // Deleting single
    public void deleteItems(Items item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, KEY_ID + " = ?",
                new String[]{String.valueOf(item.getID())});
        db.close();
    }
}
