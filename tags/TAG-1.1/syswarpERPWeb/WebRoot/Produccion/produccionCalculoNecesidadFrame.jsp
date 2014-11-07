<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<% 
 String idop = request.getParameter("idop") ;
 String tipoPedido =  request.getParameter("tipoPedido") ;      
%>
<title>Impresion de Orden de Producción</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<frameset rows="*,80" frameborder="NO" border="0" framespacing="0">
  <frame src="produccionCalculoNecesidadImprime.jsp?idop=<%=idop%>&tipoPedido=<%=tipoPedido%>" name="produccionCNTopFrame">
  <frame src="produccionCalculoNecesidadBottom.jsp" name="produccionCNBottomFrame" scrolling="NO" noresize>
</frameset> 
<noframes><body>
</body></noframes>
</html>
