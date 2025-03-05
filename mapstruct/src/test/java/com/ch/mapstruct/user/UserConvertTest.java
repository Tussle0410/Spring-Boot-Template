package com.ch.mapstruct.user;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class UserConvertTest {

  private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

  @Test
  @DisplayName("UserDto to User 변환 테스트")
  void userToUserDtoConvertTest(){
    //given
    User defaultUser = User.createDefaultUser();
    //when
    UserDto userDto = userMapper.userToUserDto(defaultUser);

    //then
    assertThat(userDto.getFullName()).isEqualTo(defaultUser.fullName());
    assertThat(userDto.getAge()).isEqualTo(defaultUser.age());
    assertThat(userDto.getEmail()).isEqualTo(defaultUser.email());
    assertThat(userDto.getPhoneNumber()).isEqualTo(defaultUser.phone());
    assertThat(userDto.getGenderCode()).isEqualTo(defaultUser.gender().getCode());
  }

}
