package com.ch.openfeign.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@ImportAutoConfiguration({
    OpenFeignConfig.class,
    FeignAutoConfiguration.class,
    HttpMessageConvertersAutoConfiguration.class,
})
class FeignTestContext {

}