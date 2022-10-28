package com.driver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {

    private List<Book> bookList;
    private  int id;
    private static int idGenerator=1;

    public List<Book> getBookList() {

        return bookList;
    }

    public void setBookList(List<Book> bookList) {

        this.bookList = bookList;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 1;
    }

    // post request /create-book
    // pass book as request body
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        // Your code goes here.
        book.setId(idGenerator++);
        bookList.add(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    // getBookById()
    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id")Integer id){
        Book book;
        for(int i=0;i<bookList.size();i++){
            if(bookList.get(i).getId()==id){
                return new ResponseEntity<>(bookList.get(i),HttpStatus.FOUND);
            }
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    // deleteBookById()
    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id")Integer id){
        for(int i=0;i<bookList.size();i++){
            if(bookList.get(i).getId()==id){
                bookList.remove(bookList.get(i));
                break;
            }
        }
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }

    // get request /get-all-books
    // getAllBooks()
    @GetMapping("/get-all-books")
    public ResponseEntity<List<Book>> getAllBooks(){
        return new ResponseEntity<>(bookList,HttpStatus.OK);
    }

    // delete request /delete-all-books
    // deleteAllBooks()
    @DeleteMapping("/delete-all-books")
    public ResponseEntity<String> deleteAllBooks(){
        // Your code goes here.
        bookList.clear();
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }

    // get request /get-books-by-author
    // pass author name as request param
    // getBooksByAuthor()
    @GetMapping("/get-books-by-author")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("author")String author){
        List<Book> authorList=new ArrayList<>();
        for(int i=0;i<bookList.size();i++){
            Book b=bookList.get(i);
            String currAuthor=b.getAuthor();
            if(currAuthor.equals(author)){
                authorList.add(b);
            }
        }
        return new ResponseEntity<>(authorList,HttpStatus.OK);
    }

    // get request /get-books-by-genre
    // pass genre name as request param
    // getBooksByGenre()
    @GetMapping("/get-books-by-genre")
    public ResponseEntity<List<Book>> getBooksByGenre(@RequestParam("genre")String genre){
        List<Book> genreList=new ArrayList<>();
        for(int i=0;i<bookList.size();i++){
            Book b=bookList.get(i);
            String currGenre=b.getGenre();
            if(currGenre.equals(genre)){
                genreList.add(b);
            }
        }
        return new ResponseEntity<>(genreList,HttpStatus.OK);
    }
}
