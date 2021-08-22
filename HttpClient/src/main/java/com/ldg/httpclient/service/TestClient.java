package com.ldg.httpclient.service;

import com.ldg.httpclient.domain.JsonBodyHandler;
import com.ldg.httpclient.domain.TestResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Service
public class TestClient {

    private final HttpClient httpClient;
    
    public TestClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

}
