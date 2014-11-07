<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<title>REMITO MOVIMIENTO ENTRADA</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<%
String remito_interno =  request.getParameter("remito_interno") ;
String fechamov =  request.getParameter("fechamov") ;
%>
<frameset rows="*,80" frameborder="NO" border="0" framespacing="0">
  <!--frame src="stockRemitoMovSalida.jsp?nint_ms_an=< %=nint_ms_an%>&fechamov=< %=fechamov%>" name="remitoMovSalidaMainFrame"-->
  <frame src="vRemitoInternoFrm.jsp?remito_interno=<%=remito_interno%>&fechamov=<%=fechamov%>" name="remitoMovSalidaMainFrame">	
  <frame src="stockRemitoMovSalidaAbajo.jsp" name="remitoMovSalidaAbajoFrame" scrolling="NO" noresize>
</frameset>
<noframes><body>
</body></noframes>
</html>
