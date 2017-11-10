package edu.wctc.distjava.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Mike
 */
@Stateless
public class AuthorService implements Serializable {

    private final String AUTHOR_TABLE = "author";
    private final String AUTHOR_PK = "author_id";

    @PersistenceContext(unitName = "book_PU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public AuthorService() {
    }

    public Author findAuthorById(String id) throws ClassNotFoundException, SQLException {
        List<Author> authorList = new ArrayList();
        Integer value = Integer.parseInt(id);
        String jpql = "select a from Author a where a.authorId = :id ";
        TypedQuery q = getEm().createQuery(jpql, Author.class);
        q.setParameter("id", value);
        authorList = q.getResultList();
        
        Author author = authorList.get(0);
        return author;
    }

    public List<Author> getAuthorList() throws Exception {
        List<Author> authorList = new ArrayList<>();
        String jpql = "select a from Author a";
        TypedQuery q = getEm().createQuery(jpql, Author.class);
//        q.setMaxResults(500); 
        authorList = q.getResultList();

        return authorList;
    }

//    List<String> colNames, List<Object> colValues
    public int editAuthorById(String authorName, String dateAdded, Object pkValue) throws ClassNotFoundException, SQLException, ParseException {
        if (authorName == null || dateAdded == null || pkValue == null) {
            throw new IllegalArgumentException("No values provided");
        }
//        String jpql = "update Author a set a.authorName = :authorName AND a.dateAdded = :dateAdded where a.authorId = :id ";
//        TypedQuery q = getEm().createQuery(jpql, Author.class);
//        q.setParameter("authorName", authorName);
//        q.setParameter("dateAdded", dateAdded);
//        q.setParameter("id", pkValue);

        Author author = new Author();
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.format(dateAdded);
        Date date = (Date)dateFormat.parse(dateAdded); 
        
        Integer value = Integer.parseInt(pkValue.toString());
        
        author.setAuthorId(value);
        author.setAuthorName(authorName);
        author.setDateAdded(date);
        
        getEm().merge(author);

        return 1;
    }

    public int addAuthor(String authorName) throws ClassNotFoundException, SQLException {
        if (authorName == null) {
            throw new IllegalArgumentException("Please provide values");
        }
        
        Author author = new Author();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        dateFormat.format(date);
        
        author.setAuthorName(authorName);
        author.setDateAdded(date);
        
        getEm().persist(author);
        
        return 1;
    }

    public int removeAuthorById(String id) throws ClassNotFoundException, SQLException, NumberFormatException {
        if (id == null) {
            throw new IllegalArgumentException("id must be integer greater than zero");
        }

        Integer value = Integer.parseInt(id);

        String jpql = "delete from Author a where a.authorId = :id";
        Query q = getEm().createQuery(jpql);
        q.setParameter("id", value);

        return q.executeUpdate();
    }

}
