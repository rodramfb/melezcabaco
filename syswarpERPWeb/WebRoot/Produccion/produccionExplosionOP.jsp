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
<%@ page import="java.util.Iterator" %> 
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
String titulo = "ORDENES DE PRODUCCION PENDIENTES";
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
%>
<html>
<jsp:useBean id="BPMPA"  class="ar.com.syswarp.web.ejb.BeanProduccionExplosionOP"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPMPA" property="*" />
<%
 BPMPA.setResponse(response);
 BPMPA.setRequest(request);
 BPMPA.setUsuarioalt(usuario);
 BPMPA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPMPA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel = "stylesheet" href = "<%= pathskin %>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script >
 
 function asignarValores(indice, obj){ 
    var total = document.frm.registros.value;
		if(total == 1){
		  //alert("A: "+total);
      document.frm.cantidadparcial.disabled = document.frm.realizado.disabled = document.frm.estimado.disabled = !obj.checked 		
		}
		else{
		  //alert("B: "+total);
		  document.frm.cantidadparcial[indice].disabled = document.frm.realizado[indice].disabled = document.frm.estimado[indice].disabled = !obj.checked 
		}
 }  
  
 </script>

</head>
<%
// titulos para las columnas
tituCol[0] = "Orden";
tituCol[1] = "Esq.";
tituCol[2] = "idcliente";
tituCol[3] = "Acum.";
tituCol[4] = "Est.";
tituCol[5] = "F. Prom.";
tituCol[6] = "F. Em.";
tituCol[7] = "Obs.";
tituCol[8] = "Art.";
tituCol[9] = "Descripción";
tituCol[10] = "idcontador";
tituCol[11] = "nrointerno"; 
java.util.List ProduccionMovProdu = new java.util.ArrayList();
ProduccionMovProdu= BPMPA.getProduccionMovProduList();
iterProduccionMovProdu = ProduccionMovProdu.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="produccionExplosionOP.jsp" method="POST" name="frm" >
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td width="13%" height="38"><input name="explosion" type="submit" class="boton" id="explosion" value="Actualizar"></td>
                   <td width="87%">
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
     <td width="3%" >&nbsp;</td>
		 
		 <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
		 <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Realizado</td>
		 <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
		 
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td width="39%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>

     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
    
    </tr>
   <%int r = 0;
   while(iterProduccionMovProdu.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterProduccionMovProdu.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td bgcolor="#E1EEF4" class="fila-det-border" >
      <input name="idop" type="checkbox" onClick="asignarValores(<%=r-1%>, this)"  value="<%= sCampos[0]%>" ></td>

      <td bgcolor="#E1EEF4" class="fila-det-border" ><img src="../imagenes/default/gnome_tango/devices/stock_printers.png" width="18" height="18" onClick="abrirVentana('produccionCalculoNecesidadFrame.jsp?idop=<%=sCampos[0]%>&tipoPedido=N');" style="cursor:pointer"></td>
      <td bgcolor="#E1EEF4" class="fila-det-border" ><input name="cantidadparcial" type="text" disabled class="style1 style2" id="cantidadparcial" value="<%=gb.getNumeroFormateado(Float.parseFloat(sCampos[4] ) - Float.parseFloat(sCampos[3]) , 10, 2)%>" size="5" maxlength="10">
			</td>
      <td bgcolor="#E1EEF4" class="fila-det-border" ><%=gb.getNumeroFormateado(Float.parseFloat(sCampos[3]) , 10, 2)%>&nbsp;
        <input name="realizado" type="hidden" value="<%=gb.getNumeroFormateado(Float.parseFloat(sCampos[3] ), 10, 2)%>"id="realizado" disabled>
			</td>
      <td bgcolor="#E1EEF4" class="fila-det-border" ><%=gb.getNumeroFormateado(Float.parseFloat(sCampos[4]), 10, 2 )%>&nbsp;
			<input name="estimado" type="hidden" value="<%=gb.getNumeroFormateado(Float.parseFloat(sCampos[4] ), 10, 2)%>"id="estimado" disabled>
			</td>


      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[8]%>&nbsp;</td>  
      <td class="fila-det-border" ><%=sCampos[9]%>&nbsp;</td>   

      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime( java.sql.Date.valueOf(sCampos[5]), "JSDateToStr")%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime( java.sql.Date.valueOf(sCampos[6]), "JSDateToStr")%>&nbsp;</td>
      <td class="fila-det-border" >&nbsp;<img src="../imagenes/default/gnome_tango/status/info.png" width="22" height="22" title="Observaciones:<%=sCampos[7]%>"></td>

    </tr>
<%
   }
	 %>
	 
  </table>

	 <input name="registros" value="<%=r%>" type="hidden">
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

