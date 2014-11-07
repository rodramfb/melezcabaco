<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: crmProductos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Aug 03 11:44:52 ART 2007 
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
<jsp:useBean id="BCPF"  class="ar.com.syswarp.web.ejb.BeanCrmProductosFrm"   scope="page"/>
<head>
 <title>FRMCrmProductos.jsp</title>
 <meta http-equiv="description" content="mypage">
<link href="<%=pathskin%>" rel="stylesheet" type="text/css">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCPF" property="*" />
 <% 
 String titulo = BCPF.getAccion().toUpperCase() + " DE PRODUCTOS" ;
 BCPF.setResponse(response);
 BCPF.setRequest(request);
 BCPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCPF.setIdempresa(new BigDecimal( session.getAttribute("empresa").toString() ));   
 BCPF.ejecutarValidacion();
 Enumeration enumera ;
 String clave = "";
 String selected = "";
 %>
<form action="crmProductosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCPF.getAccion()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BCPF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Lote(*)</td>
                <td class="fila-det-border">&nbsp;<input name="nrolote" type="text" value="<%=BCPF.getNrolote()%>" class="campo" size="20" maxlength="18" ></td>
              </tr>
              <tr class="fila-det">
                <td width="21%" class="fila-det-border">Tipo Cotizaci&oacute;n: (*) </td>
                <td width="79%" class="fila-det-border">&nbsp;<select name="idfamiliacotizacion" class="campo">
				  <option value="-1">Seleccionar</option>
				  <% 
				   enumera = Common.getSetSorted (BCPF.getHtFamiliaCotizacion().keySet());
				   while(enumera != null && enumera.hasMoreElements()){
				     clave = (String) enumera.nextElement();	
					 if(BCPF.getIdfamiliacotizacion().toString().equals(clave)) selected = "selected";
					 else  selected = "";
				   %>
				  <option value="<%=clave%>" <%=selected%> ><%=BCPF.getHtFamiliaCotizacion().get(clave)%></option>
				  <% 
				   } %>
				</select>                </td>
              </tr>
              <tr class="fila-det">
                <td width="21%" class="fila-det-border">Grupo : (*) </td>
                <td width="79%" class="fila-det-border">&nbsp;<select name="idgrupoproducto" class="campo">
				  <option value="-1">Seleccionar</option>
				  <% 
				   enumera = Common.getSetSorted (BCPF.getHtGrupoProducto().keySet());
				   while(enumera != null && enumera.hasMoreElements()){
				     clave = (String) enumera.nextElement();	
					 if(BCPF.getIdgrupoproducto().toString().equals(clave)) selected = "selected";
					 else  selected = "";
				   %>
				  <option value="<%=clave%>" <%=selected%> ><%=BCPF.getHtGrupoProducto().get(clave)%></option>
				  <% 
				   } %>				  
				</select>                </td>
              </tr>
              <tr class="fila-det">
                <td width="21%" class="fila-det-border">Status:  </td>
                <td width="79%" class="fila-det-border">&nbsp;<select name="idproductostatus" class="campo">
				  <option value="-1">Seleccionar</option>
				  <% 
				   enumera = Common.getSetSorted (BCPF.getHtProductoStatus().keySet());
				   while(enumera != null && enumera.hasMoreElements()){
				     clave = (String) enumera.nextElement();	
					 if(BCPF.getIdproductostatus().toString().equals(clave)) selected = "selected";
					 else  selected = "";
				   %>
				  <option value="<%=clave%>" <%=selected%> ><%=BCPF.getHtProductoStatus().get(clave)%></option>
				  <% 
				   } %>					  
				</select></td>
              </tr>
              <tr class="fila-det">
                <td width="21%" class="fila-det-border">Calificaci&oacute;n: (*) </td>
                <td width="79%" class="fila-det-border">&nbsp;<select name="calificacion" class="campo">
				  <option value="">Seleccionar</option>
  				  <option value="A" <%=BCPF.getCalificacion().equalsIgnoreCase("A") ? "selected": ""%>>A</option>
				  <option value="B" <%=BCPF.getCalificacion().equalsIgnoreCase("B") ? "selected": ""%>>B</option>
                </select>                </td>
              </tr>
              <tr class="fila-det">
                <td width="21%" class="fila-det-border">Superficie: (*) </td>
                <td width="79%" class="fila-det-border">&nbsp;<input name="superficie" type="text" value="<%=BCPF.getSuperficie()%>" class="campo" size="20" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="21%" class="fila-det-border">Precio x mts: (*) </td>
                <td width="79%" class="fila-det-border">&nbsp;<input name="precioxmts" type="text" value="<%=BCPF.getPrecioxmts()%>" class="campo" size="20" maxlength="18"  readonly>
                Precio Actual Para el Tipo de Cotizaci&oacute;n Seleccionado : <%= BCPF.getValorunidadsup() %></td>
              </tr>
              <tr class="fila-det">
                <td width="21%" class="fila-det-border">Precio: (*) </td>
                <td width="79%" class="fila-det-border">&nbsp;<input name="precio" type="text" value="<%=BCPF.getPrecio()%>" class="campo" size="20" maxlength="18"  readonly></td>
              </tr>
              <tr class="fila-det">
                <td width="21%" class="fila-det-border">Valor Contado: (*) </td>
                <td width="79%" class="fila-det-border">&nbsp;<input name="valorcontado" type="text" value="<%=BCPF.getValorcontado()%>" class="campo" size="20" maxlength="18" readonly ></td>
              </tr>
              <tr class="fila-det">
                <td width="21%" class="fila-det-border">Boleto: (*) </td>
                <td width="79%" class="fila-det-border">&nbsp;<input name="boleto" type="text" value="<%=BCPF.getBoleto()%>" class="campo" size="20" maxlength="18"  readonly></td>
              </tr>
              <tr class="fila-det">
                <td width="21%" class="fila-det-border">Cuotas x 36: (*) </td>
                <td width="79%" class="fila-det-border">&nbsp;<input name="cuotasx36" type="text" value="<%=BCPF.getCuotasx36()%>" class="campo" size="20" maxlength="18"  readonly></td>
              </tr>
              <tr class="fila-det">
                <td height="36" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"> &nbsp;<input name="calcular" type="submit" class="boton" id="calcular" value="Calcular Importes"></td>
              </tr>
              <tr class="fila-det">
                <td height="36" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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
  System.out.println("ERROR (" + pagina + ") : "+ex);   
}%>

