<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacoRefCtaCte
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jun 17 12:34:05 ART 2010 
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
<jsp:useBean id="BBRCCF"  class="ar.com.syswarp.web.ejb.BeanBacoRefCtaCteFrm"   scope="page"/>
<head>
 <title></title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBRCCF" property="*" />
 <% 
 String titulo =   "ASIGNACION MANUAL DE PUNTOS PARA CLIENTE: " + BBRCCF.getIdcliente() + " - " + BBRCCF.getCliente() ;
 BBRCCF.setResponse(response);
 BBRCCF.setRequest(request);
 BBRCCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBRCCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBRCCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBRCCF.ejecutarValidacion();
 %>
<form action="bacoRefCtaCteFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBRCCF.getAccion()%>" >
<input name="idctacte" type="hidden" value="<%=BBRCCF.getIdctacte()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BBRCCF" property="mensaje"/>&nbsp;
                <input name="idcliente" type="hidden" value="<%=BBRCCF.getIdcliente()%>" class="campo" size="10" maxlength="10"  >
                <input name="cliente" type="hidden" class="campo" id="cliente" value="<%=BBRCCF.getCliente()%>" size="40" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Operaci&oacute;n: (*) </td>
                <td width="88%" class="fila-det-border">
                  <select name="idoperacion" id="idoperacion" class="campo" style="width:200px" onChange="document.frm.signo.value = this.options[this.selectedIndex].lang" >
                    <option value="-1" >Seleccionar</option>
                    <% 
					  Iterator iter = BBRCCF.getListOperaciones().iterator();
					  while(iter.hasNext()){
						String [] datos = (String[])iter.next();%>
                    <option value="<%= datos[0] %>" <%= datos[0].equals( BBRCCF.getIdoperacion().toString()) ? "selected" : "" %> lang="<%= datos[5] %>"><%= datos[1] + " - " + datos[2] %></option>
                    <%  
					  }%>
                  </select></td>
              </tr>
              <%--               
			  <tr class="fila-det">
                <td width="12%" class="fila-det-border">Referido:  </td>
                <td width="88%" class="fila-det-border"><input name="idreferido" type="text" value="<%=BBRCCF.getIdreferido()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
			   --%>
			  
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Puntos: (*) </td>
                <td width="88%" class="fila-det-border"><input name="signo" type="text" class="campo" id="signo" value="<%=BBRCCF.getSigno()%>" size="2" maxlength="1"  style="text-align:right">
                <input name="puntos" type="text" value="<%=BBRCCF.getPuntos()%>" class="campo" size="10" maxlength="10" style="text-align:right"></td>
              </tr>
<%--
               <tr class="fila-det">
                <td class="fila-det-border">Fecha: (*) </td>
                <td colspan="2" class="fila-det-border" ><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecha" value="<%=BBRCCF.getFecha()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fecha','BTN_date_6');return false;"> <img align="absmiddle" border="0" name="BTN_date_6" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a> </td>
              </tr> 
--%>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Observaciones(*)</td>
                <td colspan="2" class="fila-det-border" ><textarea name="observaciones" cols="100" rows="2" class="campo" id="observaciones"><%=BBRCCF.getObservaciones()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

