<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vBacoReferidosEvolucion
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Apr 13 14:42:13 ART 2012 
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
String titulo = "EVOLUCIÓN DE MOVIMIENTOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVBacoReferidosEvolucion   = null;
int totCol = 7; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVBREA"  class="ar.com.syswarp.web.ejb.BeanVBacoReferidosEvolucionAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVBREA" property="*" />
<%
 BVBREA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BVBREA.setResponse(response);
 BVBREA.setRequest(request);
 BVBREA.ejecutarValidacion();
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
tituCol[1] = "Cliente";
tituCol[2] = "Movimiento";
tituCol[3] = "Fecha";
tituCol[4] = "Detalle";
tituCol[5] = "idpedido_cabe";
tituCol[6] = "idempresa";
java.util.List VBacoReferidosEvolucion = new java.util.ArrayList();
VBacoReferidosEvolucion= BVBREA.getVBacoReferidosEvolucionList();
iterVBacoReferidosEvolucion = VBacoReferidosEvolucion.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vBacoReferidosEvolucionAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="32"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="11%" height="38"><table width="100%" border="0">
                    <tr>
                      <td width="6%" height="26" class="text-globales">Buscar</td>
                      <td width="22%"><input name="ocurrencia" type="text" value="<%=BVBREA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                      </td>
                      <td width="72%"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr class="text-globales">
                                  <td width="1%" height="19">&nbsp;</td>
                                  <td width="23%">&nbsp;Total de registros:&nbsp;<%=BVBREA.getTotalRegistros()%></td>
                                  <td width="11%" >Visualizar:</td>
                                  <td width="11%"><select name="limit" >
                                      <%for(i=15; i<= 150 ; i+=15){%>
                                      <%if(i==BVBREA.getLimit()){%>
                                      <option value="<%=i%>" selected><%=i%></option>
                                      <%}else{%>
                                      <option value="<%=i%>"><%=i%></option>
                                      <%}
                                                      if( i >= BVBREA.getTotalRegistros() ) break;
                                                    %>
                                      <%}%>
                                      <option value="<%= BVBREA.getTotalRegistros()%>">Todos</option>
                                    </select>                                  </td>
                                  <td width="7%">&nbsp;P&aacute;gina:</td>
                                  <td width="12%"><select name="paginaSeleccion" >
                                      <%for(i=1; i<= BVBREA.getTotalPaginas(); i++){%>
                                      <%if ( i==BVBREA.getPaginaSeleccion() ){%>
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
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVBREA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[0]%></div></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[1]%></div></td>
     <td width="44%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="24%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[3]%></div></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
    </tr>
   <%int r = 0;
   while(iterVBacoReferidosEvolucion.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVBacoReferidosEvolucion.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="right"><%=sCampos[0]%></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[1]%></div></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[2]%></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[3]), "JSDateToStr")%></div></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[4]%></td>
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

