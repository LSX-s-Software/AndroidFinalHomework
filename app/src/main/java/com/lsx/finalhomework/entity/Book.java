package com.lsx.finalhomework.entity;

public class Book {

    public enum Category {
        COMPUTER,
        NOVEL,
        DICTIONARY
    }

    int id;
    Category category;
    String name;
    String imgUrl;
    String author;
    String ISBN;
    String description;
    double price;

    public Book() { }

    public Book(int id, Category category, String name, String imgUrl, String author, String ISBN, String description, double price) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.imgUrl = imgUrl;
        this.author = author;
        this.ISBN = ISBN;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
