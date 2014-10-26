
<META HTTP-EQUIV = "pragma" CONTENT="no-cache">
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.Hashtable" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.Ref" %>
<%@ page import="java.sql.ResultSetMetaData" %>
<%@ page import="ar.com.syswarp.api.Common"%>
<%@ include file="session.jspf"%>
<%

java.util.List reportes = new java.util.ArrayList();	 
Iterator iterReportes   = null;
String idreporte = request.getParameter("idreporte");
String pathskin = session.getAttribute("pathskin").toString();
Hashtable htPar = new Hashtable();
Hashtable htParWithPar = new Hashtable();
htPar = (Hashtable) session.getAttribute("htPar");
String titulo_general = session.getAttribute("titulo_general").toString();
java.sql.ResultSet rsTablas = null;
Report repo = null;
String titu_parametro = "";
int nGrafico = 1;
String cGrafico = "";
try {
	Report report = Common.getReport();
   // capturo todas los parametros 
   Enumeration enumE = htPar.keys();
   while(enumE.hasMoreElements()){
       String values = (String) enumE.nextElement();
       String variable        = report.par2var(values);
       variable = request.getParameter(variable);       
       htParWithPar.put(values, variable);       
   }        
   // hasta aca hice request de todas las variables y cargue mi hashtable con todo listo
   
   // ahora ejecuto
   java.util.List     param = report.getParametros(htParWithPar);
   java.util.Iterator iterParam = param.iterator();   
   while(iterParam.hasNext()){ 
         String[] sCampos = (String[]) iterParam.next();
         String idparametro     = sCampos[0];         
         String descripcion     = sCampos[2];
         String parametro       = sCampos[1];
         String idtipoparametro = sCampos[3];         
         String variable = request.getParameter( report.par2var(parametro) );                  
         titu_parametro +=" "+descripcion+" : " + variable;         
         if (iterParam.hasNext()){
           titu_parametro += " , ";
         }             
    }
       
   
  
    rsTablas = report.getTablasReportes( new Integer(idreporte)  );
    
   }
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
  }    
%>
<html>

<head>
<title>TNX</title>

<META HTTP-EQUIV = "pragma" CONTENT="no-cache">
<link rel = "stylesheet" href = "<%= pathskin %>">
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr class="titulo">
    <td ><%=titulo_general%> - Reporte:</td>
  </tr>

  <tr class="subtitulo-dos">
    <td class="fila-det-verde" ><%=titu_parametro%> </td>
  </tr>
</table>


<%String titulo_tabla = "";%>
<%while(rsTablas.next()){
    boolean imprime_titulos_tablas = rsTablas.getBoolean("imprime_titulos");
    String qry = rsTablas.getString("query_consulta");
    String ds  = rsTablas.getString("iddatasource");        
    String idtabla = rsTablas.getString("idtabla");  
    titulo_tabla =rsTablas.getString("tabla");        
    String queryDepurado = report.setQueryParametros(qry, htParWithPar );        
    java.sql.ResultSet rsTbSalida = report.getConnectionDS(new Integer(ds), queryDepurado);
    java.sql.ResultSetMetaData rsMD = rsTbSalida.getMetaData();	
    
%>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <%boolean bTitulo = true;%>
  <%while(rsTbSalida.next()){%>
     <%if(bTitulo==true){%>        
         <%if(imprime_titulos_tablas==true){%>           
         <tr class="fila-det">
				  <td class="fila-det-border">
           <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
             <tr class="fila-det-border">
               <td class="fila-titulo-dos"> <%=titulo_tabla%>  </td>
             </tr>
           </table>           
          </td>
				 </tr>  
         <%}%> 
         <%bTitulo=false;%>
        <tr class="fila-encabezado">
            <%for(int i=1; i<= rsMD.getColumnCount(); i++ ){ %>
              <td class="fila-det-border"><%= rsMD.getColumnName(i) %></td>
            <%}%>  
        </tr>         
     <%}%>
  <tr class="fila-det">
    <%for(int i=1; i<= rsMD.getColumnCount(); i++ ){ %>
       <td class="fila-det-border"><%= rsTbSalida.getString(i) %></td>
     <%}%>  
  </tr>
  <%}%>
</table>   
  <%
   // ahora reviso a ver si tiene graficos relacionados con la tabla
    if(report.hasGraficosTablas( new Integer (idtabla) )){
       
       System.out.println("la tabla tiene graficos relacionados");
       java.sql.ResultSet rsGrafTablas = report.getGraficosTablas( new Integer (idtabla) );
       while(rsGrafTablas.next()){
         String strGrafico = "";
         boolean imprime_titulos_graficos = rsGrafTablas.getBoolean("imprime_titulos");
         String idgrafico        = rsGrafTablas.getString("idgrafico");
         String qry_grafico      = rsGrafTablas.getString("query_consulta");
         // cambio para que tome mas de un resultset
         String qry_grafico2      = rsGrafTablas.getString("query_consulta2");
         String qry_grafico3      = rsGrafTablas.getString("query_consulta3");
         
         String tipo_grafico     = rsGrafTablas.getString("idtipografico");
         String datasource       = rsGrafTablas.getString("iddatasource");
         String nombre_grafico   = rsGrafTablas.getString("grafico");         
         String queryDepuradoGrafico = report.setQueryParametros(qry_grafico, htParWithPar );     
         String queryDepuradoGrafico2 = "";
         String queryDepuradoGrafico3 = "";         

         if(qry_grafico2 != null) queryDepuradoGrafico2 = report.setQueryParametros(qry_grafico2, htParWithPar );     
         if(qry_grafico3 != null) queryDepuradoGrafico3 = report.setQueryParametros(qry_grafico3, htParWithPar );     

         ++nGrafico;
         cGrafico = session.getId() + nGrafico + ".jpg";
         java.sql.ResultSet rsG = report.getConnectionDS(new Integer(datasource),queryDepuradoGrafico);         
         // todo: discriminar por cualquier tipo de grafico
         if (tipo_grafico.equalsIgnoreCase("1") ){ // 3dpie         
         %>
         <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
           <tr>
             <td>
         <%
            strGrafico = report.GenerarGraficosPie3D(cGrafico,nombre_grafico,rsG);
            out.write("<img src=reportes/"+strGrafico+" border=0>"   );
         %>
             </td>
           </tr>             
         </table>
         <%           
         }

         if (tipo_grafico.equalsIgnoreCase("2") ){ // xy        
         %>
         <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
           <tr>
             <td>
         <%       
            strGrafico = report.GenerarGraficosXY(cGrafico,nombre_grafico,rsG);
            out.write("<img src=reportes/"+strGrafico+" border=0>"   );
         %>
             </td>
           </tr>             
         </table>
         <%           
         }

         if (tipo_grafico.equalsIgnoreCase("3") ){ // xy        
         %>
         <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
           <tr>
             <td>
         <%       
            strGrafico = report.GenerarGraficosBar3D(cGrafico,nombre_grafico,rsG);
            out.write("<img src=reportes/"+strGrafico+" border=0>"   );
         %>
             </td>
           </tr>             
         </table>
         <%           
         }
         
         // falta el tipo 4
         
         
                
         
//
         
       }
    }
    else
    {
       System.out.println("la tabla NO tiene graficos relacionados");
    }
  %>

<%}%>
</body>
</html>
