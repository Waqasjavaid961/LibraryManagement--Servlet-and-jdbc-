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

@WebServlet("/AddInput")
public class AddAdminInput extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
    	AddAdmin a=new AddAdmin();

        HttpSession session = req.getSession(false);

        if (session != null) {
            Users user = (Users) session.getAttribute("user");

            if (user != null) {
            int id=Integer.parseInt(req.getParameter("id"));
            boolean b=false;
            try {
			 b=a.AddAdminProcess(id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            PrintWriter out=res.getWriter();
            
            if(b) {
            	out.print("admin created successfully");
            	
            }
            else {
            	out.print("not created successfully");
            }
              
            }
        }
    }
}

