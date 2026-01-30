package DAO;

import Enums.Roles;
import Model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LoginDAO {

    private Connection getLoginConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    public Optional<Users> loginUser(String gmail, String password) throws SQLException {

        String sql = "SELECT * FROM users WHERE gmail = ? AND password = ?";

        try (
                Connection con = getLoginConnection();
                PreparedStatement pr = con.prepareStatement(sql)
        ) {

            pr.setString(1, gmail);
            pr.setString(2, password);

            System.out.println("checking login and password " + gmail + " " + password);

            try (ResultSet rs = pr.executeQuery()) {

                if (rs.next()) {

                    System.out.println(
                            "DEBUG: User Found! Name is: " + rs.getString("name")
                    );

                    Users user = new Users();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setGmail(rs.getString("gmail"));

                    try {
                        user.setRole(
                                Roles.fromString(
                                        rs.getString("role")
                                )
                        );
                    } catch (Exception e) {

                        System.out.println(
                                "DEBUG: Role conversion failed: " + e.getMessage()
                        );
                        user.setRole(Roles.user);
                    }

                    return Optional.of(user);

                } else {

                    System.out.println(
                            "DEBUG: Database returned NO rows for this gmail/pass."
                    );
                }
            }
        }

        return Optional.empty();
    }
}
