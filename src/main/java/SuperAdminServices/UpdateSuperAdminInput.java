package SuperAdminServices;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import Model.Users;
import Enums.Roles;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UpdateSuperAdmin")
public class UpdateSuperAdminInput extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        
        HttpSession session = req.getSession(false);
        
        // Check if user is logged in
        if (session != null && session.getAttribute("user") != null) {
            Users user = (Users) session.getAttribute("user");
            
            // Frontend form se data uthana
            String oldPass = req.getParameter("oldpassword");
            String newGmail = req.getParameter("gmail");
            String newPass = req.getParameter("password");
            Roles currentRole = user.getRole(); // Session se role le rahe hain

            try {
                UpdateSuperAdmin service = new UpdateSuperAdmin();
                
                // Service class ko call karna
                boolean success = service.UpdateSuperAdminprocess(oldPass, newGmail, newPass, currentRole);
                
                if (success) {
                    // Update ke baad session data refresh karna zaroori hai
                    user.setGmail(newGmail);
                    user.setPassword(newPass);
                    session.setAttribute("user", user);
                    
                    out.print("<div style='text-align:center; margin-top:50px;'>");
                    out.print("<h2 style='color:green;'>Profile Updated Successfully!</h2>");
                    out.print("<a href='dashboard.jsp'>Back to Dashboard</a>");
                    out.print("</div>");
                } else {
                    out.print("<h3 style='color:red;'>Update Failed! Maybe the old password was wrong.</h3>");
                    out.print("<a href='dashboard.jsp'>Try Again</a>");
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
                out.print("<h3 style='color:red;'>Database Error: " + e.getMessage() + "</h3>");
            }
        } else {
            res.sendRedirect("index.jsp");
        }
    }
}