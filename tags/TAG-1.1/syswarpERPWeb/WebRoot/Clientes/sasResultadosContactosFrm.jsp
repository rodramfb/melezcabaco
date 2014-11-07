<%@page language="java" %>

<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
%>

<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
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

	<%-- INSTANCIAR BEAN --%>  
	<jsp:useBean id="BSF"  class="ar.com.syswarp.web.ejb.BeanSasResultadosContactosFrm"   scope="page"/>

	<head>
		<title>FRMSasResultadosContactos</title>
		<meta http-equiv="description" content="DELTA">
		<link rel="stylesheet" type="text/css" href="<%=pathskin%>">
		<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
		<script>
			function handleForm() {
				document.frm.submit();
			}
		</script>
	</head>

	<BODY >

		<div id="popupcalendar" class="text"></div>

		 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
		 <jsp:setProperty name="BSF" property="*" />

		 <% 
		 String titulo = BSF.getAccion().toUpperCase() + " DE SAS - RESULTADOS DE CONTACTOS" ;
		 BSF.setResponse(response);
		 BSF.setRequest(request);
		 BSF.setUsuarioAlt( session.getAttribute("usuario").toString() );
		 BSF.setUsuarioAct( session.getAttribute("usuario").toString() );
		 BSF.setIdEmpresa( new BigDecimal( session.getAttribute("empresa").toString() ) );  
		 BSF.ejecutarValidacion();
		 %>

		<form action="sasResultadosContactosFrm.jsp" method="post" name="frm">
	
			<input name="accion" type="hidden" value="<%=BSF.getAccion()%>" >
			<input name="idResultadoContacto" type="hidden" value="<%=BSF.getIdResultadoContacto()%>" >
			<input type="hidden" name="tipoContacto" value="<%=BSF.getTipoContacto()%>">
			<input type="hidden" name="canalContacto" value="<%=BSF.getCanalContacto()%>">
			<input type="hidden" name="motivoContacto" value="<%=BSF.getMotivoContacto()%>">
			<input type="hidden" name="accionContacto" value="<%=BSF.getAccionContacto()%>">
			<input name="primeraCarga" type="hidden" value="false" >
		
			<table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
				<tr>
					<td>
						<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
		            		<tr class="text-globales">
		              			<td>&nbsp;<%= titulo %></td>
		            		</tr>
						</table> 
		            	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
		              		<tr class="fila-det-bold-rojo">
		                		<td class="fila-det-border">&nbsp;</td>
		                		<td class="fila-det-border"><jsp:getProperty name="BSF" property="mensaje"/>&nbsp;</td>
		              		</tr>
		             		<tr class="fila-det">
								<td class="fila-det-border">Tipo de contacto</td>
								<td class="fila-det-border">
									<select name="idTipoContacto" onChange="handleForm()">
										<option value="-1"  >Seleccionar</option>  		
										<%
											Iterator itertipo = BSF.getListTipos().iterator();
											while(itertipo.hasNext()) {
  
												String[] datostipo = (String[]) itertipo.next();
												%>
												<option value="<%=datostipo[0]%>" <%=Integer.parseInt(datostipo[0]) ==  BSF.getIdTipoContacto().intValue()? "selected" :""%> ><%=datostipo[1]%></option>
												<%
											}
										%>
									</select>
								</td>
							</tr>
							<tr class="fila-det">
								<td class="fila-det-border">Canal de contacto</td>
								<td class="fila-det-border">
									<select name="idCanalContacto" onChange="handleForm()" >
										<option value="-1">Seleccionar</option>  		
										<%
											Iterator itercanal = BSF.getListCanales().iterator();
											while(itercanal.hasNext()) {

												String[] datoscanal = (String[]) itercanal.next();
												%>
												<option value="<%=datoscanal[0]%>" <%=Integer.parseInt(datoscanal[0]) ==  BSF.getIdCanalContacto().intValue()? "selected" :""%>><%=datoscanal[1]%></option>
												<%
											}
										%>
									</select>
							 	</td>
							</tr>
							<tr class="fila-det">
								<td class="fila-det-border">Motivo del contacto</td>
								<td class="fila-det-border">
									<select name="idMotivoContacto" onChange="handleForm()" >
										<option value="-1">Seleccionar</option>  		
										<%
											Iterator itermotivo = BSF.getListMotivos().iterator();
											while(itermotivo.hasNext()) {

												String[] datosmotivos = (String[]) itermotivo.next();
												%>
												<option value="<%=datosmotivos[0]%>" <%=Integer.parseInt(datosmotivos[0]) ==  BSF.getIdMotivoContacto().intValue()? "selected" :""%>><%=datosmotivos[1]%></option>
												<%
											}
										%>
									</select>
								</td>
							</tr>
							<tr class="fila-det">
								<td class="fila-det-border">Acción del contacto</td>
								<td class="fila-det-border">
									<select name="idAccionContacto" >
										<option value="-1">Seleccionar</option>  		
										<%
											Iterator iteraccion = BSF.getListAcciones().iterator();
											while(iteraccion.hasNext()) {

												String[] datosacciones = (String[]) iteraccion.next();
												%>
												<option value="<%=datosacciones[0]%>" <%=Integer.parseInt(datosacciones[0]) ==  BSF.getIdAccionContacto().intValue()? "selected" :""%>><%=datosacciones[1]%></option>
												<%
											}
										%>
									</select>
								</td>
							</tr>
							<tr class="fila-det">
								<td class="fila-det-border">Resultado del contacto:</td>
								<td class="fila-det-border">
									<textarea name="resultadoContacto" cols="50" class="campo" ><%=BSF.getResultadoContacto()%></textarea>
								</td>
							</tr>
							<tr class="fila-det">
								<td class="fila-det-border" colspan="2" align="center">&nbsp;
									<input name="validar" type="submit" value="Enviar" class="boton"  >
									<input name="volver" type="submit" class="boton" id="volver" value="Volver">
								</td>
							</tr>
		          		</table>
		        	</td>
		      	</tr>
			</table>
		</form>
	</body>
</html> 
<%
} catch (Exception ex) {
	java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
	java.io.PrintWriter pw = new java.io.PrintWriter(cw, true);
	ex.printStackTrace(pw);
	System.out.println("ERROR (" + pagina + ") : " + ex);   
}
%>

