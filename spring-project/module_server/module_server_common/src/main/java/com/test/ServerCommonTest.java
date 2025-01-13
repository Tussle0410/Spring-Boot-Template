package com.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ServerCommonTest {

    @Bean
    public String serverCommonBeanTest(){
        log.info("ServerCommonTest.test()");
        return "ServerCommonTest.test()";
    }
}
