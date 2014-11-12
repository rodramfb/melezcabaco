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
<jsp:useBean id="BCAF"  class="ar.com.syswarp.web.ejb.BeanLovContableAsietip1SetValues"   scope="page"/>
<head>
 <title>SELECCION DE ASIENTOS PREDEFINIDOS</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="../imagenes/default/erp-style.css"> 
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/forms/forms.js"></script>  
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
	<script>
		function setTipomov(tipomov, indice){
		  if(document.frm.tipomov.length  && 
			   document.frm.tipomov.length != null && 
				 document.frm.tipomov.length != 'undefined'){
			  document.frm.tipomov[indice].value = tipomov; 
			}
			else{
			  document.frm.tipomov.value = tipomov; 
			}
		}
	</script> 
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCAF" property="*" />
 <% 
 String titulo =  "SELECCION DE ASIENTOS PREDEFINIDOS" ;
 BCAF.setResponse(response);
 BCAF.setRequest(request);
 BCAF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCAF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCAF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BCAF.setEjercicio( new BigDecimal( (String) session.getAttribute("ejercicioActivo") ) ) ;	 
 BCAF.ejecutarValidacion();

 
 %>
<form action="lov_contableAsietip1SetValues.jsp" method="post" name="frm">
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
                <td width="15%" class="fila-det-border">&nbsp;</td>
                <td width="85%" class="fila-det-border"><jsp:getProperty name="BCAF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td height="29" colspan="2" class="fila-det-border">&nbsp;Libro Diario(*): <%=BCAF.getLeyenda()%>                  <input name="leyenda" type="hidden" value="<%=BCAF.getLeyenda()%>" class="campo" size="50" maxlength="50"  readonly></td>
              </tr>
              <tr class="text-dos-bold">
                <td height="21" colspan="2" >Detalle Asiento (*) </td>
              </tr>
              <tr class="text-globales">
                <td height="5px" colspan="2" class="fila-det-border"></td>
              </tr>
              <tr class="fila-det">
                <td height="36" colspan="2" class="fila-det-border">
								
						<% 
							Hashtable htPredefinidos = (Hashtable) session.getAttribute("htPredefinidos");
							if(htPredefinidos != null && !htPredefinidos.isEmpty()){
							 %>
						<table width="100%" border="1" cellspacing="0" cellpadding="0">
                  <tr class="fila-det-bold">
                    <td width="41%">Cuenta</td>
                    <td width="45%">Detalle Libro Mayor </td>
                    <td width="6%"><div align="right">Importe</div></td>
                    <td width="4%"><div align="center">Debe</div></td>
                    <td width="4%"><div align="center">Haber</div></td>
                  </tr>
							<%Enumeration en = Common.getSetSorted( htPredefinidos.keySet() ); 
								String color_fondo = "";
								int indice = 0; 		
								while (en.hasMoreElements()) {
									if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
									else color_fondo = "fila-det-verde"; 				
									String key = (String) en.nextElement();
									String [] detalle = (String []) htPredefinidos.get(key);%>									
									
		              <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>">
                    <td class="fila-det-border">&nbsp;<%= detalle[0] %> - <%= detalle[1] %>
                    <input name="keyHtPredefinidos" type="hidden" id="keyHtPredefinidos" value="<%= key%>"></td>
                    <td class="fila-det-border">&nbsp;<%= detalle[2] %>
                    <input name="tipomov" type="hidden" id="tipomov" value="<%= detalle[4] %>"></td> 
                    <td class="fila-det-border"><div align="right"> 
                      <input name="importe" type="text" class="campo" id="importe" size="8" maxlength="10" value="<%= detalle[3] %>"> 
                    </div></td>
                    <td class="fila-det-border"><div align="center">
                      <input name="dh<%= detalle[0] %>" type="radio" value="1" onClick="setTipomov(this.value, <%= indice %>)" <%= detalle[4].equals("1") ? "checked" : "" %> >
                    </div></td>
		                <td class="fila-det-border"><div align="center">
		                  <input name="dh<%= detalle[0] %>" type="radio" value="2"  onClick="setTipomov(this.value, <%= indice %>)" <%= detalle[4].equals("2") ? "checked" : "" %>>
		                  </div></td>
		              </tr> 
							<%
									indice++;										 
								}
								%>
                </table>
						<%			 
							}%></td>
              </tr>
              <tr class="fila-det">
                <td height="36" colspan="2" class="fila-det-border"><input name="validar" type="submit" id="validar" value="Confirmar" class="boton">
                <input name="volver" type="submit" id="volver" value="Volver" class="boton"></td>
              </tr>
          </table>
					
       </td>
     </tr>
  </table> 
<% 
 if(!BCAF.getValidar().equals("") && BCAF.getMensaje().equals("")){
 	out.write("<script>");
	out.write("\n\topener.document.frm.leyenda.value='"+ BCAF.getLeyenda() +"';\n");
 	out.write("\n\topener.document.frm.submit();\n");
 	out.write("\n\twindow.close();\n");
 	out.write("</script>");	
 }
%>
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

