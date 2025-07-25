package com.company.employeeapp.openfeignclients;

import feign.Feign;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;

//@LoadBalancerClient(value = "ADDRESS-SERVICE", configuration = MyCustomLoadBalancer.class)
@LoadBalancerClient(value = "ADDRESS-SERVICE")//default configuration is round robbin
public class AddressServiceLoadBalancer {

    @LoadBalanced
    @Bean
    public Feign.Builder feignBuilder(){
        return Feign.builder();
    }
}
