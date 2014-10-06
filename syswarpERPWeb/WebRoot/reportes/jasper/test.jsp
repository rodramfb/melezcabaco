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
		String idempresa = str.esNulo( session.getAttribute("empresa") + "" );


        if(idempresa.equals("")) idempresa = "1";
         
 
		javax.naming.Context context = new javax.naming.InitialContext();
		Object object = context.lookup("Report");
		ReportHome sHome = (ReportHome) javax.rmi.PortableRemoteObject.narrow(object, ReportHome.class);
		Report repo = sHome.create();
		Map parameters = new HashMap();

		parameters.put("idempresa",  idempresa );

        System.out.println(" \n===========?? " +  idempresa + " ??===================="); 
        System.out.println(" \n===========????====================");  
        Map map = new HashMap();
        
        map.put("0",new Integer (1) );
        map.put("1",new Integer (2));
        map.put("2",new Integer (3));
        map.put("3",new Integer (43));
        map.put("4",new Integer (44));
        map.put("5",new Integer (45));
        map.put("6",new Integer (46));


			
		Hashtable ht = new Hashtable();
		ht.putAll(map);
		Collection col = ht.values();
		//col = map.values();
		Iterator iter = col.iterator();
		while(iter.hasNext())
			System.out.println("ITER:  " + iter.next());
			
		
		
		parameters.put("depositos",  col  );	 
		plantillaImpresionJRXML = "depositos_cheques_frame"; 
		//plantillaImpresionJRXML = "test"; 
		
		byte[] bytes = repo.getOpenReportImpresiones(plantillaImpresionJRXML, parameters);
		response.setContentType("application/pdf"); 
		response.setContentLength(bytes.length); 
		ouputStream = response.getOutputStream(); 
		ouputStream.write(bytes, 0, bytes.length); 
		ouputStream.flush(); 
		ouputStream.close(); 
	    //System.out.println("comprob_ms:" + comprob_ms);	   

	}
	catch (Exception e) { 
		e.printStackTrace(); 
		System.out.println("Error:" +e.getMessage()); 
	 }          
%>

