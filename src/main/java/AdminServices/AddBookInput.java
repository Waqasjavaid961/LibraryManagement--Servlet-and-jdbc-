package AdminServices;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;

import Model.Books;
import Enums.Catagory; // Aapki Category Enum
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AddBookInput")
public class AddBookInput extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession(false);

        // Security Check: Sirf logged in user hi access kare
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("index.jsp");
            return;
        }

        try {
            // 1. Console logic ke mutabik inputs lena
            String catStr = req.getParameter("catagory");
            String bookName = req.getParameter("bookname");
            long isbn = Long.parseLong(req.getParameter("isbn"));
            String author = req.getParameter("authorname");
            String edition = req.getParameter("edition");
            String language = req.getParameter("language");
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            long price = Long.parseLong(req.getParameter("price"));

            // 2. Category Enum Conversion
            Catagory cat = Catagory.fromString(catStr);
            if (cat == null) {
                out.print("<h3 style='color:red;'>CATAGORY NOT MATCH</h3>");
                out.print("<a href='dashboard.jsp'>Back</a>");
                return;
            }

            // 3. Books Object banana (Aapke constructor ke sequence ke mutabik)
            Books book = new Books(cat, bookName, isbn, author, edition, language, quantity, price);

            // 4. Service call karna
            AddBooks a = new AddBooks();
            Optional<Integer> resId = a.BookAddProcess(book);

            // 5. Response handling
            out.print("<div style='text-align:center; font-family:Arial; margin-top:50px;'>");
            if (resId.isPresent()) {
                // book.getId() shayad tabhi update ho jab DB se ID wapas aaye
                out.print("<h2 style='color:green;'>Book Registered Successfully!</h2>");
                out.print("<p>Generated ID: " + resId.get() + "</p>");
            } else {
                out.print("<h2 style='color:red;'>Book Not Registered</h2>");
            }
            out.print("<br><a href='dashboard.jsp' style='padding:10px; background:#0d6efd; color:white; text-decoration:none; border-radius:5px;'>Back to Dashboard</a>");
            out.print("</div>");

        } catch (NumberFormatException e) {
            out.print("<h3 style='color:red;'>Input Error: Please enter valid numbers for ISBN, Price, and Quantity.</h3>");
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("<h3 style='color:red;'>Database Error: " + e.getMessage() + "</h3>");
        }
    }
}
