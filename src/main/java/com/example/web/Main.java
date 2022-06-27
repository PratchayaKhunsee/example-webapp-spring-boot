package com.example.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @PostMapping(value = "/push", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public Map<String, Object> push(@RequestBody Map<String, Object> payload) {
        var x = new HashMap<String, Object>();
        String msg = String.valueOf(payload.get("msg"));
        x.put("success", (msg != null && msg != "" && RedisConnection.push("list", msg) > 0l));
        return x;
    }

    @GetMapping("/list")
    public List<String> list() {
        return RedisConnection.list("list");
    }
}
