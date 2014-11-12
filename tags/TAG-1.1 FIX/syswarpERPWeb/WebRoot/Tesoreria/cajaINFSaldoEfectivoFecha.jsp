<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: SaldosEfectivo
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Aug 01 11:33:07 GMT-03:00 2006 
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
String titulo = "INFORME SALDO EFECTIVO POR FECHA"; 
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterSaldosEfectivo   = null;
int totCol = 3; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCA"  class="ar.com.syswarp.web.ejb.BeanCajaINFSaldoEfectivoFecha"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCA" property="*" />
<%
 BCA.setResponse(response);
 BCA.setRequest(request);
 BCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCA.ejecutarValidacion();
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
tituCol[0] = "Identificador";
tituCol[1] = "Fecha";
tituCol[2] = "Saldo";
java.util.List SaldosEfectivo = new java.util.ArrayList();
SaldosEfectivo = BCA.getCajaSaldosFecha();
iterSaldosEfectivo = SaldosEfectivo.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>
<form action="cajaINFSaldoEfectivoFecha.jsp" method="POST" name="frm">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td><%=titulo%></td>
                </tr>
                <tr>
                  <td width="89%" height="38">
                     <table width="100%" border="0" align="center">
                        <tr class="text-globales">
                          <td width="35%" height="26" >Fecha Desde 
                            <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechaDesde" value="<%=BCA.getFechaDesde()%>" maxlength="12">
          <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date', 'img_Date_DOWN');showCalendar('frm','fechaDesde','BTN_date');return false;"> 
							<img align="absmiddle" border="0" name="BTN_date" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
                          <td width="29%"> Fecha Hasta 
                            <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechaHasta" value="<%=BCA.getFechaHasta()%>" maxlength="12">
                            <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date1', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date1', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date1', 'img_Date_DOWN');showCalendar('frm','fechaHasta','BTN_date1');return false;"> <img align="absmiddle" border="0" name="BTN_date1" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
                          <td width="36%">
                            <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
                                  <td><input name="aceptar" type="submit" class="boton" id="ir" value="  Aceptar  "></td>
                              </tr>
                          </table>                        </td>
                     </tr>
                   </table>                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" cols=<%=totCol+2-4%> id=rsTable name="rsTable"  >
  <tr class="fila-encabezado">
     <td width="33%" height="22" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[1]%></div></td>
     <td width="34%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="33%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[2]%></div></td>
   </tr>
   <%
	 int r = 0;
	 String fecha = "";
   while(iterSaldosEfectivo.hasNext()){ 
      r++;
			boolean cambia = false;
      String[] sCampos = (String[]) iterSaldosEfectivo.next(); 
      // estos campos hay que setearlos segun la grilla 
      if(!fecha.equals( sCampos[2]) ){
			  if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det"; 
        else color_fondo = "fila-det-verde";
			  fecha =   sCampos[2];
			  cambia = true;
			}
			if(cambia && r > 0 ){ 
			%>
      <tr height="3">
		      <td colspan="3" class="fila-det-border" bgcolor="#99cc66"> </td>
      </tr>				
			<% 
			}
			 %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[2]), "JSTsToStr" )%></div></td>
      <td class="fila-det-border" >
        <%=sCampos[1] + " - " + sCampos[0]%></td>
      <td class="fila-det-border" ><div align="right" class="<%= Common.colorSaldo(str.esNulo(sCampos[3]), "texto-saldo-positivo", "texto-saldo-negativo") %>"><%=str.esNulo(sCampos[3])%></div></td>
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

