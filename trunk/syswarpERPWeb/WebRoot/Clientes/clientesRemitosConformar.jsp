<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: Remitos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Nov 06 11:36:23 GMT-03:00 2009 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%--  --%>
<%@ page import="ar.com.syswarp.ejb.*" %> 
<%@ page import="java.sql.*" %> 


<%
try{



// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "CONFORMACION DE REMITOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
int r = 0;
Iterator iterRemitos   = null;
int totCol = 12; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
Iterator iter;
%>
<html>
<jsp:useBean id="BCRC"  class="ar.com.syswarp.web.ejb.BeanClientesRemitosConformar"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCRC" property="*" />
<%
 BCRC.setIdcontadorcomprobante( new BigDecimal( session.getAttribute("idcontadorremitos1").toString() ));   
 BCRC.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCRC.setUsuarioalt(usuario); 
 BCRC.setResponse(response); 
 BCRC.setRequest(request);
 BCRC.ejecutarValidacion();
%>

<% 
String msjLocal = "";
boolean habilitadoPreconf = true;
Properties props = new Properties();
props.load(ClientesBean.class.getResourceAsStream("system.properties"));
// ---------------------------------------------------------------------	
/*
------>
------>
------>
- 20111207 - Mantis 717
- Este código es un salvoconducto provisorio, el cual debe sacarse en cuanto se implementen los cambios pendientes de prueba
*/	

String clase = props.getProperty("conn.clase").trim();
String url = props.getProperty("conn.url").trim();
String dbuser = props.getProperty("conn.usuario").trim();
String clave = props.getProperty("conn.clave").trim();
Class.forName(clase);
Connection conexion = DriverManager.getConnection(url, dbuser, clave);			
String cQuery = "SELECT idusuario "
			+  "  FROM globalusuariosgrupos "
			+  " WHERE idgrupo = 33 "
			+  "   AND idusuario = " + session.getAttribute("idusuario");

Statement statement = conexion.createStatement();
ResultSet rsSalida = statement.executeQuery(cQuery);
if (rsSalida != null) {
  if (rsSalida.next()) {
    habilitadoPreconf = false;
  }
} 
else {
  msjLocal = "No Fue Posible Verificar Si Usuario: " + session.getAttribute("idusuario") + " esta autorizado a preconformar."; 
  System.out.println(msjLocal  );
  habilitadoPreconf = false;
}

//
// ---------------------------------------------------------------------
//

if(rsSalida != null ) rsSalida.close();
if(conexion != null ) conexion.close();

/*
<------
<------
<------
*/


/*
Enumeration en = session.getAttributeNames();
while(en.hasMoreElements()){
  String k = en.nextElement() + "" ;
  System.out.println("clave: " + k + " /  valor: " + session.getAttribute(k) );
}
*/
%>

<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">


 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>

 
function enviarFrm(accion){
    if(accion == 'anular'){
      if(!confirm('Confirma la anulación de los remitos implicados? ')){
        return true;
      }
    }
    document.frm.accion.value = accion;
    document.frm.submit();
}

function callCambiarEstado(){ 
  if(confirm('Confirma cambio de estado?'))
    enviarFrm('cambiarestado');

}

 </script>
</head>
<%
/*
pd.idpedido_cabe, pd.idremitocliente, cr.nrosucursal, cr.nroremitocliente, 
cr.idctrlremito, pc.idcliente, pc.idsucuclie, cr.nrohojaarmado, nrohojarutafinal
*/
// titulos para las columnas 
tituCol[0] = " ";
tituCol[1] = "Sucursal"; 
tituCol[2] = "Comprobante";
tituCol[3] = "idzona";
tituCol[4] = "C.Estado";
tituCol[5] = "Estado";
tituCol[6] = "Procesado";
tituCol[7] = "Nro.HA";
tituCol[8] = "Nro. HRF";
tituCol[9] = "Origen";
tituCol[10] = "";
tituCol[11] = "";

%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="clientesRemitosConformar.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales"> 
    <td  height="24" colspan="10" align="center">
            <table width="98%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                   <td height="31"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="1%" height="38"><table width="100%" border="0" class="text-globales">
                    <tr>
                      <td height="26">Remito: (*)</td>
                      <td><input name="nrosucursal" type="text" value="<%=Common.strZero(BCRC.getNrosucursal(), 4)%>" id="nrosucursal" size="5" maxlength="4" class="campo"  style="text-align:right">
                          <input name="nroremitocliente" type="text" value="<%=Common.strZero(BCRC.getNroremitocliente(), 8)%>" id="nroremitocliente" size="10" maxlength="8" class="campo" style="text-align:right"></td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td width="10%" height="26" class="text-globales"><p>Zona: (*)</p></td>
                      <td width="25%">
					  
					  <% 
					  if(BCRC.getZonaUsuario().longValue() == 0){ %>
					  <select name="idzona" id="idzona" class="campo" style="width:50%" onChange="document.frm.submit();">
                        <option value="">Seleccionar</option>
                        <%
						iter = BCRC.getListZonas().iterator();
						while(iter.hasNext()){
						String [] datos = (String[])iter.next();%>
                        <option value="<%= datos[0] %>" <%= datos[0].equals( BCRC.getIdzona().toString()) ? "selected" : "" %>><%= datos[1] %></option>
                        <%  
						}%>
                      </select>
					  <%
					  }
					  else if(BCRC.getZonaUsuario().longValue() > 0){ %>					  
				  
						<input name="zona" type="text" value="<%=BCRC.getZona()%>" id="zona" size="30" maxlength="100" class="campo" readonly>
						<input name="idzona" type="hidden" value="<%=BCRC.getIdzona()%>" id="idzona" size="30" maxlength="100" class="campo" >
					  <% 
					  } %>					  
					  </td>
                      <td width="65%"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td>&nbsp;</td>
                          </tr>
                      </table></td>
                    </tr> 
                    <tr>
                      <td height="26" class="text-globales">Estado:(*)</td>
                      <td><select name="idestado" id="idestado" class="campo" style="width:50%" >
                        <option value="">Seleccionar</option>
                        <%
            iter = BCRC.getListEstados().iterator();
			while(iter.hasNext()){
			  String [] datos = (String[])iter.next();			  
              // 20111207 - EJV - Mantis 717
			  //if(datos[0].equals( "6" )  && BCRC.getIdzona().intValue() !=28 ) continue;
			  if(datos[0].equals( "6" )  && !habilitadoPreconf ) continue;
			  // <--
			  if(datos[0].equals( "10" )  ) continue;
			%>
                        <option value="<%= datos[0] %>" <%= datos[0].equals( BCRC.getIdestado().toString()) ? "selected" : "" %>><%= datos[1] %></option>
                        <%  
			}%>
                      </select></td>
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><input name="consulta" type="button" class="boton" id="consulta" value="  Consultar  " onClick="enviarFrm('consulta')">
                              
                              <input name="nrosucursalAnt" type="hidden" value="<%=Common.strZero(BCRC.getNrosucursal(), 4)%>" id="nrosucursalAnt" size="5" maxlength="4"   >
                              <input name="nroremitoclienteAnt" type="hidden" value="<%=Common.strZero(BCRC.getNroremitocliente(), 8)%>" id="nroremitoclienteAnt" size="10" maxlength="8" ></td>
                            <td>&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
        </table>
      </td>
    </tr>
  </table>



<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td> 

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	  <tr class="text-diez" >
	    <td class="fila-det-bold-rojo"><jsp:getProperty name="BCRC" property="mensaje"/> <%= msjLocal %>       
	      &nbsp;</td>
	    </tr>
	</table>

  </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="9%" >&nbsp;</td>
     <td width="2%" >&nbsp;</td>
     <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[2]%></div></td>
     <td width="20%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[7]%></div></td>
     <td width="17%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[8]%></div></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>
  </tr>
   <%
   iter = BCRC.getListRemitoStatus().iterator();
   while(iter.hasNext()){
      ++r;
      String[] sCampos = (String[]) iter.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><input name="cambiarestado" type="button" class="boton" id="cambiarestado" value="Modificar Estado" onClick="callCambiarEstado();"></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="15" height="15" border="0" style="cursor:pointer" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=<%= sCampos[9] .equalsIgnoreCase("N") ? "remitos_clientes_frame" : "remitos_clientes_regalos_frame" %>&idremitoclientedesde=<%=sCampos[0]%>', 'remitocliente', 750, 500);"></div></td>
      <td class="fila-det-border" >
	  <div align="center" onClick="abrirVentana('clientesRemitoDetalle.jsp?sucursal=<%=Common.strZero(sCampos[1], 4)%>&remitocliente=<%=Common.strZero(sCampos[2], 8)%>&idremitocliente=<%=sCampos[0]%>&idcliente=<%=sCampos[10]%>&cliente=<%=sCampos[11]%>&tipopedido=<%= sCampos[9] %>','detalle', 750, 450)" style="cursor:pointer" ><a href="#"><%=Common.strZero(sCampos[1], 4)%>-<%=Common.strZero(sCampos[2], 8)%>&nbsp;</a></div>	  </td>
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[6].equalsIgnoreCase("S") ? "SI" : "NO"%></td>
      <td class="fila-det-border" ><div align="right"><%=Common.setNotNull(sCampos[7])%></div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.setNotNull(sCampos[8])%></div></td>
      <td class="fila-det-border" ><%=sCampos[9].equalsIgnoreCase("N") ? "Normal" : "Regalos"%></td>
   </tr>
<%
   }%>
  </table>

   
   <input name="accion" value="" type="hidden" >
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

