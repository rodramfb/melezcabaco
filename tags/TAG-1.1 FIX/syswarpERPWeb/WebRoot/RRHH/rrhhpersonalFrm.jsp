<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: rrhhpersonal
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Apr 22 09:44:28 ACT 2009 
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
<jsp:useBean id="BRF"  class="ar.com.syswarp.web.ejb.BeanRrhhpersonalFrm"   scope="page"/>
<head>
 <title>FRMRrhhtitulo.jsp</title>
<link rel = "stylesheet" href = "<%= pathskin %>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="vs/overlib/overlib.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BRF" property="*" />
 <% 
 String titulo = BRF.getAccion().toUpperCase() + " DE RRHHPERSONAL" ;
 BRF.setResponse(response);
 BRF.setRequest(request);
 BRF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BRF.setUsuarioact( session.getAttribute("usuario").toString() );
 BRF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BRF.ejecutarValidacion();
 %>
<form action="rrhhpersonalFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BRF.getAccion()%>" >
	<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
    	<tr>
       		<td>
         		<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            		<tr class="text-globales">
              			<td>&nbsp;<%= titulo %></td>
              			<td></td>
            		</tr>
         		</table> 
            	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              		<tr class="fila-det-bold-rojo">
                		<td class="fila-det-border">&nbsp;</td>
                		<td class="fila-det-border" colspan="3"><jsp:getProperty name="BRF" property="mensaje"/>&nbsp;</td>
              		</tr>
			  		<tr class="fila-det">
                		<td class="fila-det-border">&nbsp;Legajo: (*) </td>
                		<td class="fila-det-border">&nbsp;
                			<input name="legajo" type="text" value="<%=BRF.getLegajo()%>" class="campo" size="50" maxlength="18"  >
                		</td>
                		<td class="fila-det-border">&nbsp;Apellido y Nombre:(*)</td>
                		<td class="fila-det-border">&nbsp;
                			<input name="apellido" type="text" value="<%=BRF.getApellido()%>" class="campo" size="50" maxlength="70"  >
                		</td>
              		</tr>
              		<tr class="fila-det">
                		<td class="fila-det-border">&nbsp;Domicilio: (*) </td>
                		<td class="fila-det-border">&nbsp;
                			<input name="domicilio" type="text" value="<%=BRF.getDomicilio()%>" class="campo" size="50" maxlength="50"  >
                		</td>
                		<td class="fila-det-border">Puerta:  </td>
                		<td class="fila-det-border">&nbsp;
                			<input name="puerta" type="text" value="<%=BRF.getPuerta()%>" class="campo" size="3" maxlength="18"  >
                		</td>
                	</tr>
              		<tr class="fila-det">
                		<td class="fila-det-border">&nbsp;Localidad:  </td>
                		<td class="fila-det-border">&nbsp;
                				<input name="idlocalidad" type="hidden" id="idlocalidad" value="<%=BRF.getIdlocalidad()%>">
	            				<input name="provincia" type="hidden" id="provincia" >
	            				<input name="postal" type="hidden" value="<%=BRF.getPostal()%>" class="campo" size="10" maxlength="10"  >
                				<input name="idprovincia" type="hidden" value="<%=BRF.getIdprovincia()%>" class="campo" size="100" maxlength="100"  >
	            				<input name="localidad" type="text" class="campo" id="localidad" value="<%=BRF.getLocalidad()%>" size="50" readonly="readonly">
								<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_localidades.jsp')" style="cursor:pointer">
                    	</td>
                    	<td class="fila-det-border">&nbsp;Piso:  </td>
                		<td class="fila-det-border">&nbsp;
                			<input name="piso" type="text" value="<%=BRF.getPiso()%>" class="campo" size="3" maxlength="60">
                		</td>
              		</tr>
			  		<tr class="fila-det">
              			<td class="fila-det-border">&nbsp;Depto:  </td>
                		<td class="fila-det-border">&nbsp;
                			<input name="depto" type="text" value="<%=BRF.getDepartamento()%>" class="campo" size="3" maxlength="60">
                		</td>
                		<td class="fila-det-border">&nbsp;Estado Civil:</td>
                		<td class="fila-det-border">&nbsp;
                				<input name="idestadocivil" type="hidden" id="idestadocivil" value="<%=BRF.getIdestadocivil()%>">
                  				<input name="estadocivil" type="text" class="campo" id="estadocivil" value="<%=BRF.getEstadocivil()%>" size="50" readonly="readonly">
                    			<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_estadocivil.jsp')" style="cursor:pointer">
                        </td>
              		</tr>
              		<tr class="fila-det">
                		<td class="fila-det-border">&nbsp;Fecha de Nacimiento: </td>
                		<td class="fila-det-border" >&nbsp;
                		<input class="cal-TextBox" onFocus="this.blur()" size="12" readonly="readonly" type="text" name="fechanacStr" value="<%=BRF.getFechanacStr()%>" maxlength="12">
                  			<a class="so-BtnLink" href="javascript:calClick();return false;" onmouseover="calSwapImg('BTN_date_12', 'img_Date_OVER',true); " onmouseout="calSwapImg('BTN_date_12', 'img_Date_UP',true);" onclick="calSwapImg('BTN_date_12', 'img_Date_DOWN');showCalendar('frm','fechanacStr','BTN_date_12');return false;">
                  				<img align="absmiddle" border="0" name="BTN_date_12" src="vs/calendar/btn_date_up.gif" width="22" height="17">
                  			</a>                
                  		</td>
                  		<td class="fila-det-border">&nbsp;Sexo:  </td>
                		<td class="fila-det-border">&nbsp;
                			<select name="sexo" id="sexo"  class="campo">
                  				<option value="M" <%= BRF.getSexo().equalsIgnoreCase("M") ? "selected" : "" %> >Masculino</option>
                  				<option value="F" <%= BRF.getSexo().equalsIgnoreCase("F") ? "selected" : "" %> >Femenino</option>
                			</select>
                		</td>  
              		</tr>
              		<tr class="fila-det">
                		<td class="fila-det-border">&nbsp;Tipo Documento: </td>
                		<td class="fila-det-border">&nbsp;
                			<input name="idtipodocumento" type="hidden" id="idtipodocumento" value="<%=BRF.getIdtipodocumento()%>">
							<input name="tipodocumento" type="text" class="campo" id="tipodocumento" value="<%=BRF.getTipodocumento()%>" size="30" readonly="readonly">
                    		<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_tipodocumento.jsp')" style="cursor:pointer">
                        </td>
                  		<td class="fila-det-border">&nbsp;Nro Documento:  </td>
                		<td class="fila-det-border">&nbsp;<input name="nrodocumento" type="text" value="<%=BRF.getNrodocumento()%>" class="campo" size="50" maxlength="18"  ></td>
              		</tr>
              		<tr class="fila-det">
                		<td class="fila-det-border">&nbsp;CUIL:  </td>
                		<td class="fila-det-border">&nbsp;
                			<input name="cuil" type="text" value="<%=BRF.getCuil()%>" class="campo" size="50" maxlength="20"  >
                		</td>
                		<td class="fila-det-border">&nbsp;Pais</td>
                		<td class="fila-det-border">&nbsp;
                			<input name="idpais" type="hidden" id="idpais" value="<%=BRF.getIdpais()%>">
				        	<input name="pais" type="text" class="campo" id="pais" value="<%=BRF.getPais()%>" size="50" readonly="readonly">
                    		<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_paises.jsp')" style="cursor:pointer">
                        </td>
              		</tr>
              		 <tr class="fila-det">
                		<td class="fila-det-border">&nbsp;E-mail:  </td>
                		<td class="fila-det-border" colspan="3" >&nbsp;
                			<input name="email" type="text" value="<%=BRF.getEmail()%>" class="campo" size="55" maxlength="100"  >
                		</td>
              		</tr>
              		<tr class="fila-det">
                		<td  class="fila-det-border">&nbsp;Fecha Ingreso:  </td>
                		<td class="fila-det-border" >&nbsp;
                  			<input class="cal-TextBox" onFocus="this.blur()" size="12" readonly="readonly" type="text" name="fingresoStr" value="<%=BRF.getFingresoStr()%>" maxlength="12">
                  				<a class="so-BtnLink" href="javascript:calClick();return false;" onmouseover="calSwapImg('BTN_date_21', 'img_Date_OVER',true); " onmouseout="calSwapImg('BTN_date_21', 'img_Date_UP',true);" onclick="calSwapImg('BTN_date_21', 'img_Date_DOWN');showCalendar('frm','fingresoStr','BTN_date_21');return false;">
                  					<img align="absmiddle" border="0" name="BTN_date_21" src="vs/calendar/btn_date_up.gif" width="22" height="17">
                  				</a>
						</td>
						<td class="fila-det-border">&nbsp;</td>
						<td class="fila-det-border">&nbsp;</td>
						<!--<td class="fila-det-border">&nbsp;Fecha Baja :  </td>
                		<td class="fila-det-border" >
                  			<input class="cal-TextBox" onFocus="this.blur()" size="12" readonly="readonly" type="text" name="fbajaStr" value="< %=BRF.getFbajaStr()%>" maxlength="12">
                  			<a class="so-BtnLink" href="javascript:calClick();return false;" onmouseover="calSwapImg('BTN_date_18', 'img_Date_OVER',true); " onmouseout="calSwapImg('BTN_date_18', 'img_Date_UP',true);" onclick="calSwapImg('BTN_date_18', 'img_Date_DOWN');showCalendar('frm','fbajaStr','BTN_date_18');return false;">
                  				<img align="absmiddle" border="0" name="BTN_date_18" src="vs/calendar/btn_date_up.gif" width="22" height="17">
                  			</a>
						</td>
              		--></tr>
              		<tr class="fila-det">
                		<td class="fila-det-border">&nbsp;Categoria:  </td>
                		<td class="fila-det-border">&nbsp;
	                		<input name="idcategoria" type="hidden" id="idpais2" value="<%=BRF.getIdcategoria()%>">
	                  		<input name="categoria" type="text" class="campo" id="categoria" value="<%=BRF.getCategoria()%>" size="30" readonly="readonly">
	                    	<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_categorias.jsp')" style="cursor:pointer">
                		</td>
                		<td class="fila-det-border">&nbsp;Tarea:  </td>
                		<td class="fila-det-border">&nbsp;
                			<input name="tarea" type="text" value="<%=BRF.getTarea()%>" class="campo" size="50" maxlength="50"  >
                		</td>
              		</tr>
              		<tr class="fila-det">
                		<td class="fila-det-border">&nbsp;Titulo:  </td>
                		<td class="fila-det-border" colspan="3">&nbsp;
                			<input name="idtitulo" type="hidden" id="idtitulo" value="<%=BRF.getIdtitulo()%>">
		                  	<input name="titulo" type="text" class="campo" id="titulo" value="<%=BRF.getTitulo()%>" size="50" readonly="readonly">
		                    <img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_titulo.jsp')" style="cursor:pointer">
		                 </td>
              		</tr>
              		<tr class="fila-det">
                		<td class="fila-det-border">&nbsp;ART:</td>
                		<td class="fila-det-border">&nbsp;
                  			<input name="idart" type="hidden" id="idart" value="<%=BRF.getIdart()%>">
                  			<input name="art" type="text" class="campo" id="art" value="<%=BRF.getArt()%>" size="50" readonly="readonly">
		                    <img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_art.jsp')" style="cursor:pointer">
        	            </td>
            			<td  class="fila-det-border">&nbsp;Nro ART :  </td>
                		<td class="fila-det-border">&nbsp;
                			<input name="nroart" type="text" value="<%=BRF.getNroart()%>" class="campo" size="50" maxlength="30"  ></td>
              		</tr>
              		<tr class="fila-det">
                		<td class="fila-det-border">&nbsp;Valor hora 01 :</td>
                		<td class="fila-det-border">&nbsp;
                			<input name="valor01" type="text" value="<%=BRF.getValor01()%>" class="campo" size="3" maxlength="18"  >
              			</td>
              			<td class="fila-det-border">&nbsp;Valor hora 02 :</td>
              			<td class="fila-det-border">&nbsp;
              				<input name="valor02" type="text" value="<%=BRF.getValor02()%>" class="campo" size="3" maxlength="18"  >
              			</td>
              		</tr>
              		<tr class="fila-det">
              		<td class="fila-det-border">&nbsp;Valor hora 03 :</td>
                		<td class="fila-det-border">&nbsp;
                			<input name="valor03" type="text" value="<%=BRF.getValor03()%>" class="campo" size="3" maxlength="18"  >
              			</td>
              			<td class="fila-det-border">&nbsp;Valor hora 04 :</td>
              			<td class="fila-det-border">&nbsp;
              				<input name="valor04" type="text" value="<%=BRF.getValor04()%>" class="campo" size="3" maxlength="18"  >
              			</td>
              		</tr>
              		<tr class="fila-det">
              			<td class="fila-det-border">&nbsp;Valor hora 05 :</td>
              			<td class="fila-det-border">&nbsp;
              				<input name="valor05" type="text" value="<%=BRF.getValor05()%>" class="campo" size="3" maxlength="18"  >
              			</td>
              			<td class="fila-det-border">&nbsp;Mensual?</td>
              			<td class="fila-det-border">
              			&nbsp;<select name="mensualoquin" id="mensualoquin"  class="campo">
                  			<option value="S" <%= BRF.getMensualoquin().equalsIgnoreCase("S") ? "selected" : "" %> >S</option>
                  			<option value="N" <%= BRF.getMensualoquin().equalsIgnoreCase("M") ? "selected" : "" %> >N</option>
                			</select>
                		</td>	
              		</tr>
                	<tr class="fila-det">
		                <td class="fila-det-border">&nbsp;Cta contable 1: </td>
		                <td class="fila-det-border">&nbsp;
		                    <input name="idctacont" type="text" class="campo" id="idctacont" value="<%=BRF.getIdctacont()%>" size="50" readonly="readonly">
		                    <img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_cuentascontables.jsp')" style="cursor:pointer">
		                  </td>
                  			<td class="fila-det-border">&nbsp;Cta contable 2 </td>
                			<td class="fila-det-border">&nbsp;
                    		<input name="idctacont2" type="text" class="campo" id="idctacont2" value="<%=BRF.getIdctacont2()%>" size="50" readonly="readonly">
                    		<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_cuentascontables2.jsp')" style="cursor:pointer">
                 		</td>
              		</tr>
              		<tr class="fila-det">
		                <td class="fila-det-border">&nbsp;Años reconocidos: </td>
		                <td class="fila-det-border">&nbsp;
		                    <input name="aniosrecon" type="text" value="<%=BRF.getAniosrecon()%>" class="campo" size="3" maxlength="4"  >
		                  </td>
                  			<td class="fila-det-border">&nbsp;Mes reconocidos: </td>
                			<td class="fila-det-border">&nbsp;
                    		<input name="mesrecon" type="text" value="<%=BRF.getMesrecon()%>" class="campo" size="3" maxlength="4"  >
                 		</td>
             		 </tr>
              
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;Obra social: </td>
                <td class="fila-det-border">
                  <input name="idobrasocial" type="hidden" id="idobrasocial" value="<%=BRF.getIdobrasocial()%>">
                  &nbsp;<input name="obrasocial" type="text" class="campo" id="obrasocial" value="<%=BRF.getObrasocial()%>" size="50" readonly="readonly">
                  <img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_obrasocial.jsp')" style="cursor:pointer">
             	 </td>
                <td class="fila-det-border">&nbsp;Jubilado:  </td>
                <td class="fila-det-border">&nbsp;&nbsp;<select name="jubilado" id="jubilado"  class="campo">
                  <option value="S" <%= BRF.getJubilado().equalsIgnoreCase("S") ? "selected" : "" %> >S</option>
                  <option value="N" <%= BRF.getJubilado().equalsIgnoreCase("N") ? "selected" : "" %> >N</option>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;Localidad de Pago:
				  <input name="idlocalidadpago" type="hidden" id="idlocalidadpago" value="<%=BRF.getIdlocalidadpago()%>"></td>
                	<td class="fila-det-border">&nbsp;
                    <input name="localidadpago" type="text" class="campo" id="localidadpago" value="<%=BRF.getLocalidadpago()%>" size="50" readonly="readonly">
                    <img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_localidades2.jsp')" style="cursor:pointer">
                  </td>
              	<td class="fila-det-border">&nbsp;Modalidad de Contrato  
				<input name="idmodalidadcontrato" type="hidden" id="idmodalidadcontrato" value="<%=BRF.getIdmodalidadcontrato()%>"></td>
                <td class="fila-det-border">&nbsp;
                    <input name="modalidadcontrato" type="text" class="campo" id="modalidadcontrato" value="<%=BRF.getModalidadcontrato()%>" size="50" readonly="readonly">
                    <img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_modalidadcontrato.jsp')" style="cursor:pointer">
                </td>
              </tr> 
				<tr class="fila-det">
                		<td class="fila-det-border">&nbsp;Banco  				<input name="idbancodeposito" type="hidden" id="idbancodeposito" value="<%=BRF.getIdbancodeposito()%>"></td>
                		<td  class="fila-det-border" >&nbsp;
                    		<input name="bancodeposito" type="text" class="campo" id="bancodeposito" value="<%=BRF.getBancodeposito()%>" size="50" readonly="readonly">
                    		<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_cajaidentificadores.jsp')" style="cursor:pointer">
                  		</td>
                  		<td class="fila-det-border">&nbsp;Lista de conceptos  				
                  		<input name="idlista" type="hidden" id="idlista" value="<%=BRF.getIdlista()%>"></td>
                		<td  class="fila-det-border" >&nbsp;
                    		<input name="lista" type="text" class="campo" id="lista" value="<%=BRF.getLista()%>" size="50" readonly="readonly">
                    		<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_lista.jsp')" style="cursor:pointer">
                  		</td>
                  		
                 </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

