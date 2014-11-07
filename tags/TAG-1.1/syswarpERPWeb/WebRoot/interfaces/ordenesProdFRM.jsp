<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: produccionMovProdu
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Feb 21 13:30:17 GMT-03:00 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BPMPF"  class="ar.com.syswarp.web.ejb.BeanINTERFACESProduccionMovProduFrm"   scope="page"/>
<head>
 <title>FRMProduccionMovProdu.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel = "stylesheet" href = "<%= pathskin %>">
 
 <link rel="stylesheet" type="text/css" href="vs/calendar/calendar.css">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<script language="JavaScript" src="vs/forms/forms.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPMPF" property="*" />
 <% 
 String titulo ="INTERFACES - " + BPMPF.getAccion().toUpperCase() + " DE ORDENES DE PRODUCCION" ;
 BPMPF.setResponse(response);
 BPMPF.setRequest(request);
 BPMPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPMPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPMPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPMPF.ejecutarValidacion();
 %>
<form action="ordenesProdFRM.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPMPF.getAccion()%>" >
<input name="idop" type="hidden" value="<%=BPMPF.getIdop()%>" >
   <table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %></td> 
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center"> 
						  <%if(BPMPF.getIdop().compareTo(new BigDecimal(0)) > 0){  %>
              <tr class="text-catorce">
                <td >ORDEN DE PRODUCCION NRO:<%=  BPMPF.getIdop() %>&nbsp;</td>
                <td >
								   <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="text-catorce"> 
                      <td width="39%"><div align="right">Incluye costos&nbsp; </div> </td>
                      <td width="13%"> <img src="../imagenes/default/gnome_tango/actions/document-print-preview.png" width="22" height="22" onClick="abrirVentana('../Produccion/produccionCalculoNecesidadFrame.jsp?idop=<%= BPMPF.getIdop()%>');" style="cursor:pointer" title="Impresi&oacute;n de orden con costos inclu&iacute;dos."></td>
                      <td width="39%"><div align="right">Sin Costos&nbsp; </div> </td>
                      <td width="9%"> <img src="../imagenes/default/gnome_tango/devices/yast_printer.png" width="22" height="22" onClick="abrirVentana('../Produccion/produccionCalculoNecesidadSinCostoFrame.jsp?idop=<%= BPMPF.getIdop()%>');" style="cursor:pointer" title="Impresi&oacute;n de orden sin costos."></td>
                    </tr>
                  </table>
								</td> 
                <td >&nbsp;</td>
              </tr>
							<%}  %> 
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border"><jsp:getProperty name="BPMPF" property="mensaje"/>&nbsp;</td>
              </tr>    
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">Esquema: (*) </td> 
                <td width="53%" class="fila-det-border"> <input name="esquema" type="text" class="campo" id="esquema" value="<%=BPMPF.getEsquema()%>" size="80" maxlength="100" readonly >                </td>
                <td width="22%" class="fila-det-border">
								  <% if( Double.parseDouble(BPMPF.getCantre_op())== 0 ){ %>
									  <img src="../imagenes/default/gnome_tango/actions/find.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('../Produccion/lov_esquemaProducto.jsp', 'EP', 700, 400)">
                  <% } %>
									<input name="idesquema" type="hidden" value="<%=BPMPF.getIdesquema()%>" class="campo" >
							  </td> 
              </tr>
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">Articulo: (*) </td>
                <td colspan="2" class="fila-det-border"><input name="descrip_st" type="text" value="<%=BPMPF.getDescrip_st()%>" class="campo" size="80" maxlength="150"  readonly>
                <input name="codigo_st" type="hidden" value="<%=BPMPF.getCodigo_st()%>" class="campo"   ></td>
              </tr>
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">Cantidad Estimada : (*) </td>
                <td colspan="2" class="fila-det-border"><input name="cantest_op" type="text" value="<%=BPMPF.getCantest_op()%>" class="campo" size="12" maxlength="8"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">Fecha Prometida : (*) </td>
                <td colspan="3" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecha_prometida" value="<%=BPMPF.getFecha_prometida()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fecha_prometida','BTN_date_6');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_6" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>
                </td>
              </tr>
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">Fecha Emisi&oacute;n : (*) </td>
                <td colspan="3" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecha_emision" value="<%=BPMPF.getFecha_emision()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fecha_emision','BTN_date_7');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>
                </td>
              </tr>
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">&nbsp;Observaciones:  </td>
                <td colspan="2" class="fila-det-border"><textarea name="observaciones" cols="70" rows="6" class="campo"><%=BPMPF.getObservaciones()%></textarea>
                <input name="idcontador" type="hidden" value="<%=BPMPF.getIdcontador()%>" class="campo" size="100" maxlength="100"  >
                <input name="nrointerno" type="hidden" value="<%=BPMPF.getNrointerno()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td height="32" class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
             </tr>
              <tr class="fila-det">
                <td height="21" class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border">&nbsp;</td>
              </tr>

							<% 
							if(!BPMPF.getListaDetalleOrden().isEmpty()){
							 %>
              <tr class="text-globales">
                <td height="21" colspan="3" class="fila-det-border">DETALLE DE ORDEN DE PRODUCCION </td>
              </tr>							 
              <tr class="fila-det">
                <td colspan="3" class="fila-det-border">
								
								<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                  
									<tr class="fila-det-bold">
                    <td width="6%" class="fila-det-border">Art.</td>
										<td width="41%" class="fila-det-border">Descripci&oacute;n</td>
                    <td width="22%" class="fila-det-border">Deposito</td>
                    <td width="12%" class="fila-det-border"><div align="center">Prod. - Cons.</div></td>
                    <td width="7%" class="fila-det-border"><div align="right">Can. Est.</div></td>
                    <td width="8%" class="fila-det-border"><div align="right">Cant. Prod.</div></td>
                    <td width="4%" class="fila-det-border"><div align="center">T.</div></td>
                  </tr>
									<%
									Iterator iterDetalleOrden =  BPMPF.getListaDetalleOrden().iterator();
									while(iterDetalleOrden.hasNext()){
									  String [] datosDetalleOrden = ( String[] ) iterDetalleOrden.next();
										/*
		           mpd.idop,mpd.renglon,mpd.tipo,mpd.codigo, ae.descripcion,mpd.cantidad_cal,mpd.cantidad_rea,mpd.cantidad_stb,
				       mpd.margen,mpd.entsal,mpd.codigo_dt,sd.descrip_dt,mpd.edita,mpd.fechaaltaorden,mpd.stockbis,mpd.abrcer,mpd.improd,
				       mpd.usuarioalt,mpd.usuarioact,mpd.fechaalt,mpd.fechaact 										
										*/
									%>
                  <tr class="fila-det">
                    <td class="fila-det-border"><%= datosDetalleOrden[3] %></td>
										<td class="fila-det-border"><%= datosDetalleOrden[4] %></td>
                    <td class="fila-det-border"><%= datosDetalleOrden[11] %></td>
                    <td class="fila-det-border"><div align="center"><%= datosDetalleOrden[9] %></div></td>
                    <td class="fila-det-border"><div align="right"><%= datosDetalleOrden[5] %></div></td>
                    <td class="fila-det-border"><div align="right"><%= datosDetalleOrden[6] %></div></td>
                    <td class="fila-det-border"><div align="center"><%= datosDetalleOrden[2] %></div></td>
                  </tr>
									<% 
									} %>
                </table>
								</td>
              </tr>
							<% 
							}
							 %>							
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
}%>

