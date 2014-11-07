<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: Prospecto
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Oct 27 16:35:02 GMT-03:00 2006 
   Observaciones: 
      .

  
*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Prospecto";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterProspecto   = null;
int totCol = 27; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BGLA"  class="ar.com.syswarp.web.ejb.BeanClientesProspectoLov"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BGLA" property="*" />
<% 
 BGLA.setResponse(response);
 BGLA.setRequest(request);
 BGLA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BGLA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <script >
function bajarDatos(prospecto, razon,idtipodocumento,tipodocumento,nrodocumento,brutos,idtipoiva,tipoiva,idcondicion,condicion,descuento1,descuento2,descuento3,idctaneto,idmoneda,moneda,idlista,lista,idtipoclie,tipoclie,observacion,lcredito,idtipocomp,tipocomp,autorizado,idcredcate,credcate){
	for(var i = 0; i<10;i++)
		if(opener.document.forms[i].prospecto)
			break;
	opener.document.forms[i].prospecto.value = prospecto;
	opener.document.forms[i].razon.value = razon; 
	opener.document.forms[i].idtipodocumento.value = idtipodocumento;
	opener.document.forms[i].tipodocumento.value = tipodocumento; 
    opener.document.forms[i].nrodocumento.value = nrodocumento; 
	opener.document.forms[i].brutos.value = brutos; 
	opener.document.forms[i].idtipoiva.value = idtipoiva; 
	opener.document.forms[i].tipoiva.value = tipoiva; 
	opener.document.forms[i].idcondicion.value = idcondicion; 
	opener.document.forms[i].condicion.value = condicion; 
	opener.document.forms[i].descuento1.value = descuento1; 
	opener.document.forms[i].descuento2.value = descuento2; 
	opener.document.forms[i].descuento3.value = descuento3; 
	opener.document.forms[i].idctaneto.value = idctaneto; 
	opener.document.forms[i].idmoneda.value = idmoneda; 
	opener.document.forms[i].moneda.value = moneda; 
	opener.document.forms[i].idlista.value = idlista; 
	opener.document.forms[i].lista.value = lista; 
	opener.document.forms[i].idtipoclie.value = idtipoclie; 
	opener.document.forms[i].tipoclie.value = tipoclie; 
	opener.document.forms[i].observacion.value = observacion; 
	opener.document.forms[i].lcredito.value = lcredito; 
	opener.document.forms[i].idtipocomp.value = idtipocomp; 
	opener.document.forms[i].tipocomp.value = tipocomp; 
	opener.document.forms[i].autorizado.value = autorizado; 
	opener.document.forms[i].idcredcate.value = idcredcate; 
	opener.document.forms[i].credcate.value = credcate; 
	window.close();
}
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Codigo";
tituCol[1] = "Razon";  
tituCol[2] = "cod Tipo Documento";
tituCol[3] = "Tipo Documento";
tituCol[4] = "Numero de Documento";
tituCol[5] = "Brutos";
tituCol[6] = "cod tipo iva";
tituCol[7] = "tipo iva";
tituCol[8] = "cod condicion";
tituCol[9] = "condicion";
tituCol[10] = "Descuento 1";
tituCol[11] = "Descuento 2";  
tituCol[12] = "Descuento 3";
tituCol[13] = "cta neto";
tituCol[14] = "cod moneda";
tituCol[15] = "moneda";
tituCol[16] = "cod lista";
tituCol[17] = "lista";
tituCol[18] = "cod tipo cliente";
tituCol[19] = "tipo cliente";
tituCol[20] = "observacion";
tituCol[21] = "lcredito";
tituCol[22] = "cod tipo comp";
tituCol[23] = "tipo comp";
tituCol[24] = "autorizado";
tituCol[25] = "cod credcate";
tituCol[26] = "credcate";
java.util.List prospecto = new java.util.ArrayList();
prospecto= BGLA.getProspectoList();
iterProspecto = prospecto.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >   
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_prospecto.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td colspan="2"  ><%=titulo%></td>
                </tr>
                <tr>
                   <td width="3%" height="38">&nbsp;                  </td>
                   <td width="97%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BGLA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BGLA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BGLA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BGLA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BGLA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BGLA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BGLA.getPaginaSeleccion() ){%>
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
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BGLA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
	 <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
	 <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
	 <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
	 <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>	 
	 <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>
	 <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[10]%></td>
	 <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[11]%></td>
	 <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[12]%></td>
	 <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[13]%></td>
	 <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[15]%></td> 
	 <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[17]%></td>
	 <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[19]%></td>
	 <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[20]%></td>
	 <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[21]%></td>
	 <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[23]%></td>
	 <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[24]%></td>
	 <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[26]%></td>
   </tr>
   <%int r = 0;
   while(iterProspecto.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterProspecto.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/down.png" width="18" height="18" onClick="bajarDatos('<%=sCampos[0]%>', '<%=sCampos[1]%>', '<%=sCampos[2]%>', '<%=sCampos[3]%>', '<%=sCampos[4]%>', '<%=sCampos[5]%>', '<%=sCampos[6]%>', '<%=sCampos[7]%>', '<%=sCampos[8]%>','<%=sCampos[9]%>','<%=sCampos[10]%>','<%=sCampos[11]%>' ,'<%=sCampos[12]%>','<%=sCampos[13]%>','<%=sCampos[14]%>','<%=sCampos[15]%>','<%=sCampos[16]%>','<%=sCampos[17]%>','<%=sCampos[18]%>','<%=sCampos[19]%>','<%=sCampos[20]%>','<%=sCampos[21]%>','<%=sCampos[22]%>','<%=sCampos[23]%>','<%=sCampos[24]%>','<%=sCampos[25]%>','<%=sCampos[26]%>' )"></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
 	  <td class="fila-det-border" ><%=sCampos[3]%>&nbsp; </td>
	  <td class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td> 
	  <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>	  
	  <td class="fila-det-border" ><%=sCampos[9]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[10]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[11]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[12]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[13]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[15]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[17]%>&nbsp;</td>   
	  <td class="fila-det-border" ><%=sCampos[19]%>&nbsp;</td>   
	  <td class="fila-det-border" ><%=sCampos[20]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[21]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[23]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[24]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[26]%>&nbsp;</td>
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

