<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesTipoComp
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jun 14 10:37:10 ART 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ page import="java.math.*" %>
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
<jsp:useBean id="BCTCF"  class="ar.com.syswarp.web.ejb.BeanClientesTipoCompFrm"   scope="page"/>
<head>
 <title>FRMClientesTipoComp.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" type="text/css" href="../imagenes/default/erp-style.css">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<script language="JavaScript" src="scripts/forms.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCTCF" property="*" />
 <% 
 String titulo = BCTCF.getAccion().toUpperCase() + " DE TIPO DE COMPROBANTES " ;
 BCTCF.setResponse(response);
 BCTCF.setRequest(request);
 BCTCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCTCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCTCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ) );
 BCTCF.ejecutarValidacion();
 %>
<form action="clientesTipoCompFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCTCF.getAccion()%>" >
<input name="idtipocomp" type="hidden" value="<%=BCTCF.getIdtipocomp()%>" >
   <table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
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
                <td colspan="3" class="fila-det-border"><jsp:getProperty name="BCTCF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Tipo Movimiento:  </td>
                <td colspan="3" class="fila-det-border"><select name="tipomov_tc" class="campo">
				<option value="1" <%=BCTCF.getTipomov_tc().toString().equals("1") ? "selected" : "" %>>Factura</option>
				<option value="2" <%=BCTCF.getTipomov_tc().toString().equals("2") ? "selected" : "" %>>Nota de Débito</option>
				<option value="3" <%=BCTCF.getTipomov_tc().toString().equals("3") ? "selected" : "" %>>Nota de Crédito</option>
				<option value="4" <%=BCTCF.getTipomov_tc().toString().equals("4") ? "selected" : "" %>>Recibos</option>
				</select></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Desc. &nbsp;Tipo Movimiento: </td>
                <td colspan="3" class="fila-det-border"><input name="descri_tc" type="text" value="<%=BCTCF.getDescri_tc()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Permite cuotas: </td>
                <td width="29%" class="fila-det-border"> <table width="55%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="cuotas_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getCuotas_tc()).equalsIgnoreCase("S") ? "checked" : "" %>></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="cuotas_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getCuotas_tc()).equalsIgnoreCase("N") ? "checked" : "" %>></td>
                  </tr>
                </table></td>
                <td width="25%" class="fila-det-border">Es venta contado: </td>
                <td width="24%" class="fila-det-border"><table width="66%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="vtacont_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getVtacont_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="vtacont_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getVtacont_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Mueve Stock:  </td>
                <td class="fila-det-border"><table width="56%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="st_stok_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getSt_stok_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="st_stok_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getSt_stok_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
                <td class="fila-det-border"> Modifica Precios de Venta: </td>
                <td class="fila-det-border"><table width="66%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="st_prec_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getSt_prec_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="st_prec_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getSt_prec_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Imprime Remito:  </td>
                <td class="fila-det-border"><table width="56%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="st_remi_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getSt_remi_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="st_remi_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getSt_remi_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
                <td class="fila-det-border">Afecta Comisiones: </td>
                <td class="fila-det-border"><table width="65%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="comi_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getComi_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="comi_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getComi_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Imprime Comprobante:  </td>
                <td class="fila-det-border">
                <table width="56%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="imprime_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getImprime_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="imprime_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getImprime_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
                <td class="fila-det-border"> Modifica Moneda: </td>
                <td class="fila-det-border"><table width="65%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="mod_mon_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getMod_mon_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="mod_mon_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getMod_mon_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Afecta Ingresos Brutos:  </td>
                <td class="fila-det-border">
                <table width="56%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="ingb_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getIngb_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="ingb_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getIngb_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
                <td class="fila-det-border">Va al Ranking De Clientes:</td>
                <td class="fila-det-border"><table width="65%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="ranking_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getRanking_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="ranking_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getRanking_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border"> Modificar Transportista:  </td>
                <td class="fila-det-border">
                <table width="56%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="transpo_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getTranspo_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="transpo_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getTranspo_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
                <td class="fila-det-border">Pide Bonificaci&oacute;n en Art.: </td>
                <td class="fila-det-border"><table width="65%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="bon_x_art" type="radio" value="S" <%=str.esNulo(BCTCF.getBon_x_art()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="bon_x_art" type="radio" value="N" <%=str.esNulo(BCTCF.getBon_x_art()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Imprime R. Despacho:  </td>
                <td class="fila-det-border">
                <table width="56%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="remdesp_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getRemdesp_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="remdesp_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getRemdesp_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
                <td class="fila-det-border">Modifica C. de Venta: </td>
                <td class="fila-det-border"><table width="64%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="mod_con_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getMod_con_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="mod_con_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getMod_con_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="text-catorce">&nbsp;</td>
                <td colspan="3" class="text-catorce">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Cant. Bonificaciones:  </td>
                <td colspan="3" class="fila-det-border"><input name="bonif_tc" type="text" value="<%=BCTCF.getBonif_tc()%>" class="campo" size="10" maxlength="2"  ></td>
              </tr>				  
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Contador  Asociado:  </td>
                <td colspan="3" class="fila-det-border"><input name="contad_tc" type="text" value="<%=BCTCF.getContad_tc()%>" class="campo" size="20" maxlength="20"  >
                <img src="../imagenes/default/audit.gif" width="21" height="17" onClick="abreVentana('../General/lov_globalContadores.jsp', 700, 450);" style="cursor:pointer"></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Cta. Contable IVA:  </td>
                <td colspan="3" class="fila-det-border"><input name="ctaiva_tc" type="text" value="<%=BCTCF.getCtaiva_tc()%>" class="campo" size="20" maxlength="20" id="ctaiva_tc" readonly >
                  <img src="../imagenes/default/audit.gif" width="21" height="17" onClick="abreVentana('lov_contableInfiPlan.jsp?campos=ctaiva_tc', 700, 450);" style="cursor:pointer"> (Inscriptos)</td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Cta. Contable IVA: </td>
                <td colspan="3" class="fila-det-border"><input name="ctivani_tc" type="text" value="<%=BCTCF.getCtivani_tc()%>" class="campo" size="20" maxlength="20"  readonly >
                  <img src="../imagenes/default/audit.gif" width="21" height="17" onClick="abreVentana('lov_contableInfiPlan.jsp?campos=ctivani_tc', 700, 450);" style="cursor:pointer"> (NO Inscriptos) </td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Cta. Contable Neto Gravado: </td>
                <td colspan="3" class="fila-det-border"><input name="ctgrava_tc" type="text" value="<%=BCTCF.getCtgrava_tc()%>" class="campo" size="20" maxlength="50"  readonly >
                <img src="../imagenes/default/audit.gif" width="21" height="17" onClick="abreVentana('lov_contableInfiPlan.jsp?campos=ctgrava_tc', 700, 450);" style="cursor:pointer"></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Cta. Contable Neto Exento: </td>
                <td colspan="3" class="fila-det-border"><input name="ctexent_tc" type="text" value="<%=BCTCF.getCtexent_tc()%>" class="campo" size="20" maxlength="50"  readonly >
                <img src="../imagenes/default/audit.gif" width="21" height="17" onClick="abreVentana('lov_contableInfiPlan.jsp?campos=ctexent_tc', 700, 450);" style="cursor:pointer"></td>
              </tr>
			  
              <tr class="fila-det">
                <td class="text-catorce">Recargos</td>
                <td colspan="3" class="text-catorce">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Recargo Uno:  </td>
                <td colspan="3" class="fila-det-border"><input name="dere1_tc" type="text" value="<%=BCTCF.getDere1_tc()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">&nbsp;Formula Recargo Uno :  </td>
                <td colspan="3" class="fila-det-border"><textarea name="reca1_tc" cols="70" rows="6" class="campo"><%=BCTCF.getReca1_tc()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Cta. Cont. Recargo Uno:  </td>
                <td colspan="3" class="fila-det-border"><input name="ctare1_tc" type="text" value="<%=BCTCF.getCtare1_tc()%>" class="campo" size="20" maxlength="20"  readonly >
                <img src="../imagenes/default/audit.gif" width="21" height="17" onClick="abreVentana('lov_contableInfiPlan.jsp?campos=ctare1_tc', 700, 450);" style="cursor:pointer"></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Afecta Impuestos Internos:  </td>
                <td colspan="3" class="fila-det-border"><table width="50%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="recai1_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getRecai1_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="recai1_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getRecai1_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Va el Neto gravado:  </td>
                <td colspan="3" class="fila-det-border"><table width="50%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="recgr1_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getRecai1_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="recgr1_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getRecai1_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Recargo Dos: </td>
                <td colspan="3" class="fila-det-border"><input name="dere2_tc" type="text" value="<%=BCTCF.getDere2_tc()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">&nbsp;Formula Recargo Dos: </td>
                <td colspan="3" class="fila-det-border"><textarea name="reca2_tc" cols="70" rows="6" class="campo"><%=BCTCF.getReca2_tc()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Cta. Cont. Recargo Dos: </td>
                <td colspan="3" class="fila-det-border"><input name="ctare2_tc" type="text" value="<%=BCTCF.getCtare2_tc()%>" class="campo" size="20" maxlength="20"  readonly >
                <img src="../imagenes/default/audit.gif" width="21" height="17" onClick="abreVentana('lov_contableInfiPlan.jsp?campos=ctare2_tc', 700, 450);" style="cursor:pointer"></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Afecta Imp. Internos : </td>
                <td colspan="3" class="fila-det-border"><table width="50%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="fila-det">
                      <td width="22%">Si</td>
                      <td width="34%"><input name="recai2_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getRecai2_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                      <td width="22%">No</td>
                      <td width="22%"><input name="recai2_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getRecai2_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Va al Neto gravado : </td>
                <td colspan="3" class="fila-det-border"><table width="50%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="recgr2_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getRecgr2_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="recgr2_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getRecgr2_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Recargo Tres: </td>
                <td colspan="3" class="fila-det-border"><input name="dere3_tc" type="text" value="<%=BCTCF.getDere3_tc()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">&nbsp;Formula Recargo Tres : </td>
                <td colspan="3" class="fila-det-border"><textarea name="reca3_tc" cols="70" rows="6" class="campo"><%=BCTCF.getReca3_tc()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Cta. Cont. Recargo Tres : </td>
                <td colspan="3" class="fila-det-border"><input name="ctare3_tc" type="text" value="<%=BCTCF.getCtare3_tc()%>" class="campo" size="20" maxlength="20"  readonly >
                <img src="../imagenes/default/audit.gif" width="21" height="17" onClick="abreVentana('lov_contableInfiPlan.jsp?campos=ctare3_tc', 700, 450);" style="cursor:pointer"></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Afecta Imp. Internos : </td>
                <td colspan="3" class="fila-det-border"><table width="50%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="fila-det">
                      <td width="22%">Si</td>
                      <td width="34%"><input name="recai3_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getRecai3_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                      <td width="22%">No</td>
                      <td width="22%"><input name="recai3_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getRecai3_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Va el Neto gravado : </td>
                <td colspan="3" class="fila-det-border"><table width="50%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="fila-det">
                      <td width="22%">Si</td>
                      <td width="34%"><input name="recgr3_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getRecgr3_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                      <td width="22%">No</td>
                      <td width="22%"><input name="recgr3_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getRecgr3_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Recargo Cuatro: </td>
                <td colspan="3" class="fila-det-border"><input name="dere4_tc" type="text" value="<%=BCTCF.getDere4_tc()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">&nbsp;Formula Recargo Cuatro : </td>
                <td colspan="3" class="fila-det-border"><textarea name="reca4_tc" cols="70" rows="6" class="campo"><%=BCTCF.getReca4_tc()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Cta. Cont. Recargo Cuatro : </td>
                <td colspan="3" class="fila-det-border"><input name="ctare4_tc" type="text" value="<%=BCTCF.getCtare4_tc()%>" class="campo" size="20" maxlength="20"  readonly >
                <img src="../imagenes/default/audit.gif" width="21" height="17" onClick="abreVentana('lov_contableInfiPlan.jsp?campos=ctare4_tc', 700, 450);" style="cursor:pointer"></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">&nbsp;Afecta Imp. Internos : </td>
                <td colspan="3" class="fila-det-border"><table width="50%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="recai4_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getRecai4_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="recai4_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getRecai4_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">&nbsp;Va el Neto gravado : </td>
                <td colspan="3" class="fila-det-border"><table width="50%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="recgr4_tc" type="radio" value="S" <%=str.esNulo(BCTCF.getRecgr4_tc()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="recgr4_tc" type="radio" value="N" <%=str.esNulo(BCTCF.getRecgr4_tc()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">&nbsp;C. Costo Uno :  </td>
                <td colspan="3" class="fila-det-border"><input name="centr1_tc" type="text" value="<%=BCTCF.getCentr1_tc()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">&nbsp;C. Costo Dos :  </td>
                <td colspan="3" class="fila-det-border"><input name="centr2_tc" type="text" value="<%=BCTCF.getCentr2_tc()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">&nbsp;Toma Iva Seg&uacute;n Art&iacute;culo : </td>
                <td colspan="3" class="fila-det-border">
                <table width="50%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td width="22%">Si</td>
                    <td width="34%"><input name="iva_x_art" type="radio" value="S" <%=str.esNulo(BCTCF.getIva_x_art()).equalsIgnoreCase("S") ? "checked" : "" %> class="campo"></td>
                    <td width="22%">No</td>
                    <td width="22%"><input name="iva_x_art" type="radio" value="N" <%=str.esNulo(BCTCF.getIva_x_art()).equalsIgnoreCase("N") ? "checked" : "" %> class="campo"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">&nbsp;Reporte Asociado (jasper) : (*) </td>
                <td colspan="3" class="fila-det-border"><input name="jasper_tc" type="text" class="campo" id="jasper_tc" value="<%=BCTCF.getJasper_tc()%>" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="3" class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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
}%>

