package com.example.bankApp.model;

public class Bill {
    private int id;
    private int sent;
    private String billtype;
    private int billingno;
    private int amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSent() {
        return sent;
    }

    public void setSent(int sent) {
        this.sent = sent;
    }

    public String getBilltype() {
        return billtype;
    }

    public void setBilltype(String billtype) {
        this.billtype = billtype;
    }

    public int getBillingno() {
        return billingno;
    }

    public void setBillingno(int billingno) {
        this.billingno = billingno;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
