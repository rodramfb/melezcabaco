/**
 * Created on 01-jul-2005
 * @author EJV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package ar.com.syswarp.servlet.upload;

import java.io.*;
import java.math.BigDecimal;

import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ar.com.syswarp.api.*;

import java.util.*; // log4j
import org.apache.log4j.*;

public class UploadFicheroBlob extends HttpServlet {

	static final long serialVersionUID = 2009061810;

	static Logger log = Logger.getLogger(UploadFicheroBlob.class);

	private String mensaje = "OK";

	private String pathURL = "";

	private boolean soloImagen = false;

	private String tupla = "9999999";

	int totalFiles = 0;

	private List fileItems = new ArrayList();

	public boolean procesaFicheros(HttpServletRequest request,
			HttpServletResponse out) {

		try {

			Properties props;
			props = new Properties();
			props.load(UploadFicheroBlob.class
					.getResourceAsStream("upload.properties"));

			totalFiles = Integer.parseInt(props.getProperty("blob.totalFiles"));

			HttpSession session = request.getSession();
			// String modulo = request.getParameter("modulo");
			// String[] nombre = new String[totalFiles];
			String[] descripcion = new String[totalFiles];
			String[] principal = new String[totalFiles];
			BigDecimal idempresa = new BigDecimal(session
					.getAttribute("empresa")
					+ "");
			String usuario = session.getAttribute("usuario") + "";
			soloImagen = new Boolean(request.getParameter("soloImagen"))
					.booleanValue();
			tupla = request.getParameter("tupla");

			File[] files = new File[totalFiles];
			boolean existeFile = false;
			long blobMaxSize = 0L;

			blobMaxSize = Long.parseLong(props.getProperty("blob.MaxSize"));

			// Check that we have a file upload request
			// boolean isMultipart =
			// ServletFileUpload.isMultipartContent(request);

			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			fileItems = upload.parseRequest(request);

			// Iteramos por cada fichero

			if (fileItems == null || fileItems.size() == 0) {
				mensaje = ("Es Necesario Seleccionar un Archivo.");
				return false;
			}

			Iterator i = fileItems.iterator();
			FileItem actual = null;

			for (int t = 0; t < totalFiles; t++) {
				principal[t] = "N";
			}

			while (i.hasNext()) {

				actual = (FileItem) i.next();
				String field = actual.getFieldName();

				int index = -1;

				if (actual.isFormField()) {

					if (field.indexOf("descripcion_") != -1) {
						index = Integer.parseInt(field.substring(field
								.indexOf("_") + 1));
						descripcion[index] = actual.getString();
					} else if (field.equalsIgnoreCase("principal"))
						principal[Integer.parseInt(actual.getString())] = "S";

					continue;

				} else {
					index = Integer.parseInt(field
							.substring(field.indexOf("_") + 1));
				}

				if (actual.getSize() == 0) {
					continue;
				}

				if (soloImagen) {
					if (actual.getContentType().indexOf("image") == -1) {
						mensaje = "Solo se admiten archivos de imagen: "
								+ actual.getName();
						deleteFiles(files);
						return false;
					}
				}

				if (actual.getSize() > blobMaxSize) {
					mensaje = "Solo se admiten archivos de hasta "
							+ blobMaxSize + "B: " + actual.getName() + " / "
							+ actual.getSize() + " B";

					deleteFiles(files);
					return false;
				}

				existeFile = true;

				String ts = Calendar.getInstance().getTimeInMillis() + "_"
						+ index;
				// String fileName = actual.getName();
				String fileName = tupla
						+ "_"
						+ ts
						+ actual.getName().substring(
								actual.getName().lastIndexOf("."));
				// construimos un objeto file para recuperar el trayecto
				// completo
				File fichero = new File(fileName);
				// nos quedamos solo con el nombre y descartamos el path
				// String path = props.getProperty("upload.Path");
				String blobUploadPath = props.getProperty("blob.upload.path");
				fichero = new File(blobUploadPath + fichero.getName());
				actual.write(fichero);
				files[index] = fichero;

			}

			if (existeFile) {

				mensaje = Common.getGeneral().callGlobalBlobImagenesCreate(
						tupla, files, new String[totalFiles], descripcion,
						principal, idempresa, usuario);

			} else {
				mensaje = "Es necesario ingresar un archivo.";
			}

			fileItems = null;

		} catch (Exception e) {
			mensaje = "Se produjo una excepción al realizar el upload.";
			log.error("ERROR UPLOAD: " + e + "");
			return false;
		}

		if (mensaje.equalsIgnoreCase("OK"))
			mensaje = "Upload ejecutado correctamente.";

		return true;

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			if (procesaFicheros(request, response)) {
				response
						.sendRedirect("../General/uploadFileBlob.jsp?estadoUpload=true&mensaje="
								+ mensaje
								+ "&path="
								+ pathURL
								+ "&soloImagen="
								+ soloImagen + "&tupla=" + tupla);
			} else {
				response
						.sendRedirect("../General/uploadFileBlob.jsp?estadoUpload=false&mensaje="
								+ mensaje
								+ "&soloImagen="
								+ soloImagen
								+ "&tupla=" + tupla);
			}
		} catch (Exception e) {
			log.error("ERROR EN UPLOAD: " + e);
		}
	}

	private void deleteFiles(File[] files) throws ServletException, IOException {

		try {

			for (int r = totalFiles - 1; r > -1; r--) {
				if (files[r] != null)
					files[r].delete();
			}

			fileItems = null;

		} catch (Exception e) {
			log.error("deleteFiles(File[] files): " + e);
		}

	}
}
