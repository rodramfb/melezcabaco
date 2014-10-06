<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacoObsequiosEsquema
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Nov 06 11:36:23 GMT-03:00 2009 
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
<jsp:useBean id="BBOEF"  class="ar.com.syswarp.web.ejb.BeanBacoObsequiosEsquemaFrm"   scope="page"/>
<head>
 <title>Esquemas Obsequios</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>   
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBOEF" property="*" />
 <% 
 String titulo = BBOEF.getAccion().toUpperCase() + " DE ESQUEMAS DE OBSEQUIOS" ;
 BBOEF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBOEF.setResponse(response);
 BBOEF.setRequest(request);
 BBOEF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBOEF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBOEF.ejecutarValidacion();
 %>
<form action="bacoObsequiosEsquemaFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBOEF.getAccion()%>" >
<input name="idesquema" type="hidden" value="<%=BBOEF.getIdesquema()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BBOEF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="17%" height="29" class="fila-det-border">&nbsp;A&ntilde;o: (*) </td>
                <td width="83%" class="fila-det-border"><select name="anio" class="campo" id="anio">
                    <option value="-1">Seleccionar</option>
                    <% 
                            for(int z = BBOEF.getAnioactual(); z<=BBOEF.getAnioactual()+1 ; z++){ %>
                    <option value="<%=  z %>" <%= BBOEF.getAnio().intValue() == z ? "selected" : "" %>><%=  z %></option>
                    <%
                            }  %>
                  </select></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" height="28" class="fila-det-border">&nbsp;Mes: (*) </td>
                <td width="83%" class="fila-det-border"><select name="idmes" class="campo" id="idmes">
                    <option value="-1">Seleccionar</option>
                    <%  
                     Iterator it = BBOEF.getMesesList().iterator();
                     while(it.hasNext()){
                       String[] meses= (String[])it.next(); %>
                    <option value="<%= meses[0] %>" <%=BBOEF.getIdmes().intValue() == Integer.parseInt(meses[0] )? "selected" : "" %>><%= meses[1] %></option>
                    <%}  %>
                  </select></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" height="29" class="fila-det-border">&nbsp;Tipo Obsequio : (*) </td>
                <td width="83%" class="fila-det-border"><select name="idtipoobsequio" class="campo" id="idtipoobsequio">
                    <option value="-1">Seleccionar</option>
                    <%  
																 it = BBOEF.getListTipoObsequios().iterator();
																 while(it.hasNext()){
																	 String[] tipo = (String[])it.next(); %>
                    <option value="<%= tipo[0] %>" <%= BBOEF.getIdtipoobsequio().intValue() == Integer.parseInt(tipo[0] )? "selected" : "" %>><%= tipo[1] %></option>
                    <%}  %>
                  </select></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" height="29" class="fila-det-border">&nbsp;Art&iacute;culo: (*) </td>
                <td width="83%" class="fila-det-border"><table width="50%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10%"><input name="codigo_st" type="text" value="<%=BBOEF.getCodigo_st()%>" class="campo" size="10" maxlength="15"  ></td>
                    <td width="43%"><input name="descrip_st" type="text" class="campo" id="descrip_st" value="<%=BBOEF.getDescrip_st()%>" size="45" maxlength="100"  ></td>
                    <td width="47%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_articulo.jsp', 'arti', 700, 400)" style="cursor:pointer"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" height="31" class="fila-det-border">&nbsp;Dep&oacute;sito: (*) 
                <input name="codigo_dt" type="hidden" value="<%=BBOEF.getCodigo_dt()%>" class="campo" size="100" maxlength="100"  ></td>
                <td width="83%" class="fila-det-border"><table width="50%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="32%"><input name="descrip_dt" type="text" class="campo" id="descrip_dt" value="<%=BBOEF.getDescrip_dt()%>" size="55" maxlength="100"  ></td>
                    <td width="68%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Stock/lov_deposito.jsp', 'deposito', 750, 500)" style="cursor:pointer"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Cantidad: (*) </td>
                <td class="fila-det-border"><input name="cantidad" type="text" value="<%=BBOEF.getCantidad()%>" class="campo" size="10" maxlength="10"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" height="25" class="fila-det-border">Carta/Regalo:(*)</td>
                <td width="83%" class="fila-det-border"><select name="cartaoregalo" id="cartaoregalo" class="campo">
                    <option value="">Seleccionar</option>
                    <option value="C" <%=BBOEF.getCartaoregalo().equalsIgnoreCase("C") ? "selected" : "" %> >Carta</option>
                    <option value="R" <%=BBOEF.getCartaoregalo().equalsIgnoreCase("R") ? "selected" : "" %> >Regalo</option>
                  </select></td>
              </tr>
              <tr class="fila-det">
                <td height="42" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

