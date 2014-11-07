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

%>

 <%-- INSTANCIAR BEAN --%>
 <jsp:useBean id="BMC"  class="ar.com.syswarp.web.ejb.BeanMarketCarrito"   scope="page"/> 
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BMC" property="*" />
  <link rel="stylesheet" href="vs/Stylesheet.css"/>  
 <% 
 BMC.setResponse(response);
 BMC.setRequest(request);
 BMC.setUsuarioalt( session.getAttribute("marketUsuario").toString() );
 BMC.setUsuarioact( session.getAttribute("marketUsuario").toString() );
 BMC.setIdempresa( new BigDecimal( session.getAttribute("marketEmpresa").toString() ));
 BMC.ejecutarValidacion();
 %>
  <script>
	function setAccion( accion ){
	 
	  document.frmMarketCarrito.accion.value = accion;
		document.frmMarketCarrito.submit();
	
	}
	</script>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="34%" valign="top"><jsp:include page="index.jsp" flush="true" />
		</td>
    <td width="66%">
			 <form action="marketCarrito.jsp" method="post" name="frmMarketCarrito">
			 <table class="descripcion">
				 <tr>
				  <td><table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
							<tr >
							  <td >&nbsp;</td>
								<td >&nbsp;</td>
								<td class="mensaje"><jsp:getProperty name="BMC" property="mensaje"/>&nbsp;</td>
							  <td >&nbsp;</td>
							  <td >&nbsp;</td>
							</tr>
							<tr class="fila-head">
							  <td width="6%" ><input name="accion" type="hidden" id="accion"></td>
								<td width="7%" height="22" >C&oacute;digo</td>
								<td width="64%" >Descripci&oacute;n</td>
								<td width="12%" ><div align="center">Cantidad</div></td>
								<td width="11%" ><div align="right">Precio</div></td>
							</tr>
								
							<%
							BigDecimal totalPagina = new BigDecimal(0);
							BigDecimal totalProductos = new BigDecimal(0);
							Hashtable htCarrito =  (Hashtable) session.getAttribute("htCarrito");
							if(htCarrito != null && !htCarrito.isEmpty()){
								Enumeration e = htCarrito.keys();
								while(e.hasMoreElements()){
								  String clave = e.nextElement().toString();
									String [] datos = (String[])htCarrito.get(clave);
									totalPagina = totalPagina.add(new BigDecimal(datos[5]));
									totalProductos = totalProductos.add(new BigDecimal(datos[4]));
							  %>
							<tr >
							  <td ><div align="center">
							    <input type="checkbox" name="seleccion" value="<%= datos[0] %>">
							  </div></td>
							  <td height="22" class="fila-det"><%= datos[0] %>&nbsp;</td>
							  <td class="fila-det"><%= datos[2] %>&nbsp;</td>
							  <td class="fila-det"><div align="center">
							    <input name="keyRecalcular" type="hidden" id="keyRecalcular" value="<%= datos[0] %>">
								<input name="cantidad" type="text" id="cantidad" size="4" maxlength="4" value="<%= datos[4] %>" class="campo"> 
							    </div></td>
							  <td class="fila-det" ><div align="right"><%= datos[5] %></div></td>  
							</tr>
						
							<tr>						  </tr>

						<%  }
	            }					
						%>
					
					</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
							  <td width="87%" height="10" class="fila-det"><div align="right" >Cant.: <%= totalProductos %>&nbsp;</div></td>
							  <td width="13%" height="34" class="fila-det"><div align="right" >Subtotal: $<%= totalPagina %>&nbsp;</div></td>
						  </tr>
							<tr>
							  <td height="34"><input name="recalcular" type="button" id="recalcular" value="Recalcular" onClick="setAccion(this.name);" class="boton">
							    <input name="caja" type="button" id="caja" value="$$ Ir A Caja" onclick="setAccion(this.name);" class="boton" /> 
							    <input name="eliminar" type="button" id="eliminar" value="(-) Eliminar Seleccionados" onclick="setAccion(this.name);" class="boton" />
							    <input name="limpiar" type="button" id="limpiar" value="(-) Limpiar Carro" onclick="setAccion(this.name);" class="boton" /></td>
						  </tr>
							<tr>
							  <td height="32">&nbsp;</td>
							</tr>
							<tr>
								<td height="32">&nbsp;</td>
						  </tr>
							<tr>
							  <td height="33">&nbsp;</td>
						  </tr>
						</table>				   </td>
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

