<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesPromociones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jan 18 09:58:25 ART 2011 
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
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCPF"  class="ar.com.syswarp.web.ejb.BeanClientesPromocionesFrm"   scope="page"/>
<head>
 <title>FRMClientesPromociones</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="scripts/calendar/calendar.css">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCPF" property="*" />
 <% 
 String titulo = BCPF.getAccion().toUpperCase() + " DE PROMOCIONES PARA CLIENTES" ;
 BCPF.setResponse(response);
 BCPF.setRequest(request);
 BCPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BCPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCPF.ejecutarValidacion();
 %>
<form action="clientesPromocionesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCPF.getAccion()%>" >
<input name="idpromocion" type="hidden" value="<%=BCPF.getIdpromocion()%>" >
   <span class="fila-det-border">
   <input name="porc_liq" type="hidden" value="<%=BCPF.getPorc_liq()%>" class="campo" size="10" maxlength="10" style="text-align:right">
   </span>
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
                <td class="fila-det-border"><jsp:getProperty name="BCPF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="23%" class="fila-det-border">Promoci&oacute;n: (*) </td>
                <td width="77%" class="fila-det-border"><input name="promocion" type="text" value="<%=BCPF.getPromocion()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Club:(*)</td>
                <td class="fila-det-border">
				<select name="idclub" id="idclub" class="campo" style="width:75px" onChange="document.frm.submit();" >
                  <option value="-1" >Sel.</option>
                  <% 
									  Iterator iter = BCPF.getListClub().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                  <option value="<%= datos[0] %>" <%= datos[0].equals( BCPF.getIdclub().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                  <%  
									  }%>
                </select>
				</td>
              </tr>
              <tr class="fila-det">
                <td width="23%" class="fila-det-border">Tipo Cliente : (*) </td>
                <td width="77%" class="fila-det-border">
				<select name="idtipoclie" id="idtipoclie" class="campo" style="width:300px" >
                  <option value="">Seleccionar</option>
                  <%
					iter = BCPF.getListTipoClie().iterator();
					while(iter.hasNext()){
					String [] datos = (String[])iter.next();%>
					  <option value="<%= datos[0] %>" <%= datos[0].equals( BCPF.getIdtipoclie().toString()) ? "selected" : "" %>><%= datos[1] %></option>
					  <%  
					}%>
                </select>               </td>
              </tr>
              
              <tr class="fila-det">
                <td width="23%" class="fila-det-border">Fecha Desde : (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="campo" onFocus="this.blur()" size="12" readonly type="text" name="fechadesde" value="<%=BCPF.getFechadesde()%>" maxlength="10">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_4', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_4', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_4', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_4');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_4" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="23%" class="fila-det-border">Fecha Hasta :  </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="campo" onFocus="this.blur()" size="12" readonly type="text" name="fechahasta" value="<%=BCPF.getFechahasta()%>" maxlength="10">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_5', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_5', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_5', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_5');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_5" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="23%" class="fila-det-border">Convenio:  </td>
                <td width="77%" class="fila-det-border"><textarea name="convenio" cols="70" rows="2" class="campo"><%=BCPF.getConvenio()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="23%" class="fila-det-border">Desc. CI % Alta: (*) </td>
                <td width="77%" class="fila-det-border"><input name="porc_desc_ci" type="text" value="<%=BCPF.getPorc_desc_ci()%>" class="campo" size="10" maxlength="10" style="text-align:right"></td>
              </tr>
              <tr class="fila-det">
                <td width="23%" class="fila-det-border">Desc. CI % Reactivación: (*) </td>
                <td width="77%" class="fila-det-border"><input name="porc_desc_ci_react" type="text" value="<%=BCPF.getPorc_desc_ci_react()%>" class="campo" size="10" maxlength="10" style="text-align:right"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
             </tr>
        </table>        </td>
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

