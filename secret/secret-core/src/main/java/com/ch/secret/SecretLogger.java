package com.ch.secret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 보안 관련 로깅을 위한 공통 유틸리티 클래스
 * 암호화, 복호화, 해싱 등의 보안 작업에 대한 표준화된 로깅 제공
 */
@Slf4j
@Component
public class SecretLogger {
  /**
   * 암호화 작업 시작 로그
   * @param algorithm 사용된 알고리즘
   * @param operation 수행 작업 (encrypt, decrypt, hash 등)
   */
  public void logSecurityOperationStart(String algorithm, String operation) {
    log.debug("보안 작업 시작 - 알고리즘: {}, 작업: {}", algorithm, operation);
  }

  /**
   * 암호화 작업 성공 로그
   * @param algorithm 사용된 알고리즘
   * @param operation 수행 작업
   */
  public void logSecurityOperationSuccess(String algorithm, String operation) {
    log.debug("보안 작업 성공 - 알고리즘: {}, 작업: {}", algorithm, operation);
  }

  /**
   * 암호화 작업 실패 로그
   * @param algorithm 사용된 알고리즘
   * @param operation 수행 작업
   * @param errorType 오류 유형
   * @param errorMessage 오류 메시지
   * @param throwable 예외 객체
   */
  public void logSecurityOperationFailure(String algorithm, String operation,
      String errorType, String errorMessage, Throwable throwable) {
    log.error("보안 작업 실패 - 알고리즘: {}, 작업: {}, 오류유형: {}, 메시지: {}", algorithm, operation, errorType, errorMessage, throwable);
  }

  /**
   * 입력값 검증 실패 로그
   * @param operation 수행 작업
   * @param validationError 검증 오류 내용
   */
  public void logValidationError(String operation, String validationError) {
    log.warn("입력값 검증 실패 - 작업: {}, 오류: {}", operation, validationError);
  }

  /**
   * 성능 측정 로그 (밀리초 단위)
   * @param algorithm 사용된 알고리즘
   * @param operation 수행 작업
   * @param executionTimeMs 실행 시간 (밀리초)
   */
  public void logPerformance(String algorithm, String operation, long executionTimeMs) {
    if (executionTimeMs > 1000) {
      log.warn("보안 작업 성능 경고 - 알고리즘: {}, 작업: {}, 실행시간: {}ms", algorithm, operation, executionTimeMs);
    } else {
      log.debug("보안 작업 성능 - 알고리즘: {}, 작업: {}, 실행시간: {}ms", algorithm, operation, executionTimeMs);
    }
  }

  /**
   * 일반 디버그 로그
   * @param message 로그 메시지
   */
  public void debug(String message) {
    log.debug(message);
  }

  /**
   * 일반 정보 로그
   * @param message 로그 메시지
   */
  public void info(String message) {
    log.info(message);
  }

  /**
   * 일반 경고 로그
   * @param message 로그 메시지
   */
  public void warn(String message) {
    log.warn(message);
  }

  /**
   * 일반 오류 로그
   * @param message 로그 메시지
   * @param throwable 예외 객체
   */
  public void error(String message, Throwable throwable) {
    log.error(message, throwable);
  }
}