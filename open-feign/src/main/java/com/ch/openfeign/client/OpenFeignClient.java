package com.ch.openfeign.client;

import feign.RetryableException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "open-api-server", url = "http://localhost:8081/ch/openfeign/open-api")
public interface OpenFeignClient {

  @GetMapping("/hello-world")
  String helloWorld();

  @GetMapping("/time-out")
  String timeOut() throws RetryableException;

  @GetMapping("/path-variable/{id}")
  String pathVariable(@PathVariable String id);

  @GetMapping("/param-variable")
  String paramVariable(@RequestParam String id);

  @PostMapping("/create/without-body")
  String createWithoutBody();

  @PostMapping("/create/with-body")
  String createOfWithBody(
      @RequestBody String body
  );

}
