package edu.wctc.distjava.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Mike
 */
public interface IAuthorDao {

    public abstract List<Author> getListOfAuthors() throws SQLException, ClassNotFoundException;

   public abstract int removeAuthorById(Integer id) throws ClassNotFoundException, SQLException;
    
   public abstract int addAuthor(List<String> colName, List<Object> colValues)throws ClassNotFoundException, SQLException;
   
   public abstract int editAuthor(List<String> colNames, List<Objects> colValues, Object pkValue, String pkColName) throws ClassNotFoundException, SQLException;
}
