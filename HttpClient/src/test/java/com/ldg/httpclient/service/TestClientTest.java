package com.ldg.httpclient.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldg.httpclient.domain.JsonBodyHandler;
import com.ldg.httpclient.domain.TestRequest;
import com.ldg.httpclient.domain.TestResponse;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TestClientTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    private static final String BASE_URL = "http://127.0.0.1:8002/test";

    @Test
    void testPostMethod() throws IOException, InterruptedException {

        TestRequest testRequest = TestRequest.builder()
                .testInteger(100)
                .testStrings(Lists.newArrayList("test1", "test2"))
                .build();

        var hRequest = HttpRequest.newBuilder()
        /**  여기를 채워넣어주세요 TestController 참고
                .POST()
         **/
                .uri(URI.create(BASE_URL))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .build();

        var syncResponse =
                httpClient.send(hRequest, new JsonBodyHandler<>(TestResponse.class));

        var asyncResponse =
                httpClient.sendAsync(hRequest, new JsonBodyHandler<>(TestResponse.class));

        var asyncResponse2 =
                httpClient.sendAsync(hRequest, HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body)
                        .thenApply(body -> readValue(body, TestResponse.class));

    }

    @Test
    void testGetMethod() throws IOException, InterruptedException {

        var id = 1L;

        var hRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL+id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .build();

        var syncResponse =
                httpClient.send(hRequest, new JsonBodyHandler<>(TestResponse.class));

        System.out.println(syncResponse.body().get().toString());
    }

    @Test
    void testSleep() throws IOException, InterruptedException {

        var id = 1L;

        var hRequest1 = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL+id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .build();

        var syncResponse =
                httpClient.send(hRequest1, new JsonBodyHandler<>(TestResponse.class));

        System.out.println(syncResponse.body().get().toString());

        var hRequest2 = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://127.0.0.1:8002/test/sleep/"+id))
                .timeout(Duration.ofSeconds(5))
                .header("Content-Type", "application/json")
                .build();

        assertThrows(HttpTimeoutException.class, () ->
                httpClient.send(hRequest2, new JsonBodyHandler<>(TestResponse.class)));

    }

    private <T> T readValue (String source, Class<T> tClass) {
        try {
            return objectMapper.readValue(source, tClass);
        } catch (IOException e) {
            throw new CompletionException(e);
        }
    }
}