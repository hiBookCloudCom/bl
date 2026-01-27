package com.example.books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BooksDTO {

    private Integer bookId;
    private String bookName;
    private String author;
    private String genre;
    private String googleVolumeId;

}
