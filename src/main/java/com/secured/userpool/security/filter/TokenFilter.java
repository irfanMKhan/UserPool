package com.secured.userpool.security.filter;

import com.secured.userpool.model.dto.TokenDTO;
import com.secured.userpool.service.UserService;
import com.secured.userpool.utility.TokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Objects;

public class TokenFilter extends AuthenticationFilter {

    private final UserService userService;
    private final TokenManager tokenManager;

    @Value("${application.date.formatter}")
    private String dateTimeFormatter;

    public TokenFilter(UserService userService, TokenManager tokenManager) {
        super(
                new ProviderManager(Collections.singletonList(new DaoAuthenticationProvider())),
                new BasicAuthenticationConverter()
        );
        this.userService = userService;
        this.tokenManager = tokenManager;
    }

    private String getToken(HttpServletRequest request) {
        String requestHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(requestHeader) && requestHeader.startsWith("Bearer")) {
            return requestHeader.substring(7);
        }

        throw new RuntimeException("Token not found");
    }

    private Boolean checkOpenURL(HttpServletRequest request) {
        boolean check = Boolean.FALSE;
        String[] open_url = {"/user"};
        for (String url : open_url) {
            check = request.getRequestURI().contains(url);
            if (check && Objects.equals(request.getMethod(), "POST"))
                break;
        }
        return check;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        ZonedDateTime now = LocalDateTime.now().atZone(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormatter);
        String formattedDateTime = formatter.format(now);

        try {
            if (checkOpenURL(request)) {
                filterChain.doFilter(request, response);
            } else {
                String token = getToken(request);
                TokenDTO validatedToken = tokenManager.validateToken(token);

                if (validatedToken.getStatus()) {
                    String username = validatedToken.getSubject();
                    UserDetails userDetails = userService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    filterChain.doFilter(request, response);
                } else {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType("application/json");

                    String errorMessage = "{" +
                            "\"timestamp\": \"" + formattedDateTime + "\"," +
                            "\"status\": \"" + HttpStatus.FORBIDDEN.value() + "\"," +
                            "\"error\": \"" + "token invalid" + "\"," +
                            "\"path\": \"" + request.getRequestURI() + "\"" +
                            "}";

                    response.getWriter().write(errorMessage);
                    response.getWriter().flush();
                }
            }
        } catch (Exception exception) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json");

            String errorMessage = "{" +
                    "\"timestamp\": \"" + formattedDateTime + "\"," +
                    "\"status\": \"" + HttpStatus.INTERNAL_SERVER_ERROR.value() + "\"," +
                    "\"error\": \"" + exception.getMessage() + "\"," +
                    "\"path\": \"" + request.getRequestURI() + "\"" +
                    "}";

            response.getWriter().write(errorMessage);
            response.getWriter().flush();
        }
    }
}
