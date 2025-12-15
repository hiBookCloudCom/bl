package com.example.cloudcomp;

import java.util.List;

public interface BooksService {

    BooksDTO insertBook(BooksDTO booksDTO);
    BooksDTO updateBook(BooksDTO booksDTO);
    void deleteBook(Integer bookId);

    BooksDTO getBook(Integer bookId);

    List<BooksDTO> getAllBooks();

    List<BooksDTO> getBooksByAuthor(String author);
    List<BooksDTO> getBooksByGenre(String genre);
    List<BooksDTO> getBooksByBookName(String bookName);
    List<BooksDTO> searchByBookNameSubstring(String q);

    // dodaj uvoz


}
