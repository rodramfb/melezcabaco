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
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*"%> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "COMPROBANTES PROVEEDOR : " ;
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
<jsp:useBean id="BPMPA"  class="ar.com.syswarp.web.ejb.BeanLovCajaMovProvAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPMPA" property="*" />
<%
 BPMPA.setResponse(response);
 BPMPA.setRequest(request);
 BPMPA.setSession(session);
 BPMPA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPMPA.ejecutarValidacion();
 titulo += BPMPA.getProveedor();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
   <script>
	function eliminarRegistro(){
		if(confirm("Confirma eliminar selección ?")){
				document.frm.accion.value = "eliminar";
				document.frm.submit();
		}
	}
	
	function confirmarDetalle(){
		if(confirm("Confirma datos ingresados ?")){
				document.frm.accion.value = "confirmar";
				document.frm.submit();
		}
	}
	
	function generarAdelanto(){
		if(confirm("Confirma ingreso de Adelanto ?")){
				document.frm.accion.value = "adelanto";
				document.frm.submit();
		}
	}
  
  function agregarComprobante(nrointerno){
    document.frm.nrointerno.value = nrointerno;
    document.frm.accion.value = 'agregar';
    document.frm.submit();
  }	
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "N° Int.";
tituCol[1] = "idproveedor";
tituCol[2] = "Fecha";
tituCol[3] = "Comprobante";
tituCol[4] = "comprob";
tituCol[5] = "tipomov";
tituCol[6] = "Tipo";
tituCol[7] = "Importe";
tituCol[8] = "Saldo";
tituCol[9] = "idcondicionpago";
tituCol[10] = "fecha_subd";
tituCol[11] = "retoque";
tituCol[12] = "fechavto";
java.util.List ProveedoMovProv = new java.util.ArrayList();
ProveedoMovProv= BPMPA.getProveedoMovProvList();
iterProveedoMovProv = ProveedoMovProv.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_proveedoresMovProvAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="3"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td width="6%" height="38">&nbsp;                  </td>
                   <td colspan="2">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BPMPA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BPMPA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit"  class="campo">
                                                <%for(i=10; i<= 100 ; i+=10){%>
                                                    <%if(i==BPMPA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option> 
                                                    <%}
                                                      if( i >= BPMPA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BPMPA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" class="campo">
                                                <%for(i=1; i<= BPMPA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BPMPA.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select> 
                                          </td>
                                          <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                       </tr>
                                    </table>
                                 </td>
                              </tr>
                           </table>
                        </td>
                     </tr>
                   </table>
                </td>
            </tr>
                <tr class="text-globales">
                  <td>&nbsp;</td>
                  <td width="20%">Generar Adelanto</td>
                  <td width="74%"><input name="adelanto" type="button" class="boton" id="adelanto" value="Adelanto >>" onClick="generarAdelanto();"></td>
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
     <td width="3%" ></td>
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="32%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="13%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[7]%></div></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[8]%></div></td>
    </tr>
   <%int r = 0;
   while(iterProveedoMovProv.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterProveedoMovProv.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/nuevo.gif" width="16" height="16" onClick="agregarComprobante(<%= sCampos[0] %>);"></div></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime(!sCampos[2].equals("") ? java.sql.Timestamp.valueOf( sCampos[2] ) : null, "JSTsToStr" )%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.strZero(sCampos[3], 4)%> - <%=Common.strZero(sCampos[4], 8)%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[6] %>&nbsp;</td>
      <td class="fila-det-border" ><div align="right">&nbsp;<%=Common.getNumeroFormateado(  Double.parseDouble (sCampos[7]) , 10, 2 )%></div></td>
      <td class="fila-det-border" ><div align="right">&nbsp;<%=Common.getNumeroFormateado(  Double.parseDouble (sCampos[8]) , 10, 2 )%></div></td>
    </tr>
<%
   }%>
  </table>
     <% 
  Hashtable htComprobantesProv = (Hashtable) session.getAttribute("htComprobantesProv");
	if(htComprobantesProv != null && !htComprobantesProv.isEmpty()){
	   %>
</p>
   <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0">
     <tr class="text-globales">

       <td width="3%">Sel</td>
       <td width="23%">Comprobante</td>
       <td width="9%"><div align="center">Tipo</div></td>

       <td width="17%">Fecha</td>
       <td width="22%">Saldo</td>
       <td width="26%">Importe</td>
     </tr>
     <%
	   			Enumeration en = htComprobantesProv.keys();
					while (en.hasMoreElements()) {
						String key = (String) en.nextElement();
						String [] comprobante = (String []) htComprobantesProv.get(key);
						  %>
     <tr class="fila-det">
       <td class="fila-det-border">&nbsp;
         <input name="delKey" type="checkbox" id="delKey" value="<%= key %>" class="campo">
       </td>
       <td class="fila-det-border"> &nbsp;<%= !key.equalsIgnoreCase("adelanto") ? Common.strZero(comprobante[4], 4 ) + " - "  + Common.strZero(comprobante[5], 8) : comprobante[0] %>
           <input  name="keyHashDatosCompProv" type="hidden" value="<%= key %>"></td>

       <td class="fila-det-border"><div align="center"><%= !key.equalsIgnoreCase("adelanto") ? comprobante[9] : key %>
       </div></td>
       <td class="fila-det-border"><%=  !str.esNulo(comprobante[3]).equals("") ? Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(comprobante[3]), "JSTsToStr" ) : "&nbsp;" %></td>
       <td class="fila-det-border"><%= comprobante[6] %></td>
       <td class="fila-det-border"><input name="importe" type="text" value="<%= comprobante[1] %>" class="campo" size="10" maxlength="15" style="text-align:right"></td>
     </tr>
     <%
	        }%>
     <tr class="fila-det">
       <td height="33" class="fila-det-border">&nbsp;</td>
       <td class="fila-det-border"><input name="eliminar" type="button" class="boton" id="eliminar" value="Eliminar" onClick="eliminarRegistro();">
       </td>
       <td  class="fila-det-border"><input name="confirmar" type="button" class="boton" id="confirmar2" value="Confirma" onClick="confirmarDetalle();"></td>
       <td  class="fila-det-border">&nbsp;</td>
       <td  class="fila-det-border">&nbsp;</td>
       <td  class="fila-det-border">&nbsp;</td>
     </tr>
   </table>
   <% 
	}%>
     <input name="accion" value="" type="hidden">
     <input name="idproveedor" value="<%=BPMPA.getIdproveedor()%>" type="hidden">
     <input name="proveedor" value="<%=BPMPA.getProveedor()%>" type="hidden">	 	 
     <input name="nrointerno" value="" type="hidden" >  
      </p>
</form>

						<%  
						// 20070511 EJV: 
						// TODO :Solucion provisoria a mejorar ...
						// BPMPA.getMensaje().equalsIgnoreCase("no existen registros.")
			if(BPMPA.getAccion().equalsIgnoreCase("confirmar")  
			   && (BPMPA.getMensaje().equals("") || BPMPA.getMensaje().equalsIgnoreCase("no existen registros."))){
			    
			   %>
			  <script>
			  
					   opener.document.frm.submit();
								//this.close();
					</script>			
			<%  
			}%>
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


