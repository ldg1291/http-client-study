package com.ldg.httpclient.domain;

import lombok.Getter;

import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

@Getter
public class WebSocketListener {

    private WebSocket.Listener listener;

    public WebSocketListener() {
        this.listener = new WebSocket.Listener() {
            @Override
            public CompletionStage<?> onText(WebSocket webSocket,
                                             CharSequence data, boolean last) {

                System.out.println("onText: " + data);

                return WebSocket.Listener.super.onText(webSocket, data, last);
            }

            @Override
            public void onOpen(WebSocket webSocket) {
                System.out.println("onOpen");
                WebSocket.Listener.super.onOpen(webSocket);
            }

            @Override
            public CompletionStage<?> onClose(WebSocket webSocket, int statusCode,
                                              String reason) {
                System.out.println("onClose: " + statusCode + " " + reason);
                return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
            }
        };
    }
}
