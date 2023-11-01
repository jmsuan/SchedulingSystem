package lanej.schedulingsystem.dao;

import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides data access operations for the User model.
 * It contains methods to check the validity of a user, fetch users based on various criteria,
 * and retrieve all users from the database.
 * <p>
 * The operations are designed to interact with a database using JDBC.
 * Errors during operations are communicated through alerts using the ScreenUtility class.
 * </p>
 *
 * @author Jonathan Lane
 * @version 1.0
 */
public abstract class UserDAO {
    private static List<User> allUsers = new ArrayList<>();
    private static boolean initialized = false;

    /**
     * Checks if a user with the given details exists in the database.
     *
     * @param userInfoToCheck The user details to check.
     * @return true if the user exists, otherwise false.
     */
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

    /**
     * Retrieves a user from the database based on the provided username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The User object if found, otherwise null.
     */
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

    /**
     * Retrieves a user from the database based on the provided user ID.
     *
     * @param userId The ID of the user.
     * @return The User object if found, otherwise null.
     */
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

    /**
     * Retrieves all users from the database.
     * The list of users is retrieved only once as it shouldn't change during program execution.
     *
     * @return A list of all users.
     */
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
