<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: bacotmliquidacionresumen
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Mar 02 15:34:16 ART 2011 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Liquidiación de telemarketers";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterBacotmliquidacionresumen   = null;
int totCol = 15; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BBA"  class="ar.com.syswarp.web.ejb.BeanBacotmliquidacionresumenAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BBA" property="*" />
<%
 BBA.setResponse(response);
 BBA.setRequest(request);
 BBA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BBA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="scripts/calendar/calendarcode.js"></script> 
 <link rel="stylesheet" type="text/css" href="scripts/calendar/calendar.css"> 
<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="vs/overlib/overlib.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Campaña";
tituCol[2] = "Telemarketer";
tituCol[3] = "Com. vtas. campaña";
tituCol[4] = "Total vtas. campaña";
tituCol[5] = "Com. vtas. varios";
tituCol[6] = "Total vtas varios.";
tituCol[7] = "Com. vtas. especiales";
tituCol[8] = "Total vtas. especiales";
tituCol[9] = "Total comisiones";
java.util.List Bacotmliquidacionresumen = new java.util.ArrayList();
Bacotmliquidacionresumen= BBA.getBacotmliquidacionresumenList();
iterBacotmliquidacionresumen = Bacotmliquidacionresumen.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>
<form action="bacotmliquidacionresumenAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="fila-det-verde"> 
	<tr>
	   <td height="24" colspan="4"  class="text-globales"><%=titulo%></td>
	</tr>
  <tr class="text-globales">
  	<td> Campaña:<select name="idcampania" id="idcampania" class="campo" >
					<option value="-1">Seleccionar</option>
					<%
						Iterator itercampania = BBA.getListaCampanias().iterator();
						while(itercampania.hasNext())
						{
							String[] datoscampania =(String[]) itercampania.next();%>
							<option value="<%=datoscampania[0]%>"<%= datoscampania[0].equalsIgnoreCase(BBA.getIdcampania().toString()) ? "selected":""%>><%=datoscampania[1]%></option>
						<%
						}
					%>
					</select>
	  </td>
					
	<td> Telemarketer:<select name="vendedor" id="vendedor" class="campo" style="width:150px" >
                  <option value="-1" >Seleccionar</option>
                  <%
					 Iterator itervendedor = BBA.getListaVendedores().iterator();
					  while(itervendedor.hasNext()){
					  String [] datos = (String[])itervendedor.next();%>
					<option value="<%= datos[1] %>"<%= datos[1].equalsIgnoreCase(BBA.getVendedor()) ? "selected":""%>> <%= datos[1] %> </option>
					<%}%>
		</select>
        
	</td>
	<td>
		<input name="consulta" type="submit" class="boton" value="Consulta" onClick="document.frm.accion.value = this.name" >
	</td>
  </tr>
  <tr>
  	<td colspan="4"></td>
  </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BBA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
  	<td colspan="5"></td>
	<td colspan="3" align="center">Comisiones</td>
	<td colspan="4" align="center">Totales</td>
  </tr>
  <tr class="fila-encabezado">
     <td colspan="2" >&nbsp;</td>
	 <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
	 <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
	 <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="13%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
	 <td width="13%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
	 <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>
 </tr>
<!-- 

    <td width="10%" onClick="javascript:sortTable(< %=totCol-4%>, rsTable);">< %=tituCol[10]%></td>
     <td width="10%" onClick="javascript:sortTable(< %=totCol-4%>, rsTable);">< %=tituCol[11]%></td>
     <td width="10%" onClick="javascript:sortTable(< %=totCol-4%>, rsTable);">< %=tituCol[12]%></td>
     <td width="10%" onClick="javascript:sortTable(< %=totCol-4%>, rsTable);">< %=tituCol[13]%></td>
     <td width="10%" onClick="javascript:sortTable(< %=totCol-4%>, rsTable);">< %=tituCol[14]%></td>-->

   <%int r = 0;
   while(iterBacotmliquidacionresumen.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterBacotmliquidacionresumen.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td width="3%" height="24" class="fila-det-border" ><input type="radio" name="idliquidacion" value="<%= sCampos[0]%>"></td>
       <td width="2%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" onClick="mostrarLOV('lov_tmliquidacion.jsp')" style="cursor:pointer"></br></td>
	  <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[6]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[8]%>&nbsp;</td>
	  <%
	  	BigDecimal sumatoria = new BigDecimal(sCampos[4]).add(new BigDecimal(sCampos[6])).add(new BigDecimal(sCampos[8]));
	  %>
	  <td class="fila-det-border" ><%=sumatoria%>&nbsp;</td>
<!--        <td class="fila-det-border" >&nbsp;< %=sCampos[10]%></td>
    <td class="fila-det-border" >&nbsp;< %=sCampos[11]%></td>
      <td class="fila-det-border" >&nbsp;< %=sCampos[12]%></td>
      <td class="fila-det-border" >&nbsp;< %=sCampos[13]%></td>
      <td class="fila-det-border" >&nbsp;< %=sCampos[14]%></td>-->
   </tr>
<%
   }%>
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

