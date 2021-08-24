package com.ldg.httpclient.service;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class Http2Test {

    @Test
    void testHttp2() {

        var client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8002/test/1"))
                .build();

        var asyncRequests = new CopyOnWriteArrayList<CompletableFuture<Void>>();

        HttpResponse.PushPromiseHandler<byte[]> pph = (initial, pushRequest, acceptor) -> {
            CompletableFuture<Void> cf = acceptor.apply(HttpResponse.BodyHandlers.ofByteArray())
                    .thenAccept(response -> {
                        System.out.println("Got pushed resource: " + response.uri());
                        System.out.println("Body: " + response.body());
                    });
            asyncRequests.add(cf);
        };

        client.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray(), pph)
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
    }
}
