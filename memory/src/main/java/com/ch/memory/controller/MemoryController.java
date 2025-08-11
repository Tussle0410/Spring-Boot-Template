package com.ch.memory.controller;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * VMOption : -Xlog:gc*:file=gc.log:time,uptime,level,tags -Xmn100m -Xmx500m
 */
@RestController
@Slf4j
@RequestMapping("/memory")
public class MemoryController {
  private final List<String>  memory = new ArrayList<>();
  @GetMapping("/leak")
  public void leak(){
    log.info("Memory Leak Test");
    for(int i = 0; i < 10000000; i++){
      memory.add(String.valueOf(i));
    }
    log.info("Memory Leak Test End");
  }
  @GetMapping("/leak/half")
  public void leakHalf(){
    List<String> stringList = new ArrayList<>();
    log.info("Memory Leak  Half Test");
    for(int i = 0; i < 5000000; i++){
      stringList.add(String.valueOf(i));
    }
    log.info("Memory Leak Half Test End");
  }

  @GetMapping("/clear")
  public void clear(){
    log.info("Memory Clear");
    memory.clear();
  }

  @GetMapping("/health-check")
  public String healthCheck(){
    return "ok";
  }
}
