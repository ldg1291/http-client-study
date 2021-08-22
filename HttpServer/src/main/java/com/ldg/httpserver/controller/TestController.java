package com.ldg.httpserver.controller;

import com.ldg.httpserver.domain.TestRequest;
import com.ldg.httpserver.domain.TestResponse;
import com.ldg.httpserver.service.TestService;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("/test")
    public TestResponse handlePost(@RequestBody TestRequest testRequest) {
        return testService.createNewElement(testRequest);
    }

    @GetMapping("/test/{id}")
    public TestResponse handleGet(@PathVariable Long id) {
        return testService.getElement(id);
    }

    @GetMapping("/test/sleep/{id}")
    public TestResponse handleSleep(@PathVariable Long id) {
        return testService.getElementWithSleep(id);
    }
}
