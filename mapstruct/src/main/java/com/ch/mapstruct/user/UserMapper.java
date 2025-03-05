package com.ch.mapstruct.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "fullName", expression = "java(user.fullName())")
  @Mapping(target = "phoneNumber", source = "phone")
  @Mapping(target = "genderCode", source = "gender", qualifiedByName = "genderEnumToCode")
  UserDto userToUserDto(User user);

  @Named("genderEnumToCode")
  static Integer genderEnumToCode(Gender gender){
    return gender.getCode();
  }
}
