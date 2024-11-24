package com.targetcoders.sowing.authentication.filter;

import com.targetcoders.sowing.authentication.exception.InvalidSessionIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (InvalidSessionIdException e) {
            log.error("invalid sessionId error", e);
            response.setHeader("Location", "/login/google");
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        }
    }
}
