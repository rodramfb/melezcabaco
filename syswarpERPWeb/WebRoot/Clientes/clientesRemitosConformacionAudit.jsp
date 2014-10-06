<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: clientesRemitosConformacion
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Dec 01 14:23:57 ART 2010 
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
Iterator iterClientesRemitosConformacion   = null;
int totCol = 10; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCRCA"  class="ar.com.syswarp.web.ejb.BeanClientesRemitosConformacionAudit"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCRCA" property="*" />
<%
 String titulo = "AUDITORIA DE CAMBIO DE ESTADO DE REMITO: " + Common.strZero(BCRCA.getSucursal(), 4) + "-" + Common.strZero(BCRCA.getRemitocliente(), 8)  ;
 BCRCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BCRCA.setResponse(response);
 BCRCA.setRequest(request);
 BCRCA.ejecutarValidacion();
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
tituCol[1] = "idremitocliente";
tituCol[2] = "idestado";
tituCol[3] = "Estado";
tituCol[4] = "procesado";
tituCol[5] = "idempresa";
tituCol[6] = "U.Alta";
tituCol[7] = "U.Act.";
tituCol[8] = "F.Alta";
tituCol[9] = "U.Act.";
java.util.List ClientesRemitosConformacion = new java.util.ArrayList();
ClientesRemitosConformacion= BCRCA.getClientesRemitosConformacionList();
iterClientesRemitosConformacion = ClientesRemitosConformacion.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="clientesRemitosConformacionAudit.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                  <td height="39"><img src="../imagenes/default/gnome_tango/apps/config-users.png" width="25" height="25"></td>
                   <td><%=titulo%></td>
                </tr>
                <tr>
                  <td height="5" colspan="2"><hr color="#FFFFFF"></td>
                </tr>
                <tr>
                  <td width="4%">&nbsp;</td>
                  <td width="96%" height="38">
                     <table width="100%" border="0">
                        <tr>
                          <td width="72%" height="26">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="27%" height="19">&nbsp;Total de registros:&nbsp;<%=BCRCA.getTotalRegistros()%></td>
                                         <td width="14%" >Visualizar:</td>
                                         <td width="14%">
                                            <select name="limit" >
                                               <%for(i=15; i<= 150 ; i+=15){%>
                                                   <%if(i==BCRCA.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                   <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                   <%}
                                                      if( i >= BCRCA.getTotalRegistros() ) break;
                                                    %>
                                               <%}%>
                                               <option value="<%= BCRCA.getTotalRegistros()%>">Todos</option>
                                            </select>                                          </td>
                                         <td width="11%">&nbsp;P&aacute;gina:</td>
                                         <td width="15%">
                                            <select name="paginaSeleccion" >
                                               <%for(i=1; i<= BCRCA.getTotalPaginas(); i++){%>
                                                   <%if ( i==BCRCA.getPaginaSeleccion() ){%>
                                                      <option value="<%=i%>" selected><%=i%></option> 
                                                   <%}else{%>
                                                      <option value="<%=i%>"><%=i%></option>
                                                   <%}%>
                                               <%}%>
                                            </select>                                          </td>
                                         <td width="14%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCRCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="21%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="17%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="17%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td width="22%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td width="17%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>
  </tr>
   <%int r = 0;
   while(iterClientesRemitosConformacion.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterClientesRemitosConformacion.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setNotNull(sCampos[6])%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setNotNull(sCampos[7])%>&nbsp;</td>
      <td class="fila-det-border" ><%=(!Common.setNotNull(sCampos[8]).equals("") ? Common.setObjectToStrOrTime( java.sql.Timestamp.valueOf( Common.setNotNull(sCampos[8]) ), "JSTsToStrWithHM" ) : "")%>&nbsp;</td>
      <td class="fila-det-border" ><%=(!Common.setNotNull(sCampos[9]).equals("") ? Common.setObjectToStrOrTime( java.sql.Timestamp.valueOf( Common.setNotNull(sCampos[9]) ), "JSTsToStrWithHM" ) : "")%>&nbsp;</td>
   </tr>
<%
   }%>
  </table>
   <input name="accion" value="" type="hidden">
   <input name="idremitocliente" type="hidden" id="idremitocliente" value="<%= BCRCA.getIdremitocliente() %>">
   <input name="sucursal" type="hidden" id="sucursal" value="<%= BCRCA.getSucursal() %>">
   <input name="remitocliente" type="hidden" id="remitocliente" value="<%= BCRCA.getRemitocliente() %>">
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

