<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: cajaIdentificadores
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Oct 17 10:32:34 GMT-03:00 2008 
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
<jsp:useBean id="BCIF"  class="ar.com.syswarp.web.ejb.BeanCajaIdentificadoresFrm"   scope="page"/>
<head>
 <title>Identificadores</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script> 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>

</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCIF" property="*" />
 <% 
 String titulo = BCIF.getAccion().toUpperCase() + " DE IDENTIFICADORES" ;
 BCIF.setResponse(response);
 BCIF.setRequest(request);
 BCIF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCIF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCIF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BCIF.ejecutarValidacion();
 %>
<script>
function callSetDiasCuotas(){
	var obj = document.frm.cuotas;
	if(obj.selectedIndex == 0){
		alert("Cantidad de cuotas debe ser mayor a cero.");
	}
	else{	
		abrirVentana('cajaValorTarFrm.jsp?tipo=C&valor_id=<%=BCIF.getIdentificador()%>&accion=<%= BCIF.getAccion() %>&cuotasPresentacion=' + obj.options[obj.selectedIndex].value  , 'dias',300, 400);
	}
}	

function callSetDiasPresentacion(){
	var obj = document.frm.presentacion;
	if(obj.selectedIndex == 0){
		alert("Cantidad de presentaciones debe ser mayor a cero.");
	}
	else{	
		abrirVentana('cajaValorTarFrm.jsp?tipo=P&valor_id=<%=BCIF.getIdentificador()%>&accion=<%= BCIF.getAccion() %>&cuotasPresentacion=' + obj.options[obj.selectedIndex].value  , 'dias',300, 400);
	} 
}	 

function cambiarTipo(){

  if(document.frm.tipoidentificador.value != ""){
    if(confirm("Al cambiar tipo perderá todos los datos cargados hasta el momento. \nConfirma de todos modos ?")){
      document.frm.primeraCarga.value = 'true';
      abrirVentana('lovCajaTipoIdentificadores.jsp' , 'identificadores',700, 200);	
			document.frm.submit();
		}	
  }
  else
    abrirVentana('lovCajaTipoIdentificadores.jsp' , 'identificadores',700, 200);	

} 
</script> 
<form action="cajaIdentificadoresFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCIF.getAccion()%>" >
<input name="nosubmit" type="hidden" value="" >
<input name="ididentificador" type="hidden" value="<%=BCIF.getIdidentificador()%>" >
   <input name="primeraCarga" type="hidden" value="false" >
   <table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
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
                <td colspan="3" class="fila-det-border"><jsp:getProperty name="BCIF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">&nbsp;Tipo: (*) </td>
                <td width="31%" class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="64%"><input name="tipoidentificador" type="text" value="<%=BCIF.getTipoidentificador()%>" class="campo" size="30" maxlength="50" readonly ></td>
                    <td width="36%"><div align="left">
                      <% 
                       if(BCIF.getAccion().equalsIgnoreCase("alta")){%>
                      <img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="cambiarTipo();" style="cursor:pointer" >
                      <%} 	%>
                      <input name="idtipoidentificador" type="hidden" value="<%=BCIF.getIdtipoidentificador()%>"  >
                    </div></td>
                  </tr>
                </table></td>
                <td class="fila-det-border">&nbsp;Moneda: (*) </td>
                <td class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="47%"><input name="moneda" type="text" value="<%=BCIF.getMoneda()%>" class="campo" size="20" maxlength="40" readonly></td>
                        <td width="53%"><div align="left">
                          <% 
              if(BCIF.getAccion().equalsIgnoreCase("alta")){%>
                          <img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="abrirVentana('../Clientes/lov_moneda.jsp' , 'monedas',700, 200)" style="cursor:pointer" >
                          <%} 	%> 
                          <input name="idmoneda" type="hidden" value="<%=BCIF.getIdmoneda()%>"  >
                        </div></td>
                      </tr>
                    </table></td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">&nbsp;Identificador: (*) </td>
                <td width="31%" class="fila-det-border"><input name="identificador" type="text" value="<%=BCIF.getIdentificador()%>" class="campo" size="3" maxlength="3" <%= !BCIF.getAccion().equalsIgnoreCase("alta") ? "readonly" : ""%>></td>
                <td class="fila-det-border">&nbsp;Descripcion: (*) </td>
                <td class="fila-det-border"><input name="descripcion" type="text" value="<%=BCIF.getDescripcion()%>" class="campo" size="30" maxlength="50"  >                </td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">&nbsp;Cuenta:  (*)</td>
                <td width="31%" class="fila-det-border"><input name="cuenta" id="cuenta" type="text" value="<%=BCIF.getCuenta()%>" class="campo" size="20" maxlength="40"  >
                <img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="abrirVentana('lov_contableInfiPlanAbm.jsp?elemento=cuenta&ocurrencia=' + document.getElementById ( 'cuenta' ).value , 'plancuentas',700, 200)" style="cursor:pointer" >								</td>
                <td class="fila-det-border">&nbsp;Cta. Caucion: </td>
                <td class="fila-det-border"><input name="ctacaucion" id="ctacaucion" type="text" value="<%=BCIF.getCtacaucion()%>" class="campo" size="20" maxlength="40" readonly >
								<%if(BCIF.getIdtipoidentificador().longValue() == 1 || BCIF.getIdtipoidentificador().longValue() == 4 || BCIF.getIdtipoidentificador().longValue() == 13){%>
                    <img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="abrirVentana('lov_contableInfiPlanAbm.jsp?elemento=ctacaucion&ocurrencia=' + document.getElementById ( 'ctacaucion' ).value , 'plancuentas',700, 200)" style="cursor:pointer" > 
								<%}%>								</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;Cta. todoc: </td>
                <td class="fila-det-border"><input name="ctatodoc" id="ctatodoc" type="text" value="<%=BCIF.getCtatodoc()%>" class="campo" size="20" maxlength="40" readonly >
                  <%if(BCIF.getIdtipoidentificador().longValue() == 1 || BCIF.getIdtipoidentificador().longValue() == 4 || BCIF.getIdtipoidentificador().longValue() == 13){%>
                  <img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="abrirVentana('lov_contableInfiPlanAbm.jsp?elemento=ctatodoc&ocurrencia=' + document.getElementById ( 'ctatodoc' ).value , 'plancuentas',700, 200)" style="cursor:pointer" > 
								<%}%>								</td>										
                <td class="fila-det-border">&nbsp;Cta. Cau. Doc: </td>
                <td class="fila-det-border"><input name="ctacaudoc" id="ctacaudoc" type="text" value="<%=BCIF.getCtacaudoc()%>" class="campo" size="20" maxlength="40"  readonly >
                <%if(BCIF.getIdtipoidentificador().longValue() == 1){%>                     
										<img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="abrirVentana('lov_contableInfiPlanAbm.jsp?elemento=ctacaudoc&ocurrencia=' + document.getElementById ( 'ctacaudoc' ).value , 'plancuentas',700, 200)" style="cursor:pointer" > 
								<%}%>								</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;Cta. dto. tar: </td>
                <td class="fila-det-border"><input name="ctadtotar" id="ctadtotar" type="text" value="<%=BCIF.getCtadtotar()%>" class="campo" size="20" maxlength="40" readonly >
                  <%if( BCIF.getIdtipoidentificador().longValue() != 13){%>
                    <img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="abrirVentana('lov_contableInfiPlanAbm.jsp?elemento=ctadtotar&ocurrencia=' + document.getElementById ( 'ctadtotar' ).value , 'plancuentas',700, 200)" style="cursor:pointer" > <%}%></td>
                <td class="fila-det-border">&nbsp;Cta. Tarjeta: </td>
                <td class="fila-det-border"><input name="ctatarjeta" id="ctatarjeta" type="text" value="<%=BCIF.getCtatarjeta()%>" class="campo" size="20" maxlength="40" readonly >
                    <%if(BCIF.getIdtipoidentificador().longValue() == 7){%>
                    <img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="abrirVentana('lov_contableInfiPlanAbm.jsp?elemento=ctatarjeta&ocurrencia=' + document.getElementById ( 'ctatarjeta' ).value , 'plancuentas',700, 200)" style="cursor:pointer" > 
										<%}%></td>
              </tr>
              
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">&nbsp;Mod. cta: (*) </td>
                <td width="31%" class="fila-det-border">
								<select name="modcta" id="modcta" class="campo" <%= BCIF.getIdtipoidentificador().longValue() == 6  ? "" : "disabled"%>>
                    <option value="">Seleccionar</option>
                    <option value="S" <%=BCIF.getModcta().equalsIgnoreCase("S") ? "selected" : ""%>>SI</option>
                    <option value="N" <%=BCIF.getModcta().equalsIgnoreCase("N") ? "selected" : ""%>>NO</option>
                  </select></td>
                <td class="fila-det-border">&nbsp;Factura: (*) </td>
                <td class="fila-det-border">
								<select name="factura" id="factura" class="campo"  <%= BCIF.getIdtipoidentificador().longValue() == 6  ? "" : "disabled"%>>
                    <option value="">Seleccionar</option>
                    <option value="S" <%=BCIF.getFactura().equalsIgnoreCase("S") ? "selected" : ""%>>SI</option>
                    <option value="N" <%=BCIF.getFactura().equalsIgnoreCase("N") ? "selected" : ""%>>NO</option>
                  </select></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;Afecta Comisiones:(*) </td>
                <td class="fila-det-border"><select name="afecomicob" id="afecomicob" class="campo">
                    <option value="">Seleccionar</option>
                    <option value="S" <%=BCIF.getAfecomicob().equalsIgnoreCase("S") ? "selected" : ""%>>SI</option>
                    <option value="N" <%=BCIF.getAfecomicob().equalsIgnoreCase("N") ? "selected" : ""%>>NO</option>
                </select></td>
                <td class="fila-det-border">&nbsp;Saldo inicial: </td>
                <td class="fila-det-border"><input name="saldo_id" type="text" value="<%=BCIF.getSaldo_id()%>" class="campo" size="15" maxlength="15"  <%=(BCIF.getIdtipoidentificador().longValue() == 1 || BCIF.getIdtipoidentificador().longValue() == 2  || BCIF.getIdtipoidentificador().longValue() == 3) ? ""  : "readonly" %> ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">&nbsp;Cuotas:  </td>
                <td width="31%" class="fila-det-border">
                  <select name="cuotas" id="cuotas" class="campo" <%= BCIF.getIdtipoidentificador().longValue() == 7  ? "" : "disabled"%>>
										<%for(int g=0;g<13;g++){  %>
                    <option value="<%= g %>" <%=BCIF.getCuotas().intValue() == g ? "selected" : ""%>><%= g %></option>
										<%}  %>
                  </select>
                  <%if(BCIF.getIdtipoidentificador().longValue() == 7 ){%>
                  <img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="callSetDiasCuotas()" style="cursor:pointer" >
                  <%}%></td>
                <td class="fila-det-border">&nbsp;Presentacion: </td>
                <td class="fila-det-border">
								<select name="presentacion" id="presentacion" class="campo" <%= BCIF.getIdtipoidentificador().longValue() == 7  ? "" : "disabled"%>>
                    <%for(int g=0;g<13;g++){  %>
                    <option value="<%= g %>" <%=BCIF.getPresentacion().intValue() == g ? "selected" : ""%>><%= g %></option>
                    <%}  %>
                  </select>
								<%if(BCIF.getIdtipoidentificador().longValue() == 7 ){%> 
                <img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="callSetDiasPresentacion();" style="cursor:pointer" >
                <%}%></td>
              </tr>
              <!--tr class="fila-det">
                <td width="18%" class="fila-det-border">&nbsp;comhyper:  </td>
                <td width="82%" class="fila-det-border"><input name="comhyper" type="text" value="< %=BCIF.getComhyper()%>" class="campo" size="40" maxlength="100"  ></td>
              </tr-->
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">&nbsp;Subdi Venta:  </td>
                <td width="31%" class="fila-det-border"><select name="subdiventa" id="subdiventa" class="campo">
                    <option value="">Seleccionar</option>
                    <option value="S" <%=BCIF.getSubdiventa().equalsIgnoreCase("S") ? "selected" : ""%>>SI</option>
                    <option value="N" <%=BCIF.getSubdiventa().equalsIgnoreCase("N") ? "selected" : ""%>>NO</option>
                  </select></td>
                <td height="30" class="fila-det-border">&nbsp;Res. nro: </td>
                <td class="fila-det-border"><input name="res_nro" type="text" value="<%=BCIF.getRes_nro()%>" class="campo" size="20" maxlength="40"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">&nbsp;CC:  </td>
                <td width="31%" class="fila-det-border"><input name="idcencosto" type="text" value="<%=BCIF.getIdcencosto()%>" class="campo" size="3" maxlength="3" readonly >
                  <img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="abrirVentana('../Contable/lov_centro_de_costos1.jsp?txtCod=idcencosto&txtDesc=desc_cc' , 'cc',700, 200)" style="cursor:pointer" >
                  <input name="desc_cc" type="hidden" class="campo" id="desc_cc" value="<%//=BCIF.getIdcencosto()%>"    ></td>
                <td class="fila-det-border">&nbsp;CC1: </td>
                <td class="fila-det-border"><input name="idcencosto1" type="text" value="<%=BCIF.getIdcencosto1()%>" class="campo" size="3" maxlength="3" readonly >
                <img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="abrirVentana('../Contable/lov_centro_de_costos1.jsp?txtCod=idcencosto1&txtDesc=desc_cc1' , 'cc',700, 200)" style="cursor:pointer" >
                <input name="desc_cc1" type="hidden" value="<%//=BCIF.getIdcencosto1()%>" class="campo"    ></td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">&nbsp;Modifica CC :  </td>
                <td width="31%" class="fila-det-border">
                  <select name="modicent" id="modicent" class="campo">
										<option value="">Seleccionar</option>
										<option value="S" <%=BCIF.getModicent().equalsIgnoreCase("S") ? "selected" : ""%>>SI</option>
										<option value="N" <%=BCIF.getModicent().equalsIgnoreCase("N") ? "selected" : ""%>>NO</option>
                  </select>									</td>
                <td class="fila-det-border">&nbsp;Modifica Sub CC: </td>
                <td class="fila-det-border"><select name="modsubcent" id="modsubcent" class="campo">
                    <option value="">Seleccionar</option>
                    <option value="S" <%=BCIF.getModsubcent().equalsIgnoreCase("S") ? "selected" : ""%>>SI</option>
                    <option value="N" <%=BCIF.getModsubcent().equalsIgnoreCase("N") ? "selected" : ""%>>NO</option>
                  </select></td>
              </tr>

<!--
              <tr class="fila-det">
                <td height="30" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;Gerencia: </td>
                <td class="fila-det-border"><input name="gerencia" type="text" value="<%=BCIF.getGerencia()%>" class="campo" size="1" maxlength="1"  >
                  (#) </td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;Renglones: (*) </td>
                <td class="fila-det-border"><input name="renglones" type="text" value="<%=BCIF.getRenglones()%>" class="campo" size="15" maxlength="40"  >
                (#)</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;Contador: </td>
                <td class="fila-det-border"><input name="contador" type="text" value="<%=BCIF.getContador()%>" class="campo" size="15" maxlength="40" readonly >
                  (#) </td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">&nbsp;Prox Cheq:  </td>
                <td width="31%" class="fila-det-border"><input name="prox_cheq" type="text" value="<%=BCIF.getProx_cheq()%>" class="campo" size="15" maxlength="40" readonly >
                  (#) </td>
                <td width="19%" class="fila-det-border">&nbsp;</td>
                <td width="31%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">&nbsp;Prox Reserv:  </td>
                <td width="31%" class="fila-det-border"><input name="prox_reserv" type="text" value="<%=BCIF.getProx_reserv()%>" class="campo" size="15" maxlength="40"  readonly >
                (#)</td>
                <td width="19%" class="fila-det-border">&nbsp;</td>
                <td width="31%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">&nbsp;Ulti Cheq:  </td>
                <td width="31%" class="fila-det-border"><input name="ulti_cheq" type="text" value="<%=BCIF.getUlti_cheq()%>" class="campo" size="15" maxlength="40"  readonly >
                (#)</td>
                <td width="19%" class="fila-det-border">&nbsp;</td>
                <td width="31%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;Formula: </td>
                <td class="fila-det-border"><textarea name="formula" cols="40" rows="2" class="campo" readonly><%=BCIF.getFormula()%></textarea>
                  (#) </td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
-->							
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><input name="validar" type="submit" value="Enviar" class="boton"></td>
                <td class="fila-det-border"><input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

