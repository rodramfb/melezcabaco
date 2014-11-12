<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesUsuarioZona
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Sep 07 16:01:30 ART 2010 
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
Iterator iter;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCUZF"  class="ar.com.syswarp.web.ejb.BeanClientesUsuarioZonaFrm"   scope="page"/>
<head>
 <title>FRMClientesUsuarioZona</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCUZF" property="*" />
 <% 
 String titulo = BCUZF.getAccion().toUpperCase() + " DE USUARIO - ZONA" ;
 BCUZF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCUZF.setResponse(response);
 BCUZF.setRequest(request);
 BCUZF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCUZF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCUZF.ejecutarValidacion();
 %>
<form action="clientesUsuarioZonaFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCUZF.getAccion()%>" >
<input name="zona" type="hidden" value="<%=BCUZF.getZona()%>" >
<input name="usuario" type="hidden" value="<%=BCUZF.getUsuario()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BCUZF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td height="42" class="fila-det-border">&nbsp;Usuario: (*) </td>
                <td class="fila-det-border"><select name="idusuario" id="idusuario" class="campo" style="width:50%" onChange="document.frm.usuario.value = this.options[this.selectedIndex].text">
                    <option value="">Seleccionar</option> 
                    <%
            iter = BCUZF.getListUsuarios().iterator();
			while(iter.hasNext()){
			String [] datos = (String[])iter.next();%>
                    <option value="<%= datos[0] %>" <%= datos[0].equals( BCUZF.getIdusuario().toString()) ? "selected" : "" %>><%= datos[1] %></option>
                    <%  
			}%>
                  </select></td>
              </tr>
              <tr class="fila-det">
                <td width="16%" height="44" class="fila-det-border">&nbsp;Zona: (*) </td>
                <td width="84%" class="fila-det-border"><p>
                  <select name="idzona" id="idzona" class="campo" style="width:50%" onChange="document.frm.zona.value = this.options[this.selectedIndex].text">
                      <option value="">Seleccionar</option>
                      <%
            iter = BCUZF.getListZonas().iterator();
			while(iter.hasNext()){
			String [] datos = (String[])iter.next();%>
                      <option value="<%= datos[0] %>" <%= datos[0].equals( BCUZF.getIdzona().toString()) ? "selected" : "" %>><%= datos[1] %></option>
                      <%  
			}%>
                    </select>
</p></td>
              </tr>
              <tr class="fila-det">
                <td height="43" class="fila-det-border">&nbsp;</td>
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

