<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesAplicaciones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Sep 28 17:30:20 ART 2011 
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

// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesAplicaciones   = null;
int totCol = 11; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCAA"  class="ar.com.syswarp.web.ejb.BeanVClientesAplicacionesAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCAA" property="*" />
<%
 String titulo = "APLICACIONES DE DOCUMENTOS DE CLIENTE: " + BVCAA.getCliente();
 BVCAA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BVCAA.setResponse(response);
 BVCAA.setRequest(request);
 BVCAA.ejecutarValidacion();
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
tituCol[1] = "Razon";
tituCol[2] = "Nro.Int.";
tituCol[3] = "Comprobante";
tituCol[4] = "Accion";
tituCol[5] = "Fecha";
tituCol[6] = "Importe";
tituCol[7] = "Nro.Int.Ap";
tituCol[8] = "Comprob.Ap";
tituCol[9] = "comprobante";
tituCol[10] = "accion";

java.util.List VClientesAplicaciones = new java.util.ArrayList();
VClientesAplicaciones= BVCAA.getVClientesAplicacionesList();
iterVClientesAplicaciones = VClientesAplicaciones.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesAplicacionesAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="31"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="11%" height="25"><table width="100%" border="0">
                    <tr>
                      <td width="72%" height="25"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr class="text-globales">
                                  <td height="3px" colspan="7" bgcolor="#FFFFFF"> </td>
                                </tr>
                                <tr class="text-globales">
                                  <td width="1%" height="25">&nbsp;</td>
                                  <td width="23%">&nbsp;Total de registros:&nbsp;<%=BVCAA.getTotalRegistros()%></td>
                                  <td width="11%" >Visualizar:</td>
                                  <td width="11%"><select name="limit" >
                                      <%for(i=15; i<= 150 ; i+=15){%>
                                      <%if(i==BVCAA.getLimit()){%>
                                      <option value="<%=i%>" selected><%=i%></option>
                                      <%}else{%>
                                      <option value="<%=i%>"><%=i%></option>
                                      <%}
                                                      if( i >= BVCAA.getTotalRegistros() ) break;
                                                    %>
                                      <%}%>
                                      <option value="<%= BVCAA.getTotalRegistros()%>">Todos</option>
                                    </select>                                  </td>
                                  <td width="7%">&nbsp;P&aacute;gina:</td>
                                  <td width="12%"><select name="paginaSeleccion" >
                                      <%for(i=1; i<= BVCAA.getTotalPaginas(); i++){%>
                                      <%if ( i==BVCAA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCAA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[3]%></div></td>
     <td width="17%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[4]%></div></td>
     <td width="20%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[5]%></div></td>
     <td width="19%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[6]%></div></td>
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[8]%></div></td>
   </tr>
   <%int r = 0;
   while(iterVClientesAplicaciones.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesAplicaciones.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center"><%=sCampos[3]%></div></td>
      <td class="fila-det-border" ><div align="center"><%=sCampos[4]%></div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[5]), "JSTsToStr")%></div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.visualNumero( sCampos[6], 2 )%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="center"><%=sCampos[8]%></div></td>
   </tr>
<%
   }%>
  </table>
   <input name="accion" value="" type="hidden">
   <input name="idcliente" type="hidden" id="idcliente" value="<%=BVCAA.getIdcliente()%>">
   <input name="cliente" type="hidden" id="cliente" value="<%=BVCAA.getCliente()%>">
   <input name="nrointerno" type="hidden" id="nrointerno" value="<%=BVCAA.getNrointerno()%>">   
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

