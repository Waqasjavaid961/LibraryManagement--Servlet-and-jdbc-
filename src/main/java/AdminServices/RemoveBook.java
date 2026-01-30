package AdminServices;

import DAO.BookAddService;

import java.sql.SQLException;

public class RemoveBook {
    BookAddService a=new BookAddService();
    public boolean removeBook(int id)throws SQLException {
        return a.removeBook(id);
    }
}
