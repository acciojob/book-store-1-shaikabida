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
        book.setId(id++);
        bookList.add(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    // getBookById()
    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id){
        Book book=null;
        for(int i=0;i<bookList.size();i++){
            if(bookList.get(i).getId()==id){
                book=bookList.get(i);
                break;
            }
        }
        if(book==null) {
            return new ResponseEntity<>(book, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(book, HttpStatus.FOUND);
        }
    }

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    // deleteBookById()
    @DeleteMapping ("/delete-book-by-id/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable int id){
        boolean check=false;
        for(int i=0;i<bookList.size();i++){
            if(bookList.get(i).getId()==id){
                bookList.remove(i);
                check =true;
                break;
            }
        }
        if(check==true) {
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Doesn't exist", HttpStatus.OK);
        }
    }

    // get request /get-all-books
    // getAllBooks()
    @GetMapping ("/get-all-books")
    public ResponseEntity<List<Book>> getAllBooks(){

        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    // delete request /delete-all-books
    // deleteAllBooks()
    @DeleteMapping ("/delete-all-books")
    public ResponseEntity<String> deleteAllBooks(){
        bookList=new ArrayList<>();
        id=1;
        return new ResponseEntity<>("Deleted all books", HttpStatus.OK);
    }

    // get request /get-books-by-author
    // pass author name as request param
    // getBooksByAuthor()
    @GetMapping ("/get-books-by-author")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("author")String author){
        List<Book> authorBooks=new ArrayList<>();
        for(int i=0;i<bookList.size();i++){
            if(bookList.get(i).getAuthor().equals(author)){
                authorBooks.add(bookList.get(i));
            }
        }
        return new ResponseEntity<>(authorBooks, HttpStatus.OK);
    }


    // get request /get-books-by-genre
    // pass genre name as request param
    // getBooksByGenre()
    @GetMapping ("/get-books-by-genre")
    public ResponseEntity<List<Book>> getBooksByGenre(@RequestParam("genre")String genre){
        List<Book> genreBooks=new ArrayList<>();
        for(int i=0;i<bookList.size();i++){
            if(bookList.get(i).getGenre().equals(genre)){
                genreBooks.add(bookList.get(i));
            }
        }
        return new ResponseEntity<>(genreBooks, HttpStatus.OK);
    }
}
