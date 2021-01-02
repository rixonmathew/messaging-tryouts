package com.rixon.learn.tibcoems;


import com.rixon.learn.tibcoems.service.MessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class TibcoEmsApplication {

    public final static Logger LOGGER = LoggerFactory.getLogger(TibcoEmsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TibcoEmsApplication.class, args);
    }

    @Bean
    RouterFunction<ServerResponse> routerFunction(MessagingService messagingService) {
        return route(GET("/publishcontractsems/{count}"),
                serverRequest -> ok().body(messagingService.publishContractsToEMSQueue(Integer.parseInt(serverRequest.pathVariable("count"))), String.class))
                .andRoute(GET("/publishcontractskafka/{count}"),
                        serverRequest -> ok().body(messagingService.publishContractsToKafkaDestinations(Integer.parseInt(serverRequest.pathVariable("count"))), String.class));
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            LOGGER.info("Running Command line runner");
        };
    }
}
