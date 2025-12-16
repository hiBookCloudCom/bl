package com.example.cloudcomp;

import java.util.List;

public interface BooksService {

    BooksDTO insertBook(BooksDTO booksDTO);
    BooksDTO updateBook(BooksDTO booksDTO);
    void deleteBook(Integer bookId);

    BooksDTO getBook(Integer bookId);

    List<BooksDTO> getAllBooks(Integer userId);

    List<BooksDTO> getBooksByAuthor(String author, Integer userID);
    List<BooksDTO> getBooksByGenre(String genre, Integer userID);
    List<BooksDTO> getBooksByBookName(String bookName, Integer userID);

    List<BooksDTO> getBooksByRating(Integer rating, Integer userId);
    List<BooksDTO> searchByBookNameSubstring(String q);

    BooksDTO addBookFromGoogle(Integer userId, String q, String status, Integer rating);


}
