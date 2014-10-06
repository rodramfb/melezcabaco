<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacoObsequiosLocalidad
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Nov 04 15:51:58 GMT-03:00 2009 
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
<jsp:useBean id="BBOLF"  class="ar.com.syswarp.web.ejb.BeanBacoObsequiosLocalidadFrm"   scope="page"/>
<head>
 <title>FRMBacoObsequiosLocalidad</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBOLF" property="*" />
 <% 
 String titulo = BBOLF.getAccion().toUpperCase() + " DE CARTA / OBSEQUIOS POR LOCALIDAD" ;
 BBOLF.setResponse(response);
 BBOLF.setRequest(request);
 BBOLF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBOLF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBOLF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBOLF.ejecutarValidacion();
 %>
<form action="bacoObsequiosLocalidadFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBOLF.getAccion()%>" >
<input name="referente" type="hidden" value="<%=BBOLF.getReferente()%>" >
<input name="idobsequio" type="hidden" value="<%=BBOLF.getIdobsequio()%>" >
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
                <td height="34" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BBOLF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="19%" height="32" class="fila-det-border">&nbsp;Localidad: (*) 
                <input name="idlocalidad" type="hidden" value="<%=BBOLF.getIdlocalidad()%>"   ></td>
                <td width="81%" class="fila-det-border"><table width="36%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="localidad" type="text" class="campo" id="localidad" value="<%=BBOLF.getLocalidad()%>" size="50" readonly></td>
                      <td width="39%">
                        <img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_localidades.jsp')" style="cursor:pointer"></td>
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="19%" height="31" class="fila-det-border">&nbsp;Tipo: (*) </td>
                <td width="81%" class="fila-det-border"> 
                  <select name="cartaoregalo" id="cartaoregalo" class="campo">
                    <option value="">Seleccionar</option>
                    <option value="C" <%=BBOLF.getCartaoregalo().equalsIgnoreCase("C") ? "selected" : "" %> >Carta</option>
                    <option value="R" <%=BBOLF.getCartaoregalo().equalsIgnoreCase("R") ? "selected" : "" %> >Regalo</option>
                  </select></td>
              </tr>
              <tr class="fila-det">
                <td height="37" class="fila-det-border">&nbsp;Plantilla: </td>
                <td class="fila-det-border"><input name="informecarta" type="text" value="<%=Common.setNotNull(BBOLF.getInformecarta())%>" class="campo" size="50" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="19%" height="37" class="fila-det-border">&nbsp;Restaurant:  </td>
                <td width="81%" class="fila-det-border"><input name="restaurant" type="text" class="campo" id="restaurant" value="<%=Common.setNotNull(BBOLF.getRestaurant())%>" size="50" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td height="39" class="fila-det-border">Cluster:
                <input name="idclusterlogistica" type="hidden" id="idclusterlogistica" value="<%=BBOLF.getIdclusterlogistica()%>"   ></td>
                <td class="fila-det-border"><table width="36%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="clusterlogistica" type="text" class="campo" id="clusterlogistica" value="<%=Common.setNotNull(BBOLF.getClusterlogistica())%>" size="50" maxlength="100"  readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/bacoClustersLogistica_lov.jsp', 'CLUSTER', 750, 400)" style="cursor:pointer"></td>
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td height="39" class="fila-det-border">&nbsp;</td>
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

