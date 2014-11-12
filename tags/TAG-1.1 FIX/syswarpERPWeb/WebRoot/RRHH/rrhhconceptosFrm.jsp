<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: rrhhconceptos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Dec 14 17:06:42 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<% 
try{
Strings str = new Strings();
String viejaFormula ="";
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BRF"  class="ar.com.syswarp.web.ejb.BeanRrhhconceptosFrm"   scope="page"/>
<head>
 <title>FRMRrhhtitulo.jsp</title>
<link rel = "stylesheet" href = "<%= pathskin %>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="vs/overlib/overlib.js"></script>
<script>
function sumarFormulas(nuevaFormula)
{
	
	alert("nuevaFormula: " + nuevaFormula);
	//viejaFormula = document.frm.sql.value;
	//alert("viejaFormula: " + viejaFormula);
	//viejaFormula += viejaFormula + " " + nuevaFormula;

} 
</script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BRF" property="*" />
 <% 
 String titulo = BRF.getAccion().toUpperCase() + " DE Conceptos" ;
 BRF.setResponse(response);
 BRF.setRequest(request);
 BRF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BRF.setUsuarioact( session.getAttribute("usuario").toString() );
 BRF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BRF.ejecutarValidacion();
 %>
<form action="rrhhconceptosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BRF.getAccion()%>" >
<input name="idconcepto" type="hidden" value="<%=BRF.getIdconcepto()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BRF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Concepto: (*) </td>
                <td width="78%" class="fila-det-border"><input name="concepto" type="text" value="<%=BRF.getConcepto()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr> 
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Imprime en recibo: (*) </td>
                <td width="78%" class="fila-det-border"><select name="imprime" id="imprime"  >
                  <option value="S" <%= BRF.getImprime().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                  <option value="N" <%= BRF.getImprime().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                </select> </td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">&nbsp;Formula: (*) </td>
                <td width="78%" class="fila-det-border">
                	<textarea name="formula" cols="70" rows="6" class="campo"><%=BRF.getFormula()%></textarea>
                	<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_formula.jsp')" style="cursor:pointer">
                </td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Cuenta Contable</td>
                <td width="78%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="idctacont" type="text" class="campo" id="idctacont" value="<%=str.esNulo(BRF.getIdctacont())%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_cuentascontables.jsp')" style="cursor:pointer"></td>
                  </tr>
                </table></td>
              </tr>
			  
			  <tr class="fila-det">
                <td width="22%" class="fila-det-border">Tipo Concepto(*) 
		        <input name="idtipoconcepto" type="hidden" id="idtipoconcepto" value="<%=BRF.getIdtipoconcepto()%>"></td>          
				<td width="78%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="tipoconcepto" type="text" class="campo" id="tipoconcepto" value="<%=BRF.getTipoconcepto()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_tipoconcepto.jsp')" style="cursor:pointer"></td>
                  </tr>
                </table></td>
              </tr>
			  
			  
			  <tr class="fila-det">
                <td width="22%" class="fila-det-border">Tipo Cantidad Concepto(*) 
		        <input name="idtipocantidadconcepto" type="hidden" id="idtipocantidadconcepto" value="<%=BRF.getIdtipocantidadconcepto()%>"></td>          
				<td width="78%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="tipocantidadconcepto" type="text" class="campo" id="tipocantidadconcepto" value="<%=BRF.getTipocantidadconcepto()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_tipocantidadconcepto.jsp')" style="cursor:pointer"></td>
                  </tr>
                </table></td>
              </tr>
			  
			  <tr class="fila-det">
                <td width="22%" class="fila-det-border">Valor: (*) </td>
                <td width="78%" class="fila-det-border"><input name="valor" type="text" value="<%=BRF.getValor()%>" class="campo" size="8" maxlength="9"  ></td>
              </tr>
			  
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
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
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (" + pagina + ") : "+ex);   
}%>

