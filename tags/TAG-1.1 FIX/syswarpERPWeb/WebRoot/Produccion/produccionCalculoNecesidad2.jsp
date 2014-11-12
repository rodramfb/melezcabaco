<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: 
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Feb 13 09:18:39 GMT-03:00 2007 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.math.*"%> 
<%@ page import="ar.com.syswarp.api.*" %>
<%@ page import="ar.com.syswarp.ejb.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes

Strings str = new Strings();
GeneralBean gb = new GeneralBean();

String color_fondo ="";
String titulo = "CALCULO DE NECESIDAD DE PRODUCCION";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterProduccionEsquemas   = null;
int totCol = 5; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPCN"  class="ar.com.syswarp.web.ejb.BeanProduccionCalculoNecesidad"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPCN" property="*" />
<%
 BPCN.setResponse(response);
 BPCN.setRequest(request);
 BPCN.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPCN.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel = "stylesheet" href = "<%= pathskin %>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "";
tituCol[1] = "";
tituCol[2] = "";
tituCol[3] = "";
tituCol[4] = "";
java.util.List ProduccionEsquemas = new java.util.ArrayList();
ProduccionEsquemas= BPCN.getProduccionEsquemasList();
iterProduccionEsquemas = ProduccionEsquemas.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="produccionCalculoNecesidad2.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td  class="text-globales">
									 <%=titulo%>
									 <hr color="#FFFFFF">
									 </td> 
                </tr>
                <tr>
                  <td width="13%" height="38"><table width="100%" border="0">
                    <tr>
                      <td width="13%" height="26" class="text-globales">Articulo</td>
                      <td width="6%"><input name="codigo_st" type="text" class="campo" id="codigo_st" size="10" maxlength="10" value="<%= BPCN.getCodigo_st() %>" readonly > </td>
                      <td width="33%"><input name="descrip_st" type="text" class="campo" id="descrip_st" size="60" maxlength="150" value="<%= BPCN.getDescrip_st() %>" readonly> </td>
                      <td width="48%"><img src="../imagenes/default/gnome_tango/actions/find.png" width="22" height="22" onClick="abrirVentana('lov_articulosProduccion.jsp','LAP', 700, 400);" style="cursor:pointer"></td>
                    </tr>
                    <tr>
                      <td height="26" class="text-globales">Cantidad</td>
                      <td><input name="cantidad" type="text" class="campo" id="cantidad" size="10" maxlength="10"  value="<%= BPCN.getCantidad() %>"></td>
                      <td><input name="validar" type="submit" class="boton" id="validar" value="  Calcular "></td>
                      <td>&nbsp;</td>
                    </tr>
                  </table>								
								</td> 
              </tr>
          </table>
      </td>
    </tr>
  </table>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr >
		 <td class="fila-det-bold-rojo"><jsp:getProperty name="BPCN" property="mensaje"/></td>
		</tr>
	</table>
   <%
	 //idesquema,esquema,codigo,descripcion,tipo,descripciontipo,
	 //6 costo, 7 costo*3 AS costototal,
   //8 cantidadinsume, 9 cantidadinsume*3 AS cantidadtotalinsume, 10 cantiddeposito, 11 cantiddeposito  AS remanente, 1 AS nivel
	 int r = 0;
	 boolean esPrimero = true;
	 BigDecimal costoTotal = new BigDecimal(0);
   while(iterProduccionEsquemas.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterProduccionEsquemas.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>

			<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
			<% 
			 if(esPrimero ){
			   esPrimero = false;
			 %>
			
				<tr class="text-diez">
				  <td   >&nbsp;<%=str.getNivelStr("-", Integer.parseInt(sCampos[12]) ) + " > " + sCampos[0] + " - " + sCampos[1].toUpperCase()%></td>
			  </tr>
			<%} %>
			
			
			<% 
			 if( sCampos[4].equalsIgnoreCase("E")){
			 %>
			
				<tr class="text-dos">
				  <td  >&nbsp;<%=str.getNivelStr("-", Integer.parseInt(sCampos[12]) * 2) + " > " + sCampos[2] + " - " + sCampos[3].toUpperCase()%></td>
			  </tr>
			<%
			   continue;
			 } %>
				 <tr > 
						<td class="fila-det-border" >
							<table width="100%"  border="1" cellspacing="0" cellpadding="0">
								<tr class="fila-det-bold"  >
								  <td ><%= str.getNivelStr("-", Integer.parseInt(sCampos[12]) * 2) + " > " +  sCampos[0] %></td>
								  <td colspan="2" ><div align="center">Insume</div></td>
							    <td colspan="2" ><div align="center">Stock</div></td>
						    </tr>
								<tr>
								<td width="48%" height="21" class="fila-det"> Tipo: <%=sCampos[5]%> (<%=sCampos[4]%>) </td>
								  <td width="13%" class="fila-det"><div align="center">Parcial</div></td>
<td width="13%" class="fila-det"><div align="center">Total</div></td>
							    <td width="14%" class="fila-det"><div align="center">Existente</div></td>
								  <td width="12%" class="fila-det"><div align="center">Excedente</div></td>
							  </tr>
								<tr>
									<td class="fila-det">C&oacute;digo: <%=sCampos[2]%></td> 
							    <td class="fila-det"><div align="right"><%=sCampos[8]%></div></td>
<td width="13%" class="fila-det"><div align="right"><%=sCampos[9]%></div></td>
							    <td width="14%" class="<%=Double.parseDouble(sCampos[10])<0 ? "fila-det-bold-rojo": "fila-det" %>"><div align="right"><%=sCampos[10]%></div></td>
								  <td width="12%" class="<%=Double.parseDouble(sCampos[11])<0 ? "fila-det-bold-rojo": "fila-det" %>"><div align="right"><%=sCampos[11]%></div></td>
							  </tr>							
								<tr class="fila-det">
									<td class="fila-det">Descripci&oacute;n: <%=sCampos[3]%></td>
								  <td colspan="4" bgcolor="#006666" >&nbsp;</td>
							  </tr>
							</table>
						</td>
				 </tr>
	</table>	 
<%
     costoTotal = costoTotal.add(new BigDecimal(sCampos[7]));
   }
	 if(!esPrimero){
	 %>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
			 <tr > 
			 <td class="fila-det-border" >&nbsp;</td>
			 </tr>
		</table>	
<%  
	 }
%>


		<input name="accion" value="" type="hidden">
		<input name="idesquema" value="<%= BPCN.getIdesquema() %>" type="hidden">

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

