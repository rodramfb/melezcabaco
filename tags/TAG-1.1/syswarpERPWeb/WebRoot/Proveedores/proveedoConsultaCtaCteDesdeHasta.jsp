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
BigDecimal totalDebe  = new BigDecimal(0);
BigDecimal totalHaber = new BigDecimal(0);
String[] tituCol = new String[11];

%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>
<jsp:useBean id="BPF" class="ar.com.syswarp.web.ejb.BeanProveedoConsultaCtaCteDesdeHasta" scope="page" />
<head>
	<title>Consuta de Cuentas Corrientes de Proveedores</title>
	<link rel = "stylesheet" href = "<%= pathskin %>">
	<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
	<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
	<script language="JavaScript" src="vs/forms/forms.js"></script>
	<script language="JavaScript" src="vs/overlib/overlib.js"></script>
	<script>
	function grisarFechas(){
		if (document.frm.tipo == 'H') {
	// falta terminar
		}
		return true;
	}
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
		
			//Fecha
			//Comprobante
		    //T.Mov
		    //Debe
     		//Haber
    		//Saldo del Movimiento
			//Saldo Acumulado
			//F.Vto
     		//N.Interno
     	 tituCol[0] = "Id proveedor";
     	 tituCol[1] = "Razon Social";	
     	 tituCol[2] = "Fecha";
     	 tituCol[3] = "Sucursal";
     	 tituCol[4] = "Factura";
     	 tituCol[5] = "Tipo comprob.";
     	 tituCol[6] = "Debe";
     	 tituCol[7] = "Haber";
     	 tituCol[8] = "Saldo del movimiento";
     	 tituCol[9] = "F. Vto";
     	 tituCol[10] = "Nro. Interno";
     	 
     	 String titulo = "Consulta de Cuentas Corrientes de Proveedores.";
		 BPF.setResponse(response);
		 BPF.setRequest(request);
		 BPF.setTituCol(tituCol);
		 // ver esto BPF.setSession(session);
		 BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
		 BPF.setUsuarioact( session.getAttribute("usuario").toString() );
		 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
		 BPF.setTotCol(11);
		 BPF.ejecutarValidacion();
		 java.util.List Consulta1 = new java.util.ArrayList();
		 java.util.List Consulta2 = new java.util.ArrayList();
		 java.util.List Consulta3 = new java.util.ArrayList();
		 Consulta1 = BPF.getSaldoAnteriorList();
		 Consulta2 = BPF.getMovimientosList();
		 Consulta3 = BPF.getSaldoFinalList();
		 
		 iterConsulta1 = Consulta1.iterator();
		 iterConsulta2 = Consulta2.iterator();
		 iterConsulta3 = Consulta3.iterator();
 		%>
		<form action="proveedoConsultaCtaCteDesdeHasta.jsp" method="post" name="frm">
		<input name="accion" type="hidden" value="consulta">
			<table width="100%" border="0"  cellpadding="0" cellspacing="0" class="text-globales">
				<tr>
					<td width="367" colspan="4"><%= titulo %>&nbsp;</td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr class="fila-det-bold-rojo">
					<td class="fila-det-border">&nbsp;</td>
					<td colspan="3" class="fila-det-border">
						<jsp:getProperty name="BPF" property="mensaje" />&nbsp;					
					</td>
				</tr>
				<tr class="fila-det">
					<td width="20%" class="fila-det-border">Proveedor Desde: (*)</td>
				  	<td class="fila-det-border">
						<input name="dproveedordesde" type="text" class="campo" id="dproveedordesde" value="<%=BPF.getDproveedordesde() %>" size="30" readonly="true">	
						<input name="idproveedordesde" type="hidden" id="idproveedordesde" value="<%=BPF.getIdproveedordesde() %>">
						<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" 	onClick="mostrarLOV('../Proveedores/lov_proveedores_desde.jsp')" style="cursor:pointer" >
				  	</td>
				  	<td width="20%" class="fila-det-border"></td>
				</tr>
				<tr class="fila-det">
					<td width="13%" class="fila-det-border">
						Fecha Desde (*)					
					</td>
					<td width="39%" class="fila-det-border">
						<table width="35%" border="0">
							<tr class="fila-det-border">
								<td width="50%">
									<input name="fechadesde" type="text" class="cal-TextBox" id="fechadesde" onFocus="this.blur()" 													value="<%=BPF.getFechadesde() %>" size="12" maxlength="12" readonly="true">
								</td>
								<td width="50%">
									<a class="so-BtnLink" href="javascript:calClick();return false;" onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); " onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);" onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_6');return false;">
										<img src="vs/calendar/btn_date_up.gif" title="Ver Calendario..." name="BTN_date_6" width="22" height="17" border="0" align="absmiddle">											  								
									</a>								
								</td>
							</tr>
						</table>					
					</td>
					<td class="fila-det-border"> Fecha Hasta (*) </td>
					<td class="fila-det-border">
						<table width="35%" border="0">
							<tr class="fila-det-border">
								<td width="50%">
									<input name="fechahasta" type="text" class="cal-TextBox" id="fechahasta" onFocus="this.blur()" value="<%=BPF.getFechahasta() %>" size="12" maxlength="12" readonly="true">								
								</td>
								<td width="50%">
									<a class="so-BtnLink" href="javascript:calClick();return false;" onmouseover="calSwapImg('BTN_date_2', 'img_Date_OVER',true); " onmouseout="calSwapImg('BTN_date_2', 'img_Date_UP',true);" onclick="calSwapImg('BTN_date_2', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_2');return false;">
										<img src="vs/calendar/btn_date_up.gif" title="Ver Calendario..." name="BTN_date_2" width="22" height="17" border="0" align="absmiddle"> 
									</a>								
								</td>
							</tr>
						</table>					
					</td>	
				</tr>
				<tr class="fila-det">
					<td class="fila-det-border">Tipo Consulta : (*) </td>
					<td class="fila-det-border" colspan="3">
						<select name="tipo" class="cal-TextBox" onChange="grisarFechas();">
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
							<option value="H" selected="selected" >Historico</option>
						<%} else { %>
							<option value="H">Historico</option>
						<% }  %>
						</select>					
					</td>
				</tr>
				<tr>				  
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr class="fila-det">
							<td width="13%">
								<input name="validar" type="submit" value="Consultar" class="boton">							
							</td>
						</tr>
					</table>
				</tr>
			</table>
      	<input name="primeraCarga" type="hidden" value="false" >
		</form>

<%if (BPF.getAccion().equalsIgnoreCase("consulta")){
int r = 0;
boolean primera = true;
String idproveedor ="";
BigDecimal totalProveedor = new BigDecimal(0);
BigDecimal totalGeneral   = new BigDecimal(0);
BigDecimal totalizado = new BigDecimal(0);
BigDecimal saldoAcumulado = new BigDecimal(0);
   while(iterConsulta2.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta2.next(); 
 
             
if (!idproveedor.equalsIgnoreCase(sCampos[0])){  
%>		
 <!-- detalle -->
	<%if ( !primera )
	{%>
<tr class="fila-encabezado">  
	<td width="5%">&nbsp;</td>  
    <td width="10%"></td>
    <td width="5%"><div align="right"></div></td>
    <td width="10%"></td>
    <td width="10%"></td>
    <td width="15%"><div align="right"></div></td>
    <td width="15%"><div align="right"></div></td>
    <td width="15%"><div align="right"><%=totalProveedor%></div></td>
    <td width="10%"></td>
    <td width="15%"><div align="right"></div></td>     
</tr>  
	<table>
    	<tr>
			<td></td>     
    	</tr>
   	</table>
<%
	} %>
   <%
	primera = false; 
	totalProveedor = new BigDecimal(0);        
    %>
	<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
  		<tr class="fila-encabezado">     
     		<td width="5%"><%=sCampos[0]%>-<%=sCampos[1]%></td>     
   		</tr>	
 	</table>
    <table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
  		<tr class="fila-encabezado">  
     		<td width="6%"><div align="center">Fecha</div></td>
			<td width="17%"><div align="center">Comprobante</div></td>
		    <td width="5%"><div align="center">T.Mov</div></td>
		    <td width="11%"><div align="right">Debe</div></td>
     		<td width="13%"><div align="right">Haber</div></td>
    		<td width="13%"><div align="right"><%= Common.setNotNull(BPF.getTipo()).equalsIgnoreCase("P") ? "Saldo del Movimiento" : "Saldo del Movimiento" %></div></td>
			<td width="13%"><div align="right">Saldo Acumulado</div></td>
			<td width="15%"><div align="center">F.Vto</div></td>
     		<td width="7%"><div align="right">N.Interno</div></td> 
	 		<td>Asientos contables</td>     
  		</tr>
<%
}
      // estos campos hay que setearlos segun la grilla
      idproveedor = sCampos[0];      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
		<tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
			<td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[2]), "JSDateToStr").toString()%></div></td> 
      		<td class="fila-det-border" ><div align="center"><%=Common.strZero(sCampos[3], 4)%>-<%=Common.strZero(sCampos[4], 8)%></div></td>
        	<td class="fila-det-border" ><div align="center">&nbsp;<%=sCampos[5]%></div></td> 
       	<%
        totalDebe  = totalDebe.add(new BigDecimal(sCampos[6].trim()));
        totalHaber = totalHaber.add(new BigDecimal(sCampos[7].trim()));
        BigDecimal saldoParcial = new BigDecimal(sCampos[8]);         
        totalProveedor = totalProveedor.add(saldoParcial);
        totalGeneral   = new BigDecimal(0);
       	%>
		<%// if(sCampos[6].trim().equalsIgnoreCase("0.00")) sCampos[6]= ""; %>
      	<%// if(sCampos[7].trim().equalsIgnoreCase("0.00")) sCampos[7]= ""; %>
	      	<td class="fila-det-border" ><div align="right">&nbsp;<%=!(sCampos[6].trim().equalsIgnoreCase("0.00")) ? sCampos[6] : "" %></div></td>
	      	<td class="fila-det-border" ><div align="right">&nbsp;<%=!(sCampos[7].trim().equalsIgnoreCase("0.00")) ? sCampos[7] : "" %></div></td> 
	      	<td class="fila-det-border" ><div align="right"><%=sCampos[8]%></div></td>  
	      	<td class="fila-det-border" ><div align="right"><%= saldoParcial %>&nbsp;</div></td>
		    <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[9]), "JSDateToStr").toString()%></div></td> 
	      	<td class="fila-det-border" ><div align="right">&nbsp;<%=sCampos[10]%></div></td> 
	  		<td>
	  			<table width="100%" border="0" cellspacing="1" cellpadding="1" >
					<tr class="<%=color_fondo%>" valign="bottom">
						<td class="fila-det-border">
							<a onClick="mostrarLOV('../Proveedores/lov_ctacte_contprov.jsp?nrocomprobante=<%=sCampos[10]%>')"><img src="../imagenes/default/gnome_tango/apps/kcalc.png" title="Valores"></a>
						</td>
						<td class="fila-det-border">
							<a onClick="mostrarLOV('../Proveedores/lov_ctacte_canprov.jsp?nrocomprobante=<%=sCampos[10]%>')"><img src="../imagenes/default/gnome_tango/actions/editpaste.png" title="Aplicaciones"></a>
						</td>
					</tr>
				</table>
	  		</td>
  		</tr>
   <%
   }
   if(r!=0){
   %> 
		<tr class="fila-encabezado">  
     		<td width="6%"></td>
     		<td width="17%"></td>
     		<td width="5%"></td>
     		<td width="11%"><div align="right"></div></td>
     		<td width="13%"><div align="right"></div></td>
     		<td width="13%"><div align="right"></div></td>
	   		<td width="13%"><div align="right"><%=totalProveedor%></div></td>
	   		<td width="15%"><div align="right"></div></td>
     		<td width="7%"></td>
	 		<td width="7%"></td>
   		</tr>
<%
	
}%>  
 	</table>
  	<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
  		<tr class="fila-encabezado">     
    		<%if (!BPF.getTipo().equalsIgnoreCase("-1") && BPF.isCorrecto()){%>
			<%if(BPF.getTipo().equalsIgnoreCase("P") && BPF.isFlag()) {%>     
       		<td>El archivo se generó exitosamente. Haga click para descargar.
       			<img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=proveed_rango_cuenta_corriente_pendiente&idproveedor=<%= BPF.getIdproveedordesde() %>&idproveedorhasta=<%=BPF.getIdproveedorhasta() %>&fechadesde=<%=BPF.getFechadesde()%>&fechahasta=<%=BPF.getFechahasta()%>', 'pdf', 800, 600);"> Exportar a excel <a href="./manejarArchivosDatosToXls.jsp?file=../reportes/reportes/ConsultaCtaCtePendiente.csv" >
       			<input type="image" src="../imagenes/default/gnome_tango/mimetypes/gnome-mime-application-vnd.ms-excel.png"/></a><%
				session.setAttribute("archivo","ConsultaCtaCtePendiente.csv");%>
			</td>
    <%}%>
    <%if(BPF.getTipo().equalsIgnoreCase("H") && BPF.isFlag()) {%>     
       		<td>El archivo se generó exitosamente. Haga click para descargar.<img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="abrirVentana('../reportes/reportes/generaPDF.jsp?plantillaImpresionJRXML=proveed_cuenta_corriente_historica&idproveedor=<%=BPF.getIdproveedordesde()%>&fechadesde=<%=BPF.getFechadesde()%>&fechahasta=<%=BPF.getFechahasta()%>', 'pdf', 800, 600);">
	   		Exportar a excel <a href="./manejarArchivosDatosToXls.jsp?file=../reportes/reportes/ConsultaCtaCteHistorico.csv" >
       			<input type="image" src="../imagenes/default/gnome_tango/mimetypes/gnome-mime-application-vnd.ms-excel.png"/></a><%
				session.setAttribute("archivo","ConsultaCtaCteHistorico.csv");%>
	   		</td>
    <%}%>
	<%}%> 
		</tr>
 	</table>
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

