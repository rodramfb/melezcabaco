<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: bacoTmSeleccionSocio
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Nov 14 14:50:18 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Iterator" %>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "GESTION DE TM: Selección de Socios";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
Iterator iterador   = null;
int i = 0;
Iterator iterbacoTmSeleccionSocio   = null;
int totCol = 10; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCA"  class="ar.com.syswarp.web.ejb.BeanBacoTmSeleccionSocio"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCA" property="*" />
<%
 Iterator iter;
 BCA.setResponse(response);
 BCA.setRequest(request);
 BCA.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCA.setIdtelemark( new BigDecimal( session.getAttribute("idusuario").toString() )); 
 BCA.ejecutarValidacion();

%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
  function gestionarCliente(idcampacabe, campacabe, idcliente, cliente){
    document.frm.accion.value = 'recarga';
    document.frm.idcampacabe.value = idcampacabe;
    document.frm.campacabe.value = campacabe;
    document.frm.idcliente.value = idcliente;
    document.frm.cliente.value = cliente;
    document.frm.action = 'pedidos_cabeFrm.jsp' ;
    document.frm.submit();
  }
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "C.Camp.";
tituCol[1] = "Campaña";
tituCol[2] = "C.Clie.";
tituCol[3] = "Cliente";
tituCol[4] = "C.Est.";
tituCol[5] = "Estado";
tituCol[6] = "C.Mot.";
tituCol[7] = "Motivo";
tituCol[8] = "Categoria"; 
tituCol[9] = "Proxima LLamada"; 

java.util.List bacoTmSeleccionSocio = new java.util.ArrayList();
bacoTmSeleccionSocio= BCA.getListSeleccionSocio();
iterbacoTmSeleccionSocio = bacoTmSeleccionSocio.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" > 
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="bacoTmSeleccionSocio.jsp" method="POST" name="frm">


<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="36" colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td width="1%" height="38">&nbsp;</td>
                   <td width="99%">
				   
				   <table width="100%" border="0">
                         <tr>
                           <td width="7%" height="24" class="text-globales">Campa&ntilde;a</td>
                           <td width="21%"><select name="idcampacabe" class="campo" id="idcampacabe" onChange = "borrarcampaniaAnterior();">
                             <option value="-1">Seleccionar</option>
                             <%
                          iterador= BCA.getListCampaniasActivas().iterator(); 
                          while(iterador.hasNext()){
                            String datos[] = (String[]) iterador.next();%>
                             <option value="<%= datos[0] %>" <%= BCA.getIdcampacabe().toString().equals(datos[0]) ?  "selected" : "" %>><%= datos[1] %></option>
                             <%}  %>
                           </select></td>
                           <td width="12%"><span class="fila-det-border">
                             <input name="consulta "
											type="submit" class="boton" id="consulta " value="Agenda del D&iacute;a" onClick="document.frm.accion.value = 'consultauno'">
                           </span></td>
                           <td width="2%">&nbsp;</td>
                           <td width="10%" height="24" class="text-globales">Buscar Cliente </td>
                           <td width="18%"><span class="fila-det-border">
                             <input name="buscarsocio" type="text" class="campo" id="buscarsocio" value="<%=BCA.getBuscarsocio()%>" size="30" maxlength="30"  >
                           </span></td>
                           <td width="30%"><span class="fila-det-border">
                             <input name="buscar"
											type="submit" class="boton" id="buscar" value="Buscar Cliente" onClick="document.frm.accion.value = 'buscar'">
                           </span></td>
                         </tr>
                         <tr>
                           <td height="24" class="text-globales">Resultado</td>
                           <td><span class="fila-det-border">
                             <select name="idresultado" id="idresultado" class="campo" style="width:200px" >
                               <option value="-1">Seleccionar</option>
                               <%
                   iter = BCA.getListidresultado().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                               <option value="<%= datos[0] %>" <%=  BCA.getIdresultado().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                               <% 
                   } %>
                             </select>
                           </span></td>
                           <td><span class="fila-det-border">
                             <input name="consulta2"
											type="submit" class="boton" id="consulta2" value="Filtrar Resultado" onClick="document.frm.accion.value = 'consultados'">
                           </span></td>
                           <td>&nbsp;</td>
                           <td>&nbsp;</td>
                           <td>&nbsp;</td>
                           <td>&nbsp;</td>
                         </tr>
                   </table>
				   
				   
				   
				   
				   
                  </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCA" property="mensaje"/></td>
  </tr>
</table>


<%if (BCA.getAccion().equalsIgnoreCase("consultauno")){%>		
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="49%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Cliente</td>
     <td width="22%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Estado</td>
     <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Motivo</td>
	 <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Categoria</td>
	 <td width="3%" >&nbsp;</td>
  </tr>
   <%int r = 0;
   while(iterbacoTmSeleccionSocio.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterbacoTmSeleccionSocio.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><%=sCampos[2]%> - <%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td> 
	  <td class="fila-det-border" ><%=sCampos[8]%>&nbsp;</td>
	  <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/call-center.gif" title="Gestionar Pedidos Para el Cliente" width="22" height="20" onClick="gestionarCliente(<%=sCampos[0]%>, '<%=sCampos[1]%>', <%=sCampos[2]%>, '<%=sCampos[3]%>');"></td>
   </tr>
<%
   }%>
  </table>
<table width="100%" border="0" cellpadding="1" cellspacing="1" name="rsTable"   >
  <tr class="fila-encabezado">
    <td width="14%">Total Registros </td>
    <td><div align="left"><span class="fila-det-border"><%=r%></span></div></td>
    <td><div align="right"></div></td>
    <td>&nbsp;</td>
  </tr>
</table>
  <%}
    if (BCA.getAccion().equalsIgnoreCase("consultados")){%>	
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="15%" rowspan="2" valign="top" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Cliente</td>
     <td width="7%" rowspan="2" valign="top" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">F. Ingreso</div></td>
	 <td width="25%" rowspan="2" valign="top" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Socio Telefono</td>
	 <td colspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"> <div align="center">Rellamada</div></td>
	 <td width="8%" rowspan="2" valign="top" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Estado</td>
	 <td width="10%" rowspan="2" valign="top" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Motivo</td>
	 <td width="10%" rowspan="2" valign="top" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Observaciones</td>
	 <td width="8%" rowspan="2" valign="top" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Categoria</td>
	 <td width="4%" rowspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"></td>
    </tr>
  <tr class="fila-encabezado">
    <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Fecha</div></td>
    <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Hora</div></td>
  </tr>
   <%int r = 0;
   while(iterbacoTmSeleccionSocio.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterbacoTmSeleccionSocio.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><%=sCampos[2]%> - <%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[4]), "JSTsToStr")%>&nbsp;</div></td>
      <td class="fila-det-border" ><%=sCampos[5]%></td> 
	  <td class="fila-det-border" ><div align="center"><%=Common.setNotNull( sCampos[6] ).equals("") ? "" : Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[6]), "JSTsToStr")%>&nbsp;</div></td>
	  <td class="fila-det-border" ><div align="center"><%=Common.setNotNull( sCampos[6] ).equals("") ? "" : Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[6]), "JSTsToStrOnlyHM")%>&nbsp;</div></td>
	  <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[8]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[9]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[10]%>&nbsp;</td>
      <td width="4%" class="fila-det-border" >&nbsp;<img src="../imagenes/default/gnome_tango/actions/call-center.gif" title="Gestionar Pedidos Para el Cliente" width="22" height="20" onClick="gestionarCliente(<%=sCampos[0]%>, '<%=sCampos[1]%>', <%=sCampos[2]%>, '<%=sCampos[3]%>');"></td>
   </tr>
<%
   }%>
  </table>
<table width="100%" border="0" cellpadding="1" cellspacing="1" name="rsTable"   >
  <tr class="fila-encabezado">
    <td width="14%">Total Registros </td>
    <td><div align="left"><span class="fila-det-border"><%=r%></span></div></td>
    <td><div align="right"></div></td>
    <td>&nbsp;</td>
  </tr>
</table> 
<%}
    if (BCA.getAccion().equalsIgnoreCase("buscar")){%>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="15%" rowspan="2" valign="top" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Cliente</td>
     <td width="7%" rowspan="2" valign="top" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">F. Ingreso</div></td>
	 <td width="25%" rowspan="2" valign="top" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Socio Telefono</td>
	 <td colspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"> <div align="center">Rellamada</div></td>
	 <td width="8%" rowspan="2" valign="top" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Estado</td>
	 <td width="10%" rowspan="2" valign="top" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Motivo</td>
	 <td width="10%" rowspan="2" valign="top" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Observaciones</td>
	 <td width="8%" rowspan="2" valign="top" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Categoria</td>
	 <td width="4%" rowspan="2" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"></td>
    </tr>
  <tr class="fila-encabezado">
    <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Fecha</div></td>
    <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Hora</div></td>
  </tr>
   <%int r = 0;
   while(iterbacoTmSeleccionSocio.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterbacoTmSeleccionSocio.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><%=sCampos[2]%> - <%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="center"><%= Common.setNotNull( sCampos[4] ).equals("") ? "" : Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[4]), "JSTsToStr")%>&nbsp;</div></td>
      <td class="fila-det-border" ><%=Common.setNotNull( sCampos[5] )%>&nbsp;</td> 
	  <td class="fila-det-border" ><div align="center"><%=Common.setNotNull( sCampos[6] ).equals("") ? "" : Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[6]), "JSTsToStr")%>&nbsp;</div></td>
	  <td class="fila-det-border" ><div align="center"><%=Common.setNotNull( sCampos[6] ).equals("") ? "" : Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[6]), "JSTsToStrOnlyHM")%>&nbsp;</div></td>
	  <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[8]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=Common.setNotNull( sCampos[5] ).equals("") ? "PENDIENTE LLAMAR" :  sCampos[9]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[10]%>&nbsp;</td>
      <td width="4%" class="fila-det-border" >&nbsp;<img src="../imagenes/default/gnome_tango/actions/call-center.gif" title="Gestionar Pedidos Para el Cliente" width="22" height="20" onClick="gestionarCliente(<%=sCampos[0]%>, '<%=sCampos[1]%>', <%=sCampos[2]%>, '<%=sCampos[3]%>');"></td>
   </tr>
<%
   }%>
  </table>
<table width="100%" border="0" cellpadding="1" cellspacing="1" name="rsTable"   >
  <tr class="fila-encabezado">
    <td width="14%">Total Registros </td>
    <td><div align="left"><span class="fila-det-border"><%=r%></span></div></td>
    <td><div align="right"></div></td>
    <td>&nbsp;</td>
  </tr>
</table>  
<%}%> 

 
   <input name="accion" value="" type="hidden">
   <input name="accionGTM" type="hidden" id="accionGTM" value="<%= BCA.getAccion() %>">
   <input name="primeraCarga" value="false" type="hidden">
   <input name="idcliente" value="" type="hidden">
   <input name="cliente" value="" type="hidden">
   <input name="idcampacabe" value="" type="hidden">
   <input name="campacabe" value="" type="hidden">
   <input name="buscarsocio" value="" type="hidden">
   <input name="origenpedido" value="TM" type="hidden">
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

