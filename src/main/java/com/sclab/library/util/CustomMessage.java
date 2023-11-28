package com.sclab.library.util;

public class CustomMessage {
    private String message;
    private Integer statusCode;

    public CustomMessage() {
        this.message = "not found";
        this.statusCode = 404;
    }

    public CustomMessage NOT_FOUND(){
        this.message = "not found";
        this.statusCode = 404;
        return this;
    }

    public CustomMessage NOT_FOUND(String message){
        this.message = message;
        this.statusCode = 404;
        return this;
    }

    public CustomMessage CREATED(){
        this.message = "created";
        this.statusCode = 201;
        return this;
    }

    public CustomMessage(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

}