<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Carga de ayudas.</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<form name="frm" method="post" action="graba_ayudas.jsp">
  <p>
    <input name="referencia" type="text" id="referencia" size="50" maxlength="50">
</p>
  <p>
    <textarea name="ayuda" cols="80" rows="15" id="ayuda"></textarea>
</p>
  <p>
    <input name="subir" type="submit" id="subir" value="Subir">
    <input type="reset" name="Reset" value="Reset">
  </p>
</form>
</body>
</html>
