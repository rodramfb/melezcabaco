<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: contableAsietip1
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Sep 15 14:10:45 GMT-03:00 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.util.*" %>
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
<jsp:useBean id="BCAF"  class="ar.com.syswarp.web.ejb.BeanContableAsietip1Frm"   scope="page"/>
<head>
 <title>FRMContableAsietip1</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="../imagenes/default/erp-style.css"> 
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/forms/forms.js"></script>  
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCAF" property="*" />
 <% 
 String titulo = BCAF.getAccion().toUpperCase() + " DE ASIENTOS PREDEFINIDOS" ;
 BCAF.setResponse(response);
 BCAF.setRequest(request);
 BCAF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCAF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCAF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BCAF.setEjercicio( new BigDecimal( (String) session.getAttribute("ejercicioActivo") ) ) ;	 
 BCAF.ejecutarValidacion();
 %>
<form action="contableAsietip1Frm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCAF.getAccion()%>" >
<input name="codigo" type="hidden" value="<%=BCAF.getCodigo()%>" >
   <input name="primeraCarga" type="hidden" class="campo" id="primeraCarga" value="false" size="15" maxlength="20" readonly>
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
                <td width="23%" class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border"><jsp:getProperty name="BCAF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td height="29" class="fila-det-border">&nbsp;Libro Diario(*): </td>
                <td colspan="2" class="fila-det-border"><input name="leyenda" type="text" value="<%=BCAF.getLeyenda()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="text-dos-bold">
                <td height="21" >Detalle Asiento (*) </td>
                <td height="21" colspan="2" >&nbsp;</td>
              </tr>
              <tr  class="fila-det">
                <td  class="fila-det-border">Cuenta(*):</td>
                <td width="51%"  class="fila-det-border"><input name="idcuenta" type="text" class="campo" id="idcuenta" value="<%=BCAF.getIdcuenta()%>" size="15" maxlength="20" readonly>
                <input name="cuenta" type="text" class="campo" value="<%=BCAF.getCuenta()%>" size="50" maxlength="50" readonly="yes" ></td>
                <td width="26%"  class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="abrirVentana('lov_ccontables_imputables.jsp', 'ccontables', 750, 400)" style="cursor:pointer"></td>
              </tr>
              <tr  class="fila-det">
                <td  class="fila-det-border">Detalle Libro Mayor(*): </td>
                <td colspan="2"  class="fila-det-border"><input name="detalle" type="text" class="campo"  id="detalle"  value="<%=BCAF.getDetalle()%>" size="80" maxlength="50"></td>
              </tr>
              <tr  class="fila-det">
                <td height="30"  class="fila-det-border"><input name="agregar" type="submit" class="boton" id="agregar" value="Agregar"></td>
                <td colspan="2"  class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="24%"><input name="validar" type="submit" value="Enviar" class="boton"></td>
                    <td width="76%"><input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="text-globales">
                <td height="5px" colspan="3" class="fila-det-border"></td>
              </tr>
              <tr class="fila-det">
                <td height="36" colspan="3" class="fila-det-border">
								
						<% 
							Hashtable htDetalle = (Hashtable) session.getAttribute("htDetalle");
							if(htDetalle != null && !htDetalle.isEmpty()){
							 %> 	 
					
								<table width="100%" border="1" cellspacing="0" cellpadding="0">
                  <tr class="fila-det-bold">
                    <td height="30" colspan="3"><input name="eliminar" type="submit" class="boton" id="eliminar" value="Eliminar"></td>
                  </tr>
                  <tr class="fila-det-bold">
                    <td width="2%">&nbsp;</td>
                    <td width="45%">Cuenta</td>
                    <td width="53%">Detalle Libro Mayor </td>
                  </tr>
							<%Enumeration en = Common.getSetSorted( htDetalle.keySet() ); 
								String color_fondo = ""; 		
								while (en.hasMoreElements()) {
									if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
									else color_fondo = "fila-det-verde"; 				
									String key = (String) en.nextElement();
									String [] detalle = (String []) htDetalle.get(key);%>									
									
		              <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>">
                    <td class="fila-det-border"><input name="delKey" type="checkbox" id="delKey" value="<%=key%>"></td>
                    <td class="fila-det-border">&nbsp;<%= detalle[0] %> - <%= detalle[1] %><input name="keyHtDetalle" type="hidden" class="campo" id="keyHtDetalle" value="<%=key%>" size="15" maxlength="20" readonly></td>
                    <td class="fila-det-border">&nbsp;<%= detalle[2] %></td>
                  </tr>
							<%			 
								}
								%>
                </table>
						<%			 
							}%>		          </td>
              </tr>
          </table>
					
       </td>
     </tr>
  </table> 
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

