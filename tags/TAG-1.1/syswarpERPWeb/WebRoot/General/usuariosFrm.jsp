<%@ page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
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
  <jsp:useBean id="BUF"  class="ar.com.syswarp.web.ejb.BeanUsuariosFrm" scope="page"/>
  <head>
    <title>USUARIOS</title>
   <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script></head>

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
	BUF.setIdusuariosesion( session.getAttribute("idusuario").toString() );
	BUF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
    BUF.ejecutarValidacion();
	%>

	<form action="usuariosFrm.jsp" method="post" name="frm">
	<input name="accion" type="hidden" value="<%=BUF.getAccion()%>" > 	
	<input name="idusuario" type="hidden" value="<%=BUF.getIdusuariosesion()%>" > 
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
				<td width="16%" class="fila-det-border">&nbsp;Usuario: </td>
				<td width="84%" class="fila-det-border"><input name="usuario" type="text" value="<%=BUF.getUsuarioalt()%>" class="campo" readonly="no"></td>
			</tr>
			<tr  class="fila-det">                                                                     
				<td width="16%" class="fila-det-border">&nbsp;Clave Anterior: </td>
			  <td width="84%" class="fila-det-border"><input name="claveanterior" type="password" value="<%=BUF.getClaveanterior()%>" class="campo"></td>
			</tr>
			<tr  class="fila-det">
				<td width="16%" class="fila-det-border">&nbsp;Clave Nueva: </td>
				<td width="84%" class="fila-det-border"><input name="clave" type="password" value="<%=BUF.getClave()%>" class="campo"></td>
			</tr>	
			
			<tr  class="fila-det">
				<td width="16%" class="fila-det-border">&nbsp;Reingrese Clave Nueva: </td>
				<td width="84%" class="fila-det-border"><input name="claveContraste" type="password" value="<%=BUF.getClave()%>" class="campo"></td>
			</tr>
			<tr  class="fila-det">
				<td  class="fila-det-border">&nbsp;</td>
				<td  class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton"></td>
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