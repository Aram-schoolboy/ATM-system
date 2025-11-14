package com.mybank.app.DTO;

import Models.Gender;
import Models.HairColor;

public record UserDTO(Long id, String login, String name, Integer age, Gender gender, HairColor hairColor) {}