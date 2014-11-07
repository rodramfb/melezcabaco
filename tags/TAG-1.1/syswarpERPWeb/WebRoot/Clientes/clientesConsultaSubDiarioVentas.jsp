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
String titulo = "Consulta de Fecha de Ingreso de Prospecto";
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<title>Consulta de Movimientos por Articulo y Deposito</title>
		 <link rel = "stylesheet" href = "<%= pathskin %>">
		<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
		<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
		<script language="JavaScript" src="vs/forms/forms.js"></script>
		<script language="JavaScript" src="vs/overlib/overlib.js"></script>
		
		<script>
 
        function validarCampos(){
          var fechadesde =  trim(document.getElementById('fechadesde').value);  
          var fechahasta =  trim(document.getElementById('fechahasta').value);
          var sucursaldesde =  trim(document.getElementById('sucursaldesde').value);
          var sucursalhasta =  trim(document.getElementById('sucursalhasta').value);
          if(fechadesde == '') alert('Ingrese Fecha desde');
          else if(fechahasta == '') alert('Ingrese Fecha hasta');
          else if(sucursaldesde != '' && sucursalhasta == '') alert('Ingrese Sucursal hasta');
          else if(sucursalhasta != '' && sucursaldesde == '') alert('Ingrese Sucursal desde');
          else if( parseFloat( sucursalhasta )  < parseFloat( sucursaldesde ) ) alert('Sucursal Hasta debe ser mayor a Sucursal Desde');
          else{ 
            var pagina = '../reportes/jasper/generaPDF.jsp?fechadesde=' + fechadesde + '&fechahasta=' + fechahasta  ;
            pagina += '&sucursaldesde=' + sucursaldesde + '&sucursalhasta=' + sucursalhasta ;
            pagina += '&plantillaImpresionJRXML=contable_subdiarioiva_ventas_frame'; 
            abrirVentana(pagina, 'informesubdiario', 800, 600);
          }
        }   
         
		 window.onload = function() { 
		  document.getElementById('sucursaldesde').onkeypress = validaNumericos;
		  document.getElementById('sucursalhasta').onkeypress = validaNumericos;
          document.getElementById('generar').onclick = validarCampos;
		 }        
		</script>
		 
		<meta http-equiv="Content-Type"	content="text/html; charset=iso-8859-1">
	</head>
    <BODY>
		<div id="popupcalendar" class="text"></div>
		<form action="clientesConsultaSubDiarioVentas.jsp" method="post" name="frm">
			<input name="accion" type="hidden" value="consulta">
			

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
								<td colspan="3" class="fila-det-border">&nbsp;
									 
																								</td>
							</tr>
<tr class="fila-det">
								<td width="11%" class="fila-det-border">
									Fecha Desde </td>
								<td width="27%" class="fila-det-border"><input name="fechadesde" type="text" class="cal-TextBox"
													id="fechadesde" onFocus="this.blur()" size="12"
													maxlength="12" readonly="readonly" />
                                  <a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle" id="BTN_date_6" /> </a></td>
							  
							  
								<td width="15%" class="fila-det-border">Fecha Hasta </td>
								<td width="47%" class="fila-det-border"><input name="fechahasta" type="text" class="cal-TextBox"
													id="fechahasta" onFocus="this.blur()" size="12"
													maxlength="12" readonly="readonly" />
                                  <a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_7');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_7" width="22" height="17" border="0"
														align="absmiddle" id="BTN_date_7" /> </a></td>
						  </tr>
<tr class="fila-det">
  <td class="fila-det-border">Sucursal Desde </td>
  <td class="fila-det-border"><input name="sucursaldesde" type="text" class="cal-TextBox"
													id="sucursaldesde" 
													value="" size="12"
													maxlength="4" ></td>
  <td class="fila-det-border">Sucursal Hasta </td>
  <td class="fila-det-border"><input name="sucursalhasta" type="text" class="cal-TextBox"
													id="sucursalhasta"  size="12"
													maxlength="4" ></td>
<tr class="fila-det">
	      <td width="11%" height="46" class="fila-det-border">&nbsp;</td>
	      <td width="27%" class="fila-det-border"><input name="generar"
											type="button" class="boton" id="generar" value="Generar Informe --&gt;&gt;"></td>
	      <td width="15%" class="fila-det-border">&nbsp;</td>
	      <td width="47%" class="fila-det-border">&nbsp;						</td>
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
}%>

