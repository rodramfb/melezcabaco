<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: stockStockBis
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Aug 28 17:41:57 GMT-03:00 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias
*/ 
%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.math.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<% 
try{
Strings str = new Strings();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BSSBF"  class="ar.com.syswarp.web.ejb.BeanIframeGetStockDeposito"  scope="page"/>
<head>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BSSBF" property="*" />
 <% 
 
 BSSBF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BSSBF.setResponse(response);
 BSSBF.setRequest(request);
 BSSBF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BSSBF.setUsuarioact( session.getAttribute("usuario").toString() );
 BSSBF.ejecutarValidacion();
 %>
<form action="iframeGetStockDeposito.jsp" method="post" name="frmIframeStock" id="frmIframeStock">
<script>
	function setearExistencias(){
	  var accion = '<%= BSSBF.getAccion() %>';
		var indice = <%= BSSBF.getIndice() %>;
		if ( accion == 'setexistencias'){
			if(!parent.document.forms[0].existencia.length){
				parent.document.forms[0].existencia.value = <%=BSSBF.getCanti_sb()%>;
				parent.document.forms[0].reserva.value = <%=BSSBF.getPedid_sb()%>;
			}
			else{
				parent.document.forms[0].existencia[indice].value = <%=BSSBF.getCanti_sb()%>;
				parent.document.forms[0].reserva[indice].value = <%=BSSBF.getPedid_sb()%>;
			}
		}		 
	}
	
	setearExistencias();
</script>

<input name="accion" type="hidden" value="<%//=BSSBF.getAccion()%>" >
<input name="articu_sb" type="hidden" value="<%=BSSBF.getArticu_sb()%>" >
<input name="deposi_sb" type="hidden" value="<%=BSSBF.getDeposi_sb()%>" >
<input name="indice" type="hidden" value="<%=BSSBF.getIndice()%>" >
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





 

