<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ include file="session.jspf"%>
<%
String pathskin = session.getAttribute("pathskin").toString();
String administrador = session.getAttribute("administrador").toString();
%>
<html>

<head>
<title>TNX</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel = "stylesheet" href = "<%= pathskin %>">
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td class="titulo2">
			<table width="100%">
				<tr class="fila-det">
				  <td width="12%" class="titulo">REPORTING</td>
					<td width="6%" >&nbsp; </td>
					<td width="8%" class="fila-det"><a href="asistente1.jsp" target="mainFrame">Reportes</a> </td>
				<% 
				if(administrador.equalsIgnoreCase("1")){
				 %> 					
				  <td width="11%" class="fila-det"><a href="administracion.jsp" target="mainFrame">Administraci&oacute;n</a></td>

			    <%  
			 }%>				
			    <td width="63%" class="fila-det"><a href="index.html" target="_parent">Salir</a></td>			 	
				</tr> 
			</table> 
		</td>
  </tr>	
</table>
</body>
</html>
