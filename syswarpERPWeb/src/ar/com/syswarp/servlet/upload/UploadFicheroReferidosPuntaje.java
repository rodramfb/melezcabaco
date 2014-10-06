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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.util.*; // log4j
import org.apache.log4j.*;

import ar.com.syswarp.api.Common;

public class UploadFicheroReferidosPuntaje extends HttpServlet {

	static Logger log = Logger.getLogger(UploadFicheroReferidosPuntaje.class);

	private String mensaje = "";

	private String pathURL = "";

	private String tipoArchivo = "";

	public boolean procesaFicheros(HttpServletRequest request,
			HttpServletResponse out) {

		//HttpSession session = request.getSession();
		Properties props;
		String modulo = request.getParameter("modulo");
		tipoArchivo = request.getParameter("tipoArchivo");
		tipoArchivo = Common.setNotNull(tipoArchivo);

		try {
			props = new Properties();
			props.load(UploadFicheroReferidosPuntaje.class
					.getResourceAsStream("upload.properties"));

			// Check that we have a file upload request
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);

			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			List fileItems = upload.parseRequest(request);

			// Iteramos por cada fichero

			if (fileItems == null || fileItems.size() == 0) {
				mensaje = ("Es Necesario Seleccionar un Archivo.");
				return false;
			}

			Iterator i = fileItems.iterator();
			FileItem actual = null;
			while (i.hasNext()) {
				actual = (FileItem) i.next();
				if (actual.isFormField()) {
					continue;
				}

				//log.info("tipoArchivo: " + tipoArchivo );
				//log.info("actual.getContentType().toLowerCase(): " + actual.getContentType().toLowerCase() );
				
				if (!tipoArchivo.equals("")) {
					if (actual.getContentType().toLowerCase().indexOf(
							tipoArchivo.toLowerCase()) == -1) {
						mensaje = "Solo se admiten archivos tipo "
								+ tipoArchivo + ".";
						fileItems = null;
						return false;
					}
				}
				
				//log.info("tipoArchivo: " + tipoArchivo );

				String fileName = new java.sql.Date(Common.initObjectTime()) + "-" + actual.getName();
				// construimos un objeto file para recuperar el trayecto
				// completo
				File fichero = new File(fileName);
				// nos quedamos solo con el nombre y descartamos el path
				// String path = props.getProperty("upload.Path");
				String path = props.getProperty("upload.path.generico");
				fichero = new File(path.replaceAll("MODULO", modulo)
						+ fichero.getName());
				actual.write(fichero);

				pathURL = fichero.toURI().toURL().toString();
				pathURL = pathURL.substring(pathURL.indexOf(request
						.getContextPath())
						+ request.getContextPath().length() + 5);

			}

			fileItems = null;

		} catch (Exception e) {
			mensaje = "Se produjo una excepcion al realizar el upload.";
			log.error("ERROR UPLOAD: " + e + "");
			return false;
		}

		mensaje = "Upload ejecutado correctamente.";
		return true;

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			if (procesaFicheros(request, response)) {
				response
						.sendRedirect("../Clientes/bacoReferidosPuntajeUploadFile.jsp?estadoUpload=true&mensaje="
								+ mensaje
								+ "&path="
								+ pathURL
								+ "&tipoArchivo="
								+ tipoArchivo);
			} else {
				response
						.sendRedirect("../Clientes/bacoReferidosPuntajeUploadFile.jsp?estadoUpload=false&mensaje="
								+ mensaje + "&tipoArchivo=" + tipoArchivo);
			}
		} catch (Exception e) {
			log.error("ERROR EN UPLOAD: " + e);
		}
	}
}
