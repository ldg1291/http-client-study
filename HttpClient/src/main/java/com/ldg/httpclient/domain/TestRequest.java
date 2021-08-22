package com.ldg.httpclient.domain;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class TestRequest {

    private List<String> testStrings;
    private Integer testInteger;

    public TestRequest(List<String> testStrings, Integer testInteger) {
        this.testInteger = testInteger;
        this.testStrings = testStrings;
    }
}
