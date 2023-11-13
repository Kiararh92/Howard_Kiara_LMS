public class User {
    String firstName;
    String lastName;
    String userName;
    String email;
    String role;
    long userID;
    boolean isActive;

    public User(String userName, String role, long userID){
        this.userName = userName;
        this.role = role;
        this.userID = userID;
        this.isActive = true;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

}

