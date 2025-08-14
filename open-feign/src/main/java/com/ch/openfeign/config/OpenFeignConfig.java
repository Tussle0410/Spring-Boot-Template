package com.ch.openfeign.config;

import feign.Logger;
import feign.Retryer;
import java.util.concurrent.TimeUnit;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

@Configuration
@EnableFeignClients(basePackages = "com.ch.openfeign")
public class OpenFeignConfig {

  @Bean
  Retryer.Default retryer(){
    return new Retryer.Default(100L, TimeUnit.SECONDS.toMillis(3L), 5);
  }

  @Bean
  Logger.Level feignLoggerLevel(){
    return Logger.Level.FULL;
  }

  @Bean
  public FeignFormatterRegistrar dateTimeFormatterRegistrar() {
    return registry -> {
      DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
      registrar.setUseIsoFormat(true);
      registrar.registerFormatters(registry);
    };
  }
}
