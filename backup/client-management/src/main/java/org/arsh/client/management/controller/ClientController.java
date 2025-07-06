package org.arsh.client.management.controller;

import org.arsh.client.management.service.KeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    private KeyService keyService;

    public ClientController(KeyService keyService){
        this.keyService = keyService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerClient(){
        return null;
    }

    @GetMapping("/api-keys")
    public ResponseEntity<Void> getApiKeys(){
        return null;
    }

    @PostMapping("/api-keys/generate")
    public String generateApiKeys(){
        return keyService.generateKey();
    }

    @GetMapping("/api-keys/getHash/{key}")
    public String generateApiKeys(@PathVariable String key) throws NoSuchAlgorithmException {
        return keyService.hashKey(key);
    }

    @PutMapping("/api-keys/rotate")
    public ResponseEntity<Void> rotateApiKeys(){
        return null;
    }

    @DeleteMapping("/api-keys")
    public ResponseEntity<Void> deleteApiKeys(){
        return null;
    }

}
