package Registration;

import Model.Users;
import DAO.*;

import java.sql.SQLException;
import java.util.Optional;

public class Registration {
    DAORegistration r=new DAORegistration();
    public boolean registration_process(Users user)throws SQLException {
      boolean result=  r.checkduplicate(user.getGmail());
      if(result){
          //System.out.println("gmail account already exists ");
          return false;
      }
      boolean res =r.registerUser(user);
      if(res){

          return true;
      }
      else {
         // System.out.println("data not saved please enter write details");
      return false;
      }

    }
}

