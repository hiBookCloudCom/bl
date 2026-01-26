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
            @RequestParam(name = "userId") Integer userId,
            @RequestParam(name = "author",required = false) String author,
            @RequestParam(name = "genre",required = false) String genre,
            @RequestParam(name = "bookName",required = false) String bookName,
            @RequestParam(name = "rating", required = false) Integer rating,
            @RequestParam(required = false, name = "q") String query
    ) {
        if (author != null && !author.isBlank()) {
            return ResponseEntity.ok(booksService.getBooksByAuthor(author, userId));
        }
        if (genre != null && !genre.isBlank()) {
            return ResponseEntity.ok(booksService.getBooksByGenre(genre, userId));
        }
        if (bookName != null && !bookName.isBlank()) {
            return ResponseEntity.ok(booksService.getBooksByBookName(bookName, userId));
        }
        if (rating != null) {
            return ResponseEntity.ok(booksService.getBooksByRating(rating, userId));
        }
        if (query != null && !query.isBlank()) {
            return ResponseEntity.ok(booksService.searchByBookNameSubstring(query));
        }
        return ResponseEntity.ok(booksService.getAllBooks(userId));
    }


    @PostMapping("/addFromGoogle")
    public BooksDTO addFromGoogle(@RequestParam(name = "userId") Integer userId,
                                  @RequestParam(name = "q") String q,
                                  @RequestParam(name = "status", required = false) String status,
                                  @RequestParam(name = "rating", required = false) Integer rating) {
        return booksService.addBookFromGoogle(userId, q, status, rating);
    }

}
