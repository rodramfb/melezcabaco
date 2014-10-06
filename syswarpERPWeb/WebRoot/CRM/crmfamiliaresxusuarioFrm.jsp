<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: crmfamiliares
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jun 14 17:25:20 GMT-03:00 2007 
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
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanCrmfamiliaresxusuarioFrm"   scope="page"/>
<head>
 <title>FRMCrmfamiliares.jsp</title>
 <meta http-equiv="description" content="mypage">
  
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script></head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
  String idindividuos =  request.getParameter("idindividuos");
  String razon_nombre =  request.getParameter("razon_nombre");
 if(idindividuos!=null){
   session.setAttribute("idindividuos",idindividuos);
   session.setAttribute("razon_nombre",razon_nombre);
 } 
String titulo    =  "Alta de Familiares de: " + razon_nombre ; 
 idindividuos     =  session.getAttribute("idindividuos").toString();
 razon_nombre     =  session.getAttribute("razon_nombre").toString();
 BCF.setIdindividuos( new BigDecimal( session.getAttribute("idindividuos").toString() ));
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));   
 BCF.ejecutarValidacion();
 %>
<form action="crmfamiliaresxusuarioFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="idfamiliar" type="hidden" value="<%=BCF.getIdfamiliar()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>&nbsp;</td>
              </tr>
<input name="idindividuos" type="hidden" id="idindividuos" value="<%=BCF.getIdindividuos()%>" size="30" readonly>
<input name="razon_nombre" type="hidden" id="razon_nombre" value="<%=BCF.getRazon_nombre()%>">			  
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">Nexo familiar: (*) </td>
                <td width="75%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="nexofamiliar" type="text" class="campo" id="nexofamiliar" value="<%=str.esNulo(BCF.getNexofamiliar())%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../CRM/lov_nexofamiliar.jsp', 'individuos', 700, 400)" style="cursor:pointer"></td>
<input name="idnexofamiliar" type="hidden" id="idnexofamiliar" value="<%=BCF.getIdnexofamiliar()%>">
</tr>
</table></td>
              </tr>
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">Nombre: (*) </td>
                <td width="75%" class="fila-det-border"><input name="nombre" type="text" value="<%=str.esNulo(BCF.getNombre())%>" class="campo" size="70" maxlength="70"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">Profesion:  </td>
                <td width="75%" class="fila-det-border"><input name="profesion" type="text" value="<%=str.esNulo(BCF.getProfesion())%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">Actividad:  </td>
                <td width="75%" class="fila-det-border"><input name="actividad" type="text" value="<%=str.esNulo(BCF.getActividad())%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">E-mail:  </td>
                <td width="75%" class="fila-det-border"><input name="email" type="text" value="<%=str.esNulo(BCF.getEmail())%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">Telefono particular:  </td>
                <td width="75%" class="fila-det-border"><textarea name="telefonos_part" cols="70" rows="6" class="campo"><%=str.esNulo(BCF.getTelefonos_part())%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">Celular:  </td>
                <td width="75%" class="fila-det-border"><input name="celular" type="text" value="<%=str.esNulo(BCF.getCelular())%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">Fecha nacimiento:  </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechanacimientoStr" value="<%=BCF.getFechanacimientoStr()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_10', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_10', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_10', 'img_Date_DOWN');showCalendar('frm','fechanacimientoStr','BTN_date_10');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_10" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">Deportes:  </td>
                <td width="75%" class="fila-det-border"><input name="deportes" type="text" value="<%=BCF.getDeportes()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">Hobbies:  </td>
                <td width="75%" class="fila-det-border"><input name="hobbies" type="text" value="<%=BCF.getHobbies()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">Actividad social:  </td>
                <td width="75%" class="fila-det-border"><input name="actividad_social" type="text" value="<%=BCF.getActividad_social()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">Obseravaciones:  </td>
                <td width="75%" class="fila-det-border"><textarea name="obseravaciones" cols="70" rows="6" class="campo"><%=BCF.getObseravaciones()%></textarea></td>
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

