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
        /** 알맞게 POST method의 RequestBody를 채워보세요 참고 : TestController
                .POST()
        */
                .uri(URI.create(BASE_URL))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .build();

        var syncResponse =
                httpClient.send(hRequest, JsonBodyHandler.of(TestResponse.class));

        /**
         * syncResponse를 적절하게 출력해보세요
         * */

        var asyncResponse =
                httpClient.sendAsync(hRequest, JsonBodyHandler.of(TestResponse.class));

        /**
         * asyncResponse를 적절하게 출력해보세요
         * */

        var asyncResponse2 =
                httpClient.sendAsync(hRequest, HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body)
                        .thenApply(body -> readValue(body, TestResponse.class));

        /**
         * asyncResponse2를 적절하게 출력해보세요
         * */
    }

    @Test
    void testGetMethod() throws IOException, InterruptedException {

        var id = 1L;

        var hRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL+"/"+id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .build();

        var syncResponse =
                httpClient.send(hRequest, JsonBodyHandler.of(TestResponse.class));

        System.out.println(syncResponse.body().toString());
    }

    @Test
    void testSleep() throws IOException, InterruptedException {

        var id = 1L;

        var hRequest1 = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL+"/sleep/"+id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .build();

        var syncResponse =
                httpClient.send(hRequest1, JsonBodyHandler.of(TestResponse.class));

        System.out.println(syncResponse.body().toString());

        var hRequest2 = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://127.0.0.1:8002/test/sleep/"+id))
                .timeout(Duration.ofSeconds(5))
                .header("Content-Type", "application/json")
                .build();

        assertThrows(HttpTimeoutException.class, () ->
                httpClient.send(hRequest2, JsonBodyHandler.of(TestResponse.class)));

    }

    private <T> T readValue (String source, Class<T> tClass) {
        try {
            return objectMapper.readValue(source, tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}