<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/*  
   Formulario de carga para la entidad: crmcotizaciones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jun 19 12:25:24 GMT-03:00 2007 
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
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanCrmcotizacionesFrm"   scope="page"/>
<head>
 <title>FRMCrmcotizaciones.jsp</title>
 <meta http-equiv="description" content="mypage">
  
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo = BCF.getAccion().toUpperCase() + " DE Gestion de cotizaciones" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BCF.ejecutarValidacion();
 %>
<form action="crmcotizacionesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="idcotizacion" type="hidden" value="<%=BCF.getIdcotizacion()%>" >
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
                <td width="26%" class="fila-det-border">Usuario: (*) </td>
              <td width="74%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="usuario" type="text" class="campo" id="usuario" value="<%=BCF.getUsuario()%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../CRM/lov_usuario.jsp', 'usuario', 700, 400)" style="cursor:pointer"></td>
<input name="idusuario" type="hidden" id="idusuario" value="<%=BCF.getIdusuario()%>">
</tr>
</table></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Nro lote: (*) </td>
                <td width="74%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="nrolote" type="text" value="<%=BCF.getNrolote()%>" class="campo" size="18" maxlength="18"  ></td>
                      <td width="39%">&nbsp;</td>
                      <input name="idusuario2" type="hidden" id="idusuario2" value="<%=BCF.getIdusuario()%>" />
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Tipo cotizacion: (*) </td>
                <td width="74%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="tipocotizacion" type="text" class="campo" id="tipocotizacion" value="<%=BCF.getTipocotizacion()%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../CRM/lov_tipocotizacion.jsp', 'tipocotizacion', 700, 400)" style="cursor:pointer"></td>
<input name="idtipocotizacion" type="hidden" id="idtipocotizacion" value="<%=BCF.getIdtipocotizacion()%>">
</tr>
</table></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Tipo financiacion:  </td>
              <td width="74%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="tipofinanciacion" type="text" class="campo" id="tipofinanciacion" value="<%=str.esNulo(BCF.getTipofinanciacion())%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../CRM/lov_tipofinanciacion.jsp', 'tipofinanciacion', 700, 400)" style="cursor:pointer"></td>
<input name="idtipofinanciacion" type="hidden" id="idtipofinanciacion" value="<%=BCF.getIdtipofinanciacion()%>">
</tr>
</table></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Superficie: (*) </td>
                <td width="74%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="superficie" type="text" value="<%=BCF.getSuperficie()%>" class="campo" size="18" maxlength="18"  ></td>
                      <td width="39%">&nbsp;</td>
                      <input name="idusuario3" type="hidden" id="idusuario3" value="<%=BCF.getIdusuario()%>" />
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Medida: (*) </td>
                <td width="74%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="descrip_md" type="text" class="campo" id="descrip_md" value="<%=BCF.getDescrip_md()%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../CRM/lov_medida.jsp', 'medidas', 700, 400)" style="cursor:pointer"></td>
<input name="codigo_md" type="hidden" id="codigo_md" value="<%=BCF.getCodigo_md()%>">
</tr>
</table></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Valor unitario: (*) </td>
                <td width="74%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="valor_unitario" type="text" value="<%=BCF.getValor_unitario()%>" class="campo" size="18" maxlength="18"  ></td>
                      <td width="39%">&nbsp;</td>
                      <input name="idusuario4" type="hidden" id="idusuario4" value="<%=BCF.getIdusuario()%>" />
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Valor total: (*) </td>
                <td width="74%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="valor_total" type="text" value="<%=BCF.getValor_total()%>" class="campo" size="18" maxlength="18"  ></td>
                      <td width="39%">&nbsp;</td>
                      <input name="idusuario5" type="hidden" id="idusuario5" value="<%=BCF.getIdusuario()%>" />
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Precio contado:  </td>
                <td width="74%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="precio_contado" type="text" value="<%=BCF.getPrecio_contado()%>" class="campo" size="18" maxlength="18"  ></td>
                      <td width="39%">&nbsp;</td>
                      <input name="idusuario6" type="hidden" id="idusuario6" value="<%=BCF.getIdusuario()%>" />
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Precio financiado:  </td>
                <td width="74%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="precio_financiado" type="text" value="<%=BCF.getPrecio_financiado()%>" class="campo" size="18" maxlength="18"  ></td>
                      <td width="39%">&nbsp;</td>
                      <input name="idusuario7" type="hidden" id="idusuario7" value="<%=BCF.getIdusuario()%>" />
                    </tr>
                  </table></td>
              </tr>
			  <tr class="fila-det">
                <td width="26%" class="fila-det-border">Individuos:  </td>
              <td width="74%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="razon_nombre" type="text" class="campo" id="razon_nombre" value="<%=str.esNulo(BCF.getRazon_nombre())%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../CRM/lov_individuos.jsp', 'individuos', 700, 400)" style="cursor:pointer"></td>
<input name="idindividuos" type="hidden" id="idindividuos" value="<%=BCF.getIdindividuos()%>">
</tr>
</table></td> 
              </tr>
			  <tr class="fila-det">
			    <td class="fila-det-border">Resultado Cotizaci&oacute;n:</td>
			    <td class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><select name="idresultadocotizacion" class="campo">
                        <option value="-1" >Seleccionar</option>
                        <%  
				    Iterator iter = BCF.getListResultadoCotizacion().iterator();
				    while(iter.hasNext()){
					  String [] datos = (String []) iter.next();
					  String sel = "";
					  if(BCF.getIdresultadocotizacion() != null && BCF.getIdresultadocotizacion().toString().equals(datos[0]))
					    sel = "selected";
					%>
                        <option value="<%= datos[0] %>" <%= sel %>><%= datos[1] %></option>
                        <%
				    }  %>
                      </select></td>
                      <td width="39%">&nbsp;</td>
                      <input name="idusuario62" type="hidden" id="idusuario62" value="<%=BCF.getIdusuario()%>" />
                    </tr>
                  </table></td>
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

