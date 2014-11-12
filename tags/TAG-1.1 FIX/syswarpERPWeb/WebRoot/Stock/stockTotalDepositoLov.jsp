<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vstockTotalDeposito
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Oct 24 16:08:03 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.math.*"%>
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ page import="ar.com.syswarp.ejb.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
GeneralBean gb = new GeneralBean();
String color_fondo ="";

// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVstockTotalDeposito   = null;
int totCol = 5; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVTDA"  class="ar.com.syswarp.web.ejb.BeanVstockTotalDepositoLov"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVTDA" property="*" />
<%
 String titulo = "EXISTENCIA DE  ARTICULO " + BVTDA.getCodigo_st() + " POR DEPOSITO ";
 BVTDA.setResponse(response);
 BVTDA.setRequest(request);
 BVTDA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BVTDA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas

tituCol[0] = "Cód. Art.";
tituCol[1] = "Artículo";
tituCol[2] = "Cód. Dep.";
tituCol[3] = "Déposito";
tituCol[4] = "Total";

java.util.List VstockTotalDeposito = new java.util.ArrayList();
VstockTotalDeposito= BVTDA.getVstockTotalDepositoList();
iterVstockTotalDeposito = VstockTotalDeposito.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="stockTotalDepositoLov.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td width="3%" height="38">&nbsp;                  </td>
                   <td width="97%">&nbsp;
                  </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVTDA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="34%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="38%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
  </tr>
   <%int r = 0;
	   float total = 0;
   while(iterVstockTotalDeposito.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVstockTotalDeposito.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
			total += Float.parseFloat(sCampos[4]);
			%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=gb.getNumeroFormateado(Float.parseFloat(sCampos[4]),10,2)%>&nbsp;</td> 
   </tr>
<%
   }
	 if(r!=0){
	 %>
   <tr class="text-globales" height="3"> 
      <td height="3">&nbsp;</td>
      <td >&nbsp;</td>
      <td >&nbsp;</td>
      <td >&nbsp;</td>
      <td >&nbsp;</td>
   </tr>	 
	 
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='fila-det-bold');" class="fila-det-bold" scope="col" > 
      <td class="fila-det-border" >&nbsp;</td>
      <td class="fila-det-border" >&nbsp;</td>
      <td class="fila-det-border" >&nbsp;</td>
      <td class="fila-det-border" >Total General </td>
      <td class="fila-det-border" ><%=gb.getNumeroFormateado(total,10,2)%>&nbsp;</td>
   </tr>
<%
   }
	 %>	 	 
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

