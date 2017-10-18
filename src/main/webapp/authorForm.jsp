<%-- 
    Document   : authorForm
    Created on : Oct 16, 2017, 8:59:11 PM
    Author     : Mike
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Author</title>
    </head>
    <body>
        <h1>Author Form</h1>
        <form name="authorForm" method="POST" action="authorController?action=create">
            <label>Author Name: </label><input type="text" value="" name="value">
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
