package server.models;

public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private String facebookProfile;
    private String googlePlusProfile;

    public User() {
    }

    public User(String id, String username, String password, String email, String facebookProfile, String googlePlusProfile) {
        this.id = id;
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setFacebookProfile(facebookProfile);
        this.setGooglePlusProfile(googlePlusProfile);
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

    public String getFacebookProfile() {
        return facebookProfile;
    }

    public void setFacebookProfile(String facebookProfile) {
        this.facebookProfile = facebookProfile;
    }

    public String getGooglePlusProfile() {
        return googlePlusProfile;
    }

    public void setGooglePlusProfile(String googlePlusProfile) {
        this.googlePlusProfile = googlePlusProfile;
    }
}
