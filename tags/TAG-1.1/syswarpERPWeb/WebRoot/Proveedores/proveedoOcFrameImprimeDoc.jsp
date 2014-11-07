<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Impresion</title>
</head>
<%
String id_oc_cabe = request.getParameter("id_oc_cabe");
String tipo = request.getParameter("tipo");
String fueradestock = request.getParameter("fueradestock");
%>
<frameset rows="523*,30*" cols="*" framespacing="0"" frameborder="NO" border="0">
  <frame src="proveedoOcImprimeDocumento.jsp?id_oc_cabe=<%= id_oc_cabe %>&tipo=<%= tipo %>&fueradestock=<%= fueradestock %>" name="frameImprimeOcArriba" id="frameImprimeOcArriba" title="frameImprimeOcArriba" />
  <frame src="proveedoOcImprimeAbajo.jsp" name="frameImprimeOcAbajo" id="frameImprimeOcAbajo" title="frameImprimeOcAbajo" />
</frameset>
<noframes><body>
</body>
</noframes>
</html>
