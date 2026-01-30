package Registration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import DAO.LoginDAO;
import Model.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginInput")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String gmail = request.getParameter("gmail");
        String password = request.getParameter("password");

        if (gmail != null) {
            gmail = gmail.trim();
        }

        if (password != null) {
            password = password.trim();
        }

        LoginDAO loginDAO = new LoginDAO();

        try {

            Optional<Users> userOpt = loginDAO.loginUser(gmail, password);

            if (userOpt.isPresent()) {

                Users user = userOpt.get();

                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);

                response.sendRedirect(
                        request.getContextPath() + "/dashboard.jsp"
                );

            } else {

                response.sendRedirect(
                        request.getContextPath() + "/index.jsp?error=invalid"
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath() + "/index.jsp?error=server_error"
            );
        }
    }
}
