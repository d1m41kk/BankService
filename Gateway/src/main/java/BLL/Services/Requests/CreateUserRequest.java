package BLL.Services.Requests;

import Enums.HairColor;

public record CreateUserRequest(String login, String password, String name, Boolean sex, Integer age, HairColor hairColor) {
}