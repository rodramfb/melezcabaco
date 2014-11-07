<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: rrhhothoras
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Apr 07 15:54:04 ART 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BRF"  class="ar.com.syswarp.web.ejb.BeanRrhhothorasFrm"   scope="page"/>
<head>
 <title>FRMRrhhothoras.jsp</title>
<link rel = "stylesheet" href = "<%= pathskin %>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="vs/overlib/overlib.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BRF" property="*" />
 <% 
 String titulo = BRF.getAccion().toUpperCase() + " DE Horas" ;
 BRF.setResponse(response);
 BRF.setRequest(request);
 BRF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BRF.setUsuarioact( session.getAttribute("usuario").toString() );
 BRF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BRF.ejecutarValidacion();
 %>
<form action="rrhhothorasFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BRF.getAccion()%>" >
<input name="idothoras" type="hidden" value="<%=BRF.getIdothoras()%>" >
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
                <td width="14%" class="fila-det-border">Orden de trabajo: (*) </td>
                <td width="86%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="ordendetrabajo" type="text" class="campo" id="ordendetrabajo" value="<%=BRF.getOrdendetrabajo()%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_ordentrabajo.jsp')" style="cursor:pointer"></td>
<input name="idordendetrabajo" type="hidden" id="idordendetrabajo" value="<%=BRF.getIdordendetrabajo()%>">
</tr>
</table></td>
              </tr>
			  <tr class="fila-det">
                <td width="14%" class="fila-det-border">Detalle: (*) </td>
                <td width="86%" class="fila-det-border"><input name="detalle" type="text" value="<%=BRF.getDetalle()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Fecha:  </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecha" value="<%=BRF.getFecha()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_3', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_3', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_3', 'img_Date_DOWN');showCalendar('frm','fecha','BTN_date_3');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_3" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Hora  entrada1:  </td>
              <td colspan="2" class="fila-det-border" ><table width="8%" border="0">
<tr class="fila-det-border">
<td width="18%" ><input name="horaentrada1" type="text" class="campo" id="horaentrada1" value="<%=BRF.getHoraentrada1()%>" size="5" maxlength="2"  ></td>
<td width="82%"><input name="minutoentrada1" type="text" class="campo" id="minutoentrada1" value="<%=BRF.getMinutoentrada1()%>" size="5" maxlength="2"  ></td>
</tr>
</table>
</td> 
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Hora salida1:  </td>
                <td colspan="2" class="fila-det-border" ><table width="8%" border="0">
<tr class="fila-det-border">
<td width="18%" ><input name="horasalida1" type="text" class="campo" id="horasalida1" value="<%=BRF.getHorasalida1()%>" size="5" maxlength="2"  ></td>
<td width="82%"><input name="minutosalida1" type="text" class="campo" id="minutosalida1" value="<%=BRF.getMinutosalida1()%>" size="5" maxlength="2"  ></td>
</tr>
</table></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Hora entrada2:  </td>
                <td colspan="2" class="fila-det-border" ><table width="8%" border="0">
<tr class="fila-det-border">
<td width="18%" ><input name="horaentrada2" type="text" class="campo" id="horaentrada2" value="<%=BRF.getHoraentrada2()%>" size="5" maxlength="2"  ></td>
<td width="82%"><input name="minutoentrada2" type="text" class="campo" id="minutoentrada2" value="<%=BRF.getMinutoentrada2()%>" size="5" maxlength="2"  ></td>
</tr>
</table></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Hora salida2:  </td>
                <td colspan="2" class="fila-det-border" ><table width="8%" border="0">
<tr class="fila-det-border">
<td width="18%" ><input name="horasalida2" type="text" class="campo" id="horasalida2" value="<%=BRF.getHorasalida2()%>" size="5" maxlength="2"  ></td>
<td width="82%"><input name="minutosalida2" type="text" class="campo" id="minutosalida2" value="<%=BRF.getMinutosalida2()%>" size="5" maxlength="2"  ></td>
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

