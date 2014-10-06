<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: vproveedoOcEstado
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Apr 20 15:27:29 ART 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ page import="java.math.*"%> 
<%@ page import="java.util.*" %>
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
<jsp:useBean id="BVOEF"  class="ar.com.syswarp.web.ejb.BeanVproveedoOcEstadoFrm"   scope="page"/>
<head>
 <title>FRMVproveedoOcEstado.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">

 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script>
 function cambiarOC(id_oc_cabe){
   if(confirm("Confirma la selección de la OC nro.: " + id_oc_cabe + "?")){
     document.frm.id_oc_cabe.value = id_oc_cabe;
	 document.frm.submit();
   }
 }
 </script>
 <style type="text/css">
<!--
.Estilo1 {color: #FFFFFF}
-->
 </style>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BVOEF" property="*" />
 <% 
 
 BVOEF.setResponse(response);
 BVOEF.setRequest(request);
 BVOEF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BVOEF.setUsuarioact( session.getAttribute("usuario").toString() );
 BVOEF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BVOEF.ejecutarValidacion();
 
 String titulo = BVOEF.getAccion().toUpperCase() + " DE ORDEN DE COMPRA Nro.: " + BVOEF.getId_oc_cabe() ;
 %>
<form action="vproveedoOcEstadoFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BVOEF.getAccion()%>" >
<input name="id_oc_cabe" type="hidden" value="<%=BVOEF.getId_oc_cabe()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BVOEF" property="mensaje"/>&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="21%" class="fila-det-border"><strong>Estado Actual : </strong></td>
                <td width="27%" class="fila-det-border"><%=BVOEF.getEstadooc()%></td>
                <td class="fila-det-border"><strong>Nuevo estado : </strong></td>
                <td class="fila-det-border"><% 
				Iterator iterListaEstadoOc = BVOEF.getListaEstadosOc().iterator();
				 %><select name="idestadooc" class="campo">
                      <option value="0">Seleccionar</option>
                      <% while(iterListaEstadoOc.hasNext()){ 
							String [] datosEstados = (String[]) iterListaEstadoOc.next();
							if(datosEstados[0].equals("1")) continue;
							String sel = "";
							if(BVOEF.getIdestadooc().toString().equals(datosEstados[0])) sel = "selected";
							else sel = "";
				   %>
                      <option value="<%=datosEstados[0]%>" <%= sel %>><%= datosEstados[1] %></option>
                      <% } %>
                  </select></td>
              </tr>
              <tr class="permiso-cinco">
                <td height="3" colspan="4"></td>
              </tr>
              <tr class="fila-det">
                <td width="21%" class="fila-det-border"><strong>Proveedor: </strong></td>
                <td width="27%" class="fila-det-border"><%=BVOEF.getIdproveedor()%> - <%=BVOEF.getRazon_social()%></td>
                <td class="fila-det-border"><strong>Fecha: </strong></td>
                <td class="fila-det-border" > <%=Common.setObjectToStrOrTime(BVOEF.getFechaoc(), "JSTsToStr")%> </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border"><strong>Grupo orden: </strong></td>
                <td class="fila-det-border"><%=BVOEF.getIdgrupooc()%> -&nbsp;<%=BVOEF.getGrupooc()%><input type="hidden" name="idgrupooc" value="<%=BVOEF.getIdgrupooc()%>" ></td>
                <td class="fila-det-border"><strong>Condicion de Pago: </strong></td>
                <td class="fila-det-border"><%=BVOEF.getIdcondicionpago()%> - <%=BVOEF.getCondicion()%></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border"><strong>Tipo iva: </strong></td>
                <td class="fila-det-border"><%=BVOEF.getIdtipoiva()%> - <%=BVOEF.getTipoiva()%></td>
                <td class="fila-det-border"><strong>Moneda: </strong></td>
                <td class="fila-det-border"><%=BVOEF.getIdmoneda()%> -&nbsp;<%=BVOEF.getMoneda()%></td>
              </tr>
              <tr class="fila-det">
                <td width="21%" class="fila-det-border">&nbsp;</td>
                <td width="27%" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><strong>Total: </strong></td>
                <td class="fila-det-border"><%=BVOEF.getTotaliva()%></td>
              </tr>
              <tr class="fila-det">
                <td height="28" class="fila-det-border"><strong>Observaciones: </strong></td>
                <td colspan="3" class="fila-det-border"> <%=BVOEF.getObservaciones()%>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border"><strong> Deposito de entrega previsto:</strong></td>
                <td class="fila-det-border"><%=BVOEF.getCodigo_dt()%> -&nbsp;<%=BVOEF.getDescrip_dt()%>
                <input type="hidden" name="codigo_dt" value="<%=BVOEF.getCodigo_dt()%>" ></td>
				
                <td class="fila-det-border"><strong>Fecha prevista para la entrega:</strong></td>
                <td class="fila-det-border"><%=Common.setObjectToStrOrTime(BVOEF.getFecha_entrega_prevista(), "JSTsToStr")%></td>
              </tr>
			  
			  
			  <tr class="permiso-cinco">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
				
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
			  
			  
			  
			  <% 
			  Iterator iterListaOCAsociadas =  BVOEF.getListaOCAsociadas().iterator();
			  if(iterListaOCAsociadas.hasNext()){ 
				
						  
			   %>
              <tr class="fila-det">
                <td colspan="4" class="fila-det-border">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				  <tr class="fila-det-bold">
				    <td width="3%" height="17" class="fila-det-border">&nbsp;</td>				  
				    <td width="4%" class="fila-det-border">OC</td>
				    <td width="12%" class="fila-det-border">Estado</td>
				    <td width="67%" class="fila-det-border">Proveedor</td>
				    <td width="14%" class="fila-det-border">Total</td>
			      </tr>
				  <%
				  do{  
				    String [] datosOCAsociadas = (String[]) iterListaOCAsociadas.next();
				  %>
				  <tr class="fila-det">
                    <td class="fila-det-border"><div align="center"><img src="../imagenes/default/gnome_tango/mimetypes/exec.png" width="18" height="18" title="Seleccionar" style="cursor:pointer" onClick="cambiarOC('<%= datosOCAsociadas[0] %>');"></div></td>				  
					<td class="fila-det-border"><%= datosOCAsociadas[0] %>&nbsp;</td>
					<td class="fila-det-border"><%= datosOCAsociadas[2] %>&nbsp;</td>
					<td class="fila-det-border"><%= datosOCAsociadas[3] %> -<%= datosOCAsociadas[4] %>&nbsp;</td>
					<td class="fila-det-border"><%= datosOCAsociadas[14] %>&nbsp;</td>
				  </tr>
				  <% 
				  }while(iterListaOCAsociadas.hasNext()); 
				   %>
                </table>				</td>
              </tr>

			  
			  <%
			  }  %>
              <tr class="fila-det">
                <td width="21%" class="fila-det-border Estilo1">&nbsp;</td>
                <td width="27%" class="fila-det-border Estilo1">&nbsp;</td>
                <td width="22%" class="fila-det-border Estilo1">&nbsp;</td>
                <td width="30%" class="fila-det-border Estilo1">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
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

