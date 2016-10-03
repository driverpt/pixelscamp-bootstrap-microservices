package net.luisduarte.pixelscamp.rest.controllers;

import net.luisduarte.pixelscamp.rest.model.Book;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookController {
    private static List<Book> bookList = new LinkedList<>();

    static {
        bookList.add(Book.of("12345", "Foo"));
        bookList.add(Book.of("67890", "Bar"));
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Book> books() {
        return bookList;
    }

}
