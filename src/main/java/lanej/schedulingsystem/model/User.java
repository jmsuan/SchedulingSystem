package lanej.schedulingsystem.model;

/**
 * Represents a user within the scheduling system, providing an identifier, a username, and a password.
 *
 * <p>
 * Automatically generates getter methods, equals, hashCode, and toString methods based on the components declared,
 * so we only need to provide additional behavior or override default behavior as necessary.
 * </p>
 * <p>
 * This is supposedly a relatively new feature in Java and was suggested by my IDE.
 * </p>
 *
 * @author Jonathan Lane
 */
public record User(Integer userId, String userName, String password) {
    @Override
    public String toString() {
        return userId + " (" + userName + ")";
    }
}
