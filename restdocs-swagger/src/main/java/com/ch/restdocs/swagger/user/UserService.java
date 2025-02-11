package com.ch.restdocs.swagger.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

  public User findById(String userId){
    return new User(userId, "이찬형", "", 29);
  }

}
