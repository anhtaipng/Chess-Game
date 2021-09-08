package vn.gihot.chess.master.model.socket;

public class Message {

    //hien tai khong su dung
    private String username;
    private String message;

    public Message() {
    }

    public Message(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

//    private String name;
//    private String message;
//
//    public Message(String name, String message) {
//        this.name = name;
//        this.message = message;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//    public String getMessage() {
//        return message;
//    }
//    public void setMessage(String message) {
//        this.message = message;
//    }


}
