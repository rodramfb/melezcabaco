<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: marketformasdepago
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Mar 11 13:29:00 ART 2008 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Market formas de pago";
// variables de entorno
	String pathskin = "vs/market.css";
	String pathscript = "scripts";
// variables de paginacion
int i = 0;
Iterator iterMarketformasdepago   = null;
int totCol = 4; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("marketUsuario").toString();
%>
<html>
<jsp:useBean id="BMSFP"  class="ar.com.syswarp.web.ejb.BeanMarketSelFPago"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BMSFP" property="*" />
<%
 BMSFP.setResponse(response);
 BMSFP.setRequest(request);
 BMSFP.setUsuarioalt( session.getAttribute("marketUsuario").toString() ); 
 BMSFP.setIdempresa( new BigDecimal( session.getAttribute("marketEmpresa").toString() ));
 BMSFP.ejecutarValidacion();
%>
<head>
<title></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "codigo";
tituCol[1] = "Forma de Pago";
tituCol[2] = "leyenda";
tituCol[3] = "envio datos";
java.util.List Marketformasdepago = new java.util.ArrayList();
Marketformasdepago= BMSFP.getMarketformasdepagoList();
iterMarketformasdepago = Marketformasdepago.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="marketSelFPago.jsp" method="POST" name="frm">

<div align="center"> 
	<p>&nbsp;</p>
	<table width="50%"  border="0" cellspacing="0" cellpadding="0">
		<tr >
		 <td ><jsp:getProperty name="BMSFP" property="mensaje"/>
		   <input name="accion" value="" type="hidden"></td>
		</tr>
	</table>
	<table width="50%" border="0" cellspacing="1" cellpadding="1">
		<tr >
			 <td width="18%" height="46" >&nbsp;</td>
			 <td width="82%" class="fila-head">FORMAS DE PAGO</td>
			</tr>
		 <%
		 int r = 0;
		 while(iterMarketformasdepago.hasNext()){
				++r;
				String[] sCampos = (String[]) iterMarketformasdepago.next(); 
				// estos campos hay que setearlos segun la grilla%>
		 <tr class="fila-det"> 
				<td height="29" ><input type="radio" name="idformapago" value="<%= sCampos[0]%>"></td>
				<td ><%=sCampos[1]%>&nbsp;</td>
			</tr>
	<%
		 }%>
		 <tr > 
				<td height="48" ></td>
				<td ><input name="generarPedido" type="submit" value="Continuar -->" class="boton"></td> 
			</tr>		 
	  </table>
	  <p>&nbsp;</p>
		 <p>&nbsp; </p>
</div>
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

