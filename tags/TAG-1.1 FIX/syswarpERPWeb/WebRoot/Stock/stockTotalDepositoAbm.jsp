<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vstockTotalDeposito
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Oct 24 16:08:03 GMT-03:00 2006 
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
String titulo = "EXISTENCIAS POR DEPOSITO";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVstockTotalDeposito   = null;
int totCol = 7; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = ""; 
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVTDA"  class="ar.com.syswarp.web.ejb.BeanVstockTotalDepositoAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVTDA" property="*" />
<%
 BVTDA.setResponse(response);
 BVTDA.setRequest(request);
 BVTDA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BVTDA.setUsuario(usuario);
 BVTDA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">  
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód. Art.";
tituCol[1] = "Artículo";
tituCol[2] = "Cód. Dep.";
tituCol[3] = "Déposito";
tituCol[4] = "Disponible";
tituCol[5] = "Reservado";
tituCol[6] = "Existencia";

java.util.List VstockTotalDeposito = new java.util.ArrayList();
VstockTotalDeposito= BVTDA.getVstockTotalDepositoList();
iterVstockTotalDeposito = VstockTotalDeposito.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="stockTotalDepositoAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td  class="text-globales" colspan="2"><%=titulo%></td>
                </tr>
                <tr>
                   <td width="3%" height="38">&nbsp; </td>
                   <td width="97%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BVTDA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BVTDA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BVTDA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BVTDA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                             </select>                                          </td>
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
			<tr>
			  <td  colspan="2" class="text-globales"><div align="center">
				  <input name="paginaSeleccion" type="hidden" value="1">
				P&aacute;gina: <%= Common.getPaginacion(BVTDA.getTotalPaginas(),
				BVTDA.getTotalRegistros(), BVTDA.getPaginaSeleccion(), BVTDA.getLimit(),
				BVTDA.getOffset()) %> </div></td>
			</tr>			
          </table>
       </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVTDA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="34%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="38%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
   </tr>
   <%
	 int r = 0;
	 float total = 0;
	 float totalReservado = 0;
	 float totalExistencia = 0;
	 String articulo = "";
	 boolean isPrimer = true;
	 color_fondo = "fila-det";
   while(iterVstockTotalDeposito.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVstockTotalDeposito.next(); 
      // estos campos hay que setearlos segun la grilla 
			if(!isPrimer){
			  if(!articulo.equalsIgnoreCase(sCampos[0])){
     	    if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
     	    else color_fondo = "fila-det-verde";
				  articulo = sCampos[0];

					
			%> 
			 
				 <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='fila-det-bold');" class="fila-det-bold" scope="col" > 
						<td class="fila-det-border" >&nbsp;</td>
						<td class="fila-det-border" >&nbsp;</td>
						<td class="fila-det-border" >&nbsp;</td>
						<td class="fila-det-border" >Total </td>
						<td class="fila-det-border" ><%=gb.getNumeroFormateado(total,10,2)%>&nbsp;</td>
						<td class="fila-det-border" ><%=gb.getNumeroFormateado(totalReservado,10,2)%>&nbsp;</td>
						<td class="fila-det-border" ><%=gb.getNumeroFormateado(totalExistencia,10,2)%>&nbsp;</td>
				 </tr>
				 <tr class="text-globales" height="3"> 
					 <td height="3" colspan="7">  </td>
				 </tr>	 

			<%		
					total = 0;
			  }	
			}
			else articulo = sCampos[0];
			isPrimer = false;
			%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=gb.getNumeroFormateado(Float.parseFloat(sCampos[4]),10,2)%>&nbsp;</td> 
      <td class="fila-det-border" ><%=gb.getNumeroFormateado(Float.parseFloat(sCampos[5]),10,2)%>&nbsp;</td>
      <td class="fila-det-border" ><%=gb.getNumeroFormateado(Float.parseFloat(sCampos[6]),10,2)%>&nbsp;</td>
   </tr>
<%
     total += Float.parseFloat(sCampos[4]);
     totalReservado += Float.parseFloat(sCampos[5]);
     totalExistencia += Float.parseFloat(sCampos[6]);
   }
	 if(r!=0){
	 
	 %>
 
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='fila-det-bold');" class="fila-det-bold" scope="col" > 
      <td class="fila-det-border" >&nbsp;</td>
      <td class="fila-det-border" >&nbsp;</td>
      <td class="fila-det-border" >&nbsp;</td>
      <td class="fila-det-border" >Total </td>
   	  <td class="fila-det-border" ><%=gb.getNumeroFormateado(total,10,2)%>&nbsp;</td>
   	  <td class="fila-det-border" ><%=gb.getNumeroFormateado(totalReservado,10,2)%>&nbsp;</td>
	  <td class="fila-det-border" ><%=gb.getNumeroFormateado(totalExistencia,10,2)%>&nbsp;</td>
   </tr>
   <tr class="text-globales" height="3"> 
     <td height="3" colspan="7">  </td>
   </tr>	 

<%
   }
	 %>
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

