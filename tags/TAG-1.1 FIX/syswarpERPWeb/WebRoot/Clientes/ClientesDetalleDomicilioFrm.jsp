<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesClieSuc
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Feb 07 10:47:33 GMT-03:00 2007 
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
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCCSF"  class="ar.com.syswarp.web.ejb.BeanClientesDetalleDomicilioFrm"   scope="page"/>
<head>
 <title>FRMClientesClieSuc.jsp</title>
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
 <jsp:setProperty name="BCCSF" property="*" />
 <% 
   String idcliente =  request.getParameter("idcliente");
  String cliente =  request.getParameter("cliente");
 if(idcliente!=null){
   session.setAttribute("idcliente",idcliente);
   session.setAttribute("cliente",cliente);
 } 
idcliente =  session.getAttribute("idcliente").toString();
cliente     =  session.getAttribute("cliente").toString();
String titulo = BCCSF.getAccion().toUpperCase() + " DE Sucursales" ;



//BCCSF.setIdcliente(new BigDecimal(idcliente));

 
 
 
 BCCSF.setResponse(response);
 BCCSF.setRequest(request);
 BCCSF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCCSF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCCSF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCCSF.ejecutarValidacion();
 %>
<form action="ClientesDetalleDomicilioFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCCSF.getAccion()%>" >
<input name="idsucursal" type="hidden" value="<%=BCCSF.getIdsucursal()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BCCSF" property="mensaje"/>&nbsp;</td>
              </tr>
			  
			  
             <input name="cliente" type="hidden" id="cliente" value="<%=BCCSF.getCliente()%>" size="30" readonly>
			  <input name="idcliente" type="hidden" id="idcliente" value="<%=BCCSF.getIdcliente()%>">
			  
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Tipo Sucursal: (*) </td>
                <td width="86%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="tiposucursal" type="text" class="campo" id="tiposucursal" value="<%=BCCSF.getTiposucursal()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_tiposucursal.jsp', 700, 400)" style="cursor:pointer"></td>
                    <input name="idtiposucursal" type="hidden" id="idtiposucursal" value="<%=BCCSF.getIdtiposucursal()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Nombre Sucursal :  </td>
                <td width="86%" class="fila-det-border">&nbsp;<input name="nombre_suc" type="text" value="<%=BCCSF.getNombre_suc()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Domicilio Sucursal:  </td>
                <td width="86%" class="fila-det-border">&nbsp;<input name="domici_suc" type="text" value="<%=BCCSF.getDomici_suc()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Telefono Sucursal :  </td>
                <td width="86%" class="fila-det-border">&nbsp;<input name="telefo_suc" type="text" value="<%=BCCSF.getTelefo_suc()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Localidad:  </td>
                <td width="86%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" >                        <input name="localidad" type="text" class="campo" id="localidad" value="<%=BCCSF.getLocalidad()%>" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_localidades2.jsp', 700, 400)" style="cursor:pointer"></td>
                      <input name="idlocalidad" type="hidden" id="idlocalidad" value="<%=BCCSF.getIdlocalidad()%>">
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Vendedor:  </td>
                <td width="86%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" >
                        <input name="vendedor" type="text" class="campo" id="vendedor" value="<%=BCCSF.getVendedor()%>" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_vendedor.jsp', 700, 400)" style="cursor:pointer"></td>
                      <input name="idvendedor" type="hidden" id="idvendedor" value="<%=BCCSF.getIdvendedor()%>">
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Cobrador:  </td>
                <td width="86%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="cobrador" type="text" class="campo" id="cobrador" value="<%=BCCSF.getCobrador()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_cobrador.jsp', 700, 400)" style="cursor:pointer"></td>
                    <input name="idcobrador" type="hidden" id="idcobrador" value="<%=BCCSF.getIdcobrador()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Zona:  </td>
                <td width="86%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" >
                        <input name="expreso" type="text" class="campo" id="expreso" value="<%=BCCSF.getExpreso()%>" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_expreso.jsp', 700, 400)" style="cursor:pointer"></td>
                      <input name="idexpreso" type="hidden" id="idexpreso" value="<%=BCCSF.getIdexpreso()%>">
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

