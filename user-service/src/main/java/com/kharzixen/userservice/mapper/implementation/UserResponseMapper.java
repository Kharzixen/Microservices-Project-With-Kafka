package com.kharzixen.userservice.mapper.implementation;

import com.kharzixen.userservice.dto.UserResponseDto;
import com.kharzixen.userservice.mapper.Mapper;
import com.kharzixen.userservice.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper implements Mapper<User, UserResponseDto> {

    private final ModelMapper modelMapper;

    public UserResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponseDto mapToDto(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public User mapFromDto(UserResponseDto userResponseDto) {
        return modelMapper.map(userResponseDto, User.class);
    }
}
