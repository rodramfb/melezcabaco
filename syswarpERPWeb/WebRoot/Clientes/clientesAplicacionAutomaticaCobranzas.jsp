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
<jsp:useBean id="BAAC"  class="ar.com.syswarp.web.ejb.BeanClientesAplicacionAutomaticaCobranzas" scope="page"/>
<head>
 <title><%= titulo %></title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" href="<%=pathskin%>">
 
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<script>
	function confirmarAplicar(){
		return( confirm('Confirma aplicar comprobantes?') );
	}
</script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BAAC" property="*" />
 <% 

 BAAC.setResponse(response);
 BAAC.setRequest(request);
 BAAC.setUsuarioalt( session.getAttribute("usuario").toString() );
 BAAC.setUsuarioact( session.getAttribute("usuario").toString() );
 BAAC.setEjercicio( new BigDecimal( (String) session.getAttribute("ejercicioActivo") ) ) ; 
 BAAC.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BAAC.ejecutarValidacion();
 %>
<form action="clientesAplicacionAutomaticaCobranzas.jsp" method="post" name="frm" onSubmit="return confirmarAplicar();">
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
                <jsp:getProperty name="BAAC" property="mensaje"/>                
                &nbsp;</div></td>
              </tr>
              <tr class="fila-det-bold">
                <td class="fila-det-border"><div align="center"><span class="fila-det-bold">Mediante esta operación aplicaran las deudas de los clientes.</span></div></td>
              </tr>
              
              <tr class="fila-det-bold">
                <td class="fila-det-border"><div align="center">
                  <input name="validar" type="submit" value="EJECUTAR APLICACION AUTOMATICA" class="boton">
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

