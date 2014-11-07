<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesRemitosTotalCondicion
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jan 04 15:02:55 ART 2011 
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
String titulo = "TOTALES PENDIENTES X MODALIDAD DE PAGO";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesRemitosTotalCondicion   = null;
int totCol = 11; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCRTCA"  class="ar.com.syswarp.web.ejb.BeanVClientesRemitosTotalCondicion"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCRTCA" property="*" />
<%
 BVCRTCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BVCRTCA.setResponse(response);
 BVCRTCA.setRequest(request);
 BVCRTCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
  <link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">

 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "idzona";
tituCol[1] = "Zona";
tituCol[2] = "idcondicion";
tituCol[3] = "Condición";
tituCol[4] = "mes";
tituCol[5] = "ano";
tituCol[6] = "idestado";
tituCol[7] = "Total";
tituCol[8] = "Total iva";
tituCol[9] = "Flete";
tituCol[10] = "Total Gral.";


java.util.List VClientesRemitosTotalCondicion = new java.util.ArrayList();
VClientesRemitosTotalCondicion= BVCRTCA.getVClientesRemitosTotalCondicionList();
iterVClientesRemitosTotalCondicion = VClientesRemitosTotalCondicion.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="popupcalendar" class="text"></div>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div> 
<form action="vClientesRemitosTotalCondicion.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="11%" height="38"><table width="100%" border="0"> 
                    <tr>
                      <td height="26" colspan="3" class="text-globales"><hr color="#FFFFFF"></td>
                    </tr>
                    <tr>
                      <td width="6%" height="26" class="text-globales">Periodo:(*) </td>
                      <td width="22%"><span class="fila-det-border">
                        <input name="fecha" type="text" class="cal-TextBox" id="fecha" onFocus="this.blur()" value="<%=BVCRTCA.getFecha()%>" size="10" maxlength="10" readonly>
                      <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fecha','BTN_date_7');return false;"> <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></span> </td>
                      <td width="72%"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr class="text-globales">
                                  <td width="23%" height="19">&nbsp;Total de registros:&nbsp;<%=BVCRTCA.getTotalRegistros()%></td>
                                  <td width="11%" >&nbsp;</td>
                                  <td width="11%"></td>
                                  <td width="7%">&nbsp;</td>
                                  <td width="12%"></td>
                                  <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                </tr>
                            </table></td>
                          </tr>
                      </table></td>
                    </tr>
                  </table></td>
              </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCRTCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[7]%></div></td>
     <td width="18%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[8]%></div></td>
     <td width="18%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[9]%></div></td>
     <td width="23%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[10]%></div></td>
   </tr>
   <%int r = 0;
   String zona = "";
   double totalSinIvaZona = 0d;
   double totalIvaZona = 0d;  
   double totalFleteZona = 0d;   
   double totalGeneralZona = 0d;   
      
   while(iterVClientesRemitosTotalCondicion.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesRemitosTotalCondicion.next(); 
      // estos campos hay que setearlos segun la grilla 
	  if(!zona.equalsIgnoreCase(sCampos[1])){
	    if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
	    else color_fondo = "fila-det-verde";
		
		if(!zona.equals("")){	  
        %>

    	   <tr class="permiso-tres" > 
			  <td colspan="3" class="fila-det-border" ><div align="right"><strong>Totales zona&nbsp; </strong></div></td>
			  <td class="fila-det-border" ><div align="right"><strong><%=Common.getNumeroFormateado(totalSinIvaZona, 10, 2)%></strong></div></td>
			  <td class="fila-det-border" ><div align="right"><strong><%=Common.getNumeroFormateado(totalIvaZona, 10, 2)%></strong></div></td>
			  <td class="fila-det-border" ><div align="right"><strong><%=Common.getNumeroFormateado(totalFleteZona, 10, 2)%></strong></div></td>
			  <td class="fila-det-border" ><div align="right"><strong><%=Common.getNumeroFormateado(totalGeneralZona, 10, 2)%></strong></div></td>
		   </tr>
		   <tr class="<%=color_fondo%>" scope="col" > 
			  <td colspan="7" class="fila-det-border" height="3px" bgcolor="#003366"></td>
		   </tr>		   
        <%  
		  totalSinIvaZona = 0d;
		  totalIvaZona = 0d;	
		  totalFleteZona = 0d;	
		  totalGeneralZona = 0d;
		}
		%>
  
	   <tr class="text-dos-bold"> 
		  <td colspan="7" class="fila-det-border" ><strong><%=sCampos[1]%></strong></td>
	   </tr>  
  
  <%
	    zona = sCampos[1];
	  }
	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="18" height="18" border="0" style="cursor:pointer" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=remitos_clientes_zona_condicion_frame&fechadesde=<%=BVCRTCA.getFecha()%>&idzona=<%=sCampos[0]%>&idcondicion=<%=sCampos[2]%>', 'condiocionzona', 750, 500);"></td>
      <td class="fila-det-border" >&nbsp;</td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[3]%></td>
      <td class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado(Double.parseDouble(sCampos[7]), 10, 2)%></div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado(Double.parseDouble(sCampos[8]), 10, 2)%></div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado(Double.parseDouble(sCampos[9]), 10, 2)%></div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado(Double.parseDouble(sCampos[10]), 10, 2)%></div></td>
   </tr>
<%
      totalSinIvaZona += Double.parseDouble(sCampos[7]);
	  totalIvaZona += Double.parseDouble(sCampos[8]);
	  totalFleteZona += Double.parseDouble(sCampos[9]);
	  totalGeneralZona += Double.parseDouble(sCampos[10]);
   }
   if(!zona.equals("")){	  
	%>

	   <tr class="permiso-tres" > 
		  <td colspan="3" class="fila-det-border" ><div align="right"><strong>Totales zona&nbsp; </strong></div></td>
		  <td class="fila-det-border" ><div align="right"><strong><%=Common.getNumeroFormateado(totalSinIvaZona, 10, 2)%></strong></div></td>
		  <td class="fila-det-border" ><div align="right"><strong><%=Common.getNumeroFormateado(totalIvaZona, 10, 2)%></strong></div></td>
          <td class="fila-det-border" ><div align="right"><strong><%=Common.getNumeroFormateado(totalFleteZona, 10, 2)%></strong></div></td>
          <td class="fila-det-border" ><div align="right"><strong><%=Common.getNumeroFormateado(totalGeneralZona, 10, 2)%></strong></div></td>
	   </tr>
	<%  
	}
	%>
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

