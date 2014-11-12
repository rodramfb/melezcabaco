<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesAnexoLocalidades
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Dec 23 14:59:21 GMT-03:00 2008 
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
<jsp:useBean id="BCALF"  class="ar.com.syswarp.web.ejb.BeanClientesAnexoLocalidadesFrm"   scope="page"/>
<head>
 <title>FRMClientesAnexoLocalidades</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script> 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCALF" property="*" />
 <% 
 String titulo = BCALF.getAccion().toUpperCase() + " DE ANEXO LOCALIDADES" ;
 BCALF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCALF.setResponse(response);
 BCALF.setRequest(request);
 BCALF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCALF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCALF.ejecutarValidacion();
 %>
<form action="clientesAnexoLocalidadesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCALF.getAccion()%>" >
<input name="idanexolocalidad" type="hidden" value="<%=BCALF.getIdanexolocalidad()%>" >
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
                <td colspan="2" class="fila-det-border">&nbsp;
  <jsp:getProperty name="BCALF" property="mensaje"/>&nbsp;</td></tr>
              <tr class="fila-det">
                <td width="24%" height="27" class="fila-det-border">&nbsp;Zona / Distribuidor : (*) </td>
                <td class="fila-det-border">&nbsp;
                  <input name="expresozona" type="text" class="campo" id="expresozona" value="<%= !BCALF.getExpreso().equals("") ?  BCALF.getExpreso() + " / " + BCALF.getZona() : "" %>" size="65" maxlength="100" readonly ></td>
                <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="18" height="18" style="cursor:pointer" onClick="abrirVentana('lov_expreso_zona.jsp','ezona', 750, 400);">
                  <input name="idexpresozona" type="hidden" value="<%=BCALF.getIdexpresozona()%>" class="campo" size="10" maxlength="100"  >
                  <input name="expreso" type="hidden" class="campo" id="expreso" value="<%=BCALF.getExpreso()%>" size="10" maxlength="100"  >
                  <input name="zona" type="hidden" class="campo" id="zona" value="<%=BCALF.getZona()%>" size="10" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">&nbsp;Localidad: (*) </td>
                <td width="45%" class="fila-det-border">&nbsp;
                <input name="localidad" type="text" class="campo" id="localidad" value="<%=BCALF.getLocalidad()%>" size="65" maxlength="100" readonly ></td> 
                <td width="31%" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="18" height="18" style="cursor:pointer" onClick="abrirVentana('lov_localidades2.jsp','ezona', 750, 400);">
                <input name="idlocalidad" type="hidden" value="<%=BCALF.getIdlocalidad()%>" class="campo" size="10" maxlength="100"  ></td></tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">&nbsp;C&oacute;digo Tarifa Basica: </td>
                <td colspan="2" class="fila-det-border">&nbsp;
                <input name="codtfbasica" type="text" value="<%=BCALF.getCodtfbasica()%>" class="campo" size="10" maxlength="10"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">&nbsp;Tarifa Contado : </td>
                <td colspan="2" class="fila-det-border">&nbsp;
                  <input name="codtfctdo" type="text" class="campo" value="<%=BCALF.getCodtfctdo()%>" size="40" maxlength="248"></td>
              </tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">&nbsp;Tarifa Bulto: (*) </td>
                <td colspan="2" class="fila-det-border">&nbsp;
                <input name="tarand1bulto" type="text" value="<%=BCALF.getTarand1bulto()%>" class="campo" size="10" maxlength="10"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border"> &nbsp;Tarifa Exceso: (*) </td>
                <td colspan="2" class="fila-det-border">&nbsp;
                <input name="tarandexc" type="text" value="<%=BCALF.getTarandexc()%>" class="campo" size="10" maxlength="10"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">&nbsp;Tarifa Socio Bulto : (*) </td>
                <td colspan="2" class="fila-det-border">&nbsp;
                <input name="tarsoc1bulto" type="text" value="<%=BCALF.getTarsoc1bulto()%>" class="campo" size="10" maxlength="10"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">&nbsp;Tarifa Socio Excedente : (*) </td>
                <td colspan="2" class="fila-det-border">&nbsp;
                <input name="tarsocexc" type="text" value="<%=BCALF.getTarsocexc()%>" class="campo" size="10" maxlength="10"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">&nbsp;Cabecera/Influencia: (*) </td>
                <td colspan="2" class="fila-det-border">&nbsp;
                <select name="cabeoinflu" id="cabeoinflu" class="campo">
                  <option value="">Seleccionar</option>
                  <option value="C" <%=BCALF.getCabeoinflu().equalsIgnoreCase("C") ?  "selected" : ""%>>Cabecera</option> 
                  <option value="I" <%=BCALF.getCabeoinflu().equalsIgnoreCase("I") ?  "selected" : ""%>>Influencia</option>
                  <option value="N">No Atendida</option>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">&nbsp;Norte/Sur: (*)  </td>
                <td colspan="2" class="fila-det-border">&nbsp;
                <select name="norteosur" id="norteosur" class="campo">
								  <option value="">Seleccionar</option>
								  <option value="N" <%=BCALF.getNorteosur().equalsIgnoreCase("N") ?  "selected" : ""%>>NORTE</option>
								  <option value="S" <%=BCALF.getNorteosur().equalsIgnoreCase("S") ?  "selected" : ""%>>SUR</option>								
                </select>                </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">ID-Expreso Baco</td>
                <td colspan="2" class="fila-det-border">&nbsp;
                  <input name="idexpresobaco" type="text" class="campo" id="idexpresobaco" value="<%=BCALF.getIdexpresobaco()%>" size="10" maxlength="10"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">ID-Distribuidor Baco</td>
                <td colspan="2" class="fila-det-border">&nbsp;
                  <input name="iddistribuidorbaco" type="text" class="campo" id="iddistribuidorbaco" value="<%=BCALF.getIddistribuidorbaco()%>" size="10" maxlength="10"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

