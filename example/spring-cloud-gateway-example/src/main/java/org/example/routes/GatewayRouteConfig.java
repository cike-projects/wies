package org.example.routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRouteConfig {

  @Bean
  public RouteLocator routes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("nacos-consumer", r ->
            r.path("/consumer/**").uri("lb://spring-cloud-webmvc-nacos-consumer")
        )
        .route("risk-async-engine", r ->
            r.path("/**").uri("lb://spring-cloud-webmvc-nacos")
        )
        .build();
  }
}
