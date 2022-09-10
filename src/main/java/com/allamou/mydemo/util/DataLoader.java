package com.allamou.mydemo.util;

import com.allamou.mydemo.dao.AuthorRepository;
import com.allamou.mydemo.dao.BookRepository;
import com.allamou.mydemo.entities.Author;
import com.allamou.mydemo.entities.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class DataLoader implements CommandLineRunner {
    private static Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

    private AuthorRepository authorRepository;

    private BookRepository bookRepository;

    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void fetchAuthorsBooksByPriceJoinFetch(int price) {
        List<Author> authors = authorRepository.fetchAuthorsBooksByPriceJoinFetch(price);
        authors.forEach((e) -> System.out.println("Author name: "
                + e.getName() + ", books: " + e.getBooks()));
    }

    @Transactional(readOnly = true)
    public void fetchAuthorsBooksByPriceInnerJoin(int price) {
        List<Author> authors = authorRepository.fetchAuthorsBooksByPriceInnerJoin(price);
        authors.forEach((e) -> System.out.println("Author name: "
                + e.getName() + ", books: " + e.getBooks()));
    }

    @Override
    public void run(String... args) {
        Author author1 = null, author2 = null, author3 = null;
        Book book1, book2, book3;

        if(authorRepository.findAll().isEmpty()) {
            author1 = new Author();
            author1.setAge(25);
            author1.setName("Cecil Karol");
            author1.setGenre("Novel");

            author2 = new Author();
            author2.setAge(58);
            author2.setName("Poyraz Yoann");
            author2.setGenre("Sci Fi");

            author3 = new Author();
            author3.setAge(58);
            author3.setName("Hankin Ambrosios");
            author3.setGenre("Crime");

            authorRepository.save(author1);
            authorRepository.save(author2);
            authorRepository.save(author3);

        }

        if(bookRepository.findAll().isEmpty()) {

            book1 = new Book();
            book1.setAuthor(author1);
            book1.setTitle("Title book 1");
            book1.setIsbn("Isbn Book 1");
            book1.setPrice(200);

            book2 = new Book();
            book2.setAuthor(author2);
            book2.setTitle("Title book 2");
            book2.setIsbn("Isbn Book 2");
            book2.setPrice(400);

            book3 = new Book();
            book3.setAuthor(author3);
            book3.setTitle("Title book 3");
            book3.setIsbn("Isbn Book 3");
            book3.setPrice(500);

            bookRepository.save(book1);
            bookRepository.save(book2);
            bookRepository.save(book3);

        }

        fetchAuthorsBooksByPriceInnerJoin(300);

    }
}
