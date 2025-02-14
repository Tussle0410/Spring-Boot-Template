package com.ch.logback.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
@Slf4j
public class LogController {

  /**
   * Logback 로그 출력
   * @description LogLevel(TRACE, DEBUG, INFO, WARN, ERROR)에 따라 로그 출력
   * @return ResponseEntity<String> - 로그 호출 내용 설명
   */
  @GetMapping
  public ResponseEntity<String> printLogBack(){
    log.trace("trace log");
    log.debug("debug log");
    log.info("info log");
    log.warn("warn log");
    log.error("error log");
    return ResponseEntity.ok("print logback log");
  }
}
