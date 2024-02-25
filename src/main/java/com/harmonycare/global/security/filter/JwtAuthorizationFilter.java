package com.harmonycare.global.security.filter;

import com.harmonycare.domain.member.service.MemberService;
import com.harmonycare.global.security.provider.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends GenericFilterBean {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;

        if (((HttpServletRequest) request).getMethod().equals("OPTIONS")) {
            chain.doFilter(request, response);
        }

        String token = jwtTokenProvider.resolveToken(servletRequest);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication
                    = jwtTokenProvider.getAuthentication(token, memberService);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
