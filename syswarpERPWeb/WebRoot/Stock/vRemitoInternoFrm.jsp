<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: vRemitoInterno
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Feb 16 16:17:57 GMT-03:00 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*"%>
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
<jsp:useBean id="BVRIF"  class="ar.com.syswarp.web.ejb.BeanVRemitoInternoFrm"   scope="page"/>
<head>
 <title></title>
 <style type="text/css">
<!--
.style3 {font-size: 9px; font-family: tahoma, Arial; }
.style5 {font-size: 9px; font-family: tahoma, Arial; font-weight: bold; }
-->
 </style>
</head>
<BODY >
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BVRIF" property="*" />
 <% 
 String titulo = BVRIF.getAccion().toUpperCase() + "" ;
 BVRIF.setResponse(response);
 BVRIF.setRequest(request);
 BVRIF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BVRIF.setUsuarioact( session.getAttribute("usuario").toString() );
 BVRIF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BVRIF.ejecutarValidacion();
 List listaMovInterno = BVRIF.getListaMovInterno();
 Iterator iterMovInterno = listaMovInterno.iterator();
 %>
<form action="vRemitoInternoFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BVRIF.getAccion()%>" >
<input name="remito_interno" type="hidden" value="<%=BVRIF.getRemito_interno()%>" >
   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
			 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td> 
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;                   </td>
                <td width="74%" ><jsp:getProperty name="BVRIF" property="mensaje"/></td>
                <td width="11%" ><span class="style3"><%=Common.setObjectToStrOrTime( BVRIF.getFecha(), "JSDateToStr" )%></span></td>
              </tr>
              <tr >
                <td width="15%" ><span class="style3">&nbsp;</span></td>
                <td ><span class="style3"><%=BVRIF.getDeposito()%></span></td>
                <td ><strong><span class="style3">CI:<%=BVRIF.getRemito_interno()%></span></strong></td>
              </tr>
              <tr >
                <td width="15%" ><span class="style3">&nbsp;</span></td>
                <td colspan="2" ><span class="style3"><%=BVRIF.getDireccion()%></span></td>
              </tr>
              <tr >
                <td width="15%" >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
          </table>
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
							<% 
							while(iterMovInterno.hasNext()){
							  String [] datosMovInterno = (String [])iterMovInterno.next();
							 %>
              <tr >
                <td width="2%"   >&nbsp;</td>
                <td width="13%"   ><span class="style3"><%=datosMovInterno[3]%> </span></td>
                <td width="45%"   ><span class="style3"><%=datosMovInterno[4]%></span></td>
                <td width="10%"   ><span class="style3"><%=datosMovInterno[8]%></span></td>
                <td width="30%"   ><span class="style3"><%=datosMovInterno[6]%></span></td>
              </tr>
							<%
							}  %>
          </table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>							
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>																																																															
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>							
              <tr class="fila-det-bold-rojo">
                <td > <span class="style3">                &nbsp;</span></td>
                <td width="74%" ><span class="style3">
                  <jsp:getProperty name="BVRIF" property="mensaje"/>                  
                </span></td>
                <td width="11%" > <span class="style3"><%=Common.setObjectToStrOrTime( BVRIF.getFecha(), "JSDateToStr" )%></span></td>
              </tr>
              <tr >
                <td width="15%" ><span class="style3">&nbsp;</span></td>
                <td ><span class="style3"><%=BVRIF.getDeposito()%></span></td>
                <td ><span class="style5">CI:<%=BVRIF.getRemito_interno()%></span></td>
              </tr>
              <tr >
                <td width="15%" ><span class="style3">&nbsp;</span></td>
                <td colspan="2" ><span class="style3"><%=BVRIF.getDireccion()%></span></td>
              </tr>
              <tr >                
							<td width="15%" >&nbsp;</td> 
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
              </tr>
              <tr >
                <td >&nbsp;</td>
                <td colspan="2" >&nbsp;</td>
             </tr>
         </table>
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
							<% 
							iterMovInterno = listaMovInterno.iterator();
							while(iterMovInterno.hasNext()){
							  String [] datosMovInterno = (String [])iterMovInterno.next();
							 %> 
              <tr >
                <td width="2%"   >&nbsp;</td>
                <td width="13%"   ><span class="style3"><%=datosMovInterno[3]%> </span></td>
                <td width="45%"   ><span class="style3"><%=datosMovInterno[4]%></span></td>
                <td width="10%"   ><span class="style3"><%=datosMovInterno[8]%></span></td>
                <td width="30%"   ><span class="style3"><%=datosMovInterno[6]%></span></td>
              </tr> 
							<%
							}  %>
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

