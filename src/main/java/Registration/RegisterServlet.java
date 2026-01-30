package Registration;
import java.io.IOException;
import java.sql.SQLException;

import Enums.Roles;
import Model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterInput")
public class RegisterServlet extends HttpServlet {
    // POST method use karein kyunke form POST bhej raha hai
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String gmail = request.getParameter("gmail");
        String password = request.getParameter("password");

        if (gmail != null && gmail.contains("@") && gmail.matches(".*[a-zA-Z].*")) {
            Users user = new Users(name, gmail, password, Roles.user);
            Registration r = new Registration();
            
            try {
                if (r.registration_process(user)) {
                    // SUCCESS: Wapas index.jsp par bhejo message ke sath
                    response.sendRedirect("index.jsp?msg=registered");
                } else {
                    response.sendRedirect("index.jsp?error=already_exists");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("index.jsp?error=db_error");
            }
        } else {
            response.sendRedirect("index.jsp?error=invalid_email");
        }
    }
}