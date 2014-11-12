<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: rrhhpersonal
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Apr 22 09:44:28 ACT 2009 
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
String titulo = "Personal";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterRrhhpersonal   = null;
int totCol = 5; // cantidad de columnas 
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BRA"  class="ar.com.syswarp.web.ejb.BeanRrhhpersonalAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BRA" property="*" />
<%
 BRA.setResponse(response);
 BRA.setRequest(request);
 BRA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BRA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script type="text/javascript">
 function mostrarLOV(pagina) {
   frmLOV = open(pagina, 'winLOV', 'menubar=no,status=no,toolbar=no,location=no,scrollbars=yes,resizable=yes,width=700,height=400,left=20, top=20');
   if (frmLOV.opener == null) {
	  frmLOV.opener = self;
   }
}
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Legajo";
tituCol[1] = "Apellido";
tituCol[2] = "Domicilio";
tituCol[3] = "Es empleado?";
tituCol[4] = "Historial";
/*

tituCol[3] = "Puerta";
tituCol[4] = "Piso";
tituCol[5] = "Departamento";
tituCol[6] = "idlocalidad";
tituCol[7] = "idprovincia";
tituCol[8] = "Postal";
tituCol[9] = "Telefono";
tituCol[10] = "idestadocivil";
tituCol[11] = "Fecha nac";
tituCol[12] = "Sexo";
tituCol[13] = "idtipodoc";
tituCol[14] = "Nro documento";
tituCol[15] = "Cuil";
tituCol[16] = "idnacionalidad";
tituCol[17] = "Fecha baja";
tituCol[18] = "idcategoria";
tituCol[19] = "Tarea";
tituCol[20] = "Fecha ingreso";
tituCol[21] = "idtitulo";
tituCol[22] = "idafjp";
tituCol[23] = "Nro afjp";
tituCol[24] = "idart";
tituCol[25] = "Nro art";
tituCol[26] = "Valor 01";
tituCol[27] = "Valor 02";
tituCol[28] = "Valor 03";
tituCol[29] = "Valor 04";
tituCol[30] = "Valor 05";
tituCol[31] = "Mensual o Quincenal";
tituCol[32] = "idctacont";
tituCol[33] = "idctacont2";
tituCol[34] = "Anios recon";
tituCol[35] = "Mes recon";
tituCol[36] = "idobrasocial";
tituCol[37] = "Jubilado";
tituCol[38] = "E-mail";
*/
java.util.List Rrhhpersonal = new java.util.ArrayList();
Rrhhpersonal= BRA.getRrhhpersonalList();
iterRrhhpersonal = Rrhhpersonal.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="rrhhpersonalAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td  class="text-globales"><%=titulo%></td>
                   <td>&nbsp;</td>
                </tr>
                <tr>
                  <td width="11%" height="38"><table width="36%" border="0">
                    <tr>
                      <td width="27%"><input name="alta" id="alta" value="alta" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('alta','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name">
                      </td>
                      <td width="27%"><input name="modificacion" id="modificacion" value="modificacion" type="image" src="../imagenes/default/btn_edit_norm.gif"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('modificacion','','../imagenes/default/btn_edit_over.gif',1)" onClick="document.frm.accion.value = this.name">
                      </td>
                      <td width="27%">
                      	<input name="baja" id="baja" value="baja" type="image" src="../imagenes/default/btn_remove_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('baja','','../imagenes/default/btn_remove_over.gif',1)" onClick="return confirmarBaja('frm');"> 
                      </td>
                    </tr>
                  </table></td>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BRA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BRA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BRA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BRA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BRA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BRA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BRA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BRA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td>&nbsp;</td>
     <td> &nbsp;</td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td></td>
   </tr>
   <%int r = 0;
   while(iterRrhhpersonal.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterRrhhpersonal.next();
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><input type="radio" name="legajo" value="<%= sCampos[0]%>"></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/actions/gnome-stock-text-indent.png" width="18" height="18" onClick="abrirVentana('rrhhPersonalConceptosAsocia.jsp?legajo=' + <%=sCampos[0]%>, 'Baja de un empleado', 1000, 600)" style="cursor:pointer" title="Administrar Conceptos" alt="Administrar Conceptos"></div></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[2]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[3].equalsIgnoreCase("t") ?  "SÍ": "NO"%></td>
      <td class="fila-det-border" >&nbsp;
      	<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_personal_historial.jsp?legajo=<%=sCampos[0]%>')" style="cursor:pointer"></td>
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

