<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesMovCli
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Mar 09 09:44:25 ART 2012 
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
String titulo = "ETIQUETAS FACTURAS A";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesMovCli   = null;
int totCol = 15; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCMCA"  class="ar.com.syswarp.web.ejb.BeanVClientesMovCliEtiquetas"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCMCA" property="*" />
<%
 BVCMCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BVCMCA.setResponse(response);
 BVCMCA.setRequest(request);
 BVCMCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
 
   function generaEtiquetas(){
     var fecha = '<%= Common.setNotNull(BVCMCA.getFiltroFecha()) %>';
	 var idcliente = <%=Common.esEntero(BVCMCA.getFiltroIdclie()) && Long.parseLong (BVCMCA.getFiltroIdclie())> 0 ? BVCMCA.getFiltroIdclie() : "-1" %>;
	 if(fecha!= '')
       abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=frame_etiquetas_facturas_a&fecha='+fecha+'&idcliente=' + idcliente,'pedido',750, 400)
     else 
	   alert('Es necesario filtrar por fecha.');
   }
 
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Cliente";
tituCol[2] = "Fecha";
tituCol[3] = "sucursal";
tituCol[4] = "Comprobante";
tituCol[5] = "tipomovs";
tituCol[6] = "idtipomov";
tituCol[7] = "tipomov";
tituCol[8] = "Importe";
tituCol[9] = "Saldo";
tituCol[10] = "idcondicion";
tituCol[11] = "condicion";
tituCol[12] = "idtipocomp";
tituCol[13] = "tipocomp";
tituCol[14] = "sucucli";
java.util.List VClientesMovCli = new java.util.ArrayList();
VClientesMovCli= BVCMCA.getVClientesMovCliList();
iterVClientesMovCli = VClientesMovCli.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>
<form action="vClientesMovCliEtiquetas.jsp" method="POST" name="frm">
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
                      <td width="6%" height="26" class="text-globales"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="text-globales">
                          <td width="1%" height="28">&nbsp;</td>
                          <td width="22%">Total de registros:&nbsp;<%=BVCMCA.getTotalRegistros() + ""%></td>
                          <td width="12%" >Visualizar:</td>
                          <td width="15%"><select name="limit" >
                              <%for(i=15; i<= 150 ; i+=15){%>
                              <%if(i==BVCMCA.getLimit()){%>
                              <option value="<%=i%>" selected><%=i%></option>
                              <%}else{%>
                              <option value="<%=i%>"><%=i%></option>
                              <%}
                                                      if( i >= BVCMCA.getTotalRegistros() ) break;
                                                    %>
                              <%}%>
                              <option value="<%= BVCMCA.getTotalRegistros()%>">Todos</option>
                            </select>                          </td>
                          <td width="22%">&nbsp;P&aacute;gina:</td>
                          <td width="15%"><select name="paginaSeleccion" >
                              <%for(i=1; i<= BVCMCA.getTotalPaginas(); i++){%>
                              <%if ( i==BVCMCA.getPaginaSeleccion() ){%>
                              <option value="<%=i%>" selected><%=i%></option>
                              <%}else{%>
                              <option value="<%=i%>"><%=i%></option>
                              <%}%>
                              <%}%>
                            </select>                          </td>
                          <td width="13%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                        </tr>
                        <tr  bgcolor="#FFFFFF">
                          <td colspan="7" height="3px"> </td>
                        </tr>
                        <tr class="text-globales">
                          <td height="28">&nbsp;</td>
                          <td>Fecha:</td>
                          <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="39%"><span class="fila-det-border">
                                  <input name="filtroFecha" type="text" class="campo" id="filtroFecha" onFocus="this.blur()" value="<%=BVCMCA.getFiltroFecha()%>" size="10" maxlength="10" readonly>
                                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','filtroFecha','BTN_date_7');return false;"></a></span></td>
                                <td width="61%"><a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','filtroFecha','BTN_date_7');return false;"><img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
                              </tr>
                            </table></td>
                          <td>&nbsp;</td>
                          <td>Imprimir: </td>
                          <td><img src="../imagenes/default/gnome_tango/devices/gnome-dev-printer.png" width="22" height="22" style="cursor:pointer" title="Impresi&oacute;n Nota de Pedido" onClick="generaEtiquetas()"></td>
                          <td class="text-globales">&nbsp;</td>
                        </tr>
                        <tr class="text-globales">
                          <td height="27">&nbsp;</td>
                          <td>Cliente:</td>
                          <td ><input name="filtroIdclie" type="text" value="<%=BVCMCA.getFiltroIdclie()%>" id="filtroIdclie" size="5" maxlength="10" style="text-align:right"  onKeyPress="if(!validaNumericosFF(event)) return false;"></td>
                          <td>&nbsp;</td>
                          <td >&nbsp;</td>
                          <td>&nbsp;</td>
                          <td class="text-globales">&nbsp;</td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCMCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[0]%></div></td>
     <td width="45%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[2]%></div></td>
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[4]%></div></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[8]%></div></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[9]%></div></td>
    </tr>
   <%int r = 0;
   while(iterVClientesMovCli.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesMovCli.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="right"><%=sCampos[0]%></div></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[2]), "JSDateToStr")%></div></td>
      <td class="fila-det-border" ><div align="center"><%=sCampos[5]%>-<%=Common.strZero(sCampos[3], 4)%>-<%=Common.strZero(sCampos[4], 8)%></div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado(Double.parseDouble(sCampos[8]), 10, 2)%></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[9]%></div></td>
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

