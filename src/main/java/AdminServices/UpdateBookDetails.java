package AdminServices;

import DAO.BookAddService;
import Model.Books;

import java.sql.SQLException;
import java.util.Optional;

public class UpdateBookDetails {
    BookAddService b=new BookAddService();
    public Optional<Books>fetchProcessing(int id)throws SQLException {
        return b.fetchBooks(id);
    }
    public boolean updateProcessing(Books book)throws SQLException{
      return   b.updateBookDetails(book);
    }
}