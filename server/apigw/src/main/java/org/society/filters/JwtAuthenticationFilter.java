package org.society.filters;

import org.society.utils.JwtTokenProvider;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        super(Config.class);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            final List<String> apiEndpoints = List.of(
                    "/auth/sign-in",
                    "/auth/sign-up",
                    "swagger-ui",
                    "api-docs"
            );

            Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                    .noneMatch(uri -> r.getURI().getPath().contains(uri));

            if (isApiSecured.test(request)) {
                if (!request.getHeaders().containsKey("Authorization")) {
                    return sendUnauthorized(exchange);
                }

                String token = request.getHeaders().getOrEmpty("Authorization").get(0);
                boolean isValid = jwtTokenProvider.validateJwtToken(token);
                if (!isValid) {
                    return sendUnauthorized(exchange);
                }
                String userRoles = jwtTokenProvider.getRolesFromJwtToken(token);
                String userId = jwtTokenProvider.getUserIdFromJwtToken(token);
                String username = jwtTokenProvider.getUsernameFromJwtToken(token);

                //append userId and roles to http headers
                exchange.getRequest()
                        .mutate()
                        .header("X-User-Id", userId)
                        .header("X-User-Roles", userRoles)
                        .header("X-Username", username)
                        .build();
            }

            return chain.filter(exchange);
        });
    }

    private Mono<Void> sendUnauthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    public static class Config {
    }
}
