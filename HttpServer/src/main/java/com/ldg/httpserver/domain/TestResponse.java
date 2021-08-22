package com.ldg.httpserver.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TestResponse {

    private List<String> testStrings;
    private Integer testInteger;
    private Long id;

    public TestResponse(List<String> testStrings, Integer testInteger, Long id) {
        this.testInteger = testInteger;
        this.testStrings = testStrings;
        this.id = id;
    }
}
