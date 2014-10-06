<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesPeriodicidadEntrega
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jul 31 12:17:09 GMT-03:00 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*" %> 
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
<jsp:useBean id="BCPEF"  class="ar.com.syswarp.web.ejb.BeanClientesPeriodicidadEntregaFrm"   scope="page"/>
<head>
 <title>PERIODICIDAD</title>
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script>
 
 function seleccion(opt){
   var i=0;
   for(i=0;i<12;i++){
     unCheck(i);
     if(opt==3){
	   document.frm.idmes.options[i].selected = true;
	 }else  if(opt==1 && (i+10)%2 != 0){
       document.frm.idmes.options[i].selected = true;
	 }else  if(opt==2 && (i+10)%2 == 0){	     
       document.frm.idmes.options[i].selected = true;
	 }else unCheck(i);
   }
 }
 
 function unCheck(indice){
   document.frm.idmes.options[indice].selected = false;
 }
 
 </script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCPEF" property="*" />
 <% 
 String titulo = BCPEF.getAccion().toUpperCase() + " DE PERIODICIDAD DE ENTREGA" ;
 BCPEF.setResponse(response);
 BCPEF.setRequest(request);

 BCPEF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCPEF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCPEF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCPEF.ejecutarValidacion();

 %>
<form action="clientesPeriodicidadEntregaFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCPEF.getAccion()%>" >
<input name="idcliente" type="hidden" value="<%=BCPEF.getIdcliente()%>" >

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
                <td colspan="3" class="fila-det-border">&nbsp;<jsp:getProperty name="BCPEF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="22%" height="210" class="fila-det-border">&nbsp;Meses de Entrega: (*) </td>
                <td width="20%" class="fila-det-border">&nbsp;
                  <select name="idmes" size="12" id="idmes" multiple="multiple" class="campo">
                   <% 
				    Iterator iter = BCPEF.getListClientesPeriodicidadEntrega().iterator();  
				    while(iter.hasNext()){ 
					  String[] datos = (String[])iter.next();
				   %>
				    <option value="<%= datos[0] %>" <%= !datos[2].equals("0") ?  "selected":  "" %>><%= datos[1] %></option> 
					<% 
					} %>  
                  </select></td>
                <td width="23%" class="fila-det-border"><table width="0" height="0" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det">
                    <td width="103" height="45" ><input name="par" type="button" value="Pares" class="boton" style="width:70px" onClick="seleccion(1)" <%= BCPEF.getAccion().equalsIgnoreCase("consulta") ? "disabled" : ""%>></td>
                  </tr>
                  <tr class="fila-det">
                    <td height="45" ><input name="impar" type="button" value="Impares" class="boton" style="width:70px"  onClick="seleccion(2)" <%= BCPEF.getAccion().equalsIgnoreCase("consulta") ? "disabled" : ""%>></td>
                  </tr>
                  <tr class="fila-det">
                    <td height="45" ><input name="todos" type="button" value="Todos" class="boton" style="width:70px"  onClick="seleccion(3)" <%= BCPEF.getAccion().equalsIgnoreCase("consulta") ? "disabled" : ""%>></td>
                  </tr>
                  <tr class="fila-det">
                    <td height="45" ><input name="ninguno" type="button" class="boton" id="ninguno" style="width:70px"  onClick="seleccion(0)" value="Ninguno" <%= BCPEF.getAccion().equalsIgnoreCase("consulta") ? "disabled" : ""%>></td>
                  </tr>
                </table></td>
                <td width="35%" class="fila-det-border">
				<table width="100%" height="0" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det">
                    <td height="24" >Para seleccionar multiples entregas </td>
                  </tr>
                  <tr class="fila-det">
                    <td > presione [Ctrl + Click] o [Shift + Click] .</td>
                  </tr>
                  <tr class="fila-det">
                    <td >&nbsp;</td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td height="41" class="fila-det-border">&nbsp;</td>
                <td colspan="3" class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Generar Periodicidad" class="boton" <%= BCPEF.getAccion().equalsIgnoreCase("consulta") ? "disabled" : ""%> ></td>
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

