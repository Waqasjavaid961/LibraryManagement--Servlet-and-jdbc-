package DAO;

import Enums.Roles;
import Model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DAORegistration {
    private Connection getUserConnection()throws SQLException{
        return DBConnection.getConnection();
    }
    public boolean checkduplicate(String gmail)throws SQLException{
        String sql="select * from users where gmail = ?";
        try(Connection con=getUserConnection(); PreparedStatement pr=con.prepareStatement(sql)){
            pr.setString(1,gmail);
            ResultSet sr=pr.executeQuery();
            if(sr.next()){
                return true;
            }
            return false;
        }catch (SQLException e){
            System.out.println("database unkown error"+e.getMessage());
            return false;
        }
    }
    public  boolean registerUser(Users user)throws SQLException{
        String sql="insert into  users(name,gmail,password,role)values(?,?,?,?)";
        try(Connection con=getUserConnection(); PreparedStatement pr=con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)){
            pr.setString(1,user.getName());
            pr.setString(2,user.getGmail());
            pr.setString(3,user.getPassword());
           Roles role=Roles.fromString(user.getRole().name());
           if(role==null){
               throw new IllegalArgumentException("INVALID ROLE"+user.getRole());
           }
           pr.setString(4,user.getRole().name());
           int rows=pr.executeUpdate();

           if(rows>0) {
               try (ResultSet sr = pr.getGeneratedKeys()) {
                   if (sr.next()) {
                       int generated = sr.getInt(1);
                       user.setId(generated);
                   }
                   // System.out.println("data saved");
               }
               return true;
           }

           return false;
        }catch (SQLException e){
            System.out.println("UNKNOWN DATABASE ERROR"+e.getMessage());
            return false;
        }
    }
}

