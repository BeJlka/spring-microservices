package com.bejlka.foodservice.camunda;

import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class TestRest {

    private final RuntimeService runtimeService;

    @GetMapping("/testcamunda/{text}")
    public void send(@PathVariable("text") String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        System.out.println("Rest API: " + message);
        runtimeService.startProcessInstanceByKey("testTask", map);
    }
}
