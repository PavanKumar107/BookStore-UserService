package com.blz.bookstoreuser.util;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *  
 * Purpose:Response to the user
 * @author: Pavan Kumar G V 
 * @version: 4.15.1.RELEASE
 * 
 **/ 
@Data
@AllArgsConstructor
public class Response {
    private String message;
    private int errorCode;
    private Object token;
   
    public Response() {
    }
}