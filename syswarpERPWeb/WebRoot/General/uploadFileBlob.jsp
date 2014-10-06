<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: globalImagenes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Mar 10 10:51:29 ART 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 


%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.math.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
//String mensaje = str.esNulo(request.getParameter("mensaje"));
//String estadoUpload = str.esNulo(request.getParameter("estadoUpload")).equals("") ?  "false" : request.getParameter("estadoUpload");
//String path = str.esNulo(request.getParameter("path"));
//String soloImagen = str.esNulo(request.getParameter("soloImagen")).equals("") ? "false" : request.getParameter("soloImagen");
//String tupla = str.esNulo(request.getParameter("tupla")).equals("") ? "99999999" : request.getParameter("tupla");
%>

<HTML>

<jsp:useBean id="BUFB"  class="ar.com.syswarp.web.ejb.BeanUploadFileBlob"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BUFB" property="*" />

<%
 BUFB.setIdempresa ( new BigDecimal( session.getAttribute("empresa").toString() )  );
 BUFB.setResponse(response);
 BUFB.setRequest(request);
 BUFB.ejecutarValidacion();

%>

<HEAD>
<TITLE>Carga de Archivos</TITLE> 
</HEAD> 
 <link rel="stylesheet" href="<%=pathskin%>"> 
 <!--BORRAR-->
 <link rel="stylesheet" href="../imagenes/default/erp-style.css"> 
 <link rel="stylesheet" href="vs/calendar/calendar.css"> 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <script>

 function retornar(){

   document.location = 'globalBlobImagenesAbm.jsp?modulo=general&soloImagen=<%= BUFB.isSoloImagen() %>&tupla=<%= BUFB.getTupla() %>';

 }
 </script>
 
 
<BODY> 


<form method="POST" enctype="multipart/form-data" action="../servlet/UploadFicheroBlob?modulo=general&soloImagen=<%= BUFB.isSoloImagen() %>&tupla=<%= BUFB.getTupla() %>"> 
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
		
        <tr class="fila-det">
          <td height="34" class="text-globales">CARGA DE ARCHIVOS - BLOB </td>
          <td  class="text-globales">&nbsp;</td>
          <td  class="text-globales">&nbsp;</td>
        </tr>
        <tr class="text-dos-bold">
          <td height="22" >Limite de Cargas de Archivos:  <%= !BUFB.isBlobLimiteActivo() ?  "ILIMITADO" :(BUFB.getTotalFiles()) + ""%></td>
          <td  >&nbsp;</td>
          <td  >Posibles de Cargar: <%= !BUFB.isBlobLimiteActivo() ?  "ILIMITADO" :(BUFB.getTotalFiles() - BUFB.getTotalRegistros()) + ""%></td>
        </tr>
        <tr class="text-dos-bold"> 
          <td height="20" >Total de Archivos Cargados: <%= BUFB.getTotalRegistros() %></td> 
          <td  >&nbsp;</td>
          <td  >&nbsp;</td>
        </tr>
        <tr class="fila-det"> 
          <td height="25" colspan="3" class="fila-det-border">
						<table width="100%"  border="0" cellspacing="0" cellpadding="0">
							<tr >
							 <td class="fila-det-bold-rojo"><jsp:getProperty name="BUFB" property="mensaje"/>&nbsp;</td>
							</tr>
						</table>         </td> 
        </tr>					
        <tr class="fila-det-bold">
          <td height="35" class="fila-det-border">Archivo        
          <td class="fila-det-border">Principal        
        <td class="fila-det-border">Descripci&oacute;n       </tr>
        <% 
         for(int m = 0;m<BUFB.getTotalFiles() ;m++){
           if( (m) >= ( BUFB.getTotalFiles() - BUFB.getTotalRegistros() ) && BUFB.isBlobLimiteActivo()) break;
         %>
        <tr class="fila-det">
          <td height="35" class="fila-det-border"><input name="fichero_<%= m %>" type="file"  class="campo">        
          <td class="fila-det-border"><input name="principal" type="radio" value="0">          
        <td class="fila-det-border"><input name="descripcion_<%= m %>" type="text" class="campo" id="descripcion_<%= m %>" size="50" maxlength="250">        </tr>
        <%
         }
         %>
<%--         <tr class="fila-det">
          <td height="35" class="fila-det-border"><input name="fichero_1" type="file"  class="campo">                            
          <td class="fila-det-border"><input name="principal" type="radio" value="1">          
          <td class="fila-det-border"><input name="descripcion_1" type="text" class="campo" id="descripcion_1" size="50" maxlength="250">        </tr>
        <tr class="fila-det">
          <td height="35" class="fila-det-border"><input name="fichero_2" type="file"  class="campo">        
          <td class="fila-det-border"><input name="principal" type="radio" value="2">          
        <td class="fila-det-border"><input name="descripcion_2" type="text" class="campo" id="descripcion_2" size="50" maxlength="250">        </tr>
        <tr class="fila-det"> 
          <td width="19%" height="35" class="fila-det-border">   
        <input name="fichero_3" type="file"  class="campo" id="fichero_3">
          <td width="11%" class="fila-det-border"><input name="principal" type="radio" value="3">
        <td width="70%" class="fila-det-border"><input name="descripcion_3" type="text" class="campo" id="descripcion_3" size="50" maxlength="250">        </tr> --%>

        <tr class="fila-det">
          <td height="35" class="fila-det-border">&nbsp;</td>       
          <td class="fila-det-border"><input name="submit" type="submit" value="Aceptar" class="campo">                            
        <td class="fila-det-border"><input name="volta" type="button" class="campo" id="volta" value="Volver" onClick="retornar();"></td>  
      </tr>
  </table> 

</form> 

</BODY>
</HTML>
<% 
 } 
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true); 
   ex.printStackTrace(pw);
  System.out.println("ERROR (  uploadFile.jsp ) : "+ex);   
}%>