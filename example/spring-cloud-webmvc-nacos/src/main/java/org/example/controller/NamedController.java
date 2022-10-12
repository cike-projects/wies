package org.example.controller;

import com.alibaba.nacos.shaded.com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("named")
public class NamedController {

  @GetMapping("discovery")
  public Map<String, String> discovery() {
    return ImmutableMap.of("name", "spring-cloud-webmvc-nacos");
  }

}
