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
   
   public abstract int editAuthorById(List<String> colNames, List<Object> colValues, Object pkValue, String pkColName) throws ClassNotFoundException, SQLException;
   
   public abstract List<Author> getAuthorById(String tableName, String pkColName, Object pkValue) throws ClassNotFoundException, SQLException;
}
