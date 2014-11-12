<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: Remitos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Nov 06 11:36:23 GMT-03:00 2009 
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
String titulo = "ANULACION - DESVINCULACION DE REMITOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
int r = 0;
Iterator iterRemitos   = null;
int totCol = 12; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BBOEA"  class="ar.com.syswarp.web.ejb.BeanClientesRemitosAnular"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BBOEA" property="*" />
<%
 BBOEA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBOEA.setUsuarioalt(usuario); 
 BBOEA.setResponse(response);
 BBOEA.setRequest(request);
 BBOEA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>

function setTipoPedido(obj){
  var tipopedido = '<%=BBOEA.getTipopedido()%>';
  if(obj.value != tipopedido){
    document.frm.accion.value = '';
    //document.getElementById("consolidar").disabled = true;
    document.frm.submit();
  } 

}

function enviarFrm(accion){
    if(accion == 'anular'){
      if(!confirm('Confirma la anulación de los remitos implicados? ')){
        return true;
      }
    }
    document.frm.accion.value = accion;
    document.frm.submit();
}

 </script>
</head>
<%
/*
pd.idpedido_cabe, pd.idremitocliente, cr.nrosucursal, cr.nroremitocliente, 
cr.idctrlremito, pc.idcliente, pc.idsucuclie, cr.nrohojaarmado, nrohojarutafinal
*/
// titulos para las columnas 
tituCol[0] = "Pedido";
tituCol[1] = "IdRemito";
tituCol[2] = "Sucursal";
tituCol[3] = "Comprobante";
tituCol[4] = "Control";
tituCol[5] = "Cliente";
tituCol[6] = "Cliente";
tituCol[7] = "IdDomicilio";
tituCol[8] = "H.Armado";
tituCol[9] = "H.Final";
tituCol[10] = "";
tituCol[11] = "";

java.util.List ListRemitos = new java.util.ArrayList();
ListRemitos= BBOEA.getListRemitos();
iterRemitos = ListRemitos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="clientesRemitosAnular.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales"> 
    <td  height="24" colspan="10" align="center">
            <table width="98%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                   <td height="31"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="1%" height="38"><table width="100%" border="0">
                    <tr>
                      <td width="8%" height="26" class="text-globales"><p>Identificador:</p></td>
                      <td width="41%"><input name="idctrlremito" type="text" value="<%=BBOEA.getIdctrlremito()%>" id="idctrlremito" size="20" maxlength="16" class="campo" style="text-align:right"></td>
                      <td width="51%"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr class="text-globales">
                                  <td width="52%">Pedido Normal <span class="fila-det-border">
                                    <input name="tipopedido" type="radio" class="campo" value="N" <%= BBOEA.getTipopedido().equalsIgnoreCase("N") ? "checked" : "" %> onClick="setTipoPedido(this);" >
                                  </span></td>
                                  <td><span class="fila-det-border">R.Empresario
                                    <input name="tipopedido" type="radio" class="campo" value="R" <%= BBOEA.getTipopedido().equalsIgnoreCase("R") ? "checked" : "" %> onClick="setTipoPedido(this);">
                                  </span></td>
                                </tr>
                            </table></td>
                          </tr>
                      </table></td>
                    </tr> 
                    <tr>
                      <td height="26" class="text-globales">Remito:</td>
                      <td><input name="nrosucursal" type="text" value="<%=Common.strZero(BBOEA.getNrosucursal(), 4)%>" id="nrosucursal" size="5" maxlength="4" class="campo"  style="text-align:right">
                      <input name="nroremitocliente" type="text" value="<%=Common.strZero(BBOEA.getNroremitocliente(), 8)%>" id="nroremitocliente" size="10" maxlength="8" class="campo" style="text-align:right"></td>
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><input name="ir" type="button" class="boton" id="ir" value="  >>  " onClick="enviarFrm('buscar')">
                              <input name="idctrlremitoAnt" type="hidden" value="<%=BBOEA.getIdctrlremito()%>" id="idctrlremitoAnt" size="20" maxlength="16" style="text-align:right">
                              <input name="nrosucursalAnt" type="hidden" value="<%=Common.strZero(BBOEA.getNrosucursal(), 4)%>" id="nrosucursalAnt" size="5" maxlength="4"   >
                              <input name="nroremitoclienteAnt" type="hidden" value="<%=Common.strZero(BBOEA.getNroremitocliente(), 8)%>" id="nroremitoclienteAnt" size="10" maxlength="8" ></td>
                            <td><span class="fila-det-border">
                              <input name="volver" type="submit" class="boton" id="volver" value="Volver">
                            </span></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
        </table>
      </td>
    </tr>
  </table>



<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td> 
<div align="center">
<table width="90%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BBOEA" property="mensaje"/>&nbsp;</td>
  </tr>
</table>

  <% if(BBOEA.getTotalRegistros()>0) {
			 String target = "pedidosHistoricoClienteDetalle.jsp";//;
			 if(BBOEA.getTipopedido().equalsIgnoreCase("R")) target =  "pedidosRegalosDetalleEntrega.jsp" ;

  %>
	 <table width="90%" border="1" cellspacing="0" cellpadding="0">
		 <tr class="text-dos-bold">
			 <td colspan="2"><div align="center">Resumen de documentos involucrados </div></td>
			 </tr>
		 <tr class="text-dos-bold">
			 <td width="66%"><div align="center">Remitos</div></td>
			 <td width="34%"><div align="center">Pedidos</div></td>
		 </tr>
		 <tr valign="top" bgcolor="#CCCCFF">
			 <td >
         <table width="100%" border="1" cellspacing="0" cellpadding="0">
         <% 
				 Enumeration en = Common.getSetSorted(BBOEA.getHtRemitosInvolucrados().keySet());
         r= 0;
				 while(en.hasMoreElements()){
           String comprobante = (String) en.nextElement();
           String sucursal = Common.setNotNull(comprobante.substring(0, comprobante.indexOf("-") )).trim();
           String remito = Common.setNotNull(comprobante.substring( comprobante.indexOf("-") + 1  )).trim();
          
				 %>
         <tr class="fila-det">
           <td width="32%"><div align="center"><%= comprobante %>&nbsp;</div></td>
           <td width="27%"><div align="right">Nro. Control --&gt; </div></td>
           <td width="38%"><input name="vecIdctrlremito" type="text" value="<%=BBOEA.getVecIdctrlremito() != null ? BBOEA.getVecIdctrlremito()[r] : ""%>" id="vecIdctrlremito" size="20" maxlength="16" class="campo" style="text-align:right"></td>
           <td width="3%"><div align="center"><img src="../imagenes/default/gnome_tango/mimetypes/gnome-mime-application-x-kspread.png" width="18" height="18" onClick="abrirVentana('clientesRemitoDetalle.jsp?sucursal=<%=sucursal%>&remitocliente=<%=remito%>&idremitocliente=<%=BBOEA.getHtRemitosInvolucrados().get(comprobante)%>&idcliente=<%=BBOEA.getIdcliente()%>&cliente=<%=BBOEA.getCliente()%>&tipopedido=<%= BBOEA.getTipopedido() %>','detalle', 750, 450)" style="cursor:pointer"></div></td>
         </tr>
         <%
           r++;
				 }
				 %>
       </table>			 </td>
			 <td>
       <table width="100%" border="1" cellspacing="0" cellpadding="0">
         <% 
				 en = Common.getSetSorted(BBOEA.getHtPedidosInvolucrados().keySet());
				 while(en.hasMoreElements()){
           String pedido = en.nextElement().toString();
				 %>
         <tr class="fila-det">
           <td width="94%"><div align="center"><%= pedido %>&nbsp;</div></td>
           <td width="6%"><div align="center"><img src="../imagenes/default/gnome_tango/mimetypes/gnome-mime-application-x-kspread.png" width="18" height="18" onClick="abrirVentana('<%= target %>?tipopedido=<%=BBOEA.getTipopedido()%>&idpedido_cabe=<%=pedido%>&idcliente=<%=BBOEA.getIdcliente()%>&cliente=<%=BBOEA.getCliente()%>','pedidos', 750, 450)" style="cursor:pointer"></div></td>
         </tr>
         <%
				 }
				 %>
       </table></td>
		 </tr>
		 <tr valign="top" class="text-globales">
		   <td colspan="2"  ><input name="anula" type="button" class="boton" id="anula" value="  Anular" onClick="enviarFrm('anular')"></td>
		   </tr>
	 </table>
  <% } %>


<table width="90%" border="1" cellspacing="0" cellpadding="0">
  <tr class="text-dos-bold">
    <td><div align="center">Detalle de documentos involucrados </div></td>
  </tr>
  <tr>
    <td>


			<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
				<tr class="fila-encabezado">
					 <td width="5%" >&nbsp;</td>
					 <td width="18%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[0]%></div></td>
					 <td width="23%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[3]%></div></td>
					 <td width="18%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[5]%></div></td>
					 <td width="18%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[8]%></div></td>
					 <td width="18%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[9]%></div></td>
				</tr>
				 <% r = 0;
				 while(iterRemitos.hasNext()){
						++r;
						String[] sCampos = (String[]) iterRemitos.next(); 
						// estos campos hay que setearlos segun la grilla 
						if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
						else color_fondo = "fila-det-verde";%>
				 <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
						<td class="fila-det-border" >&nbsp;</td>
						<td class="fila-det-border" ><div align="right"><%=Common.setNotNull(sCampos[0])%>&nbsp;</div></td>
						<td class="fila-det-border" ><div align="center"><%=Common.strZero(Common.setNotNull(sCampos[2]), 4)%>&nbsp;- <%=Common.strZero(Common.setNotNull(sCampos[3]),8)%>&nbsp;</div></td>
						<td class="fila-det-border" ><div align="right"><%=Common.setNotNull(sCampos[5])%>&nbsp;</div></td>
						<td class="fila-det-border" ><div align="right"><%=Common.setNotNull(sCampos[8])%>&nbsp;</div></td>
						<td class="fila-det-border" ><div align="right"><%=Common.setNotNull(sCampos[9])%>&nbsp;</div></td>
				 </tr>
			<%
				 }%>
			 </table>

    </td>
  </tr>
</table>


 

      </div>
    </td>
  </tr>
</table>
   <input name="accion" value="" type="hidden" >
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

