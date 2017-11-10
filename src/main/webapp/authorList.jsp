<%-- 
    Document   : authorList
    Created on : Sep 19, 2017, 8:34:47 PM
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
        <title>Author List</title>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <h1>Author List</h1>   
        <form method="POST" action="authorController?action= ">
            <table border="1">
                <c:forEach var="a" items="${authorList}">
                    <tr>
                        <td><input type="checkbox" name="id" value="${a.authorId}"></td>
                        <td>${a.authorName}</td>
                        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${a.dateAdded}"></fmt:formatDate></td>
                        <td><input type="button" value="Edit" onclick="location.href='authorController?action=update&id=${a.authorId}'"></td>
                        <td><input type="button" value="Delete" onclick="location.href='authorController?action=delete&id=${a.authorId}'"></td>
                    </tr>
                </c:forEach>
            </table>
        </form>
        <input type="button" value="Add" onclick="location.href='authorController?action=add'">
    </body>
</html>
