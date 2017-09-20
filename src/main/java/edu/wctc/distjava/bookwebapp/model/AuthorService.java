package edu.wctc.distjava.bookwebapp.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Mike
 */
public class AuthorService {
    
    public List<Author> getAuthorList(){
        //List<Author> authors = new ArrayList<>();
        return Arrays.asList(
                new Author(1, "Mark Twain", new Date()),
                new Author(2, "Stephen King", new Date()),
                new Author(3, "George Orwell", new Date())
        );
        
    }
    
}
