package com.mybank.gateway.DTO;


public record UserDTO(Long id, String login, String name, Integer age, Gender gender, HairColor hairColor) {}