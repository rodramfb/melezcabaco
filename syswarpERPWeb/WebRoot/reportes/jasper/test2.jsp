<%@page language="java" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%//@ include file="session.jspf"%>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%> 
<%@ page import="java.text.*"%> 
<%@ page import="java.io.*"%>  
<%
	try {

		out.clear();
		out = pageContext.pushBody(); 
		ServletOutputStream ouputStream = null; 
        
		//--
  	    //--
		String plantillaImpresionJRXML = request.getParameter("plantillaImpresionJRXML"); 
		String idempresa = Common.setNotNull( session.getAttribute("empresa") + "" );
		String usuario = Common.setNotNull( session.getAttribute("usuario") + "" );
		BigDecimal ejercicio = new BigDecimal(session.getAttribute("ejercicioActivo")+"");
  	    //--
		String genNombreEmpresa = Common.setNotNull( session.getAttribute("genNombreEmpresa") + "" );
		String genCondicionFiscalEmpresa = Common.setNotNull(session.getAttribute("genCondicionFiscalEmpresa") + "" );
		String genCUITEmpresa = Common.setNotNull(session.getAttribute("genCUITEmpresa") + "" );
		String genDomicilioLegalEmpresa = Common.setNotNull(session.getAttribute("genDomicilioLegalEmpresa") + "" );
		String genClaveDNRPEmpresa = Common.setNotNull(session.getAttribute("genClaveDNRPEmpresa") + "" );
		String genLocalidadEmpresa = Common.setNotNull(session.getAttribute("genLocalidadEmpresa") + "" );
		String genProvinciaEmpresa = Common.setNotNull(session.getAttribute("genProvinciaEmpresa") + "" );
		String genNombreCompletoEmpresa = Common.setNotNull(session.getAttribute("genNombreCompletoEmpresa") + "" );
		String genActividadEmpresa = Common.setNotNull(session.getAttribute("genActividadEmpresa") + "" );
		String genTelefonosEmpresa = Common.setNotNull(session.getAttribute("genTelefonosEmpresa") + "" );		
  	    //--	 
		//-- Remitos 
		String idpedidoToCollection = request.getParameter("idpedidoToCollection");
        String idremitoclientedesde = request.getParameter("idremitoclientedesde");
        String idremitoclientehasta = request.getParameter("idremitoclientehasta");
 
		javax.naming.Context context = new javax.naming.InitialContext();
		Object object = context.lookup("Report");
		ReportHome sHome = (ReportHome) javax.rmi.PortableRemoteObject.narrow(object, ReportHome.class);
		Report repo = sHome.create();
		Map parameters = new HashMap();

		parameters.put("idempresa",  idempresa );
		parameters.put("usuario",  usuario ); 
		parameters.put("ejercicio",  ejercicio );
		parameters.put("genNombreEmpresa",  genNombreEmpresa );
		parameters.put("genCondicionFiscalEmpresa",  genCondicionFiscalEmpresa );
		parameters.put("genCUITEmpresa",  genCUITEmpresa );
		parameters.put("genDomicilioLegalEmpresa",  genDomicilioLegalEmpresa );
		parameters.put("genClaveDNRPEmpresa",  genClaveDNRPEmpresa );
		parameters.put("genLocalidadEmpresa",  genLocalidadEmpresa );
		parameters.put("genProvinciaEmpresa",  genProvinciaEmpresa );
		parameters.put("genNombreCompletoEmpresa",  genNombreCompletoEmpresa );
		parameters.put("genActividadEmpresa",  genActividadEmpresa );
		parameters.put("genTelefonosEmpresa",  genTelefonosEmpresa );  
		parameters.put("idremitoclientedesde",  idremitoclientedesde );
		parameters.put("idremitoclientehasta",  idremitoclientehasta );  
        //parameters.put("subreport_dir", repo.getJrxmlpath() );
        
  

		// --
        idpedidoToCollection = "77-79-80-81-82-83-84-85-86-87-88-89-90-91-92-93-94-95";
        plantillaImpresionJRXML="remitos_clientes_frame";  
        Calendar calendar = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String time = sdf.format( calendar.getTime() );
        String name = time;
        name += "-remitos-clientes-" + vectorId[0] + "-" +  vectorId[vectorId.length-1]  + ".pdf" ;  

        System.out.println("name: "  + name);
		// Tipo de parametro para operador "IN" debe ser del tipo Collection.
		if(idpedidoToCollection != null){
		  String[] vectorId = idpedidoToCollection.split("-");
		  Map map = new HashMap();
		  
		  for(int f=0;f<vectorId.length;f++) 
		    map.put(f+"",new Integer ( Integer.parseInt( vectorId[f] ) ) );

          name += "-remitos-clientes-" + vectorId[0] + "-" +  vectorId[vectorId.length-1]  + ".pdf" ;    

		  Hashtable ht = new Hashtable();
		  ht.putAll(map); 
		  Collection col = ht.values();
		  parameters.put("idpedido",  col  );
		}
		
		//byte[] bytes = repo.getOpenReportImpresiones(plantillaImpresionJRXML, parameters);
        //repo.saveByteToFile(bytes, repo.getClientesRemitosPath() + name );
        System.out.println("repo.getJrxmlpath(): NO MAS SUBREPORT_DIR *** / " ); 


	}
	catch (Exception e) { 
		//e.printStackTrace(); 
		System.out.println("Error:" +e.getMessage()); 
	}          
%>

