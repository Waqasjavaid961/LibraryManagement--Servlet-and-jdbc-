package Model;

public class CustomerDetails {
    private int user_id;
    private int  book_id;
    private String catagory;
    private String bookname;
    private int quantity;
    private long price;
    private long total;
    public CustomerDetails(String catagory,String bookname,int quantity,long price,long total){
        this.catagory=catagory;
        this.bookname=bookname;
        this.quantity=quantity;
        this.price=price;
        this.total=total;
    }
    public CustomerDetails(){}
    public int getQuantity(){
        return quantity;
    }
    public long getPrice(){
        return price;
    }
    public long getTotal(){
        return total;
    }

    public void setUser_id(int user_id){
        this.user_id=user_id;
    }
    public void setBook_id(int book_id){
        this.book_id=book_id;
    }
    public String getBookname(){
        return bookname;
    }
    public String getCatagory(){
        return catagory;
    }
    public int getUser_id(){
        return user_id;
    }
    public int getBook_id(){
        return book_id;
    }

}

