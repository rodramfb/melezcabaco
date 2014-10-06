<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vMovArtFecha
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Nov 14 09:20:44 GMT-03:00 2006 
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
String titulo = "STOCK A FECHA POR DEPOSITO";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVMovArtFecha   = null;
int totCol = 4; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVMAFA"  class="ar.com.syswarp.web.ejb.BeanVMovArtDepositoxFecha"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVMAFA" property="*" />
<%
 BVMAFA.setResponse(response);
 BVMAFA.setRequest(request);
 BVMAFA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BVMAFA.setUsuario(usuario);
 BVMAFA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Artículo";
tituCol[2] = "Cantidad";
tituCol[3] = "Depósito";
java.util.List VMovArtFecha = new java.util.ArrayList();
VMovArtFecha= BVMAFA.getVMovArtFechaList();
iterVMovArtFecha = VMovArtFecha.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="popupcalendar" class="text"></div>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="stockArtDepositoxFecha.jsp" method="POST" name="frm">
<input name="accion" type="hidden" value="consulta">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="17%" height="38">
									  <span class="fila-det-border">
										<span >
                    <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecha_ms" value="<%= BVMAFA.getFecha_ms() %>" maxlength="12">
											<a class="so-BtnLink" href="javascript:calClick();return false;" 
											 onMouseOver="calSwapImg('BTN_date_1', 'img_Date_OVER',true);" 
											 onMouseOut="calSwapImg('BTN_date_1', 'img_Date_UP',true);" 
											 onClick="calSwapImg('BTN_date_1', 'img_Date_DOWN');showCalendar('frm','fecha_ms','BTN_date_1');return false;"> <img align="absmiddle" border="0" name="BTN_date_1" src="vs/calendar/btn_date_up.gif" width="22" height="17">											</a>											</span></span>									 </td>
                   <td width="83%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="25%">
                              <input name="ocurrencia" type="text" value="<%=BVMAFA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="69%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="4%" height="19">&nbsp; </td>
                                          <td width="25%">&nbsp;Total de registros:&nbsp;<%=BVMAFA.getTotalRegistros()%></td>
                                          <td width="15%" >Visualizar:</td>
                                          <td width="15%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BVMAFA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BVMAFA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                             </select>                                          </td>
                                          <td width="14%" class="text-globales">&nbsp;</td>
                                        </tr>
                                    </table>                                 </td>
                              </tr>
                           </table>                        </td>
                     </tr>
                   </table>                </td>
            </tr>
			<tr>
			  <td  colspan="2" class="text-globales"><div align="center">
				  <input name="paginaSeleccion" type="hidden" value="1">
				P&aacute;gina: <%= Common.getPaginacion(BVMAFA.getTotalPaginas(),
				BVMAFA.getTotalRegistros(), BVMAFA.getPaginaSeleccion(), BVMAFA.getLimit(),
				BVMAFA.getOffset()) %> </div></td>
			</tr>
			<tr>
			  <td  colspan="2" class="text-globales"><table width="45%" border="0">
                <tr class="text-globales">
                  <td width="11%" >Deposito</td>
                  <td width="32%"><input name="descrip_dt" type="text" class="campo" id="descrip_dt" value="<%= str.esNulo(BVMAFA.getDescrip_dt())%>" size="30" readonly></td>
                  <td width="57%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Stock/lov_deposito.jsp')" style="cursor:pointer"></td>
                  <td width="57%"><input name="consultar" type="submit" class="boton" id="consultar" value="consultar"></td>
                  <input name="codigo_dt" type="hidden" id="codigo_dt" value="<%=BVMAFA.getCodigo_dt()%>">
                </tr>
              </table></td>
			  </tr>				
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVMAFA" property="mensaje"/></td>
  </tr>
</table>
<%if (BVMAFA.getAccion().equalsIgnoreCase("consulta")){%>	
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="49%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="37%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
   </tr>
   <%int r = 0;
   while(iterVMovArtFecha.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVMovArtFecha.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>			
   </tr>
<%
   }%>
   </table>
<%}%>      

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

