<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clienteszonas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Nov 14 14:50:17 GMT-03:00 2006 
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
 String titulo =  "RENUMERACION DE ASIENTOS" ;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanContableAsientosRenumerarFrm" scope="page"/>
<head>
 <title><%= titulo %></title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" href="<%=pathskin%>">
 
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<script>
	function confirmarRenumeracion(){
		return( confirm('Confirma renumerar todos los asientos?') );
	}
</script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 

 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setEjercicio( new BigDecimal( (String) session.getAttribute("ejercicioActivo") ) ) ; 
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCF.ejecutarValidacion();
 %>
<form action="contableAsientosRenumerarFrm.jsp" method="post" name="frm" onSubmit="return confirmarRenumeracion();">
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
                <td class="fila-det-border"><div align="center">
                <jsp:getProperty name="BCF" property="mensaje"/>                
                &nbsp;</div></td>
              </tr>
              <tr class="fila-det-bold">
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det-bold">
                <td class="fila-det-border"><div align="center"><span class="fila-det-bold">Mediante esta operaci&oacute;n se asignar&aacute; un nuevo n&uacute;mero de asiento</span></div></td>
              </tr>
              <tr class="fila-det-bold">
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det-bold">
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det-bold">
                <td class="fila-det-border"><div align="center"><span class="fila-det-bold">para los asientos correspondientes al ejercicio activo <%= BCF.getEjercicio() %></span></div></td>
              </tr>
              <tr class="fila-det-bold">
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det-bold">
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det-bold">
                <td class="fila-det-border"><div align="center"><span class="fila-det-bold">una vez confirmada la operaci&oacute;n, se perder&aacute; la antigua numeraci&oacute;n. </span></div></td>
              </tr>
              <tr class="fila-det-bold">
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det-bold">
                <td width="12%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det-bold">
                <td class="fila-det-border"><div align="center">
                  <input name="validar" type="submit" value="EJECUTAR RENUMERACION" class="boton">
                </div></td>
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

