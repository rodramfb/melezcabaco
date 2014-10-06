<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacoRefPrepro
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jun 16 10:12:03 ART 2010 
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
<jsp:useBean id="BBRPF"  class="ar.com.syswarp.web.ejb.BeanBacoRefPreproFrm"   scope="page"/>
<head>
 <title></title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
 <script>
 function setReferenteFuente(idreferente){

   document.frm.idreferente.value = idreferente;
   document.frm.referente.value = '';
 
 }
 
 function validarFuente(){
   var idreferenteFuente = document.frm.idfuente.options[document.frm.idfuente.selectedIndex].lang;
   idreferenteFuente = idreferenteFuente == null || idreferenteFuente == '' ? -1 : idreferenteFuente;
   if(idreferenteFuente < 0) alert('Para poder seleccionar un referente es necesario seleccionar previamente fuente.');
   else if(idreferenteFuente > 0 ) alert('La fuente seleccionada ya posee un referente asociado por default, no es posible modificarlo.')
   else abrirVentana('../Clientes/lov_clientes.jsp', 'clie', 700, 400)   
 }
 
 function callBuscarProvincia (){
   document.frm.idlocalidad.value = -1;
   document.frm.localidad.value = '';
   abrirVentana('../Clientes/lov_provincia.jsp', 'prov', 700, 400) ;
 }
 
 </script> 


</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBRPF" property="*" />
 <% 
 String titulo = BBRPF.getAccion().toUpperCase() + " DE PREPOSPECTOS - REFERIDOS" ;
 BBRPF.setResponse(response);
 BBRPF.setRequest(request);
 BBRPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBRPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBRPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBRPF.ejecutarValidacion();
 %>
<form action="bacoRefPreproDetalle.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBRPF.getAccion()%>" >
<input name="idpreprospecto" type="hidden" value="<%=BBRPF.getIdpreprospecto()%>" >
   <table width="90%"  border="1" cellspacing="0" cellpadding="0" align="center" class="text-dos-bold">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="1" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td colspan="2" class="fila-det-border"><jsp:getProperty name="BBRPF" property="mensaje"/></td>
              </tr>
              <tr class="text-dos-bold">
                <td width="16%" >Nombre: </td>
                <td width="84%" class="fila-det-bold"><%=BBRPF.getNombre()%>&nbsp;</td>
              </tr>
              <tr class="text-dos-bold">
                <td width="16%" >Apellido: </td>
                <td width="84%" class="fila-det-bold"><%=BBRPF.getApellido()%>&nbsp;</td>
              </tr>
              <tr class="text-dos-bold">
                <td >Fuente: </td>
                <td class="fila-det-bold">
				<% 
					  Iterator iter = BBRPF.getListFuente().iterator();
					  while(iter.hasNext()){
						String [] datos = (String[])iter.next();%>
                         <%= datos[0].equals( BBRPF.getIdfuente().toString()) ?  datos[1] : "" %>
                <%  
					  }%>&nbsp;</td>
              </tr>
              <tr class="text-dos-bold">
                <td width="16%" >Referente: </td>
                <td width="84%" class="fila-det-bold"><%=BBRPF.getIdreferente()%> - <%=BBRPF.getReferente()%></td>
              </tr>
              <tr class="text-dos-bold">
                <td width="16%" >Vendedor: </td>
                <td width="84%" class="fila-det-bold"><%=BBRPF.getVendedor()%>&nbsp;</td>
              </tr>
              <tr class="text-dos-bold">
                <td width="16%" >Fecha: </td>
                <td width="84%" class="fila-det-bold"><%=BBRPF.getFecha()%>&nbsp;</td>
              </tr>
              <tr class="text-dos-bold">
                <td width="16%" >Telefono:  </td>
                <td width="84%" class="fila-det-bold"><%=BBRPF.getTelefono()%>&nbsp;</td>
              </tr>
              <tr class="text-dos-bold">
                <td width="16%" >Celular:  </td>
                <td width="84%" class="fila-det-bold"><%=BBRPF.getCelular()%>&nbsp;</td>
              </tr>
              <tr class="text-dos-bold">
                <td width="16%" >Email:  </td>
                <td width="84%" class="fila-det-bold"><%=BBRPF.getEmail()%>&nbsp;</td>
              </tr>
              <tr class="text-dos-bold">
                <td width="16%" >Provincia:  </td>
                <td width="84%" class="fila-det-bold"><%=BBRPF.getProvincia()%>&nbsp;</td>
              </tr>
              <tr class="text-dos-bold">
                <td width="16%" >Localidad:  </td>
                <td width="84%" class="fila-det-bold"><%=BBRPF.getLocalidad()%>&nbsp;</td>
              </tr>
              <tr class="text-dos-bold">
                <td width="16%"  >Observaciones:  </td>
                <td width="84%" class="fila-det-bold"><%=BBRPF.getObservaciones()%>&nbsp;</td>
              </tr>
              <tr class="text-dos-bold">
                <td width="16%" >Procesado: </td>
                <td width="84%" class="fila-det-bold"><%= BBRPF.getProcesado().intValue()!= 1 ? "NO" : "SI" %>&nbsp;</td>
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

