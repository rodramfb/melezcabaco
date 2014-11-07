<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesSubContabilidad
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu May 31 10:18:14 ART 2012 
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
String color_fondo ="fila-det";
String titulo = "SUBCONTABILIDAD VENTAS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesSubContabilidad   = null;
int totCol = 12; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCSCA"  class="ar.com.syswarp.web.ejb.BeanVClientesSubContabilidadAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCSCA" property="*" />
<%
 BVCSCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BVCSCA.setResponse(response);
 BVCSCA.setRequest(request);
 BVCSCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
</head>
<%
// titulos para las columnas
// fechamov, cartera, nrointerno, tipomov, nrocuenta, cuenta, importe, cencosto, subcencosto, detalle, fechav, idempresa
tituCol[0] = "Fecha";
tituCol[1] = "cartera";
tituCol[2] = "Nro.Asiento";
tituCol[3] = "tipomov";
tituCol[4] = "nrocuenta";
tituCol[5] = "Cuenta";
tituCol[6] = "importe";
tituCol[7] = "cencosto";
tituCol[8] = "subcencosto";
tituCol[9] = "Detalle Diario";
tituCol[10] = "ejercicio";
tituCol[11] = "fechav";
java.util.List VClientesSubContabilidad = new java.util.ArrayList();
VClientesSubContabilidad= BVCSCA.getVClientesSubContabilidadList();
iterVClientesSubContabilidad = VClientesSubContabilidad.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="popupcalendar" class="text"></div>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesSubContabilidadAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="27"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr height="3px" bgcolor="#FFFFFF">
                   <td height="3px"  ></td>
                </tr>				
                <tr>
                  <td width="11%" height="38">
				     <table width="100%" border="0" cellspacing="0" cellpadding="0" class="text-globales">
                      <tr>
                        <td width="23%">Fecha Desde(*):</td>
                        <td width="22%"><span class="fila-det-border"><a class="so-BtnLink" href="javascript:calClick();return false;" 
					 onMouseOver="calSwapImg('BTN_date_0', 'img_Date_OVER',true);" 
					 onMouseOut="calSwapImg('BTN_date_0', 'img_Date_UP',true);" 
					 onClick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_0');return false;"></a></span>
                          <table width="36%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="26%"><span class="fila-det-border">
                                <input name="fechadesde" type="text" class="cal-TextBox" id="fechadesde" onFocus="this.blur()" value="<%= BVCSCA.getFechadesde() %>" size="12" maxlength="12" readonly>
                              </span></td>
                              <td width="74%"><a class="so-BtnLink" href="javascript:calClick();return false;" 
					 onMouseOver="calSwapImg('BTN_date_0', 'img_Date_OVER',true);" 
					 onMouseOut="calSwapImg('BTN_date_0', 'img_Date_UP',true);" 
					 onClick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_0');return false;"><img align="absmiddle" border="0" name="BTN_date_0" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
                            </tr>
                          </table></td>
                        <td width="17%">Fecha Hasta(*): </td>
                        <td width="16%"><span class="fila-det-border"><a class="so-BtnLink" href="javascript:calClick();return false;" 
					 onMouseOver="calSwapImg('BTN_date_1', 'img_Date_OVER',true);" 
					 onMouseOut="calSwapImg('BTN_date_1', 'img_Date_UP',true);" 
					 onClick="calSwapImg('BTN_date_1', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_1');return false;"></a></span>
                          <table width="35%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="23%"><span class="fila-det-border">
                                <input name="fechahasta" type="text" class="cal-TextBox" id="fechahasta" onFocus="this.blur()" value="<%= BVCSCA.getFechahasta() %>" size="12" maxlength="12" readonly>
                              </span></td>
                              <td width="77%"><a class="so-BtnLink" href="javascript:calClick();return false;" 
					 onMouseOver="calSwapImg('BTN_date_1', 'img_Date_OVER',true);" 
					 onMouseOut="calSwapImg('BTN_date_1', 'img_Date_UP',true);" 
					 onClick="calSwapImg('BTN_date_1', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_1');return false;"><img align="absmiddle" border="0" name="BTN_date_1" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
                            </tr>
                          </table></td>
                        <td width="22%"><div align="center">
                          <input name="ir" type="submit" class="boton" id="ir" value="  >>  ">
                        </div></td>
                      </tr>
                    </table></td>
              </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCSCA" property="mensaje"/></td>
  </tr>
</table>


<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >

   <%
   int r = 0;
   boolean existenReg = false;
   boolean esPrimero = true;
   String flagAsiento = "";
   float totalesParciales[] = new float[2];
   float totalGeneral[] = new float[2];
   while(iterVClientesSubContabilidad.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesSubContabilidad.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (!flagAsiento.equals(sCampos[2]) ){
	    flagAsiento = sCampos[2];	  
        if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
        else color_fondo = "fila-det-verde";

        if(!esPrimero){

  %>
  <tr class="permiso-tres">
     <td colspan="4" >&nbsp;</td>
     <td width="13%" ><div align="right"><%= Common.getNumeroFormateado( ( totalesParciales[0]  ), 10, 2) %></div></td>
     <td width="12%" ><div align="right"><%= Common.getNumeroFormateado( ( totalesParciales[1]  ), 10, 2) %></div></td>
  </tr>

  <%
          totalGeneral[0] += totalesParciales[0];
  	      totalGeneral[1] += totalesParciales[1];          
          totalesParciales[0] = totalesParciales[1] = 0f;
        }
  %>

  <tr class="text-dos-bold">
     <td width="18%" ><%=tituCol[0]%>: <%= Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf( sCampos[0] ), "JSTsToStr")%> </td>
     <td width="20%" ><%=tituCol[2]%>: <%=sCampos[2]%></td>
     <td colspan="4" ><%=tituCol[9]%>: <%=sCampos[9]%> <div align="right"></div>       <div align="right"></div></td>
  </tr>
  <tr class="text-dos">
     <td colspan="4" ><%=tituCol[5]%></td>
     <td width="13%" ><div align="right">Debe</div></td>
     <td width="12%" ><div align="right">Haber</div></td>
  </tr>


<%
	  }
	  
	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td colspan="4" class="fila-det-border" >&nbsp;<%=sCampos[4]%> - <%=sCampos[5]%></td>
      <td bgcolor="#FFFFCC" class="fila-det-border" >
      <div align="right"><%= sCampos[3].equalsIgnoreCase("D") ?  Common.getNumeroFormateado(Float.parseFloat(sCampos[6]), 10, 2) : "&nbsp;" %></div></td>
      <td bgcolor="#DEDABF" class="fila-det-border" >
      <div align="right"><%= sCampos[3].equalsIgnoreCase("H") ?  Common.getNumeroFormateado(Float.parseFloat(sCampos[6]), 10, 2) : "&nbsp;"  %></div></td>
   </tr>
<%

     totalesParciales[0] +=  sCampos[3].equalsIgnoreCase("D") ? Float.parseFloat(sCampos[6]) : 0;
	 totalesParciales[1] +=  sCampos[3].equalsIgnoreCase("H") ? Float.parseFloat(sCampos[6]) : 0;
     esPrimero = false;
   }
   
   if(!esPrimero){
      totalGeneral[0] += totalesParciales[0];
      totalGeneral[1] += totalesParciales[1];     
   %>

  <tr class="permiso-tres">
     <td colspan="4" >&nbsp;</td>
     <td width="13%" ><div align="right"><%= Common.getNumeroFormateado( ( totalesParciales[0]  ), 10, 2) %></div></td>
     <td width="12%" ><div align="right"><%= Common.getNumeroFormateado( ( totalesParciales[1]  ), 10, 2) %></div></td>
  </tr>
  
  <tr class="text-globales" height="3px">
     <td colspan="6"  height="3px"></td>
  </tr>  
  
  <tr class="fila-det-bold" > 
     <td colspan="4" bgcolor="#D5D5FF" >Totales Generales&nbsp;</td>
     <td width="13%" bgcolor="#D5D5FF"><div align="right"><%= Common.getNumeroFormateado( ( totalGeneral[0]  ), 10, 2) %></div></td>
     <td width="12%" bgcolor="#D5D5FF"><div align="right"><%= Common.getNumeroFormateado( ( totalGeneral[1]  ), 10, 2) %></div></td>
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

