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
String titulo = "CLIENTES";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterClientesClientes   = null;
int totCol = 34; // cantidad de columnas
String[] tituCol = new String[totCol]; 
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCCA"  class="ar.com.syswarp.web.ejb.BeanClientesClientesAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCCA" property="*" />
<%
 BCCA.setResponse(response);
 BCCA.setRequest(request);
 BCCA.setIdempresa(new BigDecimal(session.getAttribute("empresa").toString()));
 BCCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <%--  <script language="JavaScript" src="scripts/forms.js"></script> --%>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 
 
 <script language="JavaScript">
 
 function limpiarFiltros(){ 
 
   document.frm.filtroIdcliente.value = '';
   document.frm.filtroIdclienteKosher.value = '';
   document.frm.filtroCliente.value = '';
   document.frm.filtroNrodocumento.value = '';
   document.frm.filtroNrotarjeta.value = '';
 
 }
 
 // --
 
 function mostrarLOVDETA(pagina) {
	frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width=800,height=450,status=yes');
	if (frmLOV.opener == null) 
		frmLOV.opener = self;
 }	
 
 // --
 
 function elementos(){
   for(var i=0;i<document.frm.elements.length;i++) alert(document.frm.elements[i].name);
 }
 
 // --
 
 window.onload = function(){
   //elementos();
 };
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód."; 
tituCol[1] = "Razón Social";
tituCol[2] = "id tipo. Doc.";
tituCol[3] = "T. Doc.";
tituCol[4] = "Documento";
tituCol[5] = "brutos";
tituCol[6] = "idtipoiva";
tituCol[7] = "idvendedor";
tituCol[9] = "idcondicion";
tituCol[9] = "descuento1";
tituCol[10] = "descuento2";
tituCol[11] = "descuento3";
tituCol[12] = "Cuenta";
tituCol[13] = "idmoneda";
tituCol[14] = "idlista";
tituCol[15] = "idtipoclie";
tituCol[16] = "observacion";
tituCol[17] = "lcredito";
tituCol[18] = "idtipocomp";
tituCol[19] = "autorizado";
tituCol[20] = "idcredcate";
tituCol[21] = "idempresa";
tituCol[22] = "Estado";
tituCol[33] = "CLUB";
java.util.List ClientesClientes = new java.util.ArrayList();
ClientesClientes= BCCA.getClientesClientesList();
iterClientesClientes = ClientesClientes.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >

<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="clientesClientesAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim> 
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td  class="text-globales"><%=titulo%></td>
                   <td>&nbsp;</td>
                </tr>
                <tr>
                   <td width="11%" height="38">
                      <table width="36%" border="0">
                         <tr>
                            <td width="27%">
                               <input name="alta" id="alta" value="alta" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('alta','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name" tabindex="100">                            </td>
                            <td width="27%">
                               <input name="modificacion" id="modificacion" value="modificacion" type="image" src="../imagenes/default/btn_edit_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('modificacion','','../imagenes/default/btn_edit_over.gif',1)" onClick="document.frm.accion.value = this.name">                            </td>
                            <td width="27%">
                               <input name="baja" id="baja" value="baja" type="image" src="../imagenes/default/btn_remove_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('baja','','../imagenes/default/btn_remove_over.gif',1)" onClick="return confirmarBaja('frm');">                            </td>
						 </tr>
                      </table>
                   </td>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="2%" height="26" class="text-globales"><%-- Buscar --%> </td>
                           <td width="2%">
                              <%-- <input name="ocurrencia" type="text" value="<%=BCCA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30" tabindex="1">  --%>                           </td>
                           <td width="96%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="2%" height="19">&nbsp; </td>
                                          <td width="32%">&nbsp;Total de registros:&nbsp;<%=BCCA.getTotalRegistros()%></td>
                                          <td width="14%" >Visualizar:</td>
                                          <td width="14%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BCCA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCCA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCCA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="10%">&nbsp;P&aacute;gina:</td>
                                          <td width="15%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BCCA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BCCA.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select> 
                                          </td>
                                          <td width="13%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  " tabindex="2"></td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" rowspan="2" >&nbsp;</td>
     <td width="3%" rowspan="2" valign="bottom" ><div align="center"><%=tituCol[33]%></div></td>
     <td colspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[0]%></div></td>
     <td width="44%" rowspan="2" valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="20%" rowspan="2" valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[12]%></td>
     <td width="6%" rowspan="2" valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="8%" rowspan="2" valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[4]%></div></td>
	 <td width="6%" rowspan="2" valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Tjta</div></td>
	 <td width="4%" rowspan="2" valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[22]%></div></td>
    </tr>
  <tr class="fila-encabezado">
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Baco</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Kosher</td>
  </tr>
  <tr class="fila-encabezado">
    <td ><div align="center"><img src="../imagenes/default/gnome_tango/actions/edit-clear.png" width="18" height="18" onClick="limpiarFiltros()" title="Limpiar Filtros" style="cursor:pointer"></div></td>
    <td valign="bottom" >&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><input name="filtroIdcliente" type="text" value="<%=BCCA.getFiltroIdcliente()%>" id="filtroIdcliente" size="5" maxlength="10" style="text-align:right"  onKeyPress="if(!validaNumericosFF(event)) return false;"></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><input name="filtroIdclienteKosher" type="text" value="<%=BCCA.getFiltroIdclienteKosher()%>" id="filtroIdclienteKosher" size="5" maxlength="10" style="text-align:right"  onKeyPress="if(!validaNumericosFF(event)) return false;"></td>
    <td valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><span class="fila-det-border">
      <input name="filtroCliente" type="text" value="<%=BCCA.getFiltroCliente()%>" id="filtroCliente" size="25" maxlength="30">
    </span></td>
    <td valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right">
      <input name="filtroNrodocumento" type="text" value="<%=BCCA.getFiltroNrodocumento()%>" id="filtroNrodocumento" size="10" maxlength="11" style="text-align:right"  onKeyPress="if(!validaNumericosFF(event)) return false;">
    </div></td>
    <td valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">
      <input name="filtroNrotarjeta" type="text" value="<%=BCCA.getFiltroNrotarjeta()%>" id="filtroNrotarjeta" size="12" maxlength="16" style="text-align:right"  onKeyPress="if(!validaNumericosFF(event)) return false;">
    </div></td>
    <td valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
  </tr>
  
  
  
  
   <%int r = 0;
   while(iterClientesClientes.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterClientesClientes.next(); 
      String imagen = Common.setNotNull(sCampos[29]);
			String estado = Common.setNotNull(sCampos[26]);
			String motivo = Common.setNotNull(sCampos[28]);
			
			if(Common.setNotNull(imagen).equals("")){
			  imagen =  "../imagenes/default/gnome_tango/emblems/emblem-important.png";
				estado = "Estado no definido";
				motivo = "Posible Inconsistencia de Datos.";
			}
			
			// estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>

   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><input type="radio" name="idcliente" value="<%= sCampos[0]%>"></td>
      <td class="fila-det-border" align="center"><div align="center"><img src="<%=sCampos[34]%>" title="<%=sCampos[33]%>" > </div></td>
      <td width="3%" class="fila-det-border" ><div align="right"><%=sCampos[0]%>&nbsp;</div></td>
      <td width="4%" class="fila-det-border" ><div align="right"><%=Common.setNotNull(sCampos[35])%>&nbsp;</div></td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp; </td>
      <td class="fila-det-border" ><%=Common.setNotNull(sCampos[11])%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[4]%>&nbsp;</div></td>
	  <td class="fila-det-border" ><div align="center">####</div></td>
	  <td class="fila-det-border" ><div align="center"><img src="<%=imagen%>" title="Estado: <%=estado%> - Motivo: <%=motivo%> " width="20" height="20"  onClick="abrirVentana('clientesestadosclientesXClienteAbm.jsp?idcliente=<%=sCampos[0]%>&razon=<%=sCampos[1]%>', 'estados', 750, 400)">&nbsp;</div></td>
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

