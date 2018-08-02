package server.models;

public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private boolean online;
    private String token;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        online = false;
    }

    public User(String id, String username, String password, String email, boolean online) {
        this.id = id;
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setOnline(online);
    }


    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString(){
        return String.format("%s %s %s %s",getUsername(), getEmail(), isOnline(), getToken());
    }
}
