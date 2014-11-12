<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%//@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.*" %>
<% 
try{
Strings str = new Strings();
String titulo =  "ACTUALIZACION DE CURRICULUM VITAE" ;
%>
<html>
<jsp:useBean id="BRRHHLF"  class="ar.com.syswarp.web.ejb.BeanRrhhUploadFile"   scope="page"/>
<head>
<title><%= titulo %> </title>
<link rel="stylesheet" type="text/css" href="sitio/css/import.css">
<style type="text/css">
.Estilo1 {color: #FFFFFF}
.Estilo2 {color: #990000}
</style>
</head>
<BODY >
 <jsp:setProperty name="BRRHHLF" property="*" />

 <% 
 String idempresa = session.getAttribute("empresapostulante") +  "" ; 
 String idpostulante = session.getAttribute("idpostulante") == null ? "0" : session.getAttribute("idpostulante").toString() ; 
 
 if(str.esNulo(idempresa).equals("")) idempresa = "1"; 
 BRRHHLF.setResponse(response);
 BRRHHLF.setRequest(request);
 
 BRRHHLF.setUsuarioalt( session.getAttribute("idpostulante") == null ? "WEB" : idpostulante );
 BRRHHLF.setUsuarioact( session.getAttribute("idpostulante") == null ? "WEB" : idpostulante );
 BRRHHLF.setIduserpostulante( new BigDecimal(idpostulante));
 BRRHHLF.setIdempresa( new BigDecimal( idempresa ));
 BRRHHLF.ejecutarValidacion(); 
 %>

 <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr >
              <td><strong>&nbsp;<%= titulo %></strong><span class="Estilo1"><font class="Estilo2">
                <jsp:getProperty name="BRRHHLF" property="mensaje"/>                
              </font></span></td>
            </tr>
            <tr >
              <td class="Estilo2">&nbsp;</td>
            </tr>	
						&nbsp;					
         </table> 
            <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#F3F3F3" class="table-border">
              <tr >
                <td width="23%" height="43" bgcolor="#EAECEB" >
								<form method="POST" enctype="multipart/form-data" action="rrhhCvUpload.jsp"><input type="file" name="fichero">
								<input name="validar" type="submit"  value="Aceptar" id="validar"></form>
								</td>
              </tr>
              <tr >
                <td width="23%" height="38" ></td>
              </tr>
          </table>
       <p align="center">&nbsp;</p></td>
   </tr>
</table>
   
</body>
</html> 
<% 
 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (  + pagina +  ) : "+ex);   
}%>


