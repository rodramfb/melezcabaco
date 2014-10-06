<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: globalBlobImagenes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jun 17 11:33:18 GYT 2009 
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
<jsp:useBean id="BGBIF"  class="ar.com.syswarp.web.ejb.BeanGlobalBlobImagenesFrm"   scope="page"/>
<head>
 <title>FRMGlobalBlobImagenes</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BGBIF" property="*" />
 <% 
 String titulo = BGBIF.getAccion().toUpperCase() + " DE IMAGENES" ;
 BGBIF.setResponse(response);
 BGBIF.setRequest(request);
 BGBIF.setIdempresa ( new BigDecimal( session.getAttribute("empresa").toString() )  );
 BGBIF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BGBIF.setUsuarioact( session.getAttribute("usuario").toString() );
 BGBIF.ejecutarValidacion();
 %>
<form action="globalBlobImagenesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BGBIF.getAccion()%>" >
<input name="tupla" type="hidden" value="<%=BGBIF.getTupla()%>" >
   <input name="soloImagen" type="hidden" id="soloImagen" value="<%=BGBIF.isSoloImagen()%>">
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
                <td class="fila-det-border"><jsp:getProperty name="BGBIF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">&nbsp;Trama: (*) </td>
                <td width="76%" class="fila-det-border"><input name="trama" type="text" value="<%=BGBIF.getTrama()%>" class="campo" size="11" maxlength="11"  readonly></td>
              </tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">&nbsp;Nombre: (*) </td>
                <td width="76%" class="fila-det-border"><input name="nombre" type="text" value="<%=BGBIF.getNombre()%>" class="campo" size="50" maxlength="50" readonly ></td>
              </tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">Descripci&oacute;n: </td>
                <td width="76%" class="fila-det-border"><input name="descripcion" type="text" class="campo" value="<%=Common.setNotNull(BGBIF.getDescripcion())%>" size="70"></td>
              </tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">Es Principal?: (*) </td>
                <td width="76%" class="fila-det-border"><table width="47%" border="0" cellspacing="1" cellpadding="0">
                  <tr class="fila-det">
                      <td width="6%">Si</td>
                      <td width="37%"><input name="principal" type="radio" value="S" <%=BGBIF.getPrincipal().equalsIgnoreCase("S") ? "checked" : "" %> ></td>
                      <td width="8%">No                    </td>
                      <td width="49%"><input name="principal" type="radio" value="N" <%=BGBIF.getPrincipal().equalsIgnoreCase("N") ? "checked" : "" %> ></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">&nbsp;Directorio temporal :  </td>
                <td width="76%" class="fila-det-border"><input name="tmppath" type="text" class="campo" value="<%=BGBIF.getTmppath()%>" size="70" readonly></td>
              </tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">&nbsp;</td>
                <td width="76%" class="fila-det-border">&nbsp;</td>
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

