package ru.netology.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;


@Data
@Value
@AllArgsConstructor
public class RegistrationDto {
    String login;
    String password;
    String status;
}
