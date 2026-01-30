package UserService;

import DAO.BookAddService;
import DAO.BookBuyingService;
import Enums.Catagory;
import Model.CustomerDetails;

import java.sql.SQLException;

public class BorrowBook {
    BookBuyingService a = new BookBuyingService();

    public boolean Borrow(CustomerDetails customer,int bookquantity) throws SQLException {
//
        return a.buybook(customer,bookquantity);


    }

}
