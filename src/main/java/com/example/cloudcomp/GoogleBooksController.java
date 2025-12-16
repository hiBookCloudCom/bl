package com.example.cloudcomp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.*;

@RestController
@RequestMapping("/api/google-books")
@RequiredArgsConstructor
public class GoogleBooksController {

    private final RestClient rest = RestClient.create("https://www.googleapis.com/books/v1");

    @GetMapping("/search")
    public List<BooksDTO> search(
            @RequestParam String q,
            @RequestParam(defaultValue = "10") int maxResults,
            @RequestParam(defaultValue = "0") int startIndex
    ) {
        final int max = Math.min(maxResults, 5);
        final int start = Math.max(startIndex, 0);

        Map resp = rest.get()
                .uri(uri -> uri.path("/volumes")
                        .queryParam("q", q)
                        .queryParam("printType", "books")
                        .queryParam("startIndex", start)
                        .queryParam("maxResults", max)
                        .build())
                .retrieve()
                .body(Map.class);

        List<Map> items = (List<Map>) resp.get("items");
        if (items == null) return List.of();

        List<BooksDTO> out = new ArrayList<>();
        for (Map item : items) {
            Map vi = (Map) item.get("volumeInfo");
            if (vi == null) continue;

            String title = (String) vi.get("title");
            List<String> authors = (List<String>) vi.get("authors");
            List<String> categories = (List<String>) vi.get("categories");

            out.add(BooksDTO.builder()
                    .bookId(null) // nema baze
                    .bookName(title)
                    .author(authors == null ? null : String.join(", ", authors))
                    .genre(categories == null || categories.isEmpty() ? null : categories.get(0))
                    .build());
        }
        return out;
    }
}
