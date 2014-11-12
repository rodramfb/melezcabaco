<%@ page language="java" %>
<%
/*
Programa usuariosFrm.jsp
Objetivo: Formulario de usuarios. 
Sistema : Syswarp Reporting
Copyrigth: Syswarp S.R.L.
Fecha de creacion: 06/2006
Fecha de ultima modificacion: - 
*/
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.web.ejb.*" %>
<%@ include file="session.jspf"%>
<%  
try {
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <%--  
  INSTANCIAR BEAN
	--%>  
  <jsp:useBean id="BUF"  class="ar.com.syswarp.web.ejb.report.BeanUsuariosFrm" scope="page"/>
  <head>
    <title>USUARIOS</title>
    <meta http-equiv="description" content="This is my page">
		<link rel = "stylesheet" href = "<%= pathskin %>">
		<script language="JavaScript" src="../vs/scripts/overlib.js"></script>
		<script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
  </head>
  <body>
  <%--  
  EJECUTAR SETEO DE PROPIEDADES
	--%>
  <jsp:setProperty name="BUF" property="*" />  	

  <%
	String titulo = BUF.getAccion().toUpperCase() + " DE USUARIO";  
  BUF.setResponse(response);    
	BUF.setRequest(request); 	
	BUF.setUsuarioalt( session.getAttribute("usuario").toString() );
	BUF.setUsuarioact( session.getAttribute("usuario").toString() );
  BUF.ejecutarValidacion();
	%>

	<form action="usuariosFrm.jsp" method="post" name="frm">
	
	<input name="accion" type="hidden" value="<%=BUF.getAccion()%>" > 	
	<input name="idusuario" type="hidden" value="<%=BUF.getIdusuario()%>" > 
<table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td>
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				<tr class="text-globales">
					<td>&nbsp;<%= titulo %></td>
				</tr>
			</table>
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr  class="fila-det-bold-rojo">
				<td  class="fila-det-border">&nbsp;</td>
				<td  class="fila-det-border">&nbsp;
					<jsp:getProperty name="BUF" property="mensaje"/></td>
			</tr>	
			<tr class="fila-det">
				<td width="12%" class="fila-det-border">&nbsp;Usuario: </td>
				<td width="88%" class="fila-det-border">&nbsp;<input name="usuario" type="text" value="<%=BUF.getUsuario()%>" class="campo"></td>
			</tr>
			
			<tr  class="fila-det">
				<td width="12%" class="fila-det-border">&nbsp;Clave: </td>
				<td width="88%" class="fila-det-border">&nbsp;<input name="clave" type="password" value="<%=BUF.getClave()%>" class="campo"></td>
			</tr>	
			
			<tr  class="fila-det">
				<td width="12%" class="fila-det-border">&nbsp;Reingrese Clave: </td>
				<td width="88%" class="fila-det-border">&nbsp;<input name="claveContraste" type="password" value="<%=BUF.getClave()%>" class="campo"></td>
			</tr>		
			
			<tr  class="fila-det">
				<td  class="fila-det-border">&nbsp;Administrador: </td>
				<td  class="fila-det-border">&nbsp;<input name="administrador" type="checkbox" value="1" <% if( BUF.getAdministrador() != 0 ) out.write("checked") ; %>  class="campo"></td>
			</tr>
			<tr  class="fila-det">
				<td  class="fila-det-border">&nbsp;Mail         : </td>
				<td  class="fila-det-border">&nbsp;<input name="email" type="text" value="<%=BUF.getEmail()%>"  class="campo"></td>
			</tr>
			<tr  class="fila-det">
				<td  class="fila-det-border">&nbsp;</td>
				<td  class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">
					<input name="volver" type="submit" class="boton" id="volver" value="Volver"> </td>
			</tr>
		</table>
   </td>
  </tr>
</table>
	</form>
  </body>
</html>
<% 
   }
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
		 System.out.println("ERROR (" + pagina + ") : "+ex);
  } 
%>