package com.example.bookstore.model;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String isbn;
    private String title;
    private double price;
    private int inventorySold;
    private int inventorySupplied;
    private List<Author> authors;

    public Book() {
        this.authors = new ArrayList<>();
    }

    public Book(String isbn, String title, double price, int inventorySold, int inventorySupplied) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.inventorySold = inventorySold;
        this.inventorySupplied = inventorySupplied;
        this.authors = new ArrayList<>();
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInventorySold() {
        return inventorySold;
    }

    public void setInventorySold(int inventorySold) {
        this.inventorySold = inventorySold;
    }

    public int getInventorySupplied() {
        return inventorySupplied;
    }

    public void setInventorySupplied(int inventorySupplied) {
        this.inventorySupplied = inventorySupplied;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
