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
<html>
<head>
<title>Consulta de Estados</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">
	function passBack(idestado, estado) {   
	   opener.document.forms[0].estado.value = estado;
	   opener.document.forms[0].idestado.value = idestado;
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
<p align="left" class=subtitu><strong>SELECCION DE ESTADOS </strong></p>
<hr>
<form action="lov_wapEstados.jsp">
<%  
    
  try {
    BC repo = null;
    javax.naming.Context context = new javax.naming.InitialContext();
    Object object = context.lookup("BC");
    BCHome sHome = (BCHome) javax.rmi.PortableRemoteObject.narrow(object, BCHome.class);
    repo = sHome.create();
	String sp = "spwapestados";
	java.sql.ResultSet rsConsulta = repo.getTransaccion(sp, "'A'"); 
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
				  <td width="67%" bgcolor="#999999"><div align="center"><font class=datasheetField>Conjunto</font></div></td>
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
			out.write("<p class=error>Error al Recuperar Estados</p>");
  }
  catch (Exception ex) {
            System.out.println("ERROR: " + ex);
  }			
	%>
</form>
</body>
</html>