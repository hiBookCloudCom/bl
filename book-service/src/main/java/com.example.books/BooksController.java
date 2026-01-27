package com.example.books;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BooksController {

    private final BooksService booksService;

    @PostMapping
    public ResponseEntity<BooksDTO> createBook(@RequestBody BooksDTO dto) {
        BooksDTO created = booksService.insertBook(dto);
        return ResponseEntity
                .created(URI.create("/api/books/" + created.getBookId()))
                .body(created);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BooksDTO> updateBook(@PathVariable("bookId") Integer bookId, @RequestBody BooksDTO dto) {
        dto.setBookId(bookId);
        return ResponseEntity.ok(booksService.updateBook(dto));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable("bookId") Integer bookId) {
        booksService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BooksDTO> getBook(@PathVariable("bookId") Integer bookId) {
        return ResponseEntity.ok(booksService.getBook(bookId));
    }


    @GetMapping
    public ResponseEntity<List<BooksDTO>> getBooks(
            @RequestParam(name = "author",required = false) String author,
            @RequestParam(name = "genre",required = false) String genre,
            @RequestParam(required = false, name = "q") String query
    ) {
        if (author != null && !author.isBlank()) {
            return ResponseEntity.ok(booksService.getBooksByAuthor(author));
        }
        if (genre != null && !genre.isBlank()) {
            return ResponseEntity.ok(booksService.getBooksByGenre(genre));
        }

        if (query != null && !query.isBlank()) {
            return ResponseEntity.ok(booksService.searchByBookNameSubstring(query));
        }
        return ResponseEntity.ok(booksService.getAllBooks());
    }


    @PostMapping("/addFromGoogle")
    public BooksDTO addFromGoogle(@RequestParam(name = "q") String q) {
        return booksService.addBookFromGoogle(q);
    }

}
