package com.incubator.vrsa.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hello-world")
public class HelloWorldController {

    private final String successMsg;

    public HelloWorldController(String successMsg){
        this.successMsg = successMsg;
    }
    @Operation(summary = "Get injected bean success message")
    @GetMapping("/success")
    public ResponseEntity<String> getSuccessMessage(){
        return ResponseEntity.status(HttpStatus.OK).body(successMsg);
    }

    @Operation(summary = "Send a message to be displayed")
    @PostMapping("/message")
    public ResponseEntity<String> sendMessage(@RequestBody String message){
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
