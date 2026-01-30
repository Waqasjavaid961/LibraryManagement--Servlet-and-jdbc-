package AdminServices;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/RemoveBookInput")
public class RemoveBookInput extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession(false);

        // Security: Check if user is logged in
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("index.jsp");
            return;
        }

        String idStr = req.getParameter("id");

        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                
                // Aapki Service Class call karna
                RemoveBook service = new RemoveBook();
                boolean success = service.removeBook(id);

                out.print("<div style='text-align:center; font-family:Arial; margin-top:50px;'>");
                if (success) {
                    out.print("<h2 style='color:red;'>🗑️ Book Deleted Successfully!</h2>");
                    out.print("<p>Book ID " + id + " has been removed from the system.</p>");
                } else {
                    out.print("<h2 style='color:orange;'>Book Not Found!</h2>");
                    out.print("<p>No book exists with ID: " + id + "</p>");
                }
                out.print("<br><a href='dashboard.jsp' style='padding:10px; background:#0d6efd; color:white; text-decoration:none; border-radius:5px;'>Back to Dashboard</a>");
                out.print("</div>");

            } catch (NumberFormatException e) {
                out.print("<h3 style='color:red;'>Invalid ID! Please enter a numeric Book ID.</h3>");
            } catch (SQLException e) {
                e.printStackTrace();
                out.print("<h3 style='color:red;'>Database Error: " + e.getMessage() + "</h3>");
            }
        } else {
            out.print("<h3>Please provide a Book ID.</h3>");
        }
    }
}
