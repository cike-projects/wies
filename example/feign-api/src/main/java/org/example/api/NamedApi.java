package org.example.api;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "spring-cloud-webmvc-nacos", path = "named")
public interface NamedApi {

  @GetMapping("discovery")
  Map<String, String> discovery();
}
