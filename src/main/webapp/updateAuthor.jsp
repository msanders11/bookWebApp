<%-- 
    Document   : updateAuthor
    Created on : Oct 17, 2017, 1:56:35 PM
    Author     : Mike
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Author</title>
    </head>
    <body>
        <h1>Update Author</h1>





        <form name="updateAuthorForm" method="POST" action="authorController?action=updateAuthor&id=${updateAuthor.authorId}">
            <input type="hidden" value="${updateAuthor.authorId}" name="id">
            <label>Author Name: </label><input type="text" value="" name="value"><br>
            <label>Date Added: </label><input type="text" value="" name="date_added"><br>
            <input type="submit" value="Submit">
        </form>

    </body>
</html>
