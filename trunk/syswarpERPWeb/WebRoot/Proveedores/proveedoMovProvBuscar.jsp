<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: proveedoMovProv
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jul 27 09:30:45 GMT-03:00 2006 
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
String titulo = "Modificar Anular / Comprobantes de Proveedores ";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterProveedoMovProv   = null;
int totCol = 13; // cantidad de columnas 
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPMPA"  class="ar.com.syswarp.web.ejb.BeanProveedoMovProvBuscar"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPMPA" property="*" />
<%
 BPMPA.setResponse(response);
 BPMPA.setRequest(request);
 BPMPA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPMPA.ejecutarValidacion();
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
tituCol[0] = "nrointerno";
tituCol[1] = "Fecha";
tituCol[2] = "Saldo";
tituCol[3] = "Importe";
tituCol[4] = "Comprobante";
tituCol[5] = "Sucursal";
tituCol[6] = "Comprobante";
tituCol[7] = "Stock?";
tituCol[8] = "Aplica?";
tituCol[9] = "Aplicado?";
tituCol[10] = "sucu";
tituCol[11] = "comprob";

java.util.List ProveedoMovProv = new java.util.ArrayList();
ProveedoMovProv= BPMPA.getProveedoMovProvList();
iterProveedoMovProv = ProveedoMovProv.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="proveedoMovProvBuscar.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center"> 
  <tr>
    <td>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="30" class="text-globales"><%=titulo   + (!BPMPA.getDproveedor().equals("") ?  " - PROVEEDOR SELECCIONADO: " +  BPMPA.getDproveedor() : "" )%></td>
                </tr>
                <tr>

                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td height="26" class="text-globales">Proveedor:</td>
                           <td width="22%">
                           <input name="dproveedor" type="text" class="campo" id="dproveedor" value="<%=BPMPA.getDproveedor()%>" size="30" readonly="yes" /></td>
                           <td width="15%"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="22" height="22" onClick="abreVentana('../Proveedores/lov_proveedores.jsp', 700, 400)" style="cursor:pointer" />
                             <input name="idproveedor" type="hidden" id="idproveedor" value="<%=BPMPA.getIdproveedor()%>" /></td>
                           <td><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                         </tr>
                         <tr>
                           <td width="11%" height="26" class="text-globales">Buscar</td>
                           <td colspan="2">
                              <input name="ocurrencia" type="text" value="<%=BPMPA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30" class="campo">                           </td>
                           <td width="52%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="23%" height="19">&nbsp;Total de registros:&nbsp;<%=BPMPA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BPMPA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BPMPA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                             </select>                                          </td>
                                       </tr>
                                    </table>                                 </td>
                              </tr>
                           </table>                        </td>
                     </tr>
                   </table>                </td>
            </tr>
                <tr>
                  <td  colspan="2" class="text-globales"><table width="100%" border="0">
                    <tr>
                      <!--	                               <td width="27%"><input name="alta" id="alta" value="alta" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('alta','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name">
                               </td>
-->
                      <td width="94%"  colspan="2" class="text-globales"><div align="center">
                          <input name="paginaSeleccion" type="hidden" value="1" />
                        P&aacute;gina: <%= Common.getPaginacion(BPMPA.getTotalPaginas(),
					BPMPA.getTotalRegistros(), BPMPA.getPaginaSeleccion(), BPMPA.getLimit(),
					BPMPA.getOffset()) %> </div></td>
                      </tr>
                  </table></td>
                </tr>
          </table>
      </td>
    </tr>
  </table> 
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BPMPA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="6%" height="23" >&nbsp;</td>
     <td width="4%"><div align="center"><%=tituCol[1]%></div></td>
     <td width="25%"><div align="right"><%=tituCol[2]%>&nbsp;</div></td>
     <td width="21%"><div align="right"><%=tituCol[3]%>&nbsp;</div></td>
     <td width="34%"><div align="center"><%=tituCol[4]%></div></td>
     <td width="4%"><div align="center"><%=tituCol[7]%></div></td>
     <td width="3%"><div align="center"><%=tituCol[8]%></div></td>
     <td width="3%"><div align="center"><%=tituCol[9]%></div></td>
  </tr>
   <%int r = 0;
   while(iterProveedoMovProv.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterProveedoMovProv.next(); 
      String comprobante = sCampos[4]+"-"+sCampos[5]+"-"+sCampos[6];
			// estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
			%>
			
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" >
			  <table width="100%" border="0">
        <tr>
          <td width="3%">
				<%if(sCampos[7].equalsIgnoreCase("S") && sCampos[4].indexOf("NC")>  -1) 
				    out.write("&nbsp;");
				  else{%>					
					<%if(sCampos[7].equalsIgnoreCase("N")){  %>
						 <img src="../imagenes/default/gnome_tango/actions/edit-find-replace.png" title="Modificar" width="20" height="20" name="modificacion" onClick="llamarAccion(this, <%=sCampos[0]%>, '<%=comprobante%>', '<%=sCampos[8]%>', '<%=sCampos[9]%>', 'frm')"></td>
					<%}else out.write("&nbsp;");  %>
						<td width="3%">
						 <img src="../imagenes/default/gnome_tango/actions/editclear.png" title="Anular - Eliminar" width="20" height="20" name="baja" onClick="llamarAccion(this, <%=sCampos[0]%>, '<%=comprobante%>', '<%=sCampos[8]%>', '<%=sCampos[9]%>','frm')"></td>				
        <%} %>
        </tr>
      </table>
			</td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[1]), "JSTsToStr")%></div></td> 
      <td class="fila-det-border" ><div align="right"><%=sCampos[2]%></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[3]%></div></td>
      <td class="fila-det-border" ><div align="center"><%=comprobante%></div></td>
      <td class="fila-det-border" ><div align="center"><%=sCampos[7]%></div></td>
      <td class="fila-det-border" ><div align="center"><%=sCampos[8]%></div></td>
      <td class="fila-det-border" ><div align="center"><%=sCampos[9]%></div></td>
   </tr>

	 	 
<%
   }
   if(r>0){  
    %>
	 
   <tr class="fila-det-bold" > 
      <td colspan="2" bgcolor="#EEF5F9" class="fila-det-border" >TOTALES&nbsp;</td>
      <td bgcolor="#EEF5F9" class="fila-det-border" ><div align="right"><%=BPMPA.getSaldo()%></div></td>
      <td bgcolor="#EEF5F9" class="fila-det-border" ><div align="right"><%=BPMPA.getImporte()%></div></td>
      <td colspan="4" bgcolor="#EEF5F9" class="fila-det-border" >&nbsp;   </td>
   </tr>
<%
   }%>	 
   </table>
    </td>
  </tr>	
</table>	
   <input name="nrointerno" type="hidden" value="">  
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

