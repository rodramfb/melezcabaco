<%@page language="java" %>
<% 
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: RRHHbusquedasLaborales
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Oct 10 16:05:59 ART 2007 
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
<jsp:useBean id="BRRHHLF"  class="ar.com.syswarp.web.ejb.BeanRRHHbusquedasLaboralesFrm"   scope="page"/>
<head>
 <title>FRMRRHHbusquedasLaborales.jsp</title>
<link rel = "stylesheet" href = "<%= pathskin %>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="vs/overlib/overlib.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BRRHHLF" property="*" />
 <% 
 String titulo = BRRHHLF.getAccion().toUpperCase() + " DE Busqueda de recursos" ;
 BRRHHLF.setResponse(response);
 BRRHHLF.setRequest(request);
 BRRHHLF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BRRHHLF.setUsuarioact( session.getAttribute("usuario").toString() );
 BRRHHLF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BRRHHLF.ejecutarValidacion(); 
 %>
<form action="RRHHbusquedasLaboralesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BRRHHLF.getAccion()%>" >
<input name="idbusquedalaboral" type="hidden" value="<%=BRRHHLF.getIdbusquedalaboral()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BRRHHLF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="18%" class="fila-det-border">Referencia: (*) </td>
              <td width="82%" class="fila-det-border"><input name="referencia" type="text" value="<%=BRRHHLF.getReferencia()%>" class="campo" size="20" maxlength="20"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="18%" class="fila-det-border">Fecha busqueda desde: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechabusquedadesdeStr" value="<%=BRRHHLF.getFechabusquedadesdeStr()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_3', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_3', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_3', 'img_Date_DOWN');showCalendar('frm','fechabusquedadesdeStr','BTN_date_3');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_3" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>
                </td>
              </tr>
              <tr class="fila-det">
                <td width="18%" class="fila-det-border">Fecha busqueda hasta: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechabusquedahastaStr" value="<%=BRRHHLF.getFechabusquedahastaStr()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_4', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_4', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_4', 'img_Date_DOWN');showCalendar('frm','fechabusquedahastaStr','BTN_date_4');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_4" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>
                </td>
              </tr>
              <tr class="fila-det">
                <td width="18%" class="fila-det-border">Seniority: (*) </td>
                <td width="82%" class="fila-det-border"><input name="seniority" type="text" value="<%=BRRHHLF.getSeniority()%>" class="campo" size="30" maxlength="30"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="18%" class="fila-det-border">Lugar trabajo: (*)  </td>
                <td width="82%" class="fila-det-border"><input name="lugartrabajo" type="text" value="<%=BRRHHLF.getLugartrabajo()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="18%" class="fila-det-border">Descripcion proyecto:  </td>
                <td width="82%" class="fila-det-border"><textarea name="descripcionproyecto" cols="70" rows="6" class="campo"><%=BRRHHLF.getDescripcionproyecto()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="18%" class="fila-det-border">Descripcion tarea:  </td>
                <td width="82%" class="fila-det-border"><textarea name="descripciontarea" cols="70" rows="6" class="campo"><%=BRRHHLF.getDescripciontarea()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="18%" class="fila-det-border">Skill excluyente:  </td>
                <td width="82%" class="fila-det-border"><textarea name="skillexcluyente" cols="70" rows="6" class="campo"><%=BRRHHLF.getSkillexcluyente()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="18%" class="fila-det-border">Skill deseable:  </td>
                <td width="82%" class="fila-det-border"><textarea name="skilldeseable" cols="70" rows="6" class="campo"><%=BRRHHLF.getSkilldeseable()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="18%" class="fila-det-border">Idioma:  </td>
                <td width="82%" class="fila-det-border"><textarea name="idioma" cols="70" rows="6" class="campo"><%=BRRHHLF.getIdioma()%></textarea></td>
              </tr>
<tr class="fila-det">
                <td width="18%" class="fila-det-border">&nbsp;Posibilidad de Renovacion:  </td>
                <td width="82%" class="fila-det-border"><select name="posibilidadderenovacion" id="select"  >
<option value="S" <%= BRRHHLF.getPosibilidadderenovacion().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
<option value="N" <%= BRRHHLF.getPosibilidadderenovacion().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
</select></td>
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

