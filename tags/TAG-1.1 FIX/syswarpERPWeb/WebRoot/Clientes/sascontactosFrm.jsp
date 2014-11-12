<%@page language="java" %>

<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: sascontactos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri May 27 12:30:35 ART 2011 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias
*/ 
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
	<jsp:useBean id="BSF"  class="ar.com.syswarp.web.ejb.BeanSascontactosFrm"   scope="page"/>

	<head>
		<title>FRMSascontactos</title>
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
		 String titulo = BSF.getAccion().toUpperCase() + " DE SAS - CONTACTOS" ;
		 BSF.setResponse(response);
		 BSF.setRequest(request);
		 BSF.setUsuarioalt( session.getAttribute("usuario").toString() );
		 BSF.setUsuarioact( session.getAttribute("usuario").toString() );
		 BSF.setIdempresa ( new BigDecimal( session.getAttribute("empresa").toString() )  );  
		 BSF.ejecutarValidacion();
		 %>

		<form action="sascontactosFrm.jsp" method="post" name="frm">
	
			<input name="accion" type="hidden" value="<%=BSF.getAccion()%>" >
			<input name="idcontacto" type="hidden" value="<%=BSF.getIdcontacto()%>" >
			<input name="idcliente" type="hidden" value="<%=BSF.getIdcliente()%>" >
			<input type="hidden" name="tipocontacto" value="<%=BSF.getTipocontacto()%>">
			<input type="hidden" name="canalcontacto" value="<%=BSF.getCanalcontacto()%>">
			<input type="hidden" name="motivocontacto" value="<%=BSF.getMotivocontacto()%>">
			<input type="hidden" name="accionContacto" value="<%=BSF.getAccionContacto()%>">
			<input type="hidden" name="resultadoContacto" value="<%=BSF.getResultadoContacto()%>">
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
									<select name="idtipocontacto" onChange="handleForm()">
										<option value="-1"  >Seleccionar</option>  		
										<%
											Iterator itertipo = BSF.getListtipo().iterator();
											while(itertipo.hasNext()) {

												String[] datostipo = (String[]) itertipo.next();
												%>
												<option value="<%=datostipo[0]%>" <%=Integer.parseInt(datostipo[0]) ==  BSF.getIdtipocontacto().intValue()? "selected" :""%> ><%=datostipo[1]%></option>
												<%
											}
										%>
									</select>
								</td>
							</tr>
							<tr class="fila-det">
								<td class="fila-det-border">Canal de contacto</td>
								<td class="fila-det-border">
									<select name="idcanalcontacto" onChange="handleForm()" >
										<option value="-1">Seleccionar</option>  		
										<%
											Iterator itercanal = BSF.getListcanal().iterator();
											while(itercanal.hasNext()) {

												String[] datoscanal = (String[]) itercanal.next();
												%>
												<option value="<%=datoscanal[0]%>" <%=Integer.parseInt(datoscanal[0]) ==  BSF.getIdcanalcontacto().intValue()? "selected" :""%>><%=datoscanal[1]%></option>
												<%
											}
										%>
									</select>
							 	</td>
							</tr>
							<tr class="fila-det">
								<td class="fila-det-border">Motivo del contacto</td>
								<td class="fila-det-border">
									<select name="idmotivocontacto" onChange="handleForm()" >
										<option value="-1">Seleccionar</option>  		
										<%
											Iterator itermotivo = BSF.getLismotivos().iterator();
											while(itermotivo.hasNext()) {

												String[] datosmotivos = (String[]) itermotivo.next();
												%>
												<option value="<%=datosmotivos[0]%>" <%=Integer.parseInt(datosmotivos[0]) ==  BSF.getIdmotivocontacto().intValue()? "selected" :""%>><%=datosmotivos[1]%></option>
												<%
											}
										%>
									</select>
								</td>
							</tr>
							<tr class="fila-det">
								<td class="fila-det-border">Accion del contacto</td>
								<td class="fila-det-border">
									<select name="idAccionContacto" onChange="handleForm()" >
										<option value="-1">Seleccionar</option>  		
										<%
											Iterator iterAcciones = BSF.getListAcciones().iterator();
											while(iterAcciones.hasNext()) {
	
												String[] datosAcciones = (String[]) iterAcciones.next();
												%>
												<option value="<%=datosAcciones[0]%>" <%=Integer.parseInt(datosAcciones[0]) ==  BSF.getIdAccionContacto().intValue()? "selected" :""%>><%=datosAcciones[1]%></option>
												<%
											}
										%>
									</select>
								</td>
							</tr>
							<tr class="fila-det">
								<td class="fila-det-border">Resultado del contacto</td>
								<td class="fila-det-border">
									<select name="idResultadoContacto" >
										<option value="-1">Seleccionar</option>  		
										<%
											Iterator iterResultados = BSF.getListResultados().iterator();
											while(iterResultados.hasNext()) {
	
												String[] datosResultados = (String[]) iterResultados.next();
												%>
												<option value="<%=datosResultados[0]%>" <%=Integer.parseInt(datosResultados[0]) ==  BSF.getIdResultadoContacto().intValue()? "selected" :""%>><%=datosResultados[1]%></option>
												<%
											}
										%>
									</select>
								</td>
							</tr>
							<tr class="fila-det">
								<td class="fila-det-border">Descripcion del contacto:</td>
								<td class="fila-det-border">
									<textarea name="descripcion" cols="50" class="campo" ><%=BSF.getDescripcion()%></textarea>
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
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (" + pagina + ") : "+ex);   
}
%>

