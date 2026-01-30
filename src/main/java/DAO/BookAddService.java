package DAO;

import Enums.Catagory;
import Model.Books;
import com.mysql.cj.protocol.x.ReusableOutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookAddService {
    private Connection getBookConnection()throws SQLException{
        return DBConnection.getConnection();
    }
    public boolean checkduplicate( Catagory catagory,long isbn)throws SQLException{
        String sql="select 1 from books where catagory = ? and ISBN = ?";
        try(Connection con=getBookConnection(); PreparedStatement pr=con.prepareStatement(sql)){
            pr.setString(1,catagory.name());
            pr.setLong(2,isbn);
            try (ResultSet sr = pr.executeQuery()) {
                return sr.next(); // true if exists
            }
            
        }catch (SQLException e){
            System.out.println("unkown database error"+e.getMessage());
            return false;
        }
    }
    public Optional<Integer> Addbook(Books book)throws SQLException{
        if(checkduplicate(book.getCatagory(),book.getISBN())){
            return Optional.empty();
        }
        String sql="insert into books (catagory,book_name,ISBN,book_author,edition,language,quantity,price,status)values(?,?,?,?,?,?,?,?,?)";
        try(Connection con=getBookConnection();PreparedStatement pr=con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)){
            Catagory catagory=Catagory.fromString(book.getCatagory().name());
            if(catagory==null){
                return Optional.empty();
            }

            pr.setString(1,catagory.name());
            pr.setString(2,book.getBookname());
            pr.setLong(3,book.getISBN());
            pr.setString(4,book.getAuthor_name());
            pr.setString(5,book.getEdition());
            pr.setString(6,book.getLanguage());
            pr.setInt(7,book.getQuantity());
            pr.setLong(8,book.getPrice());
            pr.setBoolean(9,true);
            int rows=pr.executeUpdate();
            if(rows>0){
                try(ResultSet sr=pr.getGeneratedKeys()){
                    if(sr.next()){
                        int generatedkey=sr.getInt(1);
                        book.setId(generatedkey);
                    }
                }
                return Optional.of(book.getId());
            }
            return Optional.empty();
        }catch (SQLException e){
            System.out.println("unkown database error"+e.getMessage());
            return Optional.empty();
        }
    }
    public boolean checkbyId(int id)throws SQLException{
        String sql="select * from books where id = ? and status = true ";
        try(Connection con=getBookConnection();PreparedStatement pr=con.prepareStatement(sql)){
            pr.setInt(1,id);
            ResultSet sr=pr.executeQuery();
            if(sr.next()){
                return true;
            }
            return false;
        }catch (SQLException e){
            System.out.println("Unkown database error"+e.getMessage());
            return false;
        }
    }
    public Optional<Books>fetchBooks(int id)throws SQLException{
        if(!checkbyId(id)){
            return Optional.empty();
        }
        String sql="select * from books where id =? and status = true";
        try(Connection con=getBookConnection();PreparedStatement pr=con.prepareStatement(sql)){
            pr.setInt(1,id);
            try(ResultSet sr=pr.executeQuery()) {
                while (sr.next()) {
                    Books b = new Books();
                    b.setBookname(sr.getString("book_name"));
                    b.setISBN(sr.getLong("ISBN"));
                    b.setAuthor_name(sr.getString("book_author"));
                    b.setEdition(sr.getString("edition"));
                    b.setLanguage(sr.getString("language"));
                    b.setQuantity(sr.getInt("quantity"));
                    b.setPrice(sr.getLong("price"));
                    b.setId(sr.getInt("id"));
                    return Optional.of(b);
                }
                return Optional.empty();
            }
        }catch (SQLException e){
            System.out.println("unkown database error"+e.getMessage());
            return Optional.empty();
        }
    }
    public boolean updateBookDetails(Books book)throws SQLException{
        String sql="update books set book_name = ? ,  book_author = ?,  edition = ?,  language = ?,  quantity = ?, price = ? where id = ?";
        try(Connection con=getBookConnection();PreparedStatement pr=con.prepareStatement(sql)){
            pr.setString(1,book.getBookname());
            pr.setString(2,book.getAuthor_name());
            pr.setString(3,book.getEdition());
            pr.setString(4,book.getLanguage());
            pr.setInt(5,book.getQuantity());
            pr.setLong(6,book.getPrice());
            pr.setInt(7,book.getId());
            int rows=pr.executeUpdate();
            if(rows>0){
                return true;
            }
            return false;
        }catch (SQLException e){
            System.out.println("unkonw database error"+e.getMessage());
            return false;
        }
    }
    public boolean removeBook(int id)throws SQLException {
        if (!checkbyId(id)) {
            return false;
        }
        String sql = "update books set status = false where id = ?";
        try (Connection con = getBookConnection(); PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setInt(1, id);
            int rows = pr.executeUpdate();
            if (rows > 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("unkown database error" + e.getMessage());
            return false;
        }
    }
    public List<Books> booksList() throws SQLException {

        List<Books> books = new ArrayList<>();

        String sql = "SELECT * FROM books WHERE status = true";

        try (Connection con = getBookConnection();
             PreparedStatement pr = con.prepareStatement(sql);
             ResultSet sr = pr.executeQuery()) {

            while (sr.next()) {

                Books b = new Books();
                String catagory=sr.getString("catagory");
                b.setCatagory(Catagory.fromString(catagory));
                b.setId(sr.getInt("id"));
                b.setBookname(sr.getString("book_name"));
                b.setISBN(sr.getLong("ISBN"));
                b.setAuthor_name(sr.getString("book_author"));
                b.setEdition(sr.getString("edition"));
                b.setLanguage(sr.getString("language"));
                b.setQuantity(sr.getInt("quantity"));
                b.setPrice(sr.getLong("price"));
                b.setStatus(sr.getBoolean("status"));

                books.add(b);
            }
        } catch (SQLException e) {
            System.out.println("Unknown database error: " + e.getMessage());
        }

        return books;
    }

    public List<Books> booksListCatagorywise(String catagory) throws SQLException {

        List<Books> books = new ArrayList<>();

        String sql = "SELECT * FROM books WHERE status = true and catagory = ?";

        try (Connection con = getBookConnection();
             PreparedStatement pr = con.prepareStatement(sql))
             {
                 Catagory catagory1=Catagory.fromString(catagory);
                 if(catagory1==null){
                     return null;
                 }

                 pr.setString(1,catagory1.name());
                 try(ResultSet sr=pr.executeQuery()) {
                     while (sr.next()) {


                         Books b = new Books();
                         String catagory2 = sr.getString("catagory");
                         b.setCatagory(Catagory.fromString(catagory));
                         b.setId(sr.getInt("id"));
                         b.setBookname(sr.getString("book_name"));
                         b.setISBN(sr.getLong("ISBN"));
                         b.setAuthor_name(sr.getString("book_author"));
                         b.setEdition(sr.getString("edition"));
                         b.setLanguage(sr.getString("language"));
                         b.setQuantity(sr.getInt("quantity"));
                         b.setPrice(sr.getLong("price"));
                         b.setStatus(sr.getBoolean("status"));

                         books.add(b);
                     }
                 }
        } catch (SQLException e) {
            System.out.println("Unknown database error: " + e.getMessage());
        }

        return books;
    }

}
