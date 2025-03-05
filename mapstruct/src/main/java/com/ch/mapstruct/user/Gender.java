package com.ch.mapstruct.user;

import java.util.HashMap;
import java.util.Map;

public enum Gender {

  MAIL("남성", 1),
  FEMAIL("여성", 2);
  private static final Map<Integer, Gender> genderMap = new HashMap<>();

  static {
    for (Gender gender: Gender.values()) {
      genderMap.put(gender.code, gender);
    }
  }
  private final String description;
  private final int code;

  Gender(String description, int code) {
    this.description = description;
    this.code = code;
  }
  public static Gender fromCode(int code){
    return genderMap.get(code);
  }

  public int getCode() {
    return code;
  }
}
