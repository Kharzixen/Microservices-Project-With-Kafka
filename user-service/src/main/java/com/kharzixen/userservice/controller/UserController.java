package com.kharzixen.userservice.controller;

import com.kharzixen.userservice.dto.UserRequestDto;
import com.kharzixen.userservice.dto.UserResponseDto;
import com.kharzixen.userservice.error_handling.errors.ErrorResponse;
import com.kharzixen.userservice.error_handling.exceptions.UserNotFoundException;
import com.kharzixen.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto){
        return userService.createUser(userRequestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") String id){
        Optional<UserResponseDto> optionalUserResponseDto = userService.getUserById(id);
        if(optionalUserResponseDto.isPresent()){
            return new ResponseEntity<>(optionalUserResponseDto.get(), HttpStatus.OK);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable("id") String id){
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("id") String id, @RequestBody UserRequestDto userRequestDto){
        Optional<UserResponseDto> optionalUpdatedUser = userService.updateUser(id, userRequestDto);
        if(optionalUpdatedUser.isPresent()){
            return new ResponseEntity<>(optionalUpdatedUser.get(),HttpStatus.OK);
        } else {
            throw new UserNotFoundException(id);
        }
    }
    

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
