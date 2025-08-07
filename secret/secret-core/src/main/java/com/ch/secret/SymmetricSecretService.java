package com.ch.secret;

public interface SymmetricSecretService extends  SecretService {

  /**
   * 솙트 없는 암호화된 데이터 복호화
   * @param encryptedText 인코딩된 암호문
   * @return 복호화된 평문
   */
  String decrypt(String encryptedText);

  /**
   * 시스템 솔트로 암호화된 데이터 복호화
   * @param encryptedText 인코딩된 암호문
   * @return 복호화된 평문
   */
  String decryptWithSystemSalt(String encryptedText);

  /**
   * 솔트 기반 암호화된 데이터 복호화
   * @param encryptedText 인코딩된 암호문
   * @param salt 복호화에 사용할 솔트
   * @return 복호화된 평문
   */
  String decryptWithSalt(String encryptedText, String salt);

}

