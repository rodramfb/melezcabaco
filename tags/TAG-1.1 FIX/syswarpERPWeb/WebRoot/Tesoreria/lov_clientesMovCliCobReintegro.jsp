<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: clientesMovCli
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Dec 13 10:54:32 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*"%> 
<%@ page import="java.sql.*" %>
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "COMPROBANTES A APLICAR PARA EL CLIENTE ";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterClientesMovCli   = null;
int totCol = 24; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCMCA"  class="ar.com.syswarp.web.ejb.BeanLovClientesMovCliCobReintegro"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCMCA" property="*" />
<%
 BCMCA.setResponse(response);
 BCMCA.setRequest(request);
 BCMCA.setSession(session);
 BCMCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCMCA.ejecutarValidacion();
 titulo +=  BCMCA.getRazon().toUpperCase();
 
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

  function agregarComprobante(comprobante){
    document.frm.comprobante.value = comprobante;
    document.frm.accion.value = 'agregar';
    document.frm.submit();
  }	
 </script> 
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Fecha";
tituCol[2] = "Sucursal";
tituCol[3] = "Comprobante";
tituCol[4] = "comprob_has";
tituCol[5] = "Tipo";
tituCol[6] = "Tipo comprobante";
tituCol[7] = "Saldo";
tituCol[8] = "Importe";
tituCol[9] = "cambio";
tituCol[10] = "moneda";
tituCol[11] = "unamode";
tituCol[12] = "tipocomp";
tituCol[13] = "condicion";
tituCol[14] = "nrointerno";
tituCol[15] = "con_venta";
tituCol[16] = "con_cobra";
tituCol[17] = "con_vende";
tituCol[18] = "anulada";
tituCol[19] = "retoque";
//tituCol[20] = "expreso";
tituCol[20] = "Zona";
tituCol[21] = "sucucli";
tituCol[22] = "remito";
tituCol[23] = "credito";
java.util.List ClientesMovCli = new java.util.ArrayList();
ClientesMovCli= BCMCA.getClientesMovCliList();
iterClientesMovCli = ClientesMovCli.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_clientesMovCliCobReintegro.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="3"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr height="3px">
                   <td colspan="3" height="3px" ><hr color="#FFFFFF"></td>
                </tr>								
                <tr>
                   <td width="1%" height="38">&nbsp;                  </td>
                   <td colspan="2">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="21%">
                              <input name="ocurrencia" type="text" value="<%=BCMCA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="73%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="30%">&nbsp;Total de registros:&nbsp;<%=BCMCA.getTotalRegistros()%></td>
                                          <td width="15%" >Visualizar:</td>
                                          <td width="17%">
                                             <select name="limit" >
                                                <%for(i=10; i<= 100 ; i+=10){%>
                                                    <%if(i==BCMCA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCMCA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCMCA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="16%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BCMCA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BCMCA.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select> 
                                          </td>
                                          <td width="14%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
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
                <!--
				<tr class="text-globales">
                  <td >&nbsp;</td>
                  <td width="19%">Generar Adelanto </td>
                  <td width="80%"><input name="adelanto" type="button" class="boton" id="adelanto" value="Adelanto >>" onClick="generarAdelanto();"></td>
                </tr>
				 -->
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCMCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="4%" ><div align="center"></div></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="26%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="31%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
    </tr>
   <%int r = 0;
   while(iterClientesMovCli.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterClientesMovCli.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center">
        <img src="../imagenes/default/next.gif" width="13" height="13" onClick="agregarComprobante('<%= sCampos[2] + "-" + sCampos[3] + "-" + sCampos[6]  %>');">
      </div></td>
      <td class="fila-det-border" ><%= Common.setObjectToStrOrTime( Timestamp.valueOf( sCampos[1] ), "JSTsToStr") %></td>
      <td class="fila-det-border" ><%= Common.strZero(sCampos[2], 4) + "-" + Common.strZero(sCampos[3], 8) %>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[6]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[8]%>&nbsp;</td>
    </tr>
<%
   }%>
  </table>
   &nbsp;
	 <% 
		
  Hashtable htComprobantesCobCli = (Hashtable) session.getAttribute("htComprobantesCobCli");
	if(htComprobantesCobCli != null && !htComprobantesCobCli.isEmpty()){
	   %> 		 
   <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0">
     <tr class="text-globales">
       <td width="3%">Sel</td>
       <td width="21%"><div align="right">Nro. Interno </div></td>
       <td width="29%"><div align="center">Comprobante</div></td>
       <td width="23%"><div align="right">Importe Original </div></td>
       <td width="24%"><div align="right">A Reintegrar </div></td>
     </tr>
		<%
	   			Enumeration en = htComprobantesCobCli.keys();
					while (en.hasMoreElements()) {
						String key = (String) en.nextElement();
						String [] comprobantes = (String []) htComprobantesCobCli.get(key);
						//String [] comp = key.split("-");
						//for(int a = 0;a<comprobantes.length;a++) 
						//  System.out.println("comprobantes["+a+ "]: " + comprobantes[a]);
						  %>		 
		 
     <tr class="fila-det">
       <td class="fila-det-border">
         <input name="delKey" type="checkbox" id="delKey" value="<%= key %>" class="campo">       </td>
       <td class="fila-det-border"><div align="right"><%= comprobantes[0] %>
           <input  name="keyHashDatosComprobante" type="hidden" value="<%= key %>" >
       </div></td>
      <td class="fila-det-border"><div align="center">
	       <%//= key.equalsIgnoreCase("ADELANTO") ? key :comp[2] + "-" + Common.strZero( comp[0], 4) + "-" + Common.strZero( comp[1], 8)  %>
       <%= Common.strZero( comprobantes[9], 4) + "-" + Common.strZero( comprobantes[10], 8)  %>	   </div></td> 
       <td class="fila-det-border">
         <div align="right"><%= comprobantes[2] %>&nbsp;</div></td>
       <td class="fila-det-border">
         <div align="right"><%= comprobantes[1] %>
           <input name="totalcobrar" type="hidden" value="<%= comprobantes[1] %>" class="campo" size="10" maxlength="15" style="text-align:right">
           </div></td></tr>
		<%
	        }%>		 
     <tr class="fila-det">
       <td height="33" class="fila-det-border">&nbsp;</td>
       <td class="fila-det-border"><input name="eliminar" type="button" class="boton" id="eliminar" value="Eliminar" onClick="eliminarRegistro();">       </td>
       <td colspan="3" class="fila-det-border"><input name="confirmar" type="button" class="boton" id="confirmar2" value="Confirma" onClick="confirmarDetalle();"></td>
     </tr>
  </table>
	 <% 
	}%> 	
     <input name="cliente" value="<%= BCMCA.getCliente() %>" type="hidden">
     <input name="razon" value="<%= BCMCA.getRazon() %>" type="hidden">
		 <input name="accion" value="" type="hidden">
    <input name="comprobante"  type="hidden" value="<%//= sCampos[2] + "-" + sCampos[3] + "-" + sCampos[6]  %>">
      </p>
			
			<%  
			if(BCMCA.getAccion().equalsIgnoreCase("confirmar")  
			   && BCMCA.getMensaje().equals("") ){%>
			  <script>
				opener.document.frm.submit();
				this.close(); 
			  </script>			
			<%  
			}%>			
			
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

