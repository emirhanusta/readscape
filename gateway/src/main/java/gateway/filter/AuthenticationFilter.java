package gateway.filter;

import gateway.exception.CustomException;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import gateway.util.JwtUtil;

import java.util.Objects;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final RouteValidator validator;
    private final JwtUtil jwtUtil;

    public AuthenticationFilter(RouteValidator validator, JwtUtil jwtUtil) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (validator.securityFilter(request,RouteValidator.openApiEndpoints)) {
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new CustomException("Authorization header is missing", HttpStatus.UNAUTHORIZED);
                }

                String authHeader = Objects.requireNonNull(request.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                validateToken(authHeader);
                validateRole(authHeader, request);
            }

            return chain.filter(exchange);
        };
    }

    private void validateToken(String authHeader) {
        try {
            jwtUtil.validateToken(authHeader);
        } catch (Exception e) {
            log.error("Error validating token: {}", e.getMessage());
            throw new CustomException("Invalid token", HttpStatus.UNAUTHORIZED);
        }
    }

    private void validateRole(String authHeader, ServerHttpRequest request) {
        Claims claims = jwtUtil.getClaims(authHeader);
        if (!claims.get("role").equals("ADMIN") && !validator.securityFilter(request, RouteValidator.requiredAdminRoleEndpoints)) {
            log.error("Admin role required for this endpoint {}", request.getURI().getPath());
            throw new CustomException("Admin role required", HttpStatus.FORBIDDEN);
        }
    }
    public static class Config {
    }
}
