package UserService;

import DAO.BookBuyingService;

import java.sql.SQLException;

public class ReturnBook {
     BookBuyingService b=new BookBuyingService();
    public boolean returnBook(int returnQty, int bookId, int userId)throws SQLException {
       return b.returnBook(returnQty,bookId,userId);


    }
}
