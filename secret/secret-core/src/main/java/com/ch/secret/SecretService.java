package com.ch.secret;

import io.micrometer.common.util.StringUtils;
import java.security.SecureRandom;
import java.util.Base64;

interface SecretService {

  SecureRandom secureRandom = new SecureRandom();

  /**
   * 평문을 암호화
   * @param plainText 암호화할 평문
   * @return 인코딩된 암호문
   */
  String encrypt(String plainText);

  /**
   * 평문을 솔트와 함께 암호화
   * @param plainText 암호화할 평문
   * @param salt      사용할 솔트
   * @return 인코딩된 암호문
   */
  String encryptWithSalt(String plainText, String salt);

  /**
   * 평문을 시스템 솔트와 함께 암호화
   * @param plainText 암호화할 평문
   * @return 인코딩된 암호문
   */
  String encryptWithSystemSalt(String plainText);

  /**
   * 유효하지 않은 평문인지 검사
   * @param plaintText 유효성 검사 대상 평문
   * @return true : 유효하지 않은 평문, false : 유효한 평문
   */
  default boolean isInValidPlainText(String plaintText) {
    return StringUtils.isEmpty(plaintText);
  }

  /**
   * 유효하지 않은 솔트인지 검사
   * @param salt 유효성 검사 대상 솔트
   * @return true : 유효하지 않은 솔트, false : 유효한 솔트
   */
  default boolean isInValidationSalt(String salt) {
    return StringUtils.isEmpty(salt);
  }

  /**
   * 랜덤 솔트 생성
   * @param saltLength 솔트 길이(32 : 256bit)
   * @return 생성된 솔트 문자열
   */
  default String generateSalt(int saltLength) {
    byte[] saltBytes = new byte[saltLength];
    secureRandom.nextBytes(saltBytes);
    return Base64.getEncoder().encodeToString(saltBytes);
  }
}