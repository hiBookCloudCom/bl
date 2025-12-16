package com.example.cloudcomp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service

public class GoogleBooksClient {
    private final RestClient rest = RestClient.create("https://www.googleapis.com/books/v1");

    public Map searchFirst(String q) {
        return rest.get()
                .uri(uri -> uri.path("/volumes")
                        .queryParam("q", q)
                        .queryParam("maxResults", 1)
                        .queryParam("printType", "books")
                        .build())
                .retrieve()
                .body(Map.class);
    }
}

