package com.secured.userpool.security;

import com.secured.userpool.security.filter.TokenFilter;
import com.secured.userpool.security.handler.EntryDeniedHandler;
import com.secured.userpool.security.handler.EntryPointHandler;
import com.secured.userpool.security.handler.LoginAttemptHandler;
import com.secured.userpool.service.UserService;
import com.secured.userpool.utility.PasswordEncoderService;
import com.secured.userpool.utility.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    private final UserService userService;
    private final TokenManager tokenManager;
    private final LoginAttemptHandler loginAttemptHandler;

    private static CorsConfiguration getCorsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
        corsConfiguration.setExposedHeaders(Collections.singletonList("Authorization"));
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        return corsConfiguration;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoderService();
    }

    @Bean
    public EntryDeniedHandler accessDeniedHandler() {
        return new EntryDeniedHandler();
    }

    @Bean
    public EntryPointHandler authenticationEntryPoint() {
        return new EntryPointHandler(loginAttemptHandler);
    }

    @Bean
    public TokenFilter tokenFilter() {
        return new TokenFilter(userService, tokenManager);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        CorsConfiguration corsConfiguration = getCorsConfiguration();

        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.cors(cors -> cors.configurationSource(request -> corsConfiguration));

        http.exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(accessDeniedHandler()));
        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authenticationEntryPoint()));

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(request -> request
//                .requestMatchers("api/v1").authenticated()
//                .requestMatchers("api/v1/user").permitAll()
                .anyRequest().permitAll());

        http.authenticationManager(authenticationManager);

        return http.build();
    }

}
