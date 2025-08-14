package com.ch.openfeign.controller;

import com.ch.openfeign.client.OpenFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OpenFeignController {

  private final OpenFeignClient openFeignClient;

  @GetMapping("/hello-world")
  public String helloWorld() {
    return openFeignClient.helloWorld();
  }

  @GetMapping("/time-out")
  public String timeOut() {
      return openFeignClient.timeOut();
  }

}
