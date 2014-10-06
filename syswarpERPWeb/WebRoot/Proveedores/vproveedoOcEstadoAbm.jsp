<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vproveedoOcEstado
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Apr 20 15:27:29 ART 2007 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.math.*"%> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "ESTADO  ORDENES DE COMPRA";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVproveedoOcEstado   = null;
int totCol = 16; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVOEA"  class="ar.com.syswarp.web.ejb.BeanVproveedoOcEstadoAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVOEA" property="*" />
<%
 String idempresa = session.getAttribute("empresa").toString() ;
 BVOEA.setResponse(response);
 BVOEA.setRequest(request);
 BVOEA.setIdempresa( new BigDecimal( idempresa ));
 BVOEA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">

 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "idestadooc";
tituCol[2] = "Estado";
tituCol[3] = "idproveedor";
tituCol[4] = "Proveedor";
tituCol[5] = "Fecha";
tituCol[6] = "idcondicionpago";
tituCol[7] = "condicion";
tituCol[8] = "idmoneda";
tituCol[9] = "moneda";
tituCol[10] = "idtipoiva";
tituCol[11] = "tipoiva";
tituCol[12] = "idgrupooc";
tituCol[13] = "Grupo";
tituCol[14] = "Total";
tituCol[15] = "observaciones";
java.util.List VproveedoOcEstado = new java.util.ArrayList();
VproveedoOcEstado= BVOEA.getVproveedoOcEstadoList();
iterVproveedoOcEstado = VproveedoOcEstado.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vproveedoOcEstadoAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td width="11%" height="38">
                      <table width="36%" border="0">
                         <tr>
                            <td width="27%">&nbsp;</td>
                            <td width="27%">
                               <input name="modificacion" id="modificacion" value="modificacion" type="image" src="../imagenes/default/btn_edit_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('modificacion','','../imagenes/default/btn_edit_over.gif',1)" onClick="document.frm.accion.value = this.name">                            </td>
                            <td width="27%">&nbsp;</td>
                         </tr>
                      </table>                   </td>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BVOEA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BVOEA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BVOEA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BVOEA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BVOEA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BVOEA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BVOEA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVOEA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable >
  <tr class="fila-encabezado">
     <td width="5%" >&nbsp;</td>
     <td width="2%" ><%=tituCol[0]%></td>
     <td width="12%" ><%=tituCol[2]%></td>
     <td width="34%" ><%=tituCol[4]%></td>
     <td width="22%" ><%=tituCol[13]%></td>
     <td width="8%" ><%=tituCol[5]%></td>
     <td width="11%" ><%=tituCol[14]%></td>
     <td width="3%" >&nbsp;</td>
     <td width="3%" >&nbsp;</td>
  </tr>
   <%int r = 0;
   while(iterVproveedoOcEstado.hasNext()){
     ++r;
     String[] sCampos = (String[]) iterVproveedoOcEstado.next(); 
     // estos campos hay que setearlos segun la grilla 
     if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
     else color_fondo = "fila-det-verde";
	   String title = "";
	   title += "Condicion: " +  sCampos[7] + "\n";
	   title += "Moneda: " + sCampos[9] + "\n"; 
	   title += "Iva: " + sCampos[11] + "\n";
     String link = "../reportes/jasper/generaPDF.jsp?idoc=" + sCampos[0] + "&plantillaImpresionJRXML=oc_frame&idempresa=" + idempresa ;
	   %>
	 
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>"   > 
      <td class="fila-det-border" ><input type="radio" name="id_oc_cabe" value="<%= sCampos[0]%>"></td>
      <td class="fila-det-border" ><%=sCampos[0]%></td>
      <td class="fila-det-border" ><%=sCampos[1]%> - <%=sCampos[2]%></td>
      <td class="fila-det-border" ><%=sCampos[3]%> - <%=sCampos[4]%></td>
      <td class="fila-det-border" ><%=sCampos[13].trim().equals("") ? "Sin Grupo Asociado."
						: sCampos[13]%>&nbsp;</td>
      <td class="fila-det-border" >&nbsp;<%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[5]), "JSTsToStr")%></td>
      <td class="fila-det-border" ><%=sCampos[14]%></td>
      <td class="fila-det-border" ><div align="center">
								  <a href="javascript:abrirVentana('<%= link %>', '', 800, 600)"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" border="0" ></a>
			</div></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/status/dialog-information.png" width="18" height="18" style="cursor:pointer" title="<%=title%>"></div></td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>"   > 
      <td class="fila-det-border" >OBS.: </td>
      <td colspan="8" class="fila-det-border" ><%=sCampos[15]%>&nbsp;</td>
    </tr>   
   <tr   > 
      <td  colspan="9" height="3" bgcolor="#003366"></td>
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

