<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesRemitosRegalosEntregas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Dec 14 12:45:32 ART 2010 
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
String titulo = "CONSULTA DE REMITOS DE ENTREGAS DE PEDIDOS DE REGALOS EMPRESARIOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesRemitosRegalosEntregas   = null;
int totCol = 22; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCRREA"  class="ar.com.syswarp.web.ejb.BeanVClientesRemitosRegalosEntregas"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCRREA" property="*" />
<%
 BVCRREA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BVCRREA.setResponse(response);
 BVCRREA.setRequest(request);
 BVCRREA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script> 
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "NºOrden";
tituCol[1] = "idremitocliente";
tituCol[2] = "nrosucursal";
tituCol[3] = "Comprobante";
tituCol[4] = "Fecha";
tituCol[5] = "idestado";
tituCol[6] = "Estado";
tituCol[7] = "CtaCte";
tituCol[8] = "idzona";
tituCol[9] = "zona";
tituCol[10] = "idexpreso";
tituCol[11] = "expreso";
tituCol[12] = "idcliente";
tituCol[13] = "Razón";
tituCol[14] = "contacto";
tituCol[15] = "Dirección";
tituCol[16] = "nro";
tituCol[17] = "piso";
tituCol[18] = "depto";
tituCol[19] = "localidad";
tituCol[20] = "provincia";
tituCol[21] = "idempresa";
java.util.List VClientesRemitosRegalosEntregas = new java.util.ArrayList();
VClientesRemitosRegalosEntregas= BVCRREA.getVClientesRemitosRegalosEntregasList();
iterVClientesRemitosRegalosEntregas = VClientesRemitosRegalosEntregas.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="popupcalendar" class="text"></div> 
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesRemitosRegalosEntregas.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="44"  class="text-globales"><%=titulo%></td>
                </tr>

			    <tr class="text-globales" >
				  <td height="2" bgcolor="#000000"></td>
			    </tr>					
				
                <tr>
                 <td width="11%" height="38">
				  <table width="100%" border="0">
                    <tr>
                      <td width="72%" height="26"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr class="text-globales">
                                  <td width="23%" height="19">&nbsp;Total de registros:&nbsp;<%=BVCRREA.getTotalRegistros()%></td>
                                  <td width="11%" >Visualizar:</td>
                                  <td width="11%"><select name="limit" >
                                      <%for(i=15; i<= 150 ; i+=15){%>
                                      <%if(i==BVCRREA.getLimit()){%>
                                      <option value="<%=i%>" selected><%=i%></option>
                                      <%}else{%>
                                      <option value="<%=i%>"><%=i%></option>
                                      <%}
                                                      if( i >= BVCRREA.getTotalRegistros() ) break;
                                                    %>
                                      <%}%>
                                      <option value="<%= BVCRREA.getTotalRegistros()%>">Todos</option>
                                    </select>                                  </td>
                                  <td width="7%">&nbsp;P&aacute;gina:</td>
                                  <td width="12%"><select name="paginaSeleccion" >
                                      <%for(i=1; i<= BVCRREA.getTotalPaginas(); i++){%>
                                      <%if ( i==BVCRREA.getPaginaSeleccion() ){%>
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
			  <tr class="text-globales" >
				<td height="2" bgcolor="#000000"></td>
			  </tr>	
                <tr>
                  <td height="38"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="text-globales">
                      <td width="13%" height="35">F.R. Desde:(*) </td>
                      <td width="37%" ><span class="fila-det-border">
                        <input name="filtroFRDesde" type="text" class="campo"
													id="filtroFRDesde" 
													value="<%=BVCRREA.getFiltroFRDesde()%>" size="9"
													maxlength="10" readonly>
                      <a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onMouseOver="calSwapImg('BTN_date_0', 'img_Date_OVER',true); "
													onMouseOut="calSwapImg('BTN_date_0', 'img_Date_UP',true);"
													onClick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','filtroFRDesde','BTN_date_0');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_0" width="22" height="17" border="0"
														align="absmiddle"> </a></span></td>
                      <td width="13%">F.R. Hasta:(*) </td>
                      <td width="37%"><span class="fila-det-border">
                        <input name="filtroFRHasta" type="text" class="campo" 
													id="filtroFRHasta" 
													value="<%=BVCRREA.getFiltroFRHasta()%>" size="9"
													maxlength="10" readonly>
                      <a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onMouseOver="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onMouseOut="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onClick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','filtroFRHasta','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle"></a></span></td>
                    </tr>

                    <tr class="text-globales">
                      <td height="30">Orden N&ordm; :</td>
                      <td ><input name="filtroIdpedido" type="text" value="<%=BVCRREA.getFiltroIdpedido()%>" id="filtroIdpedido" size="5" maxlength="10" style="text-align:right"  onKeyPress="if(!validaNumericosFF(event)) return false;"></td>
                      <td>Cliente:</td>
                      <td><input name="filtroIdclie" type="text" value="<%=BVCRREA.getFiltroIdclie()%>" id="filtroIdclie" size="5" maxlength="10" style="text-align:right"  onKeyPress="if(!validaNumericosFF(event)) return false;"></td>
                    </tr>
                  </table></td>
                </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCRREA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="2" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td colspan="7" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Remito</div></td>
    <td colspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Cliente</div></td>
    </tr>
  <tr class="fila-encabezado">
    <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"></div></td>
    <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"></div></td>
    <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[0]%></div></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[3]%></div></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[4]%></div></td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td width="33%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[13]%></td>
     <td width="37%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[15]%></td>
    </tr>
   <%int r = 0;
   String plantillaImpresionJRXML =  "remitos_clientes_regalos_frame";
   while(iterVClientesRemitosRegalosEntregas.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesRemitosRegalosEntregas.next(); 
	  
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td bgcolor="#F7FBDE" class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/kuser.png" width="20" height="20" style="cursor:pointer" onClick="abrirVentana('clientesRemitosConformacionAudit.jsp?sucursal=<%=sCampos[2]%>&remitocliente=<%=sCampos[3]%>&idremitocliente=<%=sCampos[1]%>&idcliente=<%=sCampos[12]%>&cliente=<%=sCampos[13] + " - [ " + sCampos[14] + " ]"%>&tipopedido=R','detalle', 750, 250)"></div></td>
     <td bgcolor="#F7FBDE" class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/actions/delivery.gif" width="20" height="20" onClick="abrirVentana('vClientesRemitosDistribucionAbm.jsp?sucursal=<%=sCampos[2]%>&remitocliente=<%=sCampos[3]%>&idremitocliente=<%=sCampos[1]%>&idcliente=<%=sCampos[12]%>&cliente=<%=sCampos[13] + " - [ " + sCampos[14] + " ]"%>&tipopedido=R','detalle', 750, 250)" style="cursor:pointer"></div></td>
     <td bgcolor="#F7FBDE" class="fila-det-border" >
      <div align="right" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=notas_pedido_regalos&idpedido_cabe=<%=sCampos[0]%>','pedido',750, 400); "><a href="#"><%=Common.setNotNull(sCampos[0])%></a></div></td>
      <td bgcolor="#F7FBDE" class="fila-det-border" ><div align="center" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=<%= plantillaImpresionJRXML %>&idremitoclientedesde=<%=sCampos[1]%>', 'remitocliente', 750, 500);" style="cursor:pointer" ><a href="#"><%=Common.strZero(sCampos[2], 4)%>-<%=Common.strZero(sCampos[3], 8)%>&nbsp;</a></div></td>
      <td bgcolor="#F7FBDE" class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[4]), "JSDateToStr")%>&nbsp;</div></td>
      <td bgcolor="#F7FBDE" class="fila-det-border" ><%=sCampos[6]%>&nbsp;</td>
      <td bgcolor="#F7FBDE" class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[12]%>-<%=sCampos[13]%> / [<strong><%=sCampos[14]%></strong>]&nbsp;</td>
      <td class="fila-det-border" ><%= "Calle: " + sCampos[15] + " - Nº: " + sCampos[16] + " - Piso: " + sCampos[17] + " - Depto: " + sCampos[18] %> </td>
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

