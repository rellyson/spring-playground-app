package io.rellyson.playground.core.filters;

import io.rellyson.playground.core.entities.LoggingBody;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Component
@Profile("production")
public class JsonLoggingFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Instant start = Instant.now();
        ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);

        LoggingBody loggingBody = new LoggingBody(
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                Duration.between(start,Instant.now())
        );

        this.logger.info(loggingBody.toJsonString());

        cachingResponseWrapper.copyBodyToResponse();
    }
}
