package com.company;

public class Message {
    private String message;
    //Constructor
    public Message(String message){
        this.message=message;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getMessageArray(){
        return message.split(" ");
    }

    public char[] getMessageCharArray(){
        return message.toCharArray();
    }
}
