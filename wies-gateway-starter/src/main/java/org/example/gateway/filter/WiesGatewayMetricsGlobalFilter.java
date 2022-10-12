package org.example.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class WiesGatewayMetricsGlobalFilter implements GlobalFilter, Ordered {

  private static final Logger log =  LoggerFactory.getLogger(WiesGatewayMetricsGlobalFilter.class);

  public WiesGatewayMetricsGlobalFilter() {
    log.info("WiesGatewayMetricsGlobalFilter 启用");
  }

  private static final String START_TIME = "WiesMetricsStartTime";

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
    return chain.filter(exchange).doOnSuccess(aVoid -> endTimerRespectingCommit(exchange, null))
        .doOnError(throwable -> endTimerRespectingCommit(exchange, throwable));
  }

  private void endTimerRespectingCommit(ServerWebExchange exchange, Throwable myThrowable) {
    ServerHttpResponse response = exchange.getResponse();
    if (response.isCommitted()) {
      endTimerInner(exchange, myThrowable);
    } else {
      response.beforeCommit(() -> {
        endTimerInner(exchange, myThrowable);
        return Mono.empty();
      });
    }
  }

  private void endTimerInner(ServerWebExchange exchange, Throwable throwable) {
    Long startTime = exchange.getAttribute(START_TIME);
    if (startTime != null) {
      long executeTime = (System.currentTimeMillis() - startTime);

      if (throwable != null) {
        log.error("Gateway Time Monitor SPEND {} MS",  executeTime, throwable);
      } else {
        log.info("Gateway Time Monitor SPEND {} MS",  executeTime);
      }
    }
  }

  @Override
  public int getOrder() {
    return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER + 1;
  }
}
