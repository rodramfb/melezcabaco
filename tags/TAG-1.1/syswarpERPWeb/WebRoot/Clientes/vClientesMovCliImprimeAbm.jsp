<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesMovCliImprime
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Jan 13 09:57:36 ART 2012 
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
String titulo = "IMPRESION DE COMPROBANTES DE CLIENTES";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesMovCliImprime   = null;
int totCol = 23; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
Iterator iter;
%>
<html>
<jsp:useBean id="BVCMCIA"  class="ar.com.syswarp.web.ejb.BeanVClientesMovCliImprimeAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCMCIA" property="*" />
<%
 BVCMCIA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BVCMCIA.setResponse(response);
 BVCMCIA.setRequest(request);
 BVCMCIA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script>
 function setSort(orden){
   document.getElementById('sort').value = orden;
   document.frm.submit();
 } 
 
 function validaCharsFecha(evento){
   var codigo = getCodigo(evento);
   var fecha = document.getElementById('filtroFechamov');

   
   if((fecha.value.length == 2 || fecha.value.length == 5 ) &&  (codigo != 8 && codigo != 47  && codigo != 46)){
     fecha.value += '/';
     return false;
   }
   else{
     if( (codigo < 48 || codigo>57)  &&  (codigo != 8 && codigo != 47  && codigo != 46)  ) return false;
   }
 } 
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "nrointerno";
tituCol[1] = "Cliente";
tituCol[2] = "Razon";
tituCol[3] = "Fecha";
tituCol[4] = "sucursal";
tituCol[5] = "Comprobante";
tituCol[6] = "tipomov";
tituCol[7] = "Tipo";
tituCol[8] = "saldo";
tituCol[9] = "Importe";
tituCol[10] = "cambio";
tituCol[11] = "moneda";
tituCol[12] = "unamode";
tituCol[13] = "tipocomp";
tituCol[14] = "condicion";
tituCol[15] = "anulada";
tituCol[16] = "Observaciones";
tituCol[17] = "CAE";
tituCol[18] = "afipcaea";
tituCol[19] = "impreso";
tituCol[20] = "Letra";
tituCol[21] = "Vence";
tituCol[22] = "idempresa";
java.util.List VClientesMovCliImprime = new java.util.ArrayList();
VClientesMovCliImprime= BVCMCIA.getVClientesMovCliImprimeList();
iterVClientesMovCliImprime = VClientesMovCliImprime.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesMovCliImprimeAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="11%" height="38"><table width="100%" border="0">
                    <tr>
                      <td width="72%" height="26"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr class="text-globales">
                                  <td width="1%" height="19">&nbsp; </td>
                                  <td width="23%">&nbsp;Total de registros:&nbsp;<%=BVCMCIA.getTotalRegistros()%></td>
                                  <td width="11%" >Visualizar:</td>
                                  <td width="11%"><select name="limit" >
                                      <%for(i=15; i<= 150 ; i+=15){%>
                                      <%if(i==BVCMCIA.getLimit()){%>
                                      <option value="<%=i%>" selected><%=i%></option>
                                      <%}else{%>
                                      <option value="<%=i%>"><%=i%></option>
                                      <%}
                                                      if( i >= BVCMCIA.getTotalRegistros() ) break;
                                                    %>
                                      <%}%>
                                      <option value="<%= BVCMCIA.getTotalRegistros()%>">Todos</option>
                                    </select>                                  </td>
                                  <td width="7%">&nbsp;P&aacute;gina:</td>
                                  <td width="12%"><select name="paginaSeleccion" >
                                      <%for(i=1; i<= BVCMCIA.getTotalPaginas(); i++){%>
                                      <%if ( i==BVCMCIA.getPaginaSeleccion() ){%>
                                      <option value="<%=i%>" selected><%=i%></option>
                                      <%}else{%>
                                      <option value="<%=i%>"><%=i%></option>
                                      <%}%>
                                      <%}%>
                                    </select>                                  </td>
                                  <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                </tr>
                            </table></td>
                          </tr>
                      </table></td>
                    </tr>
                  </table></td>
              </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCMCIA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td >&nbsp;</td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 2 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 2 DESC ')"></div></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 3 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 3 DESC ')"></div></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 8 ASC, 5 ASC, 6 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort('  8 DESC, 5 DESC, 6 DESC  ')"></div></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort('  5 ASC, 6 ASC, 8 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort('  5 DESC, 6 DESC, 8 DESC  ')"></div></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 21 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 21 DESC ')"></div></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 4 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 4 DESC ')"></div></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
  </tr>
  <tr class="fila-encabezado">
    <td >&nbsp;</td>
    <td ><input name="filtroIdclie" type="text" value="<%=BVCMCIA.getFiltroIdclie()%>" id="filtroIdclie" size="5" maxlength="10" style="text-align:right"  onKeyPress="if(!validaNumericosFF(event)) return false;"></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">
      <select name="filtroTipomov" id="filtroTipomov" class="campo" onChange="document.frm.submit();" style="width:100%">
        <option value="">Sel.</option>
        <%
						    iter = BVCMCIA.getListTipoMov().iterator();   
							while(iter.hasNext()){
							  String[] datos = (String[]) iter.next();
							  if(datos[0].equalsIgnoreCase("4")) continue; 
							 %>
        <option value="<%= datos[0] %>" <%=  BVCMCIA.getFiltroTipomov().toString().equals(datos[0]) ? "selected" : "" %> label="<%= datos[1] %>"><%=  datos[1]  %></option>
        <% 
						   } %>
      </select>
    </div></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><select name="filtroSucursal" id="filtroSucursal" class="campo" onChange="document.frm.submit();">
            <option value="">Sel.</option>
            <%
						    iter = BVCMCIA.getListSucursales().iterator();   
							while(iter.hasNext()){
							 String[] datos = (String[]) iter.next();
							 %>
            <option value="<%= datos[0] %>" <%=  BVCMCIA.getFiltroSucursal().toString().equals(datos[0]) ? "selected" : "" %> label="<%= datos[1] %>"><%= Common.strZero(datos[0] , 4) %></option>
            <% 
						   } %>
          </select></td>
          <td><input name="filtroNroComprobante" type="text" value="<%=BVCMCIA.getFiltroNroComprobante()%>" id="filtroNroComprobante" size="8" maxlength="10" style="text-align:right"  onKeyPress="if(!validaNumericosFF(event)) return false;"></td>
        </tr>
      </table></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><input name="filtroFechamov" type="text" value="<%=BVCMCIA.getFiltroFechamov()%>" id="filtroFechamov" size="10" maxlength="10" style="text-align:right" onKeyPress="return validaCharsFecha(event)"></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
  </tr>
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="2%" ><div align="right"><%=tituCol[1]%></div></td>
     <td width="22%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[7]%></div></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[5]%></div></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[20]%></div></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[3]%></div></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[17]%></div></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[21]%></div></td>
     <td width="46%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[16]%></td>
  </tr>
   <%int r = 0; 
   while(iterVClientesMovCliImprime.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesMovCliImprime.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
	  String plantillaImpresionJRXML = sCampos[11].equals("1") ?  "modelo.factura.ri" :  "modelo.factura.ri.mon.ext"  ;

	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center">
        <%if(!Common.setNotNull(sCampos[17]).equals("")){%>
        <img src="../imagenes/default/gnome_tango/apps/pdf.jpg" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=<%= plantillaImpresionJRXML %>&nrointerno=<%=sCampos[0]%>','pedido',750, 400)" style="cursor:pointer">
        <% }else{ %> 
        <img src="../imagenes/default/gnome_tango/actions/lock.png" title="CAE Pendiente de Asignar !!">
        <% } %>
      </div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[1]%></div></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[2]%></td>
      <td class="fila-det-border" ><div align="center"><%=sCampos[7]%></div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.strZero(sCampos[4], 4)%>-<%=Common.strZero(sCampos[5], 8)%></div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setNotNull(sCampos[20])%></div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[3]), "JSDateToStr")%></div></td>
      <td bgcolor="#CED6F1" class="fila-det-border" ><div align="center"><%= !Common.setNotNull(sCampos[17]).equals("") ? Common.setNotNull(sCampos[17]) : "<strong class=\"fila-det-bold-rojo\">PENDIENTE</strong>" %></div></td>
      <td bgcolor="#CED6F1" class="fila-det-border" ><div align="center"><%=!Common.setNotNull(sCampos[21]).equals("") ? Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[21]), "JSDateToStr"): ""%></div></td>
      <td class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[16])%></td>
   </tr>
<%
   }%>
   </table>
   <input name="accion" value="" type="hidden">
   <input name="sort"  id="sort" value="<%= BVCMCIA.getSort() %>" type="hidden">   
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

