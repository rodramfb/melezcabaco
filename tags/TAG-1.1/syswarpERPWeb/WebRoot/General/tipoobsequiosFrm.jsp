<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesdescuentos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jul 30 15:02:20 ART 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.*" %>
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
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanGeneralTipoobsequiosFrm"   scope="page"/>
<head>
 <title>Tipo Obsequios</title>
   <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script></head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 

 String titulo = BCF.getAccion().toUpperCase() + " DE Tipo Obsequios" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCF.ejecutarValidacion();
 Iterator iter;
 %>
<form action="tipoobsequiosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="idtipoobsequio" type="hidden" value="<%=BCF.getIdtipoobsequio()%>" >
   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td height="36">&nbsp;<%= titulo %></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="19%" height="37" class="fila-det-border">Tipo Obsequio: (*) </td>
                <td width="81%" class="fila-det-border"><input name="tipoobsequio" type="text" class="campo" id="tipoobsequio" value="<%=BCF.getTipoobsequio()%>" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td height="39" class="fila-det-border">Motivo Descuento(*) </td>
                <td class="fila-det-border"><select name="idmotivodescuento" id="idmotivodescuento" class="campo"   >
                  <option value="-1">Seleccionar</option>
                  <% 
									  iter = BCF.getListMotivosDescuento().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                  <option value="<%= datos[0] %>" <%= datos[0].equals(  BCF.getIdmotivodescuento().toString() ) ? "selected" : "" %>><%= datos[1] %> </option>
                  <%  
									  }%>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td height="39" class="fila-det-border">&nbsp;</td>
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

