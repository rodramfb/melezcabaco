<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/*  
   Grilla para la entidad: crmindividuos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jun 14 17:23:23 GMT-03:00 2007 
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
String titulo = "Gestion de individuos";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterCrmindividuos   = null;
int totCol = 26; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html> 
<jsp:useBean id="BCA"  class="ar.com.syswarp.web.ejb.BeanCrmindividuosxusuarioAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCA" property="*" />
<%
 BCA.setResponse(response);
 BCA.setRequest(request);
 BCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCA.setUsu( usuario);
 BCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
  <script language="JavaScript">
  
function mostrarLOVDETA(pagina) {
	frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width=800,height=450,status=yes');
	if (frmLOV.opener == null) 
		frmLOV.opener = self;
}  
 
</script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "R. Social";
tituCol[2] = "TE ";
tituCol[3] = "Cel.";
tituCol[4] = "Email";
tituCol[5] = "Dom. Part.";
tituCol[6] = "F. Nac.";
tituCol[7] = "Empresa";
tituCol[8] = "Dom. Lab";
tituCol[9] = "TE Lab.";
tituCol[10] = "Profesion";
tituCol[11] = "Actividad";
tituCol[12] = "Deportes";
tituCol[13] = "Hobbies";
tituCol[14] = "Actividad Social";
tituCol[15] = "Diario";
tituCol[16] = "Revista ";
tituCol[17] = "Lugar Veraneo";
tituCol[18] = "Obs.";
tituCol[19] = "Usuario";
tituCol[20] = "T. Cliente";
tituCol[21] = "Clasif. Cliente";    
tituCol[22] = "Fuente";
tituCol[25] = "F.Visita";
java.util.List Crmindividuos = new java.util.ArrayList();
Crmindividuos= BCA.getCrmindividuosList();
iterCrmindividuos = Crmindividuos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="crmindividuosxusuarioAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td width="9%" height="38">
                      <table width="36%" border="0">
                         <tr>
                            <td width="27%">
                               <input name="alta" id="alta" value="alta" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('alta','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name">                            </td>
                            <td width="27%">
                               <input name="modificacion" id="modificacion" value="modificacion" type="image" src="../imagenes/default/btn_edit_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('modificacion','','../imagenes/default/btn_edit_over.gif',1)" onClick="document.frm.accion.value = this.name">                            </td>
                            <td width="27%">
                               <input name="baja" id="baja" value="baja" type="image" src="../imagenes/default/btn_remove_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('baja','','../imagenes/default/btn_remove_over.gif',1)" onClick="return confirmarBaja('frm');">                            </td>
                         </tr>
                      </table>                   </td>
                   <td width="91%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">&nbsp;</td>
                           <td width="4%">&nbsp;</td>
                           <td width="90%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="3%" height="19">&nbsp; </td>
                                          <td width="34%">&nbsp;Total de registros:&nbsp;<%=BCA.getTotalRegistros()%></td>
                                          <td width="15%" >Visualizar:</td>
                                          <td width="13%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BCA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="9%">&nbsp;P&aacute;gina:</td>
                                          <td width="14%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BCA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BCA.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option> 
                                                    <%}%>
                                                <%}%>
                                             </select>                                          </td>
                                          <td width="12%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                       </tr>
                                    </table>                                 </td>
                              </tr>
                           </table>                        </td>
                     </tr>
                   </table>                </td>
            </tr>
                <tr> 
                  <td height="250" colspan="2">
				    <div align="center">
					  <iframe frameborder="0" id="buscarIndividuosXusuario" height="100%"  width="95%" name="buscarIndividuosXusuario" src="buscarIndividuosXusuario.jsp<%= BCA.getParamUrl() %>" scrolling="auto" align="middle" ></iframe>
                    </div>
				  </td>
                </tr>
				
                <tr>
                  <td colspan="2">&nbsp; </td>
                </tr>				
          </table>
  </td>
</tr>
</table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
  <td width="9%" >&nbsp;</td>
  <td width="3%" >&nbsp;</td>
  <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
  <td width="24%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="13%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="18%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="18%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[25]%></td>
  </tr>
   <%int r = 0;
   while(iterCrmindividuos.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterCrmindividuos.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
   <td  class="fila-det-border" >
<table width="69" border="0" cellspacing="0">
<tr>
<td width="22"><img src="../imagenes/default/gnome_tango/apps/config-users.png" width="18" height="18" border="0" alt="Familiares" onClick="mostrarLOVDETA('crmfamiliaresxusuarioAbm.jsp?idindividuos=<%=sCampos[0]%>&razon_nombre=<%=sCampos[1]%>')" style="cursor:pointer" ></td>
<td width="22"><img src="../imagenes/default/gnome_tango/apps/calc.png" width="18" height="18" border="0" alt="Cotizaciones" onClick="mostrarLOVDETA('crmcotizacionesxusuarioAbm.jsp?idindividuos=<%=sCampos[0]%>&razon_nombre=<%=sCampos[1]%>')" style="cursor:pointer" ></td>
<td width="19"><img src="../imagenes/default/gnome_tango/phone.jpg" width="18" height="18" border="0" alt="Llamadas" onClick="mostrarLOVDETA('crmllamadosxusuarioAbm.jsp?idindividuos=<%=sCampos[0]%>&razon_nombre=<%=sCampos[1]%>')" style="cursor:pointer" ></td>
</tr>
</table></td>
   <td class="fila-det-border" ><input type="radio" name="idindividuos" value="<%= sCampos[0]%>"></td>
      <td class="fila-det-border" ><%=str.esNulo(sCampos[0])%>&nbsp;</td>
      <td class="fila-det-border" ><%=str.esNulo(sCampos[1])%>&nbsp;</td>
      <td class="fila-det-border" ><%=str.esNulo(sCampos[2])%>&nbsp;</td>
      <td class="fila-det-border" ><%=str.esNulo(sCampos[3])%>&nbsp;</td>
      <td class="fila-det-border" ><%=!str.esNulo(sCampos[4]).equals("") && !str.esNulo(sCampos[4]).equalsIgnoreCase("NO POSEE")? "<a href=\"mailto:" + str.esNulo(sCampos[4]) + "\">" + str.esNulo(sCampos[4]) + "</a>" : str.esNulo(sCampos[4]) %>&nbsp;</td>
      <td class="fila-det-border" ><%=str.esNulo(sCampos[5])%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[25]), "JSTsToStr")%></td>
   </tr>
<%
   }%>
  </table>
   <input name="accion" value="" type="hidden">
   <input name="filtro" value="" type="hidden">
   <input name="fechacotizaciondesde" value="" type="hidden">
   <input name="fechacotizacionhasta" value="" type="hidden">
   <input name="fechallamadodesde" value="" type="hidden">
   <input name="fechallamadohasta" value="" type="hidden">
   <input name="fechavisitadesde" value="" type="hidden">
   <input name="fechavisitahasta" value="" type="hidden">	 
   <input name="nombrepariente" value="" type="hidden">	  
   <input name="ocurrencia" value="" type="hidden">
   <input name="paramUrl" value="" type="hidden">
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

