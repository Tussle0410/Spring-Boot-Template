package com.ch.openfeign.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ch.openfeign.config.FeignTest;
import feign.RetryableException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@FeignTest
@DisplayName("OpenFeignClientTest")
class OpenFeignClientTest {
  @Autowired
  private OpenFeignClient openFeignClient;

  @Test
  @DisplayName("기본 Hello World 응답을 정상적으로 반환하는지 테스트")
  void helloWorld() {
    String s = openFeignClient.helloWorld();
    assertThat(s).isEqualTo("Hello World");
  }

  @Test
  @DisplayName("타임아웃 발생 시 RetryableException이 발생하는지 테스트")
  void timeOut(){
    assertThatThrownBy(() ->openFeignClient.timeOut()).isInstanceOf(RetryableException.class);
  }

  @Test
  @DisplayName("Path Variable을 사용한 API 호출이 정상 동작하는지 테스트")
  void pathVariable(){
    String pathVariable = "Path";
    String response = openFeignClient.pathVariable(pathVariable);
    assertThat(response).isEqualTo(pathVariable);
  }


  @Test
  @DisplayName("Query Parameter를 사용한 API 호출이 정상 동작하는지 테스트")
  void paramVariable(){
    String paramVariable = "Param";
    String response = openFeignClient.paramVariable(paramVariable);
    assertThat(response).isEqualTo(paramVariable);
  }


  @Test
  @DisplayName("Request Body 없이 POST 요청이 정상 동작하는지 테스트")
  void createWithoutBody(){
    String response = openFeignClient.createWithoutBody();
    assertThat(response).isEqualTo("without body");
  }

  @Test
  @DisplayName("Request Body와 함께 POST 요청이 정상 동작하는지 테스트")
  void createOfWithBody(){
    String body = "Body";
    String response = openFeignClient.createOfWithBody(body);
    assertThat(response).isEqualTo(body);
  }







}
