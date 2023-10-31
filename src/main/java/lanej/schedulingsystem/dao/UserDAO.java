package lanej.schedulingsystem.dao;

import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class UserDAO {
    private static List<User> allUsers = new ArrayList<>();
    private static boolean initialized = false;
    public static boolean checkForValidUser(User userInfoToCheck) {
        try {
            PreparedStatement query = JDBC.getConnection().prepareStatement(
                    "SELECT COUNT(*) FROM users WHERE User_Name = ? AND Password = ?");
            query.setString(1, userInfoToCheck.userName());
            query.setString(2, userInfoToCheck.password());
            ResultSet rs = query.executeQuery();
            // Move past header to count
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException sqlException) {
            ScreenUtility.alert("Error when querying for valid user!\nMessage: " +
                    sqlException.getMessage());
        }
        return false;
    }

    public static User getUserByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            PreparedStatement query = JDBC.getConnection().prepareStatement(
                    "SELECT * FROM users WHERE User_Name = ? AND Password = ? LIMIT 1");
            query.setString(1, username);
            query.setString(2, password);
            ResultSet rs = query.executeQuery();
            // Move past header to user record
            rs.next();
            user = new User(
                    rs.getInt("User_ID"),
                    rs.getString("User_Name"),
                    rs.getString("Password"));
        } catch (SQLException sqlException) {
            ScreenUtility.alert("Error when trying to find user by username and password!\nMessage: " +
                    sqlException.getMessage());
        }
        return user;
    }

    public static User getUserById(Integer userId) {
        User user = null;
        try {
            PreparedStatement query = JDBC.getConnection().prepareStatement(
                    "SELECT * FROM users WHERE User_ID = ?");
            query.setInt(1, userId);
            ResultSet rs = query.executeQuery();
            // Move past header to User records
            rs.next();
            user = new User(
                    rs.getInt("User_ID"),
                    rs.getString("User_Name"),
                    rs.getString("Password")
            );
        } catch (SQLException sqlException) {
            ScreenUtility.alert("Error when trying to find user by ID!\nMessage: " +
                    sqlException.getMessage());
        }
        return user;
    }

    public static List<User> getAllUsers() {
        // List of Users should not change during program execution,
        // so I will only retrieve this once.
        if (!initialized) {
            List<User> userList = new ArrayList<>();
            try {
                PreparedStatement query = JDBC.getConnection().prepareStatement("SELECT * FROM users");
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    userList.add(new User(
                            rs.getInt("User_ID"),
                            rs.getString("User_Name"),
                            rs.getString("Password")
                    ));
                }
                System.out.println("Retrieved all contacts from database.");
            } catch (SQLException sqlException) {
                ScreenUtility.alert("Error when getting all the Contacts!\nMessage: " +
                        sqlException.getMessage());
            } finally {
                allUsers = userList;
                initialized = true;
            }
        }
        return allUsers;
    }
}
