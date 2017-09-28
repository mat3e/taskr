package io.github.mat3e.jhipster.taskr.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "io.github.mat3e.jhipster.taskr")
public class FeignConfiguration {

}
