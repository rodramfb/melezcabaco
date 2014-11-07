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
Strings str = new Strings();
String color_fondo ="";
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%-- INSTANCIAR BEAN --%>
	<jsp:useBean id="BPF"
		class="ar.com.syswarp.web.ejb.BeanSuspensionEntregaRegular" scope="page" />
	<head>
		<title>Suspension de Entregas Regulares</title>

<link rel = "stylesheet" href = "../imagenes/default/erp-style.css">

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
 String titulo = "SUSPENSION DE ENTREGAS REGULARES PARA CLIENTE: " + BPF.getCliente() ;
	
		
		
 BPF.setResponse(response);
 BPF.setRequest(request);
 // ver esto BPF.setSession(session);
 BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPF.ejecutarValidacion();
 java.util.List Consulta1 = new java.util.ArrayList();

 Consulta1 = BPF.getMovimientosList();
 
 iterConsulta1 = Consulta1.iterator();
 %>
		<form action="Suspensionentregaregular.jsp" method="post" name="frm">
	    <input name="accion" type="hidden" value="consulta">
      <input name="cliente" type="hidden" class="campo" id="cliente" value="<%=BPF.getCliente()%>" size="33">
      <input name="idcliente" type="hidden" id="idcliente" value="<%=BPF.getIdcliente()%>">
			

						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							align="center">
							<tr class="text-globales">
								<td>
									<table width="100%" border="0"  cellpadding="0"
										cellspacing="0" class="text-globales">
										<tr>
											<td width="367" height="54"><%= titulo %>&nbsp;</td>
										</tr>
								  </table>
									
								</td>
							</tr>
					  </table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det-bold-rojo">
								<td height="31" class="fila-det-border">&nbsp;</td>
							  <td class="fila-det-border"><jsp:getProperty name="BPF" property="mensaje" />&nbsp;</td>
							</tr>							
              <tr class="fila-det">
                <td height="29" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="fila-det">
                      <td width="25%">Consultar / Anular Suspensi&oacute;n</td>
                      <td width="75%"><img src="../imagenes/default/gnome_tango/actions/finish.png" title="Consultar/Anular Suspensiones para Cliente: <%=BPF.getCliente()%>" width="20" height="20"  onClick="abrirVentana('../General/Consultasuspensionporcliente.jsp?idcliente=<%=BPF.getIdcliente()%>&cliente=<%=BPF.getCliente()%>' , 'suspensiones', 750, 400)" style="cursor:pointer"></td>
                  </tr>
                  </table></td>              
              <tr class="text-dos">
                <td height="8"  > </td>
                <td  > </td>
              </tr>
              <tr class="fila-det">
								<td width="11%" height="25" class="fila-det-border">A&ntilde;o</td>
								<td width="89%" class="fila-det-border">
	    
									<select name="anio" id="anio" class="campo">
                    <option value="0">Seleccionar</option>
									<% for(int year = Calendar.getInstance().get(Calendar.YEAR);year<Calendar.getInstance().get(Calendar.YEAR)+3;year++){ %>
										<option value="<%= year %>" <%= Integer.parseInt(BPF.getAnio()) == year ?  "selected" : "" %>><%= year %></option>
									<% } %>
									</select>                </td>
						  </tr><tr class="fila-det">
						    <td width="11%" height="28" class="fila-det-border">Mes
							<input name="mes" type="hidden" id="mes" value="<%=BPF.getMes()%>"></td>
						    <td width="89%" class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                              <tr class="fila-det-border">
                                <td width="61%" ><input name="des_mes" type="text" class="campo" id="des_mes" value="<%=str.esNulo(BPF.getDes_mes())%>" size="30" readonly></td>
                                <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_meses.jsp', 'meses', 800, 450)" style="cursor:pointer"></td>
                              </tr>
                            </table></td>
					      <tr class="fila-det">
					        <td height="28" class="fila-det-border">Motivo</td>
					        <td class="fila-det-border"><select name="idmotsusp" id="idmotsusp" class="campo" style="width:200px" >
                    <option value="">Seleccionar</option>
                    <%
                   iterConsulta1 = BPF.getMotivoList().iterator();   
                    while(iterConsulta1.hasNext()){
                     String[] datos = (String[]) iterConsulta1.next();
                     %>
                    <option value="<%= datos[0] %>" <%=  BPF.getIdmotsusp().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                    <% 
                   } %>
                  </select></td> 
			        <tr class="fila-det">
                <td height="46" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><input name="validar"
											type="submit" value="Generar Suspensi&oacute;n" class="boton"></td>
                      </table>
	</form>
        <%if (BPF.getAccion().equalsIgnoreCase("consulta")){ %>
        <%}%>   
			
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

