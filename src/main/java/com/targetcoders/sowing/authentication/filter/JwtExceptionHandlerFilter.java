package com.targetcoders.sowing.authentication.filter;

import com.targetcoders.sowing.authentication.exception.InvalidTokenException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (InvalidTokenException e) {
            response.setHeader("Location", "/login/google");
            response.setStatus(HttpServletResponse.SC_FOUND);
        }
    }
}
