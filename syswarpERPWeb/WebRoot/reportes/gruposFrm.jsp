<%@ page language="java" %>
<%
/*
Programa gruposFrm.jsp
Objetivo: Formulario de Grupos 
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
  <jsp:useBean id="BG"  class="ar.com.syswarp.web.ejb.report.BeanGruposFrm" scope="page"/>
  <head>
    <title></title>
    <meta http-equiv="description" content="This is my page">
		<link rel = "stylesheet" href = "<%= pathskin %>">
		<script language="JavaScript" src="../vs/scripts/overlib.js"></script>
		<script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
  </head>
  <body>
  <%--  
  EJECUTAR SETEO DE PROPIEDADES
	--%>
  <jsp:setProperty name="BG" property="*" />  	

  <%
	String titulo = BG.getAccion().toUpperCase() + " DE GRUPO";  
  BG.setResponse(response);    
	BG.setRequest(request); 
	BG.setUsuarioalt( session.getAttribute("usuario").toString() );
	BG.setUsuarioact( session.getAttribute("usuario").toString() );
  BG.ejecutarValidacion();
	%>

	<form action="gruposFrm.jsp" method="post" name="frm">
	
	<input name="accion" type="hidden" value="<%=BG.getAccion()%>" > 	
	<input name="idgrupo" type="hidden" value="<%=BG.getIdgrupo()%>" > 
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
					<jsp:getProperty name="BG" property="mensaje"/></td>
			</tr>	
			<tr class="fila-det">
				<td width="12%" class="fila-det-border">&nbsp;Grupo: </td>
				<td width="88%" class="fila-det-border">&nbsp;<input name="grupo" type="text" value="<%=BG.getGrupo()%>" class="campo"></td>
			</tr>
			<tr  class="fila-det">
				<td  class="fila-det-border">&nbsp;Descripci&oacute;n         : </td>
				<td  class="fila-det-border">&nbsp;<input name="descripcion" type="text" value="<%=BG.getDescripcion()%>"  class="campo"></td>
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
