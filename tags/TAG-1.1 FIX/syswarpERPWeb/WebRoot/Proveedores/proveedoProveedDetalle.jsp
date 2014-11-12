<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: proveedoProveed
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jul 05 15:38:22 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
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
<jsp:useBean id="BPPF"  class="ar.com.syswarp.web.ejb.BeanProveedoProveedFrm"   scope="page"/>
<head>
 <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 
 <link rel="stylesheet" href="<%=pathskin%>">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <!-- Copyright 2000, 2001, 2002, 2003 Macromedia, Inc. All rights reserved. -->
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPPF" property="*" />
 <% 
 String titulo = " FICHA PROVEEDOR" ;
 BPPF.setResponse(response);
 BPPF.setRequest(request);
 BPPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPPF.ejecutarValidacion();
 
 %>

<form action="" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPPF.getAccion()%>" >

   <table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %></td>
            </tr>
         </table> 
            <table width="100%" border="1" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td >&nbsp;</td>
                <td ><jsp:getProperty name="BPPF" property="mensaje"/>&nbsp;</td>
                <td >&nbsp;</td>
                <td >&nbsp;</td>
              </tr>
			  
			  
			<tr class="fila-det">
                <td width="16%" class="fila-det-bold">Codigo:  </td>
                <td width="34%" class="fila-det" ><%=BPPF.getIdproveedor()%></td>
                <td width="21%" class="fila-det-bold">&nbsp;</td>
                <td width="29%" >&nbsp;</td>
			  </tr>  
              <tr class="fila-det">
                
            <td width="16%" class="fila-det-bold">Razon Social: (*) </td>
                <td class="fila-det" ><%=BPPF.getRazon_social()%></td>
                <td class="fila-det-bold">Codigo postal: </td>
                <td class="fila-det" ><%=BPPF.getCodigo_postal()%></td>
              </tr>
              <tr class="fila-det">
                
            <td width="16%" class="fila-det-bold">Domicilio: (*) </td>
                <td class="fila-det" ><%=BPPF.getDomicilio()%></td>
                <td class="fila-det-bold">&nbsp;</td>
                <td class="fila-det" >&nbsp;</td>
              </tr>
              <tr class="fila-det">
                
            <td width="16%"  class="fila-det-bold">Localidad: (*)            </td>
                <td width="34%" class="fila-det" ><%=BPPF.getLocalidad()%></td>
                <td width="21%" class="fila-det-bold">Provincia: (*)</td>
                <td width="29%" class="fila-det" ><%=BPPF.getProvincia()%></td>
              </tr>
              <tr class="fila-det">
                
            <td width="16%" class="fila-det-bold">Contacto: </td>
                <td class="fila-det" ><%=BPPF.getContacto()%></td>
                <td class="fila-det-bold">Brutos: </td>
                <td class="fila-det" ><%=BPPF.getBrutos()%></td>
              </tr>
              <tr class="fila-det">
                
            <td width="16%" class="fila-det-bold">Telefono: </td>
                <td class="fila-det" ><%=BPPF.getTelefono()%></td>
                <td class="fila-det-bold">Letra Iva:</td>
                <td class="fila-det" ><%=BPPF.getLetra_iva()%></td>
              </tr>
              <tr class="fila-det">
                
            <td width="16%" class="fila-det-bold">Cuit: </td>
                <td class="fila-det" ><%=BPPF.getCuit()%></td>
                <td class="fila-det-bold">Stock Facturado</td>
                <td class="fila-det" ><%= BPPF.getStock_fact().equalsIgnoreCase("S") ? "SI" : "NO" %> </td>
              </tr>
              <tr class="fila-det">
                
            <td width="16%"  class="fila-det-bold">Cta Pasivo: (*)            </td>
                
            <td class="fila-det" ><%=BPPF.getCtapasivo()%></td>
              <td class="fila-det-bold">Retencion 1:</td>
              <td class="fila-det" ><%=str.esNulo(BPPF.getD_idretencion1())%></td>
              </tr>
							
              <tr class="fila-det">
            <td width="16%"  class="fila-det-bold">Cta Activo 1:            </td>   
            <td class="fila-det" ><%=BPPF.getCtaactivo1()%></td>
              <td class="fila-det-bold">Retencion 2:</td>
              <td class="fila-det" ><%=str.esNulo(BPPF.getD_idretencion2())%></td>
              </tr>
              <tr class="fila-det">
                
            <td width="16%"  class="fila-det-bold">Cta Activo 2:            </td>
                
            <td class="fila-det" ><%=BPPF.getCtaactivo2()%></td>
              <td class="fila-det-bold">Retencion 3:</td>
              <td class="fila-det" ><%=str.esNulo(BPPF.getD_idretencion3())%></td>
              </tr>
              <tr class="fila-det">
                
            <td width="16%"  class="fila-det-bold">Cta Activo 3:            </td>
                
            <td class="fila-det" ><%=BPPF.getCtaactivo3()%></td>
              <td class="fila-det-bold">Retencion 4:</td>
              <td class="fila-det" ><%=str.esNulo(BPPF.getD_idretencion4())%></td>
              </tr>
              <tr class="fila-det">
                
            <td width="16%"  class="fila-det-bold">Cta Activo 4:            </td>
                
            <td class="fila-det" ><%=BPPF.getCtaactivo4()%></td>
              <td class="fila-det-bold">Retencion 5:</td>
              <td class="fila-det" ><%=str.esNulo(BPPF.getD_idretencion5())%></td>
              </tr>
              
          <tr class="fila-det"> 
            <td width="16%"  class="fila-det-bold">Cta Iva: </td>
                
            <td class="fila-det" ><%=BPPF.getCtaiva()%></td>
              <td class="fila-det-bold">Ret de Ganancia:</td>
              <td class="fila-det" > <%= BPPF.getRet_gan().equalsIgnoreCase("S") ? "SI" : "NO" %> </td>
          </tr>
              <tr class="fila-det">
                
            <td width="16%" class="fila-det-bold">Cta Reten. de Iva: </td>
                
            <td class="fila-det" ><%=BPPF.getCtaretiva()%></td>
              <td class="fila-det-bold">Cta Documento:</td>
              <td class="fila-det" ><%=BPPF.getCtadocumen()%></td>
              </tr>
              <tr class="fila-det">
                
            <td width="16%"  class="fila-det-bold">Cta Descuento:</td>
                
            <td class="fila-det" ><%=BPPF.getCtades()%></td>
              <td class="fila-det-bold">Cond de Pago: (*)</td>
              <td class="fila-det" ><%=str.esNulo(BPPF.getD_idcondicionpago())%></td>
              </tr>
              <tr class="fila-det">
                
            <td width="16%" class="fila-det-bold">Cent 1:</td>
                <td class="fila-det" ><%=BPPF.getCent1()%></td>
                <td class="fila-det-bold">Cents 1:</td>
                <td class="fila-det" ><%=BPPF.getCents1()%></td>
              </tr>
              <tr class="fila-det">
                
            <td width="16%" class="fila-det-bold">Cent 2:</td>
                <td class="fila-det" ><%=BPPF.getCent2()%></td>
                <td class="fila-det-bold">Cents 2:</td>
                <td class="fila-det" ><%=BPPF.getCents2()%></td>
              </tr>
              <tr class="fila-det">
                
            <td width="16%" class="fila-det-bold">Cent 3:</td>
                <td class="fila-det" ><%=BPPF.getCent3()%></td>
                <td class="fila-det-bold">Cents 3:</td>
                <td class="fila-det" ><%=BPPF.getCents3()%></td>
              </tr>
              <tr class="fila-det">
                
            <td width="16%" class="fila-det-bold">Cent 4:</td>
                <td class="fila-det" ><%=BPPF.getCent4()%></td>
                <td class="fila-det-bold">Cents 4:</td>
                <td class="fila-det" ><%=BPPF.getCents4()%></td>
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