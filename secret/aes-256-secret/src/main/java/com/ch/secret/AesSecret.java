package com.ch.secret;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * AES-256-GCM 대칭 암호화를 제공하는 구현체
 * 양방향 암호화를 지원하며, 솔트를 사용한 키 파생 기능 제공
 */
@Component
@RequiredArgsConstructor
public class AesSecret implements SymmetricSecretService {

  private static final String ALGORITHM = "AES";
  private static final String TRANSFORMATION = "AES/GCM/NoPadding";
  private static final int IV_LENGTH = 12;
  private static final int TAG_LENGTH = 16;
  private static final int SALT_LENGTH = 16;

  private final SecretLogger secretLogger;

  @Value("${security.system.symmetric.salt}")
  private String systemSalt;

  @Value("${security.system.symmetric.secret-key}")
  private String secretKey;


  /**
   * 평문을 기본 키로 AES-GCM 암호화
   * @param plainText 암호화할 평문
   * @return Base64로 인코딩된 암호문 (IV + 암호문 + GCM 인증 태그)
   */
  @Override
  public String encrypt(String plainText) {
    isAesPlainTextValid(plainText);
    SecretKey key = generateKey();
    return encrypt(plainText, key);
  }

  /**
   * 평문을 솔트 + 기본 키 기반으로 AES-GCM 암호화
   * @param plainText 암호화할 평문
   * @param salt 키 파생에 사용할 솔트
   * @return Base64로 인코딩된 암호문 (솔트 + IV + 암호문 + GCM 인증 태그)
   */
  @Override
  public String encryptWithSalt(String plainText, String salt) {
    isAesPlainTextValid(plainText);
    isAesSaltValid(salt);
    SecretKey key = generateKeyWithSalt(salt);
    return encrypt(plainText, key);
  }

  /**
   * 평문을 시스템 솔트 + 기본 키 기반으로 AES-GCM 암호화
   * @param plainText 암호화할 평문
   * @return Base64로 인코딩된 암호문 (IV + 암호문 + GCM 인증 태그)
   */
  @Override
  public String encryptWithSystemSalt(String plainText) {
    isAesPlainTextValid(plainText);
    SecretKey key = generateKeyWithSalt(systemSalt);
    return encrypt(plainText, key);
  }

  /**
   * AES-GCM 암호화된 데이터를 기본 키로 복호화
   * @param encryptedText Base64로 인코딩된 암호문
   * @return 복호화된 평문
   */
  @Override
  public String decrypt(String encryptedText) {
    isAesHashedTextValid(encryptedText);
    SecretKey key = generateKey();
    return decrypt(encryptedText, key);
  }

  /**
   * 솔트 + 기본키 기반 암호문을 복호화
   * @param encryptedText Base64로 인코딩된 암호문
   * @param salt 복호화에 사용할 솔트
   * @return 복호화된 평문
   */
  @Override
  public String decryptWithSalt(String encryptedText, String salt) {
    isAesHashedTextValid(encryptedText);
    isAesSaltValid(salt);
    SecretKey key = generateKeyWithSalt(salt);
    return decrypt(encryptedText, key);
  }

  /**
   * 시스템 솔트 + 기본 키로 암호화된 데이터를 복호화
   * @param encryptedText Base64로 인코딩된 암호문
   * @return 복호화된 평문
   */
  @Override
  public String decryptWithSystemSalt(String encryptedText) {
    isAesHashedTextValid(encryptedText);
    SecretKey key = generateKeyWithSalt(systemSalt);
    return decrypt(encryptedText, key);
  }

  /**
   * 지정된 키로 평문을 AES-GCM 암호화
   * @param plainText 암호화할 평문
   * @param key 사용할 AES 키
   * @return Base64로 인코딩된 암호문
   */
  private String encrypt(String plainText, SecretKey key) {
    secretLogger.logSecurityOperationStart(ALGORITHM, "AES-256 암호화 진행" );
    try {
      // IV 생성
      byte[] iv = generateIV();
      GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_LENGTH * 8, iv);

      // 암호화 설정
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);
      byte[] encryptedData = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

      // IV + 암호문을 결합하여 Base64 인코딩
      byte[] encryptedWithIv = new byte[iv.length + encryptedData.length];
      System.arraycopy(iv, 0, encryptedWithIv, 0, iv.length);
      System.arraycopy(encryptedData, 0, encryptedWithIv, iv.length, encryptedData.length);

      return Base64.getEncoder().encodeToString(encryptedWithIv);

    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      secretLogger.logSecurityOperationFailure(ALGORITHM, "AES-256 암호화", "알고리즘", "지원하지 않는 암호화 알고리즘", e);
      throw new IllegalArgumentException("AES-GCM 암호화 알고리즘을 찾을 수 없습니다");
    } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
      secretLogger.logSecurityOperationFailure(ALGORITHM, "AES-256 암호화", "키/파라미터", "유효하지 않은 키 또는 파라미터", e);
      throw new IllegalArgumentException( "유효하지 않은 AES 키 또는 GCM 파라미터입니다", e);
    } catch (OutOfMemoryError e) {
      secretLogger.logSecurityOperationFailure(ALGORITHM, "AES-256 암호화", "메모리", "메모리 부족", e);
      throw new OutOfMemoryError("암호화 처리 중 메모리가 부족합니다");
    } catch (Exception e) {
      secretLogger.logSecurityOperationFailure(ALGORITHM, "AES-256 암호화", "일반오류", "예상치 못한 암호화 오류", e);
      throw new IllegalArgumentException( "AES 암호화 중 오류가 발생했습니다: " + e.getMessage(), e);
    }
  }

  /**
   * 지정된 키로 암호문을 AES-GCM 복호화
   * @param encryptedText Base64로 인코딩된 암호문
   * @param key 사용할 AES 키
   * @return 복호화된 평문
   */
  private String decrypt(String encryptedText, SecretKey key) {
    secretLogger.logSecurityOperationStart(ALGORITHM, "AES-256 복호화 진행" );
    try {
      // Base64 디코딩
      byte[] encryptedWithIv = Base64.getDecoder().decode(encryptedText);

      // IV와 암호문 분리
      byte[] iv = new byte[IV_LENGTH];
      byte[] encryptedData = new byte[encryptedWithIv.length - IV_LENGTH];

      System.arraycopy(encryptedWithIv, 0, iv, 0, iv.length);
      System.arraycopy(encryptedWithIv, iv.length, encryptedData, 0, encryptedData.length);

      // 복호화 설정
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_LENGTH * 8, iv);
      cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);

      // 복호화 수행
      byte[] decryptedData = cipher.doFinal(encryptedData);
      return new String(decryptedData, StandardCharsets.UTF_8);

    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      secretLogger.logSecurityOperationFailure(ALGORITHM, "AES-256 복호화", "알고리즘", "지원하지 않는 복호화 알고리즘", e);
      throw new IllegalArgumentException( "AES-GCM 복호화 알고리즘을 찾을 수 없습니다", e);
    } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
      secretLogger.logSecurityOperationFailure(ALGORITHM, "AES-256 복호화", "키/파라미터", "유효하지 않은 키 또는 파라미터", e);
      throw new IllegalArgumentException("유효하지 않은 AES 키 또는 GCM 파라미터입니다", e);
    } catch (OutOfMemoryError e) {
      secretLogger.logSecurityOperationFailure(ALGORITHM, "AES-256 복호화", "메모리", "메모리 부족", e);
      throw new OutOfMemoryError("복호화 처리 중 메모리가 부족합니다");
    } catch (Exception e) {
      secretLogger.logSecurityOperationFailure(ALGORITHM, "AES-256 복호화", "일반오류", "예상치 못한 복호화 오류", e);
      throw new IllegalArgumentException( "AES 복호화 중 오류가 발생했습니다: " + e.getMessage(), e);
    }
  }

  /**
   * AES-256 암호화 SecretKey를 생성<br>
   * AES-256 키는 32byte로 정의되어야 한다.
   * @return 생성된 AES-256 암호화 SecretKey
   */
  private SecretKey generateKey(){
    try{
      byte[] keyBytes = Base64.getDecoder().decode(secretKey);
      return new SecretKeySpec(keyBytes, ALGORITHM);
    } catch (IllegalArgumentException illegalArgumentException){
      throw new IllegalArgumentException("Secret Key Decode Error");
    }
  }

  /**
   * AES-256 암호화 SecretKey를 생성<br>
   * AES-256 키는 32byte로 정의되어야 한다.<br>
   * 솔트을 사용하여 키를 생성
   * @param salt 키를 생성할 때 사용할 솔트
   * @return 생성된 AES-256 암호화 SecretKey
   */
  private SecretKey generateKeyWithSalt(String salt) {
    String secretWithSalt = secretKey + ":" + salt;
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] keyBytes = digest.digest(secretWithSalt.getBytes(StandardCharsets.UTF_8));
      return new SecretKeySpec(keyBytes, ALGORITHM);
    } catch (IllegalArgumentException illegalArgumentException){
      throw new IllegalArgumentException( "Secret Key Decode Error");
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new IllegalArgumentException("Secret Key Algorithm Error");
    }
  }

  /**
   * 안전한 랜덤 IV를 생성합니다.
   * @return IV_LENGTH 바이트 IV
   */
  private byte[] generateIV() {
    byte[] iv = new byte[IV_LENGTH];
    new SecureRandom().nextBytes(iv);
    return iv;
  }

  /**
   * SALT 검증
   * @param salt 검증받을 솔트
   * @throws SecurityException 솔트가 올바르지 않음
   */
  private void isAesSaltValid(String salt) {
    if(isInValidationSalt(salt)){
      secretLogger.logValidationError("솔트 검증", "솔트가 올바르지 않는다.");
      throw new SecurityException( "솔트가 올바르지 않습니다.");
    }
  }

  /**
   * 평문 검증
   * @param plainText 검증받을 평문
   * @throws SecurityException 평문이 올바르지 않음
   */
  private void isAesPlainTextValid(String plainText) {
    if(isInValidPlainText(plainText)){
      secretLogger.logValidationError("평문 검증", "평문이 올바르지 않는다.");
      throw new SecurityException("암호화 대상 텍스트가 올바르지 않습니다.");
    }
  }
  /**
   * 암호문 검증
   * @param encryptedText 검증 받을 암호문
   * @throws SecurityException 암호문이 올바르지 않음
   */
  private void isAesHashedTextValid(String encryptedText) {
    if(isInValidPlainText(encryptedText)){
      secretLogger.logValidationError("암호문 검증", "암호문이 올바르지 않는다.");
      throw new SecurityException("암호문이 올바르지 않습니다.");
    }
  }
  /**
   * SHA3-256 해싱에 사용할 랜덤 솔트 생성
   * @return  SHA3-256 해싱에 사용할 랜덤 솔트
   */
  public String generateSalt() {
    return generateSalt(SALT_LENGTH);
  }
}
