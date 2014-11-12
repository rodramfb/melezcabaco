<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<title>Comprobante de Pagos</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<%
String nrocomprobante = request.getParameter("nrocomprobante");
String idproveedor = request.getParameter("idproveedor");
%>
<frameset rows="*,80"  framespacing="0" frameborder="NO" border="0">
  <frame src="CajaImprimePagosTopBORRAR.jsp?nrocomprobante=<%=nrocomprobante%>&idproveedor=<%=idproveedor%>" name="cajaArribaFrame">
  <frame src="CajaImprimePagosBottomBORRAR.jsp" name="cajaAbajoFrame" scrolling="NO" noresize>
</frameset>
<noframes><body>
</body></noframes>
</html>
