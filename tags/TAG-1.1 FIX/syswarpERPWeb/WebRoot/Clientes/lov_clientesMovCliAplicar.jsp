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
String titulo = "COMPROBANTES CLIENTE : " ;
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterProveedoMovProv   = null;
int totCol = 14; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPMPA"  class="ar.com.syswarp.web.ejb.BeanLovClientesMovCliAplicar"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPMPA" property="*" />
<%
 BPMPA.setResponse(response);
 BPMPA.setRequest(request);
 BPMPA.setSession(session);
 BPMPA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPMPA.ejecutarValidacion();
 titulo += BPMPA.getCliente();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
   function bajarDatos( tipo, nrointerno, comprobante, importe, saldo, fecha){
	 
	   if(tipo == 'aplicar'){
		   opener.document.frm.nrointerno_q_can.value = nrointerno;
		   opener.document.frm.comprob_q_can.value = comprobante;
		   opener.document.frm.importe_q_can.value = importe;
		   opener.document.frm.saldo_q_can.value = saldo;		 
		 }
		 else{
		   opener.document.frm.nrointerno_canc.value = nrointerno;
		   opener.document.frm.comprob_canc.value = comprobante;
		   opener.document.frm.importe_canc.value = importe;
		   opener.document.frm.saldo_canc.value = saldo;		 
		   opener.document.frm.fvence_canc.value = fecha;		 
		 }
	 
	   opener.document.frm.submit();
		 this.close();
	 
	 }
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "N° Int.";
tituCol[1] = "idcliente"; 
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
tituCol[13] = "Anulada";
java.util.List ProveedoMovProv = new java.util.ArrayList();
ProveedoMovProv= BPMPA.getClientesMovCliList();
iterProveedoMovProv = ProveedoMovProv.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_clientesMovCliAplicar.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="3"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td width="6%" height="38">&nbsp;                  </td>
                   <td width="94%" colspan="2">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BPMPA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
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
                                             <select name="limit" >
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
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BPMPA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BPMPA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BPMPA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" >&nbsp;</td>
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="32%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="13%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
    </tr>
   <%int r = 0;
   while(iterProveedoMovProv.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterProveedoMovProv.next(); 
			String comprob = Common.strZero(sCampos[3], 4)+ " - "  +Common.strZero(sCampos[4], 8);
			Object fecha = Common.setObjectToStrOrTime(!sCampos[2].equals("") ? java.sql.Timestamp.valueOf( sCampos[2] ) : null, "JSTsToStr" );
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" >
	  <% 
	  if(!Common.setNotNull(sCampos[13]).equalsIgnoreCase("1") ) {%>
	  <img src="../imagenes/default/gnome_tango/actions/down.png" width="18" height="18" onClick="bajarDatos( '<%=BPMPA.getTipo().toLowerCase() %>', <%= sCampos[0] %>, '<%= sCampos[6] + " - " + comprob %>', '<%=sCampos[7]%>', '<%=sCampos[8]%>', '<%= fecha %>')">
	  <% 
	  }else{ %>
	  <img src="../imagenes/default/gnome_tango/actions/format-text-bold.png" width="18" height="18" title="Comprobante Anulado.">
	  <% 
	  }%>
	  </td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=fecha%>&nbsp;</td> 
      <td class="fila-det-border" ><%=comprob %>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[6]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[8]%>&nbsp;</td> 
    </tr>
<%
   }%>
  </table>

     <input type="hidden" name="accion"      value="">
     <input type="hidden" name="idcliente" value="<%=BPMPA.getIdcliente()%>">
     <input type="hidden" name="cliente"   value="<%=BPMPA.getCliente()%>">	 	   
     <input type="hidden" name="nrointerno"  value="-1" >
		 <input type="hidden" name="tipo"        value="<%=BPMPA.getTipo()%>">
     <input type="hidden" name="tipomovIN"   value="<%=BPMPA.getTipomovIN()%>">
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


