<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesRemitosEstadoActual
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Oct 20 14:22:51 ART 2010 
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
String titulo = "CONSULTA DE ESTADO DE REMITOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesRemitosEstadoActual   = null;
int totCol = 25; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCREAA"  class="ar.com.syswarp.web.ejb.BeanVClientesRemitosEstadoActualAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCREAA" property="*" />
<%
 Iterator iter;
 BVCREAA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BVCREAA.setResponse(response);
 BVCREAA.setRequest(request);
 BVCREAA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script> 
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script> 
 <script>
	function mostrarMensaje(mensaje){
		overlib( mensaje , STICKY, CAPTION, '[INFO]',TIMEOUT,25000,WIDTH,250,BGCOLOR,'#FF9900');  
	} 
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "idconformacion";
tituCol[1] = "idremitocliente";
tituCol[2] = "nrosucursal";
tituCol[3] = "Comprobante";
tituCol[4] = "Fecha";
tituCol[5] = "idzona";
tituCol[6] = "zona";
tituCol[7] = "idexpreso";
tituCol[8] = "expreso";
tituCol[9] = "idexpresozona";
tituCol[10] = "idestado";
tituCol[11] = "Estado";
tituCol[12] = "fechaestado";
tituCol[13] = "procesado";
tituCol[14] = "nrohojaarmado";
tituCol[15] = "nrohojarutafinal";
tituCol[16] = "Tipo";
tituCol[17] = "Nro.";
tituCol[18] = "Razon";
tituCol[19] = "idsucuclie";
tituCol[20] = "Dirección";
tituCol[21] = "nro";
tituCol[22] = "depto";
tituCol[23] = "piso";
tituCol[24] = "idempresa";
java.util.List VClientesRemitosEstadoActual = new java.util.ArrayList();
VClientesRemitosEstadoActual= BVCREAA.getVClientesRemitosEstadoActualList();
iterVClientesRemitosEstadoActual = VClientesRemitosEstadoActual.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="popupcalendar" class="text"></div> 
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesRemitosEstadoActualAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td height="30"><%=titulo%></td>
                </tr>
                <tr class="text-globales">
                   <td height="2" bgcolor="#000000"></td>
                </tr>				
                <tr>
                  <td width="95%" height="38">
                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                          <td width="72%" height="26">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="23%" height="19">&nbsp;Total de registros:&nbsp;<%=BVCREAA.getTotalRegistros()%></td>
                                         <td width="11%" >Visualizar:</td>
                                         <td width="11%">
                                            <select name="limit" >
                                               <%for(i=15; i<= 150 ; i+=15){%>
                                                   <%if(i==BVCREAA.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                   <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                   <%}
                                                      if( i >= BVCREAA.getTotalRegistros() ) break;
                                                    %>
                                               <%}%>
                                               <option value="<%= BVCREAA.getTotalRegistros()%>">Todos</option>
                                            </select>                                          </td>
                                         <td width="7%">P&aacute;gina:</td>
                                         <td width="12%">
                                            <select name="paginaSeleccion" >
                                               <%for(i=1; i<= BVCREAA.getTotalPaginas(); i++){%>
                                                   <%if ( i==BVCREAA.getPaginaSeleccion() ){%>
                                                      <option value="<%=i%>" selected><%=i%></option> 
                                                   <%}else{%>
                                                      <option value="<%=i%>"><%=i%></option>
                                                   <%}%>
                                               <%}%>
                                            </select>                                          </td>
                                         <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  ">
										 </td>
                                      </tr>
																		  
                                   </table>                                 
								 </td>
                              </tr>
                              <tr class="text-globales" >
								<td height="2" bgcolor="#000000"></td>
							  </tr>								  
                          </table>                        
						 </td>
                     </tr>
                   </table>                
				</td>
            </tr>
                <tr>
                  <td height="38"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="text-globales">
                      <td height="25">F.R. Desde:(*) </td>
                      <td ><span class="fila-det-border">
                        <input name="filtroFRDesde" type="text" class="campo"
													id="filtroFRDesde" 
													value="<%=BVCREAA.getFiltroFRDesde()%>" size="9"
													maxlength="10" readonly>
                        <a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onMouseOver="calSwapImg('BTN_date_0', 'img_Date_OVER',true); "
													onMouseOut="calSwapImg('BTN_date_0', 'img_Date_UP',true);"
													onClick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','filtroFRDesde','BTN_date_0');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_0" width="22" height="17" border="0"
														align="absmiddle"> </a></span></td>
                      <td>F.R. Hasta:(*) </td>
                      <td><span class="fila-det-border">
                        <input name="filtroFRHasta" type="text" class="campo" 
													id="filtroFRHasta" 
													value="<%=BVCREAA.getFiltroFRHasta()%>" size="9"
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
                      <td width="13%" height="25">Zona:</td>
                      <td width="37%" ><select name="filtroZona" id="filtroZona" class="campo" style="width:50%" >
                        <option value="">Seleccionar</option>
                        <%
						iter = BVCREAA.getListZonas().iterator();
						while(iter.hasNext()){
						String [] datos = (String[])iter.next();%>
                        <option value="<%= datos[0] %>" <%= datos[0].equals( BVCREAA.getFiltroZona() ) ? "selected" : "" %>><%= datos[1] %></option>
                        <%  
						}%>
                      </select></td>
                      <td width="13%">Expreso:</td>
                      <td width="37%"><select name="filtroExpreso" id="filtroExpreso" class="campo" style="width:50%" onChange="document.frm.submit();">
                        <option value="">Seleccionar</option>
                        <%
            iter = BVCREAA.getListExpresos().iterator();
			while(iter.hasNext()){
			String [] datos = (String[])iter.next();%>
                        <option value="<%= datos[0] %>" <%= datos[0].equals( BVCREAA.getFiltroExpreso().toString()) ? "selected" : "" %> ><%= datos[1] %></option>
                        <%  
			}%>
                      </select></td>
                    </tr>
                    <tr class="text-globales">
                      <td height="25">Estado:</td>
                      <td ><select name="filtroEstados" id="filtroEstados" class="campo" style="width:50%" >
                          <option value="">Seleccionar</option>
                          <%
            iter = BVCREAA.getListEstados().iterator();
			while(iter.hasNext()){
			String [] datos = (String[])iter.next();%>
                          <option value="<%= datos[0] %>" <%= datos[0].equals( BVCREAA.getFiltroEstados() ) ? "selected" : "" %>><%= datos[1] %></option>
                          <%  
			}%>
                      </select></td>
                      <td>Cliente:</td>
                      <td><input name="filtroIdclie" type="text" value="<%=BVCREAA.getFiltroIdclie()%>" id="filtroIdclie" size="5" maxlength="10" style="text-align:right"  onKeyPress="if(!validaNumericosFF(event)) return false;"></td>
                    </tr>
                    <tr class="text-globales">
                      <td height="30">Club:</td>
                      <td height="30"><span class="fila-det-border">
                        <select name="idclub" id="idclub" class="campo" style="width:50%" >
                          <option value="-1" >Sel.</option>
                          <% 
									  iter = BVCREAA.getListClub().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                          <option value="<%= datos[0] %>" <%= datos[0].equals( BVCREAA.getIdclub().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                          <%  
									  }%>
                        </select>
                      </span></td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  </table></td>
                </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCREAA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td colspan="4" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Cliente</div></td>
    <td colspan="6" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Remito</div></td>
    </tr>
  <tr class="fila-encabezado">
    <td width="24" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td width="24" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[17]%></td>
    <td width="253" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[18]%></td>
    <td width="506" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[20]%></td>
    <td width="21" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td width="20" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td width="95" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[3]%></div></td>
     <td width="139" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[4]%></div></td>
     <td width="175" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[11]%></td>
     <td width="54" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[16]%></td>
    </tr>
   <%int r = 0;
   while(iterVClientesRemitosEstadoActual.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesRemitosEstadoActual.next(); 
	  String plantillaImpresionJRXML = sCampos[16].equalsIgnoreCase("N") ? "remitos_clientes_frame" : "remitos_clientes_regalos_frame";
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
	  String domicilio = sCampos[20];
      domicilio += sCampos[21];
      domicilio += sCampos[22];
      domicilio += sCampos[23];	 
	  
      String datosAudit = "<strong>U.Alt.:</strong> " + Common.setNotNull(sCampos[25]);
      datosAudit += "<br><strong>U.Act.:</strong> " + Common.setNotNull(sCampos[26]);
	  datosAudit += "<br><strong>F.Alt.:</strong> " +   Common.setObjectToStrOrTime( java.sql.Timestamp.valueOf( Common.setNotNull(sCampos[30]) ), "JSTsToStrWithHM" );
	  datosAudit += "<br><strong>F.Act.:</strong> " + (!Common.setNotNull(sCampos[31]).equals("") ? Common.setObjectToStrOrTime( java.sql.Timestamp.valueOf( Common.setNotNull(sCampos[31]) ), "JSTsToStrWithHM" ) : "");/*
	  datosAudit += "<br><strong> -- CONFORMACION -- </strong>"  ;
	  datosAudit += "<br><strong>U.Alt.Conf.:</strong> " + Common.setNotNull(sCampos[11]);
	  datosAudit += "<br><strong>U.Act.Conf.:</strong> " + Common.setNotNull(sCampos[12]);
	  datosAudit += "<br><strong>F.Alt.Conf.:</strong> " + (!Common.setNotNull(sCampos[13]).equals("") ? Common.setObjectToStrOrTime( java.sql.Timestamp.valueOf( Common.setNotNull(sCampos[13]) ), "JSTsToStrWithHM" ) : "");
	  datosAudit += "<br><strong>F.Act.Conf.:</strong> " + (!Common.setNotNull(sCampos[14]).equals("") ? Common.setObjectToStrOrTime( java.sql.Timestamp.valueOf( Common.setNotNull(sCampos[14]) ), "JSTsToStrWithHM" ) : "");	  */
	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td bgcolor="#F7FBDE" class="fila-det-border" ><img src="<%=Common.setNotNull(sCampos[26])%>" title="<%=Common.setNotNull(sCampos[25])%>"></td>
     <td bgcolor="#F7FBDE" class="fila-det-border" ><%=sCampos[17]%>&nbsp;</td>
     <td bgcolor="#F7FBDE" class="fila-det-border" ><%=sCampos[18]%>&nbsp;</td>
     <td bgcolor="#F7FBDE" class="fila-det-border" ><%= domicilio %></td>
     <td bgcolor="#E8E2F2" class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/actions/delivery.gif" width="20" height="20" onClick="abrirVentana('vClientesRemitosDistribucionAbm.jsp?sucursal=<%=sCampos[2]%>&remitocliente=<%=sCampos[3]%>&idremitocliente=<%=sCampos[1]%>&idcliente=<%=sCampos[17]%>&cliente=<%=sCampos[18]%>&tipopedido=<%= sCampos[16] %>','detalle', 750, 250)" style="cursor:pointer"></div></td>
     <td bgcolor="#E8E2F2" class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/kuser.png" width="20" height="20" style="cursor:pointer" onClick="abrirVentana('clientesRemitosConformacionAudit.jsp?sucursal=<%=sCampos[2]%>&remitocliente=<%=sCampos[3]%>&idremitocliente=<%=sCampos[1]%>&idcliente=<%=sCampos[17]%>&cliente=<%=sCampos[18]%>&tipopedido=<%= sCampos[16] %>','detalle', 750, 250)"></div></td>
     <td bgcolor="#E8E2F2" class="fila-det-border" ><div align="center" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=<%= plantillaImpresionJRXML %>&idremitoclientedesde=<%=sCampos[1]%>', 'remitocliente', 750, 500);" style="cursor:pointer" ><a href="#"><%=Common.strZero(sCampos[2], 4)%>-<%=Common.strZero(sCampos[3], 8)%>&nbsp;</a></div></td>
      <td bgcolor="#E8E2F2" class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[4]), "JSDateToStr")%>&nbsp;</div></td>
      <td bgcolor="#E8E2F2" class="fila-det-border" ><%=sCampos[11]%>&nbsp;</td>
      <td bgcolor="#E8E2F2" class="fila-det-border" ><%=sCampos[16]%>&nbsp;</td>
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

