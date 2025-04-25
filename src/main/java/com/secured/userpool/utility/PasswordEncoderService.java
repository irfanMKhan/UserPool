package com.secured.userpool.utility;

import com.google.common.hash.Hashing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordEncoderService implements PasswordEncoder {

    private final EncodeAlgorithm useAlgorithm;

    public PasswordEncoderService() {
        this.useAlgorithm = EncodeAlgorithm.sha512;
    }

    public PasswordEncoderService(EncodeAlgorithm setAlgorithm) {
        this.useAlgorithm = setAlgorithm;
    }

    private String sha256Hash(CharSequence password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

    private String sha386Hash(CharSequence password) {
        return Hashing.sha384().hashString(password, StandardCharsets.UTF_8).toString();
    }

    private String sha512Hash(CharSequence password) {
        return Hashing.sha512().hashString(password, StandardCharsets.UTF_8).toString();
    }

    public String encode(CharSequence rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("Password empty");
        } else {
            return switch (useAlgorithm) {
                case sha256 -> sha256Hash(rawPassword);
                case sha386 -> sha386Hash(rawPassword);
                case sha512 -> sha512Hash(rawPassword);
                default -> throw new ExceptionInInitializerError("No EncodeAlgorithm Initialized");
            };
        }
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("Password empty");
        } else if (encodedPassword == null || encodedPassword.isEmpty()) {
            throw new IllegalArgumentException("Encoded password empty");
        } else {
            String matchPassword = sha512Hash(rawPassword);
            return MessageDigest.isEqual(encodedPassword.getBytes(StandardCharsets.UTF_8), matchPassword.getBytes(StandardCharsets.UTF_8));
        }
    }

    public enum EncodeAlgorithm {
        sha256,
        sha386,
        sha512
    }
}
