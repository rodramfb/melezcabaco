<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: stockDesarmadoLog
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Dec 26 14:49:56 ART 2007 
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
String titulo = "INFORME DE DESARMADO DE PRODUCTOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterStockDesarmadoLog   = null;
int totCol = 12; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BSDLA"  class="ar.com.syswarp.web.ejb.BeanStockDesarmadoLogAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BSDLA" property="*" />
<%
 BSDLA.setResponse(response);
 BSDLA.setRequest(request);
 BSDLA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BSDLA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <link href="<%=pathskin%>/tnx.css" rel="stylesheet" type="text/css">
 <link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>  
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 
</head> 
<%
// titulos para las columnas
tituCol[0] = "Log";
tituCol[1] = "idesquema";
tituCol[2] = "Esquema";
tituCol[3] = "Comprobante";
tituCol[4] = "Recursivo";
tituCol[5] = "Cantidad";
tituCol[6] = "idmotivodesarma";
tituCol[7] = "Motivo";
tituCol[8] = "Art.";
tituCol[9] = "Cant.";
tituCol[10] = "idempresa";
tituCol[11] = "Usuario";

java.util.List StockDesarmadoLog = new java.util.ArrayList();
StockDesarmadoLog= BSDLA.getStockDesarmadoLogList();
iterStockDesarmadoLog = StockDesarmadoLog.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>
<form action="stockDesarmadoLogAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" >
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" >
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td><%=titulo%></td>
                </tr>
                <tr>
                  <td width="89%" height="38">
                     <table width="100%" border="0">
                        <tr>
                          <td width="72%" height="26">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="27%" height="19">&nbsp;Total de registros:&nbsp;<%=BSDLA.getTotalRegistros()%></td>
                                         <td width="10%" >&nbsp;</td>
                                         <td width="21%">
                                            Visualizar:
                                            <select name="limit" class="campo">
                                               <%for(i=15; i<= 150 ; i+=15){%> 
                                                   <%if(i==BSDLA.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                   <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                   <%}
                                                      if( i >= BSDLA.getTotalRegistros() ) break;
                                                    %>
                                               <%}%>
                                               <option value="<%= BSDLA.getTotalRegistros()%>">Todos</option>
                                         </select>                                          </td>
                                         <td colspan="2">&nbsp;P&aacute;gina:
                                            <select name="paginaSeleccion"  class="campo">
                                               <%for(i=1; i<= BSDLA.getTotalPaginas(); i++){%>
                                                   <%if ( i==BSDLA.getPaginaSeleccion() ){%>
                                                      <option value="<%=i%>" selected><%=i%></option> 
                                                   <%}else{%>
                                                      <option value="<%=i%>"><%=i%></option>
                                                   <%}%>
                                               <%}%>
                                            </select>                                          </td>
                                         <td width="24%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                      </tr>
                                       <tr >
                                         <td height="19" colspan="6"><hr color="#FFFFFF"></td> 
                                       </tr>
                                       <tr class="text-globales">
                                         <td height="19">Fecha Desde </td>
                                         <td colspan="2" ><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechadesde" value="<%= BSDLA.getFechadesde() %>" maxlength="12">
											<a class="so-BtnLink" href="javascript:calClick();return false;" 
											 onMouseOver="calSwapImg('BTN_date_1', 'img_Date_OVER',true);" 
											 onMouseOut="calSwapImg('BTN_date_1', 'img_Date_UP',true);" 
											 onClick="calSwapImg('BTN_date_1', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_1');return false;"> <img align="absmiddle" border="0" name="BTN_date_1" src="vs/calendar/btn_date_up.gif" width="22" height="17">											</a></td>
                                         <td width="13%">Fecha Hasta </td>
                                         <td colspan="2"><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechahasta" value="<%= BSDLA.getFechahasta() %>" maxlength="12">
                                           <a class="so-BtnLink" href="javascript:calClick();return false;" 
											 onMouseOver="calSwapImg('BTN_date_2', 'img_Date_OVER',true);" 
											 onMouseOut="calSwapImg('BTN_date_2', 'img_Date_UP',true);" 
											 onClick="calSwapImg('BTN_date_2', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_2');return false;"> <img align="absmiddle" border="0" name="BTN_date_2" src="vs/calendar/btn_date_up.gif" width="22" height="17"> </a></td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BSDLA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="37%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="17%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>
     <td width="21%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[11]%></td>		 
   </tr>
   <%int r = 0;
	 String log = "";
	 
   while(iterStockDesarmadoLog.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterStockDesarmadoLog.next(); 
			String rec = sCampos[0].equals("1") ? "Si" : "No";
      // estos campos hay que setearlos segun la grilla 
			if(!log.equals(sCampos[0])){
        if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
        else color_fondo = "fila-det-verde";
				log = sCampos[0];
				
				%>
   <tr > 
      <td colspan="7" class="text-globales" height="3"> </td>
   </tr>	
   <tr class="fila-det-rojo" scope="col" > 
      <td colspan="3" class="fila-det-border" > Esquema: <%= str.esNulo((sCampos[1] + " - " + sCampos[2] ).toUpperCase())%>   </td>
      <td class="fila-det-border" >Cantidad Desarmado: <%= str.esNulo(sCampos[5]) %></td>
      <td colspan="3" class="fila-det-border" > Motivo: <%= str.esNulo(sCampos[7])%>  </td>
    </tr>	 			
				<%
				
			}%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><%= str.esNulo(sCampos[0])%>&nbsp;</td>
      <td class="fila-det-border" ><%= str.esNulo(sCampos[3])%>&nbsp;</td>
      <td class="fila-det-border" ><%= str.esNulo(rec)%>&nbsp;</td>
      <td class="fila-det-border" ><%= str.esNulo(sCampos[9])%>&nbsp;</td>
      <td class="fila-det-border" ><%=str.esNulo(sCampos[8])%>&nbsp;</td> 
      <td class="fila-det-border" ><%= str.esNulo(sCampos[11])%>&nbsp;</td>
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

