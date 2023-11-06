public class User {

    private String username;
    private String password;
    private int messages;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.messages = 0;
    }


    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }



    // public int getMessages(DM other) {  will need to create "DM" class that holds text file and num msgs?
    //     return 0;
    // }
}
