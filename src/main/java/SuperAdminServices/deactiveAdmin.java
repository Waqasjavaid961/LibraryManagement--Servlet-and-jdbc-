package SuperAdminServices;

import DAO.DAOServices;

import java.sql.SQLException;

public class deactiveAdmin {
    DAOServices s=new DAOServices();
    public boolean Deactive(int id)throws SQLException {
       return s.deactivate(id);
    }
}

