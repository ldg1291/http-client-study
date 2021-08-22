package com.ldg.httpclient.service;

import com.ldg.httpclient.domain.WebSocketListener;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.TimeUnit;

public class WebSocketTest {

    WebSocketListener listener = new WebSocketListener();

    @Test
    void testWebsocket() throws InterruptedException {
        var client = HttpClient.newHttpClient();

        WebSocket webSocket = client.newWebSocketBuilder()
                .buildAsync(URI.create("ws://127.0.0.1:8002/websocket"), listener.getListener()).join();
        webSocket.sendText("hello from the client", true);

        TimeUnit.SECONDS.sleep(5);
        webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "ok");
    }
}
