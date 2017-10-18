package edu.wctc.distjava.bookwebapp.model;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mike
 */
public class AuthorDao implements IAuthorDao {

    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private DataAccess db;

    private final String AUTHOR_TABLE = "author";
    private final String AUTHOR_PK = "author_id";

    public AuthorDao(String driverClass, String url, String userName, String password, DataAccess db) {
        setDriverClass(driverClass);
        setUrl(url);
        setUserName(userName);
        setPassword(password);
        setDb(db);
    }

    public final int editAuthorById(List<String> colNames, List<Object> colValues, Object pkValue, String pkColName) throws ClassNotFoundException, SQLException {
        if (colNames == null || colValues == null || pkValue == null || pkColName == null) {
            throw new IllegalArgumentException();
        }
        db.openConnection(driverClass, url, userName, password);

        int recsEdited = db.updateRecordById(AUTHOR_TABLE, colNames, colValues, pkValue, AUTHOR_PK);

        db.closeConnection();

        return recsEdited;
    }

//    public final int addAuthor(Author author) throws ClassNotFoundException, SQLException {
//        if (author == null) {
//            throw new IllegalArgumentException("invalid author");
//        }
//
//        return 0;
//    }
    @Override
    public final int addAuthor(List<String> colNames, List<Object> colValues) throws ClassNotFoundException, SQLException {
        if (colNames == null && colValues == null) {
            throw new IllegalArgumentException("invalid author");
        }

        db.openConnection(driverClass, url, userName, password);

        int recsAdded = db.createRecord(AUTHOR_TABLE, colNames, colValues);

        db.closeConnection();

        return recsAdded;
    }

    //Delete Author by Primary Key id
    @Override
    public final int removeAuthorById(Integer id) throws ClassNotFoundException, SQLException {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("id must be integer greater than zero");
        }

        db.openConnection(driverClass, url, userName, password);

        int recsDeleted = db.deleteRecordById(AUTHOR_TABLE, AUTHOR_PK, id);

        db.closeConnection();

        return recsDeleted;
    }

    public final List<Author> getAuthorById(String tableName, String pkColName, Object pkValue) throws ClassNotFoundException, SQLException {

        db.openConnection(driverClass, url, userName, password);

        List<Author> list = new Vector();
        List<Map<String, Object>> rawData = db.getRecordById(tableName, pkColName, pkValue);

        Author author = null;

        for (Map<String, Object> rec : rawData) {
            author = new Author();

            Object objRecId = rec.get("author_id");
            Integer recId = objRecId == null ? 0 : Integer.parseInt(objRecId.toString());
            author.setAuthorId(recId);

            Object objName = rec.get("author_name");
            String authorName = objName == null ? "" : objName.toString();
            author.setAuthorName(authorName);

            Object objRecAdded = rec.get("date_added");
            Date recAdded = objRecAdded == null ? null : (Date) objRecAdded;
            author.setDateAdded(recAdded);

            list.add(author);
        }
        db.closeConnection();

        return list;
    }

    @Override
    public final List<Author> getListOfAuthors() throws SQLException, ClassNotFoundException {

        db.openConnection(driverClass, url, userName, password);

        List<Author> list = new Vector();
        List<Map<String, Object>> rawData = db.getAllRecords("author", 0);

        Author author = null;

        for (Map<String, Object> rec : rawData) {
            author = new Author();

            Object objRecId = rec.get("author_id");
            Integer recId = objRecId == null ? 0 : Integer.parseInt(objRecId.toString());
            author.setAuthorId(recId);

            Object objName = rec.get("author_name");
            String authorName = objName == null ? "" : objName.toString();
            author.setAuthorName(authorName);

            Object objRecAdded = rec.get("date_added");
            Date recAdded = objRecAdded == null ? null : (Date) objRecAdded;
            author.setDateAdded(recAdded);

            list.add(author);
        }

        db.closeConnection();

        return list;
    }

    public final DataAccess getDb() {
        return db;
    }

    public final void setDb(DataAccess db) {
        this.db = db;
    }

    public final String getDriverClass() {
        return driverClass;
    }

    public final void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public final String getUrl() {
        return url;
    }

    public final void setUrl(String url) {
        this.url = url;
    }

    public final String getUserName() {
        return userName;
    }

    public final void setUserName(String userName) {
        this.userName = userName;
    }

    public final String getPassword() {
        return password;
    }

    public final void setPassword(String password) {
        this.password = password;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        IAuthorDao dao = new AuthorDao(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book",
                "root",
                "admin",
                new MySqlDataAccess()
        );
        
//        int recsUpdated = dao.editAuthorById(
//                Arrays.asList("author_name", "date_added"),
//                Arrays.asList("Michael Jordan", "05-06-07"),
//                6,
//                "author_id");

        List<Author> list = dao.getAuthorById("author", "author_id", 29);

        
        //Testing Delete record by PK
//        int recsDeleted = dao.removeAuthorById(1);

        //Testing Retrieve all records from database
//        List<Author> list = dao.getListOfAuthors();

        for (Author a : list) {
            System.out.println(a.getAuthorId() + ", " + a.getAuthorName() +
                    "date added : " + a.getDateAdded());
        }

    }

}
