package edu.wctc.distjava.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Mike
 */
public interface IAuthorDao {

    public abstract List<Author> getListOfAuthors() throws SQLException, ClassNotFoundException;

   public abstract int removeAuthorById(Integer id) throws ClassNotFoundException, SQLException;
    
    
}
