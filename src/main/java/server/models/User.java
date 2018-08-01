package server.models;

public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private boolean online;
    private String TOKEN;

    public User() {
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

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }
}
