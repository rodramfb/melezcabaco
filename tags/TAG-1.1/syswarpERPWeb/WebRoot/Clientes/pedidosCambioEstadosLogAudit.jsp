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
<jsp:useBean id="BPCELA"  class="ar.com.syswarp.web.ejb.BeanPedidosCambioEstadosLogAudit"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPCELA" property="*" />
<%
 BPCELA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPCELA.setResponse(response);
 BPCELA.setRequest(request);
 BPCELA.ejecutarValidacion();
 titulo = "Auditoria Anulaciones - Rechazos Para Pedido: " + BPCELA.getIdpedido();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Pedido";
tituCol[2] = "idestadoanterior";
tituCol[3] = "Estado Anterior";
tituCol[4] = "idestadonuevo";
tituCol[5] = "Estado Nuevo";
tituCol[6] = "idempresa";
tituCol[7] = "Usuario";
tituCol[8] = "usuarioact";
tituCol[9] = "Fecha - Hora ";
tituCol[10] = "fechaact";
java.util.List PedidosCambioEstadosLog = new java.util.ArrayList();
PedidosCambioEstadosLog= BPCELA.getPedidosCambioEstadosLogList();
iterPedidosCambioEstadosLog = PedidosCambioEstadosLog.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >



<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<form action="pedidosCambioEstadosLogAudit.jsp" method="POST" name="frm">
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
    <tr class="text-globales">
      <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
     <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td  class="text-globales"><%=titulo%></td>
        </tr>
        <tr>
          <td width="2%" height="38"  class="text-globales">Total de registros:&nbsp;<%=BPCELA.getTotalRegistros()%></td>
        </tr>
      </table></td>
    </tr>
  </table>
  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr >
      <td class="fila-det-bold-rojo"><jsp:getProperty name="BPCELA" property="mensaje"/></td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
    <tr class="fila-encabezado">
      <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
      <td width="27%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
      <td width="23%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
      <td width="23%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
      <td width="24%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>
    </tr>
    <%int r = 0;
   while(iterPedidosCambioEstadosLog.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterPedidosCambioEstadosLog.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
    <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
      <td class="fila-det-border" ><%=sCampos[0]%></td>
      <td class="fila-det-border" ><%=sCampos[2] + " - " +sCampos[3]%></td>
      <td class="fila-det-border" ><%=sCampos[4] + " - " + sCampos[5]%></td>
      <td class="fila-det-border" ><%=sCampos[7].toUpperCase()%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[9]), "JSTsToStrWithHM")%>&nbsp;</td>
    </tr>
    <%
   }%>
  </table>
  <input name="accion" value="" type="hidden">
  <input name="idpedido" value="<%= BPCELA.getIdpedido()%>" type="hidden">
  <input name="tipopedido" value="<%= BPCELA.getTipopedido()%>" type="hidden">
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
