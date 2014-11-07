<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesexpresos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Nov 14 15:48:52 GMT-03:00 2006 
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
<%@ page import="java.util.*" %>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanClientesexpresosFrm"   scope="page"/>
<head>
 <title></title>
 <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script></head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 int idprovincia =1;
 int provincia = 1;
 int codigo_postal =1;
 String titulo = BCF.getAccion().toUpperCase() + " DE EXPRESOS" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BCF.ejecutarValidacion();
 %>
<form action="clientesexpresosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="idexpreso" type="hidden" value="<%=BCF.getIdexpreso()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="33%" class="fila-det-border">Expreso: (*) </td>
                <td width="67%" class="fila-det-border"><input name="expreso" type="text" value="<%=BCF.getExpreso()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Dep&oacute;sito Asociado(*): </td>
                <td class="fila-det-border">
                                   <select name="codigo_dt" id="codigo_dt" class="campo">
                                     <option value="-1">Seleccionar</option>
									<%
										Iterator iter = BCF.getListDepositos().iterator();  
										while(iter.hasNext()){ 
										  String[] datos = (String[])iter.next();
									 %>
										<option value="<%= datos[0] %>" <%= datos[0].equals(BCF.getCodigo_dt().toString()) ?  "selected":  "" %>><%= datos[1] %></option> 
									<% 
									  } %>  									
                                 </select></td>
              </tr>
              <tr class="fila-det">
                <td width="33%" class="fila-det-border">Cuit: (*) </td>
                <td width="67%" class="fila-det-border"><input name="cuit" type="text" value="<%=BCF.getCuit()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="33%" height="22" class="fila-det-border">Direccion: (*) </td>
                <td width="67%" class="fila-det-border"><input name="direccion" type="text" value="<%=BCF.getDireccion()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
			  
			  
			  
              <tr class="fila-det">
            <td width="33%" height="22" class="fila-det-border"> Localidad: (*)            </td>
                <td width="67%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
					<input name="localidad" type="text" class="campo" id="localidad" value="<%=BCF.getLocalidad()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_localidades.jsp')" style="cursor:pointer"></td>
					<input name="idlocalidad" type="hidden" id="idlocalidad" value="<%=BCF.getIdlocalidad()%>">
				    <input name="idprovincia" type="hidden" id="idprovincia" value= "idprovincia">
				    <input name="provincia" type="hidden" id="provincia" value= "provincia">
				    <input name="postal" type="hidden" id="postal" value= "postal">
                  </tr>
                </table></td>    
              </tr>
              <tr class="fila-det"> 
                <td width="33%" class="fila-det-border">Tipo iva: (*) </td>
                <td width="67%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="tipoiva" type="text" class="campo" id="tipoiva" value="<%=BCF.getTipoiva()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_Tipoiva.jsp')" style="cursor:pointer"></td>
                    <input name="idtipoiva" type="hidden" id="idtipoiva" value="<%=BCF.getIdtipoiva()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="33%" class="fila-det-border">Valor seguro: (*) </td>
                <td width="67%" class="fila-det-border"><input name="valor_seguro" type="text" value="<%=BCF.getValor_seguro()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Reporte Hoja Armado:(*) </td>
                <td class="fila-det-border"><input name="report_hojaarmado" type="text" class="campo" id="report_hojaarmado" value="<%=BCF.getReport_hojaarmado()%>" size="18" maxlength="30"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Reporte Hoja Ruta Final(*): </td>
                <td class="fila-det-border"><input name="report_hojarutafinal" type="text" class="campo" id="report_hojarutafinal" value="<%=BCF.getReport_hojarutafinal()%>" size="18" maxlength="30"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Reporte Hoja Ruta Final Regalos(*): </td>
                <td class="fila-det-border"><input name="report_hojarutafinal_reg" type="text" class="campo" id="report_hojarutafinal_reg" value="<%=BCF.getReport_hojarutafinal_reg()%>" size="18" maxlength="30"  ></td>
              </tr>
              
              <tr class="fila-det">
                <td class="fila-det-border">Unidad Medida</td>
                <td class="fila-det-border"><select name="idmedidabulto" id="idmedidabulto" class="campo">
                  <option value="-1">Seleccionar</option>
                  <%
										iter = BCF.getListMedidas().iterator();  
										while(iter.hasNext()){ 
										  String[] datos = (String[])iter.next();
									 %>
                  <option value="<%= datos[0] %>" <%= datos[0].equals(BCF.getIdmedidabulto().toString()) ?  "selected":  "" %>><%= datos[1] %></option>
                  <% 
									  } %>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Calcula Flete </td>
                <td class="fila-det-border">
				<select name="calculaflete" id="calculaflete" class="campo">
                  <option value="">Seleccionar</option>
                  <option value="S" <%= BCF.getCalculaflete().equalsIgnoreCase("S") ?  "selected":  "" %>>SI</option>
                  <option value="N" <%= BCF.getCalculaflete().equalsIgnoreCase("N") ?  "selected":  "" %>>NO</option>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Discrimina Flete </td>
                <td class="fila-det-border">
				<select name="discriminaflete" id="discriminaflete" class="campo">
                  <option value="">Seleccionar</option>
                  <option value="S" <%= BCF.getDiscriminaflete().equalsIgnoreCase("S") ?  "selected":  "" %>>SI</option>
                  <option value="N" <%= BCF.getDiscriminaflete().equalsIgnoreCase("N") ?  "selected":  "" %>>NO</option>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Valor Primer Bulto </td>
                <td class="fila-det-border"><input name="valorapagar1bulto" type="text" class="campo" id="valorapagar1bulto" value="<%=BCF.getValorapagar1bulto()%>" size="18" maxlength="18"  > </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Valor Bulto Excedente </td>
                <td class="fila-det-border"><input name="valorapagarexcedente" type="text" class="campo" id="valorapagarexcedente" value="<%=BCF.getValorapagarexcedente()%>" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Calcula Iva </td>
                <td class="fila-det-border">
				<select name="calculaiva" id="calculaiva" class="campo">
                  <option value="">Seleccionar</option>
                  <option value="S" <%= BCF.getCalculaiva().equalsIgnoreCase("S") ?  "selected":  "" %>>SI</option>
                  <option value="N" <%= BCF.getCalculaiva().equalsIgnoreCase("N") ?  "selected":  "" %>>NO</option>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
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

