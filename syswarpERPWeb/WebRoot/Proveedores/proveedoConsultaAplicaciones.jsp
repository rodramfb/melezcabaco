<%@page language="java"%>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: pedidos_cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jan 02 09:51:28 GMT-03:00 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias 


*/ 

%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="ar.com.syswarp.api.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ include file="session.jspf"%>
<% 
try{
int i = 0;
Iterator iterConsulta1   = null;
Iterator iterConsulta2   = null;
Iterator iterConsulta3   = null;

Strings str = new Strings();
String color_fondo ="";
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%-- INSTANCIAR BEAN --%>
	<jsp:useBean id="BPF"
		class="ar.com.syswarp.web.ejb.BeanProveedoConsultaAplicaciones" scope="page" />
	<head>
		<title>Consuta de Aplicaciones de Pagos a Proveedores</title>
		 <link rel = "stylesheet" href = "<%= pathskin %>">
		<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
		<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
		<script language="JavaScript" src="vs/forms/forms.js"></script>
		<script language="JavaScript" src="vs/overlib/overlib.js"></script>
		
		<script>
		function mostrarLOVDETA(pagina) {
	     frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width=800,height=450,status=yes');
	     if (frmLOV.opener == null) 
		 frmLOV.opener = self;
         }	
		 </script>
		 
		<meta http-equiv="Content-Type"	content="text/html; charset=iso-8859-1">
	</head>
	<BODY>
		<div id="popupcalendar" class="text"></div>
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
		<jsp:setProperty name="BPF" property="*" />
		<% 
 String titulo = "Consuta de Aplicaciones de Pagos a Proveedores";
	
		
		
 BPF.setResponse(response);
 BPF.setRequest(request);
 // ver esto BPF.setSession(session);
 BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPF.setTotCol(10);
 BPF.ejecutarValidacion();
 %>
		<form action="proveedoConsultaAplicaciones.jsp" method="post" name="frm">
			<input name="accion" type="hidden" value="consulta">
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							align="center">
							<tr class="text-globales">
								<td>
									<table width="100%" border="0"  cellpadding="0"
										cellspacing="0" class="text-globales">
										<tr>
											<td width="367"><%= titulo %>&nbsp;</td>
									  </td>
											<td width="50">&nbsp;											</td>
											<td width="43">
												
										  </td>
											<td width="42">
										
										  </td>
										</tr>
								  </table>
									
						  
							
					  </table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det-bold-rojo">
								<td class="fila-det-border">&nbsp;								</td>
								<td colspan="3" class="fila-det-border">
									<jsp:getProperty name="BPF" property="mensaje" />
&nbsp;																								</td>
							</tr>
							
							<tr class="fila-det">
								<td width="20%" class="fila-det-border">
									Proveedor: (*)								</td>
								<td width="37%" class="fila-det-border">
									<table width="79%" border="0">
										<tr class="fila-det-border">
											<td width="61%">
												<input name="dproveedordesde" type="text" class="campo"
													id="dproveedordesde" value="<%=BPF.getDproveedordesde() %>" size="30"
													readonly>											</td>
											<td width="39%">
												<img src="../imagenes/default/gnome_tango/actions/filefind.png"
													width="22" height="22"
													onClick="mostrarLOV('../Proveedores/lov_proveedores_desde.jsp')"
													style="cursor:pointer">											</td>
											<input name="idproveedordesde" type="hidden" id="idproveedordesde"
												value="<%=BPF.getIdproveedordesde() %>">
										</tr>
							     </table>							  </td>
							  
							  
								<td width="13%" class="fila-det-border">&nbsp;</td>
								<td width="39%" class="fila-det-border">&nbsp;</td>
							</tr>
							<tr class="fila-det">
                              <td class="fila-det-border">Tipo Consulta : (*) </td>
							  <td class="fila-det-border"><select name="tipo" class="cal-TextBox" onChange="grisarFechas();">
							  	<% if (BPF.getTipo() != null && BPF.getTipo().equalsIgnoreCase("-1"))
								{%>
									<option value="-1" selected="selected">Seleccionar</option>
								<%}else{%>
									<option value="-1" >Seleccionar</option>
								<%}%>
							  	<% if (BPF.getTipo() != null && BPF.getTipo().equalsIgnoreCase("P"))
								{%>
                                	<option value="P" selected="selected">Pendiente</option>
								<%}else{%>
									<option value="P">Pendiente</option>
								<%}%>
                                <% if ( BPF.getTipo() !=null &&  BPF.getTipo().equalsIgnoreCase("H")){ %>
                                <option value="H" selected >Historico</option>
                                <% } else { %>
                                <option value="H">Historico</option>
                                <% }  %>
                              </select></td>
							  <td class="fila-det-border">&nbsp;</td>
							  <td class="fila-det-border">&nbsp;</td>
							<tr class="fila-det">
							  <td class="fila-det-border">&nbsp;Fecha desde</td>
							  <td width="16%" class="fila-det-border"><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechadesde" value="<%=BPF.getFechadesde()%>" maxlength="12">
                          <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_4', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_4', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_4', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_4');return false;"> <img align="absmiddle" border="0" name="BTN_date_4" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
							  <td class="fila-det-border">&nbsp;</td>
							  <td class="fila-det-border">&nbsp;</td>
						  <td>						  
						  </tr>
						  <tr class="fila-det">
						  <td class="fila-det-border">&nbsp;Fecha hasta</td>
						  <td width="16%" class="fila-det-border"><span class="fila-det-border">
						  
                            <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechahasta" value="<%=BPF.getFechahasta()%>" maxlength="12">
                          <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_5', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_5', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_5', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_5');return false;"> <img align="absmiddle" border="0" name="BTN_date_5" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></span></td>
				  <td class="fila-det-border">&nbsp;</td>
				  <td class="fila-det-border">&nbsp;</td>
						  </tr>
						  
						  	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr class="fila-det">
                                  <td width="13%"><input name="validar"
											type="submit" value="Consultar" class="boton">                                  </td>
                                </tr>
                              </table>
		  </table>
          <p><input name="primeraCarga" type="hidden" value="false" >
                      </p>
		</form>
<%if (BPF.getAccion().equalsIgnoreCase("consulta") && !BPF.getTipo().equalsIgnoreCase("-1") && BPF.isCorrecto()){%>
			<table width="1100">
				<tr class="fila-det">
					<td width="194" class="fila-det-border">
						<%if(BPF.getTipo().equalsIgnoreCase("P")) {
							if(BPF.isFlag()){%>     
							   El archivo se genero exitosamente. Haga click para descargar.<img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=proveed_aplicaciones_pendiente&idproveedor=<%= BPF.getIdproveedordesde() %>', 'pdf', 800, 600);">
							   Exportar a excel <a href="./manejarArchivosDatosToXls.jsp?file=../reportes/reportes/ProveedoresAplicacionesPendiente.csv" >
                               <input type="image" src="../imagenes/default/gnome_tango/mimetypes/gnome-mime-application-vnd.ms-excel.png"/></a><%
			session.setAttribute("archivo","ProveedoresAplicacionesPendiente.csv");%>
							<%}else{%>
							   No existen registros para este proveedor y para este tipo de consulta (Pendiente).
							<%}
						}%>
						<%if(BPF.getTipo().equalsIgnoreCase("H")) {
							if(BPF.isFlag())
							{%>     
								El archivo se genero exitosamente. Haga click para descargar.<img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=proveed_aplicaciones_historico&idproveedor=<%=BPF.getIdproveedordesde()%>', 'pdf', 800, 600);">
								Exportar a excel <a href="./manejarArchivosDatosToXls.jsp?file=../reportes/reportes/ProveedoresAplicacionesHistorico.csv" >
                                <input type="image" src="../imagenes/default/gnome_tango/mimetypes/gnome-mime-application-vnd.ms-excel.png"/></a><%
			session.setAttribute("archivo","ProveedoresAplicacionesHistorico.csv");%>
							<%}else{%>
							   No existen registros para este proveedor y para este tipo de consulta (Histórico).
							<%}
						}%>
					</td>
			  </tr>
		  </table>   
					<% }%> 		
	</body>
</html>
<% 
 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (" + pagina + ") : "+ex);   
}%>

