/**
 * Created on 01-jul-2005
 * @author EJV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package ar.com.syswarp.servlet.upload;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import java.util.*;
import java.util.Properties;
//log4j
import org.apache.log4j.*;


public class UploadFichero extends HttpServlet {
	
	static Logger log = Logger.getLogger(UploadFichero.class);
	
	void depura(String cadena){
		log.error("El error es " + cadena);
	}
	public boolean procesaFicheros(HttpServletRequest req, HttpServletResponse out ) {

		HttpSession session = req.getSession();
		Properties props;	
		try {
		props = new Properties();
		props.load(UploadFichero.class.getResourceAsStream("upload.properties"));
		long MaxSize = Long.valueOf(props.getProperty("upload.MaxSize")).longValue(); 
		int  SizeThreshold = Integer.valueOf(props.getProperty("upload.SizeThreshold")).intValue();
	    // construimos el objeto que es capaz de parsear la perición
		DiskFileUpload fileUpload = new DiskFileUpload();
	    // maximo numero de bytes
	    fileUpload.setSizeMax(MaxSize); // 
	    // tamaño por encima del cual los ficheros son escritos directamente en disco
	    fileUpload.setSizeThreshold(SizeThreshold);
	    // directorio en el que se escribirán los ficheros con tamaño superior al 
	    // soportado fu.setRepositoryPath("/tmp");
	    // ordenamos procesar los ficheros
		List fileItems = fileUpload.parseRequest(req);
		if(fileItems == null || fileItems.size()== 0 ){
			session.setAttribute("mensaje","Es Necesario Seleccionar un Archivo.");			
			return false;
		}
		log.info("El número de ficheros subidos es: " + fileItems.size());
		
		
	    // Iteramos por cada fichero
		Iterator i = fileItems.iterator();
		FileItem actual = null;
		while (i.hasNext()){
			actual = (FileItem)i.next();
			String fileName = actual.getName();
		    //construimos un objeto file para recuperar el trayecto completo
			File fichero = new File(fileName);
		    // nos quedamos solo con el nombre y descartamos el path
			String path = props.getProperty("upload.Path");
						
			depura( path );
			fichero = new File( path +  fichero.getName());
			actual.write(fichero);
			fileItems = null;
		}
	}
	catch(Exception e) {
		session.setAttribute("mensaje","Hubo un error al Ejecutar el Upload.");
		log.error("Exception: " + e  + "---"  );
		return false;
	}
	   session.setAttribute("mensaje","Upload Finalizado Correctamente.");
	   return true;
	}

  
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		try {
		    if(procesaFicheros(request, response)){
		    	response.sendRedirect("cargaArchivo.jsp");
		    }
		    else{
				response.sendRedirect("cargaArchivo.jsp");
		    }
		}catch (Exception e){
			log.error("ERROR EN UPLOAD: " + e);
		}
		}
}

