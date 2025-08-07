package com.ch.secret;

public interface HashingSecretService extends SecretService {

  /**
   * 솔트가 포함된 해시값 검증
   * @param plainText 검증할 평문
   * @param hashedText 해시 형태의 문자열
   * @param salt 암호화에 사용된 솔트
   * @return true : 유효한 해시값, false : 유효하지 않은 해시값
   */
   boolean verifyWithSalt(String plainText, String hashedText, String salt);

  /**
   * 솔트 없는 해시값 검증
   * @param plainText 검증할 평문
   * @param hashedText 해시 형태의 문자열
   * @return true : 유효한 해시값, false : 유효하지 않은 해시값
   */
   boolean verify(String plainText, String hashedText);

  /**
   * 시스템 솔트 해시값 검증
   * @param plainText 검증할 평문
   * @param hashedText 해시 형태의 문자열
   * @return true : 유효한 해시값, false : 유효하지 않은 해시값
   */
  boolean verifyWithSystemSalt(String plainText, String hashedText);
}

