<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/*  
   Formulario de carga para la entidad: crmindividuos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jun 14 17:23:23 GMT-03:00 2007 
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
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanCrmindividuosFrm"   scope="page"/>
<head>
 <title>FRMCrmindividuos.jsp</title>
 <meta http-equiv="description" content="mypage">
  
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo = BCF.getAccion().toUpperCase() + " DE Gestion de individuos" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));   
 BCF.ejecutarValidacion();
 %>
<form action="crmindividuosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="idindividuos" type="hidden" value="<%=BCF.getIdindividuos()%>" >
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
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Razon nombre: (*) </td>
                <td width="74%" class="fila-det-border"><input name="razon_nombre" type="text" value="<%=str.esNulo(BCF.getRazon_nombre())%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Telefonos particular: (*) </td>
                <td width="74%" class="fila-det-border"><textarea name="telefonos_part" cols="70" rows="6" class="campo"><%=str.esNulo(BCF.getTelefonos_part())%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Celular:  </td>
                <td width="74%" class="fila-det-border"><input name="celular" type="text" value="<%=str.esNulo(BCF.getCelular())%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">E-mail:  </td>
                <td width="74%" class="fila-det-border"><input name="email" type="text" value="<%=str.esNulo(BCF.getEmail())%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Domicilio particular:  </td>
                <td width="74%" class="fila-det-border"><textarea name="domicilio_part" cols="70" rows="6" class="campo"><%=str.esNulo(BCF.getDomicilio_part())%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Fecha nacimiento:  </td>
              <td colspan="2" class="fila-det-border" ><input name="fechanacimientoStr" type="text" class="cal-TextBox" id="fechanacimientoStr" onFocus="this.blur()" value="<%=BCF.getFechanacimientoStr()%>" size="12" maxlength="12" readonly>
<a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechanacimientoStr','BTN_date_7');return false;"><img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Empresa:  </td>
                <td width="74%" class="fila-det-border"><input name="empresa" type="text" value="<%=str.esNulo(BCF.getEmpresa())%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Domicilio laboral:  </td>
                <td width="74%" class="fila-det-border"><textarea name="domicilio_laboral" cols="70" rows="6" class="campo"><%=str.esNulo(BCF.getDomicilio_laboral())%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Telefonos empresa:  </td>
                <td width="74%" class="fila-det-border"><textarea name="telefonos_empr" cols="70" rows="6" class="campo"><%=str.esNulo(BCF.getTelefonos_empr())%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Profesion:  </td>
                <td width="74%" class="fila-det-border"><input name="profesion" type="text" value="<%=str.esNulo(BCF.getProfesion())%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Actividad:  </td>
                <td width="74%" class="fila-det-border"><input name="actividad" type="text" value="<%=str.esNulo(BCF.getActividad())%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Deportes:  </td>
                <td width="74%" class="fila-det-border"><input name="deportes" type="text" value="<%=str.esNulo(BCF.getDeportes())%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Hobbies:  </td>
                <td width="74%" class="fila-det-border"><input name="hobbies" type="text" value="<%=str.esNulo(BCF.getHobbies())%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Actividad social:  </td>
                <td width="74%" class="fila-det-border"><input name="actividad_social" type="text" value="<%=str.esNulo(BCF.getActividad_social())%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Diario lectura:  </td>
                <td width="74%" class="fila-det-border"><input name="diario_lectura" type="text" value="<%=str.esNulo(BCF.getDiario_lectura())%>" class="campo" size="30" maxlength="30"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Revista lectura:  </td>
                <td width="74%" class="fila-det-border"><input name="revista_lectura" type="text" value="<%=str.esNulo(BCF.getRevista_lectura())%>" class="campo" size="30" maxlength="30"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Lugar veraneo:  </td>
                <td width="74%" class="fila-det-border"><input name="lugar_veraneo" type="text" value="<%=str.esNulo(BCF.getLugar_veraneo())%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Obseravaciones:  </td>
                <td width="74%" class="fila-det-border"><textarea name="obseravaciones" cols="70" rows="6" class="campo"><%=str.esNulo(BCF.getObseravaciones())%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Usuario:  </td>
              <td width="74%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="usuario" type="text" class="campo" id="usuario" value="<%=str.esNulo(BCF.getUsuario())%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../CRM/lov_usuario.jsp', 'usuario', 700, 400)" style="cursor:pointer"></td>
<input name="idusuario" type="hidden" id="idusuario" value="<%=BCF.getIdusuario()%>">
</tr>
</table></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Tipo cliente:  </td>
              <td width="74%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="tipocliente" type="text" class="campo" id="tipocliente" value="<%=str.esNulo(BCF.getTipocliente())%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../CRM/lov_tiposclientes.jsp', 'tipoclientes', 700, 400)" style="cursor:pointer"></td>
<input name="idtipocliente" type="hidden" id="idtipocliente" value="<%=BCF.getIdtipocliente()%>">
</tr>
</table></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Clasificacion cliente:  </td>
              <td width="74%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="clasificacioncliente" type="text" class="campo" id="clasificacioncliente" value="<%=str.esNulo(BCF.getClasificacioncliente())%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../CRM/lov_clasificacionclientes.jsp', 'clasificacioncliente', 700, 400)" style="cursor:pointer"></td>
<input name="idclasificacioncliente" type="hidden" id="idclasificacioncliente" value="<%=BCF.getIdclasificacioncliente()%>">
</tr>
</table></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Fuente:  </td>
              <td width="74%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="fuente" type="text" class="campo" id="fuente" value="<%=str.esNulo(BCF.getFuente())%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../CRM/lov_fuente.jsp', 'fuente', 700, 400)" style="cursor:pointer"></td>
<input name="idfuente" type="hidden" id="idfuente" value="<%=BCF.getIdfuente()%>">
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

