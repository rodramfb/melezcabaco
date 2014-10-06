<%@page language="java" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.web.ejb.*" %>
<%@ include file="session.jspf"%>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: reportes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jun 28 09:50:53 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias
*/ 

try {
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BRF"  class="ar.com.syswarp.web.ejb.report.BeanReportesFrm"   scope="page"/>
<head>
 <title>Reportes</title>
    <meta http-equiv="description" content="Pagina de reportes">
		<link rel = "stylesheet" href = "<%= pathskin %>">
		<script language="JavaScript" src="../vs/scripts/overlib.js"></script>
		<script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BRF" property="*" />

  <%
	String titulo = BRF.getAccion().toUpperCase() + " DE REPORTE";  
  BRF.setResponse(response);    
	BRF.setRequest(request); 
	BRF.setUsuarioalt( session.getAttribute("usuario").toString() );	
	BRF.setUsuarioact( session.getAttribute("usuario").toString() );
  BRF.ejecutarValidacion();
	%> 

  <form action="reportesFrm.jsp" method="post" name="frm">
	<input name="accion" type="hidden" value="<%=BRF.getAccion()%>" > 	
	<input name="idreporte" type="hidden" value="<%=BRF.getIdreporte()%>" > 	
			 
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
							<jsp:getProperty name="BRF" property="mensaje"/></td>
					</tr>	
					<tr class="fila-det">
						<td width="12%" class="fila-det-border">&nbsp;Reporte: </td>
						<td width="88%" class="fila-det-border">&nbsp;
						 <input name="reporte" type="text" value="<%=BRF.getReporte()%>" class="campo" size="100" maxlength="100"  >
					  </td>
					</tr>
					<tr  class="fila-det">
						<td  class="fila-det-border">&nbsp;Comentario         : </td>
						<td  class="fila-det-border">&nbsp;
							<input name="comentario" type="text" value="<%=BRF.getComentario()%>" class="campo" size="100" maxlength="1000"  >
						</td>
					</tr>
					<tr  class="fila-det">
						<td  class="fila-det-border">&nbsp;Skin         : </td>
						<td  class="fila-det-border">&nbsp;
							<input name="skin" type="text" value="<%=BRF.getSkin()%>" class="campo" size="100" maxlength="100"  >
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
