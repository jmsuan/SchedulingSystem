package lanej.schedulingsystem.model;

public record User(Integer userId, String userName, String password) {
    @Override
    public String toString() {
        return userId + " (" + userName + ")";
    }
}
