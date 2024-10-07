package com.learning.security_spring.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {

    @GetMapping("/public")
    public ResponseEntity<String> publicRoute(){
        return ResponseEntity.ok("Aqui estamos!");
    }

    @GetMapping("/private")
    public ResponseEntity<String> privateRoute(){
        return ResponseEntity.ok("Aqui estamos no privado!");
    }
}
