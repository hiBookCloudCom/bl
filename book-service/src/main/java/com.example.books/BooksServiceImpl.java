package com.example.books;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private final BooksDAO booksDAO;
    private final BooksMapper mapper;
    private final GoogleBooksClient googleBooksClient;


    @Override
    public BooksDTO insertBook(BooksDTO booksDTO) {
        try {
            Books book = Books.builder()
                    .bookName(booksDTO.getBookName())
                    .author(booksDTO.getAuthor())
                    .genre(booksDTO.getGenre())
                    .googleVolumeId(booksDTO.getGoogleVolumeId())
                    .build();

            return mapper.toDto(booksDAO.save(book));

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error inserting book: {}", booksDTO, e);
            throw new RuntimeException("Failed to insert book", e);
        }
    }


    @Override
    public BooksDTO updateBook(BooksDTO booksDTO) {
        try {
            if (booksDTO.getBookId() == null) {
                throw new RuntimeException("bookId is required");}

            Books updated = booksDAO.findById(booksDTO.getBookId())
                    .map(b -> {

                        b.setBookName(booksDTO.getBookName());
                        b.setAuthor(booksDTO.getAuthor());
                        b.setGenre(booksDTO.getGenre());
                        b.setGoogleVolumeId(booksDTO.getGoogleVolumeId());
                        return booksDAO.save(b);
                    })
                    .orElseThrow(() -> new RuntimeException("Book not found: " + booksDTO.getBookId()));

            return mapper.toDto(updated);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error updating book: {}", booksDTO, e);
            throw new RuntimeException("Failed to update book", e);
        }
    }

    @Override
    public void deleteBook(Integer bookId) {
        try {
            if (bookId == null) {
                throw new RuntimeException("bookId is required");
            }
            if (!booksDAO.existsById(bookId)) {
                throw new RuntimeException("Book not found: " + bookId);
            }
            booksDAO.deleteById(bookId);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error deleting bookId: {}", bookId, e);
            throw new RuntimeException("Failed to delete book", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BooksDTO getBook(Integer bookId) {
        if (bookId == null) {
            throw new RuntimeException("bookId is required");
        }

        return booksDAO.findById(bookId)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Book not found: " + bookId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BooksDTO> getAllBooks() {
        return booksDAO.findAll().stream().map(mapper::toDto).toList();
    }
    @Override
    @Transactional(readOnly = true)
    public List<BooksDTO> getBooksByAuthor(String author) {
        return booksDAO.findAllByAuthor(author).stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BooksDTO> getBooksByGenre(String genre) {
        return booksDAO.findAllByGenre(genre).stream().map(mapper::toDto).toList();
    }



    @Override
    @Transactional(readOnly = true)
    public List<BooksDTO> searchByBookNameSubstring(String q) {
        return booksDAO.searchSubs(q).stream().map(mapper::toDto).toList();
    }

    @Override
    public BooksDTO addBookFromGoogle(String q) {
        if (q == null || q.isBlank()) throw new RuntimeException("q is required");

        Map resp = googleBooksClient.searchFirst(q);

        List<Map> items = (List<Map>) resp.get("items");
        if (items == null || items.isEmpty()) {
            throw new RuntimeException("No results for query: " + q);
        }

        Map item = items.get(0);
        String volumeId = (String) item.get("id");

        if (volumeId != null && booksDAO.existsByGoogleVolumeId(volumeId)) {
            throw new RuntimeException("This book is already added.");
        }

        Map vi = (Map) item.get("volumeInfo");
        String title = vi == null ? null : (String) vi.get("title");
        List<String> authors = vi == null ? null : (List<String>) vi.get("authors");
        List<String> categories = vi == null ? null : (List<String>) vi.get("categories");

        Books book = Books.builder()
                .bookName(title)
                .author(authors == null ? null : String.join(", ", authors))
                .genre(categories == null || categories.isEmpty() ? null : categories.get(0))
                .googleVolumeId(volumeId)
                .build();

        return mapper.toDto(booksDAO.save(book));
    }

}
