<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<title>REMITO INTERNO</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<%
String remito_interno =  request.getParameter("remito_interno") ;
String plantillaImpresionJRXML =  request.getParameter("cambio_deposito_frame") ;
%>
<frameset rows="*,80" frameborder="NO" border="0" framespacing="0">
  <!--frame src="stockRemitoInterno.jsp?comprob_ms=< %=comprob_ms%>&fechamov=< %=fechamov%>" name="remitoInternoMainFrame"-->
  <frame src="../reportes/jasper/generaPDF.jsp?remito_interno=<%=remito_interno%>&plantillaImpresionJRXML=<%=plantillaImpresionJRXML%>" name="remitoInternoMainFrame">	
  <frame src="stockRemitoInternoAbajo.jsp" name="remitoInternoAbajoFrame" scrolling="NO" noresize>
</frameset>
<noframes><body>
</body></noframes>
</html>
