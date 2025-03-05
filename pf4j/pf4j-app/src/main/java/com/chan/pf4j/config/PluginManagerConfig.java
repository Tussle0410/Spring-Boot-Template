package com.chan.pf4j.config;

import java.io.File;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.JarPluginManager;
import org.pf4j.PluginManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PluginManagerConfig {

  @Bean
  public PluginManager pluginManager() {

    // plugin .jar file folder path
    String pathString = "/Users/chanhyeonglee/Downloads/Repo/아키텍처 설계/Spring-Boot-Template/pf4j/pf4j-app/plugins";
    Path plugins = new File(pathString).toPath();
    return new JarPluginManager(plugins);
  }
}
