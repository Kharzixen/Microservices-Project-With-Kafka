package com.kharzixen.userservice.mapper.implementation;

import com.kharzixen.userservice.dto.UserRequestDto;
import com.kharzixen.userservice.mapper.Mapper;
import com.kharzixen.userservice.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper implements Mapper<User, UserRequestDto> {

    private final ModelMapper modelMapper;

    public UserRequestMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserRequestDto mapToDto(User user) {
        return modelMapper.map(user, UserRequestDto.class);
    }

    @Override
    public User mapFromDto(UserRequestDto userRequestDto) {
        return modelMapper.map(userRequestDto, User.class);
    }
}
