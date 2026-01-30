package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String url="jdbc:mysql://localhost:3306/library";
    private static String username="root";
    private static String password="Waqas@123";
    public  static Connection getConnection()throws SQLException{
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    	}catch(ClassNotFoundException e) {
    		System.out.println("driver not found"+e.getMessage());
    	}
        return DriverManager.getConnection(url,username,password);
    }
    public static  void main(String[]args)throws SQLException{
        try(Connection connection=getConnection()){
            System.out.println("connected successfully");
        }
        catch (SQLException e){
            System.out.println("Database error "+e.getMessage());
        }
    }
}
