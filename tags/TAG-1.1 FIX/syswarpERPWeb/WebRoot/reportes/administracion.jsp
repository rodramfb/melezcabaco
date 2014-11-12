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
  <tr class="titulo">
    <td > ADMINISTRACION</td>
  </tr>
  <tr>
    <td >
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr class="fila-det">
					<td width="4%" class="fila-det-border">    
						 <a href="usuariosAbm.jsp"><img src="../imagenes/default/fileprint.png" width="22" height="21" border="0"></a>
					</td>
					<td width="96%" class="fila-det-border">Usuarios</td>
				</tr> 
				<tr class="fila-det">
					<td class="fila-det-border">    
						 <a href="gruposAbm.jsp"><img src="../imagenes/default/fileprint.png" width="22" height="21" border="0"></a>
					</td>
					<td  class="fila-det-border">Grupos</td>
				</tr>   
				
				<tr class="fila-det">
					<td class="fila-det-border">    
						 <a href="reportesAbm.jsp"><img src="../imagenes/default/fileprint.png" width="22" height="21" border="0"></a>
					</td>
					<td  class="fila-det-border">Reportes</td>
				</tr>   
					
				<tr class="fila-det">
					<td class="fila-det-border">    
						 <a href="tablasAbm.jsp"><img src="../imagenes/default/fileprint.png" width="22" height="21" border="0"></a>
					</td>
					<td  class="fila-det-border">Tablas</td>
				</tr> 
					
				<tr class="fila-det">
					<td class="fila-det-border">    
						 <a href="dataSourcesAbm.jsp"><img src="../imagenes/default/fileprint.png" width="22" height="21" border="0"></a>
					</td>
					<td  class="fila-det-border">Data Sources </td>
				</tr> 

				<tr class="fila-det">
					<td class="fila-det-border">    
						 <a href="camposAbm.jsp"><img src="../imagenes/default/fileprint.png" width="22" height="21" border="0"></a>
					</td>
					<td  class="fila-det-border">Campos </td>
				</tr> 	
				
				<tr class="fila-det">
					<td class="fila-det-border">    
						 <a href="graficoAbm.jsp"><img src="../imagenes/default/fileprint.png" width="22" height="21" border="0"></a>
					</td>
					<td  class="fila-det-border">Graficos </td>
				</tr> 			
				<tr class="fila-det">
					<td class="fila-det-border">    
						 <a href="parametrosAbm.jsp"><img src="../imagenes/default/fileprint.png" width="22" height="21" border="0"></a>
					</td>
					<td  class="fila-det-border">Parametros </td>
				</tr> 													

				<tr class="fila-det">
					<td class="fila-det-border">    
						 <a href="tipo_graficoAbm.jsp"><img src="../imagenes/default/fileprint.png" width="22" height="21" border="0"></a>
					</td>
					<td  class="fila-det-border">Tipos de Graficos </td>
				</tr> 

				<tr class="fila-det">
					<td class="fila-det-border">    
						 <a href="tipo_parametroAbm.jsp"><img src="../imagenes/default/fileprint.png" width="22" height="21" border="0"></a>
					</td>
					<td  class="fila-det-border">Tipos de Parametros </td>
				</tr> 

				<tr class="fila-det">
					<td class="fila-det-border">    
						 <a href="setupVariablesAbm.jsp"><img src="../imagenes/default/fileprint.png" width="22" height="21" border="0"></a>
					</td>
					<td  class="fila-det-border">Variables de Setup </td>
				</tr> 
								
			</table> 
		</td>
  </tr>	
</table>
</body>
</html>
