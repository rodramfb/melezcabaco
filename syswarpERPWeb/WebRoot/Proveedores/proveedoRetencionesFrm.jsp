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
<jsp:useBean id="BPRF"  class="ar.com.syswarp.web.ejb.BeanProveedoRetencionesFrm"   scope="page"/>
<head>
 <title>FRMProveedoRetenciones.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPRF" property="*" />
 <% 
 String titulo = BPRF.getAccion().toUpperCase() + " de Retenciones" ;
 BPRF.setResponse(response);
 BPRF.setRequest(request);
 BPRF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPRF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPRF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPRF.ejecutarValidacion();
 %>
<form action="proveedoRetencionesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPRF.getAccion()%>" >
<input name="idretencion" type="hidden" value="<%=BPRF.getIdretencion()%>" >
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
                
            <td width="13%" class="fila-det-border"> Retencion: (*) </td>
                <td width="87%" class="fila-det-border">
<input name="retencion" type="text" value="<%=BPRF.getRetencion()%>" class="campo" size="40" maxlength="40"  ></td>
              </tr>
              <tr class="fila-det">
                
            <td width="13%" class="fila-det-border">Importe 1: </td>
                
            <td width="87%" class="fila-det-border"> 
              <input name="impor1_ret" type="text" value="<%=BPRF.getImpor1_ret()%>" class="campo" size="14" maxlength="14"  ></td>
              </tr>
              <tr class="fila-det">
                
            <td width="13%" class="fila-det-border">Importe 2: </td>
                
            <td width="87%" class="fila-det-border"> 
              <input name="impor2_ret" type="text" value="<%=BPRF.getImpor2_ret()%>" class="campo" size="14" maxlength="14"  ></td>
              </tr>
              <tr class="fila-det">
                
            <td width="13%" class="fila-det-border">Importe 3: </td>
                
            <td width="87%" class="fila-det-border"> 
              <input name="impor3_ret" type="text" value="<%=BPRF.getImpor3_ret()%>" class="campo" size="14" maxlength="14"  ></td>
              </tr>
              <tr class="fila-det">
                
            <td width="13%" class="fila-det-border">Importe 4: </td>
                
            <td width="87%" class="fila-det-border"> 
              <input name="impor4_ret" type="text" value="<%=BPRF.getImpor4_ret()%>" class="campo" size="14" maxlength="14"  ></td>
              </tr>
              <tr class="fila-det">
                
            <td width="13%" class="fila-det-border">Importe 5: </td>
                
            <td width="87%" class="fila-det-border"> 
              <input name="impor5_ret" type="text" value="<%=BPRF.getImpor5_ret()%>" class="campo" size="14" maxlength="14"  ></td>
              </tr>
              <tr class="fila-det">
                
            <td width="13%" class="fila-det-border">Importe 6: </td>
                
            <td width="87%" class="fila-det-border"> 
              <input name="impor6_ret" type="text" value="<%=BPRF.getImpor6_ret()%>" class="campo" size="14" maxlength="14"  ></td>
              </tr>
              <tr class="fila-det">
                
            <td width="13%" class="fila-det-border">Importe 7: </td>
                
            <td width="87%" class="fila-det-border"> 
              <input name="impor7_ret" type="text" value="<%=BPRF.getImpor7_ret()%>" class="campo" size="14" maxlength="14"  ></td>
              </tr>
              <tr class="fila-det">
                
            <td width="13%" class="fila-det-border">Importe 8: </td>
                
            <td width="87%" class="fila-det-border"> 
              <input name="impor8_ret" type="text" value="<%=BPRF.getImpor8_ret()%>" class="campo" size="14" maxlength="14"  ></td>
              </tr>
              <tr class="fila-det">
                
            <td width="13%" class="fila-det-border">Importe 9: </td>
                
            <td width="87%" class="fila-det-border"> 
              <input name="impor9_ret" type="text" value="<%=BPRF.getImpor9_ret()%>" class="campo" size="14" maxlength="10"  ></td>
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

