package org.arsh.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TempController {

    @GetMapping("/temp")
    public List<String> temp() {
        List<String> fruits = List.of("apple", "banana", "cherry");
        return fruits;
    }
}
