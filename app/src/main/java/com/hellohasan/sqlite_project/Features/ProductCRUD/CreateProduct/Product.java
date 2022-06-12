package com.hellohasan.sqlite_project.Features.ProductCRUD.CreateProduct;

public class Product {
    private long id;
    private String name;
    private int code;
    private int availability;
    private double price;

    public Product(long id, String name, int code, int availability, double price) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.availability = availability;
        this.price = price;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

