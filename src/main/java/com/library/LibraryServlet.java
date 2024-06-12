package com.library;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LibraryServlet")
public class LibraryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        try {
            switch (action) {
                case "list":
                    listBooks(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteBook(request, response);
                    break;
                default:
                    response.sendRedirect("index.jsp");
                    break;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        try {
            switch (action) {
                case "create":
                    createBook(request, response);
                    break;
                case "update":
                    updateBook(request, response);
                    break;
                default:
                    response.sendRedirect("index.jsp");
                    break;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new ServletException(ex);
        }
    }

 // Add the following method in your LibraryServlet class

    private void listBooks(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ClassNotFoundException, ServletException, IOException {
        try (Connection conn = DatabaseConnection.initializeDatabase();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM books")) {
            ResultSet rs = ps.executeQuery();
            List<Book> listBooks = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                listBooks.add(new Book(id, title, author));
            }
            request.setAttribute("listBooks", listBooks);
            request.getRequestDispatcher("listBooks.jsp").forward(request, response);
        }
    }


    private void createBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");

        try (Connection conn = DatabaseConnection.initializeDatabase();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO books (title, author) VALUES (?, ?)")) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.executeUpdate();
            response.sendRedirect("LibraryServlet?action=list");
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DatabaseConnection.initializeDatabase();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM books WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                Book book = new Book(id, title, author);
                request.setAttribute("book", book);
                request.getRequestDispatcher("updateBook.jsp").forward(request, response);
            } else {
                response.sendRedirect("LibraryServlet?action=list");
            }
        }
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");

        try (Connection conn = DatabaseConnection.initializeDatabase();
             PreparedStatement ps = conn.prepareStatement("UPDATE books SET title = ?, author = ? WHERE id = ?")) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setInt(3, id);
            ps.executeUpdate();
            response.sendRedirect("LibraryServlet?action=list");
        }
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DatabaseConnection.initializeDatabase();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM books WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            response.sendRedirect("LibraryServlet?action=list");
        }
    }
}
