package com.mielibi.mielibi.service;

import com.mielibi.mielibi.domain.Book;
import com.mielibi.mielibi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findAllBooks() throws IOException {
        return bookRepository.findAll();
    }

    @Override
    public Book findBookById(Long bookId) throws IOException {
        return bookRepository.findById(bookId).orElseThrow(()
                -> new IllegalArgumentException("Book not found by id [" + bookId + "]"));
    }

    @Override
    public List<Book> findBooksByAuthorName(String authorName) throws IOException {
        return bookRepository.findAllByAuthorName(authorName);
    }

    @Override
    public Book createBook(Book book) throws IOException {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) throws IOException {
        Book exBook = findBookById(book.getBookId());
        exBook.setBookName(book.getBookName());
        exBook.setPages(book.getPages());
        exBook.setAuthorName(book.getAuthorName());
        return bookRepository.save(exBook);
    }

    @Override
    public void deleteBook(Long bookId) throws IOException {
        bookRepository.deleteById(bookId);
    }
}
