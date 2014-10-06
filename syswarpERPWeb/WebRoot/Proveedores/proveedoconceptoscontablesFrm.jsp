<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: proveedoRetenciones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jul 04 18:02:24 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ page import="java.math.*"%> 
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
<jsp:useBean id="BPRF"  class="ar.com.syswarp.web.ejb.BeanProveedoconceptoscontablesFrm"   scope="page"/>
<head>
 <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <!-- Copyright 2000, 2001, 2002, 2003 Macromedia, Inc. All rights reserved. -->
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPRF" property="*" />
 <% 
 String titulo = BPRF.getAccion().toUpperCase() + "Conceptos Contables";
 BPRF.setResponse(response);
 BPRF.setRequest(request);
 BPRF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPRF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPRF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPRF.ejecutarValidacion();
 %>
<form action="proveedoconceptoscontablesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPRF.getAccion()%>" >
<input name="idconcepto" type="hidden" value="<%=BPRF.getIdconcepto()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BPRF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                
            <td width="13%" class="fila-det-border"> Cuenta Contable: (*) </td>
			    
                <td width="87%" class="fila-det-border"><table width="17%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="idcuenta" type="text" class="campo" id="idcuenta" value="<%=BPRF.getIdcuenta()%>" ></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="mostrarLOV('lov_cuentascont.jsp')" style="cursor:pointer"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                
            <td width="13%" class="fila-det-border">Orden (*) : </td>
                
            <td width="87%" class="fila-det-border"> 
              <input name="orden" type="text" class="campo" id="orden" value="<%=BPRF.getOrden()%>" size="9" maxlength="9"  ></td>
              </tr>
              <tr class="fila-det">
                
            <td width="13%" class="fila-det-border">Letra (*) </td>
                
            <td width="87%" class="fila-det-border"> 
              <input name="letra" type="text" class="campo" id="letra" value="<%=BPRF.getLetra()%>" size="1" maxlength="14"  ></td>
              </tr>
              <tr class="fila-det">
                
            <td width="13%" class="fila-det-border">Tipo Comprobante: (*) </td>
            <input name="idtipocomp" type="hidden" id="idtipocomp" value="<%=BPRF.getIdtipocomp()%>">    
            <td width="87%" class="fila-det-border"><table width="17%" border="0" cellpadding="0" cellspacing="0">
                <tr class="fila-det-border">
                  <td width="61%" ><input name="tipocomp" type="text" class="campo" id="tipocomp" value="<%=BPRF.getTipocomp()%>" readonly="yes"></td>
                  <td width="39%"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="mostrarLOV('lov_tipocomp.jsp')" style="cursor:pointer"></td>
                </tr>
              </table></td>
              </tr>
              <tr class="fila-det">
                
            <td width="13%" class="fila-det-border">Tipo: (*) </td>
                
            <td width="87%" class="fila-det-border"> 
              <input name="tipo" type="text" value="<%=BPRF.getTipo()%>" class="campo" size="1" maxlength="1"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
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

