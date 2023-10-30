package lanej.schedulingsystem.dao;

import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDAO {
    public static boolean checkForValidUser(User userInfoToCheck) {
        try {
            PreparedStatement query = JDBC.getConnection().prepareStatement(
                    "SELECT COUNT(*) FROM users WHERE User_Name = ? AND Password = ?");
            query.setString(1, userInfoToCheck.getUserName());
            query.setString(2, userInfoToCheck.getPassword());
            ResultSet rs = query.executeQuery();
            return rs.getInt(1) > 0;
        } catch (SQLException sqlException) {
            ScreenUtility.alert("Error when querying for valid user!\nMessage: " +
                    sqlException.getMessage());
        }
        return false;
    }

    public static User getUserById(int userId) {
        User user = null;
        try {
            PreparedStatement query = JDBC.getConnection().prepareStatement(
                    "SELECT * FROM users WHERE User_ID = ?");
            query.setInt(1, userId);
            ResultSet rs = query.executeQuery();
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
}
