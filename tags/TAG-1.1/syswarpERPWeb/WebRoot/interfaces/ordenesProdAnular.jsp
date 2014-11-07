<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: produccionEsquemas_Cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Feb 13 09:18:39 GMT-03:00 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="java.math.*" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
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
<jsp:useBean id="BPAO"  class="ar.com.syswarp.web.ejb.BeanINTERFACESProduccionAnulaOrden"   scope="page"/>
<head>
 <title></title>
 <meta http-equiv="description" content="mypage">
 <link rel = "stylesheet" href = "<%= pathskin %>">
 
 <script language="JavaScript" src="vs/forms/forms.js"></script>
<script >


</script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPAO" property="*" />
 <% 
 String titulo = "INTERFACES - ANULACION DE ORDEN DE PRODUCCION: " ;
 BPAO.setResponse(response);
 BPAO.setRequest(request);
 //BPAO.setSession(session);
 BPAO.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPAO.setUsuarioact( session.getAttribute("usuario").toString() );
 BPAO.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPAO.setEjercicioactivo( Integer.parseInt( session.getAttribute("ejercicioActivo").toString() ) );
 BPAO.setIdcontadorcomprobante( new BigDecimal( session.getAttribute("idcontadorremitos1").toString() ));
 BPAO.ejecutarValidacion(); 
 
 %>
<form action="ordenesProdAnular.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="" >


<table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %> <%= BPAO.getIdop() %></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BPAO" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">Esquema: (*) </td>
                <td class="fila-det-border"><%=BPAO.getEsquema()%>&nbsp;
                <input name="recursivo" type="hidden" id="recursivo" value="0" >
                <input name="idop" type="hidden" id="idop" value="<%=BPAO.getIdop()%>" ></td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">Producto / Articulo: (*)</td>
                <td class="fila-det-border"><%=BPAO.getDescrip_st()%>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Dep&oacute;sito:(*)</td>
                <td class="fila-det-border"><%=BPAO.getDescrip_dt()%>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Cantidad: (*) </td>
              <td class="fila-det-border"><%=BPAO.getCantidad()%>&nbsp;
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              
              <tr class="fila-det">
                <td class="fila-det-border">Motivo:(*)</td>
                <td class="fila-det-border">
								<select name="idmotivodesarma" class="campo">
								  <option value="-1">Seleccionar</option>
									<%
									  Iterator iterMotivos = BPAO.getStockMotivosDesarma().iterator();
									  while(iterMotivos.hasNext()){
										  String[] datos = (String[])iterMotivos.next(); 
											  %>
									<option value="<%= datos[0] %>" <%=  datos[0].equals(BPAO.getIdmotivodesarma().toString()) ? "selected" : ""%>><%= datos[1] %></option>
									<%}  %>
                </select>                </td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">Observaciones:  </td>
                <td class="fila-det-border"><textarea name="observaciones" cols="70" rows="6" class="campo"><%=BPAO.getObservaciones()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Confirmaci&oacute;n Anulaci&oacute;n </td>
                <td class="fila-det-border"><input name="visto" type="checkbox" id="visto" value="1" onClick="document.frm.validar.disabled = !this.checked"></td>
              </tr> 
              <tr class="fila-det">
                <td height="40" class="fila-det-border">&nbsp;</td> 
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Anular" class="boton" disabled onClick="document.frm.accion.value='desarmar'"> </td>
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


