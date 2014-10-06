<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.security.*"%>
<%@ page import="javax.naming.*"%>
<%@ page import="javax.naming.directory.*"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="ar.com.syswarp.validar.*"%>
<%@ page import="java.math.BigDecimal" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String usuario  = request.getParameter("usuario");
String password = request.getParameter("password");
String empresa  = request.getParameter("empresa");



String ipcliente =  request.getRemoteAddr();
String hostname  =  request.getRemoteHost();
 
			try {
			
			    
				// primero guardo el usuario en session
				session.setAttribute("usuario", usuario);
				session.setAttribute("empresa", empresa);
				
				//Strings str = new Strings();
				
				/*
				// INSTANCIAR EL MODULO ENTERPRISE
				javax.naming.Context contextEnt = new javax.naming.InitialContext();
				Object objectEnt = contextEnt.lookup("Enterprise");
				EnterpriseHome eHome = (EnterpriseHome) javax.rmi.PortableRemoteObject
						.narrow(objectEnt, EnterpriseHome.class);
				Enterprise ent = eHome.create();		
				*/		
     
				// INSTANCIAR EL MODULO GENERAL
				/*
				javax.naming.Context context = new javax.naming.InitialContext();
				Object object = context.lookup("General");
				GeneralHome sHome = (GeneralHome) javax.rmi.PortableRemoteObject
						.narrow(object, GeneralHome.class);
				General repo = sHome.create();
				*/
				GeneralBean repo = new GeneralBean();
				
				// VALIDO EL USUARIO
				BigDecimal idusuario = repo.getUsuarioValidacion(usuario, password);
				session.setAttribute("idusuario", idusuario);
			/*
			 * Utilidad: devolver el idusuario validado
			 *   Excepciones:
			 *   
			 *   -1 usuario invalido
			 *   -2 conexion a la base de datos
			 *   -3 salida por excepcion
			 *   -4 usuario inhabilitado 
			 *   0 superusuario
			 *   > 0 usuario
			 *   
			 */			   			
				
				if (idusuario.compareTo(new BigDecimal(-1)) == 0){				
				   response.sendRedirect("index.jsp?mensaje=Usuario Invalido");
				}   

				if (idusuario.compareTo(new BigDecimal(-2)) == 0){				
				   response.sendRedirect("index.jsp?mensaje=Error de Conexion a la base de Datos");
				}   
				
				if (idusuario.compareTo(new BigDecimal(-3)) == 0){				
				   response.sendRedirect("index.jsp?mensaje=Error General del sistema");
				}   

				if (idusuario.compareTo(new BigDecimal(-4)) == 0){				
				   response.sendRedirect("index.jsp?mensaje=Error Usuario Inhabilitado");
				}  
				
				// recupero si el usuario es jefe de area
				session.setAttribute("ismanager",repo.isManager(usuario)); 
				 
				
			    // recupero el puesto de trabajo que tiene asignado!
			    BigDecimal idpuesto = repo.getGlobalUsuariosPuesto(idusuario);
			    session.setAttribute("idpuesto", idpuesto);
			    
			    // recupero el last login
			    //String lastlogin = repo.getUltimaSesionUsuario(usuario);
			    //session.setAttribute("lastlogin", lastlogin);
			    // grabo nuevamenta un lastlogin
			    //repo.setUltimaSesionUsuario( usuario, ipcliente, hostname);
			    			    
				if (idpuesto.compareTo(new BigDecimal(0)) != 0){								
   				   List puesto =  repo.getGlobalpuestosPK(idpuesto,new BigDecimal(session.getAttribute("empresa").toString() ));
   				   Iterator iterPuesto= puesto.iterator();
   				   String nombrePuesto = "PUESTO ERRONEO";
   				   int r = 0;
                   while(iterPuesto.hasNext()){
                      ++r;
                      String[] sCampos = (String[]) iterPuesto.next(); 
                      nombrePuesto = sCampos[1];
                   }   
				   session.setAttribute("nombrePuesto", nombrePuesto);
				   
				   BigDecimal idcontadorfacturaA = repo.getContadorDocumentos(idpuesto,"idconta_facturasa");
				   session.setAttribute("idcontadorfacturaA", idcontadorfacturaA);

				   BigDecimal idcontadorfacturaB = repo.getContadorDocumentos(idpuesto,"idconta_facturasb");
				   session.setAttribute("idcontadorfacturaB", idcontadorfacturaB);

				   BigDecimal idcontadorfacturaC = repo.getContadorDocumentos(idpuesto,"idconta_facturasc");
				   session.setAttribute("idcontadorfacturaC", idcontadorfacturaC);

				   BigDecimal idcontadorrecibos = repo.getContadorDocumentos(idpuesto,"idconta_recibos");
				   session.setAttribute("idcontadorrecibos", idcontadorrecibos);

				   BigDecimal idcontadorremitos1 = repo.getContadorDocumentos(idpuesto,"idconta_remitos1");
				   session.setAttribute("idcontadorremitos1", idcontadorremitos1);

				   BigDecimal idcontadorremitos2 = repo.getContadorDocumentos(idpuesto,"idconta_remitos2");
				   session.setAttribute("idcontadorremitos2", idcontadorremitos2);

				   BigDecimal idcontadorremitos3 = repo.getContadorDocumentos(idpuesto,"idconta_remitos3");
				   session.setAttribute("idcontadorremitos3", idcontadorremitos3);

				   BigDecimal idcontadorremitos4 = repo.getContadorDocumentos(idpuesto,"idconta_remitos4");
				   session.setAttribute("idcontadorremitos4", idcontadorremitos4);
				   
				   // estos numeros de documentos son solamente para mostrarse en la pagina principal (nada mas).
				   //////////////////////////////////////////////////////////////////////////////////////////////

				   String numerofacturaA = repo.getProximoNumeroDocumento(idcontadorfacturaA, new BigDecimal(empresa));
				   session.setAttribute("numerofacturaA", numerofacturaA);
				   
				   String numerofacturaB = repo.getProximoNumeroDocumento(idcontadorfacturaB, new BigDecimal(empresa));
				   session.setAttribute("numerofacturaB", numerofacturaB);
				   
				   String numerofacturaC = repo.getProximoNumeroDocumento(idcontadorfacturaC, new BigDecimal(empresa));
				   session.setAttribute("numerofacturaC", numerofacturaC);
				   
				   String numerorecibo = repo.getProximoNumeroDocumento(idcontadorrecibos, new BigDecimal(empresa));
				   session.setAttribute("numerorecibo", numerorecibo);

				   String numeroremitos1 = repo.getProximoNumeroDocumento(idcontadorremitos1, new BigDecimal(empresa));
				   session.setAttribute("numeroremitos1", numeroremitos1);
					 /*
 				   System.out.println(" ------->                        <-------- ");
					 System.out.println(" -------> " + numeroremitos1 + " <-------- ");
 				   System.out.println(" ------->                        <-------- ");					 
           */
				   String numeroremitos2= repo.getProximoNumeroDocumento(idcontadorremitos2, new BigDecimal(empresa));
				   session.setAttribute("numeroremitos2", numeroremitos2);

				   String numeroremitos3= repo.getProximoNumeroDocumento(idcontadorremitos3, new BigDecimal(empresa));
				   session.setAttribute("numeroremitos3", numeroremitos3);

				   String numeroremitos4= repo.getProximoNumeroDocumento(idcontadorremitos4, new BigDecimal(empresa));
				   session.setAttribute("numeroremitos4", numeroremitos4);			   
				}

				// INSTANCIAR EL MODULO CONTABLE
				/* 
				Object objectContable = context.lookup("Contable");
				ContableHome sHomeContable = (ContableHome) javax.rmi.PortableRemoteObject
						.narrow(objectContable, ContableHome.class);
				Contable contableHome = sHomeContable.create();
				*/
				ContableBean contableHome = new ContableBean();
				 
				// PRUEBAS DE ALCANCE DE VARIABLES DE SESSION
				//ent.setTest(usuario);
				
				contableHome.setUsuario(usuario, new BigDecimal(session.getAttribute("empresa").toString() ));
				//System.out.println("TEST *** : " + ent.getTest());
				//System.out.println("prueba de conversion de numeros 3145895: " + repo.convertirNumerosALetras(3145895) );

				java.util.List variables = new java.util.ArrayList();
				variables = repo.getVariables(empresa);
				Iterator iterVariables = null;
				iterVariables = variables.iterator();
				while (iterVariables.hasNext()) {
					String[] sCampos = (String[]) iterVariables.next();
					String cmp_variable = sCampos[0];
					String cmp_valor = sCampos[1];
					session.setAttribute(cmp_variable, cmp_valor);
				}
				%>
<html>
	<head>

		<title>
			ejecutando validaciones
		</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
	</head>

	<body>
		<%if (contableHome.getEjercicioActivo(new BigDecimal(session.getAttribute("empresa").toString() )) < 0) {

				%>
		<script>
	  alert("No existe ejercicio activo.");
	</script>
		<%}
				//Guardar en session ejercicio activo, y su rango de fechas validas para operar.
				session.setAttribute("ejercicioActivo", contableHome
						.getEjercicioActivo(new BigDecimal(session.getAttribute("empresa").toString() ))
						+ "");
				session.setAttribute("fechaEjercicioActivoDesde", contableHome
						.getFechaEjercicioActivoDesde(new BigDecimal(session.getAttribute("empresa").toString() )));
				session.setAttribute("fechaEjercicioActivoHasta", contableHome
						.getFechaEjercicioActivoHasta(new BigDecimal(session.getAttribute("empresa").toString() )));
						
			   if(empresa==null) empresa ="";
			  // ent.setEmpresa(empresa);
			   //System.out.println("El usuario: " +  usuario +" ingresa a la empresa : "+ ent.getEmpresa() );						
			   response.sendRedirect("inicial.jsp");

			%>

	</body>
</html>

<%
  } catch (Exception ex) {
				java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
				java.io.PrintWriter pw = new java.io.PrintWriter(cw, true);
				ex.printStackTrace(pw);
			}

%>
