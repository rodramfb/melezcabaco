<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: cajaValorTar
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Oct 21 10:01:01 GMT-03:00 2008 
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
<jsp:useBean id="BCVTF"  class="ar.com.syswarp.web.ejb.BeanCajaValorTarFrm"   scope="page"/>
<head>
 <title></title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCVTF" property="*" />
 <% 
 String titulo = " DETALLE DE " +  (BCVTF.getTipo().equalsIgnoreCase("C") ?  " CUOTAS" :  " PRESENTACION");
 BCVTF.setResponse(response);
 BCVTF.setRequest(request);
 BCVTF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCVTF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCVTF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));   
 BCVTF.ejecutarValidacion();
 %>
<form action="cajaValorTarFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCVTF.getAccion()%>" >
<input name="valor_id" type="hidden" value="<%=BCVTF.getValor_id()%>" >
<input name="cuotasPresentacion" type="hidden" value="<%=BCVTF.getCuotasPresentacion()%>" >
<input name="primeraCarga" type="hidden" value="false" >
<input name="tipo" type="hidden" value="<%=BCVTF.getTipo()%>" class="campo" size="3" maxlength="3"  >

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
                <td height="24" class="fila-det-border"><jsp:getProperty name="BCVTF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="74%" height="22" class="fila-det-border">
								
									<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
										<tr class="fila-det">
										  <td height="22" class="fila-det-border"><div align="center"><%= (BCVTF.getTipo().equalsIgnoreCase("C") ?  " CUOTA" :  " PRESENTACION") %></div></td>
										  <td class="fila-det-border"><div align="center">Dias(*)</div></td>
									  </tr>										
										<% 
										for(int f = 0; f<BCVTF.getCuotasPresentacion().intValue(); f++){ %>
										

										<tr class="fila-det">
											<td width="46%" height="22" class="fila-det-border"><div align="center"><%=BCVTF.getNumero()[f]%></div></td>
										  <td width="54%" class="fila-det-border"><div align="center">
										    <input name="dias" type="text" value="<%=BCVTF.getDias()[f]%>" class="campo" size="5" maxlength="3"  >
										    <input name="numero" type="hidden" value="<%=BCVTF.getNumero()[f]%>" class="campo" size="100" maxlength="100"  >
									    </div></td>
										</tr>
										
										<% 
										} %>
								</table>								</td> 
              </tr>
              <tr class="fila-det">
                <td height="35" class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Confirmar" class="boton">
                <input name="cerrarr" type="button" class="boton" id="cerrar" value="Cerrar" onClick="window.close()"></td>
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

