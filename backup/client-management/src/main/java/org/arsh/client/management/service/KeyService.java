package org.arsh.client.management.service;

import java.security.NoSuchAlgorithmException;

public interface KeyService {
    String generateKey();
    String hashKey(String key) throws NoSuchAlgorithmException;

}
