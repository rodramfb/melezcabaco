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
		class="ar.com.syswarp.web.ejb.BeanConsultaReactivacionesEntreFechas" scope="page" />
	<head>
		<title>Consulta de Reactivaciones entre Fechas</title>
		 <link rel = "stylesheet" href = "<%= pathskin %>">
		<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
		<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
		<script language="JavaScript" src="vs/forms/forms.js"></script>
		<script language="JavaScript" src="vs/overlib/overlib.js"></script>
		
<script>

  function checkUncheck(onOff){
     var i = 0;
     for(i=0;i<document.frm.elements.length;i++){
        if(document.frm.elements[i].type == 'checkbox' && !document.frm.elements[i].disabled){
          document.frm.elements[i].checked = onOff;
        }
      }    
  }


	function callGenerarFichasSeleccion() {
     var i = 0;
     var ok = false;
     var clientesToCollection = "";
     for(i=0;i<document.frm.elements.length;i++){
        if(document.frm.elements[i].type == 'checkbox' && !document.frm.elements[i].disabled){
          if(document.frm.elements[i].checked){
            ok = true; 
            clientesToCollection+=document.frm.elements[i].value + "-";
          } 
        }
      }
   
     if(ok)       
       abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=clientes_precarga_ficha_asocia&tipo=REACTIVACION&clientesToCollection=' + clientesToCollection, 'ficha', 750, 500);
     else
       alert('Debe seleccionar al menos un Prospecto.');
  }	
</script>
		 
		<meta http-equiv="Content-Type"	content="text/html; charset=iso-8859-1">
	</head>
	<BODY>
		<div id="popupcalendar" class="text"></div>
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
		<jsp:setProperty name="BPF" property="*" />
		<% 
 String titulo = "Consulta de Reactivaciones entre Fechas";
	
		
		
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
		<form action="ConsultadeReactivacionesEntreFechas.jsp" method="post" name="frm">
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
																					
											<td width="50">&nbsp;											</td>
											<td width="43">
												
										  </td>
											<td width="42">
										
										  </td>
										</tr>
								  </table>
									
								</td>
							</tr>
					  </table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det-bold-rojo">
								<td class="fila-det-border">&nbsp;								</td>
								<td colspan="3" class="fila-det-border">
									<jsp:getProperty name="BPF" property="mensaje" />
&nbsp;																								</td>
							</tr>
<tr class="fila-det">
								<td width="11%" class="fila-det-border">
									Fecha Desde </td>
								<td width="26%" class="fila-det-border"><table width="88%" border="0">
                                  <tr class="fila-det-border">
                                    <td width="29%"><input name="fechadesde" type="text" class="cal-TextBox"
													id="fechadesde" onFocus="this.blur()"
													value="<%=BPF.getFechadesde() %>" size="12"
													maxlength="12" readonly>
                                    </td>
                                    <td width="71%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle"> </a> </td>
                                  </tr>
          </table></td>
							  
							  
								<td width="11%" class="fila-det-border">Fecha Hasta </td>
								<td width="52%" class="fila-det-border"><table width="19%" border="0">
                                  <tr class="fila-det-border">
                                    <td width="12%"><input name="fechahasta" type="text" class="cal-TextBox"
													id="fechahasta" onFocus="this.blur()"
													value="<%=BPF.getFechahasta() %>" size="12"
													maxlength="12" readonly>
                                    </td>
                                    <td width="88%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_7');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_7" width="22" height="17" border="0"
														align="absmiddle" id="BTN_date_7"> </a> </td>
                                  </tr>
          </table></td>
						  </tr><tr class="fila-det">
						    <td width="11%" height="46" class="fila-det-border">&nbsp;</td>
						    <td width="26%" class="fila-det-border"><input name="validar"
											type="submit" value="Consultar" class="boton"></td>
						    <td width="11%" class="fila-det-border">&nbsp;</td>
						    <td width="52%" class="fila-det-border">&nbsp;						</td>
		  </table>
			<input name="primeraCarga" type="hidden" value="false" >
		</form>

<%if (BPF.getAccion().equalsIgnoreCase("consulta")){%>		
<table width="200%" border="0" cellspacing="2" cellpadding="1" name="rsTable"   >
  <tr class="text-globales">
    <td colspan="16" ><table width="56%" border="0" cellspacing="2" cellpadding="0">
      <tr class="text-globales">
        <td width="23%"><a href="#">
          <div onClick="checkUncheck(true)" >Todos</div>
        </a></td>
        <td width="33%"><a href="#">
          <div onClick="checkUncheck(false)" > Ninguno</div>
        </a></td>
        <td width="44%"><span><a href="#">
          <div onClick="callGenerarFichasSeleccion( )" > Imprimir Selecci&oacute;n</div>
        </a></span></td>
      </tr>
    </table></td>
  </tr>
  <tr class="fila-encabezado">
    <td width="0" rowspan="2" >&nbsp;</td>
    <td width="0" rowspan="2" >&nbsp;</td>
    <td width="0" rowspan="2"><div align="right">Cliente</div></td>  
     <td width="0" rowspan="2">Razon</td>
	 <td width="0" rowspan="2">Vendedor</td>
     <td width="0" rowspan="2">Promoci&oacute;n</td>
     <td width="0" rowspan="2"><div align="center">Sucursal FC</div></td>
     <td width="0" rowspan="2">Preferencia</td>
     <td width="0" rowspan="2">Cuenta</td>
     <td width="0" rowspan="2">Condici&oacute;n</td>
     <td width="0" rowspan="2">Lista de Precios </td>
     <td width="0" rowspan="2">Tipo IVA </td>
     <td width="0" rowspan="2">Periodicidad</td>
     <td colspan="3"> <div align="center">Reactivaci&oacute;n </div>
     <div align="center"></div></td>
  </tr>
  <tr class="fila-encabezado">
    <td width="0"><div align="right">%</div></td>
    <td width="0"><div align="center">Fecha  </div></td>
    <td width="0"><div align="center">F. Baja</div></td>
  </tr> 
   <%int r = 0;
   while(iterConsulta1.hasNext()){ 
      ++r;
      String[] sCampos = (String[]) iterConsulta1.next(); 
       String imagen ="";      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td width="0"  class="fila-det-border" ><input name="clientesToCollection" type="checkbox" id="clientesToCollection" value="<%=Common.setNotNull(sCampos[0])%>" <%=Common.setNotNull(sCampos[0]).equals("") ? "disabled" : ""%>></td>
     <td width="0"  class="fila-det-border" ><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" style="cursor:pointer" <%=Common.setNotNull(sCampos[0]).equals("") ? "onClick=\"alert('Prospecto no posee N&uacute;mero de Cliente asignado.')\"" : "onClick=\"abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=clientes_precarga_ficha_asocia&clientesToCollection=" + sCampos[0]  +"&tipo=REACTIVACION', 'ficha', 750, 500)\" "%> ></td>
     <td width="0" height="22" class="fila-det-border" ><div align="right"><%=sCampos[0]%></div></td>
      <td width="0" class="fila-det-border" ><%=sCampos[1]%> </td>
     <td width="0" class="fila-det-border" ><%=sCampos[3]%></td>
     <td width="0" class="fila-det-border" ><%=Common.setNotNull(sCampos[7])%>&nbsp;</td>
     <td width="0" class="fila-det-border" ><div align="center"><%=Common.strZero(Common.setNotNull(sCampos[8]), 4 )%>&nbsp;</div></td>
     <td width="0" class="fila-det-border" ><%=Common.setNotNull(sCampos[10])%></td>
     <td width="0" class="fila-det-border" ><%= !Common.setNotNull( sCampos[13]).equals("") ?  (   Common.setNotNull(sCampos[13]) + "-" + Common.setNotNull(sCampos[14])  ) : "<img src=\"../imagenes/default/gnome_tango/status/dialog-error.png\"   title=\"Cuenta no corresponde al ejercicio activo o no asignada\" height=\"15\" width=\"15\">" %></td>
     <td width="0" class="fila-det-border" ><%=Common.setNotNull(sCampos[17])%></td>
     <td width="0" class="fila-det-border" ><%=Common.setNotNull(sCampos[12])%></td>
     <td width="0" class="fila-det-border" ><%=Common.setNotNull(sCampos[19])%></td>
     <td width="0" class="fila-det-border" ><%=Common.setNotNull(sCampos[20])%></td>
     <td width="0" class="fila-det-border" ><div align="right"><%=sCampos[2]%></div></td>
     <td width="0" class="fila-det-border" ><div align="center"><%= Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[5]), "JSTsToStr") %></div></td>
     <td width="0" class="fila-det-border" ><div align="center"><%=!Common.setNotNull(sCampos[4]).equals("") ? Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[4]), "JSTsToStr") : "<img src=\"../imagenes/default/gnome_tango/colors.red.icon.gif\" width=\"30\" height=\"3\">"%></div></td> 
   </tr>
   <%
   }%>
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

