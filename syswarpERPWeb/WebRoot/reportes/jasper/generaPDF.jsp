<%@page language="java" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%//@ include file="session.jspf"%>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%
	try {

		out.clear();
		out = pageContext.pushBody(); 
		ServletOutputStream ouputStream = null; 
		Strings str = new Strings();
		//--
    //--
		String plantillaImpresionJRXML = request.getParameter("plantillaImpresionJRXML"); 
		String idempresa = Common.setNotNull( session.getAttribute("empresa") + "" );
        String accion = request.getParameter("accion"); 
	
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
		String genIIBB = Common.setNotNull(session.getAttribute("genIIBB") + "" );	
    /*
    System.out.println("genIIBB: " + genIIBB) ;	
    Enumeration en = session.getAttributeNames();
    while(en.hasMoreElements()) {
      String name = en.nextElement() + "";
      System.out.println("NAME: " + name +  " ~: " + session.getAttribute(name) ); 
    }
    */
    //--	 
		//-- Remitos 
		String remito_interno = request.getParameter("remito_interno");   
		String comprob_ms = request.getParameter("comprob_ms"); 
		String tipo = request.getParameter("tipo"); 
		String comprobante = request.getParameter("comprobante");
		// -- Ordenes compra
		String idoc = request.getParameter("idoc"); 
		// -- Pagos Egresos Directos
		String comprob_mt =  request.getParameter("comprob_mt"); 
		// -- Pedidos
		String idpedido_cabe = request.getParameter("idpedido_cabe");
		// -- Caja
		String identificador = request.getParameter("identificador");		
		String descripcion_identificador = request.getParameter("descripcion_identificador");				
		String fechadesde = request.getParameter("fechadesde");
		String fechahasta = request.getParameter("fechahasta");	
		String sucursaldesde = Common.setNotNull(request.getParameter("sucursaldesde")).equals("") ? "0": request.getParameter("sucursaldesde");
		String sucursalhasta = Common.setNotNull(request.getParameter("sucursalhasta")).equals("") ? "0": request.getParameter("sucursalhasta");	
		String fecha = request.getParameter("fecha");
        String totalAdelanto = Common.setNotNull(request.getParameter("totalAdelanto"));
		// -- Pagos
		BigDecimal nrointerno = new BigDecimal( Common.setNotNull( request.getParameter("nrointerno") ).equals("") ? "0"  : request.getParameter("nrointerno"));	  			
    // -- Pedidos/Remitos Clientes
		BigDecimal idremitoclientedesde = new BigDecimal( Common.setNotNull( request.getParameter("idremitoclientedesde") ).equals("") ? "0"  : request.getParameter("idremitoclientedesde"));	  			
		BigDecimal idremitoclientehasta = new BigDecimal( Common.setNotNull( request.getParameter("idremitoclientehasta") ).equals("") ? idremitoclientedesde.toString()  : request.getParameter("idremitoclientehasta"));
		BigDecimal idzona = new BigDecimal( Common.setNotNull( request.getParameter("idzona") ).equals("") ? "0"  : request.getParameter("idzona"));
		BigDecimal idcondicion = new BigDecimal( Common.setNotNull( request.getParameter("idcondicion") ).equals("") ? "0"  : request.getParameter("idcondicion"));		
		
		// Hoja Armado
		BigDecimal nrohojaarmado = new BigDecimal( Common.setNotNull( request.getParameter("nrohojaarmado") ).equals("") ? "0"  : request.getParameter("nrohojaarmado"));	  			
    //
	    BigDecimal idcliente = new BigDecimal( Common.setNotNull( request.getParameter("idcliente") ).equals("") ? "0"  : request.getParameter("idcliente"));
	 
		String depositosToCollection = request.getParameter("depositosToCollection");
		String clientesToCollection = request.getParameter("clientesToCollection");
		String preprospectosToCollection = request.getParameter("preprospectosToCollection");
		
		// proveedores
		String idproveedor = request.getParameter("idproveedor");
		String idproveedorhasta = request.getParameter("idproveedorhasta");
	
	
		BigDecimal anio = new BigDecimal( Common.setNotNull( request.getParameter("anio") ).equals("") ? "0"  : request.getParameter("anio"));
		BigDecimal mes = new BigDecimal( Common.setNotNull( request.getParameter("mes") ).equals("") ? "0"  : request.getParameter("mes"));
		//
		BigDecimal idtarjetacredito = new BigDecimal( Common.setNotNull( request.getParameter("idtarjetacredito") ).equals("") ? "0"  : request.getParameter("idtarjetacredito"));
        BigDecimal idclub = new BigDecimal( Common.setNotNull( request.getParameter("idclub") ).equals("") ? "0"  : request.getParameter("idclub"));
        //
        BigDecimal idtipoclie = new BigDecimal( Common.setNotNull( request.getParameter("idtipoclie") ).equals("") ? "0"  : request.getParameter("idtipoclie"));
		String rangoCharDesde = request.getParameter("rangoCharDesde");
		String rangoCharHasta = request.getParameter("rangoCharHasta");
        //
        BigDecimal idgeneracion = new BigDecimal( Common.setNotNull( request.getParameter("idgeneracion") ).equals("") ? "0"  : request.getParameter("idgeneracion"));   
 
        ReportBean repo = new ReportBean();

		Map parameters = new HashMap();

		parameters.put("idempresa",  idempresa );
		parameters.put("usuario",  usuario ); 
        parameters.put("accion",  accion ); 
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
		parameters.put("genIIBB",  genIIBB );   
		// --
		parameters.put("remito_interno",  remito_interno );
		parameters.put("comprob_ms",  comprob_ms );
		parameters.put("tipo",  tipo );
		parameters.put("comprobante",  comprobante );
		// --		
		parameters.put("idoc",  idoc );
		// --
		parameters.put("comprob_mt",  comprob_mt );	
		// --
		parameters.put("idpedido_cabe",  idpedido_cabe );	
		// --
		parameters.put("identificador",  identificador );	
		parameters.put("descripcion_identificador",  descripcion_identificador );	
		parameters.put("fechadesde",  fechadesde );	
		parameters.put("fechahasta",  fechahasta );	
		parameters.put("sucursaldesde",  sucursaldesde );	
		parameters.put("sucursalhasta",  sucursalhasta );	
		parameters.put("fecha",  fecha );		
		parameters.put("totalAdelanto",  totalAdelanto );		
		//-
		parameters.put("nrointerno",  nrointerno );		
		//-
		parameters.put("idremitoclientedesde",  idremitoclientedesde );		
		parameters.put("idremitoclientehasta",  idremitoclientehasta );	
		parameters.put("idzona",  idzona );	
		parameters.put("idcondicion",  idcondicion );											
		// --
		parameters.put("nrohojaarmado",  nrohojaarmado );
		
		parameters.put("idproveedor",  idproveedor );
		parameters.put("idproveedorhasta",  idproveedorhasta );											
		// --
		parameters.put("idcliente",  idcliente );
		// --
		parameters.put("anio",  anio );
		parameters.put("mes",  mes );
		// --
		parameters.put("idtarjetacredito",  idtarjetacredito );
		parameters.put("idclub",  idclub );		
		//--
        parameters.put("idtipoclie",  idtipoclie );
		parameters.put("rangoCharDesde",  rangoCharDesde );
		parameters.put("rangoCharHasta",  rangoCharHasta );			
        //--
		parameters.put("idgeneracion",  idgeneracion );
					
		// Tipo de parametro para operador "IN" debe ser del tipo Collection.
		if(depositosToCollection != null){
		  String[] auxDep = depositosToCollection.split("-");
		  Map map = new HashMap();
		  
		  for(int f=0;f<auxDep.length;f++) 
		    map.put(f+"",new Integer ( Integer.parseInt( auxDep[f] ) ) );   

		  Hashtable ht = new Hashtable();
		  ht.putAll(map); 
		  Collection col = ht.values();
		  parameters.put("depositos",  col  );
		}
		
		if(clientesToCollection != null){
		  String[] auxClientes = clientesToCollection.split("-");
		  Map map = new HashMap();
	  
		  for(int f=0;f<auxClientes.length;f++) {
		    map.put(f+"",new Long ( Long.parseLong( auxClientes[f] ) ) );  
          } 

		  Hashtable ht = new Hashtable();
		  ht.putAll(map); 
		  Collection col = ht.values();
		  parameters.put("clientesCollection",  col  );
		}
        //

		if(preprospectosToCollection != null){
		  String[] auxProspectos = preprospectosToCollection.split("-");
		  Map map = new HashMap();
	  
		  for(int f=0;f<auxProspectos.length;f++) {
		    map.put(f+"",new Long ( Long.parseLong( auxProspectos[f] ) ) );  
          } 

		  Hashtable ht = new Hashtable();
		  ht.putAll(map); 
		  Collection col = ht.values();
		  parameters.put("preprospectosCollection",  col  );
		}

		byte[] bytes = repo.getOpenReportImpresiones(plantillaImpresionJRXML, parameters);
		response.setContentType("application/pdf"); 
		response.setContentLength(bytes.length); 
		ouputStream = response.getOutputStream(); 
		ouputStream.write(bytes, 0, bytes.length); 
		ouputStream.flush(); 
		ouputStream.close();
		//-- agregado CEP 30/11/2009
		if(ouputStream != null) ouputStream = null;
		 

	}
	catch (Exception e) { 
		e.printStackTrace(); 
		System.out.println("Error:" +e.getMessage()); 
	}          
%>