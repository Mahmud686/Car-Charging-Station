public class User {
    private String username;
    private UserType userType;

    public enum UserType {
        EXTERNAL_USER, ADMINISTRATOR 
    }

    public User(String username, UserType userType) {
        this.username = username;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public UserType getUserType() {
        return userType;
    }
}
