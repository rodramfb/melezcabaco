<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0); 
/* 
   Formulario de carga para la entidad: clientesclientes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Dec 11 15:30:21 GMT-03:00 2006 
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
String diasAuditoria = session.getAttribute("clientesDomiciliosDiasDeAuditoria").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanClientesClientesFrm"   scope="page"/>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
<head>
 <title></title>
 <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 <!--BORRAR-->
 <link rel="stylesheet" href="vs/calendar/calendar.css"> 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <script >
 


  function callRelacionClientes(idcliente, razon){
/*
    var accion = '<%=BCF.getAccion()%>'; 
    if(accion.toUpperCase()!= 'MODIFICACION'){
      alert('Solo se pueden relacionar clientes cuando el mismo ya haya sido dado de alta.');
      return false;
    }
*/    
    abrirVentana('clientesRelacionesAbm.jsp?idcliente='+idcliente+'&razon='+razon, '', 750, 250);  
    return true;

  }
  

 </script>
 
 
 </head>

</head>
<BODY >
<div id="popupcalendar" class="text"></div>

 <% 
 Iterator iter;
 BCF.setResponse(response);
 BCF.setRequest(request); 
 BCF.setSession(session);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() ); 
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));     
 BCF.ejecutarValidacion();
 String titulo = BCF.getAccion().toUpperCase() + " DE CLIENTES" ;
 %>
<form action="clientesClientesDetalle.jsp" method="post" name="frm"  >


  <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center"> 
     <tr>
       <td>
         <table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<img src="../imagenes/default/gnome_tango/apps/config-users.png" width="22" height="22"> <%= titulo %></td>
            </tr>
         </table> 
            <table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="3" class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>
                &nbsp;</td>
              </tr>
			  
			  <tr class="fila-det">
                <td width="20%" class="fila-det-border">Codigo:  (*)</td>
                <td width="31%" class="fila-det-border"><%=BCF.getIdcliente()%>&nbsp;</td>
                <td class="fila-det-border">Razon Social: (*) </td>
                <td class="fila-det-border"><%=BCF.getRazon()%>&nbsp;</td>
			  </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Nro Documento: (*) </td>
                <td class="fila-det-border"><%=BCF.getNrodocumento()%>&nbsp;</td>
                <td width="22%" class="fila-det-border">Tipo Documento:(*)  
                <td width="27%" class="fila-det-border">
                    <%
                   iter = BCF.getListTiposDoc().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                     <%=  BCF.getIdtipodocumento().toString().equals(datos[0]) ? datos[0] : "" %>
                    <% 
                   } %>
				</td>
              </tr>

              <tr class="fila-det">
                <td class="fila-det-border">Tipo de cliente: 
                <input name="idtipoclie" type="hidden" id="idtipoclie" value="<%=BCF.getIdtipoclie()%>"></td>
                <td class="fila-det-border"><%=str.esNulo(BCF.getTipoclie())%>&nbsp;</td>
                <td class="fila-det-border">Autorizado: (*) </td>
                <td class="fila-det-border"><%= BCF.getAutorizado().equalsIgnoreCase("S") ? "SI" : "NO" %>&nbsp;</td>
              </tr>
			  

              <tr class="fila-det">
                <td class="fila-det-border">Categoria:
                  <input name="idcategoria" type="hidden" id="idcategoria" value="<%=BCF.getIdcategoria()%>">
                  (*)</td>
                <td class="fila-det-border"><%=str.esNulo(BCF.getCategoria())%>&nbsp;</td>
                <td class="fila-det-border">Periodicidad:(*)</td>
                <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/apps/date.png" width="22" height="22" onClick="abrirVentana('../Clientes/clientesPeriodicidadEntregaFrm.jsp?idcliente=<%=BCF.getIdcliente()%>&accion=consulta', 'periodicidad', 800, 450)" style="cursor:pointer"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Observaciones:</td>
                <td colspan="3" class="fila-det-border"><%=str.esNulo(BCF.getObservacion()) %>&nbsp;</td>
              </tr> 
              <tr class="fila-det">
                <td class="fila-det-border">Razones Asociadas : </td>
                <td colspan="3" class="fila-det-border"><table width="3%" border="0" cellpadding="0" cellspacing="0">  
                  <tr class="fila-det-border">
                    <td width="61%" ><img src="../imagenes/default/gnome_tango/actions/mail_replyall.png" width="20" height="20" onClick="callRelacionClientes( <%=BCF.getIdcliente()%>, '<%=BCF.getRazon()%>');" style="cursor:pointer"></td>
                  </tr>
                </table></td>
              </tr>
			  
              <tr class="text-dos-bold">
                <td colspan="4"  ><img src="../imagenes/default/gnome_tango/apps/accessories-calculator.png" width="22" height="22"> GESTION COMERCIAL  </td>
              </tr>
              <tr class="fila-det"> 
                <td class="fila-det-border">Nro de Ingresos Brutos: </td>
                <td class="fila-det-border"><%=BCF.getBrutos()%>&nbsp;</td>
                <td width="22%" class="fila-det-border">&nbsp;</td>
                <td width="27%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Tipo de I.V.A.: (*)                </td>
                <td class="fila-det-border">
                    <%
                   iter = BCF.getListIva().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
					 <%=  BCF.getIdtipoiva().toString().equals(datos[0]) ? datos[1] : "" %>
                    <% 
                   } %>&nbsp;
 
                </td>
                <td class="fila-det-border">Condicion de Pago: (*)                </td>
                <td class="fila-det-border">&nbsp;
                    <%
                   iter = BCF.getListCondicion().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
					 <%=  BCF.getIdcondicion().toString().equals(datos[0]) ? datos[1]  : "" %>
                    <% 
                   } %>
                </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Cuenta Contable Neto:</td>
                <td class="fila-det-border"><%=BCF.getIdctaneto()%>&nbsp;</td>
                <td class="fila-det-border">Moneda de Facturacion: (*)                </td>
                <td class="fila-det-border">&nbsp; 
                   <%
                   iter = BCF.getMonedaList().iterator();   
                   while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
					 <%= ( BCF.getIdmoneda().longValue() < 0 && datos[1].trim().equalsIgnoreCase("PESOS") )|| BCF.getIdmoneda().toString().equals(datos[0]) ? datos[1] : "" %>
                     
                   <% 
                   } %>
 
                </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Categoria de Credito: (*)                  </td>
                <td class="fila-det-border">&nbsp;
                    
                    <%
                   iter = BCF.getListCategoriaCred().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
					 <%=  BCF.getIdcredcate().toString().equals(datos[0]) ? datos[1]  : "" %>
                    <% 
                   } %>
 
                </td>
                <td class="fila-det-border">Limite de credito: </td>
                <td class="fila-det-border"><%=BCF.getLcredito()%>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Lista de Precios:                </td>
                <td class="fila-det-border">&nbsp;
                  <%
                   iter = BCF.getListasPreciosList().iterator();   
                   int x = 0; 
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
					 <%= ( BCF.getIdlista().longValue() < 0 && x==0  )|| BCF.getIdlista().toString().equals(datos[0]) ?   datos[1]  : "" %>
                  <% 
                     x++;
                   } %>

                </td>
                <td class="fila-det-border">Tipo de Comprobante :                </td>
                <td class="fila-det-border">&nbsp;
                  <%
                   iter = BCF.getTiposComprobList().iterator();   
                   while(iter.hasNext()){
                     String[] datos = (String[]) iter.next(); 
                     %>
                     <%= ( BCF.getIdtipocomp().longValue() < 0 && datos[2].trim().equalsIgnoreCase("FA") )|| BCF.getIdtipocomp().toString().equals(datos[0]) ?  datos[2] : "" %>					 
 
                  <% 
                   } %>
              </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Descuento 1: </td>
                <td class="fila-det-border">&nbsp;<%=BCF.getDescuento1()%></td>
                <td class="fila-det-border">Descuento 3: </td>
                <td class="fila-det-border">&nbsp;<%=BCF.getDescuento3()%></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Descuento 2: </td>
                <td class="fila-det-border">&nbsp;<%=BCF.getDescuento2()%></td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det" >
                <td colspan="4" class="fila-det-border">
								<table width="100%" border="1px" cellspacing="0" cellpadding="0" align="center" id="tarjeta">
                  <tr class="text-dos-bold" >
                    <td width="100%" > <img src="../imagenes/default/gnome_tango/actions/contact-new.png" width="22" height="22"> TARJETAS DE CREDITO DEL CLIENTE                     </td>
                  </tr>  
                  <tr class="text-dos-bold" >
                    <td class="fila-det-bold-rojo"><%=BCF.getSubAccion().equalsIgnoreCase("agregarTarjeta") ?  BCF.getMensaje() : ""   %></td>
                  </tr>
                  <tr class="text-globales">
                    <td class="fila-det" >
										<% 
										Enumeration enu;
										Hashtable htTarjetas = (Hashtable)session.getAttribute("htTarjetasCliente");
										enu = htTarjetas.keys();
										if(!htTarjetas.isEmpty()){
										%>
										<table width="100%" border="1" cellspacing="0" cellpadding="0" >
                        <tr class="fila-det-bold">
                          <td>Tarjeta</td>
                          <td>Activa</td>
                          <td>Titular</td>
                          <td>Nro. Tarjeta </td>
                          <td width="16%">Orden</td>
                          </tr>
										<%
											while(enu.hasMoreElements()){
												Object [] tarjeta = (Object[])htTarjetas.get(enu.nextElement());
										 %>
                        <tr class="fila-det">
                          <td width="13%"><%= tarjeta[2] %>&nbsp;</td>
                          <td width="6%"><%= tarjeta[11] %>&nbsp;</td>
                          <td width="24%"><%= tarjeta[9] %>&nbsp;</td>
                          <td width="28%"><%= tarjeta[5] %>&nbsp;</td>
                          <td width="28%"><%= tarjeta[10] %>&nbsp;</td>
                          </tr>
										<% 
											}										
										 %>											
                    </table>		
										<% 
										}										
										 %>										</td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td colspan="4" class="fila-det-border">
								 <table width="100%" border="1px" cellspacing="0" cellpadding="0" align="center" id="domicilio">
                  <tr class="text-globales">
                    <td width="100%" class="text-diez" ><img src="../imagenes/default/gnome_tango/actions/go-home.png" width="22" height="22"> DOMICILIOS DEL CLIENTE </td>
                  </tr>
                  <tr class="text-globales">
                    <td class="fila-det-bold-rojo" ><%=BCF.getSubAccion().equalsIgnoreCase("agregarDomicilio") ?  BCF.getMensaje() : ""   %></td>
                  </tr>
                  <tr class="text-globales">
                    <td class="fila-det" >
										<% 
										Hashtable htDomicilios = (Hashtable)session.getAttribute("htDomiciliosCliente");
										enu = htDomicilios.keys();
										if(!htDomicilios.isEmpty()){
										%>
					<table width="100%" border="1" cellspacing="0" cellpadding="0">
	                    <tr class="fila-det-bold">
                        <td>Calle</td>
                        <td>Nro</td>
                        <td>Localidad</td>
                        <td>Contacto</td>
                        <td>Tipo Domicilio </td>
                        </tr>
										<%
											while(enu.hasMoreElements()){
												Object [] domicilio = (Object[])htDomicilios.get(enu.nextElement());
											 
										 %>
                        <tr class="fila-det">
                        <td width="26%">&nbsp;<%= domicilio[4] %></td>
                        <td width="9%">&nbsp;<%= domicilio[5] %></td>
                        <td width="23%">&nbsp;<%= domicilio[9] %></td>
                        <td width="17%">&nbsp;<%= domicilio[12] %></td> 
						<td width="25%">&nbsp;<%= domicilio[2] %></td> 
                      </tr>
										<% 
											}										
										 %>											
                    </table>		
										<% 
										}										
										 %>									  </td>
                  </tr>
                </table></td>
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