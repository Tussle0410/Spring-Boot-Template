package com.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestBean {
    @Bean
    public String commonTest(){
        log.info("Test Bean {}", "등록 완료!");
        return "Test Bean";
    }
}
