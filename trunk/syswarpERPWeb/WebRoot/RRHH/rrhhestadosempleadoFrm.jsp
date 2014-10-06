<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: rrhhestadosempleado
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jul 19 12:24:44 ART 2012 
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
<jsp:useBean id="BRF"  class="ar.com.syswarp.web.ejb.BeanRrhhestadosempleadoFrm"   scope="page"/>
<head>
 <title>FRMRrhhestadosempleado</title>
 <meta http-equiv="description" content="DELTA">
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
	<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
	<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
	<script language="JavaScript" src="vs/forms/forms.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BRF" property="*" />
 <% 
 String titulo = BRF.getAccion().toUpperCase() + " DE ESTADO ACTUAL DEL EMPLEADO" ;
 BRF.setResponse(response);
 BRF.setRequest(request);
 BRF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BRF.setUsuarioact( session.getAttribute("usuario").toString() );
 BRF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BRF.ejecutarValidacion();
 %>
<form action="rrhhestadosempleadoFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BRF.getAccion()%>" >
<input name="accionpersonal" type="hidden" value="<%=BRF.getAccionpersonal()%>" >
<input name="idestadoempleado" type="hidden" value="<%=BRF.getIdestadoempleado()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BRF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Legajo: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="legajo" type="text" value="<%=BRF.getLegajo()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Apellido:  </td>
                <td width="88%" class="fila-det-border">&nbsp;<textarea name="apellido" cols="70" rows="6" class="campo"><%=BRF.getApellido()%></textarea></td>
              </tr>
              <!--<tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;esempleado: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="esempleado" type="text" value="< %=BRF.getEsempleado()%>" class="campo" size="1" maxlength="1"  ></td>
              </tr>
              --><tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Motivo: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;
                	<select name = "idmotivo" onclick="document.frm.submit()">
                		<option value="-1">Seleccionar</option>
                		<%
                		Iterator iterMotivos = BRF.getListMotivos().iterator();
                		while (iterMotivos.hasNext())
                		{
                			String[] datosMotivos = (String[]) iterMotivos.next();
                			%>
                			<option value="<%=datosMotivos[0]%>" <%=BRF.getIdmotivo().longValue() == new BigDecimal(datosMotivos[0]).longValue() ? "Selected": "" %> ><%=datosMotivos[1]%></option>
                			<%
                		}
                		%>
                	</select>
                </td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Razon: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;
                	<select name = "idrazon" >
                		<option value="-1">Seleccionar</option>
                		<%
                		Iterator iterRazon = BRF.getListRazones().iterator();
                		while (iterRazon.hasNext())
                		{
                			String[] datosRazon = (String[]) iterRazon.next();
                			%>
                			<option value="<%=datosRazon[0]%>" <%=BRF.getIdrazon().longValue() == new BigDecimal(datosRazon[0]).longValue() ? "Selected": "" %>><%=datosRazon[1]%></option>
                			<%
                		}
                		%>
                	</select>
                
                </td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Observacion:  </td>
                <td width="88%" class="fila-det-border">&nbsp;<textarea name="observacion" cols="70" rows="6" class="campo"><%=BRF.getObservacion()%></textarea></td>
              </tr>
              <!--<tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Fecha: (*) </td>
                <td colspan="2" class="fila-det-border" >&nbsp;
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly="readonly" type="text" name="fecha" value="< %=BRF.getFecha()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_8', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_8', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_8', 'img_Date_DOWN');showCalendar('frm','fecha','BTN_date_8');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_8" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>
                </td>
              </tr>
              --><tr class="fila-det">
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

