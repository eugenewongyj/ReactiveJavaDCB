package com.example.demo.services;

import com.example.demo.domain.BookInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class BookInfoService {

    public Flux<BookInfo> getBooks() {
        var books = List.of(
                new BookInfo(1, "Book One", "Author One", "001"),
                new BookInfo(2, "Book Two", "Author Two", "002"),
                new BookInfo(3, "Book Three", "Author Three", "003")
        );

        return Flux.fromIterable(books);
    }

    public Mono<BookInfo> getBookById(long bookId) {
        var book = new BookInfo(bookId, "Book One", "Author One", "001");

        return Mono.just(book);
    }
}
