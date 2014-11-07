<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: sascontactos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri May 27 12:30:35 ART 2011 
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
String titulo = "SAS - HISTORIAL DE CONTACTOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterSascontactos   = null;
int totCol = 7; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BSA"  class="ar.com.syswarp.web.ejb.BeanSasHistorialContactos"   scope="page"/>


<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BSA" property="*" />
<%
 BSA.setResponse(response);
 BSA.setRequest(request);
 BSA.setIdempresa ( new BigDecimal( session.getAttribute("empresa").toString() )  );  
 BSA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Descripción";
tituCol[2] = "Tipo de contacto";
tituCol[3] = "Canal de contacto";
tituCol[4] = "Motivo del contacto";
tituCol[5] = "Usuario de carga";
tituCol[6] = "Fecha y hora";
java.util.List Sascontactos = new java.util.ArrayList();
Sascontactos= BSA.getSascontactosList();
iterSascontactos = Sascontactos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>
<form action="sashistorialcontactos.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td  class="text-globales"><%=titulo%></td>
                   <td>&nbsp;</td>
                </tr>
                <tr>
                   <td width="30%" height="38">
                      <table width="100%" border="0">
                         <tr>
                           <td width="21%"> <input name="fechadesde" type="text" class="cal-TextBox"
													id="fechadesde" onFocus="this.blur()"
													value="<%=BSA.getFechadesde() %>" size="12"
													maxlength="12" readonly>                                    </td>
                                    <td width="28%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_7');return false;"><img
														src="../vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_7" width="22" height="17" border="0"
														align="absmiddle"> </a> </td>
							  <td width="22%">
						   <input name="fechahasta" type="text" class="cal-TextBox"
													id="fechahasta" onFocus="this.blur()"
													value="<%=BSA.getFechahasta() %>" size="12"
													maxlength="12" readonly>                                    </td>
                              <td width="29%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_8', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_8', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_8', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_8');return false;"><img
														src="../vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_8" width="22" height="17" border="0"
														align="absmiddle"> </a>							  </td>
                         </tr>
                     </table>
                   </td>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BSA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="2%" height="19">&nbsp; </td>
                                          <td width="44%">&nbsp;Total de registros:&nbsp;<%=BSA.getTotalRegistros()%></td>
                                          <td width="9%" >Visualizar:</td>
                                          <td width="12%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BSA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BSA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BSA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="9%">&nbsp;P&aacute;gina:</td>
                                          <td width="13%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BSA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BSA.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select> 
                                          </td>
                                          <td width="11%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BSA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
	 <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
	 <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
	 <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
   </tr>
   <%int r = 0;
   while(iterSascontactos.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterSascontactos.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 

      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>	  
	  <td class="fila-det-border" ><%=sCampos[8]%>&nbsp;</td>	  
	  <td class="fila-det-border" ><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[10]), "JSTsTOStr")%>&nbsp;</td>	  
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

