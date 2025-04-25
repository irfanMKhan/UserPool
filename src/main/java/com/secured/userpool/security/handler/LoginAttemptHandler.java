package com.secured.userpool.security.handler;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class LoginAttemptHandler {

    @Value(value = "${application.login.attempt}")
    private Integer MAX_ATTEMPT;

    private final LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptHandler(@Value("${application.ip.lockout.duration}") Integer ipLockoutDuration) {
        Integer userIpLockoutDuration = ipLockoutDuration;
        attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(userIpLockoutDuration, TimeUnit.MINUTES).build(new CacheLoader<>() {
            @Nonnull
            @Override
            public Integer load(@Nonnull String key) {
                return 0;
            }
        });
    }

    public void loginSucceeded(HttpServletRequest servletRequest) {
        String key = getClientIP(servletRequest);
        attemptsCache.invalidate(key);
    }

    public void loginFailed(HttpServletRequest servletRequest) {
        String key = getClientIP(servletRequest);
        int attempts;
        try {
            attempts = attemptsCache.get(key);
        } catch (final ExecutionException exception) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(HttpServletRequest servletRequest) {
        String key = getClientIP(servletRequest);
        Integer maxAttempt = MAX_ATTEMPT;
        try {
            return attemptsCache.get(key) >= maxAttempt;
        } catch (final ExecutionException exception) {
            return false;
        }
    }

    public String getClientIP(HttpServletRequest servletRequest) {
        final String xfHeader = servletRequest.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        return servletRequest.getRemoteAddr();
    }

}
