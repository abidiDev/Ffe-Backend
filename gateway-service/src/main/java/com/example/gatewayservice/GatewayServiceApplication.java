package com.example.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
@EnableDiscoveryClient

public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
/*
  @Bean
    RouteLocator routeLocator(RouteLocatorBuilder buider){
        return buider.routes()
                .route((r)->r.path("customers/").uri("lb://company-service")).build();
    }
*/
    //with this method the gateway see the recieved uri take the name of the service from it then contacts the registerservivice to get the specific adress of the service needed
    //it use load balacing automatically(by ribon service)
   @Bean
    DiscoveryClientRouteDefinitionLocator drfinitionLocator(ReactiveDiscoveryClient rdc,
                                                            DiscoveryLocatorProperties properties){
        return new DiscoveryClientRouteDefinitionLocator(rdc, properties);
    }
}
