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
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "CONFORMAR REMITOS PRECONFORMADOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
int r = 0;
Iterator iter   = null;
int totCol = 12; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BBOEA"  class="ar.com.syswarp.web.ejb.BeanClientesRemitosConformarPreconformados" scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BBOEA" property="*" />
<%
 BBOEA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBOEA.setUsuarioalt(usuario); 
 BBOEA.setResponse(response);
 BBOEA.setRequest(request);
 BBOEA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
//-----------------------------------------------------------------------

function enviarFrm(accion){

    if(document.getElementById('idestado').value != 5 && document.getElementById('idestado').value > 0 ) 
	  alert('ATENCION: Solicitar Nota de Crédito.');
    document.frm.cambiarestado.disabled = true;
	document.frm.cambiarestado.value = 'Procesando ....';
    document.frm.accion.value = accion;
    document.frm.submit();
}

function sendValidacion(){

  var cmpInput = document.frm.inputremito;
  var sucursal = document.frm.nrosucursal;
  var comprobante = document.frm.nroremitocliente;
 
  if(cmpInput.value.length == 19  && document.frm.accion.value != 'cambiarestado') { 
    sucursal.value = cmpInput.value.substring(7, 11 );
	comprobante.value = cmpInput.value.substring(11);
	document.frm.accion.value += 'cambiarestado';
    document.frm.submit();
  }
 
 
}

function setEnabled(tipoInput){
	if(tipoInput == 'L'){
    	document.frm.nroremitocliente.readOnly 	= true;
    	document.frm.nrosucursal.readOnly 	= true;
		document.frm.inputremito.readOnly 	= false;
		document.frm.cambiarestado.disabled = true;
		document.frm.inputremito.focus(); 
	}else if(tipoInput == 'M'){
    	document.frm.nroremitocliente.readOnly 	= false;
    	document.frm.nrosucursal.readOnly 	= false;
		document.frm.inputremito.readOnly 	= true;
		document.frm.cambiarestado.disabled = false;
		document.frm.nrosucursal.select(); 
	}
   	document.frm.nrosucursal.value 	= '0000';
	document.frm.nroremitocliente.value 	= '00000000';
}


function callSetEnabled() {
    for(var i =0 ; i<document.frm.tipoinput.length;i++){
	  if(document.frm.tipoinput[i].checked)
	    setEnabled(document.frm.tipoinput[i].value);
	}
}



function test (){

  alert(document.getElementById('idzona').value);
  

}

window.onload = function() { 
  var tipoInput = '<%= BBOEA.getTipoinput() %>';
  setEnabled(tipoInput);
  //--->
  
  document.getElementById('idestado').onchange = callSetEnabled ;
  //document.getElementById('idzona').onchange = callSetEnabled
  document.getElementById('idzona').onchange = function () { document.frm.submit(); };

  
  //<---
  
}

 </script>
</head>
<%
/*
pd.idpedido_cabe, pd.idremitocliente, cr.nrosucursal, cr.nroremitocliente, 
cr.idctrlremito, pc.idcliente, pc.idsucuclie, cr.nrohojaarmado, nrohojarutafinal
*/
// titulos para las columnas 
tituCol[0] = "";
tituCol[1] = "";
tituCol[2] = "";
tituCol[3] = "";
tituCol[4] = "";
tituCol[5] = "";
tituCol[6] = "";
tituCol[7] = "";
tituCol[8] = "";
tituCol[9] = "";
tituCol[10] = "";
tituCol[11] = "";

java.util.List ListRemitos = new java.util.ArrayList();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" id="cuerpo">
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="clientesRemitosConformarPreconformados.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales"> 
    <td  height="24" colspan="10" align="center">
            <table width="98%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                   <td height="31"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td height="5px"> <hr color="#FFFFFF"></td>
                </tr>
                <tr>
                  <td height="38">
				  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="text-globales">
                      <td height="26" class="text-globales">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>TIPO INGRESO  </td>
                    </tr>
                    <tr class="text-globales">
                      <td width="17%" height="26" class="text-globales">Input Remito: </td>
                      <td width="32%"><input name="inputremito" type="text" class="campo" id="inputremito" style="text-align:right" onKeyUp="sendValidacion(this)" size="19" maxlength="100" ></td>
                      <td width="51%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr class="text-globales">
                            <td width="30%">Lectora</td>
                            <td width="70%"><div align="left">
                                <input name="tipoinput" type="radio" class="campo" value="L"  <%=BBOEA.getTipoinput().equalsIgnoreCase("L") ? "checked" : "" %>  onClick="setEnabled(this.value)">
                            </div></td>
                          </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td height="26" class="text-globales">Remito:(*)</td>
                      <td ><input name="nrosucursal" type="text" value="<%=Common.strZero(BBOEA.getNrosucursal(), 4)%>" id="nrosucursal" size="5" maxlength="4" class="campo"  style="text-align:right" >
                          <input name="nroremitocliente" type="text" value="<%=Common.strZero(BBOEA.getNroremitocliente(), 8)%>" id="nroremitocliente" size="10" maxlength="8" class="campo" style="text-align:right" ></td>
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr class="text-globales">
                            <td width="30%">Manual</td>
                            <td width="70%"><div align="left">
                                <input name="tipoinput" type="radio" value="M" class="campo" <%= BBOEA.getTipoinput().equalsIgnoreCase("M") ? "checked" : "" %> onClick="setEnabled(this.value)">
                            </div></td>
                          </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td height="26" class="text-globales"><p>Zona: (*)</p></td>
                      <td >
					  <%if(BBOEA.getZonaUsuario().longValue() == 0){%>
					  <select name="idzona" id="idzona" class="campo" style="width:160" >
                            <option value="">Seleccionar</option>
                            <%
						iter = BBOEA.getListZonas().iterator();
						while(iter.hasNext()){
						String [] datos = (String[])iter.next();%>
                            <option value="<%= datos[0] %>" <%= datos[0].equals( BBOEA.getIdzona().toString()) ? "selected" : "" %>><%= datos[1] %></option>
                            <%  
						}%>
                          </select>
                          <%
					  }
					  else if(BBOEA.getZonaUsuario().longValue() > 0){ %>
                          <input name="zona" type="text" value="<%=BBOEA.getZona()%>" id="zona" size="30" maxlength="100" class="campo" readonly>
                          <input name="idzona" type="hidden" value="<%=BBOEA.getIdzona()%>" id="idzona" size="30" maxlength="100" class="campo" >
                          <% 
					  } %>                      </td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td height="25" valign="top" class="text-globales">Estado:(*)</td>
                      <td><select name="idestado" class="campo" id="idestado" style="width:160" >
                          <option value="">Seleccionar</option>
                          <%
            iter = BBOEA.getListEstadosRemito().iterator();
			while(iter.hasNext()){
				String [] datos = (String[])iter.next();
				// 20110606 - Mantis 706 - EJV -->
				//if(!datos[0].equals("2") && !datos[0].equals("3") && !datos[0].equals("5") && !datos[0].equals("10")) continue;
				if(!datos[0].equals("2") && !datos[0].equals("3") && !datos[0].equals("5") && !datos[0].equals("10") && !datos[0].equals("9")) continue;
				// <--
				if(datos[0].equals("10") && BBOEA.getIdzona().intValue() != 28) continue;
			   
			
			%>
                          <option value="<%= datos[0] %>" <%= datos[0].equals( BBOEA.getIdestado().toString()) ? "selected" : "" %>><%= datos[1] %></option>
                          <%  
			}%>
                      </select></td>
                      <td valign="top">&nbsp;</td>
                    </tr>
                    <tr>
                      <td height="26" class="text-globales"><input name="cambiarestado" type="button" class="boton" id="cambiarestado" value="Conformar Remito" onClick="enviarFrm('cambiarestado');"></td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  </table></td>
                </tr>
        </table>
      </td>
    </tr>
  </table>



<table width="80%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr >
    <td height="30" class="fila-det-bold-rojo"><jsp:getProperty name="BBOEA" property="mensaje"/>
      &nbsp;</td>
  </tr>
  <tr >
    <td height="30" class="text-diez"><%= "ULTIMO COMPROBANTE INGRESADO: " + Common.strZero(BBOEA.getNrosucursal(), 4) + " - " +  Common.strZero(BBOEA.getNroremitocliente(), 8)%> &nbsp;</td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><label></label></td>
  </tr>
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

