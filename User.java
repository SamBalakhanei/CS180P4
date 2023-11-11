public class User {

    private String username;
    private String password;
    private int messageNum;
    private boolean userType; // true = student, false = tutor

    public User(String username, String password, boolean userType) {
        this.username = username;
        this.password = password;
        this.messageNum = 0;
        this.userType = userType;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
