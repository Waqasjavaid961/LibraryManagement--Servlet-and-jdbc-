package Registration;

import DAO.DAOServices;
import Enums.Roles;
import Model.Users;
import DAO.LoginDAO;
import java.sql.SQLException;
import java.util.Optional;

public class Login {
    LoginDAO login=new LoginDAO();
    DAOServices d=new DAOServices();
    public  Optional<Users> loginProcess(String inputGmail, String inputPassword) throws SQLException {
        // DAO method ko call kiya
        Optional<Users> user = login.loginUser(inputGmail, inputPassword);
        if(user.isEmpty()){
            System.out.println("login fail ");
            return Optional.empty();
        }
        if(user.isPresent()) {
        	Users useropt=user.get();
        	if(useropt.isIsactive()) {
        		return Optional.of(useropt);
        	}
        }
        return Optional.empty();
    }
}
     