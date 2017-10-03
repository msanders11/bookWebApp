package edu.wctc.distjava.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Mike
 */
public interface IAuthorDao {

    List<Author> getListOfAuthors() throws SQLException, ClassNotFoundException;

    int DeleteAuthor(String tableName, int primaryKey);
    
    
    
}
