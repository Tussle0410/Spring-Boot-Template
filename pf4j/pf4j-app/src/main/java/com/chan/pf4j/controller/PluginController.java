package com.chan.pf4j.controller;

import com.chan.pf4j.Operator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.PluginManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plugins")
@Slf4j
public class PluginController {

  private final PluginManager pluginManager;

  @GetMapping("/load")
  public void loadPlugins() {
    pluginManager.loadPlugins();
  }

  @GetMapping("/unload")
  public void unloadPlugins() {
    pluginManager.unloadPlugins();
  }


  @GetMapping("/start")
  public void startPlugins() {
    pluginManager.startPlugins();
  }

  @GetMapping("/start/{pluginId}")
  public void startPluginByPluginId(@PathVariable String pluginId) {
    pluginManager.startPlugin(pluginId);
  }

  @GetMapping("/stop")
  public void stopPlugins() {
    pluginManager.stopPlugins();
  }

  @GetMapping("/stop/{pluginId}")
  public void stopPluginByPluginId(@PathVariable String pluginId) {
    pluginManager.stopPlugin(pluginId);
  }
  @GetMapping("/operator")
  public void operator(@RequestParam int a, @RequestParam int b) {
    List<Operator> extensions = pluginManager.getExtensions(Operator.class);
    printOperatorLog(a, b, extensions);
  }

  @GetMapping("/operator/{pluginId}")
  public void operatorByPluginId(@RequestParam int a, @RequestParam int b, @PathVariable String pluginId) {
    List<Operator> extensions = pluginManager.getExtensions(Operator.class, pluginId);
    printOperatorLog(a, b, extensions);
  }

  private void printOperatorLog(int a, int b, List<Operator> extensions) {
    for (Operator operator : extensions) {
      log.info("==================================================================");
      log.info("[------------------------ {}------------------------] ", operator.getOperatorName());
      log.info("a = {}, b = {}", a, b);
      log.info("plus >>> {}", operator.plus(a, b));
      log.info("minus >>> {}", operator.minus(a, b));
      log.info("multiply >>> {}", operator.multiply(a, b));
      log.info("divide >>> {}", operator.divide(a, b));
      log.info("remainder >>> {}", operator.remainder(a, b));
      log.info("==================================================================");
    }
  }
}
