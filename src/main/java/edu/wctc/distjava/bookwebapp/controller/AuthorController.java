package edu.wctc.distjava.bookwebapp.controller;

import edu.wctc.distjava.bookwebapp.model.Author;
import edu.wctc.distjava.bookwebapp.model.AuthorDao;
import edu.wctc.distjava.bookwebapp.model.AuthorService;
import edu.wctc.distjava.bookwebapp.model.IAuthorDao;
import edu.wctc.distjava.bookwebapp.model.MySqlDataAccess;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mike
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/authorController"})
public class AuthorController extends HttpServlet {

    public static final String ACTION = "action";
    public static final String ID = "id";
    public static final String COL_NAME = "colName";
    public static final String AUTHOR_NAME = "value";
    public static final String LIST_ACTION = "list";
    public static final String DELETE_ACTION = "delete";
    public static final String UPDATE_ACTION = "update";
    public static final String CREATE_ACTION = "create";
    public static final String UPDATE_AUTHOR_ACTION = "updateAuthor";
    public static final String DATE_ADDED_COL_NAME = "date_added";
    public static final String AUTHOR_NAME_COL_NAME = "author_name";

    public static final String UPDATE_AUTHOR_DESTINATION = "/updateAuthor.jsp";
    public static final String LIST_AUTHOR_DESTINATION = "/authorList.jsp";

    private String driverClass;
    private String url;
    private String username;
    private String password;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String destination = "/authorList.jsp"; //default

        try {
            String action = request.getParameter(ACTION);

//            AuthorService authorService = new AuthorService(
//                    new AuthorDao(driverClass, url, userName, password,new MySqlDbAccess()));
            IAuthorDao dao = new AuthorDao(
                    driverClass,
                    url,
                    username,
                    password,
                    new MySqlDataAccess());

            AuthorService authorService = new AuthorService(dao);

            List<Author> authorList = null;
            List<Author> updateAuthor = null;

            if (action.equalsIgnoreCase(LIST_ACTION)) {
                destination = LIST_AUTHOR_DESTINATION;
                authorList = authorService.getAuthorList();
                request.setAttribute("authorList", authorList);
                // add more if logic for other CRUD methods Add/Update/Delete
            } else if (action.equalsIgnoreCase(DELETE_ACTION)) {
                String id = request.getParameter(ID);

                authorService.removeAuthorById(id);

                authorList = authorService.getAuthorList();
                request.setAttribute("authorList", authorList);

            } else if (action.equalsIgnoreCase(UPDATE_ACTION)) {

                String id = request.getParameter(ID);

                updateAuthor = authorService.findAuthorById(id);

                Author author = new Author();
                author = updateAuthor.get(0);

                request.setAttribute("updateAuthor", author);

                destination = UPDATE_AUTHOR_DESTINATION;

            } else if (action.equalsIgnoreCase(UPDATE_AUTHOR_ACTION)) {
                String id = request.getParameter(ID);
                String authorName = request.getParameter(AUTHOR_NAME);
                String date = request.getParameter(DATE_ADDED_COL_NAME);

                List<Object> colValues = Arrays.asList(authorName, date);
                List<String> colNames = Arrays.asList(AUTHOR_NAME_COL_NAME, DATE_ADDED_COL_NAME);

                authorService.editAuthorById(colNames, colValues, id);

                authorList = authorService.getAuthorList();
                request.setAttribute("authorList", authorList);

            } else if (action.equalsIgnoreCase(CREATE_ACTION)) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                dateFormat.format(date);

                List<String> colNames = Arrays.asList(AUTHOR_NAME_COL_NAME, DATE_ADDED_COL_NAME);
                Object obj = request.getParameter(AUTHOR_NAME);
                List<Object> colValues = Arrays.asList(obj, date);

                authorService.addAuthor(colNames, colValues);

                authorList = authorService.getAuthorList();
                request.setAttribute("authorList", authorList);
            }

        } catch (Exception e) {
//            destination = "/authorList.jsp";
            request.setAttribute("errorMessage", e.getMessage());
        }

        RequestDispatcher view
                = request.getRequestDispatcher(destination); // Where you are sending the data
        view.forward(request, response);
    }

    @Override
    public void init() throws ServletException {
        driverClass = getServletContext()
                .getInitParameter("driverClass");
        url = getServletContext()
                .getInitParameter("url");
        username = getServletContext()
                .getInitParameter("username");
        password = getServletContext()
                .getInitParameter("password");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
