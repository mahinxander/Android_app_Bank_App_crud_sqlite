package com.example.bankApp;

public class Variable {
    // User Table Columns names
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_ACC_ID = "accountID";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PHONE_NUMBER = "phoneNumber";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_BALANCE = "balance";


    //column names of subject table
    public static final String TABLE_ACCOUNTS = "accounts";
    public static final String COLUMN_ACCOUNT_ID = "accountID";
//    public static final String COLUMN_USER_ID_ACC = "userID";
    public static final String COLUMN_ACCOUNT_NUMBER = "accountNumber";
//    public static final String COLUMN_BALANCE = "balance";

    //column names of Transaction table
    public static final String TABLE_TRANSACTION = "transactions";
    public static final String COLUMN_TRANSACTION_ID = "id";
    public static final String COLUMN_TRANSACTION_SENT_FROM = "sent";
    public static final String COLUMN_TRANSACTION_SENT_TO = "received";
    public static final String COLUMN_TRANSACTION_BALANCE = "amount";

    //Column names of Bill Table
    public static final String TABLE_BILL = "bills";
    public static final String COLUMN_BILL_ID = "id";
    public static final String COLUMN_BILL_SENT_FROM = "sent";
    public static final String COLUMN_BILL_TYPE = "Billtype";
    public static final String COLUMN_BILL_NO = "billingno";
    public static final String COLUMN_BILL_AMOUNT = "amount";

}
