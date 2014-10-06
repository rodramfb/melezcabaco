<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: Cajaclearing
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Aug 01 11:36:49 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.math.*"%> 
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
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanCajaDepositosEfectivoFrm"   scope="page"/>
<head>
 <title></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
<script language="JavaScript" src="<%=pathscript%>/forms.js"></script> 
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>

<script>

function callCCDeposito(pagina){
  if(document.frm.identificadorDeposito.value == ''){
	alert ("Es necesario que seleccione Identificador a depositar.");
	return false;		  	
  }

  if(document.frm.modCCDeposito.value == 'false' ){
	alert ("El Identificador a Depositar no permite modificar Centros de Costos."); 
	return false;	  
  }  
  
  abrirVentana(pagina,'CC',750, 350);
}

function callCCBanco(pagina){
  if(document.frm.identificadorBanco.value == ''){
	alert ("Es necesario que seleccione Banco a depositar."); 
	return false;		  	
  }
  
  if(document.frm.modCCBanco.value == 'false' ){
	alert ("El Banco a Depositar no permite modificar Centros de Costos."); 
	return false;	  
  }
  
  abrirVentana(pagina,'CC',750, 350);
}


</script>
</head>
<BODY  topmargin="0" leftmargin="0" rightmargin="0">
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo =  " DEPOSITOS EN EFECTIVO " ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCF.ejecutarValidacion();
 %>
<form action="cajaDepositosEfectivo.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >

   <span class="fila-det-border">
   <input name="tesoFechaCaja" type="hidden" id="tesoFechaCaja" value="<%=BCF.getTesoFechaCaja()%>">
   </span>
   <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="23%" height="41" class="fila-det-border">Identificador a Depositar (*):  </td>
                <td width="77%" class="fila-det-border"><input name="identificadorDeposito" type="text" class="campo" id="identificadorDeposito" value="<%=BCF.getIdentificadorDeposito()%>" size="5" maxlength="3">
                     <input name="descripcionDeposito" type="text" class="campo" id="descripcionDeposito" value="<%=BCF.getDescripcionDeposito()%>" size="60" maxlength="150"  > <span class="fila-titulo-dos">
                  <input name="btnidentificadores" type="button" class="boton" id="btnidentificadores" style="cursor:pointer" onClick="abrirVentana('lov_cajaIdentificadoresDepositosEfectivo.jsp?propio=N&tipomov=E&cmpCodigo=identificadorDeposito&cmpDescripcion=descripcionDeposito&cmpCC1=cc1Deposito&cmpCC2=cc2Deposito&cmpModCC=modCCDeposito', 'deposito', 800, 400)" value=">>">
                </span></td>
              </tr>
              <tr class="fila-det">    
                <td height="41" class="fila-det-border">Centros de Costos:  </td>
                <td class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="fila-det">
                      <td colspan="2">CC1</td>
                      <td colspan="2">CC2</td>
                    </tr>
                    <tr class="fila-det">
                      <td width="8%"><input name="cc1Deposito" type="text" class="campo" id="cc1Deposito" value="<%=BCF.getCc1Deposito()%>" size="5" maxlength="5" readonly ></td>
                      <td width="43%"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="callCCDeposito('../Contable/lov_centro_de_costos1.jsp?txtCod=cc1Deposito&txtDesc=descCc1Deposito')" style="cursor:pointer">
                      <input name="descCc1Deposito" type="hidden" class="campo" id="descCc1Deposito" value="<%=BCF.getDescCc1Deposito()%>" size="30" maxlength="100"  readonly>
                      <input name="modCCDeposito" type="hidden" class="campo" id="modCCDeposito" value="<%=BCF.isModCCDeposito()%>" size="30" maxlength="100"  readonly></td>
                      <td width="8%"><input name="cc2Deposito" type="text" class="campo" id="cc2Deposito" value="<%=BCF.getCc2Deposito()%>" size="5" maxlength="5" readonly ></td>
                      <td width="41%"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="callCCDeposito('../Contable/lov_centro_de_costos1.jsp?txtCod=cc2Deposito&txtDesc=descCc2Deposito')" style="cursor:pointer">
                      <input name="descCc2Deposito" type="hidden" class="campo" id="descCc2Deposito" value="<%=BCF.getDescCc2Deposito()%>" size="30" maxlength="1000" readonly ></td>
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td height="3" class="text-globales"></td>
                <td height="3" class="text-globales"></td>
              </tr>
              <tr class="fila-det"> 
                <td width="23%" height="41" class="fila-det-border">Banco a Depositar (*):  </td>
                <td width="77%" class="fila-det-border"><input name="identificadorBanco" type="text" class="campo" id="identificadorBanco" value="<%=BCF.getIdentificadorBanco()%>" size="5" maxlength="3">
                     <input name="descripcionBanco" type="text" class="campo" id="descripcionBanco" value="<%=BCF.getDescripcionBanco()%>" size="60" maxlength="150"  >
                  <span class="fila-titulo-dos">
                  <input name="btnbancos" type="button" class="boton" id="btnbancos" style="cursor:pointer" onClick="abrirVentana('lov_cajaIdentificadoresDepositosEfectivo.jsp?propio=S&tipomov=C&cmpCodigo=identificadorBanco&cmpDescripcion=descripcionBanco&cmpCC1=cc1Banco&cmpCC2=cc2Banco&cmpModCC=modCCBanco', 'banco', 800, 400)" value="&gt;&gt;">
                </span></td>
              </tr>
              <tr class="fila-det"> 
                <td height="41" class="fila-det-border">Centros de Costos: </td>
                <td class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td colspan="2">CC1</td>
                    <td colspan="2">CC2</td>
                  </tr>
                  <tr class="fila-det">
                    <td width="8%"><input name="cc1Banco" type="text" class="campo" id="cc1Banco" value="<%=BCF.getCc1Banco()%>" size="5" maxlength="5" readonly  ></td>
                    <td width="43%"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="callCCBanco('../Contable/lov_centro_de_costos1.jsp?txtCod=cc1Banco&txtDesc=descCc1Banco')" style="cursor:pointer">
                    <input name="descCc1Banco" type="hidden" class="campo" id="descCc1Banco" value="<%=BCF.getDescCc1Banco()%>" size="30" maxlength="100" readonly="" >
                    <input name="modCCBanco" type="hidden" class="campo" id="modCCBanco" value="<%=BCF.isModCCBanco()%>" size="30" maxlength="100"  readonly></td>
                    <td width="8%"><input name="cc2Banco" type="text" class="campo" id="cc2Banco" value="<%=BCF.getCc2Banco()%>" size="5" maxlength="5" readonly ></td>
                    <td width="41%"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="callCCBanco('../Contable/lov_centro_de_costos1.jsp?txtCod=cc2Banco&txtDesc=descCc2Banco')" style="cursor:pointer">
                    <input name="descCc2Banco" type="hidden" class="campo" id="descCc2Banco" value="<%=BCF.getDescCc2Banco()%>" size="30" maxlength="100"  readonly></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td height="3" class="text-globales"></td>
                <td height="3" class="text-globales"></td>
              </tr>
              <tr class="fila-det"> 
                <td height="34" class="fila-det-border">Importe a Depositar: </td>
                <td class="fila-det-border"><input name="importeDeposito" type="text" class="campo" id="importeDeposito" value="<%=BCF.getImporteDeposito()%>" size="18" maxlength="12"  ></td>
              </tr>
              <tr class="fila-det">
                <td height="43" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Confirmar Dep&oacute;sito" class="boton"></td>
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

