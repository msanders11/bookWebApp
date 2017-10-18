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

    public final List<Author> findAuthorById(String id) throws ClassNotFoundException, SQLException {
        return authorDao.getAuthorById(AUTHOR_TABLE, AUTHOR_PK, id);
    }

    public final List<Author> getAuthorList() throws SQLException, ClassNotFoundException {
        return authorDao.getListOfAuthors();

    }

    public final int editAuthorById(List<String> colNames, List<Object> colValues, Object pkValue) throws ClassNotFoundException, SQLException {
        if (colNames == null || colValues == null || pkValue == null) {
            throw new IllegalArgumentException();
        }
        return authorDao.editAuthorById(colNames, colValues, pkValue, AUTHOR_PK);
    }

    public final int addAuthor(List<String> colName, List<Object> colValues) throws ClassNotFoundException, SQLException {
        if (colName == null || colValues == null) {
            throw new IllegalArgumentException("Please provide values");
        }
        return authorDao.addAuthor(colName, colValues);
    }

    public final int removeAuthorById(String id) throws ClassNotFoundException, SQLException, NumberFormatException {
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
        
        System.out.println(authorService.findAuthorById("30"));

//        authorService.editAuthorById(
//                Arrays.asList("author_name", "date_added"),
//                Arrays.asList("Mike Sanders", "2010-03-10"),
//                30);
//        
        //Test for Delet Author by id
//        int recsDeleted = authorService.removeAuthorById("2");

        //Test retrieve all records
//        List<Author> list = authorService.getAuthorList();
//
//        for (Author a : list) {
//            System.out.println(a.getAuthorId() + ", "
//                    + a.getAuthorName() + ", " + a.getDateAdded() + "\n");
//        }
    }
}
