package AdminServices;

import DAO.BookAddService;
import Model.Books;

import java.sql.SQLException;
import java.util.Optional;

public class AddBooks {
    BookAddService a=new BookAddService();
    public Optional<Integer> BookAddProcess(Books book)throws SQLException {
        return a.Addbook(book);

    }
}
