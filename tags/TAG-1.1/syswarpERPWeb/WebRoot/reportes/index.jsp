<%@ page language="java" %>
<%

response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);


%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.web.ejb.*" %>

<html>
<head>  
  <%--  
  INSTANCIAR BEAN
	--%>  
  <jsp:useBean id="VA"  class="ar.com.syswarp.web.ejb.report.BeanValidar"   scope="page"/>   
  <title>:: Sistema de Reporting ::</title>
  <link rel="stylesheet" type="text/css" href="imagenes/default/tnx.css">         
</head>
<body background="imagenes/reporting.gif">
  <%--  
  EJECUTAR SETEO DE PROPIEDADES
	--%>
  <jsp:setProperty name="VA" property="*" />  
  <% 
	VA.setResponse(response);    
	VA.setRequest(request);  		
  VA.setSession(session); 
  VA.ejecutarValidacion();

	%>			
<form action="" method="post">
<table width="370">
	<tr class="subtitulo-tres">
		<td width="362" >
			Reporting - Ingreso al sistema
		</td>
	</tr>
</table>
<table width="370">
	<tr class="text5">
		<td colspan="2" class="fila-det-bold-rojo">
		<jsp:getProperty name="VA" property="mensaje"/>	</td>
		</tr>						
	<tr class="subtitulo-tres">
		<td width="47">Nombre:</td>
		<td width="311"> <input type="text" name="usuario" value="<%= VA.getUsuario() %>"></td>
	</tr>             
	<tr class="subtitulo-tres">
		<td>Clave:</td>
		<td>	<input type="password" name="password" value="<%= VA.getPassword() %>">		</td>
	</tr>
</table>
<p><input name="ingreso" value="Ingresar" type="submit" class="obj-submit"></p>
</form>			
	
</body>
</html>