<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacoErEsquema
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Mar 25 14:12:47 GYT 2009 
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
<jsp:useBean id="BBEEF"  class="ar.com.syswarp.web.ejb.BeanBacoErEsquemaFrm"   scope="page"/>
<head>
 <title>ESQUEMAS DE ENTREGAS REGULARES</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script>
   function getLista(){
     document.getElementById('codigo_st').value = '';
     document.getElementById('descrip_st').value = '';
     abrirVentana('lov_lista.jsp','lista',750, 350);
   }
 </script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBEEF" property="*" />
 <% 
 String titulo = BBEEF.getAccion().toUpperCase() + " DE ESQUEMAS DE ENTREGAS REGULARES " ;
 BBEEF.setResponse(response);
 BBEEF.setRequest(request);
 BBEEF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBEEF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBEEF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBEEF.ejecutarValidacion();
 Iterator iter ;
 %>
<form action="bacoErEsquemaFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBEEF.getAccion()%>" >
<input name="idesquema" type="hidden" value="<%=BBEEF.getIdesquema()%>" >
   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
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
                <td colspan="2" class="fila-det-border"><jsp:getProperty name="BBEEF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">A&ntilde;o: (*) </td>
                <td colspan="2" class="fila-det-border">
                  <select name="anio" id="anio" class="campo">
                    <%
                    for(int ano=BBEEF.getAnioactual()-3;ano<=BBEEF.getAnioactual()+3;ano++){%>
                    <option value="<%= ano %>" <%= BBEEF.getAnio().intValue() == ano ? "selected": ""  %> ><%= ano %></option>
                    <%
                    }  %>
                  </select></td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">Mes: (*) </td>
                <td colspan="2" class="fila-det-border"><select name="idmes" id="idmes" class="campo"  >
                  <option value="-1">Seleccionar</option>
                  <% 
									  iter = BBEEF.getListMeses().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                  <option value="<%= datos[0] %>" <%= datos[0].equals(  BBEEF.getIdmes().toString() ) ? "selected" : "" %> ><%= datos[1] %></option>
                  <%  
									  }%>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">Preferencia: (*) </td>
                <td colspan="2" class="fila-det-border"><select name="idpreferencia" id="idpreferencia" class="campo"  >
                    <option value="-1">Seleccionar</option>
                    <% 
									  iter = BBEEF.getListPreferencia().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                    <option value="<%= datos[0] %>" <%= datos[0].equals(  BBEEF.getIdpreferencia().toString() ) ? "selected" : "" %> ><%= datos[1] %></option>
                    <%  
									  }%>
                  </select></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Lista: (*):                
                <input name="idlista" type="hidden" value="<%=BBEEF.getIdlista()%>" class="campo" size="5" maxlength="6" id="idlista"></td>
                <td width="44%" class="fila-det-border"><input name="lista" type="text" class="campo" id="lista" value="<%=BBEEF.getLista()%>" size="60" maxlength="100" readonly></td>
                <td width="37%" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/find.png" width="22" height="22" onClick="getLista();" style="cursor:pointer"></td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">Art&iacute;culo: (*) </td>
                <td class="fila-det-border"><input name="codigo_st" type="text" class="campo" id="codigo_st" value="<%=BBEEF.getCodigo_st()%>" size="5" maxlength="5"  readonly>
                <input name="descrip_st" type="text" class="campo" id="descrip_st" value="<%=BBEEF.getDescrip_st()%>" size="50" maxlength="100"  readonly></td>
                <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/find.png" width="22" height="22" onClick="abrirVentana('lovArticulosListaPrecios.jsp?idlista=' + document.getElementById('idlista').value + '&lista=' + document.getElementById('lista').value ,'listaarticulos',800, 350);" style="cursor:pointer"></td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">Cantidad: (*) </td>
                <td colspan="2" class="fila-det-border"><input name="cantidad" type="text" value="<%=BBEEF.getCantidad()%>" class="campo" size="8" maxlength="4"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">Dep&oacute;sito: (*) 
                <input name="codigo_dt" type="hidden" class="campo" id="codigo_dt" value="<%=BBEEF.getCodigo_dt()%>" size="100" maxlength="100"  ></td>
                <td class="fila-det-border"><input name="descrip_dt" type="text" class="campo" id="descrip_dt" value="<%=BBEEF.getDescrip_dt()%>" size="60" maxlength="100" readonly ></td>
                <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/find.png" width="22" height="22" onClick="abrirVentana('../Stock/lov_deposito.jsp','lista',750, 350);" style="cursor:pointer"></td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">Descuento:  </td>
                <td colspan="2" class="fila-det-border"><select name="iddescuento" id="iddescuento" class="campo" >
                  <option value="0" >0.00</option>
                  <% 
									  iter = BBEEF.getListPorcDescuento().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                                        <option value="<%= datos[0] %>" <%= datos[0].equals(  BBEEF.getIddescuento().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                  <%  
									  }%>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">Motivo descuento:  </td>
                <td colspan="2" class="fila-det-border"><select name="idmotivodescuento" id="idmotivodescuento" class="campo"  >
                    <option value="-1">Seleccionar</option>
                    <% 
									  iter = BBEEF.getListMotivoDescuento().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                    <option value="<%= datos[0] %>" <%= datos[0].equals(  BBEEF.getIdmotivodescuento().toString() ) ? "selected" : "" %> ><%= datos[1] %></option>
                    <%  
									  }%>
                  </select></td>
              </tr>
              <tr class="fila-det">
                <td height="30" class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

