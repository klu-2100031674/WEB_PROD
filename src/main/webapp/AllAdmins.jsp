<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>All Admis</h1>
<%@ page import="java.util.*" %>
<%@ page import="com.fashion.model.Admin" %>

<%-- Assuming you have a List of Products named 'products' --%>
<% List<Admin> admins = (List<Admin>) request.getAttribute("admins"); %>

<%-- Check if the 'products' list is not empty --%>
<% if (admins != null && !admins.isEmpty()) { %>
<table border="2">
<tr>
<th>Id</th>
<th>Admin Name</th>
<th>Admin Rating</th>
</tr>
<% for (Admin admin : admins) { %>
<tr>
<td><%=admin.getId()%></td>
<td><%=admin.getName()%></td>
<td><%=admin.getAdminRating()%></td>
</tr>
<%} %>
</table>
<%} %>
</body>
</html>