<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<% 
String pathskin = "imagenes/default/tnx.css";
session.setAttribute("pathskin",pathskin);
%>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<frameset rows="34,*" cols="*" framespacing="0" frameborder="NO" border="0">
  <frame src="menu.jsp" name="topFrame" scrolling="NO" noresize >
  <frame src="inicialRigth.jsp" name="mainFrame">
</frameset>
<noframes><body>
</body></noframes>
</html>
