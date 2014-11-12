<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: tickets_proyectos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Feb 25 11:21:53 ART 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
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
<jsp:useBean id="BTF"  class="ar.com.syswarp.web.ejb.BeanTickets_proyectosFrm"   scope="page"/>
<head>
 <title>FRMTickets_proyectos.jsp</title>
   <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script></head></head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BTF" property="*" />
 <% 
 String titulo = BTF.getAccion().toUpperCase() + " DE Tickets Proyectos" ;
 BTF.setResponse(response);
 BTF.setRequest(request);
 BTF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BTF.setUsuarioact( session.getAttribute("usuario").toString() );
 BTF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BTF.ejecutarValidacion();
 %>
<form action="tickets_proyectosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BTF.getAccion()%>" >
<input name="idproyecto" type="hidden" value="<%=BTF.getIdproyecto()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BTF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Nombre: (*) </td>
                <td width="88%" class="fila-det-border"><textarea name="nombre" cols="70" rows="6" class="campo"><%=BTF.getNombre()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Descripcion: (*) </td>
                <td width="88%" class="fila-det-border"><textarea name="description" cols="70" rows="6" class="campo"><%=BTF.getDescription()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Estado: (*) </td>
                <td width="88%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="estado" type="text" class="campo" id="estado" value="<%=BTF.getEstado()%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../General/lov_estado.jsp')" style="cursor:pointer"></td>
<input name="idestado" type="hidden" id="idestado" value="<%=BTF.getIdestado()%>">
</tr>
</table></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Activo: (*) </td>
                <td width="88%" class="fila-det-border"><select name="activo" id="activo"  class="campo">
<option value="S" <%= BTF.getActivo().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
<option value="N" <%= BTF.getActivo().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
</select></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Estado vista: (*) </td>
                <td width="88%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="desc_estadovista" type="text" class="campo" id="desc_estadovista" value="<%=BTF.getDesc_estadovista()%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../General/lov_estado2.jsp')" style="cursor:pointer"></td>
<input name="idestadovista" type="hidden" id="idestadovista" value="<%=BTF.getIdestadovista()%>">
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

