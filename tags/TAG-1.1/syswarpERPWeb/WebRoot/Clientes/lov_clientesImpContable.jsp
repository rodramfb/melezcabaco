<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: clientesTipoComp
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jun 14 10:37:10 ART 2007 
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
String color_fondo ="fila-det-bold";
String titulo = "IMPUTACION CONTABLE";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterClientesTipoComp   = null;
int totCol = 1; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCTCA"  class="ar.com.syswarp.web.ejb.BeanClientesTipoCompAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCTCA" property="*" />
<%
 BCTCA.setResponse(response);
 BCTCA.setRequest(request);
 BCTCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));   
 BCTCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "";

java.util.List ClientesTipoComp = new java.util.ArrayList();
ClientesTipoComp= BCTCA.getClientesTipoCompList();
iterClientesTipoComp = ClientesTipoComp.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_clientesImpContable.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="11%">&nbsp;</td>
                  <td width="89%">&nbsp;</td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCTCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="31%" height="39" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">IMPUTACION</td>
     <td colspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">CUENTA</td>
    </tr>
   <tr  class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" >Neto Gravado &nbsp;</td>
      <td width="18%" class="fila-det-border" ><input name="cuentanetogravado" id="cuentanetogravado" type="text" value="<%//=BPMPF.getNetogravado()%>" class="campo" size="20" maxlength="20" /></td>
      <td width="51%" class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/edit-find-replace.png" width="18" height="18" onClick="abrirVentana('lov_clientesInfiPlanAbm.jsp?elemento=cuentanetogravado', 'imputacion', 700, 200)" style="cursor:pointer"></td>
   </tr>
   <tr  class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" >Neto Exento </td>
     <td class="fila-det-border" ><input name="cuentanetoexento" id="cuentanetoexento" type="text" value="<%//=BPMPF.getNetogravado()%>" class="campo" size="20" maxlength="20" /></td>
     <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/edit-find-replace.png" width="18" height="18" onClick="abrirVentana('lov_clientesInfiPlanAbm.jsp?elemento=cuentanetoexento', 'imputacion', 700, 400)" style="cursor:pointer"></td>
   </tr>
   <tr  class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" >IVA</td>
     <td class="fila-det-border" ><input name="cuentaiva" id="cuentaiva" type="text" value="<%//=BPMPF.getNetogravado()%>" class="campo" size="20" maxlength="20" /></td>
     <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/edit-find-replace.png" width="18" height="18" onClick="abrirVentana('lov_clientesInfiPlanAbm.jsp?elemento=cuentaiva', 'imputacion', 700, 400)" style="cursor:pointer"></td>
   </tr>
   <tr  class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" >IVA No Inscripto </td>
     <td class="fila-det-border" ><input name="cuentaivanoinscripto" id="cuentaivanoinscripto" type="text" value="<%//=BPMPF.getNetogravado()%>" class="campo" size="20" maxlength="20" /></td>
     <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/edit-find-replace.png" width="18" height="18" onClick="abrirVentana('lov_clientesInfiPlanAbm.jsp?elemento=cuentaivanoinscripto', 'imputacion', 700, 400)" style="cursor:pointer"></td>
   </tr>
   <tr  class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" >Total</td>
     <td class="fila-det-border" ><input name="cuentatotal" id="cuentatotal" type="text" value="<%//=BPMPF.getNetogravado()%>" class="campo" size="20" maxlength="20" /></td>
     <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/edit-find-replace.png" width="18" height="18" onClick="abrirVentana('lov_clientesInfiPlanAbm.jsp?elemento=cuentatotal', 'imputacion', 700, 400)" style="cursor:pointer"></td>
   </tr>
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

