<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: globalentidadesasociacionesmov
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed May 07 11:53:08 CEST 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.math.BigDecimal" %>
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
<jsp:useBean id="BGF"  class="ar.com.syswarp.web.ejb.BeanAsociarDocumentosPasoDos"   scope="page"/>
<head>
 <title></title>
<link rel = "stylesheet" href = "<%= pathskin %>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="vs/overlib/overlib.js"></script>
<script>
  var validate = false;
	function confirmarAsociacion(){
	  if(validate){
			if(confirm("ATENCION: Si esta seguro de asociar estos docuemtos,\npresione aceptar, caso contrario cancele la operación,\nya que la misma solo podrá eliminarla un usuario con privilegios. "))
				return true;
			else
				return false;
		}
		return true;
	}
</script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BGF" property="*" />
 <% 
 BGF.setResponse(response);
 BGF.setRequest(request);
 BGF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BGF.setUsuarioact( session.getAttribute("usuario").toString() );
 BGF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BGF.ejecutarValidacion();
 String titulo = "Asociar Movimientos de " + BGF.getEntidadorigen() + " con " + BGF.getEntidaddestino() ; 
 String url = "";
 %>
<form action="asociarDocumentosPasoDos.jsp" method="post" name="frm" onSubmit="javascript:return confirmarAsociacion();">
<input name="accion" type="hidden" value="<%=BGF.getAccion()%>" >
<input name="identidadesasociacionesmov" type="hidden" value="<%=BGF.getIdentidadesasociacionesmov()%>" >
   <span class="fila-det-border">
   <input name="identidadorigen" type="hidden" id="identidadorigen" value="<%=BGF.getIdentidadorigen()%>">
   </span>
   <span class="fila-det-border">
   <input name="identidaddestino" type="hidden" id="identidaddestino" value="<%=BGF.getIdentidaddestino()%>">
   </span>
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
                <td colspan="2" class="fila-det-border"><jsp:getProperty name="BGF" property="mensaje"/>&nbsp;</td>
              </tr>
							<% url = "lov_asociarDocumentosGenerico.jsp?cmpCodigo=pkorigen&cmpDescrip=docOrigen&identidadesasociables=" + BGF.getIdentidadorigen(); %>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">Registro Origen : (*) </td>
                <td width="39%" class="fila-det-border"><input name="docOrigen" type="text" value="<%=BGF.getDocOrigen()%>" class="campo" size="50" maxlength="100"  >
                <input name="pkorigen" type="hidden" value="<%=BGF.getPkorigen()%>" class="campo" size="18" maxlength="18"  ></td>
                <td width="37%" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/find.png" width="18" height="18" style="cursor:pointer" onClick="abrirVentana('<%= url %>', 'generico', 750, 350)"></td>
              </tr>
							<% url = "lov_asociarDocumentosGenerico.jsp?cmpCodigo=pkdestino&cmpDescrip=docDestino&identidadesasociables=" + BGF.getIdentidaddestino(); %>							
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">Registro a Relacionar: (*) </td>
                <td width="39%" class="fila-det-border"><input name="docDestino" type="text" value="<%=BGF.getDocDestino()%>" class="campo" size="50" maxlength="100"  >
                <input name="pkdestino" type="hidden" value="<%=BGF.getPkdestino()%>" class="campo" size="18" maxlength="18"  ></td>
                <td width="37%" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/find.png" width="18" height="18" style="cursor:pointer" onClick="abrirVentana('<%= url %>', 'generico', 750, 350)"></td>
              </tr> 
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">Fecha: (*) </td>
                <td colspan="3" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecha" value="<%=BGF.getFecha()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fecha','BTN_date_6');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_6" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="24%" class="fila-det-border">Observacion:  </td>
                <td colspan="2" class="fila-det-border"><textarea name="observacion" cols="70" rows="6" class="campo" id="observacion"><%=BGF.getObservacion()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton" onClick="validate=true;">               <input name="volver" type="submit" class="boton" id="volver" value="Volver" onClick="validate=false;"></td>
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

