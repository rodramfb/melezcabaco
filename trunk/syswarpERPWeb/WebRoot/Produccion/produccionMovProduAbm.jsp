<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: produccionMovProdu
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Feb 21 13:30:17 GMT-03:00 2007 
   Observaciones: 
      .
*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*"%> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ page import="ar.com.syswarp.ejb.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
GeneralBean gb = new GeneralBean();
String color_fondo ="";
String titulo = "ORDENES DE PRODUCCION";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterProduccionMovProdu   = null;
int totCol = 12; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
Hashtable htColorEstado = new Hashtable();
htColorEstado.put("PENDIENTE","#FFCC66");
htColorEstado.put("EN PROCESO","#6699FF");
htColorEstado.put("FINALIZADA","#339999");
htColorEstado.put("ANULADA","#CC0000");
%>
<html>
<jsp:useBean id="BPMPA"  class="ar.com.syswarp.web.ejb.BeanProduccionMovProduAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPMPA" property="*" />
<%
 BPMPA.setResponse(response);
 BPMPA.setRequest(request);
 BPMPA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPMPA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel = "stylesheet" href = "<%= pathskin %>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
 function evitarCheck(){
   if(document.frm.accion.value.toLowerCase() == 'alta'){
		 var i=0;
		 for(i=0;i<document.frm.idop.length;i++){
			 if(document.frm.idop[i].checked){
				 document.frm.idop[i].checked = false;
				 break;
			 }
		 } 
	 }
 }
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Orden";
tituCol[1] = "Esq.";
tituCol[2] = "idcliente";
tituCol[3] = "Realizado";
tituCol[4] = "Estimado";
tituCol[5] = "F. Prom.";
tituCol[6] = "F. Emision";
tituCol[7] = "Obs.";
tituCol[8] = "Art.";
tituCol[9] = "Descripcion";
tituCol[10] = "idcontador";
tituCol[11] = "nrointerno";
java.util.List ProduccionMovProdu = new java.util.ArrayList();
ProduccionMovProdu= BPMPA.getProduccionMovProduList();
iterProduccionMovProdu = ProduccionMovProdu.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="produccionMovProduAbm.jsp" method="POST" name="frm" onSubmit="evitarCheck();">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td width="11%" height="38">
                      <table width="36%" border="0">
                         <tr>
                            <td width="27%">
                               <input name="alta" id="alta" value="alta" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('alta','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name ;   ">                            </td>
                            <td width="27%">
                               <input name="modificacion" id="modificacion" value="modificacion" type="image" src="../imagenes/default/btn_edit_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('modificacion','','../imagenes/default/btn_edit_over.gif',1)" onClick="document.frm.accion.value = this.name">                            </td>
                            <td width="27%">
                               <input name="baja" id="baja" value="baja" type="image" src="../imagenes/default/btn_remove_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('baja','','../imagenes/default/btn_remove_over.gif',1)" onClick="return confirmarBaja('frm');">                            </td>
                            <td width="27%"><img src="../imagenes/default/gnome_tango/status/gnome-stock-trash-full.png" width="18" height="18" style="cursor:pointer" name="anular" onClick="document.frm.accion.value = this.name; document.frm.submit();"></td>
                         </tr>
                      </table>
                   </td>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BPMPA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BPMPA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BPMPA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BPMPA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BPMPA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BPMPA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BPMPA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BPMPA" property="mensaje"/>     </td>
  </tr> 
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" height="21" >&nbsp;</td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td width="41%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>		 
		 <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%="Estado"%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
    </tr>
   <%int r = 0;
   while(iterProduccionMovProdu.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterProduccionMovProdu.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><input type="radio" name="idop" value="<%= sCampos[0]%>" ></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[8]%>&nbsp;</td>  
      <td class="fila-det-border" ><%=sCampos[9]%>&nbsp;</td>  
      <td class="fila-det-border" ><%=gb.getNumeroFormateado(Float.parseFloat(sCampos[3]) , 10, 2)%>&nbsp;</td>
      <td class="fila-det-border" ><%=gb.getNumeroFormateado(Float.parseFloat(sCampos[4]), 10, 2 )%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime( java.sql.Date.valueOf(sCampos[5]), "JSDateToStr")%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime( java.sql.Date.valueOf(sCampos[6]), "JSDateToStr")%>&nbsp;</td>
      <td class="fila-det-border"><font color="<%= htColorEstado.get(sCampos[12]) %>" style="font-weight:bold"><%=sCampos[12]%></font></td>
      <td class="fila-det-border" >&nbsp;<img src="../imagenes/default/gnome_tango/status/info.png" width="22" height="22" title="Observaciones: <%=sCampos[7]%>"></td>
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

