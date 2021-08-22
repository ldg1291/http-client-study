package com.ldg.httpserver.service;

import com.ldg.httpserver.domain.TestRequest;
import com.ldg.httpserver.domain.TestResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TestService {

    private Long id = 0L;

    private Map<Long, TestResponse> map = new HashMap<>();

    public TestResponse createNewElement(final TestRequest req) {

        var newElement = new TestResponse(req.getTestStrings(), req.getTestInteger(), id);

        map.put(id, newElement);

        id++;

        return newElement;
    }

    public TestResponse getElement(Long id) {
        return map.get(id);
    }

    public TestResponse getElementWithSleep(Long id) {

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Oops.. Some Problem happened.");
        }
        return map.get(id);
    }
}
