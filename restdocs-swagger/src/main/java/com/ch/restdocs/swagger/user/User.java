package com.ch.restdocs.swagger.user;

/**
 * 사용자 도메인
 * @param id 사용자 ID
 * @param name 사용자 이름
 * @param email 사용자 이메일
 * @param age 사용자 나이
 */
public record User(

    String id,
    String name,
    String email,
    Integer age
) {

}
