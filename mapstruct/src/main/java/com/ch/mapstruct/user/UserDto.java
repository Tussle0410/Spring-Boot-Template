package com.ch.mapstruct.user;


import lombok.Getter;

@Getter
public class UserDto {
  private String fullName;
  private String age;
  private String email;
  private String phoneNumber;
  private Integer genderCode;

  public UserDto(String fullName, String age, String email, String phoneNumber, Integer genderCode) {
    this.fullName = fullName;
    this.age = age;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.genderCode = genderCode;
  }

  @Override
  public String toString() {
    return String.format("UserDto = [fullName='%s', age='%s', email='%s', phoneNumber='%s', genderCode='%d']",
        fullName, age, email, phoneNumber, genderCode);
  }
}
