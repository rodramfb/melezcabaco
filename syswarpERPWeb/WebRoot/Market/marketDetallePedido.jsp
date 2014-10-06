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
<%@ page import="java.util.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<% 
try{
	Strings str = new Strings();
	String pathskin = "vs/market.css";
	String pathscript = "scripts";
	Hashtable htDireccion =  (Hashtable) session.getAttribute("htDireccion");
%>
<link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<jsp:useBean id="BMDP"  class="ar.com.syswarp.web.ejb.BeanMarketDetallePedido"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BMDP" property="*" />
<%
 BMDP.setResponse(response);
 BMDP.setRequest(request);
 BMDP.setUsuarioalt( session.getAttribute("marketUsuario").toString() ); 
 BMDP.setIdempresa( new BigDecimal( session.getAttribute("marketEmpresa").toString() ));
 BMDP.setIdpedicabe( new BigDecimal( session.getAttribute("ordenCarrito").toString() ));
 BMDP.ejecutarValidacion(); 
%>
  <div align="center">
	<table width="80%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="47%"  class="fila-head">ORDEN # <%= session.getAttribute("ordenCarrito") %></td> 
    </tr>
  <tr>
    <td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="fila-det">
      <tr>
        <td class="fila-head">Facturar A: </td>
        <td>&nbsp;</td>
        <td width="15%" class="fila-head">Enviar A: </td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td width="15%" class="fila-head">Nombre y Apellido: </td>
        <td width="35%"><%= BMDP.getNombreFact() %> <%= htDireccion.get("apellidoFact") %></td>
        <td class="fila-head">Nommbre y Apellido: </td>
        <td width="35%"><%= BMDP.getNombreEnv() %>&nbsp; <%= htDireccion.get("apellidoEnv") %></td>
      </tr>
      <tr>
        <td class="fila-head">Empresa:</td>
        <td><%= BMDP.getEmpresaFact() %>&nbsp;</td>
        <td class="fila-head">Empresa:</td>
        <td><%= BMDP.getEmpresaEnv() %>&nbsp;</td>
      </tr>
      <tr>
        <td class="fila-head">Email:</td>
        <td><%= session.getAttribute("emailCarrito") %>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td class="fila-head">Telefono:</td>
        <td><%= BMDP.getTelefonoFact() %>&nbsp;</td>
        <td class="fila-head">Telefono:</td>
        <td><%= BMDP.getTelefonoEnv() %>&nbsp;</td>
      </tr>
      <tr>
        <td class="fila-head">Fax:</td>
        <td><%= BMDP.getFaxFact() %>&nbsp;</td>
        <td class="fila-head">Fax:</td>
        <td><%= BMDP.getFaxEnv() %>&nbsp;</td>
      </tr>
      <tr>
        <td class="fila-head">Direccion:</td>
        <td><%= BMDP.getDireccionFact() %>&nbsp;</td>
        <td class="fila-head">Direccion:</td>
        <td><%= BMDP.getDireccionEnv() %>&nbsp;</td>
      </tr>
      <tr>  
        <td class="fila-head">Ciudad, Provincia, CP : </td>
        <td><%= BMDP.getCiudadFact() %>, <%= BMDP.getProvinciaestadoFact() %>, <%= BMDP.getCodigopostalFact() %></td>
        <td class="fila-head">Ciudad, Provincia, CP :</td>
        <td><%= BMDP.getCiudadEnv() %>, <%= BMDP.getProvinciaestadoEnv() %>, <%= BMDP.getCodigopostalEnv() %></td>
      </tr>
      <tr>
        <td class="fila-head">Pais:</td>
        <td><%= BMDP.getPaisFact() %></td>
        <td class="fila-head">Pais:</td>
        <td><%= BMDP.getPaisEnv()%></td>
      </tr>
      <tr>
        <td class="fila-head">Formas de Pago: </td>
        <td><%= BMDP.getFormadepago() %>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td class="fila-head">Medio de Env&iacute;o: </td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    </tr>
</table>  

	<table width="80%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="66%">   
			 <form action="marketDetallePedido.jsp" method="post" name="frmMarketCarrito">
			 <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center"  class="fila-det">
				 <tr>
					 <td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
							<tr >
							  <td >&nbsp;</td>
							  <td >&nbsp;</td>
							  <td >&nbsp;</td>
							</tr>
							<tr >
							  <td width="70%" height="22" class="fila-head" >Art&iacute;culo</td>
								<td width="10%" class="fila-head" ><div align="right">Cantidad</div></td>
								<td width="7%" class="fila-head" ><div align="right">Precio</div></td>
							</tr>
							<%
							Iterator iter =  BMDP.getListDetallePedido().iterator();
							BigDecimal total = new BigDecimal(0);
							BigDecimal subTotal = new BigDecimal(0);
							BigDecimal gastosEnvio = new BigDecimal(10);
							BigDecimal precio = new BigDecimal(0);
							BigDecimal cantidad = new BigDecimal(0);
							if(iter != null){
								
								while(iter.hasNext()){
									String [] datos = (String[])iter.next();
									cantidad = new BigDecimal(datos[5]);
									precio =  new BigDecimal(datos[6]);
									BigDecimal totalArticulo = precio.multiply(cantidad);
								  subTotal = subTotal.add(totalArticulo); 
							  %>   
							<tr class="fila-det">
							  <td height="22" > <%= datos[2] %> - <%= datos[3] %> &nbsp;</td> 
							  <td >
							    <div align="right"><%= datos[5] %>
							      </div></td>
							  <td ><div align="right"><%= totalArticulo %></div></td> 
							</tr>

						<%  }
	            }					
						%>
						 </table> 

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fila-head">
								  <div align="right">Sub-Total</div></td>
								<td class="fila-head">
								  <div align="right">Gastos de envio</div></td>
								<td><div align="right">&nbsp;</div></td>
								<td class="fila-head">
								  <div align="right">Total</div></td>
							</tr>
							<tr class="fila-head">
								<td>
								  <div align="right">$ <%= subTotal %></div></td>
								<td>
								  <div align="right">$ <%= gastosEnvio %></div></td>
								<td>
								  <div align="right">&nbsp;
								    <%//=  %>
								    </div></td>
								<td>
								  <div align="right">$ <%= subTotal.add(gastosEnvio) %></div></td>
							</tr>
						</table>
						 
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
							  <td><input name="enviarPedido" type="submit" id="enviarPedido" value="Continuar -->" class="boton"></td>
							  </tr>
						</table>
					 
					 </td> 
				 </tr> 
			</table>
			</form>
		</td>
  </tr>
</table> 
</div>
<% 
 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw); 
  System.out.println("ERROR (" + pagina + ") : "+ex);   
}%>

