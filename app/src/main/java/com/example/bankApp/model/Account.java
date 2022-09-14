package com.example.bankApp.model;

public class Account {
    private int accountID;
//    private int userID;
    private int accountNumber;
//    private int balance;

//    public Account(int accountID, int userID, int accountNumber, int balance) {
//        this.accountID = accountID;
//        this.accountNumber = accountNumber;
////        this.userID = userID;
//        this.balance = balance;
//
//    }

    public int getaccountID() {
        return accountID;
    }

    public void setaccountID(int accountID) {
        this.accountID = accountID;
    }

//    public int getuserID() {
//        return userID;
//    }
//
//    public void setuserID(int userID) {
//        this.userID = userID;
//    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

//    public int getBalance() {
//        return balance;
//    }

//    public void setBalance(int balance) {
//        this.balance = balance;
//    }

}
