package SuperAdminServices;

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

@WebServlet("/DeactiveInput")
public class DeactiveAdminInput extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        
        // Session validation
        HttpSession session = req.getSession(false);
        
        if (session != null && session.getAttribute("user") != null) {
            
            String idParam = req.getParameter("id");
            
            if (idParam != null && !idParam.isEmpty()) {
                try {
                    int id = Integer.parseInt(idParam);
                    
                    // Aapki service class call kar rahe hain
                    deactiveAdmin service = new deactiveAdmin();
                    boolean success = service.Deactive(id);
                    
                    if (success) {
                        out.print("<div style='text-align:center; margin-top:50px; font-family:Arial;'>");
                        out.print("<h2 style='color:orange;'>User ID " + id + " has been Deactivated!</h2>");
                        out.print("<a href='dashboard.jsp' style='text-decoration:none; color:blue;'>Go Back to Dashboard</a>");
                        out.print("</div>");
                    } else {
                        out.print("<h3 style='color:red;'>Failed to deactivate. ID might not be valid.</h3>");
                        out.print("<a href='dashboard.jsp'>Try Again</a>");
                    }
                    
                } catch (NumberFormatException e) {
                    out.print("<h3 style='color:red;'>Error: ID must be a number!</h3>");
                } catch (SQLException e) {
                    e.printStackTrace();
                    out.print("<h3 style='color:red;'>Database Error: " + e.getMessage() + "</h3>");
                }
            } else {
                out.print("<h3>Please enter a User ID to deactivate.</h3>");
            }
        } else {
            res.sendRedirect("index.jsp");
        }
    }
}