<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: pedidosDomiciliosEntrega
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Nov 13 10:00:09 GMT-03:00 2009 
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
String titulo = "DOMICILIOS DE ENTREGA";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterPedidosDomiciliosEntrega   = null;
int totCol = 24; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPDEA"  class="ar.com.syswarp.web.ejb.BeanPedidosDomiciliosEntregaLov" scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPDEA" property="*" />
<%
 BPDEA.setResponse(response);
 BPDEA.setRequest(request);
 BPDEA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));   
 BPDEA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
  function callOverlib(leyenda){
  //  overlib(leyenda, STICKY, CAPTION, 'MAS INFO',TIMEOUT,5000,HAUTO,VAUTO,WIDTH,350,BGCOLOR, '#DBDEEE', CAPCOLOR, '#FF0000');
    overlib(leyenda, STICKY, CAPTION, 'MAS INFO',TIMEOUT,5000,HAUTO,FIXY ,0 ,WIDTH,350,BGCOLOR, '#9999CC', CAPCOLOR, '#FF0000'); 
  }
  

/*
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "IdCliente";
tituCol[2] = "Dirección";
tituCol[3] = "Nro";
tituCol[4] = "Piso";
tituCol[5] = "Depto.";
tituCol[6] = "CPA";
tituCol[7] = "CP";
tituCol[8] = "Contacto";
tituCol[9] = "Cargo";
tituCol[10] = "TE";
tituCol[11] = "CEL";
tituCol[12] = "FAX";
tituCol[13] = "WEB";
tituCol[14] = "IdAnexoLocalidad";
tituCol[15] = "idcobrador";
tituCol[16] = "IdExpreso";
tituCol[17] = "Expreso";
tituCol[18] = "IdZona";
tituCol[19] = "Zona";
tituCol[20] = "IdDocalidad";
tituCol[21] = "Localidad";
tituCol[22] = "IdProvincia";
tituCol[23] = "Provincia";
*/


  function bajarDatos(idsucuclie, idcliente, calle, nro, piso, depto, cpa, 
                      postal, contacto, cargocontacto, telefonos, 
                      celular, fax, web, idanexolocalidad, idcobrador, 
                      idexpreso, expreso, idzona, zona, idlocalidad, localidad, idprovincia, provincia){

		if(opener.document.frm.idsucuclie)opener.document.frm.idsucuclie.value = idsucuclie;
    // --		
		if(opener.document.frm.idlocalidad)opener.document.frm.idlocalidad.value = idlocalidad;
		if(opener.document.frm.localidad)opener.document.frm.localidad.value = localidad;
		if(opener.document.frm.idprovincia)opener.document.frm.idprovincia.value = idprovincia;
		if(opener.document.frm.provincia)opener.document.frm.provincia.value = provincia;
    // --		
		if(opener.document.frm.calle)opener.document.frm.calle.value = calle;
		if(opener.document.frm.nro)opener.document.frm.nro.value = nro;
		if(opener.document.frm.piso)opener.document.frm.piso.value = piso;
		if(opener.document.frm.depto)opener.document.frm.depto.value = depto;
		if(opener.document.frm.cpa)opener.document.frm.cpa.value = cpa;
		if(opener.document.frm.postal)opener.document.frm.postal.value = postal;
		if(opener.document.frm.contacto)opener.document.frm.contacto.value = contacto;
		if(opener.document.frm.cargocontacto)opener.document.frm.cargocontacto.value = cargocontacto;
		if(opener.document.frm.telefonos)opener.document.frm.telefonos.value = telefonos;
		if(opener.document.frm.celular)opener.document.frm.celular.value = celular;
		if(opener.document.frm.fax)opener.document.frm.fax.value = fax;
		if(opener.document.frm.web)opener.document.frm.web.value = web;
		// --
		if(opener.document.frm.idexpreso)opener.document.frm.idexpreso.value = idexpreso;
		if(opener.document.frm.idzona)opener.document.frm.idzona.value = idzona;
		if(opener.document.frm.idanexolocalidad)opener.document.frm.idanexolocalidad.value = idanexolocalidad;
   
    this.close();
  
  }

 </script>
</head>
<%



// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "IdCliente";
tituCol[2] = "Dirección";
tituCol[3] = "Nro";
tituCol[4] = "Piso";
tituCol[5] = "Depto.";
tituCol[6] = "CPA";
tituCol[7] = "CP";
tituCol[8] = "Contacto";
tituCol[9] = "Cargo";
tituCol[10] = "TE";
tituCol[11] = "CEL";
tituCol[12] = "FAX";
tituCol[13] = "WEB";
tituCol[14] = "IdAnexoLocalidad";
tituCol[15] = "idcobrador";

/*

+ "       dm.iddomicilio,dm.idcliente,dm.calle,dm.nro,dm.piso,dm.depto,"
+ "       dm.cpa,dm.postal,dm.contacto,dm.cargocontacto,dm.telefonos,dm.celular,"
+ "       dm.fax,dm.web,dm.idanexolocalidad,dm.idcobrador, "
+ "       ex.idexpreso, ex.expreso, zo.idzona, zo.zona,"
+ "       lo.idlocalidad, lo.localidad, pv.idprovincia, pv.provincia,"

*/

tituCol[16] = "IdExpreso";
tituCol[17] = "Expreso";
tituCol[18] = "IdZona";
tituCol[19] = "Zona";
tituCol[20] = "IdDocalidad";
tituCol[21] = "Localidad";
tituCol[22] = "IdProvincia";
tituCol[23] = "Provincia";

java.util.List PedidosDomiciliosEntrega = new java.util.ArrayList();
PedidosDomiciliosEntrega= BPDEA.getPedidosDomiciliosEntregaList();
iterPedidosDomiciliosEntrega = PedidosDomiciliosEntrega.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_pedidosDomiciliosEntrega.jsp" method="POST" name="frm">
<input name="idcliente" type="hidden" value="<%=BPDEA.getIdcliente()%>" >
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="33" colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="11%" height="38">&nbsp;</td>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BPDEA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BPDEA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BPDEA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BPDEA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BPDEA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BPDEA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BPDEA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BPDEA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" >&nbsp;</td>
     <td width="3%" >&nbsp;</td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="44%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="22%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[21]%></td>
     <td width="19%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[10]%></td>
    </tr>
   <%int r = 0;
   Hashtable htRestringido = new Hashtable();
   htRestringido.put("15", "15");
   while(iterPedidosDomiciliosEntrega.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterPedidosDomiciliosEntrega.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";

      String masInfo = "";
      String paramJS= "";
      for(int m=0;m<tituCol.length; m++) {
        if(m<24) paramJS+= "'" + Common.setNotNull(sCampos[m]).replaceAll("\n", "").trim() + (m != 23 ? "', " : "'") ; 
        if(htRestringido.containsKey(m + "")) continue;
        masInfo += "<strong>" + tituCol[m] + ": </strong>" + Common.setNotNull(sCampos[m]) + "<br>";
      }

%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td rowspan="2" class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/actions/down.png" width="18" height="18" onClick="bajarDatos(<%= paramJS %>)"></div></td>
      <td rowspan="2" class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/status/dialog-information.png" width="18" height="18" onClick="callOverlib('<%=masInfo%>')"  title="Click para ver más info."></div></td>
      <td rowspan="2" class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="<%=color_fondo%>" ><%=sCampos[2]%>&nbsp;</td>
      <td rowspan="2" class="fila-det-border" ><%=sCampos[21]%>&nbsp;</td>
      <td rowspan="2" class="fila-det-border" ><%=sCampos[8]%>&nbsp;</td>
      <td rowspan="2" class="fila-det-border" ><%=sCampos[10]%>&nbsp;</td>
    </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" >[<%=tituCol[3]%> : <%=sCampos[3]%>] - [<%=tituCol[4]%> : <%=sCampos[4]%>] - [<%=tituCol[5]%> : <%=sCampos[5]%>] - [<%=tituCol[6]%> : <%=sCampos[6]%>] - [<%=tituCol[7]%> : <%=sCampos[7]%>]</td>
   </tr>
<%
   }%>
   </table>
   <input name="accion" value="" type="hidden">
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

