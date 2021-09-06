package com.yangzhuo.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
public class GatewayConfiguration {
    
    @PostConstruct
    public void doInit() {
        //限流回调函数
        System.out.println("限流回调函数");
        GatewayCallbackManager.setBlockHandler(new BlockRequestHandler() {
            // 当请求被限流是调用的方法
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                return ServerResponse.status(200).syncBody("系统繁忙,请稍后!");
            }
        });
    }
}
