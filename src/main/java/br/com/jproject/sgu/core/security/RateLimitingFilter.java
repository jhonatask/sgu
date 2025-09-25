package br.com.jproject.sgu.core.security;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.github.bucket4j.Bandwidth;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    
    // Configuração de rate limiting
    private static final int REQUESTS_PER_MINUTE = 60;
    private static final int REQUESTS_PER_HOUR = 1000;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        
        String clientIp = getClientIpAddress(request);
        Bucket bucket = getBucket(clientIp);
        
        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("{\"error\":\"Rate limit exceeded. Please try again later.\"}");
            response.setContentType("application/json");
        }
    }
    
    private Bucket getBucket(String clientIp) {
        return buckets.computeIfAbsent(clientIp, this::createNewBucket);
    }
    
    private Bucket createNewBucket(String clientIp) {
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(REQUESTS_PER_MINUTE, Refill.intervally(REQUESTS_PER_MINUTE, Duration.ofMinutes(1))))
                .addLimit(Bandwidth.classic(REQUESTS_PER_HOUR, Refill.intervally(REQUESTS_PER_HOUR, Duration.ofHours(1))))
                .build();
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
}
