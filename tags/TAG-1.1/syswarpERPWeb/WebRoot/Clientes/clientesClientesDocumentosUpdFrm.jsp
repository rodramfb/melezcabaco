<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesestados
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Mar 02 11:31:57 GMT-03:00 2007 
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
<%@ page import="java.util.*" %>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanClientesDocumentosUpdFrm"   scope="page"/>
<head>
 <title>Actualizar Documentos de Clientes</title>
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
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo = "MODFICACION DE DOCUMENTOS DE CLIENTES" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));    
 BCF.ejecutarValidacion();
 %>
<form action="clientesClientesDocumentosUpdFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="recargar" type="hidden" value="1" >

   <input name="nrodocumentoActualCliente" type="hidden" id="nrodocumentoActualCliente" value="<%=BCF.getNrodocumentoActualCliente()%>" >
   <input name="nrodocumentoActualPrecarga" type="hidden" id="nrodocumentoActualPrecarga" value="<%=BCF.getNrodocumentoActualPrecarga() %>" >
   <input name="tipodocumentoActualCliente" type="hidden" id="tipodocumentoActualCliente" value="<%=BCF.getTipodocumentoActualCliente() %>" >
   <input name="tipodocumentoActualPrecarga" type="hidden" id="tipodocumentoActualPrecarga" value="<%=BCF.getTipodocumentoActualPrecarga() %>" >
   <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="2" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td colspan="4" class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>                  &nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border"><table width="100%" height="22" border="0" cellpadding="0" cellspacing="0" >
                  <tr class="text-dos-bold">
                    <td height="100%">Cliente</td>
                  </tr>
                </table></td>
                <td class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
                    <tr class="text-dos-bold">
                      <td width="10%" ><input name="idcliente" type="text" class="campo" value="<%=BCF.getIdcliente()      %>" size="10" readonly></td>
                      <td width="30%" ><input name="cliente" type="text" class="campo" id="cliente" value="<%=BCF.getCliente()%>" size="30" maxlength="50"  readonly></td>
                      <td width="60%" ><img src="../imagenes/default/gnome_tango/actions/gtk-find.png" width="22" height="22" onClick="abrirVentana('lov_clientes.jsp', 'branch', 750, 450);" style="cursor:pointer"> </td>
                  </tr>
                  </table></td>
                <td colspan="2" class="fila-det-border"> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                    <tr class="text-dos-bold" >
                      <td height="21"><div align="center">Datos Actuales </div></td>
                    </tr>
                  </table>                </td>
              </tr> 
              <tr class="fila-det">
                <td height="21" bgcolor="#CCCCFF" class="fila-det-border">&nbsp;</td>
                <td height="21" bgcolor="#CCCCFF" class="fila-det-border">&nbsp;</td>
                <td bgcolor="#FFFFCC" class="fila-det-border"><table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFCC">
                  <tr class="fila-det-bold">
                    <td bgcolor="#FFFFCC" ><div align="center">Cliente</div></td>
                  </tr>
                </table></td>
                <td bgcolor="#FFFFCC" class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
                  <tr class="fila-det-bold">
                    <td bgcolor="#FFFFCC" ><div align="center">Precarga</div></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="10%" height="42" bgcolor="#CCCCFF" class="fila-det-border">N&uacute;mero: (*) </td>
                <td width="48%" bgcolor="#CCCCFF" class="fila-det-border"><input name="nrodocumento" type="text" value="<%=BCF.getNrodocumento()%>" class="campo" size="15" maxlength="50"  ></td>
                <td width="20%" bgcolor="#FFFFCC" class="fila-det-border"><div align="center"><%= BCF.getNrodocumentoActualCliente() + "" %>&nbsp;</div></td>
                <td width="22%" bgcolor="#FFFFCC" class="fila-det-border"><div align="center"><%= BCF.getNrodocumentoActualPrecarga() + "" %>&nbsp;</div></td>
              </tr>
              <tr class="fila-det">
                <td width="10%" height="44" bgcolor="#CCCCFF" class="fila-det-border">Tipo: (*) </td>
                <td width="48%" bgcolor="#CCCCFF" class="fila-det-border">
				<select name="idtipodocumento" id="idtipodocumento" class="campo" style="width:200px" >
                  <option value="-1">Seleccionar</option>
                  <%
                   Iterator iter = BCF.getListTiposDoc().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                    <option value="<%= datos[0] %>" <%=  BCF.getIdtipodocumento().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                  <% 
                   } %>
                </select>				</td>
                <td width="20%" bgcolor="#FFFFCC" class="fila-det-border"><div align="center"><%= BCF.getTipodocumentoActualCliente() + "" %>&nbsp;</div></td>
                <td width="22%" bgcolor="#FFFFCC" class="fila-det-border"><div align="center"><%= BCF.getTipodocumentoActualPrecarga() + "" %>&nbsp;</div></td>
              </tr>
              <tr class="fila-det">
                <td height="42" bgcolor="#CCCCFF" class="fila-det-border">&nbsp;</td>
                <td bgcolor="#CCCCFF" class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Actualizar" class="boton"></td>
                <td colspan="2" bgcolor="#FFFFCC" class="fila-det-border">&nbsp;</td>
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

