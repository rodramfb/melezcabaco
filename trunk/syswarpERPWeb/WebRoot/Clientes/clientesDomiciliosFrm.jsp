<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesDomicilios
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jan 22 17:07:35 GMT-03:00 2009 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
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
<jsp:useBean id="BCDF"  class="ar.com.syswarp.web.ejb.BeanClientesDomiciliosFrm"   scope="page"/>
<head>
 <title>Domicilios Cliente</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
 
 function asignarDomicilio(iddomicilio){

   opener.document.iddomicilionuevo.value = iddomicilio 
   opener.document.frm.submit();
 
 }
 

  function callOverlib(leyenda){
  //  overlib(leyenda, STICKY, CAPTION, 'MAS INFO',TIMEOUT,5000,HAUTO,VAUTO,WIDTH,350,BGCOLOR, '#DBDEEE', CAPCOLOR, '#FF0000');
    overlib(leyenda, STICKY, CAPTION, 'MAS INFO',TIMEOUT,5000,HAUTO,FIXY ,0 ,WIDTH,350,BGCOLOR, '#9999CC', CAPCOLOR, '#FF0000'); 
  }
 </script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCDF" property="*" />
 <% 
 int i = 0;
Iterator iterClientesDomicilios   = null;
int totCol = 40; // cantidad de columnas
String[] tituCol = new String[totCol];
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "idCliente";
tituCol[2] = "idtipodomicilio";
tituCol[3] = "Tipo";
tituCol[4] = "Default";
tituCol[5] = "Calle";
tituCol[6] = "Nro.";
tituCol[7] = "Piso";
tituCol[8] = "Depto.";
tituCol[9] = "Idloc.";
tituCol[10] = "Localidad";
tituCol[11] = "CPA";
tituCol[12] = "CP";
tituCol[13] = "Contacto";
tituCol[14] = "Cargo";
tituCol[15] = "TE";
tituCol[16] = "CEL";
tituCol[17] = "FAX";
tituCol[18] = "WEB";
tituCol[19] = "IdZona";
//tituCol[20] = "Zona";
tituCol[20] = "Dist.";
tituCol[21] = "IdExpreso";
//tituCol[22] = "Expreso";
tituCol[22] = "Zona";
tituCol[23] = "IdCobrador";
tituCol[24] = "Cobrador";
tituCol[25] = "IdVendedor";
tituCol[26] = "Vendedor";
tituCol[27] = "IdTIva";
tituCol[28] = "TDoc.";
tituCol[29] = "NDoc.";
tituCol[30] = "IIBB";
tituCol[36] = "Dias Audit";
tituCol[37] = "IdProv";
tituCol[38] = "Provincia";
tituCol[39] = "IdAnexolocalidad";
// 
 Iterator iter; 
 String titulo = BCDF.getAccion().toUpperCase() + " DE DOMICILIOS PARA EL CLIENTE: " + BCDF.getIdcliente();
 BCDF.setResponse(response);
 BCDF.setRequest(request);
 BCDF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCDF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCDF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BCDF.ejecutarValidacion();
 
 java.util.List ClientesDomicilios = new java.util.ArrayList();
 ClientesDomicilios= BCDF.getClientesDomiciliosList();
 iterClientesDomicilios = ClientesDomicilios.iterator();
 
 %>
<form action="clientesDomiciliosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCDF.getAccion()%>" >
<input name="iddomicilio" type="hidden" value="<%=BCDF.getIddomicilio()%>" >
<span class="fila-det-border">
<input name="idcliente" type="hidden" value="<%=BCDF.getIdcliente()%>" class="campo" size="100" maxlength="100"  >
</span><span class="fila-det-border">
<input name="cliente" type="hidden" class="campo" id="cliente" value="<%=BCDF.getCliente()%>" size="100" maxlength="100"  >
<input name="idcondicion" type="hidden" class="campo" id="idcondicion" value="<%=BCDF.getIdcondicion()%>" size="100" maxlength="100"  >
</span>
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
                <td colspan="5" class="fila-det-border"><jsp:getProperty name="BCDF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="15%" class="fila-det-border">&nbsp;
                  <input name="idprovincia" type="hidden" id="idprovincia">
                  <input name="provincia" type="hidden" id="provincia"></td>
                <td width="32%" class="fila-det-border">&nbsp;</td>
                <td width="14%" class="fila-det-border">&nbsp;</td>
                <td width="26%" class="fila-det-border">&nbsp;</td>
                <td width="5%" class="fila-det-border">&nbsp;</td>
                <td width="8%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="15%" class="fila-det-border">Tipo Domicilio: (*) </td>
                <td width="32%" class="fila-det-border"><select name="idtipodomicilio" id="idtipodomicilio" class="campo">
									  <option value="-1" >Seleccionar</option>
										<% 
										iter = BCDF.getListaTiposDomicilios().iterator();
										
										while (iter.hasNext()){
										  String[] datos = (String[]) iter.next();%> 
										  <option value="<%=datos[0]%>" <%=BCDF.getIdtipodomicilio().longValue() == Long.parseLong(datos[0]) ? "selected" : "" %>><%=datos[1]%></option>
										 <% 
										}%>
                  </select>                </td>
                <td width="14%" class="fila-det-border">Default?: (*) </td>
                <td width="26%" class="fila-det-border"><input name="esdefault" type="checkbox" id="esdefault" value="S" <%=BCDF.getEsdefault().equalsIgnoreCase("S") ? "checked" : ""%>></td>
                <td width="5%" class="fila-det-border">&nbsp;</td>
                <td width="8%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="15%" class="fila-det-border">Calle: (*) </td>
                <td width="32%" class="fila-det-border"><input name="calle" type="text" value="<%=BCDF.getCalle()%>" class="campo" size="30" maxlength="100"  ></td>
                <td class="fila-det-border">Nro: </td>
                <td class="fila-det-border"><input name="nro" type="text" value="<%=BCDF.getNro()%>" class="campo" size="10" maxlength="10"  ></td>
                <td width="5%" class="fila-det-border">Piso: </td>
                <td width="8%" class="fila-det-border"><input name="piso" type="text" value="<%=BCDF.getPiso()%>" class="campo" size="10" maxlength="10"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Localidad: (*)                
                <input name="idlocalidad" type="hidden" value="<%=BCDF.getIdlocalidad()%>"  size="30" maxlength="100"  ></td>
                <td class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="65%"><input name="localidad" type="text" class="campo" id="localidad" value="<%=BCDF.getLocalidad()%>" size="30" maxlength="100"  readonly></td>
                        <td width="35%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_localidades_anexos.jsp', 'localidad', 800, 450)" style="cursor:pointer"></td>
                      </tr>
                    </table></td>
                <td class="fila-det-border">Depto: </td>
                <td class="fila-det-border"><input name="depto" type="text" value="<%=BCDF.getDepto()%>" class="campo" size="10" maxlength="10"  ></td>
                <td class="fila-det-border">Postal: </td>
                <td class="fila-det-border"><input name="postal" type="text" value="<%=BCDF.getPostal()%>" class="campo" size="10" maxlength="10"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;Distribuidor: 
                <input name="idzona" type="hidden" id="idzona" value="<%=BCDF.getIdzona()%>">
                <input name="idanexolocalidad" type="hidden" value="<%=BCDF.getIdanexolocalidad()%>"  size="30" maxlength="100"  ></td>
                <td class="fila-det-border"><input name="zona" type="text" class="campo" id="zona" value="<%=BCDF.getZona()%>" size="30" maxlength="50" readonly ></td>
                <td class="fila-det-border">Zona: 
                  <input name="idexpreso" type="hidden"  id="idexpreso" value="<%=BCDF.getIdanexolocalidad()%>" size="30" maxlength="100"  ></td>
                <td class="fila-det-border"><input name="expreso" type="text" class="campo" id="expreso" value="<%=BCDF.getExpreso()%>" size="30" maxlength="100" readonly></td>
                <td class="fila-det-border">Cpa: </td>
                <td class="fila-det-border"><input name="cpa" type="text" value="<%=BCDF.getCpa()%>" class="campo" size="10" maxlength="15"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="15%" class="fila-det-border">Contacto: (*) </td>
                <td width="32%" class="fila-det-border"><input name="contacto" type="text" value="<%=BCDF.getContacto()%>" class="campo" size="30" maxlength="50"  ></td>
                <td class="fila-det-border">Cargo contacto: </td>
                <td class="fila-det-border"><input name="cargocontacto" type="text" value="<%=BCDF.getCargocontacto()%>" class="campo" size="30" maxlength="50"  ></td>
                <td width="5%" class="fila-det-border">&nbsp;</td>
                <td width="8%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="15%" class="fila-det-border">Telefonos:  </td>
                <td width="32%" class="fila-det-border"><input name="telefonos" type="text" value="<%=BCDF.getTelefonos()%>" class="campo" size="30" maxlength="50"  ></td>
                <td class="fila-det-border">Celular: </td>
                <td class="fila-det-border"><input name="celular" type="text" value="<%=BCDF.getCelular()%>" class="campo" size="30" maxlength="50"  ></td>
                <td width="5%" class="fila-det-border">&nbsp;</td>
                <td width="8%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="15%" class="fila-det-border">Fax:  </td>
                <td width="32%" class="fila-det-border"><input name="fax" type="text" value="<%=BCDF.getFax()%>" class="campo" size="30" maxlength="50"  ></td>
                <td class="fila-det-border">Web: </td>
                <td class="fila-det-border"><input name="web" type="text" value="<%=BCDF.getWeb()%>" class="campo" size="30" maxlength="50"  ></td>
                <td width="5%" class="fila-det-border">&nbsp;</td>
                <td width="8%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Cobrador: 
                (*)
                <input name="idcobrador" type="hidden" value="<%=BCDF.getIdcobrador()%>"  size="30" maxlength="100"  ></td>
                <td class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="65%"><input name="cobrador" type="text" value="<%=BCDF.getCobrador()%>" class="campo" size="30" maxlength="100" readonly ></td>
                    <td width="35%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_cobrador.jsp')" style="cursor:pointer"></td>
                  </tr>
                </table></td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td width="5%" class="fila-det-border">&nbsp;</td>
                <td width="8%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="15%" class="fila-det-border">Obs. Entrega: </td>
                <td colspan="3" class="fila-det-border"><textarea name="obsentrega" cols="80" rows="2" class="campo" id="obsentrega"><%=str.esNulo(BCDF.getObsentrega())%></textarea></td>
                <td width="5%" class="fila-det-border">&nbsp;</td>
                <td width="8%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><input name="validar" type="submit" value="Enviar" class="boton">                </td>
                <td class="fila-det-border"><input name="cerrar" type="button" class="boton" id="cerrar" value="Cerrar" onClick="opener.document.frm.submit(); window.close();"></td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td colspan="6" class="fila-det-border"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="text-globales">
                    <td width="1%" height="19">&nbsp;</td>
                    <td width="23%">&nbsp;Total de Domicilios:&nbsp;<%=BCDF.getTotalRegistros()%></td>
                    <td width="11%" >Visualizar:</td>
                    <td width="11%"><select name="limit" >
                        <%for(i=15; i<= 150 ; i+=15){%>
                        <%if(i==BCDF.getLimit()){%>
                        <option value="<%=i%>" selected><%=i%></option>
                        <%}else{%>
                        <option value="<%=i%>"><%=i%></option>
                        <%}
                                                      if( i >= BCDF.getTotalRegistros() ) break;
                                                    %>
                        <%}%>
                        <option value="<%= BCDF.getTotalRegistros()%>">Todos</option>
                      </select>
                    </td>
                    <td width="7%">&nbsp;P&aacute;gina:</td>
                    <td width="12%">
                      <select name="paginaSeleccion" >
                        <%for(i=1; i<= BCDF.getTotalPaginas(); i++){%>
                        <%if ( i==BCDF.getPaginaSeleccion() ){%>
                        <option value="<%=i%>" selected><%=i%></option>
                        <%}else{%>
                        <option value="<%=i%>"><%=i%></option>
                        <%}%>
                        <%}%>
                      </select> 
                    </td>
                    <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td colspan="6" class="fila-det-border">
								

<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="33%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[12]%></td>
     <td width="27%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[10]%></td>
     <td width="17%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[38]%></td>
     <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
  </tr>
   <%
	 int r = 0;
   Hashtable htRestringido = new Hashtable();
   htRestringido.put("0", "0");
   htRestringido.put("1", "1");
   htRestringido.put("2", "2");
   htRestringido.put("9", "9");
   htRestringido.put("19", "19");
   htRestringido.put("21", "21");
   htRestringido.put("23", "23");
   htRestringido.put("25", "25");
   htRestringido.put("27", "27");
   htRestringido.put("30", "30");
   htRestringido.put("31", "31");
   htRestringido.put("32", "32");
   htRestringido.put("33", "33");
   htRestringido.put("34", "34");
   htRestringido.put("35", "35");
   htRestringido.put("36", "36");
   htRestringido.put("37", "37");
   htRestringido.put("39", "39");

/*

tituCol[0] = "Cód.";
tituCol[1] = "idCliente";
tituCol[2] = "idtipodomicilio";
tituCol[3] = "Tipo";
tituCol[4] = "Default";
tituCol[5] = "Calle";
tituCol[6] = "Nro.";
tituCol[7] = "Piso";
tituCol[8] = "Depto.";
tituCol[9] = "Idloc.";
tituCol[10] = "Localidad";
tituCol[11] = "CPA";
tituCol[12] = "CP";
tituCol[13] = "Contacto";
tituCol[14] = "Cargo";
tituCol[15] = "TE";
tituCol[16] = "CEL";
tituCol[17] = "FAX";
tituCol[18] = "WEB";
tituCol[19] = "IdZona";
//tituCol[20] = "Zona";
tituCol[20] = "Dist.";
tituCol[21] = "IdExpreso";
//tituCol[22] = "Expreso";
tituCol[22] = "Zona";
tituCol[23] = "IdCobrador";
tituCol[24] = "Cobrador";
tituCol[25] = "IdVendedor";
tituCol[26] = "Vendedor";
tituCol[27] = "IdTIva";
tituCol[28] = "TDoc.";
tituCol[29] = "NDoc.";
tituCol[30] = "IIBB";
tituCol[36] = "Dias Audit";
tituCol[37] = "IdProv";
tituCol[38] = "Provincia";
tituCol[39] = "IdAnexolocalidad";


*/



	 String color_fondo ="";
   while(iterClientesDomicilios.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterClientesDomicilios.next(); 
			//for(int h=0;h<sCampos.length;h++) System.out.println( "sCampos[" + h + "]"  + sCampos[h]);
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
      String masInfo = "";
      for(int m=0;m<tituCol.length; m++) {
        if(htRestringido.containsKey(m + "")) continue;
        masInfo += "<strong>" + tituCol[m] + ": </strong>" + Common.setNotNull(sCampos[m]) + "<br>";
      }

%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[6]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[8]%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setNotNull(sCampos[12])%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[10]%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setNotNull(sCampos[38])%>&nbsp;</td>
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/gtk-home.png" width="15" height="15" onClick="callOverlib('<%=masInfo%>')"  title="Click para ver m&aacute;s info."></td>
   </tr>
<%
   }%>
</table>								</td>
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

