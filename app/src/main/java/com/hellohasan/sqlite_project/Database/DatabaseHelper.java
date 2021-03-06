package com.hellohasan.sqlite_project.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hellohasan.sqlite_project.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    // All Static variables
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = Config.DATABASE_NAME;

    // Constructor
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static DatabaseHelper getInstance(Context context) {
        if(databaseHelper==null){
            synchronized (DatabaseHelper.class) {
                if(databaseHelper==null)
                    databaseHelper = new DatabaseHelper(context);
            }
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tables SQL execution
        String CREATE_INVOICE_TABLE = "CREATE TABLE " + Config.TABLE_INVOICE + "("
                + Config.COLUMN_INVOICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_INVOICE_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_INVOICE_DATE + " INTEGER NOT NULL UNIQUE, "
                + Config.COLUMN_INVOICE_PHONE + " TEXT, " //nullable
                + Config.COLUMN_INVOICE_ADDRESS + " TEXT NOT NULL,"
                + Config.COLUMN_INVOICE_AMOUNT + " REAL NOT NULL"
                + ")";

        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + Config.TABLE_PRODUCT + "("
                + Config.COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_PRODUCT_CODE + " INTEGER NOT NULL, "
                + Config.COLUMN_PRODUCT_AVAILABILITY + " INTEGER NOT NULL,"
                + Config.COLUMN_PRODUCT_PRICE + " REAL " //nullable
                + ")";

//        String CREATE_INVOICE_PRODUCT_TABLE = "CREATE TABLE " + Config.TABLE_INVOICE_PRODUCT + "("
//                + Config.COLUMN_INVOICE_PRODUCT_NUMBER + " INTEGER NOT NULL, "
//                + "FOREIGN KEY (" + Config.COLUMN_FK_INVOICE_ID + ") REFERENCES " + Config.TABLE_INVOICE + "(" + Config.COLUMN_INVOICE_ID + "),"
//                + "FOREIGN KEY (" + Config.COLUMN_FK_PRODUCT_ID + ") REFERENCES " + Config.TABLE_PRODUCT + "(" + Config.COLUMN_PRODUCT_ID + ")"
//                + ")";

        db.execSQL(CREATE_INVOICE_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
//        db.execSQL(CREATE_INVOICE_PRODUCT_TABLE);


        Logger.d("DB created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_INVOICE);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_PRODUCT);
//        db.execSQL("DROP TABLE IF EXISTS " + Config.COLUMN_INVOICE_PRODUCT_NUMBER);

        // Create tables again
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        //enable foreign key constraints like ON UPDATE CASCADE, ON DELETE CASCADE
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

}
