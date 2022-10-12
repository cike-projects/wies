package org.example.controller;

import java.util.Map;
import org.example.api.NamedApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("consumer/named")
public class ConsumerController {

  @Autowired
  private NamedApi namedApi;

  @GetMapping("discovery")
  public Map<String, String> discovery() {
    Map<String, String> namedInfo = namedApi.discovery();
    namedInfo.put("self", "spring-cloud-webmvc-nacos-consumer");
    return namedInfo;
  }

}
