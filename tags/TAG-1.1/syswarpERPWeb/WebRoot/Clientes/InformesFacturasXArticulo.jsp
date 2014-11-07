<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: clientesClientes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Feb 12 11:19:57 ART 2008 
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
String titulo = "Informes de Facturas Por Artículo";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterFacturasArticulos   = null;
int totCol = 7; // cantidad de columnas
String[] tituCol = new String[totCol]; 
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCCA"  class="ar.com.syswarp.web.ejb.BeanClientesInformesFacturaArticulo"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCCA" property="*" />
<%
 tituCol[0] = "Nro. Comprobante"; 
 tituCol[1] = "Tipo Comprobante";
 tituCol[2] = "Fecha Comprob.";
 tituCol[3] = "Cód. Cliente";
 tituCol[4] = "Razon Social";
 tituCol[5] = "Moneda";
 tituCol[6] = "Cantidad";
 BCCA.setResponse(response);
 BCCA.setRequest(request);
 BCCA.setIdempresa(new BigDecimal(session.getAttribute("empresa").toString()));
 BCCA.setTotCol(totCol);
 BCCA.setTitulos(tituCol);
 BCCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="scripts/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <script language="JavaScript">
 function mostrarLOVDETA(pagina) {
	frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width=800,height=450,status=yes');
	if (frmLOV.opener == null) 
		frmLOV.opener = self;
 }	
 
 function elementos(){
   for(var i=0;i<document.frm.elements.length;i++) alert(document.frm.elements[i].name);
 }
 
 window.onload = function(){
   //elementos();
 };
 </script>
</head>
<%
// titulos para las columnas

java.util.List facturaArticulo = new java.util.ArrayList();
facturaArticulo= BCCA.getFacturasArticuloList();
iterFacturasArticulos = facturaArticulo.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >

<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="InformesFacturasXArticulo.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim> 
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td  class="text-globales"><%=titulo%></td>
                   <td>&nbsp;</td>
                </tr>
                <tr>
                   <td >
                      <table border="0">
                         <tr>
                           <td class="text-globales">Seleccionar Articulo:</td>
                           <td>
                           		<input type = "text" name="codigo_st" value ="<%=BCCA.getCodigo_st()%>" size="8" readonly="readonly"/>
                           		<input type = "text" name="descrip_st" value ="<%=BCCA.getDescrip_st()%>" size="30" readonly="readonly" />
                              	<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22"  onClick="ventana=abrirVentana('lov_articulo.jsp', 'art', 800, 400)" >
                           </td>
                           <td >
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td height="19">&nbsp; </td>
                                          <td >&nbsp;Total de registros:&nbsp;<%=BCCA.getTotalRegistros()%></td>
                                          <td class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  " tabindex="2" onClick="document.frm.accion.value = this.name"></td>
                                          <td><input type="image" src="../imagenes/default/gnome_tango/mimetypes/gnome-mime-application-vnd.ms-excel.png" align="middle" name="exportar" value="exportar"  onClick="document.frm.accion.value = this.name"></td>
                                       </tr>
                                    </table>
                                 </td>
                              </tr>
                           </table>
                        </td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCCA" property="mensaje"/>
   <%
	if (BCCA.getAccion().equalsIgnoreCase("exportar"))
	{
		if (BCCA.getMensaje().equalsIgnoreCase("exportado correctamente"))
		{
			System.out.print("exportado OK");
		%>
			<a href="./manejarArchivosDatosToXls.jsp?file=../reportes/reportes/FacturasPorArticulo.csv">Haga Click Aqui</a>
			<%session.setAttribute("archivo","FacturasPorArticulo.csv");%>
			
		<%
		}
	}
%>
</td>
  </tr>
</table>


<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[0]%></div></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[3]%></div></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
	 <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[6]%></div></td>
    </tr>
   <%int r = 0;
   BigDecimal totCantidades = new BigDecimal(0);
   while(iterFacturasArticulos.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterFacturasArticulos.next(); 
      	// estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="right"><%=sCampos[0]%>&nbsp;</div></td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[3]), "JSTsToStr").toString()%>&nbsp; </td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[4]%>&nbsp;</div></td>
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[6]%>&nbsp;</td>
	  <td class="fila-det-border" ><div align="right"><%=sCampos[2]%>&nbsp;</div></td>
   </tr>
<%
	totCantidades = totCantidades.add(new BigDecimal(sCampos[2]));
   }%>
   <tr class="text-dos">
   		<td colspan="6"></td>
   		<td><div align = "right"><%=totCantidades%></div></td>
   </tr>
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

