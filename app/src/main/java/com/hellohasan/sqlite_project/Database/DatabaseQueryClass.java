package com.hellohasan.sqlite_project.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.hellohasan.sqlite_project.Features.InvoiceCRUD.CreateInvoice.Invoice;
import com.hellohasan.sqlite_project.Features.ProductCRUD.CreateProduct.Product;
import com.hellohasan.sqlite_project.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DatabaseQueryClass {

    private Context context;

    public DatabaseQueryClass(Context context){
        this.context = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public long insertInvoice(Invoice invoice){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_INVOICE_NAME, invoice.getName());
        contentValues.put(Config.COLUMN_INVOICE_DATE, invoice.getCreatedDate());
        contentValues.put(Config.COLUMN_INVOICE_PHONE, invoice.getPhoneNumber());
        contentValues.put(Config.COLUMN_INVOICE_ADDRESS, invoice.getAddress());
        contentValues.put(Config.COLUMN_INVOICE_AMOUNT, invoice.getAmount());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_INVOICE, null, contentValues);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<Invoice> getAllInvoice(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_INVOICE, null, null, null, null, null, null, null);

            /**
                 // If you want to execute raw query then uncomment below 2 lines. And comment out above line.

                 String SELECT_QUERY = String.format("SELECT %s, %s, %s, %s, %s FROM %s", Config.COLUMN_STUDENT_ID, Config.COLUMN_STUDENT_NAME, Config.COLUMN_STUDENT_REGISTRATION, Config.COLUMN_STUDENT_EMAIL, Config.COLUMN_STUDENT_PHONE, Config.TABLE_STUDENT);
                 cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);
             */

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Invoice> invoiceList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_INVOICE_ID));
                        String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_INVOICE_NAME));
                        String createdDate = cursor.getString(cursor.getColumnIndex(Config.COLUMN_INVOICE_DATE));
                        String address = cursor.getString(cursor.getColumnIndex(Config.COLUMN_INVOICE_ADDRESS));
                        String phone = cursor.getString(cursor.getColumnIndex(Config.COLUMN_INVOICE_PHONE));
                        double amount = cursor.getDouble(cursor.getColumnIndex(Config.COLUMN_INVOICE_AMOUNT));


                        invoiceList.add(new Invoice(id, name, createdDate, phone, address,amount));

                    }   while (cursor.moveToNext());

                    return invoiceList;
                }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public Invoice getInvoiceById(long selectedId){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        Invoice invoice = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_INVOICE, null,
                    Config.COLUMN_INVOICE_ID + " = ? ", new String[]{String.valueOf(selectedId)},
                    null, null, null);

            /**
                 // If you want to execute raw query then uncomment below 2 lines. And comment out above sqLiteDatabase.query() method.

                 String SELECT_QUERY = String.format("SELECT * FROM %s WHERE %s = %s", Config.TABLE_STUDENT, Config.COLUMN_STUDENT_REGISTRATION, String.valueOf(registrationNum));
                 cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);
             */

            if(cursor.moveToFirst()){
                int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_INVOICE_ID));
                String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_INVOICE_NAME));
                String createdDate = cursor.getString(cursor.getColumnIndex(Config.COLUMN_INVOICE_DATE));
                String address = cursor.getString(cursor.getColumnIndex(Config.COLUMN_INVOICE_ADDRESS));
                String phone = cursor.getString(cursor.getColumnIndex(Config.COLUMN_INVOICE_PHONE));
                double amount = cursor.getDouble(cursor.getColumnIndex(Config.COLUMN_INVOICE_AMOUNT));


                invoice = new Invoice(id, name, createdDate, phone, address,amount);

            }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return invoice;
    }

    public long updateInvoiceInfo(Invoice invoice){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_INVOICE_NAME, invoice.getName());
        contentValues.put(Config.COLUMN_INVOICE_DATE, invoice.getCreatedDate());
        contentValues.put(Config.COLUMN_INVOICE_PHONE, invoice.getPhoneNumber());
        contentValues.put(Config.COLUMN_INVOICE_ADDRESS, invoice.getAddress());
        contentValues.put(Config.COLUMN_INVOICE_AMOUNT, invoice.getAmount());

        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_INVOICE, contentValues,
                    Config.COLUMN_INVOICE_ID + " = ? ",
                    new String[] {String.valueOf(invoice.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long deleteInvoice(long id) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Config.TABLE_INVOICE,
                                    Config.COLUMN_INVOICE_ID + " = ? ",
                                    new String[]{ String.valueOf(id)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }

    public long getNumberOfInvoice(){
        long count = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_INVOICE);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return count;
    }

    // Products
    public long insertProduct(Product product){
        long rowId = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_PRODUCT_NAME, product.getName());
        contentValues.put(Config.COLUMN_PRODUCT_CODE, product.getCode());
        contentValues.put(Config.COLUMN_PRODUCT_AVAILABILITY, product.getAvailability());
        contentValues.put(Config.COLUMN_PRODUCT_PRICE, product.getPrice());

        try {
            rowId = sqLiteDatabase.insertOrThrow(Config.TABLE_PRODUCT, null, contentValues);
        } catch (SQLiteException e){
            Logger.d(e);
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowId;
    }

    public Product getProductById(long id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Product product = null;

        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_PRODUCT, null,
                    Config.COLUMN_PRODUCT_ID + " = ? ", new String[] {String.valueOf(id)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUCT_NAME));
                int code = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODUCT_CODE));
                int availability = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODUCT_AVAILABILITY));
                double price = cursor.getDouble(cursor.getColumnIndex(Config.COLUMN_PRODUCT_PRICE));

                product = new Product(id, name, code, availability, price);
            }
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return product;
    }

    public long updateProductInfo(Product product){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_PRODUCT_NAME, product.getName());
        contentValues.put(Config.COLUMN_PRODUCT_CODE, product.getCode());
        contentValues.put(Config.COLUMN_PRODUCT_AVAILABILITY, product.getAvailability());
        contentValues.put(Config.COLUMN_PRODUCT_PRICE, product.getPrice());

        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_PRODUCT, contentValues,
                    Config.COLUMN_PRODUCT_ID + " = ? ",
                    new String[] {String.valueOf(product.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public List<Product> getAllProduct(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_PRODUCT, null, null, null, null, null, null, null);

            /**
             // If you want to execute raw query then uncomment below 2 lines. And comment out above line.

             String SELECT_QUERY = String.format("SELECT %s, %s, %s, %s, %s FROM %s", Config.COLUMN_STUDENT_ID, Config.COLUMN_STUDENT_NAME, Config.COLUMN_STUDENT_REGISTRATION, Config.COLUMN_STUDENT_EMAIL, Config.COLUMN_STUDENT_PHONE, Config.TABLE_STUDENT);
             cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);
             */

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Product> productList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODUCT_ID));
                        String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUCT_NAME));
                        int code = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODUCT_CODE));
                        int availability = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODUCT_AVAILABILITY));
                        double price = cursor.getDouble(cursor.getColumnIndex(Config.COLUMN_PRODUCT_PRICE));

                        productList.add(new Product(id, name,code, availability, price));

                    }   while (cursor.moveToNext());

                    return productList;
                }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public boolean deleteProductById(long id) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        int row = sqLiteDatabase.delete(Config.TABLE_PRODUCT,
                Config.COLUMN_PRODUCT_ID + " = ? ", new String[]{String.valueOf(id)});

        return row > 0;
    }

    public long getNumberOfProduct(){
        long count = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_PRODUCT);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return count;
    }

}