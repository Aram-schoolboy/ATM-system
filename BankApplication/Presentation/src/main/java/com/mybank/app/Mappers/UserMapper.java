package com.mybank.app.Mappers;

import Models.User;
import com.mybank.app.DTO.UserDTO;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getLogin(),
                user.getName(),
                user.getAge(),
                user.getGender(),
                user.getHairColor()
        );
    }
}
