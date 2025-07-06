package org.arsh.client.management.service.impl;

import org.arsh.client.management.service.KeyService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class KeyServiceImpl implements KeyService {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder  base64Encoder = Base64.getUrlEncoder().withoutPadding();

    @Override
    public String generateKey() {
        byte[] randomNumber = new byte[24];
        secureRandom.nextBytes(randomNumber);

        return base64Encoder.encodeToString(randomNumber);
    }

    @Override
    public String hashKey(String key) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(key.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

}
