<!DOCTYPE html>
<html>
<head>
    <title>Update Book</title>
       <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        form {
            width: 30%;
            margin: 100px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input[type="text"] {
            width: calc(100% - 12px);
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            border: none;
            border-radius: 3px;
            color: #fff;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h1>Update Book</h1>
    <form action="LibraryServlet?action=update" method="post">
        <input type="hidden" name="id" value="${book.id}" />
        <label>Title:</label> <input type="text" name="title" value="${book.title}" required /><br/>
        <label>Author:</label> <input type="text" name="author" value="${book.author}" required /><br/>
        <input type="submit" value="Update" />
    </form>
</body>
</html>
