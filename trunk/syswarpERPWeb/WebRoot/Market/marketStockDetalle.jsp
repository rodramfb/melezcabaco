<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: Stockstock
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Sep 04 09:21:30 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.math.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<% 
try{
	Strings str = new Strings();
	String pathskin = "vs/market.css";
	String pathscript = "scripts";
%>

 <%-- INSTANCIAR BEAN --%>
 <jsp:useBean id="BMSD"  class="ar.com.syswarp.web.ejb.BeanMarketStockDetalle"   scope="page"/>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BMSD" property="*" />
 <link rel="stylesheet" href="vs/Stylesheet.css"/>  
 <% 
 BMSD.setResponse(response);
 BMSD.setRequest(request);
 BMSD.setUsuarioalt( session.getAttribute("marketUsuario").toString() );
 BMSD.setUsuarioact( session.getAttribute("marketUsuario").toString() );
 BMSD.setIdempresa( new BigDecimal( session.getAttribute("marketEmpresa").toString() ));
 BMSD.ejecutarValidacion();
 %>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="34%" valign="top"><jsp:include page="index.jsp" flush="true" />
		</td>
    <td width="66%">
			 <form action="marketStockDetalle.jsp" method="post" name="frmMarketStockDetalle">
			 <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
				 <tr>
					 <td>
						<table class="descripcion">
							<tr >
								<td >&nbsp;</td>
								<td ><jsp:getProperty name="BMSD" property="mensaje"/>&nbsp; </td>
							</tr>
							<tr width="28%" class="fila-det">
								<td class="fila-head"> Código:   </td> 
								<td width="72%" ><%=BMSD.getCodigo_st()%>
								  <input name="codigo_st" type="hidden" value="<%=BMSD.getCodigo_st()%>"  ></td>
								</tr>
							<tr class="fila-det">
							  <td class="fila-head">Alias:  </td>
							  <td ><%=BMSD.getAlias_st()%></td>
							  </tr>
							<tr class="fila-det">
								<td class="fila-head">Descripcion:  </td>
								<td ><%=BMSD.getDescrip_st()%></td>
							</tr>
							<tr class="fila-det">
							  <td class="fila-head">Descripcion Corta: </td>
							  <td ><%=BMSD.getDescri2_st()%></td>
							  </tr>
							<tr class="fila-det">
							  <td class ="fila-head">Precio:</td>
							  <td ><%=BMSD.getPrecipp_st() %></td>
							  </tr>
							<tr class="fila-det">
							  <td >&nbsp;</td>
							  <td >&nbsp;</td> 
							  </tr>
							<tr class="fila-det">
                <td >&nbsp;</td>
							  <td >&nbsp;
                  <input name="agregar" type="submit" value="(+) Agregar al carrito " class="boton"></td>
							  </tr>
						 </table>
					 </td>
				 </tr>
			</table>
			</form>
		</td>
  </tr>
</table>


<% 
 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (" + pagina + ") : "+ex);   
}%>

