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
<jsp:useBean id="BPMPF"  class="ar.com.syswarp.web.ejb.BeanProduccionMovProduParciales"   scope="page"/>
<head>
 <title></title>
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
 String titulo =  "SEGUIMIENTO DE PARCIALES DE ORDENES DE PRODUCCION" ;
 BPMPF.setResponse(response);
 BPMPF.setRequest(request);
 BPMPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPMPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPMPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPMPF.ejecutarValidacion();
 %>
<form action="produccionMovProduParciales.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="consulta" >
<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center"> 
						  
              <tr class="text-catorce">
                <td width="27%" >ORDEN DE PRODUCCION NRO:&nbsp;</td>
                <td ><input name="idop" type="text" value="<%=BPMPF.getIdop()%>" class="campo"></td>
                <td ><span class="fila-det-border">
                  <input name="validar" type="submit" value="Buscar" class="boton">
                </span></td>
              </tr>
							
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border"><jsp:getProperty name="BPMPF" property="mensaje"/>&nbsp;</td>
              </tr>    
							<% 
							if(!BPMPF.getListaDetalleOrden().isEmpty()){
							 %>
              <tr class="fila-det">
                <td class="fila-det-border">Esquema: </td>
                <td width="51%" class="fila-det-border"> <%=BPMPF.getEsquema()%>&nbsp;</td>
                <td width="22%" class="fila-det-border">&nbsp;

									<input name="idesquema" type="hidden" value="<%=BPMPF.getIdesquema()%>" class="campo" >							  </td> 
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Articulo: </td>
                <td colspan="2" class="fila-det-border"><%=BPMPF.getDescrip_st()%>&nbsp;
                <input name="codigo_st" type="hidden" value="<%=BPMPF.getCodigo_st()%>" class="campo"   ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Cantidad Estimada :</td>
                <td colspan="2" class="fila-det-border"><%=BPMPF.getCantest_op()%>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Fecha Prometida : </td>
                <td colspan="3" class="fila-det-border" ><%=BPMPF.getFecha_prometida()%>&nbsp;                </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Fecha Emisi&oacute;n : </td>
                <td colspan="3" class="fila-det-border" ><%=BPMPF.getFecha_emision()%>&nbsp;                </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;Observaciones:  </td>
                <td colspan="2" class="fila-det-border"><%=BPMPF.getObservaciones()%>&nbsp;
                <input name="idcontador" type="hidden" value="<%=BPMPF.getIdcontador()%>" class="campo" size="100" maxlength="100"  >
                <input name="nrointerno" type="hidden" value="<%=BPMPF.getNrointerno()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border">&nbsp;</td>
              </tr>

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
							
							
							<tr class="text-globales">
                <td height="21" colspan="3" class="fila-det-border">&nbsp;</td>
              </tr>
							<tr class="text-globales">
                <td height="21" colspan="3" class="fila-det-border">PARCIALES REALIZADOS </td>
              </tr>
							<tr class="text-globales">
                <td height="21" colspan="3" class="fila-det-border">
								
								<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                  
									<tr class="fila-det-bold">
                    <td width="29%" class="fila-det-border"><div align="center">Fecha Movimiento </div></td>
										<td width="32%" class="fila-det-border"><div align="right">Cantidad</div></td>
                    <td width="39%" class="fila-det-border"><div align="right">Usuario</div></td>
                  </tr>
									<% 
									Iterator iterParcialesOrden =  BPMPF.getListaParcialesOrden().iterator();
									while(iterParcialesOrden.hasNext()){
									  String [] datosParcialesOrden = ( String[] ) iterParcialesOrden.next();									
									 %>									
									<tr class="fila-det">
									  <td class="fila-det-border"><div align="center"><%= Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(datosParcialesOrden[11]), "JSTsToStrWithHM") %>&nbsp;</div></td>
									  <td class="fila-det-border"><div align="right"><%= datosParcialesOrden[13] %>&nbsp;</div></td>
									  <td class="fila-det-border"><div align="right"><%= datosParcialesOrden[14] %>&nbsp;</div></td>
								  </tr>
									
									<% 
									}
									 %>
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

