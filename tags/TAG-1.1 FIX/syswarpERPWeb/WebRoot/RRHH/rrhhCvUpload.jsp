<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*"%>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Properties" %>
<%@ page import="org.apache.log4j.*" %>


<%! /**
	 * Created on 01-jul-2005
	 * 
	 * @author EJV
	 * 
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */
	private String mensaje = "";
	private boolean error = false;
	private String filecv = "";
	private boolean upfile = true;
	

	private boolean procesaFicheros(HttpServletRequest req, HttpServletResponse out ) {

		HttpSession session = req.getSession();
		Properties props = new Properties();
    String idpostulante = session.getAttribute("idpostulante") == null ? "0" : session.getAttribute("idpostulante").toString() ; 
    boolean existeArchivo = false;
    
    error = false;
    mensaje = "";
		filecv = "";

		try {
		   
			if(idpostulante.equals("0")){
			  error = true;
			  mensaje = "Es necesario ingresar para poder actualizar CV.";
			  return false;			
			}  
		  
		  props.load(ar.com.syswarp.ejb.GeneralBean.class.getResourceAsStream("system.properties"));
		  String path = props.getProperty("rrhh.cv.path");;
			
			long MaxSize = 16777216; 
		  int  SizeThreshold = 4096;
	    // construimos el objeto que es capaz de parsear la petición
		  DiskFileUpload fileUpload = new DiskFileUpload();
	    // maximo numero de bytes
	    fileUpload.setSizeMax(MaxSize); // 
	    // tamaño por encima del cual los ficheros son escritos directamente en
		// disco
	    fileUpload.setSizeThreshold(SizeThreshold);
	    // directorio en el que se escribirán los ficheros con tamaño superior
		// al
	    // soportado fu.setRepositoryPath("/tmp");
	    // ordenamos procesar los ficheros
		List fileItems = fileUpload.parseRequest(req);
		
		//System.out.println("fileUpload.getRepositoryPath() : " + fileUpload.getRepositoryPath()  );
		
		if(fileItems == null || fileItems.size()== 0 ){
			error = true;
			mensaje = "Es necesario seleccionar un archivo.";
			return false;
		}
	   // Iteramos por cada fichero
		Iterator i = fileItems.iterator();
		FileItem actual = null;
		while (i.hasNext()){
			actual = (FileItem)i.next();
			//System.out.println("actual.getName() : " + actual.getName() );
			if (!actual.isFormField()) {	
					 
				String fileName = actual.getName();
				//System.out.println("fileName : " + fileName  );
			    // construimos un objeto file para recuperar el trayecto completo

        // Eliminar path del nombre del fileItem					
				if (fileName != null) {
					 fileName = org.apache.commons.io.FilenameUtils.getName(fileName);
				}
					
				File archivo = new File(fileName);
					
		 
				//-----------------------------------
				InputStream streamEntrada = actual.getInputStream();
				OutputStream streamSalida = new FileOutputStream(archivo);
				byte[] buffer = new byte[4096];
				int bytesRead;
				while ((bytesRead = streamEntrada.read(buffer)) >= 0) {
				  streamSalida.write(buffer, 0, bytesRead);
				}
				//-----------------------------------
					
				System.out.println("archivo.length() : " + archivo.length()  ); 
				
				if(archivo.length() < 1){
					error = true;
					mensaje = "Por favor, debe seleccionar un archivo.";
					return false;
				}
				
				if(archivo.length() > 1048576){
					error = true;
					mensaje = "El tamaño del archivo excede el permitido.";
					return false;
				}
			    // nos quedamos solo con el nombre y descartamos el path
				filecv = idpostulante + "-" + archivo.getName();
				File fichero = new File( path + filecv);
				actual.write(fichero);
				fileItems = null;
				existeArchivo = true;
			}
		}
		
	}
	catch(Exception e) {
		mensaje="Imposible cargar CV. Por favor intente mas tarde.";
		error = true;
		System.out.println("procesaFicheros - Exception: " + e );
		return false;
	}
	   return true;
	}
 %>
 <% 
    
		if(procesaFicheros(request, response)){
			response.sendRedirect("rrhhCvSeleccion.jsp?error="+error+"&mensaje=Carga correcta de CV.&filecv="+filecv+"&upfile="+upfile);
		}
		else{
			response.sendRedirect("rrhhCvSeleccion.jsp?error="+error+"&mensaje="+mensaje+"&upfile="+upfile);
		}
  %>