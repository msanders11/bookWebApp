package edu.wctc.distjava.bookwebapp.model;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author Mike
 */
public class MockAuthorDao implements IAuthorDao {

    public MockAuthorDao() {
    }

    @Override
    public final List<Author> getListOfAuthors() throws SQLException, ClassNotFoundException {

        List<Author> list = Arrays.asList(
                new Author(1, "John Doe", new Date()),
                new Author(2, "Bob Smith", new Date())
        );

        return list;
    }

    @Override
    public final int DeleteAuthor(String tableName, int primaryKey) {
        return 0;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        IAuthorDao dao = new MockAuthorDao();

//        AuthorDao dao = new AuthorDao(
//                "com.mysql.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/book",
//                "root",
//                "admin",
//                new MySqlDataAccess("com.mysql.jdbc.Driver",
//                        "jdbc:mysql://localhost:3306/book",
//                        "root",
//                        "admin")
//        );
        List<Author> list = dao.getListOfAuthors();

        for (Author a : list) {
            System.out.println(a.getAuthorId() + ", "
                    + a.getAuthorName() + ", " + a.getDateAdded() + "\n");
        }
    }

}
