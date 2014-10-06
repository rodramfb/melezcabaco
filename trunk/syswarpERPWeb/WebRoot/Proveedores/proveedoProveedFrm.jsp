<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: proveedoProveed
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jul 05 15:38:22 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ page import="java.math.*" %>
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
<jsp:useBean id="BPPF"  class="ar.com.syswarp.web.ejb.BeanProveedoProveedFrm"   scope="page"/>
<head>
 <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <!-- Copyright 2000, 2001, 2002, 2003 Macromedia, Inc. All rights reserved. -->
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPPF" property="*" />
 <% 
 String titulo = BPPF.getAccion().toUpperCase() + " DE PROVEEDORES" ;
 BPPF.setResponse(response);
 BPPF.setRequest(request);
 BPPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 //BPPF.setEjercicio( new Integer(session.getAttribute("ejercicioActivo").toString() ));
 //BPPF.setEjercicio =  Integer.parseInt( (String)session.getAttribute("ejercicioActivo") );
 BPPF.setEjercicio( new BigDecimal( session.getAttribute("ejercicioActivo").toString() ));
 BPPF.ejecutarValidacion();
 String idretencion1    = str.esNulo( request.getParameter("idretencion1") );
 String d_idretencion1    = str.esNulo( request.getParameter("d_idretencion1") );
 String idretencion2   = str.esNulo( request.getParameter("idretencion2") );
 String d_idretencion2    = str.esNulo( request.getParameter("d_idretencion2") );
 String idretencion3  = str.esNulo( request.getParameter("idretencion3") );
 String d_idretencion3    = str.esNulo( request.getParameter("d_idretencion3") );
 String idretencion4   = str.esNulo( request.getParameter("idretencion4") );
 String d_idretencion4    = str.esNulo( request.getParameter("d_idretencion4") );
 String idretencion5   = str.esNulo( request.getParameter("idretencion5") );
 String d_idretencion5    = str.esNulo( request.getParameter("d_idretencion5") );
 String idprovincia   = str.esNulo( request.getParameter("idprovincia") );
 String provincia    = str.esNulo( request.getParameter("provincia") );
 String idcondicionpago   = str.esNulo( request.getParameter("idcondicionpago") );
 String d_idcondicionpago    = str.esNulo( request.getParameter("d_idcondicionpago") );
 %>
<form action="proveedoProveedFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPPF.getAccion()%>" >

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
                <td colspan="5" class="fila-det-border"><jsp:getProperty name="BPPF" property="mensaje"/>&nbsp;</td>
              </tr>
			  
			  
			<tr class="fila-det">
                <td width="182" class="fila-det-border">Código:  </td>
            <td width="160" class="fila-det-border"><input name="idproveedor" type="text" class="campo" id="idproveedor" value="<%=BPPF.getIdproveedor()%>" size="18" maxlength="7"  <%= !BPPF.getAccion().equalsIgnoreCase("alta") ? "readonly" : "" %> ></td>
                <td width="164" class="fila-det-border">&nbsp;</td>
                <td width="141" class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border">&nbsp;</td>
		          </tr>  
			  
			  
			  
			  
			  
			  
			  
			  
              <tr class="fila-det">
                
            <td width="182" class="fila-det-border">Razón Social: (*) </td>
                <td colspan="2" class="fila-det-border"><input name="razon_social" type="text" value="<%=BPPF.getRazon_social()%>" class="campo" size="35" maxlength="70"  ></td>
                <td class="fila-det-border">Domicilio: (*) </td>
                <td colspan="2" class="fila-det-border"><input name="domicilio" type="text" value="<%=BPPF.getDomicilio()%>" class="campo" size="35" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                
            <td width="182" class="fila-det-border">C&oacute;digo Postal: </td>
                <td colspan="2" class="fila-det-border"><input name="codigo_postal" type="text" value="<%=BPPF.getCodigo_postal()%>" class="campo" size="10" maxlength="10"  ></td>
                <td class="fila-det-border">Localidad: (*) </td>
                <td width="187" class="fila-det-border"><input name="localidad" type="text" class="campo" id="localidad" value="<%=BPPF.getLocalidad()%>" size="30" readonly></td>
                <td width="77" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" title="A" width="18" height="18" style="cursor:pointer" onClick="mostrarLOV('../Proveedores/lov_localidades.jsp')">
                <input name="idlocalidad" type="hidden" id="idlocalidad" value="<%=BPPF.getIdlocalidad()%>"></td>
              </tr>
              <tr class="fila-det">
                
            <td width="182" class="fila-det-border">E-Mail:</td>
                <td colspan="2" class="fila-det-border"><input name="email" type="text" class="campo" id="email" value="<%=BPPF.getEmail()%>" size="30" maxlength="50"  ></td>
                <td class="fila-det-border">Provincia: (*)</td>
                <td colspan="2" class="fila-det-border"><input name="provincia" type="text"  class="campo" id="provincia4" size="30" maxlength="45" readonly value="<%=BPPF.getProvincia()%>">
                  <input name="idprovincia"  type="hidden" id="idprovincia" size="8" maxlength="8"  value="<%=BPPF.getIdprovincia()%>">								</td>
              </tr>
              <tr class="fila-det">
                
            <td width="182" class="fila-det-border">Contacto: </td>
                <td colspan="2" class="fila-det-border"><input name="contacto" type="text" value="<%=BPPF.getContacto()%>" class="campo" size="30" maxlength="30"  ></td>
                <td class="fila-det-border">Nro de IIBB: </td>
                <td colspan="2" class="fila-det-border"><input name="brutos" type="text" value="<%=BPPF.getBrutos()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                
            <td width="182" class="fila-det-border">Tel&eacute;fono: </td>
                <td colspan="2" class="fila-det-border"><input name="telefono" type="text" value="<%=BPPF.getTelefono()%>" class="campo" size="35" maxlength="60"  ></td>
                <td class="fila-det-border">Letra IVA:</td>
                <td colspan="2" class="fila-det-border">
                  <select name="letra_iva" id="letra_iva" class="campo"  >
                    <option value="A" <%=BPPF.getLetra_iva().equalsIgnoreCase("A") ?  "selected" : "" %>>A</option>
                    <option value="B" <%=BPPF.getLetra_iva().equalsIgnoreCase("B") ?  "selected" : "" %>>B</option>
                    <option value="C" <%=BPPF.getLetra_iva().equalsIgnoreCase("C") ?  "selected" : "" %>>C</option>
                    <option value="E" <%=BPPF.getLetra_iva().equalsIgnoreCase("E") ?  "selected" : "" %>>E</option>
                    <option value="M" <%=BPPF.getLetra_iva().equalsIgnoreCase("M") ?  "selected" : "" %>>M</option>
                    <option value="R" <%=BPPF.getLetra_iva().equalsIgnoreCase("R") ?  "selected" : "" %>>R</option>
                  </select>
                </td>
              </tr>
              <tr class="fila-det">
                
            <td width="182" class="fila-det-border">C.U.I.T.: </td>
                <td colspan="2" class="fila-det-border"><input name="cuit" type="text" value="<%=BPPF.getCuit()%>" class="campo" size="18" maxlength="18"  ></td>
                <td class="fila-det-border">Afecta al Stock </td>
                <td colspan="2" class="fila-det-border"><select name="stock_fact" id="stock_fact" class="campo"  >
                  <option value="S" <%= BPPF.getStock_fact().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                  <option value="N" <%= BPPF.getStock_fact().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                </select></td>
              </tr>
              <tr class="fila-det">
                
            <td width="182" class="fila-det-border">Cta Pasivo: (*)            </td>
                
            <td class="fila-det-border"><input name="ctapasivo" type="text" class="campo" id="ctapasivo" value="<%=BPPF.getCtapasivo()%>" readonly="yes"></td>
              <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="mostrarLOV('lov_cuentascontables_ctapasivo.jsp')" style="cursor:pointer"></td>
              <td class="fila-det-border">Retenci&oacute;n 1:</td>
              <td class="fila-det-border"><input name="d_idretencion1" type="text" class="campo" id="d_idretencion1" value="<%=str.esNulo(BPPF.getD_idretencion1())%>" size="30" readonly></td>
              <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="18" height="18" onClick="abrirVentana('../Proveedores/lov_cajaretenciones.jsp?cmpCod=idretencion1&cmpDesc=d_idretencion1', 'retenciones', 750, 400)" style="cursor:pointer">
                <img src="../imagenes/default/gnome_tango/actions/editclear.png" style="cursor:pointer" width="18" height="18" onClick="document.frm.idretencion1.value = document.frm.d_idretencion1.value = ''">
                <input name="idretencion1" type="hidden" id="idretencion1" value="<%=BPPF.getIdretencion1()%>"></td>
              </tr>
							
              <tr class="fila-det">
            <td width="182" class="fila-det-border">Cta contrapartida 1: (*)            </td>   
            <td class="fila-det-border"><input name="ctaactivo1" type="text" class="campo" id="ctaactivo1" value="<%=BPPF.getCtaactivo1()%>" readonly="yes"></td>
              <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="mostrarLOV('lov_cuentascontables.jsp')" style="cursor:pointer"><img src="../imagenes/default/gnome_tango/actions/editclear.png" style="cursor:pointer" width="18" height="18" onClick="document.frm.ctaactivo1.value =  ''"> </td>
              <td class="fila-det-border">Retenci&oacute;n 2:</td>
              <td class="fila-det-border"><input name="d_idretencion2" type="text" class="campo" id="d_idretencion2" value="<%=str.esNulo(BPPF.getD_idretencion2())%>" size="30" readonly></td>
              <td class="fila-det-border">
							<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="18" height="18" onClick="abrirVentana('../Proveedores/lov_cajaretenciones.jsp?cmpCod=idretencion2&cmpDesc=d_idretencion2', 'retenciones', 750, 400)" style="cursor:pointer">
							<img src="../imagenes/default/gnome_tango/actions/editclear.png"  style="cursor:pointer"  width="18" height="18" onClick="document.frm.idretencion2.value = document.frm.d_idretencion2.value = ''">
							<input name="idretencion2" type="hidden" id="idretencion2" value="<%=BPPF.getIdretencion2()%>"></td>
              </tr>
              <tr class="fila-det">
                
            <td width="182" height="28" class="fila-det-border">Cta contrapartida 2: </td>
                
            <td class="fila-det-border"><input name="ctaactivo2" type="text" class="campo" id="ctaactivo2" value="<%=BPPF.getCtaactivo2()%>" readonly="yes"></td>
              <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="mostrarLOV('lov_cuentascontables_ctaactivo2.jsp')" style="cursor:pointer"><img src="../imagenes/default/gnome_tango/actions/editclear.png" style="cursor:pointer" width="18" height="18" onClick="document.frm.ctaactivo2.value =  ''"></td>
              <td class="fila-det-border">Retenci&oacute;n 3:</td>
              <td class="fila-det-border"><input name="d_idretencion3" type="text" class="campo" id="d_idretencion33" value="<%=str.esNulo(BPPF.getD_idretencion3())%>" size="30" readonly></td>
              <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="18" height="18" onClick="abrirVentana('../Proveedores/lov_cajaretenciones.jsp?cmpCod=idretencion3&cmpDesc=d_idretencion3', 'retenciones', 750, 400)" style="cursor:pointer">
                <img src="../imagenes/default/gnome_tango/actions/editclear.png"  style="cursor:pointer" width="18" height="18" onClick="document.frm.idretencion3.value = document.frm.d_idretencion3.value = ''">
                <input name="idretencion3" type="hidden" id="idretencion3" value="<%=BPPF.getIdretencion3()%>"></td>
              </tr>
              <tr class="fila-det">
                
            <td width="182" height="28" class="fila-det-border">Cta contrapartida 3: </td>
                
            <td class="fila-det-border"><input name="ctaactivo3" type="text" class="campo" id="ctaactivo3" value="<%=BPPF.getCtaactivo3()%>" readonly="yes"></td>
              <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="mostrarLOV('lov_cuentascontables_ctaactivo3.jsp')" style="cursor:pointer"><img src="../imagenes/default/gnome_tango/actions/editclear.png" style="cursor:pointer" width="18" height="18" onClick="document.frm.ctaactivo3.value =  ''"></td>
              <td class="fila-det-border">Retenci&oacute;n 4:</td>
              <td class="fila-det-border"><input name="d_idretencion4" type="text" class="campo" id="d_idretencion43" value="<%=str.esNulo(BPPF.getD_idretencion4())%>" size="30" readonly></td>
              <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="18" height="18" onClick="abrirVentana('../Proveedores/lov_cajaretenciones.jsp?cmpCod=idretencion4&cmpDesc=d_idretencion4', 'retenciones', 750, 400)" style="cursor:pointer">
                <img src="../imagenes/default/gnome_tango/actions/editclear.png"  style="cursor:pointer" width="18" height="18" onClick="document.frm.idretencion4.value = document.frm.d_idretencion4.value = ''">
                <input name="idretencion4" type="hidden" id="idretencion4" value="<%=BPPF.getIdretencion4()%>"></td>
              </tr>
              <tr class="fila-det">
                
            <td width="182" height="30" class="fila-det-border">Cta contrapartida 4: </td>
                
            <td class="fila-det-border"><input name="ctaactivo4" type="text" class="campo" id="ctaactivo4" value="<%=BPPF.getCtaactivo4()%>" readonly="yes"></td>
              <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="mostrarLOV('lov_cuentascontables_ctaactivo4.jsp')" style="cursor:pointer"><img src="../imagenes/default/gnome_tango/actions/editclear.png" style="cursor:pointer" width="18" height="18" onClick="document.frm.ctaactivo4.value =  ''"></td>
              <td class="fila-det-border">Retenci&oacute;n 5:</td>
              <td class="fila-det-border"><input name="d_idretencion5" type="text" class="campo" id="d_idretencion5" value="<%=str.esNulo(BPPF.getD_idretencion5())%>" size="30" readonly></td>
              <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="18" height="18" onClick="abrirVentana('../Proveedores/lov_cajaretenciones.jsp?cmpCod=idretencion5&cmpDesc=d_idretencion5', 'retenciones', 750, 400)" style="cursor:pointer">
                <img src="../imagenes/default/gnome_tango/actions/editclear.png"  style="cursor:pointer" width="18" height="18" onClick="document.frm.idretencion5.value = document.frm.d_idretencion5.value = ''">
                <input name="idretencion5" type="hidden" id="idretencion5" value="<%=BPPF.getIdretencion5()%>"></td>
              </tr> 
              
          <tr class="fila-det"> 
            <td width="182" class="fila-det-border">Cta Percepci&oacute;n. IVA: </td>
                
            <td class="fila-det-border"><input name="ctaiva" type="text" class="campo" id="ctaiva" value="<%=BPPF.getCtaiva()%>" readonly="yes"></td>
              <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="mostrarLOV('lov_cuentascontables_ctaiva.jsp')" style="cursor:pointer"><img src="../imagenes/default/gnome_tango/actions/editclear.png" style="cursor:pointer" width="18" height="18" onClick="document.frm.ctaiva.value =  ''"></td>
              <td class="fila-det-border">Ret. de Ganancias:</td>
              <td colspan="2" class="fila-det-border"><select name="ret_gan" id="ret_gan"  class="campo"  >
                <option value="S" <%= BPPF.getRet_gan().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                <option value="N" <%= BPPF.getRet_gan().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
              </select></td>
          </tr>
              <tr class="fila-det">
                
            <td width="182" class="fila-det-border">Cta Retenci&oacute;n. de IVA: </td>
                
            <td class="fila-det-border"><input name="ctaretiva" type="text" class="campo" id="ctaretiva" value="<%=BPPF.getCtaretiva()%>" readonly="yes"></td>
			  

			  
              <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="mostrarLOV('lov_cuentascontables_ctaretiva.jsp')" style="cursor:pointer"><img src="../imagenes/default/gnome_tango/actions/editclear.png" style="cursor:pointer" width="18" height="18" onClick="document.frm.ctaretiva.value =  ''"></td>
              <td class="fila-det-border">Cond. de Pago: (*)</td> 
              <td class="fila-det-border"><input name="d_idcondicionpago" type="text" class="campo" id="d_idcondicionpago2" value="<%=str.esNulo(BPPF.getD_idcondicionpago())%>" size="30" readonly></td>
              <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" title="v" width="18" height="18" style="cursor:pointer" onClick="mostrarLOV('../Proveedores/lov_condpago.jsp')">
                <input name="idcondicionpago" type="hidden" id="idcondicionpago2" value="<%=BPPF.getIdcondicionpago()%>"></td>
              </tr>
              <tr class="fila-det">
                <td width="182" height="34" class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border">&nbsp;</td>
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

