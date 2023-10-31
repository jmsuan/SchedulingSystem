package lanej.schedulingsystem.model;

public class User {
    private Integer userId;
    private String userName;
    private String password;
    public User(Integer userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getUserId() {
        return userId;
    }
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    @Override
    public String toString() {
        return ((Integer)userId).toString();
    }
}
