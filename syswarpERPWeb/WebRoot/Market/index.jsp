<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: Stockfamilias
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Sep 04 09:19:38 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*"%>
<%@ page import="ar.com.syswarp.api.*" %> 
<%//@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
	Strings str = new Strings();
	String pathskin = "vs/market.css";
	String color_fondo ="";
	String titulo = "";
	// variables de entorno
	//String pathskin = session.getAttribute("pathskin").toString();
	//String pathscript = session.getAttribute("pathscript").toString();
	// variables de paginacion
	int i = 0;
	Iterator iterStockfamilias   = null;
	int totCol = 2; // cantidad de columnas
	String[] tituCol = new String[totCol];
	String usuarioalt = "";
	String usuarioact = "";
	String fechaalt   = "";
	String fechaact   = "";
	//String usuario    = session.getAttribute("usuario").toString();
%>
<jsp:useBean id="BMI"  class="ar.com.syswarp.web.ejb.BeanMarketIndex"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BMI" property="*" />
<%
 BMI.setResponse(response);
 BMI.setRequest(request);
 BMI.ejecutarValidacion();
%>
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%//=pathscript%>/forms.js"></script>
 <link rel="stylesheet" type="text/css" href="vs/Stylesheet.css" />
<%
%>

	<!----Aca empiezan las tablas fijas--->
    <table width="26%" height="100%" cellpadding="0" cellspacing="0" bgcolor="#99CCFF">
      <tr>
        <td valign="top"><table class="panel" cellpadding="0" cellspacing="0">
            <tr>
              <td><a href="marketRegistroFrm.jsp">Log in</a>|<a href="marketRegistroFrm.jsp">Registrarse</a>|<a href="marketAviso.jsp">Log out</a></td>
            </tr>
            <tr>
              <td><br />Bienvenido: nombredelusuario</td>
            </tr>
          </table>
		  <table width="284" height="47">
		  		<tr>
				<td width="242">
					<hr/>
				</td>
				</tr>
		  </table>
            <table class="buscador">
              <tr>
                <td>Cat&aacute;logo</td>
              </tr>
              <tr>
                <td class="combo"><form action="marketSeleccionGrupo.jsp" method="post" name="frmIndex" id="frmIndex">
                    <select name="codigo_fm" id="codigo_fm" onChange="document.frmIndex.submit();" class="combo">
                      <option value="-1" >Seleccionar</option>
                      <%
			java.util.List Stockfamilias = new java.util.ArrayList();
			Stockfamilias= BMI.getStockfamiliasList();
			iterStockfamilias = Stockfamilias.iterator();
			while(iterStockfamilias.hasNext()){ 
				String[] sCampos = (String[]) iterStockfamilias.next();%>
                      <option value="<%=sCampos[0]%>"  <%=BMI.getCodigo_fm().toString().equals(sCampos[0]) ? "selected" : ""%>><%=sCampos[1]%></option>
                      <%}
						 
		%>
                    </select>
                </form></td>
              </tr>
              <tr>
                <td>Buscador</td>
              </tr>
              <tr>
                <td class="campo"><form action="marketStockSinAgrupar.jsp" method="post" name="frmBuscar" id="frmBuscar">
                    <input name="cmpBuscar" type="text" value="<%//=BMI.getOcurrencia()%>" id="cmpBuscar" size="20" maxlength="50" class="campo" />
                    <input name="ir" type="submit"  id="ir" value=" Ir" />
                </form></td>
              </tr>
              <tr>
                <td><jsp:getProperty name="BMI" property="mensaje"/></td>
              </tr>
          </table></td>
      </tr>
    </table>
</body>
</html>


<% 
 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (index.jsp) : "+ex);   
}%>

