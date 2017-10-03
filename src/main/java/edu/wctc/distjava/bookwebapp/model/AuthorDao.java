package edu.wctc.distjava.bookwebapp.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    public AuthorDao(String driverClass, String url, String userName, String password, DataAccess db) {
        setDriverClass(driverClass);
        setUrl(url);
        setUserName(userName);
        setPassword(password);
        setDb(db);
    }

    @Override
    public final List<Author> getListOfAuthors() throws SQLException, ClassNotFoundException {
        List<Author> list = new Vector();
        List<Map<String, Object>> rawData = db.getAllRecords("author", 0);

        Author author = null;

        for (Map<String, Object> rec : rawData) {
            author = new Author();

            Object objRecId = rec.get("author_id");
            Integer recId = objRecId == null ? 0 : Integer.parseInt(objRecId.toString());
            author.setAuthorId(recId);
//            author.setAuthorId(Integer.parseInt(rec.get("author_id").toString()));

            Object objName = rec.get("author_name");
            String authorName = objName == null ? "" : objName.toString();
            author.setAuthorName(authorName);
//            author.setAuthorName(rec.get("author_name").toString());

            Object objRecAdded = rec.get("date_added");
            Date recAdded = objRecAdded == null ? null : (Date) objRecAdded;
            author.setDateAdded(recAdded);
//            author.setDateAdded((Date)rec.get("date_added"));

            list.add(author);
        }

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
        AuthorDao dao = new AuthorDao(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book",
                "root",
                "admin",
                new MySqlDataAccess("com.mysql.jdbc.Driver",
                        "jdbc:mysql://localhost:3306/book",
                        "root",
                        "admin")
        );

        List<Author> list = dao.getListOfAuthors();

        for (Author a : list) {
            System.out.println(a.getAuthorId() + ", "
                    + a.getAuthorName() + ", " + a.getDateAdded() + "\n");
        }
    }

    @Override
    public int DeleteAuthor(String tableName, int primaryKey) {
        int result = 0;
        try {
            result = db.deleteRecordByPrimaryKey(tableName, primaryKey);
        } catch (SQLException ex) {
            Logger.getLogger(AuthorDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AuthorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
