package DAO;

import Enums.Catagory;
import Model.Books;
import Model.CustomerDetails;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import javax.swing.text.html.Option;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookBuyingService {
    private Connection getCustomerConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    public List<Books> check(Catagory catagory, String bookname) throws SQLException {
        String sql = "SELECT * FROM books where catagory = ? and book_name = ? ";
        List<Books> books = new ArrayList<>();
        try (Connection con = getCustomerConnection(); PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, catagory.name());
            pr.setString(2, bookname);
           try( ResultSet sr = pr.executeQuery()){
            while (sr.next()) {

                Books b = new Books();
                b.setId(sr.getInt("id"));
                b.setBookname(sr.getString("book_name"));
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

    public boolean buybook(CustomerDetails customer, int quantity) throws SQLException {
        String sql = "insert into customerdetail (user_id,book_id,catagory,book_name,quantity,price,total) values(?,?,?,?,?,?,?)";
        String sqll = "update books set quantity = quantity - ? where id = ? and quantity >= ?";

        Connection con = null;
        try {
            con = getCustomerConnection(); // Connection try ke andar
            if (con == null) return false;
            
            con.setAutoCommit(false);

            try (PreparedStatement pr = con.prepareStatement(sql)) {
                pr.setInt(1, customer.getUser_id());
                pr.setInt(2, customer.getBook_id());
                pr.setString(3, customer.getCatagory());
                pr.setString(4, customer.getBookname());
                pr.setInt(5, customer.getQuantity());
                pr.setLong(6, customer.getPrice());
                pr.setLong(7, customer.getTotal());
                pr.executeUpdate();

                try (PreparedStatement pr2 = con.prepareStatement(sqll)) {
                    pr2.setInt(1, customer.getQuantity());
                    pr2.setInt(2, customer.getBook_id());
                    pr2.setInt(3, customer.getQuantity()); // Use customer's requested qty

                    int rows = pr2.executeUpdate();
                    if (rows > 0) {
                        con.commit();
                        return true;
                    } else {
                        con.rollback();
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            if (con != null) con.rollback();
            return false;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close(); // Proper closing
            }
        }
    }
    public List<CustomerDetails> show(int id) throws SQLException {

        List<CustomerDetails> det = new ArrayList<>();
        String sql = "SELECT * FROM customerdetail WHERE user_id = ?";

        try (Connection con = getCustomerConnection();
             PreparedStatement pr = con.prepareStatement(sql)) {

            // ✅ STEP 1: parameter set
            pr.setInt(1, id);

            // ✅ STEP 2: now execute
            try (ResultSet sr = pr.executeQuery()) {
                while (sr.next()) {
                    CustomerDetails b = new CustomerDetails(
                            sr.getString("catagory"),
                            sr.getString("book_name"),
                            sr.getInt("quantity"),
                            sr.getLong("price"),
                            sr.getLong("total")
                    );
                    b.setUser_id(sr.getInt("user_id"));
                    b.setBook_id(sr.getInt("book_id"));

                    det.add(b);
                }
            }

            return det;

        } catch (SQLException e) {
            System.out.println("Unknown database error: " + e.getMessage());
            return new ArrayList<>(); // ❗ null return mat karo
        }
    }

    public boolean isBookOwnedByUser(int userId, int bookId) throws SQLException {

        String sql = "SELECT 1 FROM customerdetail WHERE user_id = ? AND book_id = ?";

        try (Connection con = getCustomerConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, bookId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // agar record mila → true
            }
        }
    }

    public boolean returnBook(int returnQty, int bookId, int userId) throws SQLException {
        // Queries
        String sqlBooks = "UPDATE books SET quantity = quantity + ? WHERE id = ?";
        String sqlCustomer = "UPDATE customerdetail SET quantity = quantity - ?, total = (quantity - ?) * price WHERE book_id = ? AND user_id = ?";
        String sqlCheck = "SELECT quantity FROM customerdetail WHERE book_id = ? AND user_id = ?";

        Connection con = null;
        try {
            con = getCustomerConnection();
            con.setAutoCommit(false); // 1. Transaction Start

            // STEP 1: Verification (Kiya user ke paas utni books hain?)
            int currentOwned = 0;
            try (PreparedStatement prCheck = con.prepareStatement(sqlCheck)) {
                prCheck.setInt(1, bookId);
                prCheck.setInt(2, userId);
                ResultSet rs = prCheck.executeQuery();
                if (rs.next()) {
                    currentOwned = rs.getInt("quantity");
                }
            }

            if (returnQty > currentOwned || returnQty <= 0) {
                System.out.println("Invalid Quantity! You own: " + currentOwned);
                return false;
            }

            // STEP 2: Books Table Update (Stock wapas+)
            try (PreparedStatement pr1 = con.prepareStatement(sqlBooks)) {
                pr1.setInt(1, returnQty);
                pr1.setInt(2, bookId);
                pr1.executeUpdate();
            }

            // STEP 3: Customer Table Update (Quantity aur Total dono update)
            try (PreparedStatement pr2 = con.prepareStatement(sqlCustomer)) {
                pr2.setInt(1, returnQty);    // quantity = quantity - ?
                pr2.setInt(2, returnQty);    // total update ke liye calculation mein help
                pr2.setInt(3, bookId);
                pr2.setInt(4, userId);
                pr2.executeUpdate();
            }

            // STEP 4: Final Commit
            con.commit();
            System.out.println("Return Successful!");
            return true;

        } catch (SQLException e) {
            if (con != null) con.rollback();
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }
}
