<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vCajaSubcontabilidad
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jul 07 08:34:18 GYT 2009 
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

// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVCajaSubcontabilidad   = null;
int totCol = 8; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCSA"  class="ar.com.syswarp.web.ejb.BeanSubcontabilidadGetSetPasaje"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCSA" property="*" />
<%
 BVCSA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BVCSA.setResponse(response);
 BVCSA.setRequest(request);
 BVCSA.ejecutarValidacion();

 /*
  FECHA: 20090729 
  Autor: EJV
  Comentario: Esta lógica debería estar en el BEAN.
              Se aplico para no realizar un pasaje de la app.
  @TODO: Aplicar en BeanSubcontabilidadGetSetPasaje
 */
 if( Common.isFormatoFecha( BVCSA.getFechaUltimoPasajeContable()  ) && 
     Common.isFechaValida( BVCSA.getFechaUltimoPasajeContable()  ) 
   ){
   System.out.println("Testeando fechas ahora con validacion de formato ... ");
   Calendar cal = new GregorianCalendar();
   java.util.Date fecha = (java.util.Date)( Common.setObjectToStrOrTime(BVCSA.getFechaUltimoPasajeContable(), "StrToJUDate") );  
   cal.setTime(fecha);
   cal.add(Calendar.DATE, 1);
   BVCSA.setFdesde(  Common.setObjectToStrOrTime( cal.getTime(), "JUDateToStr").toString() );
 }
  
 String titulo = "CONSULTA - PASAJE A LA SUBCONTABILIDAD" +  ( !BVCSA.getModulo().equals("") ? ( ": " + BVCSA.getModulo().toUpperCase() )  : "" );
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" type="text/css" href="vs/calendar/calendar.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "fechamov";
tituCol[1] = "cartera";
tituCol[2] = "Comprobante";
tituCol[3] = "tipomov";
tituCol[4] = "Cuenta";
tituCol[5] = "Cuenta";
tituCol[6] = "importe";
tituCol[7] = "cencosto";
java.util.List VCajaSubcontabilidad = new java.util.ArrayList();
VCajaSubcontabilidad= BVCSA.getVCajaSubcontabilidadList();
iterVCajaSubcontabilidad = VCajaSubcontabilidad.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>
<form action="subcontabilidadGetSetPasaje.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="35"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td  class="text-globales"><hr color="#FFFFFF"></td>
                </tr>
                <tr>
                  <td width="97%" height="38">
                     <table width="100%" border="0">
                        <tr>
                          <td width="20%" height="26" class="text-globales">Tipo Consulta - Pasaje </td>
                          <td width="33%">
                           <select name="entidad" class="campo" onChange="document.frm.submit();">
                             <option value="" >Seleccionar</option>
                             <option value="vcajasubcontabilidad" <%= BVCSA.getEntidad().equalsIgnoreCase("vcajasubcontabilidad") ? "selected" : "" %>>Caja</option>
                             <option value="vproveedoressubcontabilidad" <%= BVCSA.getEntidad().equalsIgnoreCase("vproveedoressubcontabilidad") ? "selected" : "" %>>Compras</option>
                             <option value="vstocksubcontabilidad" <%= BVCSA.getEntidad().equalsIgnoreCase("vstocksubcontabilidad") ? "selected" : "" %>>Stock</option>
                             <option value="vclientessubcontabilidad" <%= BVCSA.getEntidad().equalsIgnoreCase("vclientessubcontabilidad") ? "selected" : "" %>>Ventas</option>
                           </select></td>
                          <td width="47%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="35%" height="19">Fecha Desde </td>
                                         <td width="33%"><span class="fila-det-border">
                                           <input class="campo" onFocus="this.blur()" size="12" readonly type="text" name="fdesde" value="<%= BVCSA.getFdesde() %>" maxlength="12">
                                         <!--a class="so-BtnLink" href="javascript:calClick();return false;" 
					 onmouseover="calSwapImg('BTN_date_0', 'img_Date_OVER',true);" 
					 onmouseout="calSwapImg('BTN_date_0', 'img_Date_UP',true);" 
					 onclick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','fdesde','BTN_date_0');return false;"> <img align="absmiddle" border="0" name="BTN_date_0" src="vs/calendar/btn_date_up.gif" width="22" height="17"> </a--></span></td>
                                         <td width="32%"><input name="consulta" type="submit" class="boton" id="consulta" value="Ejecutar Consulta " onClick="document.frm.accion.value = this.name " style="cursor:pointer"></td>
                                      </tr>
                                   </table>                                 </td>
                              </tr>
                          </table>                        </td>
                       </tr>
                        <tr>
                          <td height="26" colspan="2" class="text-globales">Total de registros:&nbsp;<%=BVCSA.getTotalRegistros() + "" %> </td>
                          <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr class="text-globales">
                              <td width="35%" height="19">Fecha Hasta </td>
                              <td width="33%"><span class="fila-det-border">
                                <input class="campo" onFocus="this.blur()" size="12" readonly type="text" name="fhasta" value="<%= BVCSA.getFhasta() %>" maxlength="12">
                              <a class="so-BtnLink" href="javascript:calClick();return false;"  
					 onmouseover="calSwapImg('BTN_date_1', 'img_Date_OVER',true);" 
					 onmouseout="calSwapImg('BTN_date_1', 'img_Date_UP',true);" 
					 onclick="calSwapImg('BTN_date_1', 'img_Date_DOWN');showCalendar('frm','fhasta','BTN_date_1');return false;"> <img align="absmiddle" border="0" name="BTN_date_1" src="vs/calendar/btn_date_up.gif" width="22" height="17"> </a></span></td>
                              <td width="32%"><input name="pasaje" type="submit" class="boton" id="pasaje" value="Ejecutar Pasaje    " onClick="document.frm.accion.value = this.name" <%=  BVCSA.getAccion().equals("") || !BVCSA.getMensaje().equals("") ? "disabled" : "style=\"cursor:pointer\"" %>></td>
                            </tr>
                           </table></td>
                        </tr>
                        <tr>
                          <td height="26" colspan="2" class="text-globales">Fecha de Ultimo Pasaje: <%= BVCSA.getFechaUltimoPasajeContable() %></td>
                          <td>&nbsp;</td>
                        </tr>
                   </table>                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCSA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="30%" ><%=tituCol[4]%></td>
     <td width="25%" >Debe</td>
     <td width="25%" >Haber</td>
   </tr>
   <%int r = 0;
   String comprobante = "";
   BigDecimal parcialDebe =  new BigDecimal(0.00);
   BigDecimal parcialHaber =  new BigDecimal(0.00);
   BigDecimal totalDebe =  new BigDecimal(0.00);
   BigDecimal totalHaber =  new BigDecimal(0.00);
   while(iterVCajaSubcontabilidad.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVCajaSubcontabilidad.next(); 
      // estos campos hay que setearlos segun la grilla 
      if(!comprobante.equals(sCampos[2])){
				if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
				else color_fondo = "fila-det-verde";
        if(!comprobante.equals("")){
      %>
				<tr class="permiso-dos"  > 
					<td class="fila-det-border" ><div align="right">Totales Asiento &nbsp;</div></td>
					<td class="fila-det-border" ><div align="right"><%=parcialDebe%>&nbsp;</div></td>
					<td class="fila-det-border" ><div align="right"><%=parcialHaber%>&nbsp;</div></td>
				</tr>
			<%
        }%>
			<tr class="permiso-cuatro"  height="3">
				 <td colspan="3" height="3"></td>
			</tr>
			<tr class="fila-det-bold">
				 <td colspan="3" class="fila-det-border"  >Fecha: <%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[0]), "JSTsToStr")%> - Cartera: <%=sCampos[1]%> - Detalle: <%=sCampos[9]%>&nbsp;</td>
	    </tr>
			<tr class="permiso-cuatro"  height="2">
				 <td colspan="3" height="3"></td>
			</tr>
     <%
        comprobante=sCampos[2];
				parcialDebe =  new BigDecimal(0.00);
				parcialHaber = new BigDecimal(0.00); 
      }
%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><%=sCampos[4]%> - <%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[3].equalsIgnoreCase("D") ? sCampos[6] : ""%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[3].equalsIgnoreCase("H") ? sCampos[6] : ""%>&nbsp;</div></td>
   </tr>
<%
     parcialDebe = sCampos[3].equalsIgnoreCase("D") ?  parcialDebe.add(new BigDecimal(sCampos[6])) : parcialDebe ;
     parcialHaber = sCampos[3].equalsIgnoreCase("H") ?  parcialHaber.add(new BigDecimal(sCampos[6])) : parcialHaber ;  
     totalDebe = sCampos[3].equalsIgnoreCase("D") ?  totalDebe.add(new BigDecimal(sCampos[6])) : totalDebe ;
     totalHaber = sCampos[3].equalsIgnoreCase("H") ?  totalHaber.add(new BigDecimal(sCampos[6])) : totalHaber ; 
   
   }
   if(!comprobante.equals("")){%>
		<tr class="permiso-dos"  > 
			<td class="fila-det-border" ><div align="right">Totales Asiento &nbsp;</div></td>
			<td class="fila-det-border" ><div align="right"><%=parcialDebe%>&nbsp;</div></td>
			<td class="fila-det-border" ><div align="right"><%=parcialHaber%>&nbsp;</div></td>
		</tr>

			<tr class="permiso-uno" height="3">
				 <td height="3" colspan="3" ></td>
			</tr>

		<tr class="permiso-dos"  > 
			<td class="fila-det-border" ><div align="right">Totales Generales &nbsp;</div></td>
			<td class="fila-det-border" ><div align="right">&nbsp;</div></td>
			<td class="fila-det-border" ><div align="right">&nbsp;</div></td>
		</tr>
		<tr class="permiso-dos"  > 
			<td class="fila-det-border" ><div align="right">Total Débito &nbsp;</div></td>
			<td class="fila-det-border" ><div align="right"><%=totalDebe%>&nbsp;</div></td>
			<td class="fila-det-border" ><div align="right">&nbsp;</div></td>
		</tr>
		<tr class="permiso-dos"  > 
			<td class="fila-det-border" ><div align="right">Totales Crédito &nbsp;</div></td>
			<td class="fila-det-border" ><div align="right">&nbsp;</div></td>
			<td class="fila-det-border" ><div align="right"><%=totalHaber%>&nbsp;</div></td>
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

