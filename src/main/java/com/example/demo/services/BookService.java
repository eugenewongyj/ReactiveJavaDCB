package com.example.demo.services;

import com.example.demo.domain.Book;
import com.example.demo.domain.Review;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class BookService {

    private BookInfoService bookInfoService;
    private ReviewService reviewService;

    public BookService(BookInfoService bookInfoService, ReviewService reviewService) {
        this.bookInfoService = bookInfoService;
        this.reviewService = reviewService;
    }

    // eugene: not sure how to use zipWith
    // eugene: also not sure how to get bookId to input into reviewService.getReviews
    //
    public Flux<Book> getBooks() {
        var allBooks = bookInfoService.getBooks();

        return allBooks
                .flatMap(bookInfo -> {
                    Mono<List<Review>> reviews =
                           reviewService.getReviews(bookInfo.getBookId()).collectList();
                    return reviews
                            .map(review -> new Book(bookInfo, review));
                })
                .log();
    }

    public Mono<Book> getBook(long bookId) {
        /*
        var book = bookInfoService.getBookById(bookId);

        return book
                .flatMap(bookInfo -> {
                    Mono<List<Review>> reviews =
                            reviewService.getReviews(bookInfo.getBookId()).collectList();
                    return reviews.map(review -> new Book(bookInfo, review));
                });

         */
        var book = bookInfoService.getBookById(bookId);
        var reviews = reviewService.getReviews(bookId).collectList();

        return book.zipWith(reviews, (b, r) -> new Book(b, r));

    }

}
