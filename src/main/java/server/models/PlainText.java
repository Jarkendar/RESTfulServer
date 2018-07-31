package server.models;

public class PlainText {

    private String message;

    public PlainText(){
    }

    public PlainText(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
