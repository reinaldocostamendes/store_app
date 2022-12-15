package com.tcompany.dto;

import com.tcompany.model.Users;

public class UserAdpter {
    private UserAdpter() {
    }

    public static Users toEntity(UserDto userDto){
        Users userEntity = new Users();
        userEntity.setUser_id(userDto.getUser_id());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setEnabled(userDto.isEnabled());
        userEntity.setFirs_name(userDto.getFirs_name());
        userEntity.setLast_name(userDto.getLast_name());
        userEntity.setEmail(userDto.getEmail());
        return  userEntity;
    }
}
