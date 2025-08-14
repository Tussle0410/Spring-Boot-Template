package com.ch.openfeign.openapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FeignOpenApiController {

  @GetMapping("/hello-world")
  public String helloWorld() {
    return "Hello World";
  }

  @GetMapping("/time-out")
  public String timeOut(){
    try{
      Thread.sleep(10000L);
      return "None Time Out";
    }catch (InterruptedException interruptedException) {
      log.error(interruptedException.getMessage(), interruptedException);
      Thread.currentThread().interrupt();
      throw new IllegalArgumentException(interruptedException);
    }
  }

  @GetMapping("/path-variable/{id}")
  public String pathVariable(@PathVariable String id){
    return id;
  }

  @GetMapping("/param-variable")
  public String parmVariable(@RequestParam String id){
    return id;
  }

  @PostMapping("/create/without-body")
  public String createWithoutBody() {
    return "without body";
  }

  @PostMapping("/create/with-body")
  public String createWithBody(@RequestBody String body) {
    return body;
  }
}
