<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: clientesAnexov
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Jun 22 16:36:57 ART 2007 
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
String titulo = "CLIENTES - ANEXO ";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterClientesAnexov   = null;
int totCol = 24; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCAA"  class="ar.com.syswarp.web.ejb.BeanClientesAnexovAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCAA" property="*" />
<%
 BCAA.setResponse(response);
 BCAA.setRequest(request);
 BCAA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCAA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <link rel="stylesheet" href="../imagenes/default/erp-style.css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
 function bajarDatos(cuit, razon, idtipoiva, sucursal, compr_an, compro_has,  postal, idlocalidad, idprovincia, tipom_an, inter_an,impor_an, fecha_an, com_venta, com_cobra, com_vende, idmoneda, cambio_an, form_an, tipomovs_an, anulada, domicilio, idexpreso, idempresa, localidad, provincia, moneda, tipoiva, expreso){
 
 /*
cav.cuit_an,cav.razon_an,iva_an,cav.sucur_an,cav.compr_an,cav.compro_has,cav.postal,cav.loca_an,cav.pcia_an,
cav.tipom_an,cav.inter_an,cav.impor_an,cav.fcha_an,cav.com_venta,cav.com_cobra,cav.com_vende,cav.moneda_an,
cav.cambio_an,cav.form_an,cav.tipmovs_an,cav.anulada,cav.domici_an,cav.expreso_an,cav.idempresa,
gl.localidad,gp.provincia,gm.moneda,cti.tipoiva,ce.expreso,
cav.usuarioalt,cav.usuarioact,cav.fechaalt,cav.fechaact  
 */
    var j = opener.document.forms.length;
	for(var i = 0; i<j;i++){
	    //alert(j);
		if( opener.document.forms[i].idcliente  ){
            //opener.document.forms[i].idcliente.value = idcliente;
			if( opener.document.forms[i].cliente  )
			  opener.document.forms[i].cliente.value = razon; 
			if( opener.document.forms[i].idmoneda  )
 			  opener.document.forms[i].idmoneda.value = idmoneda; 

			if( opener.document.forms[i].cuit  )
 			  opener.document.forms[i].cuit.value = cuit; 
			else if( opener.document.forms[i].nrodocumento  )
 			  opener.document.forms[i].nrodocumento.value = cuit;   
			  
			if( opener.document.forms[i].domicilio  )
 			  opener.document.forms[i].domicilio.value = domicilio; 
			if( opener.document.forms[i].idlocalidad  )
 			  opener.document.forms[i].idlocalidad.value = idlocalidad; 
			if( opener.document.forms[i].localidad  )
 			  opener.document.forms[i].localidad.value = localidad; 
			if( opener.document.forms[i].idprovincia  )
 			  opener.document.forms[i].idprovincia.value = idprovincia; 
			if( opener.document.forms[i].provincia  )
 			  opener.document.forms[i].provincia.value = provincia; 
			if( opener.document.forms[i].postal  )
 			  opener.document.forms[i].postal.value = postal; 			  			  
			  			  			  
			if( opener.document.forms[i].moneda  )
			  opener.document.forms[i].moneda.value = moneda;
			if( opener.document.forms[i].idtipoiva  )
			  opener.document.forms[i].idtipoiva.value = idtipoiva; 
			if( opener.document.forms[i].tipoiva  )
			  opener.document.forms[i].tipoiva.value = tipoiva;
			  
			opener.document.forms[i].submit();  
			  
			break;			
		}
	}
	
	
    close(); 
 }
 </script>
 
</head>
<%
// titulos para las columnas
tituCol[0] = "Cuit";
tituCol[1] = "Razon";
tituCol[2] = "IVA";
tituCol[3] = "Sucursal";
tituCol[4] = "Compr.";
tituCol[5] = "compro_has";
tituCol[6] = "postal";
tituCol[7] = "loca_an";
tituCol[8] = "pcia_an";
tituCol[9] = "tipom_an";
tituCol[10] = "inter_an";
tituCol[11] = "impor_an";
tituCol[12] = "fcha_an";
tituCol[13] = "com_venta";
tituCol[14] = "com_cobra";
tituCol[15] = "com_vende";
tituCol[16] = "moneda_an";
tituCol[17] = "cambio_an";
tituCol[18] = "form_an";
tituCol[19] = "tipmovs_an";
tituCol[20] = "anulada";
tituCol[21] = "domici_an";
tituCol[22] = "expreso_an";
tituCol[23] = "idempresa";
java.util.List ClientesAnexov = new java.util.ArrayList();
ClientesAnexov= BCAA.getClientesAnexovList();
iterClientesAnexov = ClientesAnexov.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_clientesAnexov.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="4%" height="38">&nbsp;</td>
                   <td width="96%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BCAA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BCAA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BCAA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCAA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCAA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BCAA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BCAA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCAA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="37%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="18%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
    </tr>
   <%int r = 0;
   while(iterClientesAnexov.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterClientesAnexov.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
	  
	  String param = "";
	  for(int h=0;h<29;h++){
	    param += "'" + sCampos[h] + "',";
	  }
	  param = param.substring(0, param.lastIndexOf(","));
/*
cav.cuit_an, cav.razon_an, iva_an,cav.sucur_an, cav.compr_an, cav.compro_has, cav.postal, cav.loca_an,cav.pcia_an,
cav.tipom_an,cav.inter_an,cav.impor_an,cav.fcha_an,cav.com_venta,cav.com_cobra,cav.com_vende,cav.moneda_an,
cav.cambio_an,cav.form_an,cav.tipmovs_an,cav.anulada,cav.domici_an,cav.expreso_an,cav.idempresa,
gl.localidad,gp.provincia,gm.moneda,cti.tipoiva,ce.expreso
*/
	  
	  
	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/down.png" width="18" height="18" style="cursor:pointer" onClick="bajarDatos(<%= param %>);"></td>
      <td class="fila-det-border" ><%=str.esNulo(sCampos[0])%>&nbsp;</td>
      <td class="fila-det-border" ><%=str.esNulo(sCampos[1])%>&nbsp;</td>
      <td class="fila-det-border" ><%=str.esNulo(sCampos[2])%>&nbsp;</td>
      <td class="fila-det-border" ><%=str.esNulo(sCampos[3])%>&nbsp;</td>
      <td class="fila-det-border" ><%=str.esNulo(sCampos[4])%>&nbsp;</td>
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

