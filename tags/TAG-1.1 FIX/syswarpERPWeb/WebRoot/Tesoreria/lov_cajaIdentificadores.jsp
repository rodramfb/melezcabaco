<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: cajaIdentificadores
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Oct 17 10:32:34 GMT-03:00 2008 
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
String titulo = "IDENTIFICADORES";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterCajaIdentificadores   = null;
int totCol = 35; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCIA"  class="ar.com.syswarp.web.ejb.BeanCajaIdentificadoresAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCIA" property="*" />
<%
 BCIA.setResponse(response);
 BCIA.setRequest(request);
 BCIA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCIA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
 function bajarDatos(codigo, descripcion, propio, tipo){
   opener.document.frm.identificador.value = codigo;
   opener.document.frm.descripcion_identificador.value = descripcion; 
	 opener.document.frm.propio.value = propio;
	 opener.document.frm.tipo.value = tipo;
	 
	 window.close();
 }
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Tipo";
tituCol[2] = "Identificador";
tituCol[3] = "Descripcion";
tituCol[4] = "cuenta";
tituCol[5] = "idmoneda";
tituCol[6] = "modcta";
tituCol[7] = "factura";
tituCol[8] = "saldo_id";
tituCol[9] = "saldo_disp";
tituCol[10] = "renglones";
tituCol[11] = "ctacaucion";
tituCol[12] = "ctatodoc";
tituCol[13] = "gerencia";
tituCol[14] = "formula";
tituCol[15] = "cuotas";
tituCol[16] = "presentacion";
tituCol[17] = "ctacaudoc";
tituCol[18] = "porcentaje";
tituCol[19] = "ctadtotar";
tituCol[20] = "ctatarjeta";
tituCol[21] = "comhyper";
tituCol[22] = "contador";
tituCol[23] = "afecomicob";
tituCol[24] = "impri_id";
tituCol[25] = "subdiventa";
tituCol[26] = "idcencosto";
tituCol[27] = "idcencosto1";
tituCol[28] = "modicent";
tituCol[29] = "prox_cheq";
tituCol[30] = "prox_reserv";
tituCol[31] = "ulti_cheq";
tituCol[32] = "modsubcent";
tituCol[33] = "res_nro";

java.util.List CajaIdentificadores = new java.util.ArrayList();
CajaIdentificadores= BCIA.getCajaIdentificadoresList();
iterCajaIdentificadores = CajaIdentificadores.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_cajaIdentificadores.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="11%" height="38">&nbsp;</td>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BCIA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BCIA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BCIA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCIA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCIA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BCIA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BCIA.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select>                                          </td>
                                          <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  " ></td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCIA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="43%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="41%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
  </tr>
   <%int r = 0;
	 String tipo = "";
   while(iterCajaIdentificadores.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterCajaIdentificadores.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
			if(!tipo.equals(sCampos[1])){
			  if(!tipo.equals("")){%>
    <tr class="permiso-cinco">
     <td colspan="5" height="4"></td>
    </tr>
		<%
			  }		
		    tipo=sCampos[1];
			}		  
			%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/down.png" width="18" height="18" style="cursor:pointer" onClick="bajarDatos('<%=sCampos[2]%>', '<%=sCampos[3]%>', '<%=sCampos[34]%>', '<%=sCampos[35]%>');"></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td> 
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
   </tr>
<%
   }%>
  </table>
   <input name="accion" value="" type="hidden">
   <input name="identificador" type="hidden" id="identificador" value="">
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

