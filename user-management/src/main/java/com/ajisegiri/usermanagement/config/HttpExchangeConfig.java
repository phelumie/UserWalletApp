package com.ajisegiri.usermanagement.config;

import com.ajisegiri.usermanagement.exceptions.FirebaseException;
import com.ajisegiri.usermanagement.webclient.FirebaseClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class HttpExchangeConfig {

    @Bean
    FirebaseClient firebaseClient() {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build();
        WebClient webClient = WebClient.builder()
//                .baseUrl("")
                .exchangeStrategies(exchangeStrategies)
                .filter(logRequest())
                .build();

        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .blockTimeout(Duration.ofMillis(30000))
                .build()
                .createClient(FirebaseClient.class);
    }
    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().isError()) {
                return clientResponse.bodyToMono(ErrorResponse.class)
                        .flatMap(errorMessage -> {
                            log.error("Error response received: {} - {}", clientResponse.statusCode(), errorMessage);

                            return Mono.error(new FirebaseException(errorMessage.error().message(),clientResponse.statusCode().value()));

                        });
            }
            return Mono.just(clientResponse);
        });
    }
    private record ErrorResponse(ErrorDetail error) {

        public record ErrorDetail(int code, String message, List<ErrorItem> errors) {

            public record ErrorItem(String message, String domain, String reason) {
            }
        }
    }


}
