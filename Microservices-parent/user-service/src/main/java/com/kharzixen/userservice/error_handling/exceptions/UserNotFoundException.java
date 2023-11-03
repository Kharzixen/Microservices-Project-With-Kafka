package com.kharzixen.userservice.error_handling.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String id) {
        super("User not found with ID: " + id);
    }
}
