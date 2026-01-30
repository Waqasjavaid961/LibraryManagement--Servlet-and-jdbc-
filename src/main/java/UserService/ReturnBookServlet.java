package UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import Model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ReturnBookInput")
public class ReturnBookServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("index.jsp");
            return;
        }

        Users user = (Users) session.getAttribute("user");

        try {
            int bookId = Integer.parseInt(req.getParameter("bookId"));
            int returnQty = Integer.parseInt(req.getParameter("quantity"));
            int userId = user.getId();

            ReturnBook service = new ReturnBook();
            // STEP 4 of your console logic: Call Service
            boolean success = service.returnBook(returnQty, bookId, userId);

            out.print("<div style='text-align:center; margin-top:50px; font-family:Arial;'>");
            if (success) {
                out.print("<h2 style='color:green;'>✅ Book Returned Successfully!</h2>");
            } else {
                out.print("<h2 style='color:red;'>❌ Return Failed. Check quantity or ID.</h2>");
            }
            out.print("<a href='dashboard.jsp'>Back to Dashboard</a>");
            out.print("</div>");

        } catch (Exception e) {
            out.print("Error: " + e.getMessage());
        }
    }
}