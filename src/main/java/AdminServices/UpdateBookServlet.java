package AdminServices;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;

import Model.Books;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UpdateBookServlet")
public class UpdateBookServlet extends HttpServlet {

    // GET method: Book ki details fetch karke update form dikhane ke liye
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        UpdateBookDetails service = new UpdateBookDetails();
        
        try {
            Optional<Books> optBook = service.fetchProcessing(id);
            if(optBook.isPresent()) {
                req.setAttribute("bookToUpdate", optBook.get());
                // Wapas dashboard par bhej rahe hain jahan form khulega
                req.getRequestDispatcher("dashboard.jsp?action=edit").forward(req, res);
            } else {
                res.getWriter().print("Book not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // POST method: Final update save karne ke liye
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            // Saara data pakadna (Jaise aapne console mein switch-case mein kiya tha)
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("bookname");
            String author = req.getParameter("authorname");
            String edition = req.getParameter("edition");
            String language = req.getParameter("language");
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            long price = Long.parseLong(req.getParameter("price"));

            // Pehle existing book fetch karein
            UpdateBookDetails service = new UpdateBookDetails();
            Optional<Books> opt = service.fetchProcessing(id);
            
            if(opt.isPresent()) {
                Books book = opt.get();
                // Naya data set karein
                book.setBookname(name);
                book.setAuthor_name(author);
                book.setEdition(edition);
                book.setLanguage(language);
                book.setQuantity(quantity);
                book.setPrice(price);

                boolean success = service.updateProcessing(book);
                if(success) {
                    res.sendRedirect("dashboard.jsp?msg=UpdateSuccess");
                } else {
                    res.sendRedirect("dashboard.jsp?msg=UpdateFailed");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().print("Error: " + e.getMessage());
        }
    }
}
