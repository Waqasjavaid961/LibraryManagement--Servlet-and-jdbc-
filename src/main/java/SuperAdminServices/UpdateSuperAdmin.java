package SuperAdminServices;

import DAO.DAOServices;
import Enums.Roles;

import java.sql.SQLException;

public class UpdateSuperAdmin {
    public boolean UpdateSuperAdminprocess(String oldpassword,String gmail,String password,Roles currentrole)throws SQLException {
        DAOServices s=new DAOServices();
       return s.updateSuperAdminDetails(oldpassword,gmail,password,currentrole);
    }
}
