package com.tcompany.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private int user_id;

    private String email;

    private String firs_name;

    private String last_name;
    private String password;

    private boolean enabled;
}
