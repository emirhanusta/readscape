package gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RouteValidator {
    Logger log = LoggerFactory.getLogger(RouteValidator.class);

    public static final Map<String, List<HttpMethod>> openApiEndpoints = new HashMap<>() {{
        put("/api/v1/books", List.of(HttpMethod.GET));
        put("/api/v1/authors", List.of(HttpMethod.GET));
        put("/api/v1/reviews", List.of(HttpMethod.GET));
        put("/api/v1/accounts", List.of(HttpMethod.GET));
    }};

    public static final Map<String, List<HttpMethod>> requiredAdminRoleEndpoints = new HashMap<>() {{
          put("/api/v1/books", List.of(HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE));
          put("/api/v1/authors", List.of(HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE));
     }};

    protected boolean securityFilter(ServerHttpRequest request, Map<String, List<HttpMethod>> endpoints) {
        String path = request.getURI().getPath();
        HttpMethod method = request.getMethod();
        log.info("Request Path: {}, Method: {}", path, method);

        for (Map.Entry<String, List<HttpMethod>> entry : endpoints.entrySet()) {
            String endpoint = entry.getKey();
            List<HttpMethod> allowedMethods = entry.getValue();

            if (path.contains(endpoint) && allowedMethods.contains(method)) {
                return false;
            }
        }
        return true;
    }
}
