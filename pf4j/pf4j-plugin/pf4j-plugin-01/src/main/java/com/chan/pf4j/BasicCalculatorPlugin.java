package com.chan.pf4j;

import lombok.extern.slf4j.Slf4j;
import org.pf4j.Plugin;
@Slf4j
public class BasicCalculatorPlugin extends Plugin {

    @Override
    public void start() {
        log.info("기본 계산기 플로그인이 시작되었습니다.");

    }

    @Override
    public void stop() {
        log.info("기본 계산기 플로그인이 중지되었습니다.");
    }

}
