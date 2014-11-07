<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: Lov Clientes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Enero 02 16:04 2007 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Clientes";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterclientesclientes   = null;
int totCol = 18; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BGLA"  class="ar.com.syswarp.web.ejb.BeanClientesLovIngMov"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BGLA" property="*" />
<%
 BGLA.setResponse(response);
 BGLA.setRequest(request);
 BGLA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BGLA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script >
 
function bajarDatos(idcliente, cliente, idcondicion, condicion, idvendedor, vendedor, idlista, descri_lis, idmoneda, moneda, idtipoiva, tipoiva, idlocalidad, localidad, postal, idprovincia, provincia, idexpreso, expreso, cuit, domicilio){
/*
---- NUEVO ----
cl.idcliente, cl.razon, cl.idcondicion, cc.condicion, gtd.tipodocumento, cl.nrodocumento, 
cs.idlista, cs.descri_lis, gm.idmoneda, gm.moneda, cti.idtipoiva, cti.tipoiva 

*/


/*
---- VIEJO ----
idcliente, cliente, idcondicion, condicion, idvendedor, vendedor, idlista, descri_lis, idmoneda, moneda, idtipoiva, tipoiva,   
idlocalidad, localidad, postal, idprovincia, provincia, idexpreso, expreso, cuit, direccion   
*/
    // setValoresTipocliente(codigo, cliente, cuit, tipoiva, domicilio, cp, provincia, localidad)
	opener.setValoresTipocliente(idcliente, cliente, cuit, tipoiva, domicilio, postal, provincia, localidad);
    var j = opener.document.forms.length;
	for(var i = 0; i<j;i++){
	    //alert(j);
		if( opener.document.forms[i].idcliente  ){
            opener.document.forms[i].idcliente.value = idcliente;
			if( opener.document.forms[i].cliente  )
			  opener.document.forms[i].cliente.value = cliente; 
			if( opener.document.forms[i].idcondicion  )
			  opener.document.forms[i].idcondicion.value = idcondicion;
			if( opener.document.forms[i].condicion  )
			  opener.document.forms[i].condicion.value = condicion; 
			if( opener.document.forms[i].idvendedor  )
			  opener.document.forms[i].idvendedor.value = idvendedor; 
			if( opener.document.forms[i].vendedor  )
			  opener.document.forms[i].vendedor.value = vendedor; 
			if( opener.document.forms[i].idlista  )
			  opener.document.forms[i].idlista.value = idlista; 
			if( opener.document.forms[i].lista  )
			  opener.document.forms[i].lista.value = lista; 
			if( opener.document.forms[i].idmoneda  )
 			  opener.document.forms[i].idmoneda.value = idmoneda; 
			if( opener.document.forms[i].moneda  )
			  opener.document.forms[i].moneda.value = moneda;
			if( opener.document.forms[i].idtipoiva  )
			  opener.document.forms[i].idtipoiva.value = idtipoiva; 
			if( opener.document.forms[i].tipoiva  )
			  opener.document.forms[i].tipoiva.value = tipoiva;

			  
			break;			
		}
	}
	window.close();
}

function recargarOpener(){
   // TODO: funcion provisoria, agregar logica en pagina y eliminar....
   //alert("recargarOpener");
   //opener.document.frm.submit();
}

function getDomicilios(idcliente, razon_social){
      document.frm.ocurrencia.value = '';
      document.frm.idcliente.value = idcliente;
      document.frm.razon_social.value = razon_social;
      document.frm.action = 'clientesDomiciliosIngMovLov.jsp';
      document.frm.submit(); 
	   
}


function mostrarMensaje(obs){

overlib( obs , STICKY, CAPTION, '[INFO]',TIMEOUT,25000,HAUTO,VAUTO, LEFT, OFFSETX , 50, WIDTH,350,BGCOLOR,'#FF9900');
}
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Cliente";
tituCol[2] = "Cód. Condicion";
tituCol[3] = "Condicion";
tituCol[4] = "Cód. Vendedor";
tituCol[5] = "Vendedor";
tituCol[6] = "Cód. Lista";
tituCol[7] = "Lista";
tituCol[8] = "Cód. Moneda";
tituCol[9] = "Moneda";
tituCol[10] = "Cód. Iva";
tituCol[11] = "Iva";
tituCol[12] = "C. Loc.";
tituCol[13] = "Loc.";
tituCol[14] = "CP";
tituCol[15] = "C. Prov.";
tituCol[16] = "Prov.";

java.util.List clientesclientes = new java.util.ArrayList();
clientesclientes = BGLA.getClientesclientesList();
iterclientesclientes = clientesclientes.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  onUnload="recargarOpener();">
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_clientes_ingmov.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td colspan="2"  ><%=titulo%></td>
                </tr>
                <tr>
                   <td width="3%" height="38">&nbsp;                  </td>
                   <td width="97%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BGLA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BGLA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BGLA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BGLA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BGLA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BGLA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BGLA.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select> 
                                          </td>
                                          <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                       </tr>
                                    </table>
                                 </td>
                              </tr>
                           </table>
                        </td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BGLA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="43%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="27%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[11]%></td>
     <td width="21%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
	 <td width="2%" ></td>	
   </tr>
   <%int r = 0;
   while(iterclientesclientes.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterclientesclientes.next(); 
      String title = "";
	  String paramJS = "";
	  
	  
	  for(int f=0;f<12;f++){
	    sCampos[f] = str.esNulo(sCampos[f]);
	    title += tituCol[f] + ": "+   sCampos[f] + "<br>";
		//paramJS += "'" + sCampos[f] + "',";
	  }
  	  //paramJS = paramJS.substring(0, paramJS.lastIndexOf(","));
	  
	  
	  //System.out.println("*\n   " + paramJS + "  \n*");
	  // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
	  
	  %>
	  
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" > 
	  <div align="center">
	  <!--img src="../imagenes/default/gnome_tango/actions/forward.png" width="18" height="18" onClick="bajarDatos( < %=sCampos[0]%>, < %=sCampos[0]%>, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19<%//=paramJS%> )"-->
	  <img src="../imagenes/default/gnome_tango/actions/forward.png" width="18" height="18" onClick="getDomicilios( <%=sCampos[0]%>, '<%=sCampos[1]%>' )">	  </div></td>
      <td class="fila-det-border" ><%=sCampos[0]%></td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[11]%>&nbsp;</td> 
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
	  <td class="fila-det-border" >
	    <div align="center"><img src="../imagenes/default/gnome_tango/status/info.png" width="22" height="22" title="<%=title%>" onClick="mostrarMensaje('<%=title%>')"></div></td>	  
   </tr>
<%
   }%>
   </table>

 
   <input name="idcliente" value="" type="hidden">
   <input name="razon_social" value="" type="hidden">
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


