<%@ page language="java" %>
<%
/*
Programa tablasFrm.jsp
Objetivo: Formulario de tablas. 
Sistema : Syswarp Reporting
Copyrigth: Syswarp S.R.L.
Fecha de creacion: 06/2006
Fecha de ultima modificacion: - 
*/

response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
%>

<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.web.ejb.*" %>
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%  
try {

Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <%--  
  INSTANCIAR BEAN
	--%>  
  <jsp:useBean id="BTF"  class="ar.com.syswarp.web.ejb.report.BeanTablasFrm" scope="page"/>
  <head>
    <title>TABLAS</title>
    <meta http-equiv="description" content="This is my page">
		<link rel = "stylesheet" href = "<%= pathskin %>">
		<script language="JavaScript" src="../vs/scripts/overlib.js"></script>
		<script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
  </head>
  <body>
  <%--  
  EJECUTAR SETEO DE PROPIEDADES
	--%>
  <jsp:setProperty name="BTF" property="*" />  	

  <%
	String titulo = BTF.getAccion().toUpperCase() + " DE TABLA";  
  BTF.setResponse(response);    
	BTF.setRequest(request); 
	BTF.setUsuarioalt( session.getAttribute("usuario").toString() );	
	BTF.setUsuarioact( session.getAttribute("usuario").toString() );
  BTF.ejecutarValidacion();
	Hashtable htDT = BTF.getHtDT();
	Enumeration enumDT = htDT.keys();
	
	%>

	<form action="tablasFrm.jsp" method="post" name="frm">
	
	<input name="accion" type="hidden" value="<%=BTF.getAccion()%>" > 	
	<input name="idtabla" type="hidden" value="<%=BTF.getIdtabla()%>" > 
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
					<jsp:getProperty name="BTF" property="mensaje"/></td>
			</tr>	
			<tr class="fila-det">
				<td width="12%" class="fila-det-border">&nbsp;Tabla: </td>
				<td width="88%" class="fila-det-border"><input name="tabla" type="text" value="<%=BTF.getTabla()%>" class="campo">&nbsp;</td>
			</tr>
			
			
			<tr class="fila-det">
				<td width="12%" class="fila-det-border">&nbsp;Qury Carga: </td>
				<td width="88%" class="fila-det-border">
				  <textarea name="query_carga" cols="70" rows="6" class="campo"><%=str.esNulo(BTF.getQuery_carga())%></textarea>&nbsp;</td>
			</tr>			
			
			<tr class="fila-det">
				<td width="12%" class="fila-det-border">&nbsp;Qury Consulta: </td>
				<td width="88%" class="fila-det-border">
				  <textarea name="query_consulta" cols="70" rows="6" class="campo"><%=str.esNulo(BTF.getQuery_consulta())%></textarea>&nbsp;</td>
			</tr>		
			
			<tr class="fila-det">
				<td width="12%" class="fila-det-border">&nbsp;Data Source: </td>
				<td width="88%" class="fila-det-border">&nbsp;<select name="iddatasource" class="campo">
																												<option value="0">Seleccionar</option>
																											<% 
																											String sel = "";
																											while(enumDT.hasMoreElements()){
																												String key =  (String)enumDT.nextElement() ;
																												if( Long.parseLong(key) == BTF.getIddatasource() ) sel = "selected";
																												else sel = "";
																							
																											 %>
																											 <option value="<%= key %>" <%= sel %> ><%= htDT.get(key) %></option>
																											<%  
																											}
																											%>
																											</select>
				</td>
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