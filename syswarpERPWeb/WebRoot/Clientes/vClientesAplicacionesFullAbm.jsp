<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesAplicacionesFull
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Sep 19 10:35:59 ART 2012 
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
String titulo = "DESAPLICAR COMPROBANTES DE CLIENTES";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesAplicacionesFull   = null;
int totCol = 21; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCAFA"  class="ar.com.syswarp.web.ejb.BeanVClientesAplicacionesFullAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCAFA" property="*" />
<%

 BVCAFA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BVCAFA.setUsuarioact(usuario);
 BVCAFA.setResponse(response);
 BVCAFA.setRequest(request);
 BVCAFA.ejecutarValidacion();

%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
  <script>
 
 function desaplicarComprobantes(nrointerno_ap, comprobante_ap, nrointerno_qcan, comprobante_qcqn){ 
   if(confirm('Esta seguro que desea desaplicar el comprobante: ' + comprobante_ap + ',\ncancelado por el comprobante: ' + comprobante_qcqn + '?')){  
     document.frm.nrointerno_ap.value = nrointerno_ap;
	 document.frm.nrointerno_qcan.value = nrointerno_qcan;
	 document.frm.accion.value = 'desaplicar';
	 document.frm.submit();   
   }
 }
 
 function docCuotas(nrointerno_ap, comprobante_ap, nrointerno_qcan, comprobante_qcqn){ 
    alert('imposible desaplicar el comprobante: ' + comprobante_ap + ',\ncancelado por el comprobante: ' + comprobante_qcqn + ' porque poseen cuotas');
    return false;   
 }

 
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "idcliente";
tituCol[1] = "razon";
tituCol[2] = "nrointerno_ap";
tituCol[3] = "tipomov_ap";
tituCol[4] = "tipomovs_ap";
tituCol[5] = "sucursal_ap";
tituCol[6] = "comprob_ap";
tituCol[7] = "Saldo";
tituCol[8] = "Importe";
tituCol[9] = "Comprobante";
tituCol[10] = "nrointerno_qc";
tituCol[11] = "tipomov_qc";
tituCol[12] = "tipomovs_qc";
tituCol[13] = "sucursal_qc";
tituCol[14] = "comprob_qc";
tituCol[15] = "Saldo";
tituCol[16] = "Importe";
tituCol[17] = "Comprobante";
tituCol[18] = "Importe";
tituCol[19] = "Fecha";
tituCol[20] = "idempresa";
java.util.List VClientesAplicacionesFull = new java.util.ArrayList();
VClientesAplicacionesFull= BVCAFA.getVClientesAplicacionesFullList();
iterVClientesAplicacionesFull = VClientesAplicacionesFull.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesAplicacionesFullAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                  <td height="60"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="89%" height="38">
                     <table width="100%" border="0">
                        <tr>
                          <td width="13%" height="26" class="text-globales"><span class="fila-det-border">Cliente: (*)
                              <input name="idcliente" type="hidden" id="idcliente" value="<%=BVCAFA.getIdcliente()%>">
                          </span></td>
                          <td width="18%"><table width="41%" border="0">
                            <tr class="fila-det-border">
                              <td width="14%" >&nbsp;</td>
                              <td width="69%"><input name="cliente" type="text" id="cliente" size="30" maxlength="45" readonly="true" value="<%=BVCAFA.getCliente()%>"></td>
                              <td width="17%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_clientes.jsp')" style="cursor:pointer"></td>
                            </tr>
                          </table></td>
                          <td width="69%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="0%" height="19">&nbsp; </td>
                                         <td width="39%">&nbsp;Total de registros:&nbsp;<%=BVCAFA.getTotalRegistros()%></td>
                                         <td width="15%" >Visualizar:</td>
                                         <td width="11%">
                                            <select name="limit" >
                                               <%for(i=15; i<= 150 ; i+=15){%>
                                                   <%if(i==BVCAFA.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                   <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                   <%}
                                                      if( i >= BVCAFA.getTotalRegistros() ) break;
                                                    %>
                                               <%}%>
                                               <option value="<%= BVCAFA.getTotalRegistros()%>">Todos</option>
                                         </select>                                          </td>
                                         <td width="9%">&nbsp;P&aacute;gina:</td>
                                         <td width="13%">
                                            <select name="paginaSeleccion" >
                                               <%for(i=1; i<= BVCAFA.getTotalPaginas(); i++){%>
                                                   <%if ( i==BVCAFA.getPaginaSeleccion() ){%>
                                                      <option value="<%=i%>" selected><%=i%></option> 
                                                   <%}else{%>
                                                      <option value="<%=i%>"><%=i%></option>
                                                   <%}%>
                                               <%}%>
                                         </select>                                          </td>
                                         <td width="13%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCAFA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="2" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="text-dos-bold">
    <td >&nbsp;</td>
    <td colspan="3" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">DOCUMENTO CANCELADO </div></td>
    <td colspan="3" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"> DOCUMENTO QUE CANCELA </div></td>
    <td colspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">CANCELACION</div></td>
    </tr>
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[9]%></div></td>
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[7]%></div></td>
     <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[8]%></div></td>
     <td width="13%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[17]%></div></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[15]%></div></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[16]%></div></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[19]%></div></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[18]%></div></td>
    </tr>
   <%int r = 0;
   while(iterVClientesAplicacionesFull.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesAplicacionesFull.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
      <%if(sCampos[25].equalsIgnoreCase("N") && sCampos[26].equalsIgnoreCase("N")){ %>
      <%System.out.println("Tiene cuotas 1: " + sCampos[25] ); %>
      <%System.out.println("Tiene cuotas 2: " + sCampos[26] ); %>
         <td class="fila-det-border" ><div align="center" title="Desaplicar documentos <%=sCampos[9]%><||><%=sCampos[17]%>"><img src="../imagenes/default/gnome_tango/emblems/icon_unlink.gif" width="22" height="22" onClick="desaplicarComprobantes(<%=sCampos[2]%>, '<%=sCampos[9]%>', <%=sCampos[10]%>, '<%=sCampos[17]%>');"  ></div></td>
      <%}else{%>   
         <td class="fila-det-border" ><div align="center" title="Documentos relacionados a Cuotas <%=sCampos[9]%><||><%=sCampos[17]%>"><img src="../imagenes/default/gnome_tango/emblems/emblem-readonly.png" width="22" height="22" onClick="docCuotas(<%=sCampos[2]%>, '<%=sCampos[9]%>', <%=sCampos[10]%>, '<%=sCampos[17]%>');"  ></div></td>  
      <%}%>
      <td bgcolor="#DBFFCE" class="fila-det-border" ><div align="center"><%=sCampos[9]%></div></td>
      <td bgcolor="#DBFFCE" class="fila-det-border" ><div align="right"><%=sCampos[7]%></div></td>
      <td bgcolor="#DBFFCE" class="fila-det-border" ><div align="right"><%=sCampos[8]%></div></td>
      <td bgcolor="#EBD4BF" class="fila-det-border" ><div align="center"><%=sCampos[17]%></div></td>
      <td bgcolor="#EBD4BF" class="fila-det-border" ><div align="right"><%=sCampos[15]%></div></td>
      <td bgcolor="#EBD4BF" class="fila-det-border" ><div align="right"><%=sCampos[16]%></div></td>
      <td bgcolor="#D9D9FF" class="fila-det-border" ><div align="center"><%= Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[19]), "JSTsToStr") %></div></td>
      <td bgcolor="#D9D9FF" class="fila-det-border" ><div align="right"><%=sCampos[18]%></div></td>
    </tr>
<%
   }%>
  </table>
  
   <input name="accion" value="" type="hidden">
   <input name="recargar" type="hidden" id="recargar" value="1">
   <input name="nrointerno_ap" type="hidden" id="nrointerno_ap" value="0">
   <input name="nrointerno_qcan" type="hidden" id="nrointerno_qcan" value="0">
   
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

