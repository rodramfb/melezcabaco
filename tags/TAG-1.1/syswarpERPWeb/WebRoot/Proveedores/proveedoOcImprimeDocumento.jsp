<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: proveedo_Oc_Cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Mar 28 09:44:56 CEST 2007 
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
<jsp:useBean id="BPOCF"  class="ar.com.syswarp.web.ejb.BeanProveedoOcImprimeDocumento"   scope="page"/>
<head>
 <title>Emisi&oacute;n de Ordenes de Compra</title>
 <link rel="stylesheet" href="<%=pathskin%>">

 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
</head>
<BODY >
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPOCF" property="*" />
 <%    
 String titulo = "Emisión de Ordenes de Compra" ;      
 BPOCF.setResponse(response);
 BPOCF.setRequest(request);
 BPOCF.setSession(session);
 BPOCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPOCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPOCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPOCF.ejecutarValidacion(); 
String genNombreEmpresa = session.getAttribute("genNombreEmpresa").toString();
String genCondicionFiscalEmpresa = session.getAttribute("genCondicionFiscalEmpresa").toString();
String genCUITEmpresa = session.getAttribute("genCUITEmpresa").toString();
String genDomicilioLegalEmpresa = session.getAttribute("genDomicilioLegalEmpresa").toString();
String genClaveDNRPEmpresa = session.getAttribute("genClaveDNRPEmpresa").toString();
String genLocalidadEmpresa = session.getAttribute("genLocalidadEmpresa").toString();
String genProvinciaEmpresa = session.getAttribute("genProvinciaEmpresa").toString();
String genNombreCompletoEmpresa = session.getAttribute("genNombreCompletoEmpresa").toString();
String genActividadEmpresa = session.getAttribute("genActividadEmpresa").toString();
String genTelefonosEmpresa = session.getAttribute("genTelefonosEmpresa").toString();
 %>
 <form action="proveedoOcFrm.jsp" method="post" name="frm">
   
            <table width="100%" border="0" cellspacing="2" cellpadding="0" align="center">
              <tr class="fila-det-impresion-rojo">
                <td >&nbsp;</td>
                <td colspan="3" ><jsp:getProperty name="BPOCF" property="mensaje"/>
                  &nbsp;</td>
              </tr>
              <tr class="fila-det-impresion">
                <td colspan="4" bgcolor="#CCCCCC" >DATOS DE ENTIDAD SOLICITANTE </td>
              </tr>
              <tr class="fila-det-impresion">
                <td >Razon Social:</td>
                <td ><%= genNombreEmpresa %></td>
                <td >TE:</td>
                <td   ><%= genTelefonosEmpresa %>&nbsp;</td>
              </tr>
              <tr class="fila-det-impresion">
                <td >Direccion:</td>
                <td ><%= genDomicilioLegalEmpresa %>&nbsp;</td>
                <td >Localidad:</td>
                <td   ><%= genLocalidadEmpresa %>&nbsp;</td>
              </tr>
              <tr class="fila-det-impresion">
                <td >CP:</td>
                <td >&nbsp;</td>
                <td >Provincia:</td>
                <td   ><%= genProvinciaEmpresa %>&nbsp;</td>
              </tr>
              <tr class="fila-det-impresion">
                <td >CUIT:</td>
                <td ><%= genCUITEmpresa %>&nbsp;</td>
                <td >Contacto:</td>
                <td   >&nbsp;</td>
              </tr>
              <tr class="fila-det-impresion">
                <td >&nbsp;</td>
                <td >&nbsp;</td>
                <td >&nbsp;</td>
                <td   >&nbsp;</td>
              </tr>
              <tr class="fila-det-impresion"> 
                <td >Estado: </td>
                <td ><%= BPOCF.getEstadooc() %></td>
                <td >Grupo Cotizaci&oacute;n: </td>
                <td   ><%//= BPOCF.getProveedor() %></td>
              </tr>
              <tr class="fila-det-impresion">
                <td >Condici&oacute;n de pago :</td>
                <td ><%= BPOCF.getCondicion() %></td>
                <td >&nbsp;Fecha: </td>
                <td   ><%= BPOCF.getFechaoc() %></td>
              </tr>
              <tr class="fila-det-impresion">
                <td >Tipo de Iva : </td>
                <td ><%= BPOCF.getTipoiva() %></td>
                <td >&nbsp;Moneda: </td>
                <td   ><%= BPOCF.getMoneda() %></td>
              </tr>
              <tr class="fila-det-impresion">
                <td >&nbsp;</td>
                <td >&nbsp;</td>
                <td width="17%" >&nbsp;</td>
                <td   >&nbsp;</td>
              </tr>
              <tr class="fila-det-impresion">
                <td colspan="4" bgcolor="#CCCCCC" >DATOS DEL PROVEEDOR </td>
              </tr>
              <tr class="fila-det-impresion">
                <td width="17%" >Razon Social :</td>
                <td width="36%" ><%= BPOCF.getProveedor() %>&nbsp;</td>
                <td >TE : </td>
                <td   ><%= BPOCF.getProveedorTelefono() %></td>
              </tr>
              <tr class="fila-det-impresion">
                <td >Direccion : </td>
                <td ><%= BPOCF.getProveedorDireccion() %></td>
                <td >Localidad: </td>
                <td ><%= BPOCF.getProveedorLocalidad() %></td>
              </tr>
              <tr class="fila-det-impresion">
                <td >CP : </td>
                <td ><%= BPOCF.getProveedorCodPost() %></td>
                <td > Provincia: </td>
                <td ><%= BPOCF.getProveedorProvincia() %></td>
              </tr>
              <tr class="fila-det-impresion">
                <td width="17%" > CUIT </td>
                <td width="36%" ><%= BPOCF.getProveedorCuit() %></td>
                <td >Contacto:</td>
                <td ><%= BPOCF.getProveedorContacto() %></td>
              </tr>
              <tr class="fila-det-impresion">
                <td >&nbsp;</td>
                <td >&nbsp;</td>
                <td width="17%" >&nbsp;</td>
                <td width="30%" >&nbsp;</td>
              </tr>
              <tr class="fila-det-impresion">
                <td width="17%" >Observaciones:  </td>
                <td colspan="3" ><%= BPOCF.getObservaciones() %></td>
              </tr>
              <tr class="fila-det-impresion">
                <td >&nbsp;</td>
                <td >&nbsp;</td>
                <td >&nbsp;</td>
                <td >&nbsp;</td>
              </tr>
              <tr class="fila-det-impresion">
                <td colspan="4" ><table width="100%" border="0" cellspacing="0" cellpadding="0"
							align="center">
                  <tr >
                    <td colspan="4" bgcolor="#CCCCCC" class="fila-det-impresion">&nbsp;
                    <div align="center">DETALLE   </div></td>
                  </tr>
                  <tr class="fila-det-impresion">
                    <td colspan="4" valign="top" ><!-- 2.1 -->
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr class="fila-det-impresion">
                            <td width="52%" > Articulo </td>
                            <td width="14%" ><div align="right"> Cantidad </div></td>
                            <td width="15%" ><div align="right"> P.U </div></td>
                            <td width="19%" ><div align="right">Total </div></td>
                          </tr>
                          <%												
										Iterator iterDetalle = BPOCF.getListOCDeta().iterator();
										 while(iterDetalle.hasNext()){
										   String art[] = (String[])iterDetalle.next(); %>
                          <tr class="fila-det-impresion">
                            <td height="25" ><%= art[2] %> - <%= art[3] %></td>
                            <td ><div align="right"> <%= art[10] %> </div></td>
                            <td ><div align="right"> <%= art[8] %> </div></td>
                            <td ><div align="right"> <%= art[16] %> </div></td>
                          </tr>
                          <% 
										 }
												%>
                          <tr class="fila-det-impresion">
                            <td colspan="3" ><div align="right">Total&nbsp;</div></td>
                            <td ><div align="right"><%= BPOCF.getTotalDebe() %></div></td>
                          </tr>
                        </table>
                    <!-- 2.1 -->                    </td>
                  </tr>

                </table></td>
              </tr>
              <tr class="fila-det-impresion">
                <td width="17%" >&nbsp;</td>
                <td width="36%" >&nbsp;</td>
                <td width="17%" >&nbsp;</td>
                <td width="30%" >&nbsp;</td>
              </tr>
              <tr class="fila-det-impresion">
                <td colspan="4" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det-impresion">
                    <td width="43%" ><div align="right"> Bonific 1: </div></td>
                    <td width="6%" ><div align="right"><%= BPOCF.gb.getNumeroFormateado(Float.parseFloat(BPOCF.getBonific1()), 10, 2) %></div></td>
                    <td width="4%" >&nbsp;</td>
                    <td width="29%" ><div align="right"> Recargo 1: </div></td>
                    <td width="6%" ><div align="right"><%=  BPOCF.gb.getNumeroFormateado(Float.parseFloat(BPOCF.getRecargo1()), 10, 2) %></div></td>
                    <td width="12%" >&nbsp;</td>
                  </tr>
                  <tr class="fila-det-impresion">
                    <td ><div align="right"> Bonific 2: </div></td>
                    <td ><div align="right"><%=  BPOCF.gb.getNumeroFormateado(Float.parseFloat(BPOCF.getBonific2()), 10, 2) %></div></td>
                    <td >&nbsp;</td>
                    <td ><div align="right"> Recargo 2: </div></td>
                    <td ><div align="right"><%=  BPOCF.gb.getNumeroFormateado(Float.parseFloat(BPOCF.getRecargo2()), 10, 2) %></div></td>
                    <td >&nbsp;</td>
                  </tr>
                  <tr class="fila-det-impresion">
                    <td ><div align="right"> Bonific 3: </div></td>
                    <td ><div align="right"><%=  BPOCF.gb.getNumeroFormateado(Float.parseFloat(BPOCF.getBonific3()), 10, 2) %></div></td>
                    <td >&nbsp;</td>
                    <td ><div align="right"> Recargo 3: </div></td>
                    <td ><div align="right"><%=  BPOCF.gb.getNumeroFormateado(Float.parseFloat(BPOCF.getRecargo3()), 10, 2) %></div></td>
                    <td >&nbsp;</td>
                  </tr>
                  <tr class="fila-det-impresion">
                    <td >&nbsp;</td>
                    <td >&nbsp;</td>
                    <td >&nbsp;</td>
                    <td ><div align="right">Recargo 4:</div></td>
                    <td ><div align="right"><%=  BPOCF.gb.getNumeroFormateado(Float.parseFloat(BPOCF.getRecargo4()), 10, 2) %></div></td>
                    <td >&nbsp;</td>
                  </tr>

                  <tr class="fila-det-impresion">
                    <td height="25" >&nbsp;</td>
                    <td >&nbsp;</td>
                    <td >&nbsp;</td>
                    <td ><div align="right"> Total General: </div></td>
                    <td  >
                    <div align="right"><%=  BPOCF.gb.getNumeroFormateado(Float.parseFloat(BPOCF.getTotalgeneral().toString()), 10, 2) %> </div></td>
                    <td  >&nbsp;</td>
                  </tr>
                  <tr class="fila-det-impresion">
                    <td >&nbsp;</td>
                    <td >&nbsp;</td>
                    <td >&nbsp;</td>
                    <td ><div align="right">Total General IVA:</div></td>
                    <td  >
                    <div align="right"><%=  BPOCF.getTotaliva() %></div></td>
                    <td  >&nbsp;</td>
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

