<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesRemitosDistribucion
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Aug 23 13:37:09 ART 2010 
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
String titulo = "REMITOS - DISTRIBUCION";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesRemitosDistribucion   = null;
int totCol = 20; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCRDA"  class="ar.com.syswarp.web.ejb.BeanVClientesRemitosDistribucionAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCRDA" property="*" />
<%
 BVCRDA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BVCRDA.setResponse(response);
 BVCRDA.setRequest(request);
 BVCRDA.ejecutarValidacion();
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
tituCol[1] = "Fecha";
tituCol[2] = "Suc.";
tituCol[3] = "Remito";
tituCol[4] = "CClie";
tituCol[5] = "Cliente";
tituCol[6] = "idZona";
tituCol[7] = "Zona";
tituCol[8] = "idExpreso";
tituCol[9] = "Expreso";
tituCol[10] = "CtaCte";
tituCol[11] = "Tipo";
tituCol[12] = "CExpZona";
tituCol[13] = "Bultos";
tituCol[14] = "Kilos";
tituCol[15] = "H.Arm.";
tituCol[16] = "F. HA";
tituCol[17] = "H.Ruta";
tituCol[18] = "F. HR";
tituCol[19] = "Flete";
java.util.List VClientesRemitosDistribucion = new java.util.ArrayList();
VClientesRemitosDistribucion= BVCRDA.getVClientesRemitosDistribucionList();
iterVClientesRemitosDistribucion = VClientesRemitosDistribucion.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesRemitosDistribucionAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td height="31"  ><%=titulo%></td>
                </tr>
                <tr>
                  <td width="5%" height="15"><hr color="#FFFFFF"></td>
              </tr>
                <tr class="text-globales">
                  <td height="15">CLIENTE: <%=BVCRDA.getIdcliente() + " - " + BVCRDA.getCliente() %> / REMITO: <%= Common.strZero(BVCRDA.getSucursal() , 4 )+ " - " + Common.strZero(BVCRDA.getRemitocliente(), 8) %> / TIPO PEDIDO: <%=BVCRDA.getTipopedido().equalsIgnoreCase("N") ? "Normal" : "Regalos" %></td>
                </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCRDA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="23%" rowspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td width="22%" rowspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>
     <td width="13%" rowspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[13]%></div></td>
     <td width="9%" rowspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[14]%></div></td>
     <td colspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">
       Hoja Armado 
       </div></td>
     <td colspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Hoja Ruta </div></td>
     <td width="6%" rowspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[19]%></div></td>
  </tr>
  <tr class="fila-encabezado">
    <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right">Nro.</div></td>
    <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Fecha</div></td>
    <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right">Nro</div></td>
    <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Fecha</div></td>
    </tr>
   <%int r = 0;
   while(iterVClientesRemitosDistribucion.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesRemitosDistribucion.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[9]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[13]%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado (Double.parseDouble(sCampos[14]), 10, 2 )%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.setNotNull(sCampos[15])%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setNotNull(sCampos[16]).equals("") ? "" : Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[16]), "JSDateToStr" )%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.setNotNull(sCampos[17])%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setNotNull(sCampos[18]).equals("") ? "" : Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[18]), "JSDateToStr" )%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado (Double.parseDouble(sCampos[19]), 10, 2 )%></div></td>
   </tr>
<%
   }%>
  </table>
   <input name="accion" value="" type="hidden">
   <input name="idremitocliente" type="hidden" id="idremitocliente" value="<%=BVCRDA.getIdremitocliente()%>">
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

