<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vclientesCtaCtes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Mar 25 08:50:26 GYT 2009 
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
String titulo = "Consulta de Cta. Cte. Cliente: ";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVclientesCtaCtes   = null;
Iterator iter;
int totCol = 15; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCCA"  class="ar.com.syswarp.web.ejb.BeanVclientesCtaCtesGeneral"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCCA" property="*" />
<%
 BVCCA.setResponse(response);
 BVCCA.setRequest(request);
 BVCCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BVCCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link rel="stylesheet" href="../imagenes/default/erp-style.css">

 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "idcliente";
tituCol[1] = "razon";
tituCol[2] = "Fecha";
tituCol[3] = "Tipo";
tituCol[4] = "idtipomov";
tituCol[5] = "Comprobante";
tituCol[6] = "Debe";
tituCol[7] = "Haber";
tituCol[8] = "Saldo";
tituCol[9] = "F.Vto";
tituCol[10] = "nrointerno";
tituCol[14] = "Condicion";
java.util.List VclientesCtaCtes = new java.util.ArrayList();
VclientesCtaCtes= BVCCA.getVclientesCtaCtesList();
iterVclientesCtaCtes = VclientesCtaCtes.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vclientesCtaCtesGeneral.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="29"  class="text-globales"><%=titulo   + "  " + BVCCA.getCliente() %></td>
                </tr>
                <tr>
                   <td  class="text-globales"><hr color="#FFFFFF"></td>
                </tr>
                <tr>
                  <td width="89%" height="38">
                     <table width="100%" border="0">
                        <tr>
                          <td width="15%" height="26" class="text-globales">&nbsp;</td>
                          <td width="10%">
                             <%-- <input name="ocurrencia" type="text" value="<%=BVCCA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30"> --%> &nbsp;                          </td>
                          <td width="75%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="20%" height="19">&nbsp;Total de registros:&nbsp;<%= BVCCA.getTotalRegistros() + "" %></td>
                                         <td width="13%" >Visualizar:</td>
                                         <td width="12%">
                                            <select name="limit" >
                                               <%for(i=15; i<= 150 ; i+=15){%>
                                                   <%if(i==BVCCA.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                   <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                   <%}
                                                      if( i >= BVCCA.getTotalRegistros() ) break;
                                                    %>
                                               <%}%>
                                               <option value="<%= BVCCA.getTotalRegistros()%>">Todos</option>
                                            </select>                                          </td>
                                         <td width="8%">&nbsp;P&aacute;gina:</td>
                                         <td width="13%">
                                            <select name="paginaSeleccion" >
                                               <%for(i=1; i<= BVCCA.getTotalPaginas(); i++){%>
                                                   <%if ( i==BVCCA.getPaginaSeleccion() ){%>
                                                      <option value="<%=i%>" selected><%=i%></option> 
                                                   <%}else{%>
                                                      <option value="<%=i%>"><%=i%></option>
                                                   <%}%>
                                               <%}%>
                                            </select>                                          </td>
                                         <td width="11%" class="text-globales"><input name="consulta" type="submit" class="boton" id="consulta" value="  >>  " onClick="document.frm.accion.value = 'consulta'"></td>
                                      </tr>
                                   </table>                                 </td>
                              </tr>
                          </table>                        </td>
                     </tr>
                        <tr class="text-globales">
                          <td height="26" >Pendiente: </td>
                          <td><input name="historico" type="radio" value="P" <%=  BVCCA.getHistorico().equals("P") ? "checked" : "" %>></td>
                          <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                              <tr class="text-globales">
                                <td width="20%" height="19">&nbsp;</td>
                                <td width="13%" > Todo:</td>
                                <td width="12%"><input name="historico" type="radio" value="H" <%=  BVCCA.getHistorico().equals("H") ? "checked" : "" %>></td>
                                <td width="8%">&nbsp;</td>
                                <td width="13%">&nbsp;</td>
                                <td width="11%" class="text-globales">&nbsp;</td>
                              </tr>
                          </table></td>
                        </tr>
                        <tr class="text-globales">
                          <td height="26" >Rango Desde: </td>
                          <td>&nbsp;
                              <select name="rangoRazonDesde" id="rangoRazonDesde" >
                                <option value="A" >Todos</option>
                                <%for( i=65;i<91;i++){%>
                                <option value="<%=(char)i%>" <%=  BVCCA.getRangoRazonDesde().equals( (String.valueOf ( (char)i)  ) ) ? "selected" : "" %>> <%=(char)i%></option>
                                <%if ( i==-78 ){
												   // Es por la &Ntilde;, pero al ser ascii extendido hay que evaluar de 
												   // hacer un salvoconducto, ya que no entra en el rango normal.
												   %>
                                <option value="&Ntilde;" >&Ntilde;</option>
                                <%}%>
                                <%}%>
                              </select>                          </td>
                          <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                              <tr class="text-globales">
                                <td width="20%" height="19">&nbsp;</td>
                                <td width="13%" > Rango Hasta:</td>
                                <td width="12%"><select name="rangoRazonHasta" id="rangoRazonHasta" >
                                    <option value="Z" >Todos</option>
                                    <%for( i=65;i<91;i++){%>
                                    <option value="<%=(char)i%>"  <%=  BVCCA.getRangoRazonHasta().equals( (String.valueOf ( (char)i)  ) ) ? "selected" : "" %>><%=(char)i%></option>
                                    <%if ( i==-78 ){
												   // Es por la &Ntilde;, pero al ser ascii extendido hay que evaluar de 
												   // hacer un salvoconducto, ya que no entra en el rango normal.
												   %>
                                    <option value="&Ntilde;" >&Ntilde;</option>
                                    <%}%>
                                    <%}%>
                                </select></td>
                                <td width="8%">&nbsp;</td>
                                <td width="13%">&nbsp;</td>
                                <td width="11%" class="text-globales">&nbsp;</td>
                              </tr>
                          </table></td>
                        </tr>
                        <tr class="text-globales">
                          <td height="26" >Tipo Cliente : </td>
                          <td>&nbsp;<span class="fila-det-border">
                            <select name="idtipoclie" id="idtipoclie" class="campo" style="width:80%" >
                              <option value="-1" selected>Sel.</option>
                              <% 
									  iter = BVCCA.getListTipoCliente().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                              <option value="<%= datos[0] %>" <%= datos[0].equals( BVCCA.getIdtipoclie().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                              <%  
									  }%>
                            </select>
                          </span></td>
                          <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                              <tr class="text-globales">
                                <td width="20%" height="19">&nbsp;</td>
                                <td width="13%" ><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=<%=  BVCCA.getHistorico().equals("P") ? "clientes_ctacte_gral_pendiente_frame" : "clientes_ctacte_gral_historico_frame"   %>&rangoCharDesde=<%=BVCCA.getRangoRazonDesde()%>&rangoCharHasta=<%=BVCCA.getRangoRazonHasta()%>&idtipoclie=<%=BVCCA.getIdtipoclie()%>','pedido',750, 400)" style="cursor:pointer" title="Impresi&oacute;n Nota de Pedido"></td>
                                <td width="12%">&nbsp;</td>
                                <td width="8%">&nbsp;</td>
                                <td width="13%">&nbsp;</td>
                                <td width="11%" class="text-globales">&nbsp;</td>
                              </tr>
                          </table></td>
                        </tr>
                   </table>                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="2" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="text-dos-bold">
     <td width="3%" ><div align="center"><%=tituCol[2]%></div></td>
     <td width="4%" ><div align="center"><%=tituCol[9]%></div></td>
     <td width="12%" ><%=tituCol[5]%></td>
     <td width="46%" ><%=tituCol[14]%></td>

     <td width="9%" ><div align="right"><%=tituCol[6]%></div></td>
     <td width="10%" ><div align="right"><%=tituCol[7]%></div></td>

     <td width="10%" ><div align="right"><%=tituCol[8]%></div></td>


     <td width="2%" >&nbsp;</td>
     <td width="2%" >&nbsp;</td>
     <td width="2%" >&nbsp;</td>
  </tr>
  
   <%
   int r = 0;
   Calendar cal = Calendar.getInstance();
   cal.set(Calendar.MILLISECOND, 0);
   cal.set(Calendar.SECOND, 0);
   cal.set(Calendar.MINUTE, 0);
   cal.set(Calendar.HOUR_OF_DAY, 0);
		    
   double [] totalesParcialesVencidos  = new double[3];
   double [] totalesParcialesEnTermino = new double[3];
   double [] totalesParciales          = new double[3];
   String idcliente = "";
   boolean esPrimero = true;
   java.sql.Date factual = new java.sql.Date(cal.getTimeInMillis());
   java.sql.Date fVenceComprob = factual;
   String classVence = "";
   while(iterVclientesCtaCtes.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVclientesCtaCtes.next(); 
      // estos campos hay que setearlos segun la grilla 
	  //
	  fVenceComprob = java.sql.Date.valueOf(sCampos[9]);
	  if( !idcliente.equals(sCampos[0]) ){
	    idcliente=sCampos[0];
		if(!esPrimero){
        %>		
		   <tr class="permiso-dos"> 
			  <td colspan="4"><div align="right"> ACUMULADO &nbsp;</div></td>
			  <td ><div align="right">
			    <table width="100%" border="1" cellspacing="0" cellpadding="0">
                  <tr class="permiso-tres">
                    <td width="39%" ><div align="right">Vencido:</div></td>
                    <td width="61%"><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParcialesVencidos[0], 10, 4), 2 ) %></div></td>
                  </tr>
                  <tr class="permiso-tres">
                    <td> <div align="right">Vencer: </div></td>
                    <td><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParcialesEnTermino[0], 10, 4), 2 ) %></div></td>
                  </tr>
                  <tr class="permiso-tres">
                    <td><div align="right">Total:</div></td>
                    <td><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParciales[0], 10, 4), 2 ) %></div></td>
                  </tr>
                </table>
			    </div></td>
			  <td ><div align="right">
			    <table width="100%" border="1" cellspacing="0" cellpadding="0">
                  <tr class="permiso-tres">
                    <td width="61%"><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParcialesVencidos[1], 10, 4), 2 ) %></div></td>
                  </tr>
                  <tr class="permiso-tres">
                    <td><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParcialesEnTermino[1], 10, 4), 2 ) %></div></td>
                  </tr>
                  <tr class="permiso-tres">
                    <td><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParciales[1], 10, 4), 2 ) %></div></td>
                  </tr>
                </table>
			    </div></td> 
			  <td ><div align="right">
			    <table width="100%" border="1" cellspacing="0" cellpadding="0">
                  <tr class="permiso-tres">
                    <td width="61%"><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParcialesVencidos[2], 10, 4), 2 ) %></div></td>
                  </tr>
                  <tr class="permiso-tres">
                    <td><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParcialesEnTermino[2], 10, 4), 2 ) %></div></td>
                  </tr>
                  <tr class="permiso-tres">
                    <td><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParciales[2], 10, 4), 2 ) %></div></td>
                  </tr>
                </table>
			    </div></td>
			  <td colspan="3" ><div align="center"></div>        <div align="center"></div>        <div align="center"></div></td>
		   </tr>		
		<%
		}
        if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
        else color_fondo = "fila-det-verde";
	    totalesParcialesVencidos [0] = totalesParcialesVencidos [1] = totalesParcialesVencidos [2] = 0d;	
		totalesParcialesEnTermino [0] = totalesParcialesEnTermino [1] = totalesParcialesEnTermino [2] = 0d;
		totalesParciales [0] = totalesParciales [1] = totalesParciales [2] = 0d;	
	  %>	  
		<tr class="text-dos">
		 <td colspan="5" >CLIENTE: <%= sCampos[0] + " - " + sCampos[1]%></td> 
		 <td colspan="5" >TIPO: <%= sCampos[12]%></td>
		</tr>	  
	  <%
	   
	  }

      if(fVenceComprob.before(factual)){
	    classVence = "fila-det-rojo";
	    totalesParcialesVencidos [0] += Double.parseDouble(sCampos[6]);
	    totalesParcialesVencidos [1] += Double.parseDouble(sCampos[7]);
	    totalesParcialesVencidos [2] += Double.parseDouble(sCampos[8]);
	  }else{
	    classVence = color_fondo;
  	    totalesParcialesEnTermino [0] += Double.parseDouble(sCampos[6]);
	    totalesParcialesEnTermino [1] += Double.parseDouble(sCampos[7]);
	    totalesParcialesEnTermino [2] += Double.parseDouble(sCampos[8]);
      }
	  totalesParciales [0] += Double.parseDouble(sCampos[6]);
	  totalesParciales [1] += Double.parseDouble(sCampos[7]);
	  totalesParciales [2] += Double.parseDouble(sCampos[8]);
	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[2]), "JSDateToStr")%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="center" class="<%= classVence %>"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[9]), "JSDateToStr")%></div></td>
      <td class="fila-det-border" ><%=sCampos[3]%>-<%=sCampos[5]%></td>
      <td class="fila-det-border" ><%=sCampos[14]%></td>

      <td class="fila-det-border" ><div align="right"><%=Common.visualNumero(sCampos[6], 2 )%></div></td> 
      <td class="fila-det-border" ><div align="right"><%=Common.visualNumero(sCampos[7], 2 )%></div></td>

      <td class="fila-det-border" ><div align="right"><%=Common.visualNumero(sCampos[8], 2 )%></div></td>

      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/calc.png" title="Asiento contable del movimiento." width="18" height="18"  onClick="ventana=abrirVentana('vclientesAsientosAbm.jsp?nrointerno=<%= sCampos[10] %>&comprobante=<%=sCampos[3] + "-" +sCampos[5]%>', 'asie', 600, 300)" style="cursor:pointer"></div></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/kwin.png" title="Aplicaciones del movimiento." width="22" height="22"  onClick="ventana=abrirVentana('vClientesAplicacionesAbm.jsp?idcliente=<%=  sCampos[0] %>&cliente=<%=  sCampos[1] %>&nrointerno=<%= sCampos[10] %>&comprobante=<%=sCampos[3] + "-" +sCampos[5]%>', 'aplicaci', 700, 300)" style="cursor:pointer"></div></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/mimetypes/gnome-mime-application-vnd.ms-excel.png" title="Cuotas del movimiento." width="22" height="22"  onClick="ventana=abrirVentana('vClientesMovCuota.jsp?idcliente=<%=  sCampos[0] %>&cliente=<%=  sCampos[1] %>&nrointerno=<%= sCampos[10] %>&comprobante=<%=sCampos[3] + "-" +sCampos[5]%>', 'aplicaci', 700, 300)" style="cursor:pointer"></div></td>
   </tr>
<%
     esPrimero = false;
	 
   }
   
   %>
   
   
   
		   <tr class="permiso-dos"> 
			  <td colspan="4"><div align="right"> ACUMULADO &nbsp;</div></td>
			  <td ><div align="right">
			    <table width="100%" border="1" cellspacing="0" cellpadding="0">
                  <tr class="permiso-tres">
                    <td width="39%" ><div align="right">Vencido:</div></td>
                    <td width="61%"><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParcialesVencidos[0], 10, 4), 2 ) %></div></td>
                  </tr>
                  <tr class="permiso-tres">
                    <td><div align="right">Vencer: </div></td>
                    <td><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParcialesEnTermino[0], 10, 4), 2 ) %></div></td>
                  </tr>
                  <tr class="permiso-tres">
                    <td><div align="right">Total:</div></td>
                    <td><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParciales[0], 10, 4), 2 ) %></div></td>
                  </tr>
                </table>
			  </div></td>
			  <td ><div align="right">
			    <table width="100%" border="1" cellspacing="0" cellpadding="0">
                  <tr class="permiso-tres">
                    <td width="61%"><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParcialesVencidos[1], 10, 4), 2 ) %></div></td>
                  </tr>
                  <tr class="permiso-tres">
                    <td><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParcialesEnTermino[1], 10, 4), 2 ) %></div></td>
                  </tr>
                  <tr class="permiso-tres">
                    <td><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParciales[1], 10, 4), 2 ) %></div></td>
                  </tr>
                </table>
			  </div></td> 
			  <td ><div align="right">
			    <table width="100%" border="1" cellspacing="0" cellpadding="0">
                  <tr class="permiso-tres">
                    <td width="61%"><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParcialesVencidos[2], 10, 4), 2 ) %></div></td>
                  </tr>
                  <tr class="permiso-tres">
                    <td><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParcialesEnTermino[2], 10, 4), 2 ) %></div></td>
                  </tr>
                  <tr class="permiso-tres">
                    <td><div align="right"><%= Common.visualNumero( Common.getNumeroFormateado(totalesParciales[2], 10, 4), 2 ) %></div></td>
                  </tr>
                </table>
			  </div></td>
			  <td colspan="3" ><div align="center"></div>        <div align="center"></div>        <div align="center"></div></td>
		   </tr>	   
   
   
   
   <%-- 
    <tr class="text-dos-bold" > 
      <td colspan="3" >Saldo Documentado </td>
      <td ><div align="right">< %=BVCCA.getSaldo_total()%></div></td>
      <td colspan="5" ></td> 
    </tr>
    --%>
   </table>
   <input name="accion" value="" type="hidden">
   <input name="idcliente" value="<%=  BVCCA.getIdcliente() %>" type="hidden">
   <input name="cliente" value="<%=  BVCCA.getCliente() %>" type="hidden">
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


