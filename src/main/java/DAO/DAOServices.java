package DAO;

import Enums.Roles;
import Model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DAOServices {
    private Connection GetDAOServiceConnection() throws SQLException {
        return DBConnection.getConnection();
    }
    public boolean checkbyid(int id)throws SQLException {
        String sql = "select 1 from users where id = ? and ( role ='user' or role ='admin')";
        try (Connection con = GetDAOServiceConnection(); PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setInt(1, id);
            ResultSet sr = pr.executeQuery();
            return sr.next();
        } catch (SQLException e) {
            System.out.println("unknown database error" + e.getMessage());
            return false;
        }
    }

    public Optional<Integer> PromoteUser(int id) throws SQLException {
        if(!checkbyid(id)){
            return Optional.empty();
        }

        String sql = "update  users set role = ? where id = ? ";
        try (Connection con = GetDAOServiceConnection(); PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, Roles.admin.name());
            pr.setInt(2, id);
            int rows=pr.executeUpdate();
            if (rows>0) {
                return Optional.of(id);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.out.println("Unknown database error" + e.getMessage());
            return Optional.empty();
        }
    }
    public boolean deactivate(int id)throws SQLException{
        if(!checkbyid(id)){
            return false;
        }

        String sql="update users set status = ? where id = ? and role = 'admin'";
        try(Connection con=GetDAOServiceConnection(); PreparedStatement pr=con.prepareStatement(sql)){
            pr.setBoolean(1,false);
            pr.setInt(2,id);
            int rows=pr.executeUpdate();
            if(rows>0){
                return true;
            }
            return false;
        }catch (SQLException e){
            System.out.println("Unkonwn database error"+e.getMessage());
            return false;
        }
    }
    public boolean checkStatus(int id)throws SQLException{
        if(!checkbyid(id)){
            return false;
        }
        String sql="select * from users where status = true and   id = ?";
        try(Connection con=GetDAOServiceConnection(); PreparedStatement pr=con.prepareStatement(sql)){
            pr.setInt(1,id);
            ResultSet sr=pr.executeQuery();
            return sr.next();
        }catch (SQLException e){
            System.out.println("unkown database error"+e.getMessage());
            return false;
        }
    }

    public boolean ShowUsers()throws SQLException {
        String sql = "select * from users where role = 'user'";
        try (Connection con = GetDAOServiceConnection(); PreparedStatement pr = con.prepareStatement(sql); ResultSet sr = pr.executeQuery()) {
            while (sr.next()) {
                System.out.println("ID" + sr.getInt("id"));
                System.out.println("NAME" + sr.getString("name"));
                System.out.println("gmail" + sr.getString("gmail"));
                System.out.println("ROLE" + sr.getString("role"));
                System.out.println("--------------------------------------------");

            }
            return true;
        } catch (SQLException e) {
            System.out.println("unknown database error" + e.getMessage());
            return false;
        }
    }
    public boolean updateSuperAdminDetails(String oldpassword,String gmail,String password,Roles currentrole)throws SQLException{
        if(currentrole!=Roles.superadmin|| currentrole==null){
            return false;
        }
        String sql="update users set gmail = ? , password= ? where role ='superadmin' and password = ?";
        try(Connection con=GetDAOServiceConnection();PreparedStatement pr=con.prepareStatement(sql)){
            pr.setString(1,gmail);
            pr.setString(2,password);
            pr.setString(3,oldpassword);
            int rows=pr.executeUpdate();
            if(rows>0){
                return true;
            }
            return false;
        }catch (SQLException e){
            System.out.println("unkown database error"+e.getMessage());
            return false;
        }
    }
    public boolean Showadmins()throws SQLException {
        String sql = "select * from users where role = 'admin'";
        try (Connection con = GetDAOServiceConnection(); PreparedStatement pr = con.prepareStatement(sql); ResultSet sr = pr.executeQuery()) {
            while (sr.next()) {
                System.out.println("ID" + sr.getInt("id"));
                System.out.println("NAME" + sr.getString("name"));
                System.out.println("gmail" + sr.getString("gmail"));
                System.out.println("ROLE" + sr.getString("role"));
                System.out.println("--------------------------------------------");

            }
            return true;
        } catch (SQLException e) {
            System.out.println("unknown database error" + e.getMessage());
            return false;
        }
    }

}
