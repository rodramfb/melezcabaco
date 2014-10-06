<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vreImpresionRecibos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Sep 27 14:09:35 ART 2007 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "RE-IMPRESION DE RECIBOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVreImpresionRecibos   = null;
int totCol = 5; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVIRA"  class="ar.com.syswarp.web.ejb.BeanVreImpresionRecibosAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVIRA" property="*" />
<%
 java.math.BigDecimal idempresa = new java.math.BigDecimal(session.getAttribute("empresa").toString());
 BVIRA.setResponse(response);
 BVIRA.setRequest(request);
 BVIRA.setIdempresa(idempresa);
 BVIRA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>/tnx.css" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Comprobante";
tituCol[1] = "Fecha";
tituCol[2] = "Importe";
tituCol[3] = "Cliente";
tituCol[4] = "CUIT";
java.util.List VreImpresionRecibos = new java.util.ArrayList();
VreImpresionRecibos= BVIRA.getVreImpresionRecibosList();
iterVreImpresionRecibos = VreImpresionRecibos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vreImpresionRecibosAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr >
                   <td class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="94%" height="38">
                     <table width="100%" border="0">
                        <tr>
                          <td width="6%" height="26" class="text-globales">Buscar</td>
                          <td width="22%">
                             <input name="ocurrencia" type="text" value="<%=BVIRA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                          <td width="72%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="1%" height="19">&nbsp; </td>
                                         <td width="23%">&nbsp;Total de registros:&nbsp;<%=BVIRA.getTotalRegistros()%></td>
                                         <td width="11%" >Visualizar:</td>
                                         <td width="11%">
                                            <select name="limit" class="campo">
                                               <%for(i=15; i<= 150 ; i+=15){%>
                                                   <%if(i==BVIRA.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                   <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                   <%}
                                                      if( i >= BVIRA.getTotalRegistros() ) break;
                                                    %>
                                               <%}%>
                                               <option value="<%= BVIRA.getTotalRegistros()%>">Todos</option>
                                            </select>                                          </td>
                                         <td width="7%">&nbsp;P&aacute;gina:</td>
                                         <td width="12%">
                                            <select name="paginaSeleccion"  class="campo">
                                               <%for(i=1; i<= BVIRA.getTotalPaginas(); i++){%>
                                                   <%if ( i==BVIRA.getPaginaSeleccion() ){%>
                                                      <option value="<%=i%>" selected><%=i%></option> 
                                                   <%}else{%>
                                                      <option value="<%=i%>"><%=i%></option>
                                                   <%}%>
                                               <%}%>
                                            </select>                                          </td>
                                         <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                      </tr>
                                   </table>                                 </td>
                              </tr>
                          </table>                        </td>
                     </tr>
                   </table>                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVIRA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[0]%></div></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[1]%></div></td>
     <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[2]%></div></td>
     <td width="41%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
  </tr>
   <%int r = 0;
   while(iterVreImpresionRecibos.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVreImpresionRecibos.next(); 
	  String link = "../reportes/jasper/generaPDF.jsp?comprobante=" + Integer.parseInt(sCampos[0]) + "&plantillaImpresionJRXML=modelo.recibo&idempresa=" + idempresa ;
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center"><%=sCampos[0] %></div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[1]), "JSTsToStr")%></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[2]%>&nbsp;&nbsp;</div></td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="center"><a href="javascript:abrirVentana('<%= link %>', '', 800, 600)"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" border="0"></a></div></td>
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

