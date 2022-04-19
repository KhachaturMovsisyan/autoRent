package com.autorent.mapper;

import com.autorent.mapper.config.BaseMapper;
import com.autorent.web.dto.CreateUserRequest;
import com.autorent.web.dto.response.UserResponse;
import com.autorent.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class UserMapper implements BaseMapper<User, CreateUserRequest, UserResponse> {

    private final ModelMapper mapper;

    @Override
    public User toEntity(CreateUserRequest createUserRequest) {
        return null;
    }

    @Override
    public UserResponse toResponse(User user) {
        return null;
    }
}
