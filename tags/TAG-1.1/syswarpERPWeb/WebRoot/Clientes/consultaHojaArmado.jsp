<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: pedidos_cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Feb 12 08:43:04 GYT 2009 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*" %> 
<%@ page import="java.sql.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "ASIGNAR HOJA DE ARMADO";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterPedidos_cabe   = null;
int totCol = 21; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCRCHA"  class="ar.com.syswarp.web.ejb.BeanClientesRemitosConsultaHojaArmado"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCRCHA" property="*" />
<%
 BCRCHA.setResponse(response);
 BCRCHA.setRequest(request);
 //BCRCHA.setIdcontadorcomprobante( new BigDecimal( session.getAttribute("idcontadorremitos4").toString() ));  
 BCRCHA.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCRCHA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BCRCHA.ejecutarValidacion(); 

/*
 Enumeration e = session.getAttributeNames();
 while(e.hasMoreElements()){
   String atr = e.nextElement().toString();
   System.out.println("  " + atr + " : " + session.getAttribute(atr));
 }
*/

%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>


</head>
<%
// titulos para las columnas
tituCol[0] = "Id.Remito";
tituCol[1] = "Fecha";
tituCol[2] = "Sucursal";
tituCol[3] = "Remito";
tituCol[4] = "Id.Cliente";
tituCol[5] = "Cliente";
tituCol[6] = "Calle";
tituCol[7] = "Nro";
tituCol[8] = "Piso";
tituCol[9] = "Depto";
tituCol[10] = "idzona";
//tituCol[11] = "Zona";
tituCol[11] = "Dist.";
tituCol[12] = "idexpreso";
//tituCol[13] = "Expreso";
tituCol[13] = "Zona";
tituCol[14] = "idlocalidad";
tituCol[15] = "Localidad";
tituCol[16] = "idProvincia";
tituCol[17] = "Provincia"; 
tituCol[18] = "codigo_dt";
tituCol[19] = "Depósito Origen"; 
tituCol[20] = "Cta.Cte"; 

java.util.List Pedidos_cabe = new java.util.ArrayList();
Pedidos_cabe= BCRCHA.getPedidos_cabeList();
iterPedidos_cabe = Pedidos_cabe.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="consultaHojaArmado.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" >
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td><%=titulo%></td>
                </tr>
                <tr>
                  <td width="89%" height="38">
                     <table width="100%" border="0">
                        <tr>
                          <!--td width="6%" height="26" class="text-globales">Buscar</td>
                          <td width="22%">
                             <input name="ocurrencia" type="text" value="< %=BCRCHA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td-->
                          <td width="72%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="12%" height="26">Buscar</td>
                                         <td width="22%"><input name="ocurrencia" type="text" value="<%=BCRCHA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                                         </td>
                                         <td width="14%">&nbsp;Total de registros:&nbsp;<%=BCRCHA.getTotalRegistros() + ""%></td>
                                         <td width="14%" >Visualizar:</td>
                                         <td width="10%">
                                            <select name="limit" >
                                               <%for(i=10; i<= 100 ; i+=10){%>
                                                   <%if(i==BCRCHA.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                   <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                   <%}
                                                      if( i >= BCRCHA.getTotalRegistros() ) break;
                                                    %>
                                               <%}%>
                                               <option value="<%= BCRCHA.getTotalRegistros()%>">Todos</option>
                                         </select>                                          </td>
                                         <td width="10%">&nbsp;P&aacute;gina:</td>
                                         <td width="6%">
                                            <select name="paginaSeleccion" >
                                               <%for(i=1; i<= BCRCHA.getTotalPaginas(); i++){%>
                                                   <%if ( i==BCRCHA.getPaginaSeleccion() ){%>
                                                      <option value="<%=i%>" selected><%=i%></option> 
                                                   <%}else{%>
                                                      <option value="<%=i%>"><%=i%></option>
                                                   <%}%>
                                               <%}%>
                                         </select>                                           </td>
                                         <td width="12%"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCRCHA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td width="12%" ><div align="center"><%=tituCol[3]%>&nbsp;</div></td>
     <td width="16%" ><div align="center"><%=tituCol[5]%></div></td>
     <td width="15%" ><div align="center"><%=tituCol[1]%></div></td>
     <td width="14%" ><div align="center"><%=tituCol[20]%></div></td>
     <td width="19%" ><%=tituCol[13]%></td>
     <td width="24%" ><%=tituCol[19]%></td>
    </tr>
   <%int r = 0;

//   Enumeration en = Common.getSetSorted(BCRCHA.getHtColorGrupoArmado().keySet());
   while(iterPedidos_cabe.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterPedidos_cabe.next(); 
      String fecha = Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[1]), "JSDateToStr") + "" ;
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
      

%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="center" onClick="abrirVentana('clientesRemitoDetalle.jsp?sucursal=<%=sCampos[2]%>&remitocliente=<%=sCampos[3]%>&idremitocliente=<%=sCampos[0]%>&idcliente=<%=sCampos[4]%>&cliente=<%=sCampos[5]%>','detalle', 750, 350)" style="cursor:pointer" ><a href="#"><%=sCampos[2]%>-<%=sCampos[3]%>&nbsp;</a></div></td>
      <td class="fila-det-border" ><div title="<%=sCampos[4]%>-<%=sCampos[5]%>">
        <div align="center"><%=sCampos[4]%></div>
      </div></td>
      <td class="fila-det-border" ><div title="<%=  fecha %>">
        <div align="center"><%= fecha.substring(3, 10) %></div>
      </div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setNotNull(sCampos[19])%>&nbsp;</div></td>
      <td class="fila-det-border" ><%=sCampos[13].length() > 20 ? sCampos[13].substring(0, 20) : sCampos[13] %>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[18].length() > 20 ? sCampos[18].substring(0, 20) : sCampos[18] %>&nbsp;</td>  
    </tr>
<% 
   }%>
   </table> 
   <input name="nrohojaarmado"  id="nrohojaarmado" value="<%= BCRCHA.getNrohojaarmado() %>" type="hidden">
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

