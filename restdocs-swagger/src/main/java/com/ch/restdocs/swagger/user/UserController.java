package com.ch.restdocs.swagger.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 서비스 상태 체크 API
     * @description 서비스 상태가 정상인지 체크를 하는 API
     */
    @GetMapping("/health-check")
    public ResponseEntity<Void> healthCheck(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *  특정 사용자 조회 API
     * @description 사용자 ID를 받아서 해당 사용자 정보를 조히하는 API
     * @param userId 사용자 ID
     * @return User 사용자 정보
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<User> readUserById(
        @PathVariable String userId){
        User user = userService.findById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
