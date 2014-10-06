<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: pedidosCambioEstadosLog
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Mar 31 10:01:56 GMT-03:00 2010 
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
String titulo = "";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterPedidosCambioEstadosLog   = null;
int totCol = 11; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPRP"  class="ar.com.syswarp.web.ejb.BeanPedidosRemitosPedido"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPRP" property="*" />
<%
 BPRP.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPRP.setResponse(response);
 BPRP.setRequest(request);
 BPRP.ejecutarValidacion();
 titulo = "Remitos Asociados Para Pedido: " + BPRP.getIdpedido();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
<%
  /*  
 <script language="JavaScript" src="< %=pathskin% >/overlib.js"></script>
 <script language="JavaScript" src="< %=pathscript% >/forms.js"></script>
  */
%>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script> 
 <script>
	function mostrarMensaje(mensaje){
		//overlib( mensaje , STICKY, CAPTION, '[INFO]',TIMEOUT,25000,HAUTO,VAUTO,WIDTH,350,BGCOLOR,'#FF9900');
		overlib( mensaje , STICKY, CAPTION, '[INFO]',TIMEOUT,25000,FIXX,0,FIXY,0,WIDTH,350,BGCOLOR,'#FF9900');  
	} 
 </script>
 

 
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Pedido";
tituCol[2] = "Sucursal";
tituCol[3] = "Comprobante";
tituCol[6] = "Actual";
tituCol[7] = "Conformación";

java.util.List PedidosCambioEstadosLog = new java.util.ArrayList();
PedidosCambioEstadosLog= BPRP.getPedidosCambioEstadosLogList();
iterPedidosCambioEstadosLog = PedidosCambioEstadosLog.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >



<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<form action="pedidosRemitosPedido.jsp" method="POST" name="frm">
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
    <tr class="text-globales">
      <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
     <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td  class="text-globales"><%=titulo%></td>
        </tr>
        <tr>
          <td width="2%" height="38"  class="text-globales">Total de registros:&nbsp;<%=BPRP.getTotalRegistros()%></td>
        </tr>
      </table></td>
    </tr>
  </table>
  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr >
      <td class="fila-det-bold-rojo"><jsp:getProperty name="BPRP" property="mensaje"/></td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
    <tr class="fila-encabezado">
      <td width="2%" rowspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
      <td width="2%" rowspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
      <td width="2%" rowspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
      <td width="2%" rowspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
      <td width="32%" rowspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[3]%></div></td>
      <td colspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Estado</div></td>
    </tr>
    <tr class="fila-encabezado">
      <td width="31%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[6]%></div></td>
      <td width="29%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[7]%></div></td>
    </tr>
    <%int r = 0;
   String plantillaImpresionJRXML = BPRP.getTipopedido().equalsIgnoreCase("N") ? "remitos_clientes_frame" : "remitos_clientes_regalos_frame"; 	
   while(iterPedidosCambioEstadosLog.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterPedidosCambioEstadosLog.next(); 
	  String datosAudit = "<strong>U.Alt.:</strong> " + Common.setNotNull(sCampos[9]);
      datosAudit += "<br><strong>U.Act.:</strong> " + Common.setNotNull(sCampos[10]);
      datosAudit += "<br><strong>F.Alt.:</strong> " +   Common.setObjectToStrOrTime( java.sql.Timestamp.valueOf( Common.setNotNull(sCampos[11]) ), "JSTsToStrWithHM" );
      datosAudit += "<br><strong>F.Act.:</strong> " + (!Common.setNotNull(sCampos[12]).equals("") ? Common.setObjectToStrOrTime( java.sql.Timestamp.valueOf( Common.setNotNull(sCampos[12]) ), "JSTsToStrWithHM" ) : "");
	  datosAudit += "<br><strong> -- CONFORMACION -- </strong>"  ;
      datosAudit += "<br><strong>U.Alt.Conf.:</strong> " + Common.setNotNull(sCampos[13]);
      datosAudit += "<br><strong>U.Act.Conf.:</strong> " + Common.setNotNull(sCampos[14]);
      datosAudit += "<br><strong>F.Alt.Conf.:</strong> " + (!Common.setNotNull(sCampos[15]).equals("") ? Common.setObjectToStrOrTime( java.sql.Timestamp.valueOf( Common.setNotNull(sCampos[15]) ), "JSTsToStrWithHM" ) : "");
      datosAudit += "<br><strong>F.Act.Conf.:</strong> " + (!Common.setNotNull(sCampos[16]).equals("") ? Common.setObjectToStrOrTime( java.sql.Timestamp.valueOf( Common.setNotNull(sCampos[16]) ), "JSTsToStrWithHM" ) : "");
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";//
      else color_fondo = "fila-det-verde";%>
    <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/delivery.gif" width="22" height="22" onClick="abrirVentana('vClientesRemitosDistribucionAbm.jsp?sucursal=<%=sCampos[2]%>&remitocliente=<%=sCampos[3]%>&idremitocliente=<%=sCampos[0]%>&idcliente=<%=sCampos[4]%>&cliente=<%=sCampos[5]%>&tipopedido=<%= BPRP.getTipopedido() %>','detalle', 750, 250)" style="cursor:pointer"></td>
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" border="0" style="cursor:pointer" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=<%= plantillaImpresionJRXML %>&idremitoclientedesde=<%=sCampos[0]%>', 'remitocliente', 750, 500);"></td>
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/apps/kuser.png" width="22" height="22" style="cursor:pointer" onClick="mostrarMensaje('<%= datosAudit %>');"></td>
      <td class="fila-det-border" ><%=sCampos[0]%></td>
      <td class="fila-det-border" ><div align="left" onClick="abrirVentana('clientesRemitoDetalle.jsp?sucursal=<%=sCampos[2]%>&remitocliente=<%=sCampos[3]%>&idremitocliente=<%=sCampos[0]%>&idcliente=<%=sCampos[4]%>&cliente=<%=sCampos[5]%>&tipopedido=<%= BPRP.getTipopedido() %>','detalle', 750, 450)" style="cursor:pointer"> 
        <div align="center"><a href="#"><%= Common.strZero(sCampos[2], 4) + " - " + Common.strZero(sCampos[3], 8)%></a></div>
      </div></td>
      <td class="fila-det-border" ><div align="center"><%= Common.setNotNull( sCampos[6] )%></div></td>
      <td class="fila-det-border" ><div align="center"><%= Common.setNotNull( sCampos[7] )%></div></td>
    </tr>
    <%
   }%>
  </table>
  <input name="accion" value="" type="hidden">
  <input name="idpedido" value="<%= BPRP.getIdpedido()%>" type="hidden">
  <input name="tipopedido" type="hidden" id="tipopedido" value="<%= BPRP.getTipopedido()%>">
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
