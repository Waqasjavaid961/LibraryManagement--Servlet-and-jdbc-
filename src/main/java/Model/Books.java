package Model;

import Enums.Catagory;

import java.util.ArrayList;
import java.util.List;

public class Books {
    private Catagory catagory;
    private int id;
    private String bookname;
    private long ISBN;
    private String author_name;
    private String edition;
    private String language;
    private int quantity;
    private long price;
    private boolean status;

    public Books(Catagory catagory, String bookname, long ISBN, String author_name, String edition, String language, int quantity, long price) {
        this.catagory = catagory;
        this.bookname = bookname;
        this.ISBN = ISBN;
        this.author_name = author_name;
        this.edition = edition;
        this.language = language;
        this.quantity = quantity;
        this.price = price;
    }

    public Books() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setCatagory(Catagory catagory) {
        this.catagory = catagory;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public void setISBN(long ISBN) {
        this.ISBN = ISBN;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(long price) {
        this.price = price;
    }


    public boolean isStatus() {
        return status;
    }

    public String getBookname() {
        return bookname;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public Catagory getCatagory() {
        return catagory;
    }

    public String getEdition() {
        return edition;
    }

    public String getLanguage() {
        return language;
    }

    public int getId() {
        return id;
    }

    public long getISBN() {
        return ISBN;
    }

    public long getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}


