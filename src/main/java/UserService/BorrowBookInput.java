package UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import Model.Books;
import Model.CustomerDetails;
import Model.Users;
import Enums.Catagory;
import DAO.DAOServices; // Assume searching logic is here
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/BorrowBookInput")
public class BorrowBookInput extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession(false);

        // Security Check: Only logged-in users can borrow
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("index.jsp");
            return;
        }

        Users currentUser = (Users) session.getAttribute("user");

        try {
            // 1. Get data from form
            String catStr = req.getParameter("catagory");
            String bookName = req.getParameter("bookname");
            int requestedQty = Integer.parseInt(req.getParameter("quantity"));
            
            // Console logic: Check Category
            Catagory cat = Catagory.fromString(catStr);
            if (cat == null) {
                out.print("<h3 style='color:red;'>INVALID CATEGORY!</h3>");
                return;
            }

            // 2. Business Logic: Get book details to check stock and price
            // (Shayad aapke paas koi Search Service ho, main temporary object use kar raha hoon logic dikhane ke liye)
            // Yahan humein book ki current price aur quantity chahiye database se
            
            // Assuming you have a way to fetch the book object:
            // Books b = s.check(cat, bookName).get(0); 
            
            // For now, let's process the Borrow via your Service
            BorrowBook service = new BorrowBook();
            
            // NOTE: Console mein aap total calculate kar rahe the. 
            // Hum form se hidden fields mein price mangwa sakte hain ya DB se fetch kar sakte hain.
            long pricePerUnit = Long.parseLong(req.getParameter("price")); 
            int availableQty = Integer.parseInt(req.getParameter("availableQty"));
            int bookId = Integer.parseInt(req.getParameter("bookId"));

            if (requestedQty <= 0) {
                out.print("<h3>Please enter a valid quantity.</h3>");
            } else if (requestedQty > availableQty) {
                out.print("<h3 style='color:red;'>Quantity out of range! Available: " + availableQty + "</h3>");
            } else {
                long total = pricePerUnit * requestedQty;
                
                // 3. Create CustomerDetails Object (As per your console logic)
                CustomerDetails c = new CustomerDetails(catStr, bookName, requestedQty, pricePerUnit, total);
                c.setUser_id(currentUser.getId());
                c.setBook_id(bookId);

                // 4. Call Borrow Method
                boolean success = service.Borrow(c, availableQty);

                out.print("<div style='text-align:center; font-family:Arial; margin-top:50px;'>");
                if (success) {
                    out.print("<h2 style='color:green;'>🎉 Book Borrowed Successfully!</h2>");
                    out.print("<p>Product Price: " + pricePerUnit + "</p>");
                    out.print("<p>Total Cost: <strong>" + total + "</strong></p>");
                } else {
                    out.print("<h2 style='color:red;'>Transaction Failed!</h2>");
                }
                out.print("<br><a href='dashboard.jsp'>Back to Dashboard</a>");
                out.print("</div>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("Error: " + e.getMessage());
        }
    }
}