package com.ch.mapstruct.user;

import lombok.Builder;

@Builder
public record User(
    String firstName,
    String lastName,
    String age,
    String email,
    String phone,
    Gender gender) {


  public static User createDefaultUser(){
    return User.builder()
        .firstName("ChanHyeong")
        .lastName("Lee")
        .age("29")
        .email("cksgud410@gmail.com")
        .phone("010-0000-0000")
        .gender(Gender.MAIL)
        .build();
  }

  public String fullName(){
    return String.format("%s %s", firstName, lastName);
  }
}
