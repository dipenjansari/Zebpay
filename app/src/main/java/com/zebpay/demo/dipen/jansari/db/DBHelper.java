package com.zebpay.demo.dipen.jansari.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zebpay.demo.dipen.jansari.model.TickerModel;
import com.zebpay.demo.dipen.jansari.util.Logg;

/**
 *  Created by dipen on 17/7/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "zebPayData";
    private static DBHelper mInstance = null;

    // Table Name
    private String TABLE_TICKER = "tickerData";

    //Column Names
    private String KEY_ID = "id";
    private String KEY_MARKET = "market";
    private String KEY_BUY = "buy";
    private String KEY_SELL = "sell";
    private String KEY_CURRENCY = "currency";
    private String KEY_VOLUME = "volume";

    // Table Create Statements
    // Todo table create statement
    private String CREATE_TABLE = "CREATE TABLE "
            + TABLE_TICKER
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_MARKET + " TEXT, "
            + KEY_BUY + " TEXT, "
            + KEY_SELL + " TEXT, "
            + KEY_CURRENCY + " TEXT, "
            + KEY_VOLUME + " TEXT "
            + ")";

    /**
     * constructor should be private to prevent direct instantiation.
     * make call to static factory method "getInstance()" instead.
     */
    private DBHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHelper getInstance(Context ctx) {
        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new DBHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Logg.d(TAG, "DB Created");
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKER);
        onCreate(db);
    }

    /**
     * Add ticker data into db
     */
    public void addTickerData(TickerModel tickerModel) {

        Logg.d("Ticker Table", "Updated");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_MARKET, tickerModel.getMarket());
        values.put(KEY_BUY, tickerModel.getBuy());
        values.put(KEY_SELL, tickerModel.getSell());
        values.put(KEY_CURRENCY, tickerModel.getCurrency());
        values.put(KEY_VOLUME, tickerModel.getVolume());

        db.insert(TABLE_TICKER, null, values);
        db.close();
    }
}
