package com.example.books;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service

public class GoogleBooksClient {
    private final RestClient rest = RestClient.create("https://www.googleapis.com/books/v1");

    @Value("${google.books.api-key:}")
    private String apiKey;

    public Map searchFirst(String q) {
        return rest.get()
                .uri(uri -> uri.path("/volumes")
                        .queryParam("q", q)
                        .queryParam("maxResults", 1)
                        .queryParam("printType", "books")
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .body(Map.class);
    }


    public Map search(String q, int maxResults, int startIndex) {
        final int max = Math.min(maxResults, 5);
        final int start = Math.max(startIndex, 0);

        return rest.get()
                .uri(uri -> uri.path("/volumes")
                        .queryParam("q", q)
                        .queryParam("printType", "books")
                        .queryParam("startIndex", start)
                        .queryParam("maxResults", max)
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .body(Map.class);
    }
}

