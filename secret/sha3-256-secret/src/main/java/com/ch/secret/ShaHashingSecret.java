package com.ch.secret;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * SHA3-256 해싱을 제공하는 구현체
 * 단방향 해싱을 지원하며, 솔트를 사용한 안전한 해싱 기능 제공
 */
@Component
@RequiredArgsConstructor
public class ShaHashingSecret implements HashingSecretService {

  private final SecretLogger secretLogger;
  private static final String ALGORITHM = "SHA3-256";
  private static final int SALT_LENGTH = 32;

  @Value("${security.system.hashing.salt}")
  private String systemSalt;

  /**
   * 평문을 SHA3-256으로 해싱 (솔트 없이)
   * @param plainText 해싱할 평문
   * @return Base64로 인코딩된 해시값
   */
  @Override
  public String encrypt(String plainText) {
    isShaPlainTextValid(plainText);
    String shaHashing = shaHashing(plainText);
    secretLogger.logSecurityOperationSuccess(ALGORITHM, "SHA3-256 해싱" );
    return shaHashing;
  }

  /**
   * 평문을 지정된 솔트와 함께 SHA3-256으로 해싱
   * @param plainText 해싱할 평문
   * @param salt 사용할 솔트
   * @return Base64로 인코딩된 해시값 (솔트 + 해시값)
   */
  @Override
  public String encryptWithSalt(String plainText, String salt) {
    isShaPlainTextValid(plainText);
    isShaSaltValid(salt);
    String shaHashing = shaHashing(plainText + salt);
    secretLogger.logSecurityOperationSuccess(ALGORITHM, "SHA3-256 해싱" );
    return shaHashing;
  }

  /**
   * 평문을 시스템 솔트와 함께 SHA3-256으로 해싱
   * @param plainText 해싱할 평문
   * @return Base64로 인코딩된 해시값
   */
  @Override
  public String encryptWithSystemSalt(String plainText) {
    isShaPlainTextValid(plainText);
    String shaHashing = shaHashing(plainText + systemSalt);
    secretLogger.logSecurityOperationSuccess(ALGORITHM, "SHA3-256 해싱" );
    return shaHashing;
  }

  /**
   * 솔트 없는 해시값 검증
   * @param plainText 원본 평문
   * @param hashedText 해시된 텍스트
   * @return 검증 결과
   */
  @Override
  public boolean verify(String plainText, String hashedText) {
    isShaHashedTextValid(hashedText);
    try {
      String newHash = encrypt(plainText);
      return newHash.equals(hashedText);
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 솔트가 포함된 해시값 검증
   * @param plainText 원본 평문
   * @param hashedText 해시 형태의 문자열(평문 + 솔트)
   * @param salt 암호화에 사용된 솔트
   * @return 검증 결과
   */
  @Override
  public boolean verifyWithSalt(String plainText, String hashedText, String salt) {
    isShaHashedTextValid(hashedText);
    try {
      String newHash = encryptWithSalt(plainText, salt);
      return newHash.equals(hashedText);
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 시스템 솔트 해시값 검증
   * @param plainText 원본 평문
   * @param hashedText 해시된 텍스트(평문 + 시스템 솔트)
   * @return 검증 결과
   */
  @Override
  public boolean verifyWithSystemSalt(String plainText, String hashedText) {
    isShaHashedTextValid(hashedText);
    try {
      String newHash = encryptWithSystemSalt(plainText);
      return newHash.equals(hashedText);
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * SHA3-256 해싱에 사용할 랜덤 솔트 생성
   * @return  SHA3-256 해싱에 사용할 랜덤 솔트
   */
  public String generateSalt() {
    return generateSalt(SALT_LENGTH);
  }

  /**
   * SHA3-256 해싱
   * @param plainText 해싱할 평문
   * @return Base64로 인코딩된 해시값
   * @throws OutOfMemoryError SHA3-256 해싱시 시스템 자원 문제로 인한 실패
   */
  private String shaHashing(String plainText) {
    secretLogger.logSecurityOperationStart(ALGORITHM, "SHA3-256 해싱" );
    try {
      MessageDigest digest = new SHA3.Digest256();

      byte[] hashBytes = digest.digest(plainText.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(hashBytes);
    } catch (OutOfMemoryError outOfMemoryError) {
      secretLogger.logSecurityOperationFailure(ALGORITHM, "SHA3-256 해싱", "시스템자원", "시스템 자원 문제", outOfMemoryError);
      throw new OutOfMemoryError( "SHA3-256 해싱 실패");
    }
  }

  /**
   * SALT 검증
   * @param salt 검증받을 솔트
   * @throws SecurityException 솔트가 올바르지 않음
   */
  private void isShaSaltValid(String salt) {
    if(isInValidationSalt(salt)){
      secretLogger.logValidationError("솔트 검증", "솔트가 올바르지 않는다.");
      throw new SecurityException("솔트가 올바르지 않습니다.");
    }
  }

  /**
   * 평문 검증
   * @param plainText 검증받을 평문
   * @throws SecurityException 평문이 올바르지 않음
   */
  private void isShaPlainTextValid(String plainText) {
    if(isInValidPlainText(plainText)){
      secretLogger.logValidationError("평문 검증", "평문이 올바르지 않는다.");
      throw new SecurityException("암호화 대상 텍스트가 올바르지 않습니다.");
    }
  }
  /**
   * 해시값 검증
   * @param hashedText 검증 받을 해싱값
   * @throws SecurityException 해싱 값이 올바르지 않음
   */
  private void isShaHashedTextValid(String hashedText) {
    if(isInValidPlainText(hashedText)){
      secretLogger.logValidationError("해싱된 평문 검증", "해싱된 평문이 올바르지 않는다.");
      throw new SecurityException("해싱된 평문이 올바르지 않습니다.");
    }
  }
}
