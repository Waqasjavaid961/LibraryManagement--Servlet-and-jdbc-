package SuperAdminServices;

import DAO.DAOServices;

import java.sql.SQLException;
import java.util.Optional;

public class AddAdmin {
    DAOServices d=new DAOServices();
    public boolean AddAdminProcess(int id)throws SQLException {
       Optional<Integer>res= d.PromoteUser(id);
       if(res.isPresent()){
           return true;
       }
       else{
           return false;
       }
    }

}

