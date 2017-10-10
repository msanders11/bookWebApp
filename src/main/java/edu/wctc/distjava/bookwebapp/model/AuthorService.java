package edu.wctc.distjava.bookwebapp.model;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Mike
 */
public class AuthorService {

    private IAuthorDao authorDao;
    private final String AUTHOR_TABLE = "author";
    private final String AUTHOR_PK = "author_id";

    public AuthorService(IAuthorDao authorDao) {
        setAuthorDao(authorDao);
    }

    public List<Author> getAuthorList() throws SQLException, ClassNotFoundException {
        return authorDao.getListOfAuthors();

    }
    
    public final int editAuthor(List<String> colNames, List<Objects> colValues, Object pkValue, String pkColName) throws ClassNotFoundException, SQLException{
        if(colNames == null || colValues == null || pkValue == null || pkColName == null){
            throw new IllegalArgumentException();      
            }
        return authorDao.editAuthor(colNames, colValues, pkValue, pkColName);
    }
    
    public final int addAuthor(List<String> colName, List<Object> colValues) throws ClassNotFoundException, SQLException{
        if(colName == null || colValues == null){
            throw new IllegalArgumentException("Please provide values");
        }
        return authorDao.addAuthor(colName, colValues);
    }

    public int removeAuthorById(String id) throws ClassNotFoundException, SQLException, NumberFormatException{
        if (id == null) {
            throw new IllegalArgumentException("id must be integer greater than zero");
        }
        
        Integer value = Integer.parseInt(id);
        
        return authorDao.removeAuthorById(value);
    }

    public final IAuthorDao getAuthorDao() {
        return authorDao;
    }

    public final void setAuthorDao(IAuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        IAuthorDao dao = new AuthorDao(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book",
                "root",
                "admin",
                new MySqlDataAccess()
        );

        AuthorService authorService = new AuthorService(dao);
        
        //Test for Delet Author by id
        int recsDeleted = authorService.removeAuthorById("2");

        //Test retrieve all records
        List<Author> list = authorService.getAuthorList();

        for (Author a : list) {
            System.out.println(a.getAuthorId() + ", "
                    + a.getAuthorName() + ", " + a.getDateAdded() + "\n");
        }
    }
}
