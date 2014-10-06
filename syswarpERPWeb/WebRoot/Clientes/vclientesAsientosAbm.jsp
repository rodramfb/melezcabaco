<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vclientesAsientos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Mar 25 10:59:30 GYT 2009 
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
String titulo = "ASIENTO CONTABLE CORRESPONDIENTE AL COMPROBANTE: "  ;
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVclientesAsientos   = null;
int totCol = 6; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVAA"  class="ar.com.syswarp.web.ejb.BeanVclientesAsientosAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVAA" property="*" />
<%
 BVAA.setResponse(response);
 BVAA.setRequest(request);
 BVAA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BVAA.ejecutarValidacion();
%>
<head>
<title><%=titulo + BVAA.getComprobante() %></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "nrointerno";
tituCol[1] = "Cuenta";
tituCol[2] = "Descripción Cuenta";
tituCol[3] = "tip";
tituCol[4] = "tipdh";
tituCol[5] = "importe";
java.util.List VclientesAsientos = new java.util.ArrayList();
VclientesAsientos= BVAA.getVclientesAsientosList();
iterVclientesAsientos = VclientesAsientos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vclientesAsientosAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td height="29"><%=titulo + BVAA.getComprobante() %></td>
                </tr>
                <tr>
                  <td width="89%" height="38">&nbsp;</td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVAA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td width="19%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="55%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right">Debe</div></td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right">Haber</div></td>
    </tr>
   <%int r = 0;
   while(iterVclientesAsientos.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVclientesAsientos.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><%=sCampos[1]%></td> 
      <td class="fila-det-border" ><%=sCampos[2]%></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[3].equals("D") ? Common.visualNumero( sCampos[5], 2 ) : "" %>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[3].equals("H") ? Common.visualNumero( sCampos[5], 2 ) : "" %>&nbsp;</div></td>
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

