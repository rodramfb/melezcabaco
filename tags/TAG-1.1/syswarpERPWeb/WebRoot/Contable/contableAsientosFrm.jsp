<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: contableLeyendas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Sep 10 11:42:24 GMT-03:00 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.util.*" %> 
<%@ page import="java.sql.*" %> 
<%@ page import="java.math.*" %> 
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCLF"  class="ar.com.syswarp.web.ejb.BeanContableAsientosFrm"   scope="page"/>
<head>
 <title></title>
 <link rel="stylesheet" type="text/css" href="vs/calendar/calendar.css">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/forms/forms.js"></script> 
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script> 
 <script>
   function envio(){
     disabledBtnEnviar(true);
     document.frm.validar.value = 'validar';
     document.frm.submit();
   }

   function disabledBtnEnviar(habilitado){
     document.frm.enviar.disabled = habilitado;
   }

   function enabledBtnEnviar(time){
     disabledBtnEnviar(time, true);
     setTimeout("disabledBtnEnviar(false);", time);
   }

   /*
   window.onload = function(){
     enabledBtnEnviar(6000);
   }
   */
 </script>
 
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCLF" property="*" />
 <% 
 String titulo = BCLF.getAccion().toUpperCase() + " DE ASIENTOS " ;
 BCLF.setResponse(response);
 BCLF.setRequest(request);
 BCLF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCLF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCLF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ) );
 BCLF.setEjercicio( new BigDecimal( (String) session.getAttribute("ejercicioActivo") ) ) ;	
 BCLF.setFechaEjercicioActivoDesde((Timestamp) session.getAttribute("fechaEjercicioActivoDesde"));
 BCLF.setFechaEjercicioActivoHasta ((Timestamp) session.getAttribute("fechaEjercicioActivoHasta")); 
  	 
 BCLF.ejecutarValidacion();
 %>
<form action="contableAsientosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCLF.getAccion()%>" >
<input name="validar" type="hidden" value="" >
<input name="idasiento" type="hidden" value="<%=BCLF.getIdasiento()%>" >
<input name="primeraCarga" type="hidden" id="primeraCarga" value="false" >
   <table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="3" class="fila-det-border"><jsp:getProperty name="BCLF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Fecha(*)</td>
                <td colspan="3" class="fila-det-border"><input  class="campo" onFocus="this.blur()" size="12" readonly type="text" name="fecha" value="<%=BCLF.getFecha()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;" 
										 onmouseover="calSwapImg('BTN_date_0', 'img_Date_OVER',true);" 
										 onmouseout="calSwapImg('BTN_date_0', 'img_Date_UP',true);" 
										 onclick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','fecha','BTN_date_0');return false;"> <img align="absmiddle" border="0" name="BTN_date_0" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
              </tr>
              <tr class="fila-det">
                <td width="25%" class="fila-det-border">Detalle Libro Diario (*) </td>
                <td colspan="3" class="fila-det-border"><input name="leyenda" type="text" class="campo" value="<%=BCLF.getLeyenda()%>" size="80" maxlength="100"></td>
              </tr>
              <tr class="text-globales">
                <td class="fila-det-border" height="5px" ></td>
                <td colspan="3" class="fila-det-border"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Cuenta(*):</td>
                <td width="17%" class="fila-det-border"><input name="idcuenta" type="text" class="campo" id="idcuenta" value="<%=BCLF.getIdcuenta()%>" size="25" maxlength="20" readonly></td>
                <td width="35%" class="fila-det-border"><input name="cuenta" type="text" class="campo" value="<%=BCLF.getCuenta()%>" size="50" maxlength="50" readonly="yes" ></td>
                <td width="23%" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="22" height="22" onClick="abrirVentana('lov_ccontables_imputables.jsp', 'ccontables', 750, 400)" style="cursor:pointer"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Detalle Libro Mayor (*): </td>
                <td colspan="3" class="fila-det-border"><input name="detalle" type="text" class="campo"  id="detalle"  value="<%=BCLF.getDetalle()%>" size="80" maxlength="50"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Importe(*):</td>
                <td colspan="3" class="fila-det-border"><input name="importe" type="text" class="campo"  value="<%=BCLF.getImporte()%>" size="25" maxlength="18" ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Tipo Registro(*):</td>
                <td colspan="3" class="fila-det-border"><label></label>
                  <table width="20%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="50%" class="fila-det"><div align="center">Debe</div></td>
                      <td width="50%" class="fila-det"><div align="center">Haber</div></td>
                    </tr>
                    <tr>
                      <td><div align="center">
                        <input name="tipomov" type="radio" value="1" class="campo" <%= BCLF.getTipomov().intValue() == 1 ?  "checked" : "" %>>
                      </div></td>
                      <td><div align="center">
                        <input name="tipomov" type="radio" value="2" class="campo" <%= BCLF.getTipomov().intValue() == 2 ?  "checked" : "" %>>
                      </div></td>
                    </tr>
                  </table></td>
              </tr>
              <tr class="text-globales">
                <td class="fila-det-border" height="5px" ></td>
                <td colspan="3" class="fila-det-border"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="3" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td colspan="4" class="fila-det-border"><input name="agregar" type="submit" class="boton" id="agregar" value="Ingresar Registro">
                  <input name="asientotipo" type="button" class="boton" id="asientotipo" value="Asiento Predefinido" onClick="abrirVentana('lov_contableAsietip1.jsp', 'predefinidos', 800, 350);"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="3" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="text-globales">
                <td class="fila-det-border" height="5px" ></td>
                <td colspan="3" class="fila-det-border"></td>
              </tr>							
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="3" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td colspan="4" class="fila-det-border">
                <input name="enviar" type="submit" value="Confirmar Asiento" class="boton" onClick="envio();">
                <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
              </tr>
          </table>
       </td>
     </tr>
  </table>
	 <% 
		Hashtable htAsientos = (Hashtable) session.getAttribute("htAsientos");
		if(htAsientos != null && !htAsientos.isEmpty()){
	   %> 	 
		 &nbsp;
<table width="95%"  border="1" align="center" cellpadding="0" cellspacing="0">
  <tr  class="text-globales" height="3">
    <td colspan="5"></td>
  </tr>
  <tr  class="fila-det-bold">
    <td colspan="5"><input name="eliminar" type="submit" class="boton" id="eliminar" value="Eliminar" onClick="eliminarRegistro();"></td>
    </tr>
  <tr  class="text-dos-bold">
    <td width="6%">&nbsp;Sel.</td>
    <td width="32%">&nbsp;Cuenta</td>
    <td width="41%">Detalle Libro Mayor </td>
    <td width="11%"><div align="right">Debe</div></td>
    <td width="10%"><div align="right">Haber</div></td>
    </tr>
  <tr  class="text-globales" height="3">
    <td colspan="5"></td>
  </tr>
  
		<%Enumeration en = Common.getSetSorted( htAsientos.keySet() ); 
			String color_fondo = ""; 		
			while (en.hasMoreElements()) {
				if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
				else color_fondo = "fila-det-verde"; 				
				String key = (String) en.nextElement();
				String [] articulos = (String []) htAsientos.get(key);%>
		<tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>">
			<td class="fila-det-border"><div align="center">
			  <input name="delKey" type="checkbox" id="delKey" value="<%= key %>" class="campo" />
		  </div></td>
			<td class="fila-det-border">&nbsp;<%= articulos[0] %>-<%= articulos[1] %>
		  <input  name="keyHashDatosArticulo" type="hidden" value="<%= key %>" /></td>
			<td class="fila-det-border">&nbsp;<%= articulos[2] %></td>
			<td class="fila-det-border"><div align="right">&nbsp;<%= !articulos[5].equals("0")  ? articulos[5] : "" %></div></td>
			<td class="fila-det-border"><div align="right">&nbsp;<%= !articulos[6].equals("0")  ? articulos[6] : "" %></div></td>
		</tr>
		<%			 
			}
			%>
		<tr  class="text-globales" height="3">
			<td colspan="5"></td>
		</tr>
		<tr class="fila-det">
		  <td colspan="2" rowspan="2" class="fila-det-border">&nbsp;</td>
		  <td class="text-dos"><div align="right">Totales:</div></td>
		  <td class="text-dos"><div align="right">&nbsp;<%=BCLF.getTotaldebe()%></div></td>
		  <td class="text-dos"><div align="right">&nbsp;-<%=BCLF.getTotalhaber()%></div></td>
	  </tr>
		<tr class="fila-det">
		  <td class="text-dos"><div align="right">Balance:</div></td>
		  <td class="text-dos"><div align="right">&nbsp;<%=BCLF.getTotaldebe().subtract(BCLF.getTotalhaber())%></div></td>
		  <td class="text-dos"><div align="right">&nbsp;</div></td>
	  </tr>
  <tr  class="text-globales" height="3">
    <td colspan="5"></td>
  </tr>
</table>
	<%			 
		}%>
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

