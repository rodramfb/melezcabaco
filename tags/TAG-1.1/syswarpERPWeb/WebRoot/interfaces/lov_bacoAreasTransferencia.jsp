<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: contablecencosto
   Copyrigth(r) sysWarp S.R.L. 
*/ 
%>
<%@ page import="ar.com.syswarp.ejb.*" %> 
<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%//@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %> 
<%@ page import="ar.com.syswarp.api.Common"%>

<% 

String cmpCod =  request.getParameter("cmpCod");
String cmpDesc =  request.getParameter("cmpDesc");

 %>
<html>
<head>
<title>Consulta de Areas</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">
	function passBack(codigo, descripcion) {   
	   opener.document.forms[0].<%= cmpCod %>.value = codigo;
	   opener.document.forms[0].<%= cmpDesc %>.value = descripcion;
	   close();
	}
	
	//
	
	function Find_OnClick() {
	   document.forms[0].submit();
	}
</script>
<link rel="stylesheet" href="jmc.css" type="text/css">
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0">
<p align="left" class=subtitu><strong>SELECCION DE AREAS </strong></p>
<hr>
<form action="lov_bacoAreasTransferencia.jsp">
<input name="cmpCod"  type="hidden" value="<%= cmpCod %>">
<input name="cmpDesc" type="hidden" value="<%= cmpDesc %>">
<%  
    
  try {
	BC bc = Common.getBc();
	String sp = "spabm_areas";
	java.sql.ResultSet rsConsulta = bc.getTransaccion(sp, " 'T' "); 
	if (rsConsulta != null) {
		boolean existenReg = false;
		boolean esPrimero = true;
		while (rsConsulta.next()) {
			existenReg = true;
			if (esPrimero) {
				esPrimero = false; %>
			  <table width="100%"  cellPadding="1" cellSpacing="1"  border="1" class=color-tabletrim >
				<tr class=color-columnheaders> 
				  <td width="3%" height="13" bgcolor="#999999">&nbsp;</td>
				<td width="30%" bgcolor="#999999"><div align="center"><font class=datasheetField>Codigo</font></div></td>
				  <td width="67%" bgcolor="#999999"><div align="center"><font class=datasheetField>Area</font></div></td>
				</tr>
		<% 
			} %>
				<tr class=c> 
				  <td width="3%" height="15"><a href="javascript:passBack('<%=rsConsulta.getString(1)%>', '<%=rsConsulta.getString(2)%>')"><img src="../imagenes/default/gnome_tango/actions/filefind.png"  width="21" height="17" border="0"></a></td>
				  <td width="30%" height="15"><font class=datasheetField><%=rsConsulta.getString(1)%>&nbsp;</font></td>
				  <td width="67%" height="15"><font class=datasheetField><%=rsConsulta.getString(2)%>&nbsp;</font></td>
				</tr>
	<%	}
			if (!existenReg)  
			  out.write("<p class=error>No existen registros para la Búsqueda</p>");
			else {%> 	
  </table>  <%
			} 
		} 
		else  
			out.write("<p class=error>Error al Recuperar Areass</p>");
  }
  catch (Exception ex) {
            System.out.println("ERROR: " + ex);
  }			
	%>
</form>
</body>
</html>