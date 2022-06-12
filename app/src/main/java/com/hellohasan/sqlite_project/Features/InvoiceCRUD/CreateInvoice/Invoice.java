package com.hellohasan.sqlite_project.Features.InvoiceCRUD.CreateInvoice;

public class Invoice {
    private long id;
    private String name;
    private String createdDate;
    private String phoneNumber;
    private String address;
    private double  amount;



    public Invoice(int id, String name, String createdDate, String phoneNumber, String address, double amount) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double  getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
