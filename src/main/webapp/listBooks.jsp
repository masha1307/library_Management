<%@ page import="java.util.List" %>
<%@ page import="com.library.Book" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>List Books</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f2f2f2;
        }
        .container {
            width: 80%;
            margin: 20px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .logout {
            text-align: right;
        }
        .logout a {
            color: #555;
            text-decoration: none;
            padding: 5px 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
            background-color: #f9f9f9;
        }
        .logout a:hover {
            background-color: #e9e9e9;
        }
        .btn {
            padding: 5px 10px;
            font-size: 14px;
            margin-right: 5px;
            text-decoration: none;
            color: #fff;
            background-color: #007bff;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>List of Books</h1>
        <div class="logout">
            <a href="LogoutServlet">Logout</a>
        </div>
        <table>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Actions</th>
            </tr>
            <% HttpSession session2 = request.getSession(false);
               if (session == null || session.getAttribute("username") == null) {
                   response.sendRedirect("login.jsp");
               } else {
                   List<Book> listBooks = (List<Book>) request.getAttribute("listBooks");
                   if (listBooks != null) {
                       for (Book book : listBooks) { %>
                           <tr>
                               <td><%= book.getId() %></td>
                               <td><%= book.getTitle() %></td>
                               <td><%= book.getAuthor() %></td>
                               <td>
                                   <a class="btn" href="LibraryServlet?action=edit&id=<%= book.getId() %>">Update</a>
                                   <a class="btn" href="LibraryServlet?action=delete&id=<%= book.getId() %>">Delete</a>
                               </td>
                           </tr>
                       <% }
                   }
               } %>
        </table>
    </div>
</body>
</html>
