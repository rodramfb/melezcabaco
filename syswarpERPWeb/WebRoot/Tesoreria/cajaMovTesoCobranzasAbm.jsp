<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: cajaMovTesoCobranzas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed May 13 11:50:20 GYT 2009 
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
String titulo = "ANULACION DE COBRANZAS - INGRESOS DIRECTOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterCajaMovTesoCobranzas   = null;
int totCol = 11; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCMTCA"  class="ar.com.syswarp.web.ejb.BeanCajaMovTesoCobranzasAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCMTCA" property="*" />
<%
 /*
 Enumeration en = session.getAttributeNames();
  while (en.hasMoreElements()) {
     String var = en.nextElement().toString();
     System.out.println("VAR: " + var + " =  " + session.getAttribute(var) );
  }
*/

 BCMTCA.setIdempresa(new BigDecimal(session.getAttribute("empresa").toString())); 
 BCMTCA.setUsuarioact(usuario);
 BCMTCA.setResponse(response);
 BCMTCA.setRequest(request);
 BCMTCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
 
  function callConfirmaBaja(sucursal, comprobante, idcliente, fechamo_mt){
    var msj = 'Est� seguro que desea anular el comprobante de cobranza Nro.: ' + sucursal + '-' + comprobante;
    msj += '?\nTodas las aplicaciones que involucren a este documento ser�n afectadas.';

    if( confirm(msj) ){
      document.frm.accion.value = 'baja';
      document.frm.sucursal.value = sucursal;
      document.frm.comprobante.value = comprobante;
      document.frm.idcliente.value = idcliente;
      document.frm.fechamo_mt.value = fechamo_mt;
      document.frm.submit();
    }

  }

 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Sucursal";
tituCol[1] = "Comprobante";
tituCol[2] = "Importe";
tituCol[3] = "nrointerno";
tituCol[4] = "idcliente";
tituCol[5] = "Cliente";
tituCol[6] = "Fecha";
tituCol[7] = "Obs.";
tituCol[8] = "tipomov_mt";
tituCol[9] = "fecha_mt";
tituCol[10] = "tipo_mt";
java.util.List CajaMovTesoCobranzas = new java.util.ArrayList();
CajaMovTesoCobranzas= BCMTCA.getCajaMovTesoCobranzasList();
iterCajaMovTesoCobranzas = CajaMovTesoCobranzas.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="cajaMovTesoCobranzasAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="11%" height="38">&nbsp;</td>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BCMTCA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BCMTCA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit"  class="campo">
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BCMTCA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCMTCA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCMTCA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion"  class="campo">
                                                <%for(i=1; i<= BCMTCA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BCMTCA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCMTCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[1]%></div></td>
     <td width="13%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[2]%>&nbsp;</div></td>
     <td width="38%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="27%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
    </tr>
   <%int r = 0;
   while(iterCajaMovTesoCobranzas.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterCajaMovTesoCobranzas.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/status/gtk-missing-image.png" title="Anular - Borrar Comprobante de Cobranza <%=sCampos[0]%> - <%=sCampos[1]%>" width="22" height="22" onClick="callConfirmaBaja('<%=sCampos[0]%>', '<%=sCampos[1]%>', '<%=sCampos[4]%>', '<%= sCampos[6]%>');"></td>
      <td class="fila-det-border" ><div align="center"><%=sCampos[0]%>&nbsp;- <%=sCampos[1]%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[2]%>&nbsp;</div></td> 
      <td class="fila-det-border" ><%=sCampos[4]%> - <%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[6]), "JSTsToStr")%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setNotNull(sCampos[7])%>&nbsp;</td>
    </tr>
<%
   }%>
  </table>
   <input name="accion" value="" type="hidden">
   <input name="sucursal" value="-1" type="hidden">
   <input name="comprobante" value="-1" type="hidden">
   <input name="idcliente" type="hidden" id="idcliente" value="-1">
   <input name="fechamo_mt" type="hidden" id="fechamo_mt">
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

