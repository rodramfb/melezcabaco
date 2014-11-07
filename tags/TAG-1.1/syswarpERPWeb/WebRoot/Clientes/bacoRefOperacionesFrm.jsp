<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacoRefOperaciones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jun 15 11:32:56 ART 2010 
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
<jsp:useBean id="BBROF"  class="ar.com.syswarp.web.ejb.BeanBacoRefOperacionesFrm"   scope="page"/>
<head>
 <title>FRMBacoRefOperaciones</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text">ffsdfd</div>  
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBROF" property="*" />
 <% 
 String titulo = BBROF.getAccion().toUpperCase() + " DE OPERACIONES" ;
 BBROF.setResponse(response);
 BBROF.setRequest(request);
 BBROF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBROF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBROF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBROF.ejecutarValidacion();
%>
<form action="bacoRefOperacionesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBROF.getAccion()%>" >
<input name="idoperacion" type="hidden" value="<%=BBROF.getIdoperacion()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BBROF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="15%" class="fila-det-border">Tipo Operaci&oacute;n: </td>
                <td width="85%" class="fila-det-border">
                  <select name="operacion" id="operacion" class="campo" style="width:200px" >
                    <option value="" >Seleccionar</option>
                    <% 
					  Iterator iter = BBROF.getListOperaciones().iterator();
					  while(iter.hasNext()){
						String [] datos = (String[])iter.next();%>
                    <option value="<%= datos[1] %>" <%= datos[1].equals( BBROF.getOperacion() ) ? "selected" : "" %>  ><%= datos[1] + " ( " +  datos[2]  + ") " %></option>
                    <%  
					  }%>
                  </select> 
				  </td>
              </tr>
              <tr class="fila-det">
                <td width="15%" class="fila-det-border">Descripci&oacute;n: </td>
                <td width="85%" class="fila-det-border"><input name="descripcion" type="text" value="<%=BBROF.getDescripcion()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="15%" class="fila-det-border">Puntaje: (*) </td>
                <td width="85%" class="fila-det-border"><input name="puntaje" type="text" value="<%=BBROF.getPuntaje()%>" class="campo" size="10" maxlength="10" style="text-align:right"></td>
              </tr>
              <tr class="fila-det">
                <td width="15%" class="fila-det-border">Acci&oacute;n: (*) 
                <input name="tipo" type="hidden" value="<%=BBROF.getTipo()%>" class="campo" size="20" maxlength="20"   ></td>
                <td width="85%" class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="8%" class="fila-det">Suma</td>
                      <td width="7%"><input name="signo" type="radio" value="+" onClick="document.frm.tipo.value = 'suma'" <%=BBROF.getSigno().equalsIgnoreCase("+") ?  "checked" : "" %>  ></td>
                      <td width="6%" class="fila-det">Resta</td>
                      <td width="79%"><input name="signo" type="radio" value="-"  onClick="document.frm.tipo.value = 'resta'"  <%=BBROF.getSigno().equalsIgnoreCase("-") ?  "checked" : "" %> ></td>
                    </tr>
                  </table></td> 
              </tr>
              <tr class="fila-det">
                <td width="15%" class="fila-det-border">Fecha desde: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechadesde" value="<%=BBROF.getFechadesde()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_7');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="15%" class="fila-det-border">Fecha hasta: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechahasta" value="<%=BBROF.getFechahasta()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_8', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_8', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_8', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_8');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_8" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="15%" class="fila-det-border">Proceso <%=BBROF.getProceso()%></td>
                <td colspan="2" class="fila-det-border" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="8%" class="fila-det">Manual</td>
                    <td width="7%"><input name="proceso" type="radio" value="man"  <%=BBROF.getProceso().equalsIgnoreCase("man") ?  "checked" : "" %>  ></td>
                    <td width="6%" class="fila-det">Auto</td>
                    <td width="79%"><input name="proceso" type="radio" value="auto"   <%=BBROF.getProceso().equalsIgnoreCase("auto") ?  "checked" : "" %> ></td>
                  </tr>
                </table></td>
              </tr>
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

