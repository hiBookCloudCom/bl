package com.example.books;

import java.util.List;

public interface BooksService {

    BooksDTO insertBook(BooksDTO booksDTO);
    BooksDTO updateBook(BooksDTO booksDTO);
    void deleteBook(Integer bookId);

    BooksDTO getBook(Integer bookId);

    List<BooksDTO> getAllBooks();

    List<BooksDTO> getBooksByAuthor(String author);
    List<BooksDTO> getBooksByGenre(String genre);
    List<BooksDTO> searchByBookNameSubstring(String q);

    BooksDTO addBookFromGoogle(String q);


}
