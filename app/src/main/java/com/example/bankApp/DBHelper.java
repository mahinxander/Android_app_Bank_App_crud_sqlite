package com.example.bankApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bankApp.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "DBbank.db";

    private String CREATE_USER_TABLE = "CREATE TABLE " + Variable.TABLE_USERS + "("
            + Variable.COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Variable.COLUMN_USER_ACC_ID + " INTEGER UNIQUE,"
            + Variable.COLUMN_BALANCE + " INTEGER,"
            + Variable.COLUMN_USER_NAME + " TEXT,"
            + Variable.COLUMN_USER_PHONE_NUMBER + " TEXT,"
            + Variable.COLUMN_USER_EMAIL + " TEXT,"
            + Variable.COLUMN_USER_PASSWORD + " TEXT" + ")";

    private String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + Variable.TABLE_TRANSACTION + "("
            + Variable.COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Variable.COLUMN_TRANSACTION_SENT_FROM + " INTEGER,"
            + Variable.COLUMN_TRANSACTION_SENT_TO + " INTEGER,"
            + Variable.COLUMN_TRANSACTION_BALANCE + " INTEGER,"
            + "FOREIGN KEY(" + Variable.COLUMN_TRANSACTION_SENT_FROM + ") REFERENCES " + Variable.TABLE_USERS + "(" + Variable.COLUMN_USER_ACC_ID + ") ON UPDATE CASCADE ON DELETE CASCADE "
            + ")";

    private String CREATE_BILL_TABLE = "CREATE TABLE " + Variable.TABLE_BILL + "("
            + Variable.COLUMN_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Variable.COLUMN_BILL_SENT_FROM + " INTEGER,"
            + Variable.COLUMN_BILL_TYPE + " TEXT,"
            + Variable.COLUMN_BILL_NO + " INTEGER,"
            + Variable.COLUMN_BILL_AMOUNT + " INTEGER,"
            + "FOREIGN KEY(" + Variable.COLUMN_BILL_SENT_FROM + ") REFERENCES " + Variable.TABLE_USERS + "(" + Variable.COLUMN_USER_ACC_ID + ") ON UPDATE CASCADE ON DELETE CASCADE "
            + ")";

    private String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + Variable.TABLE_ACCOUNTS + "("
            + Variable.COLUMN_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Variable.COLUMN_ACCOUNT_NUMBER + " INTEGER UNIQUE,"
            + "FOREIGN KEY(" + Variable.COLUMN_ACCOUNT_NUMBER + ") REFERENCES " + Variable.TABLE_USERS + "(" + Variable.COLUMN_USER_ACC_ID + ") ON UPDATE CASCADE ON DELETE CASCADE "
            + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + Variable.TABLE_USERS;
    private String DROP_ACCOUNT_TABLE = "DROP TABLE IF EXISTS " + Variable.TABLE_ACCOUNTS;
    private String DROP_TRANSACTION_TABLE = "DROP TABLE IF EXISTS " + Variable.TABLE_TRANSACTION;
    private String DROP_BILL_TABLE = "DROP TABLE IF EXISTS " + Variable.TABLE_BILL;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);
        db.execSQL(CREATE_BILL_TABLE);

        db.execSQL("insert into " + Variable.TABLE_USERS+"("
                + Variable.COLUMN_USER_ACC_ID+","
                + Variable.COLUMN_BALANCE+","
                + Variable.COLUMN_USER_NAME+","+ Variable.COLUMN_USER_PHONE_NUMBER+","
                + Variable.COLUMN_USER_EMAIL+","+ Variable.COLUMN_USER_PASSWORD
                + ")" + " values(123456, 50000, 'Aporajita', '123456', 'ap@gmail.com', '0011')");

        db.execSQL("insert into " + Variable.TABLE_USERS+"("
                + Variable.COLUMN_USER_ACC_ID+","
                + Variable.COLUMN_BALANCE+","
                + Variable.COLUMN_USER_NAME+","+ Variable.COLUMN_USER_PHONE_NUMBER+","
                + Variable.COLUMN_USER_EMAIL+","+ Variable.COLUMN_USER_PASSWORD
                + ")" + " values(111111, 80000, 'babul', '123456', 'b@gmail.com', '0011')");

    }

    public User getUser(String email) {
        String[] columns = {
                Variable.COLUMN_USER_ID ,
                Variable.COLUMN_USER_ACC_ID ,
                Variable.COLUMN_BALANCE ,
                Variable.COLUMN_USER_NAME ,
                Variable.COLUMN_USER_PHONE_NUMBER ,
                Variable.COLUMN_USER_EMAIL ,
                Variable.COLUMN_USER_PASSWORD
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = Variable.COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};
        User user = new User();

        Cursor cursor = db.query(Variable.TABLE_USERS, columns, selection, selectionArgs,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(Variable.COLUMN_USER_ID))));
            user.setaccountID(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(Variable.COLUMN_USER_ACC_ID))));
            user.setName(cursor.getString(cursor.getColumnIndexOrThrow(Variable.COLUMN_USER_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(Variable.COLUMN_USER_EMAIL)));
            user.setBalance(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(Variable.COLUMN_BALANCE))));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(Variable.COLUMN_USER_PASSWORD)));
        }
        cursor.close();
        return user;
    }


    public void transactions(int sent_from, int sent_to, int amount) {
        int credit_sent = 0;
        int credit_received = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Variable.COLUMN_TRANSACTION_SENT_FROM, sent_from);
        values.put(Variable.COLUMN_TRANSACTION_SENT_TO, sent_to);
        values.put(Variable.COLUMN_TRANSACTION_BALANCE, amount);


        db.insert(Variable.TABLE_TRANSACTION, null, values);

        String[] columns = {Variable.COLUMN_BALANCE};

        String selection = Variable.COLUMN_USER_ACC_ID + " = ?";
        String[] selectionArgs_sent = {String.valueOf(sent_from)};
        String[] selectionArgs_recieved = {String.valueOf(sent_to)};
        User user = new User();
        Cursor cursor_from = db.query(Variable.TABLE_USERS, columns, selection, selectionArgs_sent,
                null,
                null,
                null);

        Cursor cursor_to = db.query(Variable.TABLE_USERS, columns, selection, selectionArgs_recieved,
                null,
                null,
                null);


        if (cursor_from.moveToFirst()) {

            credit_sent = Integer.parseInt(cursor_from.getString(cursor_from.getColumnIndexOrThrow(Variable.COLUMN_BALANCE)));
        }
        cursor_from.close();

        if (cursor_to.moveToFirst()) {

            credit_received = Integer.parseInt(cursor_to.getString(cursor_to.getColumnIndexOrThrow(Variable.COLUMN_BALANCE)));
        }
        cursor_to.close();

        int credit_updated_sent = credit_sent-amount;
        int credit_updated_received = credit_received+amount;

        ContentValues value_from = new ContentValues();
        value_from.put(Variable.COLUMN_BALANCE, credit_updated_sent);
        ContentValues value_to = new ContentValues();
        value_to.put(Variable.COLUMN_BALANCE, credit_updated_received);

        db.update(Variable.TABLE_USERS, value_from, Variable.COLUMN_USER_ACC_ID + " = ?",
                new String[]{String.valueOf(sent_from)});

        db.update(Variable.TABLE_USERS, value_to, Variable.COLUMN_USER_ACC_ID + " = ?",
                new String[]{String.valueOf(sent_to)});
        db.close();
    }

    public void bills(int sent_from, String billtype, int billingno, int amount) {
        int credit_sent = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Variable.COLUMN_BILL_SENT_FROM, sent_from);
        values.put(Variable.COLUMN_BILL_TYPE, billtype);
        values.put(Variable.COLUMN_BILL_NO, billingno);
        values.put(Variable.COLUMN_BILL_AMOUNT, amount);

        db.insert(Variable.TABLE_BILL, null, values);

        String[] columns = {Variable.COLUMN_BALANCE};

        String selection = Variable.COLUMN_USER_ACC_ID + " = ?";
        String[] selectionArgs_sent = {String.valueOf(sent_from)};

        Cursor cursor_from = db.query(Variable.TABLE_USERS, columns, selection, selectionArgs_sent,
                null,
                null,
                null);

        if (cursor_from.moveToFirst()) {

            credit_sent = Integer.parseInt(cursor_from.getString(cursor_from.getColumnIndexOrThrow(Variable.COLUMN_BALANCE)));
        }
        cursor_from.close();


        int credit_updated = credit_sent-amount;

        ContentValues value_from = new ContentValues();
        value_from.put(Variable.COLUMN_BALANCE, credit_updated);

        db.update(Variable.TABLE_USERS, value_from, Variable.COLUMN_USER_ACC_ID + " = ?",
                new String[]{String.valueOf(sent_from)});

        db.close();
    }

    public boolean checkUser(String email) {
        // array of columns to fetch
        String[] columns = {
                Variable.COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = Variable.COLUMN_USER_EMAIL + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(Variable.TABLE_USERS, columns, selection, selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String email, String password) {

        String[] columns = {
                Variable.COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = Variable.COLUMN_USER_EMAIL + " = ?" + " AND " + Variable.COLUMN_USER_PASSWORD + " = ?";

        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(Variable.TABLE_USERS, columns, selection, selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_ACCOUNT_TABLE);
        db.execSQL(DROP_TRANSACTION_TABLE);
        db.execSQL(DROP_BILL_TABLE);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

}
