<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: globalpuestos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Feb 08 12:58:13 GMT-03:00 2007 
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
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BGF"  class="ar.com.syswarp.web.ejb.BeanGlobalpuestosFrm"   scope="page"/>
<head>
 <title>FRMGlobalpuestos.jsp</title>
 <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script></head>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BGF" property="*" />
 <% 
 String titulo = BGF.getAccion().toUpperCase() + " DE Puestos" ;
 BGF.setResponse(response);
 BGF.setRequest(request);
 BGF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BGF.setUsuarioact( session.getAttribute("usuario").toString() );
 BGF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BGF.ejecutarValidacion();
 %>
<form action="globalpuestosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BGF.getAccion()%>" >
<input name="idpuesto" type="hidden" value="<%=BGF.getIdpuesto()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BGF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Puesto: (*) </td>
                <td width="88%" class="fila-det-border"><input name="puesto" type="text" value="<%=BGF.getPuesto()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Planta: (*) </td>
                <td width="88%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" >
                      <input name="planta" type="text" class="campo" id="planta" value="<%=BGF.getPlanta()%>" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../General/lov_planta.jsp')" style="cursor:pointer"></td>
                      <input name="idplanta" type="hidden" id="idplanta" value="<%=BGF.getIdplanta()%>">
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Conta_facturasa:  </td>
                <td width="88%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" >
                        <input name="conta_facturasa" type="text" class="campo" id="conta_facturasa" value="<%=BGF.getConta_facturasa()%>" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../General/lov_contador.jsp')" style="cursor:pointer"></td>
                      <input name="idconta_facturasa" type="hidden" id="idconta_facturasa" value="<%=BGF.getIdconta_facturasa()%>">
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Conta_facturasb:  </td>
                <td width="88%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="conta_facturasb" type="text" class="campo" id="conta_facturasb" value="<%=BGF.getConta_facturasb()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../General/lov_contador2.jsp')" style="cursor:pointer"></td>
                    <input name="idconta_facturasb" type="hidden" id="idconta_facturasb" value="<%=BGF.getIdconta_facturasb()%>">
                  </tr>
                </table>                  </td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Conta_facturasc:  </td>
                <td width="88%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="conta_facturasc" type="text" class="campo" id="conta_facturasc" value="<%=BGF.getConta_facturasc()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../General/lov_contador3.jsp')" style="cursor:pointer"></td>
                    <input name="idconta_facturasc" type="hidden" id="idconta_facturasc" value="<%=BGF.getIdconta_facturasc()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Conta_recibos:  </td>
                <td width="88%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" >
                        <input name="conta_recibos" type="text" class="campo" id="conta_recibos" value="<%=BGF.getConta_recibos()%>" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../General/lov_contador4.jsp')" style="cursor:pointer"></td>
                      <input name="idconta_recibos" type="hidden" id="idconta_recibos" value="<%=BGF.getIdconta_recibos()%>">
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Conta_remitos1:  </td>
                <td width="88%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="conta_remitos1" type="text" class="campo" id="conta_remitos1" value="<%=BGF.getConta_remitos1()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../General/lov_contador5.jsp')" style="cursor:pointer"></td>
                    <input name="idconta_remitos1" type="hidden" id="idconta_remitos1" value="<%=BGF.getIdconta_remitos1()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Conta_remitos2:  </td>
                <td width="88%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="conta_remitos2" type="text" class="campo" id="conta_remitos2" value="<%=BGF.getConta_remitos2()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../General/lov_contador6.jsp')" style="cursor:pointer"></td>
                    <input name="idconta_remitos2" type="hidden" id="idconta_remitos2" value="<%=BGF.getIdconta_remitos2()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Conta_remitos3:  </td>
                <td width="88%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="conta_remitos3" type="text" class="campo" id="conta_remitos3" value="<%=BGF.getConta_remitos3()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../General/lov_contador7.jsp')" style="cursor:pointer"></td>
                    <input name="idconta_remitos3" type="hidden" id="idconta_remitos3" value="<%=BGF.getIdconta_remitos3()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Conta_remitos4:  </td>
                <td width="88%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="conta_remitos4" type="text" class="campo" id="conta_remitos4" value="<%=BGF.getConta_remitos4()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../General/lov_contador8.jsp')" style="cursor:pointer"></td>
                    <input name="idconta_remitos4" type="hidden" id="idconta_remitos4" value="<%=BGF.getIdconta_remitos4()%>">
                  </tr>
                </table></td>
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

