package hr.algebra.model;

public class User {
    private int id;
    private String username;
    private String password;
    private boolean administrator;
    
    
    public User(String username, String password, boolean administrator) {
        this(-1, username, password, administrator);
    }

    public User(int id, String username, String password, boolean administrator) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.administrator = administrator;
    }

    public int getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }
}
