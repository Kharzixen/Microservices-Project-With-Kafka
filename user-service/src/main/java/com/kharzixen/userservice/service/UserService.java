package com.kharzixen.userservice.service;

import com.kharzixen.userservice.dto.UserRequestDto;
import com.kharzixen.userservice.dto.UserResponseDto;
import com.kharzixen.userservice.error_handling.exceptions.UserNotFoundException;
import com.kharzixen.userservice.mapper.implementation.UserRequestMapper;
import com.kharzixen.userservice.mapper.implementation.UserResponseMapper;
import com.kharzixen.userservice.model.User;
import com.kharzixen.userservice.repository.UserRepository;
import lombok.extern.java.Log;
import org.modelmapper.internal.bytebuddy.dynamic.DynamicType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log
public class UserService {
    private final UserRepository userRepository;

    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;


    public UserService(UserRepository userRepository, UserRequestMapper userRequestMapper, UserResponseMapper userResponseMapper) {
        this.userRepository = userRepository;
        this.userRequestMapper = userRequestMapper;
        this.userResponseMapper = userResponseMapper;
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto){
        User user = userRequestMapper.mapFromDto(userRequestDto);
        User savedUser = userRepository.save(user);
        return userResponseMapper.mapToDto(savedUser);
    }

    public List<UserResponseDto> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map(userResponseMapper::mapToDto).toList();
    }

    public Optional<UserResponseDto> getUserById(String id){
        Optional<User> user = userRepository.findById(id);
        return user.flatMap(user1 -> Optional.ofNullable(userResponseMapper.mapToDto(user1)));
    }

    public void deleteUser(String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.deleteById(user.get().getId());
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public Optional<UserResponseDto> updateUser(String id, UserRequestDto userRequestDto){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            User newUser = userRequestMapper.mapFromDto(userRequestDto);
            newUser.setId(user.get().getId());
            User updated = userRepository.save(newUser);
            return Optional.ofNullable(userResponseMapper.mapToDto(updated));
        } else {
            return Optional.empty();
        }
    }
}
