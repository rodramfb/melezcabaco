<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: Stockgrupos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Sep 04 09:20:45 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*"%>
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
	Strings str = new Strings();
	Iterator iterStockgrupos   = null;
%>
<jsp:useBean id="BMSG"  class="ar.com.syswarp.web.ejb.BeanMarketSeleccionGrupos" scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BMSG" property="*" />

<%
 String pathskin = "vs/market.css";
 BMSG.setResponse(response);
 BMSG.setRequest(request);
 BMSG.setIdempresa( new BigDecimal( session.getAttribute("marketEmpresa").toString() ));
 BMSG.ejecutarValidacion();
%>

<script>
function enviarFormulario(){
  var action = 'marketSeleccionGrupo.jsp';
	for(var i=0;i<document.frmSeleccionGrupo.codigo_gr.length;i++){
		if(document.frmSeleccionGrupo.codigo_gr.options[i].selected){ 
			action = 'marketStockXGrupo.jsp' ;
			break;
		}
	}
	
	document.frmSeleccionGrupo.accion.value = 'buscarStockXGrupo';
	document.frmSeleccionGrupo.action = action;
	document.frmSeleccionGrupo.submit();
}  
</script>
<html>
<link href="vs/Stylesheet.css" rel="stylesheet" type="text/css" />
	<body>
		<table width="100%" height="100%">
			<tr>
				<td class="panel" valign="top"><jsp:include page="index.jsp" flush="true" /></td>
				<td valign="top" class="descripcion">
						<form action="marketStockXGrupo.jsp" method="POST" name="frmSeleccionGrupo"><select name="codigo_gr" size="6"   class="lista" id="codigo_gr"  >
						<%
						java.util.List Stockgrupos = new java.util.ArrayList();
						Stockgrupos= BMSG.getStockgruposList();
						iterStockgrupos = Stockgrupos.iterator();
							while(iterStockgrupos.hasNext()){ 
								String[] sCampos = (String[]) iterStockgrupos.next();%>
								<option value="<%=sCampos[0]%>"  ><%=sCampos[1]%></option>
								 <%}%>
								</select>
						  <input name="buscarStockXGrupo" type="button" id="buscarStockXGrupo" value="Buscar" onclick="enviarFormulario();" class="boton" />
						  <input type="hidden" name="codigo_fm" id="codigo_fm" value="<%= BMSG.getCodigo_fm() %>" />
						  <input type="hidden" name="accion" id="accion" value="" />
					  </form>
			  </td>	
			</tr>
		</table>
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