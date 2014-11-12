<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: Stockdepositos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Sep 04 09:24:05 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.math.*"%>
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
<jsp:useBean id="BSF"  class="ar.com.syswarp.web.ejb.BeanStockdepositosFrm"   scope="page"/>
<head>
 <title>FRMStockdepositos.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script></head>
 <script language="JavaScript" src="scripts/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BSF" property="*" />
 <% 
 String titulo = BSF.getAccion().toUpperCase() + " DE Depositos" ;
 BSF.setResponse(response);
 BSF.setRequest(request);
 BSF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BSF.setUsuarioact( session.getAttribute("usuario").toString() );
 BSF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BSF.ejecutarValidacion();
 %>
<form action="StockdepositosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BSF.getAccion()%>" >
<input name="codigo_dt" type="hidden" value="<%=BSF.getCodigo_dt()%>" >
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
                <td width="19%" class="fila-det-border">Descripcion: (*) </td>
                <td width="81%" class="fila-det-border"><input name="descrip_dt" type="text" value="<%=BSF.getDescrip_dt()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">Direccion: (*) </td>
                <td width="81%" class="fila-det-border"><input name="direc_dt" type="text" value="<%=BSF.getDirec_dt()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">Factura:  </td>
                <td width="81%" class="fila-det-border"><select name="factura_dt" id="factura_dt"  >
                  <option value="S" <%= BSF.getFactura_dt().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                  <option value="N" <%= BSF.getFactura_dt().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                </select>                </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Localidad: </td>
                <td class="fila-det-border"><input name="localidad" type="text" value="<%=BSF.getLocalidad()%>" class="campo" size="50" maxlength="50"  ><input name="idlocalidad" type="hidden" value="<%=BSF.getIdlocalidad()%>" >
                <img src="../imagenes/default/lupa.gif" width="21" height="17" style="cursor:pointer" onClick="abrirVentana('lov_localidades.jsp', 'localidad', 700, 400)"></td>
              </tr>
			  
			  <% if(BSF.getAccion().equalsIgnoreCase("modificacion")){ %>
			  
              <tr class="fila-det">
                <td class="fila-det-border">Bloquear Para Regalos </td>
                <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/gnome-lockscreen.png" width="22" height="22" onClick="abrirVentana('stockDepositosLockRegalosAbm.jsp?codigo_dt=<%=BSF.getCodigo_dt()%>&descrip_dt=<%=BSF.getDescrip_dt()%>', 'lock', 750, 450)" style="cursor:pointer"></td>
              </tr>
			  
			  <% }%>
			  
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

