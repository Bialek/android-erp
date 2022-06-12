package com.hellohasan.sqlite_project.Util;

public class Config {

    public static final String DATABASE_NAME = "invoice-db";

    //column names of invoice table
    public static final String TABLE_INVOICE = "invoice";
    public static final String COLUMN_INVOICE_ID = "_id";
    public static final String COLUMN_INVOICE_NAME = "name";
    public static final String COLUMN_INVOICE_DATE = "created_date";
    public static final String COLUMN_INVOICE_PHONE = "phone";
    public static final String COLUMN_INVOICE_ADDRESS = "address";
    public static final String COLUMN_INVOICE_AMOUNT = "amount";

    //column names of product table
    public static final String TABLE_PRODUCT = "product";
    public static final String COLUMN_PRODUCT_ID = "_id";
    public static final String COLUMN_PRODUCT_NAME = "name";
    public static final String COLUMN_PRODUCT_CODE = "product_code";
    public static final String COLUMN_PRODUCT_PRICE = "price";
    public static final String COLUMN_PRODUCT_AVAILABILITY = "availability";


    //column names of invoice_product table
    public static final String TABLE_INVOICE_PRODUCT = "invoice_product";
    public static final String COLUMN_FK_PRODUCT_ID = "fk_product_id";
    public static final String COLUMN_FK_INVOICE_ID = "fk_invoice_id";
    public static final String COLUMN_INVOICE_PRODUCT_NUMBER = "products_number";

    //others for general purpose key-value pair data
    public static final String TITLE = "title";
    public static final String CREATE_INVOICE = "create_invoice";
    public static final String UPDATE_INVOICE = "update_invoice";
    public static final String CREATE_PRODUCT = "create_product";
    public static final String UPDATE_PRODUCT = "update_product";
    public static final String INVOICE_CREATED = "invoice_created";
}
