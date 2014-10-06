<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.util.*" %> 
<%@ include file="session.jspf"%>
<%
try{
List reportes = new java.util.ArrayList();	 
Iterator iterReportes   = null;
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
String idusuario = session.getAttribute("idusuario").toString();
%>
<html>
  <%--  
  INSTANCIAR BEAN
	--%>  
  <jsp:useBean id="BAU"  class="ar.com.syswarp.web.ejb.report.BeanAsistenteUno" scope="page"/>
	<% 
	 BAU.setIdusuario(Long.parseLong(idusuario));
	 BAU.ejecutarValidacion();
   reportes = BAU.getListReportes();
   iterReportes = reportes.iterator();  	
	 %>
<head>
<title>TNX</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel = "stylesheet" href = "<%= pathskin %>">
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr class="titulo">
    <td height="39" >Seleccion del Reporte</td>
  </tr>
  <tr>
    <td class="titulo2">
			<table>
				<tr class="fila-encabezado">
					<td width="23">Sel</td>
					<td width="100">Reporte</td>
					<td width="344">Comentario</td>
				</tr>
			<% while(iterReportes.hasNext()){ 
				String[] sCampos = (String[]) iterReportes.next(); %>
				<tr class="fila-det">
					<td class="fila-det-border">    
						 <a href="asistente2.jsp?idreporte=<%=sCampos[0]%>"><img src="imagenes/default/fileprint.png" width="22" height="21" border="0"></a>
					</td>
					<td class="fila-det-border"><%=sCampos[1]%></td>
					<td class="fila-det-border"><%=sCampos[2]%></td>
				</tr>   
			<%}%>
			</table>		
		</td>
  </tr>
</table>

</body>
</html>
<% 
   }
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
  }   %>