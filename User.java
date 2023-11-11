public class User {

    private String username;
    private String password;
    private int messageNum;
    private boolean userType; // true = student, false = tutor

    //Used during sign up
    public User(String username, String password, boolean userType) {
        this.username = username;
        this.password = password;
        this.messageNum = 0;
        this.userType = userType;
    }

    //Used during login
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Used while finding a user to chat with
    public User(String username, boolean userType) {
        this.username = username;
        this.userType = userType; 
    }


    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setMessages(int messageNum) {
        this.messageNum = messageNum;
    }

    public void setUserType(boolean userType) {
        this.userType = userType;
    }

    public boolean getUserType() {
        return this.userType;
    }

    public int getMessageNum() {
        return this.messageNum;
    }


}
