<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: cajaIdentificadores
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Dec 18 15:14:22 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*"%> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "IDENTIFICADORES";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterCajaIdentificadores   = null;
int totCol = 36; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCIDE"  class="ar.com.syswarp.web.ejb.BeanLovCajaIdentificadoresDepositosEfectivo"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCIDE" property="*" />
<%
 BCIDE.setResponse(response);
 BCIDE.setRequest(request);
 BCIDE.setSession(session);
 BCIDE.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCIDE.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>

  <script>
	function bajarDatos(codigo, descripcion, cc1, cc2,  modcc){
    //alert( window.opener.document.frm ); 
		window.opener.document.frm.<%= BCIDE.getCmpCodigo() %>.value = codigo;
		window.opener.document.frm.<%= BCIDE.getCmpDescripcion() %>.value = descripcion;  
		if(window.opener.document.frm.<%= BCIDE.getCmpCC1() %>) window.opener.document.frm.<%= BCIDE.getCmpCC1() %>.value = cc1; 
		if(window.opener.document.frm.<%= BCIDE.getCmpCC2() %>) window.opener.document.frm.<%= BCIDE.getCmpCC2() %>.value = cc2; 
		if('<%= BCIDE.getCmpModCC() %>' != '' ){
		  window.opener.document.frm.<%= BCIDE.getCmpModCC() %>.value = (modcc.toUpperCase() == 'S' ? 'true' : 'false');
		}
		this.close();
	}
	
 </script>
 
</head>
<%
// titulos para las columnas
// id / descripcion / cuenta cont / moneda  / tipomov / propio
tituCol[0] = "Cód.";
tituCol[1] = "Tipo";
tituCol[2] = "Identificador";
tituCol[3] = "Descripcion";
tituCol[4] = "Cuenta";
tituCol[5] = "idmoneda";
tituCol[6] = "Tipo Mov."; 
tituCol[7] = "Propio";
tituCol[8] = "modcta";
tituCol[9] = "factura";
tituCol[10] = "saldo_id";
tituCol[11] = "saldo_disp";
tituCol[12] = "renglones";
tituCol[13] = "ctacaucion";
tituCol[14] = "ctatodoc";
tituCol[15] = "gerencia";
tituCol[16] = "formula";
tituCol[17] = "cuotas";
tituCol[18] = "presentacion";
tituCol[19] = "ctacaudoc";
tituCol[20] = "porcentaje";
tituCol[21] = "ctadtotar";
tituCol[22] = "ctatarjeta";
tituCol[23] = "comhyper";
tituCol[24] = "contador";
tituCol[25] = "afecomicob";
tituCol[26] = "impri_id";
tituCol[27] = "subdiventa";
tituCol[28] = "idcencosto";
tituCol[29] = "idcencosto1";
tituCol[30] = "modicent";
tituCol[31] = "prox_cheq";
tituCol[32] = "prox_reserv";
tituCol[33] = "ulti_cheq";
tituCol[34] = "modsubcent";
tituCol[35] = "res_nro";
java.util.List CajaIdentificadores = new java.util.ArrayList();
CajaIdentificadores= BCIDE.getCajaIdentificadoresList();
iterCajaIdentificadores = CajaIdentificadores.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_cajaIdentificadoresDepositosEfectivo.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td colspan="2"  class="text-globales"><%=titulo%> 
                  </td>
                </tr>
                <tr>
                   <td width="3%" height="38">&nbsp;                  </td>
                   <td width="97%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BCIDE.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BCIDE.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BCIDE.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCIDE.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCIDE.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BCIDE.getTotalPaginas(); i++){%>
                                                    <%if ( i==BCIDE.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCIDE" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" ><div align="center"></div></td>
		   <!-- 
			 // id / descripcion / cuenta cont / moneda  / tipomov / propio
			  -->		 
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="31%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="44%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
    </tr>
   <%int r = 0;
   while(iterCajaIdentificadores.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterCajaIdentificadores.next();   
	  
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/actions/down.png" width="18" height="18" onClick="bajarDatos('<%= sCampos[2] %>', '<%= sCampos[3] %>', '<%= sCampos[28] %>', '<%= sCampos[29] %>', '<%= sCampos[30] %>')" style="cursor:pointer"></div></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[6]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
    </tr>
<%
   }%>
  </table>
   <p>&nbsp;
     <% 
  Hashtable htIdentificadores = (Hashtable) session.getAttribute("htIdentificadores");
	if(htIdentificadores != null && !htIdentificadores.isEmpty()){
	   %>
</p>
   <% 
	}%>
  <input name="accion" value="" type="hidden">
  <input name="tipomov" value="<%= BCIDE.getTipomov()%>" type="hidden">
  <input name="propio" value="<%= BCIDE.getPropio()%>" type="hidden">
  <input name="cmpCodigo" type="hidden" id="cmpCodigo" value="<%= BCIDE.getCmpCodigo()%>">
  <input name="cmpDescripcion" type="hidden" id="cmpDescripcion" value="<%= BCIDE.getCmpDescripcion()%>">
  <input name="cmpCC1" type="hidden" id="cmpCC1" value="<%= BCIDE.getCmpCC1()%>"> 
  <input name="cmpCC2" type="hidden" id="cmpCC2" value="<%= BCIDE.getCmpCC2()%>"> 
  <input name="cmpModCC" type="hidden" id="cmpModCC" value="<%= BCIDE.getCmpModCC()%>">  
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

