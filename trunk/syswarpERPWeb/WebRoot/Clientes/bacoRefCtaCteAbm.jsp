<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: bacoRefCtaCte
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jun 17 12:34:05 ART 2010 
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
String titulo = "CUENTA CORRIENTE DE REFERIDOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterBacoRefCtaCte   = null;
int totCol = 9; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BBRCCA"  class="ar.com.syswarp.web.ejb.BeanBacoRefCtaCteAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BBRCCA" property="*" />
<%
 BBRCCA.setResponse(response);
 BBRCCA.setRequest(request);
 BBRCCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBRCCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathscript%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
//-->

function validarAlta(){
  var idcliente = '<%=BBRCCA.getIdcliente() %>';
  if(idcliente == ''){
    alert('Es necesario seleccionar cliente.');
	return false;
  }
  else{
    document.frm.accion.value = 'alta';
    return true;
  }
  

}

 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Cliente";
tituCol[2] = "idoperacion";
tituCol[3] = "operacion";
tituCol[4] = "Operación";
tituCol[5] = "NºPrep.";
tituCol[6] = "Puntos";
tituCol[7] = "Fecha";
tituCol[8] = "Observaciones";
java.util.List BacoRefCtaCte = new java.util.ArrayList();
BacoRefCtaCte= BBRCCA.getBacoRefCtaCteList();
iterBacoRefCtaCte = BacoRefCtaCte.iterator();

%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="bacoRefCtaCteAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="../imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="41"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td width="94%" height="38">
                      <table width="100%" border="0">
                         <tr>
                           <td width="72%" height="26">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19"><table width="36%" border="0">
                                              <tr>
                                                <td width="27%"><input name="alta" id="alta" value="alta" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('alta','','../imagenes/default/btn_add_over.gif',1)" onClick="return validarAlta()"></td>
                                                <%--
							 <td width="27%">
                               <input name="modificacion" id="modificacion" value="modificacion" type="image" src="../imagenes/default/btn_edit_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('modificacion','','../imagenes/default/btn_edit_over.gif',1)" onClick="document.frm.accion.value = this.name">                            </td>
                            <td width="27%">
                               <input name="baja" id="baja" value="baja" type="image" src="../imagenes/default/btn_remove_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('baja','','../imagenes/default/btn_remove_over.gif',1)" onClick="return confirmarBaja('frm');">
							</td>
							 --%>
                                              </tr>
                                            </table></td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BBRCCA.getTotalRegistros() + "" %></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BBRCCA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BBRCCA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BBRCCA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BBRCCA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BBRCCA.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select>                                          </td>
                                          <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                       </tr>
                                    </table>                                 </td>
                              </tr>
                           </table>                        </td>
                     </tr>
                   </table>                </td>
            </tr>
                <tr >
                  <td height="38">
				  <table width="100%" border="1" cellspacing="0" cellpadding="0" >
                    <tr class="text-dos-bold">
                      <td width="3%"><div align="center"></div></td>
                      <td width="32%">RAZON</td>
                      <td width="25%">TIPO</td>
                      <td width="21%">DOCUMENTO</td>
                      <td width="19%">Estado</td>
                    </tr>
                    <tr class="text-dos">
                      <td><div align="center"><img src="../imagenes/default/gnome_tango/actions/filefind.png"
														width="18" height="18"
														onClick="abrirVentana('../Clientes/lov_clientesPedidos.jsp', 'clientes', 800, 400)"
														style="cursor:pointer" > </div></td>
                      <td><%=BBRCCA.getIdcliente()+"" %> - <%=BBRCCA.getCliente()+"" %>&nbsp;</td>
                      <td><%=BBRCCA.getTipocliente() +"" %>&nbsp;</td>
                      <td><%=BBRCCA.getTipodocumento() +""%> - <%=BBRCCA.getNrodocumento()+"" %>&nbsp; </td>
                      <td><%=BBRCCA.getEstado() +"" %>&nbsp;</td>
                    </tr>
                  </table></td>
                </tr>

          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BBRCCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <%-- <td width="5%" >&nbsp;</td> --%>
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="58%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[6]%></div></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[5]%></div></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Cliente</td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"></div></td>
   </tr>
   <%
   int r = 0;
   BigDecimal totalPuntos = new BigDecimal(0);
   while(iterBacoRefCtaCte.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterBacoRefCtaCte.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
	  totalPuntos=totalPuntos.add(new BigDecimal(sCampos[6]));%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <%-- <td class="fila-det-border" ><input type="radio" name="idctacte" value="<%= sCampos[0]%>"></td> --%>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setNotNull(sCampos[8]) + (sCampos[3].equalsIgnoreCase("PREPRO") ? (" - " + sCampos[9])  : "")%>&nbsp;</td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[6]%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[3].equalsIgnoreCase("PREPRO") ? sCampos[5]  : ""%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%= !sCampos[3].equalsIgnoreCase("PREPRO") && !sCampos[5].equalsIgnoreCase("0") ? sCampos[5]  : ""%>&nbsp;</div></td>
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[7]), "JSDateToStr")%></td>
      <td class="fila-det-border" ><div align="center">
          <%if(sCampos[3].equalsIgnoreCase("PREPRO")){%>
          <img src="../imagenes/default/gnome_tango/actions/stock_new-bcard.png" width="18" height="18" onClick="abrirVentana('../Clientes/bacoRefPreproDetalle.jsp?idpreprospecto=<%= sCampos[5] %>&accion=consulta', 'detaClie', 750, 350)"> 
          <% }else if(!sCampos[3].equalsIgnoreCase("MANS") && !sCampos[3].equalsIgnoreCase("MANR")){ %>
          <img src="../imagenes/default/gnome_tango/apps/config-users.png" width="18" height="18" onClick="abrirVentana('../Clientes/clientesClientesDetalle.jsp?idcliente=<%= sCampos[5] %>&accion=consulta', 'detaClie', 750, 400)">    
          <% }else{%>
          <img src="../imagenes/default/gnome_tango/emblems/emblem-readonly.png" width="18" height="18"  title="Operación manual, sin referido.">    		  
          <% } %>		  
      </div></td>
   </tr>
<%
   }
   
   if(r!=0){%>
   
   <tr  class="text-dos-bold"  >
     <td >&nbsp;</td>
     <td ><div align="right"></div></td>
     <td >Puntos P&aacute;gina Actual</td>
     <td ><div align="right"><%=totalPuntos + "" %>&nbsp;</div></td>
     <td >&nbsp;</td>
     <td >&nbsp;</td>
     <td >&nbsp;</td>
     <td >&nbsp;</td>
   </tr>
   <tr  class="text-dos-bold"  > 
      <%-- <td class="fila-det-border" ><input type="radio" name="idctacte" value="<%= sCampos[0]%>"></td> --%>
      <td >&nbsp;</td>
      <td ><div align="right"></div></td>
      <td >Total Puntos Disponibles</td>
      <td ><div align="right"><%=BBRCCA.getTotalDisponible() + "" %>&nbsp;</div></td>
      <td ><div align="right">&nbsp;</div></td>
      <td >&nbsp;</td>
      <td >&nbsp;</td>
      <td ><div align="center"></div></td>
   </tr>   
    <%
   }
   %>
  </table>
   <input name="accion" value="" type="hidden">
   <input name="recargar" type="hidden" id="recargar" value="1">
   <input name="cliente" type="hidden" id="cliente" value="<%=BBRCCA.getCliente() %>">
   <input name="idcliente" type="hidden" id="idcliente" value="<%=BBRCCA.getIdcliente() %>">
   <input name="tipodocumento" type="hidden" id="tipodocumento" value="<%=BBRCCA.getTipodocumento() %>">
   <input name="nrodocumento" type="hidden" id="nrodocumento" value="<%=BBRCCA.getNrodocumento() %>">
   <input name="tipocliente" type="hidden" id="tipocliente" value="<%=BBRCCA.getTipocliente() %>">
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

