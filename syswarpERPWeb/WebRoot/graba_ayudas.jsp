<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.Iterator" %>
<%
String referencia = request.getParameter("referencia");
String ayuda = request.getParameter("ayuda");


Connection conexion;
Class.forName("org.postgresql.Driver");
conexion = DriverManager.getConnection("jdbc:postgresql://192.168.0.201:5432/swhelp", "postgres", "manager");

PreparedStatement insert = conexion.prepareStatement("INSERT INTO AYUDAS(referencia, ayuda) VALUES(?,?)");
insert.setString(1, referencia);
insert.setString(2, ayuda);
int n = insert.executeUpdate();		



%>
<html>
<head>
<title>grabar ayudas.</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
</body>
</html>
