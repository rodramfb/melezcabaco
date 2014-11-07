<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: clientesDomicilios
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Feb 25 11:28:12 ART 2008 
   Observaciones: 
      . 


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%> 
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterClientesDomicilios   = null;
int totCol = 28; // cantidad de columnas 
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCDA"  class="ar.com.syswarp.web.ejb.BeanClientesDomiciliosLov"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCDA" property="*" />
<%
 String titulo = "DOMICILIOS CLIENTE: " + BCDA.getRazon_social();
 BCDA.setResponse(response);
 BCDA.setRequest(request);
 BCDA.setIdempresa(new BigDecimal(session.getAttribute("empresa").toString()));
 BCDA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="scripts/forms.js"></script>
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>   
 <script>
	function bajarDatos(idcliente, iddomicilio, idtipoiva ){
		
		for(var i = 0; i<10;i++)
			if(opener.document.forms[i].idcliente)
				break;
	
		opener.document.forms[i].idcliente.value = idcliente; 
		opener.document.forms[i].iddomicilio.value = iddomicilio; 
		opener.document.forms[i].idtipoiva.value = idtipoiva; 
		if( opener.document.forms[i].recarga  )
		  opener.document.forms[i].recarga.value = 'true';

 	    opener.document.frm.submit();
		
		window.close();
	}
	
 </script> 
 
</head>
<%

/*
d.iddomicilio,d.idcliente, t.idtipodomicilio, t.tipodomicilio, d.esdefault,d.calle,d.nro,d.piso,d.depto,d.idlocalidad,l.localidad, 
d.cpa,d.postal,d.contacto,d.cargocontacto,d.telefonos,d.celular,d.fax,d.web,d.idzona,z.zona, 
d.idexpreso,e.expreso,d.idcobrador,c.cobrador,p.idprovincia, p.provincia, cl.idtipoiva, 
d.idempresa,d.usuarioalt,d.usuarioact,d.fechaalt,d.fechaact  

*/
// titulos para las columnas
tituCol[0] = "C.Dom.";
tituCol[1] = "IdCliente";
tituCol[2] = "IdTipodomicilio";
tituCol[3] = "Tipo";
tituCol[4] = "Principal";
tituCol[5] = "Calle.";
tituCol[6] = "Nro.";
tituCol[7] = "Piso";
tituCol[8] = "Depto";
tituCol[9] = "Idlocalidad";
tituCol[10] = "Localidad";
tituCol[11] = "CPA";
tituCol[12] = "CP";
tituCol[13] = "Contacto";
tituCol[14] = "Cargo Contacto";
tituCol[15] = "TE";
tituCol[16] = "CEL";
tituCol[17] = "Fax";
tituCol[18] = "Web";
tituCol[19] = "idzona";
//tituCol[20] = "Zona";
tituCol[20] = "Dist.";
tituCol[21] = "IdExpreso";
//tituCol[22] = "Expreso";
tituCol[22] = "Zona";
tituCol[23] = "IdCobrador";
tituCol[24] = "Cobrador";
tituCol[25] = "IdProvincia";
tituCol[26] = "Provincia";
tituCol[27] = "idtipoiva";

//1, razonsocial,7, 8, 3 4 5 6, 10, 22, 23, '', ''

java.util.List ClientesDomicilios = new java.util.ArrayList();
ClientesDomicilios= BCDA.getClientesDomiciliosList();
iterClientesDomicilios = ClientesDomicilios.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="clientesDomiciliosIngMovLov.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td><%=titulo%></td>
                </tr>
                <tr>
                  <td width="89%" height="38">
                     <table width="100%" border="0">
                        <tr>
                          <td width="6%" height="26" class="text-globales">Buscar</td>
                          <td width="22%">
                             <input name="ocurrencia" type="text" value="<%=BCDA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                          <td width="72%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="1%" height="19">&nbsp; </td>
                                         <td width="23%">&nbsp;Total de registros:&nbsp;<%=BCDA.getTotalRegistros()%></td>
                                         <td width="11%" >Visualizar:</td>
                                         <td width="11%">
                                            <select name="limit" >
                                               <%for(i=15; i<= 150 ; i+=15){%>
                                                   <%if(i==BCDA.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                   <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                   <%}
                                                      if( i >= BCDA.getTotalRegistros() ) break;
                                                    %>
                                               <%}%>
                                               <option value="<%= BCDA.getTotalRegistros()%>">Todos</option>
                                            </select>                                          </td>
                                         <td width="7%">&nbsp;P&aacute;gina:</td>
                                         <td width="12%">
                                            <select name="paginaSeleccion" >
                                               <%for(i=1; i<= BCDA.getTotalPaginas(); i++){%>
                                                   <%if ( i==BCDA.getPaginaSeleccion() ){%>
                                                      <option value="<%=i%>" selected><%=i%></option> 
                                                   <%}else{%>
                                                      <option value="<%=i%>"><%=i%></option>
                                                   <%}%>
                                               <%}%>
                                            </select>                                          </td>
                                         <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                      </tr>
                                   </table>                                 </td>
                              </tr>
                          </table>                        </td>
                     </tr>
                   </table>                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCDA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="3%" ><%=tituCol[4]%></td>
     <td width="53%" ><%=tituCol[5]%></td>
     <td width="29%" ><%=tituCol[6]%></td>
     <td width="9%" ><%=tituCol[8]%></td>
     <td width="4%" >&nbsp;</td>
  </tr>
   <%int r = 0;
   while(iterClientesDomicilios.hasNext()){
      String data = "";
      ++r;
      String[] sCampos = (String[]) iterClientesDomicilios.next(); 
	  for(int k=7;k<27;k++){
	    data += tituCol[k] + ": " +  sCampos[k] + "<br>"; 
	  }
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" >
			<div align="center">
			<img src="../imagenes/default/gnome_tango/actions/down.png" width="18" height="18" onClick="bajarDatos( <%=sCampos[1]%> ,<%=sCampos[0]%>,<%=sCampos[27]%>);">			</div>			</td> 
      <td class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[6]%>&nbsp;</td>
       <td class="fila-det-border" ><%=sCampos[8]%>&nbsp;</td>
       <td class="fila-det-border" >
			<a href="javascript:void(0);" onMouseOver="return overlib('<%= data %>', STICKY, CAPTION, '<%=BCDA.getRazon_social()%>',  LEFT, OFFSETX, 0, OFFSETY, 0, DELAY, 5 )" onMouseOut="nd();"> 
			<img src="../imagenes/default/gnome_tango/status/dialog-information.png" width="22" height="22" style="cursor:pointer" title="Previsualizar" border="0">
			</a>	   
	   <!--img src="../imagenes/default/gnome_tango/status/dialog-information.png" width="22" height="22" title="< %= data %>"-->
	   </td>
   </tr>
<%
   }%>
   </table>
   <input name="accion" value="" type="hidden">
   <input name="idcliente" value="<%=BCDA.getIdcliente()%>" type="hidden">
   <input name="razon_social" value="<%=BCDA.getRazon_social()%>" type="hidden">	 
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


