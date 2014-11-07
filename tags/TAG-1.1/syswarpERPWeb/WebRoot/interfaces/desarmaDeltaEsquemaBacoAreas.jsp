<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: produccionEsquemas_Cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Feb 13 09:18:39 GMT-03:00 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="java.math.*" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%//@ include file="session.jspf"%>
<% 
try{
Strings str = new Strings();
String idempresa = str.esNulo( request.getParameter("idempresa")  != null ? request.getParameter("idempresa")  + "" :   session.getAttribute("empresa") + "");;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BSDPE"  class="ar.com.syswarp.web.ejb.BeanINTERFACESDesDeltaEsqBacoAreas"   scope="page"/>
<head>
 <title></title>
 <link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="vs/forms/forms.js"></script>
<script >


</script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BSDPE" property="*" />
 <% 
 String titulo = " DESARMADO DE PRODUCTOS DE STOCK DELTA - BACO" ;
 String usuarioalt = str.esNulo( request.getParameter("usuario")  != null ? request.getParameter("usuario")  + "" :   session.getAttribute("usuario") + "");
 if(idempresa.equals("")) idempresa = "1";
 //if(usuarioalt.equals("")) usuarioalt = "INTERFAZ"; 
 if(usuarioalt.equals("")) response.sendRedirect("caduco.jsp"); 
 

 
 BSDPE.setResponse(response); 
 BSDPE.setRequest(request);
 //BSDPE.setSession(session);
 BSDPE.setUsuarioalt( usuarioalt ); 
 BSDPE.setUsuarioact( usuarioalt );
 BSDPE.setIdempresa( new BigDecimal( idempresa ));
 //BSDPE.setEjercicioactivo( -1 );
 //BSDPE.setIdcontadorcomprobante( new BigDecimal( -1 ));
 BSDPE.ejecutarValidacion(); 
 
 %>
<form action="desarmaDeltaEsquemaBacoAreas.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="" >


<table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center"> 
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
                <td colspan="2" class="fila-det-border"><jsp:getProperty name="BSDPE" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-rojo">&nbsp;</td>
                <td colspan="2" class="fila-det-rojo">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Area Baco(*) </td>
                <td class="fila-det-border"><input name="area" type="text" class="campo" id="area" value="<%=BSDPE.getArea()%>" size="80" maxlength="100"  ></td>
                <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/find.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_bacoAreas.jsp?cmpCod=idarea&cmpDesc=area','interfareas',800,400);">
                    <input name="idarea" type="hidden" id="idarea" value="<%=BSDPE.getIdarea()%>" ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-rojo">&nbsp;</td>
                <td colspan="2" class="fila-det-rojo">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">Esquema: (*) </td>
                <td colspan="2" class="fila-det-border"><input name="esquema" type="text" value="<%=BSDPE.getEsquema()%>" class="campo" size="80" maxlength="80" readonly>
								<input name="idesquema" type="hidden" value="<%=BSDPE.getIdesquema()%>" >								</td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">Producto / Articulo: (*)                </td>
                <td width="51%" class="fila-det-border"><input name="descrip_st" type="text" class="campo" id="descrip_st" value="<%=BSDPE.getDescrip_st()%>" size="80" maxlength="100" readonly ></td>
                <td width="29%" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/find.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_producto_esquema.jsp?idempresa=<%= idempresa %>','interfprod',800,400);">
								<input name="codigo_st" type="hidden" id="codigo_st" value="<%=BSDPE.getCodigo_st()%>" >								</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Dep&oacute;sito:(*)</td>
                <td class="fila-det-border"><input name="descrip_dt" type="text" value="<%=BSDPE.getDescrip_dt()%>" class="campo" size="80" maxlength="100" readonly ></td>
                <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/find.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_depositoDelta.jsp?idempresa=<%= idempresa %>','interfdeposito',800,400);"> 
                <input name="codigo_dt" type="hidden" id="codigo_dt" value="<%=BSDPE.getCodigo_dt()%>" ></td>
              </tr> 
              <tr class="fila-det">
                <td class="fila-det-border">Cantidad: (*) </td>
                <td class="fila-det-border"><input name="cantidad" type="text" class="campo" id="cantidad" value="<%=BSDPE.getCantidad()%>" size="40" maxlength="18"  ></td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Desarma Recursivamente: </td>
                <td colspan="2" class="fila-det-border"><input name="recursivo" type="checkbox" id="recursivo" value="1" <%= BSDPE.getRecursivo() == 1 ? "checked" : "" %>> (<span class="fila-det-rojo">ATENCION</span>: si el esquema esta compuesto por otros esquemas, estos tambi&eacute;n ser&aacute;n desarmados hasta el &uacute;ltimo nivel.)</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Motivo:(*)</td>
                <td colspan="2" class="fila-det-border">
								<select name="idmotivodesarma" class="campo">
								  <option value="-1">Seleccionar</option>
									<%
									  Iterator iterMotivos = BSDPE.getStockMotivosDesarma().iterator();
									  while(iterMotivos.hasNext()){
										  String[] datos = (String[])iterMotivos.next(); 
											  %>
									<option value="<%= datos[0] %>" <%=  datos[0].equals(BSDPE.getIdmotivodesarma().toString()) ? "selected" : ""%>><%= datos[1] %></option>
									<%}  %>
                </select>                </td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">Observaciones:  </td>
                <td colspan="2" class="fila-det-border"><textarea name="observaciones" cols="70" rows="6" class="campo"><%=BSDPE.getObservaciones()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Confirmaci&oacute;n Desarmado </td>
                <td colspan="2" class="fila-det-border"><input name="visto" type="checkbox" id="visto" value="1" onClick="document.frm.validar.disabled = !this.checked"></td>
              </tr> 
              <tr class="fila-det">
                <td height="40" class="fila-det-border">&nbsp;</td> 
                <td colspan="2" class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Confirmar Desarmado" class="boton" disabled onClick="document.frm.accion.value='desarmar'"> </td>
             </tr>
          </table>
       </td>  
     </tr>
  </table>
	 <input name="usuario" type="hidden" id="usuario" value="<%= usuarioalt %>">
	 <input name="idempresa" type="hidden" id="idempresa" value="<%= idempresa %>">	 
</form>
  </body>
</html> 
<% 
 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR ( desarmaDeltaEsquemaBacoAreas.jsp ) : "+ex);   
}%>

