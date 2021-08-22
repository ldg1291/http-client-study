package com.ldg.httpclient.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpResponse;

public class JsonBodyHandler<T> implements HttpResponse.BodyHandler<T> {
    private final ObjectMapper objectMapper;
    private final Class<T> type;

    public static <T> JsonBodyHandler<T> of(final Class<T> type) {
        return jsonBodyHandler(new ObjectMapper(), type);
    }

    public static <T> JsonBodyHandler<T> jsonBodyHandler(final ObjectMapper objectMapper,
                                                         final Class<T> type) {
        return new JsonBodyHandler<>(objectMapper, type);
    }

    private JsonBodyHandler(ObjectMapper objectMapper, Class<T> type) {
        this.objectMapper = objectMapper;
        this.type = type;
    }

    @Override
    public HttpResponse.BodySubscriber<T> apply(final HttpResponse.ResponseInfo responseInfo) {
        return HttpResponse.BodySubscribers.mapping(HttpResponse.BodySubscribers.ofByteArray(),
                byteArray -> readValue(byteArray, this.type));
    }

    private T readValue (byte[] source, Class<T> tClass) {
        try {
            return this.objectMapper.readValue(source, tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
