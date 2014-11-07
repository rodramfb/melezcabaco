<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: pedidosConDescuento
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jul 15 09:28:13 GYT 2009 
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
String titulo = "CONSULTA DE PEDIDOS CON DESCUENTO EN TODOS SUS PRODUCTOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterPedidosConDescuento   = null;
int totCol = 14; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPCDA"  class="ar.com.syswarp.web.ejb.BeanPedidosConDescuentoAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPCDA" property="*" />
<%
 BPCDA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPCDA.setResponse(response);
 BPCDA.setRequest(request);
 BPCDA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "C.Pedido";
tituCol[1] = "C.Tipo Clie.";
tituCol[2] = "T.Clie.";
tituCol[3] = "C.Clie";
tituCol[4] = "Cliente";
tituCol[5] = "C.Expreso";
tituCol[6] = "Expreso";
tituCol[7] = "C.Est.";
tituCol[8] = "Estado";
tituCol[9] = "Motivo";
tituCol[10] = "F.Desde";
tituCol[11] = "F.Pedido";
tituCol[12] = "Origen";
tituCol[13] = "idempresa";
java.util.List PedidosConDescuento = new java.util.ArrayList();
PedidosConDescuento= BPCDA.getPedidosConDescuentoList();
iterPedidosConDescuento = PedidosConDescuento.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div> 
<form action="pedidosConDescuentoAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr  class="text-globales">
                   <td height="43"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="89%" height="38">
                     <table width="100%" border="0">
                        <tr>
                          <td width="11%" height="26" class="text-globales">Buscar</td>
                          <td width="19%">
                             <input name="ocurrencia" type="text" value="<%=BPCDA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                          <td width="70%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="13%" height="19" >Visualizar:</td>
                                         <td width="29%">
                                            <select name="limit" >
                                               <%for(i=15; i<= 150 ; i+=15){%>
                                                   <%if(i==BPCDA.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                   <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                   <%}
                                                      if( i >= BPCDA.getTotalRegistros() ) break;
                                                    %>
                                               <%}%>
                                               <option value="<%= BPCDA.getTotalRegistros()%>">Todos</option>
                                            </select>                                          </td>
                                         <td width="22%">&nbsp;P&aacute;gina:</td>
                                         <td width="24%">
                                            <select name="paginaSeleccion" >
                                               <%for(i=1; i<= BPCDA.getTotalPaginas(); i++){%>
                                                   <%if ( i==BPCDA.getPaginaSeleccion() ){%>
                                                      <option value="<%=i%>" selected><%=i%></option> 
                                                   <%}else{%>
                                                      <option value="<%=i%>"><%=i%></option>
                                                   <%}%>
                                               <%}%>
                                            </select>                                          </td>
                                         <td width="12%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                      </tr>
                                   </table>                                 </td>
                              </tr>
                          </table>                        </td>
                     </tr>
                        <tr>
                          <td class="text-globales">Tipo Cliente:</td>
                          <td class="text-globales"><select name="idtipoclie" id="idtipoclie"  class="campo">
                              <option value="-1" >Seleccionar</option>
                              <% 
                              Iterator iter = BPCDA.getListTipoClie().iterator();  
                              while(iter.hasNext()){
                                String[] datos = (String[]) iter.next();
                              %>
                              <option value="<%=datos[0]%>" <%= datos[0].equals(BPCDA.getIdtipoclie().toString() ) ?  "selected" : "" %> ><%=datos[1]%></option>
                              <%
                              }%>
                          </select></td>
                          <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr class="text-globales">
                              <td width="13%" >Mes y A&ntilde;o:</td>
                              <td width="29%"><span class="fila-det-border">
                          <input name="fechapedido" type="text" class="campo"
													id="fechapedido" 
													value="<%=BPCDA.getFechapedido()%>" size="12"
													maxlength="12" readonly>
                                <a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onMouseOver="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onMouseOut="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onClick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fechapedido','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle"> </a></span></td>
                              <td width="22%">&nbsp;Estado:</td>
                              <td width="24%"><select name="idestado" id="idestado"  class="campo" style="width:120px">
                                <option value="-1" >Seleccionar</option>
                                <% 
                              iter = BPCDA.getEstadosList().iterator(); 
                              while(iter.hasNext()){
                                String[] datos = (String[]) iter.next();
                              %>
                                <option value="<%=datos[0]%>" <%= datos[0].equals(BPCDA.getIdestado().toString() ) ?  "selected" : "" %> ><%=datos[1]%></option>
                                <%
                              }%>
                              </select></td>
                              <td width="12%" class="text-globales">&nbsp;</td>
                            </tr>
                          </table></td>
                        </tr>
                        <tr>
                          <td height="28" colspan="2" class="text-globales">Total de registros:&nbsp;<%=BPCDA.getTotalRegistros()+""%></td>
                          <td>&nbsp;</td>
                        </tr>
                   </table>                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BPCDA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" ><div align="center"><%=tituCol[0]%></div></td>
     <td width="7%" ><%=tituCol[3]%></td>
     <td width="28%" ><%=tituCol[4]%></td>
     <td width="14%" ><%=tituCol[2]%></td>
     <td width="31%" ><%=tituCol[6]%></td>
     <td width="8%" ><%=tituCol[8]%></td>
     <td width="5%" >&nbsp;</td>
     <td width="4%" >&nbsp;</td>
   </tr>
   <%int r = 0;
   while(iterPedidosConDescuento.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterPedidosConDescuento.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center"><%=sCampos[0]%></div></td>
      <td class="fila-det-border" ><%=sCampos[3]%></td>
      <td class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[6]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[8]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/mimetypes/gnome-mime-application-vnd.lotus-1-2-3.png" width="22" height="22" onClick="abrirVentana('pedidosHistoricoClienteDetalle.jsp?tipopedido=N&idpedido_cabe=<%=sCampos[0]%>&idcliente=<%=sCampos[3]%>&cliente=<%=sCampos[4]%>','detalle_pedido', 700, 400)" style="cursor:pointer" title="Detalle del Pedido"></div></td> 
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/date.png" width="22" height="22" onClick="abrirVentana('../Clientes/clientesPeriodicidadEntregaFrm.jsp?idcliente=<%=sCampos[3]%>&accion=consulta', 'periodicidad', 800, 450)" style="cursor:pointer" title="Periodicidad del Socio"></div></td>
   </tr>
<%
   }%> 
   </table>
   <input name="accion" value="consultar" type="hidden">
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

