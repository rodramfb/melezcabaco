<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: rrhhpersonal
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Apr 22 09:44:28 ACT 2009 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%@ page import="java.math.*" %>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Liquidación de Sueldos y Jornales";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
int totCol = 5; // cantidad de columnas 
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BRA"  class="ar.com.syswarp.web.ejb.BeanRrhhLiquidacionSueldosJornales"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BRA" property="*" />
<%
 BRA.setResponse(response);
 BRA.setRequest(request);
 BRA.setUsuarioalt( session.getAttribute("usuario").toString() );
 BRA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BRA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script type="text/javascript">
 function mostrarLOV(pagina) {
   frmLOV = open(pagina, 'winLOV', 'menubar=no,status=no,toolbar=no,location=no,scrollbars=yes,resizable=yes,width=700,height=400,left=20, top=20');
   if (frmLOV.opener == null) {
	  frmLOV.opener = self;
   }
}
 </script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="rrhhLiquidacionSueldosJornales.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="color-tabletrim">
	<tr class="text-globales">
		<td>
        	<table width="100%" border="0">
        		<tr>
                	<td  class="text-globales"><%=titulo%></td>
                   	<td>&nbsp;</td>
               	</tr>
        		<tr>
        			<td class="text-globales">Año a liquidar <input type = "text" name="anioliquidacion" value="<%=BRA.getAnioliquidacion()%>" class="campo" size="1" maxlength="4"> 
        			Mes a liquidar 
        			<select name="mesliquidacion">
        				<option value="-1" >Seleccionar</option>
        				<option value="1" <%=BRA.getMesliquidacion().equalsIgnoreCase("1") ? "selected" : ""%>>ENERO</option>
        				<option value="2" <%=BRA.getMesliquidacion().equalsIgnoreCase("2") ? "selected" : ""%>>FEBRERO</option>
        				<option value="3" <%=BRA.getMesliquidacion().equalsIgnoreCase("3") ? "selected" : ""%>>MARZO</option>
        				<option value="4" <%=BRA.getMesliquidacion().equalsIgnoreCase("4") ? "selected" : ""%>>ABRIL</option>
        				<option value="5" <%=BRA.getMesliquidacion().equalsIgnoreCase("5") ? "selected" : ""%>>MAYO</option>
        				<option value="6" <%=BRA.getMesliquidacion().equalsIgnoreCase("6") ? "selected" : ""%>>JUNIO</option>
        				<option value="7" <%=BRA.getMesliquidacion().equalsIgnoreCase("7") ? "selected" : ""%>>JULIO</option>
        				<option value="8" <%=BRA.getMesliquidacion().equalsIgnoreCase("8") ? "selected" : ""%>>AGOSTO</option>
        				<option value="9" <%=BRA.getMesliquidacion().equalsIgnoreCase("9") ? "selected" : ""%>>SEPTIEMBRE</option>
        				<option value="10"<%=BRA.getMesliquidacion().equalsIgnoreCase("10") ? "selected" : ""%>>OCTUBRE</option>
        				<option value="11"<%=BRA.getMesliquidacion().equalsIgnoreCase("11") ? "selected" : ""%>>NOVIEMBRE</option>
        				<option value="12"<%=BRA.getMesliquidacion().equalsIgnoreCase("12") ? "selected" : ""%>>DICIEMBRE</option>
        				
        				<!--<option value="3" >MARZO</option>
        				<option value="4" < %=Integer.parseInt(BRA.getMesliquidacion()) ==  4 ? "selected" : "" %>>ABRIL</option>
        				<option value="5" < %=Integer.parseInt(BRA.getMesliquidacion()) ==  5 ? "selected" : "" %>>MAYO</option>
        				<option value="6" < %=Integer.parseInt(BRA.getMesliquidacion()) ==  6 ? "selected" : "" %>>JUNIO</option>
        				<option value="7" < %=Integer.parseInt(BRA.getMesliquidacion()) ==  7 ? "selected" : "" %>>JULIO</option>
        				<option value="8" < %=Integer.parseInt(BRA.getMesliquidacion()) ==  8 ? "selected" : "" %>>AGOSTO</option>
        				<option value="9" < %=Integer.parseInt(BRA.getMesliquidacion()) ==  9 ? "selected" : "" %>>SEPTIEMBRE</option>
        				<option value="10"< %=Integer.parseInt(BRA.getMesliquidacion()) ==  10 ? "selected" : "" %>>OCTUBRE</option>
        				<option value="11"< %=Integer.parseInt(BRA.getMesliquidacion()) ==  11 ? "selected" : "" %>>NOVIEMBRE</option>
        				<option value="12"< %=Integer.parseInt(BRA.getMesliquidacion()) ==  12 ? "selected" : "" %>>DICIEMBRE</option>
        			--></select> 
        			<input name="ir" type="submit" class="boton" id="ir" value="  >>  ">
        			</td>
        			
                	<!--<td height="26" class="text-globales">Legajo desde</td>
					<td>
                    	<input name="desdeLegajo" type="text" value="< %=BRA.getDesdeLegajo()%>" readonly="readonly" size="30" maxlength="30">
                    	<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_personal_desde.jsp')" style="cursor:pointer">
                   	</td>
                   	<td height="26" class="text-globales">Legajo hasta</td>
					<td>
                    	<input name="hastaLegajo" type="text" value="< %=BRA.getHastaLegajo()%>" readonly="readonly"  size="30" maxlength="30">
                    	<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_personal_hasta.jsp')" style="cursor:pointer">
                   	</td>
                    -->
                </tr>
          </table>
      </td>
    </tr>
</table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BRA" property="mensaje"/></td>
  </tr>
</table>
</table>
   <input name="accion" value="" type="hidden">
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

