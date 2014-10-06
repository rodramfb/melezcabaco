package ar.com.syswarp.api;

import java.util.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJBException;
import javax.mail.Message;
import javax.mail.internet.*;

import org.apache.log4j.Logger;

import ar.com.syswarp.ejb.*;

public class Common {

	static Logger log = Logger.getLogger(Common.class);

	private static ClientesBean clientes = new ClientesBean();
	private static ContableBean contable = new ContableBean();
	private static CRMBean crm = new CRMBean();
	private static GeneralBean general = new GeneralBean();
	private static ProduccionBean produccion = new ProduccionBean();
	private static ProveedoresBean proveedores = new ProveedoresBean();
	private static ReportBean report = new ReportBean();
	private static RRHHBean rrhh = new RRHHBean();
	private static StockBean stock = new StockBean();
	private static TesoreriaBean tesoreria = new TesoreriaBean();
	private static BCBean bc = new BCBean();

	public Common() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static Contable getContable() {
		return contable;
	}

	public static Report getReport() {
		return report;
	}

	public static RRHH getRrhh() {
		return rrhh;
	}

	public static Proveedores getProveedores() {
		return proveedores;
	}

	public static Tesoreria getTesoreria() {
		return tesoreria;
	}

	public static Stock getStock() {
		return stock;
	}

	public static Produccion getProduccion() {
		return produccion;
	}

	public static Clientes getClientes() {
		return clientes;
	}

	public static General getGeneral() {
		return general;
	}

	public static CRM getCrm() {
		return crm;
	}
	public static BC getBc() {
		return bc;
	}

	
	// unicamente para interfaces. ;)

	public static Object setObjectToStrOrTime(Object obj, String conversion) {

		/**
		 * posibles conversiones - string to java.sql.timestamp - string to
		 * java.sql.date - string to java.util.date - java.sql.timestamp to
		 * string - java.sql.date to string - java.util.date to string
		 * 
		 * @see: *
		 * @param: <code>Object</code> obj
		 * @param: String conversion
		 * @return: Object
		 * 
		 * 
		 */
		String salida = "";
		try {
			if (obj == null || obj.toString().trim().equals("")) {
				if (conversion.toLowerCase().indexOf("str") == 0) {
					obj = null;
				} else {
					obj = "";
				}
			} else if (conversion.equalsIgnoreCase("strToJSTs")) {

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Timestamp ts = new Timestamp(sdf.parse(
						obj.toString().replaceAll("-", "/")).getTime());
				obj = ts;
				salida = ("(1)strToJSTs: " + obj);

			} else if (conversion.equalsIgnoreCase("strYMDToJSTs")) {

				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				Timestamp ts = new Timestamp(sdf.parse(
						obj.toString().replaceAll("-", "/")).getTime());
				obj = ts;
				salida = ("(1.1)strYMDToJSTs: " + obj);

			} else if (conversion.equalsIgnoreCase("strToJSTsWithHM")) {

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				Timestamp ts = new Timestamp(sdf.parse(
						obj.toString().replaceAll("-", "/")).getTime());

				obj = ts;
				salida = ("(2)strToJSTsWithHM: " + obj);

			} else if (conversion.equalsIgnoreCase("strToJSDate")) {

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				java.sql.Date jsd = new java.sql.Date(sdf.parse(
						obj.toString().replaceAll("-", "/")).getTime());

				obj = jsd;
				salida = ("(3)strToJSDate: " + obj);

			} else if (conversion.equalsIgnoreCase("strYMDToJSDate")) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				java.sql.Date jsd = new java.sql.Date(sdf.parse(
						obj.toString().replaceAll("-", "/")).getTime());
				SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");

				obj = jsd;
				salida = ("(3.1)strYMDToJSDate: " + obj);

			} else if (conversion.equalsIgnoreCase("strToJUDate")) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				java.util.Date jsd = new java.util.Date(sdf.parse(
						obj.toString()).getTime());

				obj = jsd;
				salida = ("(4)strToJUDate: " + obj);

			} else if (conversion.equalsIgnoreCase("strYMDToJUDate")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date jsd = new java.util.Date(sdf.parse(
						obj.toString().replaceAll("-", "/")).getTime());

				obj = jsd;
				salida = ("(4.1)strYMDToJUDate: " + obj);

			} else if (conversion.equalsIgnoreCase("JSTsToStr")) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String strFecha = sdf.format(obj);

				obj = strFecha;
				salida = ("(5)JSTsToStr: " + obj);

			} else if (conversion.equalsIgnoreCase("JSTsToStrWithHM")) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				String strFecha = sdf.format(obj);

				obj = strFecha;
				salida = ("(6)JSTsToStrWithHM: " + obj);

			} else if (conversion.equalsIgnoreCase("JSTsToStrOnlyHM")) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				String strFecha = sdf.format(obj);

				obj = strFecha;
				salida = ("(6.1)JSTsToStrOnlyHM: " + obj);

			} else if (conversion.equalsIgnoreCase("JSTsToStrOnlyHour")) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH");
				String strFecha = sdf.format(obj);

				obj = strFecha;
				salida = ("(6.2)JSTsToStrOnlyHour: " + obj);

			} else if (conversion.equalsIgnoreCase("JSTsToStrOnlyMin")) {
				SimpleDateFormat sdf = new SimpleDateFormat("mm");
				String strFecha = sdf.format(obj);

				obj = strFecha;
				salida = ("(6.3)JSTsToStrOnlyMin: " + obj);

			} else if (conversion.equalsIgnoreCase("JSDateToStr")) {

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String strFecha = sdf.format(obj);

				obj = strFecha;
				salida = ("(7)JSDateToStr: " + obj);

			} else if (conversion.equalsIgnoreCase("JUDateToStr")) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String strFecha = sdf.format(obj);

				obj = strFecha;
				salida = ("(8)JUDateToStr: " + obj);
			}
		} catch (Exception e) {
			log.error("setObjectToStrOrTime(Object " + obj + ", String "
					+ conversion + "): " + e);
		}
		return obj;
	}

	public static long initObjectTime() {
		try {

			Calendar calendar = new GregorianCalendar();
			return calendar.getTime().getTime();

		} catch (Exception e) {
			// TODO: handle exception
			return -1l;
		}
	}

	public static String initObjectTimeStr() {
		try {

			Calendar calendar = new GregorianCalendar();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(calendar.getTime());

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static Hashtable getHashEntidad(String nomMetodo) {
		Hashtable hashtable = new Hashtable();
		Proveedores proveedor = getProveedores();
		List lista = new ArrayList();
		Long objParam[] = new Long[] { new Long(250), new Long(0) };
		try {
			Method metodo = proveedor.getClass().getMethod(nomMetodo,
					new Class[] { long.class, long.class });
			lista = (List) metodo.invoke(proveedor, objParam);
			java.util.Iterator iterator = lista.iterator();
			while (iterator.hasNext()) {
				String[] dtCampos = (String[]) iterator.next();
				hashtable.put(dtCampos[0], dtCampos[1]);
			}

		} catch (Exception e) {
			log.error("getHashEntidad(String nomMetodo):" + e);
		}
		return hashtable;
	}

	public static Hashtable getHashEntidad(String nomMetodo,
			Class[] tipoArgumento, Object[] valorArgumento) {
		Hashtable hashtable = new Hashtable();
		Proveedores verificacion = getProveedores();
		List lista = new ArrayList();
		try {
			Method metodo = verificacion.getClass().getMethod(nomMetodo,
					tipoArgumento);
			lista = (List) metodo.invoke(verificacion, valorArgumento);
			java.util.Iterator iterator = lista.iterator();
			while (iterator.hasNext()) {
				String[] dtCampos = (String[]) iterator.next();
				hashtable.put(dtCampos[0], dtCampos[1]);
			}
		} catch (Exception e) {
			log
					.error("getHashEntidad(String nomMetodo, Class[] tipoArgumento, Object [] valorArgumento):"
							+ e);
		}
		return hashtable;
	}

	public static Hashtable getHashEntidadCrm(String nomMetodo,
			Class[] tipoArgumento, Object[] valorArgumento) {
		Hashtable hashtable = new Hashtable();
		CRM crm = getCrm();
		List lista = new ArrayList();
		try {
			Method metodo = crm.getClass().getMethod(nomMetodo, tipoArgumento);
			lista = (List) metodo.invoke(crm, valorArgumento);
			java.util.Iterator iterator = lista.iterator();
			while (iterator.hasNext()) {
				String[] dtCampos = (String[]) iterator.next();
				hashtable.put(dtCampos[0], dtCampos[1]);
			}
		} catch (Exception e) {
			log
					.error("getHashEntidad(String nomMetodo, Class[] tipoArgumento, Object [] valorArgumento):"
							+ e);
		}
		return hashtable;
	}

	public static void htRellenar(Hashtable htOrigen, Hashtable htDestino) {
		try {

			if (htOrigen != null && !htOrigen.isEmpty()) {

				Enumeration en = htOrigen.keys();

				while (en.hasMoreElements()) {

					Object obj = en.nextElement();

					htDestino.put(obj, htOrigen.get(obj));

				}

			}

		} catch (Exception e) {
			log.error("htRellenar(Hashtable htOrigen, Hashtable htDestino): "
					+ e);
		}
	}

	public static void ltRellenar(LinkedHashMap ltOrigen,
			LinkedHashMap ltDestino) {
		try {

			if (ltOrigen != null && !ltOrigen.isEmpty()) {

				Set keys = ltOrigen.keySet();
				Iterator iterKeys = keys.iterator();
				while (iterKeys.hasNext()) {
					Object obj = iterKeys.next();

					ltDestino.put(obj, ltOrigen.get(obj));

				}

			}

		} catch (Exception e) {
			log
					.error("ltRellenar(LinkedHashMap ltOrigen,LinkedHashMap ltDestino): "
							+ e);
		}
	}

	// 20120629 - EJV + CAMI - Modificación de la fc esNumerico
	// Dejaba pasar los formatos nnnD o nnnF o nnnL

	public static boolean esNumerico(String num) throws EJBException {
		try {

			Pattern patron = Pattern.compile("[^0-9.]");
			Matcher compara;

			compara = patron.matcher(num);
			boolean invalido = compara.find();

			if (invalido) {
				// Asegurar que solo ingresen valores numericos sin casteo
				// ALFABETICO
				return false;
			} else {
				Double.parseDouble(num);
				return true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;

		}
	}

	public static boolean esEntero(String num) throws EJBException {
		try {
			if (esNumerico(num)) {

				Long.parseLong(num);
				return true;
			} else
				return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public static Enumeration getSetSorted(Set set) {
		Enumeration enSorted = null;
		try {
			if (set != null && !set.isEmpty()) {
				Vector vecSort = new Vector(set);
				Collections.sort(vecSort);
				enSorted = vecSort.elements();
			}
		} catch (Exception e) {
			log.error("getSetSorted(set):" + e);
		}
		return enSorted;
	}

	public static boolean isFormatoFecha(String fecha) {
		boolean ok = true;

		// TODO: Pendiente a�os bisiestos - meses 30 / 31 dias.
		String faux = fecha.replaceAll("/", "");

		try {
			if (fecha.length() != 10) {
				ok = false;
			} else if (fecha.charAt(2) != '/' || fecha.charAt(5) != '/') {
				ok = false;
			} else if (!Common.esEntero(faux)) {
				ok = false;
			} else if (Integer.parseInt(fecha.substring(0, 2)) < 1
					|| Integer.parseInt(fecha.substring(0, 2)) > 31) {
				ok = false;
			} else if (Integer.parseInt(fecha.substring(3, 5)) < 1
					|| Integer.parseInt(fecha.substring(3, 5)) > 12) {
				ok = false;
			} else if (Integer.parseInt(fecha.substring(6)) < 0
					|| Integer.parseInt(fecha.substring(6)) > 9999) {
				ok = false;
			}

		} catch (Exception e) {
			ok = false;
			log.error("isFormatoFecha: " + e);
		}

		return ok;
	}

	public static boolean isFechaValida(String fecha) {
		boolean ok = true;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {

			sdf.setLenient(false);
			sdf.parse(fecha);

		} catch (Exception e) {
			ok = false;
			log.error("isFechaValida: " + e);
		}
		return ok;
	}

	public static boolean isValorEnRango(long valor, long piso, long techo) {

		boolean retorno = true;

		try {

			if (valor < piso || valor > techo) {
				retorno = false;
			}

		} catch (Exception e) {
			retorno = false;
			log.error("isValorEnRango" + e);
		}

		return retorno;
	}

	public static String strZero(String numero, int cantidadceros) {
		String salida = "" + numero;
		try {
			for (int i = salida.length(); i < cantidadceros; i++) {
				salida = "0" + salida;
			}
		} catch (Exception ex) {
			System.out
					.println("StrZero(numero, cantidadceros) salida por excepcion: "
							+ ex);
		}
		return salida;
	}

	public static boolean esPorcentaje(String porcentaje) {
		boolean fOk = false;
		try {
			if (esNumerico(porcentaje)) {
				if (Float.valueOf(porcentaje).floatValue() >= 0
						&& Float.valueOf(porcentaje).floatValue() <= 100) {
					fOk = true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("esPorcentaje(String porcentaje)" + e);
		}

		return fOk;
	}

	public static String getPaginacion(long totalPaginas, long totalRegistros,
			long paginaSeleccion, int limit, long offset) {
		String paginacion = "";

		long i = 0;
		try {

			if ((totalPaginas) <= 10) {

				for (i = 1; i <= totalPaginas; i++) {

					paginacion += "<a onClick=\"paginacion("
							+ (i)
							+ ")\" style=\"cursor:pointer\">  "
							+ (i == paginaSeleccion ? "<font color=\"#FFFFFF\">"
									+ i + "</font>"
									: i + "") + "  </a>, ";
				}

				if (paginacion.indexOf(',') > -1)
					paginacion = paginacion.substring(0, paginacion
							.lastIndexOf(","));

			} else {

				if (paginaSeleccion >= (totalPaginas - 10))
					paginaSeleccion = totalPaginas - 10;

				paginacion += "<a onClick=\"paginacion(1)\" style=\"cursor:pointer\">  &lt;&lt;Pri. </a>, ";

				paginacion += "<a onClick=\"paginacion("
						+ (paginaSeleccion - 10 < 1 ? 1 : paginaSeleccion - 10)
						+ ")\" style=\"cursor:pointer\">  &lt;&lt;Ant. </a>, ";

				for (i = paginaSeleccion; i <= (paginaSeleccion + 10); i++) {
					paginacion += "<a onClick=\"paginacion("
							+ (i)
							+ ")\" style=\"cursor:pointer\">  "
							+ (i == paginaSeleccion ? "<font color=\"#FFFFFF\">"
									+ i + "</font>"
									: i + "") + "  </a>,  ";
				}

				paginacion += "...";

				paginacion += "<a onClick=\"paginacion("
						+ (paginaSeleccion + 10)
						+ ")\" style=\"cursor:pointer\">  Sig.&gt;&gt; </a>,";

				paginacion += "<a onClick=\"paginacion(" + totalPaginas
						+ ")\" style=\"cursor:pointer\">  Ult. &gt;&gt; </a>";

			}

		} catch (Exception e) {
			log.error("getPaginacion():" + e);
		}
		return paginacion;
	}

	public static String colorSaldo(Object objeto, String clasePos,
			String claseNeg) throws EJBException {
		String clase = clasePos;
		try {
			if (objeto instanceof Float) {
				Float valor = (Float) objeto;
				if (valor.floatValue() < 0) {
					clase = claseNeg;
				}
			} else if (objeto instanceof Integer) {
				Integer valor = (Integer) objeto;
				if (valor.intValue() < 0) {
					clase = claseNeg;
				}
			} else {
				String valor = (String) objeto;
				if (Float.parseFloat(valor) < 0) {
					clase = claseNeg;
				}
			}
		} catch (Exception ex) {
			clase = "";
			log.info("salida por Exception [ colorSaldo() ]:" + ex);
		}
		return clase;
	}

	/*
	 * Validar Mail ----> EJV: Este metodo esta incompleto, momentaneamente se
	 * valida que haya al menos un dominio
	 */
	public static boolean isValidEmailAddress(String aEmailAddress) {
		if (aEmailAddress == null)
			return false;

		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(aEmailAddress);
			if (!hasNameAndDomain(aEmailAddress)) {
				result = false;
			}
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	private static boolean hasNameAndDomain(String aEmailAddress) {
		String[] tokens = aEmailAddress.split("@");
		return tokens.length == 2 && textHasContent(tokens[0])
				&& textHasContent(tokens[1]);
	}

	private static boolean textHasContent(String aText) {
		return aText != null && aText.trim().length() > 0;
	}

	/*
	 * <---- Validar Mail
	 */

	/*
	 * getAnios: Calcular anios a partir de dos fecha.
	 */
	public static int getAnios(java.util.Date fdesde, java.util.Date fhasta) {
		int anios = 0;
		long timems = 0;

		try {

			if (fdesde == null)
				fdesde = Calendar.getInstance().getTime();

			timems = fdesde.getTime() - fhasta.getTime();

			anios = new Long(timems / 1000l / 60l / 60l / 24l / 365l)
					.intValue();

		} catch (Exception e) {

			anios = -1;
			log.error("getAnios(fdesde, fhasta): " + e);

		}

		return anios;

	}

	// SOBRECARGADO - v.0
	public static String getNumeroFormateado(float numero, int enteros,
			int decimales) {
		String salida = "";

		try {

			String mask = "";
			// EJV 20081031
			BigDecimal clearScientificNotation = new BigDecimal(0.00);
			clearScientificNotation = clearScientificNotation
					.setScale(decimales);

			for (int i = 0; i < enteros; i++)
				mask += "#";

			if (decimales > 0)
				mask += ".";
			for (int i = 0; i < decimales; i++)
				mask += "0";

			DecimalFormat df = new DecimalFormat(mask);
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			df.setDecimalFormatSymbols(dfs);
			if (numero == 0) {
				salida = "0.";
				for (int i = 0; i < decimales; i++)
					salida += "0";
			} else
				salida = df.format(numero).toString();
			// EJV 20081031
			clearScientificNotation = new BigDecimal(salida);
			// clearScientificNotation =
			// clearScientificNotation.setScale(decimales);
			salida = clearScientificNotation.toString();
			//
		} catch (Exception e) {
			log.error("getNumeroFormateado( FLOAT , int , int  ): " + e);
		}

		return salida;
	}

	/**
	 * @FECHA: 20081031
	 * @AUTOR: EJV
	 * @COMENTARIO: Prueba y sobrecarga de metodo getNumeroFormateado para
	 *              evitar visualizacion en formato de notacion cientifica.
	 */

	// SOBRECARGADO - v.1
	public static String getNumeroFormateado(double numero, int enteros,
			int decimales) {

		String salida = "-1";

		try {

			String mask = "";
			BigDecimal clearScientificNotation = new BigDecimal(0.00);
			clearScientificNotation = clearScientificNotation
					.setScale(decimales);

			for (int i = 0; i < enteros; i++)
				mask += "#";

			if (decimales > 0)
				mask += ".";
			for (int i = 0; i < decimales; i++)
				mask += "0";

			DecimalFormat df = new DecimalFormat(mask);
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			df.setDecimalFormatSymbols(dfs);
			if (numero == 0) {
				salida = "0.";
				for (int i = 0; i < decimales; i++)
					salida += "0";
			} else
				salida = df.format(numero).toString();

			clearScientificNotation = new BigDecimal(salida);
			// clearScientificNotation =
			// clearScientificNotation.setScale(decimales);
			salida = clearScientificNotation.toString();

		} catch (Exception e) {
			log.error("getNumeroFormateado( DOUBLE , int , int  ): " + e);
		}

		return salida;
	}

	public static BigDecimal setPorcentejeDescuento(BigDecimal valor,
			BigDecimal porcentaje, int decimales) {

		// valor - (valor * (porcentaje / 100.0) )

		BigDecimal factor = new BigDecimal(0);
		BigDecimal descuento = valor;
		BigDecimal valorConDescuento = valor;

		try {

			factor = porcentaje.divide(new BigDecimal(100));
			descuento = descuento.multiply(factor);
			valorConDescuento = valorConDescuento.subtract(descuento);
			valorConDescuento = valorConDescuento.setScale(decimales,
					BigDecimal.ROUND_UP);

		} catch (Exception e) {
			// TODO: handle exception

			valorConDescuento = valor;
			log
					.error("setPorcentejeDescuento(BigDecimal porcentaje, int decimales ):"
							+ e);
		}

		return valorConDescuento;

	}

	// 20130322 - EJV -->
	public static BigDecimal setPorcentejeDescuento(BigDecimal valor,
			BigDecimal porcentaje, int decimales, int round) {

		// valor - (valor * (porcentaje / 100.0) )

		BigDecimal factor = new BigDecimal(0);
		BigDecimal descuento = valor;
		BigDecimal valorConDescuento = valor;

		try {

			factor = porcentaje.divide(new BigDecimal(100));
			descuento = descuento.multiply(factor);
			valorConDescuento = valorConDescuento.subtract(descuento);
			valorConDescuento = valorConDescuento.setScale(decimales, round);

		} catch (Exception e) {
			// TODO: handle exception

			valorConDescuento = valor;
			log
					.error("setPorcentejeDescuento(BigDecimal porcentaje, int decimales, int round. ):"
							+ e);
		}

		return valorConDescuento;

	}

	// <--

	public static BigDecimal getTotalHTArrayByIndex(Hashtable ht, int indice) {
		BigDecimal total = new BigDecimal(0);
		Enumeration en;
		try {
			if (ht != null) {
				en = ht.keys();
				while (en.hasMoreElements()) {
					Object clave = en.nextElement();
					String[] datos = (String[]) ht.get(clave);
					total = total.add(new BigDecimal(datos[indice]));
				}
			}
		} catch (Exception e) {

			log.error("getTotalHTArrayByIndex(Hashtable ht, int indice):");

		}

		return total;
	}

	public static String setNotNull(String str) {
		try {
			if (str == null)
				return "";
			else if (str.equalsIgnoreCase("null"))
				return "";
			return str;
		} catch (Exception e) {
			log.error("setNotNull: " + e);
			return "";
		}
	}

	public static java.sql.Timestamp getTesoFechaCaja(BigDecimal idempresa) {
		Timestamp fechatesoreria = null;
		try {

			String tesoFechaCaja = getGeneral().getValorSetupVariablesNoStatic(
					"tesoFechaCaja", idempresa);

			if (!isFormatoFecha(tesoFechaCaja)) {

				log.warn("getTesoFechaCaja(): Formato de Fecha inv�lido | "
						+ tesoFechaCaja);

			} else if (!isFechaValida(tesoFechaCaja)) {

				log.warn("getTesoFechaCaja(): Fecha inv�lida | "
						+ tesoFechaCaja);

			} else {

				fechatesoreria = (Timestamp) Common.setObjectToStrOrTime(
						tesoFechaCaja, "StrToJSTs");

			}

		} catch (Exception e) {
			log.error("getTesoFechaCaja(idempresa): " + e);
		}

		return fechatesoreria;
	}

	public static String getNivelStr(String patron, int cant) {
		String str = "";
		for (int i = 0; i < cant; i++) {
			str += patron;
		}
		return str;
	}

	/*
	 * 
	 * */

	public static boolean sendMail(String subject, String body, String to) {

		boolean isSend = false;

		try {

			Properties props = new Properties();
			props.load(Common.class.getResourceAsStream("mail.properties"));
			String cMailSmtp = setNotNull(props.getProperty("mail.smtpServer"))
					.trim();
			// lo manda el robot
			String cMailFrom = setNotNull(props.getProperty("mail.from"))
					.trim();
			String cMailFromName = setNotNull(
					props.getProperty("mail.from.name")).trim();
			//
			String cMailBCC = setNotNull(props.getProperty("mail.bcc")).trim();
			String cMailUsuario = setNotNull(props.getProperty("mail.usuario"))
					.trim();
			String cMailClave = setNotNull(props.getProperty("mail.clave"))
					.trim();

			String mailer = "JavaMail API";
			props.put("mail.smtp.auth", "true");

			javax.mail.Session session = javax.mail.Session.getDefaultInstance(
					props, null);

			javax.mail.Message msg = new javax.mail.internet.MimeMessage(
					session);
			msg.setFrom(new javax.mail.internet.InternetAddress(cMailFrom,
					cMailFromName));

			msg.setRecipients(Message.RecipientType.TO,
					javax.mail.internet.InternetAddress.parse(to, false));
			msg.setSubject(subject);
			msg.setText(body);
			msg.setHeader("X-Mailer", mailer);
			msg.setSentDate(new java.util.Date());
			javax.mail.Transport transporte = session.getTransport("smtp");
			transporte.connect(cMailSmtp, cMailUsuario, cMailClave);
			msg.saveChanges();
			transporte.sendMessage(msg, msg.getAllRecipients());
			props = null;
			transporte.close();
			isSend = true;

		} catch (IOException ioEx) {
			log.error("Error IO: " + ioEx);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}

		return isSend;
	}

	public static boolean sendMail(String subject, String body, String to,
			String title) {

		boolean isSend = false;

		try {

			Properties props = new Properties();
			props.load(Common.class.getResourceAsStream("mail.properties"));
			String cMailSmtp = setNotNull(props.getProperty("mail.smtpServer"))
					.trim();
			// lo manda el robot
			String cMailFrom = setNotNull(props.getProperty("mail.from"))
					.trim();
			String cMailFromName = title;
			//
			String cMailBCC = setNotNull(props.getProperty("mail.bcc")).trim();
			String cMailUsuario = setNotNull(props.getProperty("mail.usuario"))
					.trim();
			String cMailClave = setNotNull(props.getProperty("mail.clave"))
					.trim();

			String mailer = "JavaMail API";
			props.put("mail.smtp.auth", "true");

			javax.mail.Session session = javax.mail.Session.getDefaultInstance(
					props, null);

			javax.mail.Message msg = new javax.mail.internet.MimeMessage(
					session);
			msg.setFrom(new javax.mail.internet.InternetAddress(cMailFrom,
					cMailFromName));

			msg.setRecipients(Message.RecipientType.TO,
					javax.mail.internet.InternetAddress.parse(to, false));
			msg.setSubject(subject);
			msg.setText(body);
			msg.setHeader("X-Mailer", mailer);
			msg.setSentDate(new java.util.Date());
			javax.mail.Transport transporte = session.getTransport("smtp");
			transporte.connect(cMailSmtp, cMailUsuario, cMailClave);
			msg.saveChanges();
			transporte.sendMessage(msg, msg.getAllRecipients());
			props = null;
			transporte.close();
			isSend = true;

		} catch (IOException ioEx) {
			log.error("Error IO: " + ioEx);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}

		return isSend;
	}

	/*
	 *
	 * */
/*
	public static Hashtable getCotizacionActualMoneda(BigDecimal idmoneda,
			BigDecimal idempresa) {

		Hashtable htCotizacion = new Hashtable();
		htCotizacion.put("valor", "0");
		htCotizacion.put("mensaje", "");
		htCotizacion.put("valida", "false");

		List listCotizacion = new ArrayList();
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		java.sql.Date hoy = new java.sql.Date(cal.getTimeInMillis());
		java.sql.Date fechaCot = null;

		try {

			if (idmoneda.longValue() > 0) {

				listCotizacion = Common.getGeneral()
						.getGlobalMonedaCotizacionActual(idmoneda, idempresa);

				if (!listCotizacion.isEmpty()) {

					// gm.idmoneda, gm.moneda, COALESCE(gc.valor, 1.00) valor,
					// gc.fecha, gm.escotizable
					String[] datos = (String[]) listCotizacion.get(0);
					fechaCot = (datos[3] != null ? java.sql.Date
							.valueOf(datos[3]) : null);

					if (setNotNull(datos[4]).equalsIgnoreCase("S")) {

						if (fechaCot != null) {

							if (fechaCot.compareTo(hoy) == 0) {

								// Cotizacion OK
								htCotizacion.put("valor", datos[2]);
								htCotizacion.put("valida", "true");

							} else {
								// Cotizacion no correponde a la del dia

								htCotizacion
										.put("mensaje",
												"Fecha de cotizacion actual  no correponde a fecha del dia.");
								htCotizacion.put("valor", datos[2]);

							}

						} else {

							// no existe cotizacion para la moneda, defualt 1
							htCotizacion.put("mensaje",
									"No existe cotizacion para la moneda.");

						}

					} else {

						// moneda no es cotizable, default 0
						htCotizacion.put("valida", "true");
					}

				} else {

					htCotizacion.put("mensaje",
							"No fue posible recuperar cotizacion actual.");
				}

			}

		} catch (Exception e) {
			htCotizacion.put("mensaje",
					"(EX)No fue posible verificar cotizacion actual.");
			log.error("getCotizacionActualMoneda():" + e);
		}

		return htCotizacion;
	}
*/
	/**
	 * @author ejv
	 * @date 20110624
	 * 
	 *       Metodo de validacion de Clave de Identificacion Unica
	 *       [Tributaria(CUIT)-Laboral(CUIL)]
	 * 
	 * 
	 *       TIPO: Físicas (Hombres, Mujeres) o Jurídicas 27-mujer 20-hombre
	 *       23-puede ser ambos (se usa cuando hay otro número igual)
	 *       30-empresas
	 * 
	 * 
	 * */

	public static boolean validarClaveIdentificacionUnica(String clave) {

		boolean cuitCuilValido = false;

		String identificador = (clave == null ? "" : clave).replaceAll("-", "")
				.trim();
		String tipo = "";
		String numero = "";
		String digitoIngresado = "";
		int digitoValido = -1;
		Hashtable htTiposValidos = new Hashtable();

		htTiposValidos.put("27", "MUJER");
		htTiposValidos.put("20", "HOMBRE");
		htTiposValidos.put("23", "AMBOS");
		// --
		htTiposValidos.put("24", "HOMBRE?");
		htTiposValidos.put("30", "EMPRESA");
		// --
		htTiposValidos.put("33", "EMPRESA?");
		htTiposValidos.put("34", "EMPRESA?");

		try {

			if (!esEntero(identificador)) {
				// Caracteres invalidos
				log
						.warn("validarClaveIdentificacionUnica: Caracteres invalidos en CUIT-CUIL - "
								+ clave);
			} else if (identificador.length() < 4) {
				// Longitud minima
				log
						.warn("validarClaveIdentificacionUnica: Longitud no alcanza la minima CUIT-CUIL - "
								+ clave);

			} else if (identificador.length() > 11) {
				// Longitud minima
				log
						.warn("validarClaveIdentificacionUnica: Longitud excede la maxima CUIT-CUIL - "
								+ clave);
			} else {

				tipo = identificador.substring(0, 2);
				numero = identificador.substring(2, identificador.length() - 1);
				digitoIngresado = identificador.substring(identificador
						.length() - 1);

				if (!htTiposValidos.containsKey(tipo)) {

					// Tipo invalido
					log
							.warn("validarClaveIdentificacionUnica: Tipo invalido CUIT-CUIL - "
									+ clave);

				} else {

					numero = tipo + strZero(numero, 8);
					int suma = 0;
					int modSumaOnce = 0;
					for (int i = 9, factor = 2; i >= 0; i--, factor++) {
						if (factor > 7)
							factor = 2;

						if (i == 9) {
							suma += Integer.parseInt(numero.substring(i))
									* factor;

						} else {
							suma += Integer
									.parseInt(numero.substring(i, i + 1))
									* factor;
						}

					}

					modSumaOnce = suma % 11;

					digitoValido = 11 - modSumaOnce;

					if (digitoValido == 11)
						digitoValido = 0;
					if (digitoValido == 10)
						digitoValido = 9;

					if (digitoValido == Integer.parseInt(digitoIngresado)) {
						cuitCuilValido = true;
						log
								.info("validarClaveIdentificacionUnica: DIGITO CORRECTO :  "
										+ digitoValido
										+ " / "
										+ digitoIngresado);

					} else
						log
								.info("validarClaveIdentificacionUnica: DIGITO INVALIDO :  "
										+ digitoValido
										+ " / "
										+ digitoIngresado);

				}

			}

		} catch (Exception e) {
			log.error("validarClaveIdentificacionUnica(): " + e);
		}

		return cuitCuilValido;

	}

	// 20120710 - EJV - Mantis 842 -->
	// Metodo sin implicancias en calculos, redondeos u alguna otra variable que
	// modifique valores. Solo se usa con el fin de manipular la cantidad de
	// decimales a VISUALIZAR desde la capa de visualizacion (paginas )
	public static String visualNumero(String numero, int decimal) {

		String nroAux = "";

		try {

			if (((numero.indexOf('.') + 1) + decimal) <= numero.length())
				nroAux = numero.substring(0,
						((numero.indexOf('.') + 1) + decimal));
			else
				nroAux = numero;

		} catch (Exception e) {
			log.error("visualNumero ( " + numero + ",  " + decimal + " ) : "
					+ e);
			return numero;
		}

		return nroAux;
	}

	// 20130322 - EJV -->

	public static BigDecimal setAplicarPorcIVA(BigDecimal valorsiniva,
			BigDecimal porcentaje, int escala) {

		BigDecimal valorconiva = new BigDecimal(0);
		BigDecimal factor = new BigDecimal(1);

		try {

			factor = factor.add(porcentaje.divide(new BigDecimal(100)));
			valorconiva = valorsiniva.multiply(factor);
			valorconiva = valorconiva.setScale(escala,
					BigDecimal.ROUND_HALF_EVEN);

			String x = String.valueOf(Common.redondeo(
					valorconiva.doubleValue(), 2));

			valorconiva = new BigDecimal(x);

		} catch (Exception e) {
			log.error("setAplicarPorcIVA: " + e);
		}

		return valorconiva;
	}

	// <--

	public static double redondeo(double val, int places) {

		long factor = (long) Math.pow(10, places);

		// Shift the decimal the correct number of places
		// to the right.
		val = val * factor;

		// Round to the nearest integer.
		long tmp = Math.round(val);

		// Shift the decimal the correct number of places
		// back to the left.
		return (double) tmp / factor;
	}

	public static int getUltimoDiaMes(java.util.Date fecha) {

		int ultimoDia = 0;
		Calendar cal = new GregorianCalendar();
		cal.setTime(fecha);

		try {

			ultimoDia = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		} catch (Exception e) {

			log.error("getUltimoDiaMes(java.util.Date fecha) : " + e);

		}

		return ultimoDia;
	}
	public static boolean isCCVSTypeMatchIDSistema(BigDecimal idtarjetacredito,
			String CCVSType) {

		boolean corresponde = false;

		Hashtable ht = new Hashtable();
		Object valorCCVSType = "";
		BigDecimal idtarjetaAuxiliar = new BigDecimal(0);

		ht.put("VISA", "1");
		ht.put("MASTERCARD", "2");
		ht.put("DINERS CLUB", "3");
		ht.put("AMERICAN EXPRESS", "4");
		ht.put("CABAL", "6");
		// ------------------------------------------------
		ht.put("JCB", "-100");
		ht.put("Carte Blanche", "-200");
		ht.put("Australian BankCard", "-300");
		ht.put("Discover/Novus", "-400");

		/*
		 * -------------------------------- IdGeneradosEnSistemaDelta
		 * --------------------------------1;"VISA"
		 * --------------------------------2;"MASTERCARD"
		 * --------------------------------3;"DINERS"
		 * --------------------------------4;"AMERICAN EXPRESS"
		 * --------------------------------5;"VISA ELECTRON"
		 * --------------------------------6;"CABAL"
		 * --------------------------------7;"FUEGUINA"
		 */

		try {

			valorCCVSType = ht.get(CCVSType.toUpperCase());
			idtarjetaAuxiliar = new BigDecimal(
					valorCCVSType != null ? valorCCVSType.toString() : "0");

			if (idtarjetaAuxiliar.intValue() == idtarjetacredito.intValue())
				corresponde = true;

			log.info("idtipotarjeta: " + idtarjetacredito);
			log.info("idtarjetaAuxiliar: " + idtarjetaAuxiliar);
			log.info("CCVSType: " + CCVSType);

		} catch (Exception e) {
			corresponde = false;
			log.error("isCCVSTypeMatchIDSistema: " + e);
		}

		log.info("corresponde: " + corresponde);
		return corresponde;

	}

	public static boolean sendMailRegalos(String subject, String body,
			String sector) {

		boolean isSend = false;

		try {

			String[] toSector;

			Properties props = new Properties();
			props.load(Common.class.getResourceAsStream("mail.properties"));
			String cMailSmtp = setNotNull(props.getProperty("mail.smtpServer"))
					.trim();

			// lo manda el robot
			String cMailFrom = setNotNull(
					props.getProperty("mail.regalos.from")).trim();
			String cMailFromName = setNotNull(
					props.getProperty("mail.regalos.from.name")).trim();

			//
			String cMailBCC = setNotNull(props.getProperty("mail.regalos.bcc"))
					.trim();
			String cMailUsuario = setNotNull(
					props.getProperty("mail.regalos.usuario")).trim();
			String cMailClave = setNotNull(
					props.getProperty("mail.regalos.clave")).trim();
			String authSmtp = setNotNull(props.getProperty("mail.smtp.auth"))
					.trim();

			toSector = setNotNull(
					props.getProperty("mail.sector." + sector + ".to")).trim()
					.split(";");

			InternetAddress[] addresses = new InternetAddress[toSector.length];

			String mailer = "JavaMail API";
			// Por el momento no es necesaria la autenticacion de correo SMTP
			props.put("mail.smtp.auth", authSmtp);

			javax.mail.Session session = javax.mail.Session.getDefaultInstance(
					props, null);

			javax.mail.Message msg = new javax.mail.internet.MimeMessage(
					session);
			msg.setFrom(new javax.mail.internet.InternetAddress(cMailFrom,
					cMailFromName));

			// msg.setRecipients(Message.RecipientType.TO,
			// javax.mail.internet.InternetAddress.parse(to, false));
			//
			for (int i = 0; i < toSector.length; i++) {
				log.info("I (" + i + "): " + toSector[i]);

				try {
					addresses[i] = new InternetAddress(toSector[i]);
				} catch (Exception e) {
					log.info("I (" + i + "): " + toSector[i] + " EX: " + e);
				}

			}
			msg.setRecipients(Message.RecipientType.TO, addresses);
			//
			msg.setSubject(subject);
			msg.setText(body);
			msg.setHeader("X-Mailer", mailer);
			msg.setSentDate(new java.util.Date());
			javax.mail.Transport transporte = session.getTransport("smtp");

			transporte.connect(cMailSmtp, cMailUsuario, cMailClave);

			msg.saveChanges();
			transporte.sendMessage(msg, msg.getAllRecipients());
			props = null;
			transporte.close();
			isSend = true;

		} catch (IOException ioEx) {
			log.error("Error IO: " + ioEx);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Salida por exception: " + ex);
		}

		return isSend;
	}
	

}

/*
 * 
 * ------------------------------------------- version anterior.
 * 
package ar.com.syswarp.api;

import java.util.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJBException;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.*;

import org.apache.log4j.Logger;

import ar.com.syswarp.ejb.*;

public class Common {

	static Logger log = Logger.getLogger(Common.class);

	public Common() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static Contable getContable() {
		Contable contable = null;
		try {
			javax.naming.Context context = new javax.naming.InitialContext();
			Object objContable = context.lookup("Contable");
			ContableHome rh = (ContableHome) javax.rmi.PortableRemoteObject
					.narrow(objContable, ContableHome.class);
			contable = rh.create();

		} catch (Exception e) {
			log.error("getContable(): " + e);
		}
		return contable;
	}

	public static Report getReport() {
		Report report = null;
		try {
			javax.naming.Context context = new javax.naming.InitialContext();
			Object objReport = context.lookup("Report");
			ReportHome rh = (ReportHome) javax.rmi.PortableRemoteObject.narrow(
					objReport, ReportHome.class);
			report = rh.create();

		} catch (Exception e) {
			log.error("getReport(): " + e);
		}
		return report;
	}

	public static RRHH getRrhh() {
		RRHH rrhh = null;
		try {
			javax.naming.Context context = new javax.naming.InitialContext();
			Object objRRHH = context.lookup("RRHH");
			RRHHHome rh = (RRHHHome) javax.rmi.PortableRemoteObject.narrow(
					objRRHH, RRHHHome.class);
			rrhh = rh.create();

		} catch (Exception e) {
			log.error("getContable(): " + e);
		}
		return rrhh;
	}

	public static Proveedores getProveedores() {
		Proveedores proveedores = null;
		try {
			javax.naming.Context context = new javax.naming.InitialContext();
			Object objProveedores = context.lookup("Proveedores");
			ProveedoresHome rh = (ProveedoresHome) javax.rmi.PortableRemoteObject
					.narrow(objProveedores, ProveedoresHome.class);
			proveedores = rh.create();

		} catch (Exception e) {
			log.error("getProveedores(): " + e);
		}
		return proveedores;
	}

	public static Tesoreria getTesoreria() {
		Tesoreria tesoreria = null;
		try {
			javax.naming.Context context = new javax.naming.InitialContext();
			Object objTesoreria = context.lookup("Tesoreria");
			TesoreriaHome rh = (TesoreriaHome) javax.rmi.PortableRemoteObject
					.narrow(objTesoreria, TesoreriaHome.class);
			tesoreria = rh.create();

		} catch (Exception e) {
			log.error("getTesoreria(): " + e);
		}
		return tesoreria;
	}

	public static Stock getStock() {
		Stock stock = null;
		try {
			javax.naming.Context context = new javax.naming.InitialContext();
			Object objStock = context.lookup("Stock");
			StockHome rh = (StockHome) javax.rmi.PortableRemoteObject.narrow(
					objStock, StockHome.class);
			stock = rh.create();

		} catch (Exception e) {
			log.error("getStock(): " + e);
		}
		return stock;
	}

	public static Produccion getProduccion() {
		Produccion produccion = null;
		try {
			javax.naming.Context context = new javax.naming.InitialContext();
			Object objProduccion = context.lookup("Produccion");
			ProduccionHome rh = (ProduccionHome) javax.rmi.PortableRemoteObject
					.narrow(objProduccion, ProduccionHome.class);
			produccion = rh.create();

		} catch (Exception e) {
			log.error("getProduccion(): " + e);
		}
		return produccion;
	}

	public static Clientes getClientes() {
		Clientes clientes = null;
		try {
			javax.naming.Context context = new javax.naming.InitialContext();
			Object objClientes = context.lookup("Clientes");
			ClientesHome rh = (ClientesHome) javax.rmi.PortableRemoteObject
					.narrow(objClientes, ClientesHome.class);
			clientes = rh.create();

		} catch (Exception e) {
			log.error("getClientes(): " + e);
		}
		return clientes;
	}

	public static General getGeneral() {
		General general = null;
		try {
			javax.naming.Context context = new javax.naming.InitialContext();
			Object objGeneral = context.lookup("General");
			GeneralHome rh = (GeneralHome) javax.rmi.PortableRemoteObject
					.narrow(objGeneral, GeneralHome.class);
			general = rh.create();

		} catch (Exception e) {
			log.error("getClientes(): " + e);
		}
		return general;
	}

	public static CRM getCrm() {
		CRM crm = null;
		try {
			javax.naming.Context context = new javax.naming.InitialContext();
			Object objCRM = context.lookup("CRM");
			CRMHome rh = (CRMHome) javax.rmi.PortableRemoteObject.narrow(
					objCRM, CRMHome.class);
			crm = rh.create();

		} catch (Exception e) {
			log.error("getCrm(): " + e);
		}
		return crm;
	}

	// unicamente para interfaces. ;)
	public static BC getBc() {
		BC bc = null;
		try {
			javax.naming.Context context = new javax.naming.InitialContext();
			Object objBC = context.lookup("BC");
			BCHome rh = (BCHome) javax.rmi.PortableRemoteObject.narrow(objBC,
					BCHome.class);
			bc = rh.create();

		} catch (Exception e) {
			log.error("getBc(): " + e);
		}
		return bc;
	}

	public static Object setObjectToStrOrTime(Object obj, String conversion) {
*/
		/**
		 * posibles conversiones - string to java.sql.timestamp - string to
		 * java.sql.date - string to java.util.date - java.sql.timestamp to
		 * string - java.sql.date to string - java.util.date to string
		 * 
		 * @see: *
		 * @param: <code>Object</code> obj
		 * @param: String conversion
		 * @return: Object
		 * 
		 * 
		 */
/*
		String salida = "";
		try {
			if (obj == null || obj.toString().trim().equals("")) {
				if (conversion.toLowerCase().indexOf("str") == 0) {
					obj = null;
				} else {
					obj = "";
				}
			} else if (conversion.equalsIgnoreCase("strToJSTs")) {

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Timestamp ts = new Timestamp(sdf.parse(
						obj.toString().replaceAll("-", "/")).getTime());
				obj = ts;
				salida = ("(1)strToJSTs: " + obj);

			} else if (conversion.equalsIgnoreCase("strYMDToJSTs")) {

				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				Timestamp ts = new Timestamp(sdf.parse(
						obj.toString().replaceAll("-", "/")).getTime());
				obj = ts;
				salida = ("(1.1)strYMDToJSTs: " + obj);

			} else if (conversion.equalsIgnoreCase("strToJSTsWithHM")) {

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				Timestamp ts = new Timestamp(sdf.parse(
						obj.toString().replaceAll("-", "/")).getTime());

				obj = ts;
				salida = ("(2)strToJSTsWithHM: " + obj);

			} else if (conversion.equalsIgnoreCase("strToJSDate")) {

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				java.sql.Date jsd = new java.sql.Date(sdf.parse(
						obj.toString().replaceAll("-", "/")).getTime());

				obj = jsd;
				salida = ("(3)strToJSDate: " + obj);

			} else if (conversion.equalsIgnoreCase("strYMDToJSDate")) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				java.sql.Date jsd = new java.sql.Date(sdf.parse(
						obj.toString().replaceAll("-", "/")).getTime());
				SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");

				obj = jsd;
				salida = ("(3.1)strYMDToJSDate: " + obj);

			} else if (conversion.equalsIgnoreCase("strToJUDate")) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				java.util.Date jsd = new java.util.Date(sdf.parse(
						obj.toString()).getTime());

				obj = jsd;
				salida = ("(4)strToJUDate: " + obj);

			} else if (conversion.equalsIgnoreCase("strYMDToJUDate")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date jsd = new java.util.Date(sdf.parse(
						obj.toString().replaceAll("-", "/")).getTime());

				obj = jsd;
				salida = ("(4.1)strYMDToJUDate: " + obj);

			} else if (conversion.equalsIgnoreCase("JSTsToStr")) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String strFecha = sdf.format(obj);

				obj = strFecha;
				salida = ("(5)JSTsToStr: " + obj);

			} else if (conversion.equalsIgnoreCase("JSTsToStrWithHM")) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				String strFecha = sdf.format(obj);

				obj = strFecha;
				salida = ("(6)JSTsToStrWithHM: " + obj);

			} else if (conversion.equalsIgnoreCase("JSTsToStrOnlyHM")) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				String strFecha = sdf.format(obj);

				obj = strFecha;
				salida = ("(6.1)JSTsToStrOnlyHM: " + obj);

			} else if (conversion.equalsIgnoreCase("JSTsToStrOnlyHour")) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH");
				String strFecha = sdf.format(obj);

				obj = strFecha;
				salida = ("(6.2)JSTsToStrOnlyHour: " + obj);

			} else if (conversion.equalsIgnoreCase("JSTsToStrOnlyMin")) {
				SimpleDateFormat sdf = new SimpleDateFormat("mm");
				String strFecha = sdf.format(obj);

				obj = strFecha;
				salida = ("(6.3)JSTsToStrOnlyMin: " + obj);

			} else if (conversion.equalsIgnoreCase("JSDateToStr")) {

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String strFecha = sdf.format(obj);

				obj = strFecha;
				salida = ("(7)JSDateToStr: " + obj);

			} else if (conversion.equalsIgnoreCase("JUDateToStr")) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String strFecha = sdf.format(obj);

				obj = strFecha;
				salida = ("(8)JUDateToStr: " + obj);
			}
		} catch (Exception e) {
			log.error("setObjectToStrOrTime(Object " + obj + ", String "
					+ conversion + "): " + e);
		}
		return obj;
	}

	public static long initObjectTime() {
		try {

			Calendar calendar = new GregorianCalendar();
			return calendar.getTime().getTime();

		} catch (Exception e) {
			// TODO: handle exception
			return -1l;
		}
	}

	public static String initObjectTimeStr() {
		try {

			Calendar calendar = new GregorianCalendar();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(calendar.getTime());

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static Hashtable getHashEntidad(String nomMetodo) {
		Hashtable hashtable = new Hashtable();
		Proveedores proveedor = getProveedores();
		List lista = new ArrayList();
		Long objParam[] = new Long[] { new Long(250), new Long(0) };
		try {
			Method metodo = proveedor.getClass().getMethod(nomMetodo,
					new Class[] { long.class, long.class });
			lista = (List) metodo.invoke(proveedor, objParam);
			java.util.Iterator iterator = lista.iterator();
			while (iterator.hasNext()) {
				String[] dtCampos = (String[]) iterator.next();
				hashtable.put(dtCampos[0], dtCampos[1]);
			}

		} catch (Exception e) {
			log.error("getHashEntidad(String nomMetodo):" + e);
		}
		return hashtable;
	}

	public static Hashtable getHashEntidad(String nomMetodo,
			Class[] tipoArgumento, Object[] valorArgumento) {
		Hashtable hashtable = new Hashtable();
		Proveedores verificacion = getProveedores();
		List lista = new ArrayList();
		try {
			Method metodo = verificacion.getClass().getMethod(nomMetodo,
					tipoArgumento);
			lista = (List) metodo.invoke(verificacion, valorArgumento);
			java.util.Iterator iterator = lista.iterator();
			while (iterator.hasNext()) {
				String[] dtCampos = (String[]) iterator.next();
				hashtable.put(dtCampos[0], dtCampos[1]);
			}
		} catch (Exception e) {
			log
					.error("getHashEntidad(String nomMetodo, Class[] tipoArgumento, Object [] valorArgumento):"
							+ e);
		}
		return hashtable;
	}

	public static Hashtable getHashEntidadCrm(String nomMetodo,
			Class[] tipoArgumento, Object[] valorArgumento) {
		Hashtable hashtable = new Hashtable();
		CRM crm = getCrm();
		List lista = new ArrayList();
		try {
			Method metodo = crm.getClass().getMethod(nomMetodo, tipoArgumento);
			lista = (List) metodo.invoke(crm, valorArgumento);
			java.util.Iterator iterator = lista.iterator();
			while (iterator.hasNext()) {
				String[] dtCampos = (String[]) iterator.next();
				hashtable.put(dtCampos[0], dtCampos[1]);
			}
		} catch (Exception e) {
			log
					.error("getHashEntidad(String nomMetodo, Class[] tipoArgumento, Object [] valorArgumento):"
							+ e);
		}
		return hashtable;
	}

	public static void htRellenar(Hashtable htOrigen, Hashtable htDestino) {
		try {

			htDestino.clear();

			if (htOrigen != null && !htOrigen.isEmpty()) {

				Enumeration en = htOrigen.keys();

				while (en.hasMoreElements()) {

					Object obj = en.nextElement();

					htDestino.put(obj, htOrigen.get(obj));

				}

			}

		} catch (Exception e) {
			log.error("htRellenar(Hashtable htOrigen, Hashtable htDestino): "
					+ e);
		}
	}

	public static boolean esNumerico(String num) throws EJBException {
		try {

			Pattern patron = Pattern.compile("[^0-9.]");
			Matcher compara;

			compara = patron.matcher(num);
			boolean invalido = compara.find();

			if (invalido) {
				// Asegurar que solo ingresen valores numericos sin casteo
				// ALFABETICO
				log
						.info("Verificacion para casteo con valores alfabeticos Float - Double - Long (F-D-L)");
				return false;
			} else {
				Double.parseDouble(num);
				return true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public static boolean esEntero(String num) throws EJBException {
		try {
			if (esNumerico(num)) {

				Long.parseLong(num);
				return true;
			} else
				return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public static Enumeration getSetSorted(Set set) {
		Enumeration enSorted = null;
		try {
			if (set != null && !set.isEmpty()) {
				Vector vecSort = new Vector(set);
				Collections.sort(vecSort);
				enSorted = vecSort.elements();
			}
		} catch (Exception e) {
			log.error("getSetSorted(set):" + e);
		}
		return enSorted;
	}

	public static boolean isFormatoFecha(String fecha) {
		boolean ok = true;

		// TODO: Pendiente a�os bisiestos - meses 30 / 31 dias.
		String faux = fecha.replaceAll("/", "");

		try {
			if (fecha.length() != 10) {
				ok = false;
			} else if (fecha.charAt(2) != '/' || fecha.charAt(5) != '/') {
				ok = false;
			} else if (!Common.esEntero(faux)) {
				ok = false;
			} else if (Integer.parseInt(fecha.substring(0, 2)) < 1
					|| Integer.parseInt(fecha.substring(0, 2)) > 31) {
				ok = false;
			} else if (Integer.parseInt(fecha.substring(3, 5)) < 1
					|| Integer.parseInt(fecha.substring(3, 5)) > 12) {
				ok = false;
			} else if (Integer.parseInt(fecha.substring(6)) < 0
					|| Integer.parseInt(fecha.substring(6)) > 9999) {
				ok = false;
			}

		} catch (Exception e) {
			ok = false;
			log.error("isFormatoFecha: " + e);
		}

		return ok;
	}

	public static boolean isFechaValida(String fecha) {
		boolean ok = true;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {

			sdf.setLenient(false);
			sdf.parse(fecha);

		} catch (Exception e) {
			ok = false;
			log.error("isFechaValida: " + e);
		}
		return ok;
	}

	public static boolean isValorEnRango(long valor, long piso, long techo) {

		boolean retorno = true;

		try {

			if (valor < piso || valor > techo) {
				retorno = false;
			}

		} catch (Exception e) {
			retorno = false;
			log.error("isValorEnRango" + e);
		}

		return retorno;
	}

	public static String strZero(String numero, int cantidadceros) {
		String salida = "" + numero;
		try {
			for (int i = salida.length(); i < cantidadceros; i++) {
				salida = "0" + salida;
			}
		} catch (Exception ex) {
			System.out
					.println("StrZero(numero, cantidadceros) salida por excepcion: "
							+ ex);
		}
		return salida;
	}

	public static boolean esPorcentaje(String porcentaje) {
		boolean fOk = false;
		try {
			if (esNumerico(porcentaje)) {
				if (Float.valueOf(porcentaje).floatValue() >= 0
						&& Float.valueOf(porcentaje).floatValue() <= 100) {
					fOk = true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("esPorcentaje(String porcentaje)" + e);
		}

		return fOk;
	}

	public static String getPaginacion(long totalPaginas, long totalRegistros,
			long paginaSeleccion, int limit, long offset) {
		String paginacion = "";

		long i = 0;
		try {

			if ((totalPaginas) <= 10) {

				for (i = 1; i <= totalPaginas; i++) {

					paginacion += "<a onClick=\"paginacion("
							+ (i)
							+ ")\" style=\"cursor:pointer\">  "
							+ (i == paginaSeleccion ? "<font color=\"#FFFFFF\">"
									+ i + "</font>"
									: i + "") + "  </a>, ";
				}

				if (paginacion.indexOf(',') > -1)
					paginacion = paginacion.substring(0, paginacion
							.lastIndexOf(","));

			} else {

				if (paginaSeleccion >= (totalPaginas - 10))
					paginaSeleccion = totalPaginas - 10;

				paginacion += "<a onClick=\"paginacion(1)\" style=\"cursor:pointer\">  &lt;&lt;Pri. </a>, ";

				paginacion += "<a onClick=\"paginacion("
						+ (paginaSeleccion - 10 < 1 ? 1 : paginaSeleccion - 10)
						+ ")\" style=\"cursor:pointer\">  &lt;&lt;Ant. </a>, ";

				for (i = paginaSeleccion; i <= (paginaSeleccion + 10); i++) {
					paginacion += "<a onClick=\"paginacion("
							+ (i)
							+ ")\" style=\"cursor:pointer\">  "
							+ (i == paginaSeleccion ? "<font color=\"#FFFFFF\">"
									+ i + "</font>"
									: i + "") + "  </a>,  ";
				}

				paginacion += "...";

				paginacion += "<a onClick=\"paginacion("
						+ (paginaSeleccion + 10)
						+ ")\" style=\"cursor:pointer\">  Sig.&gt;&gt; </a>,";

				paginacion += "<a onClick=\"paginacion(" + totalPaginas
						+ ")\" style=\"cursor:pointer\">  Ult. &gt;&gt; </a>";

			}

		} catch (Exception e) {
			log.error("getPaginacion():" + e);
		}
		return paginacion;
	}

	public static String colorSaldo(Object objeto, String clasePos,
			String claseNeg) throws EJBException {
		String clase = clasePos;
		try {
			if (objeto instanceof Float) {
				Float valor = (Float) objeto;
				if (valor.floatValue() < 0) {
					clase = claseNeg;
				}
			} else if (objeto instanceof Integer) {
				Integer valor = (Integer) objeto;
				if (valor.intValue() < 0) {
					clase = claseNeg;
				}
			} else {
				String valor = (String) objeto;
				if (Float.parseFloat(valor) < 0) {
					clase = claseNeg;
				}
			}
		} catch (Exception ex) {
			clase = "";
			log.info("salida por Exception [ colorSaldo() ]:" + ex);
		}
		return clase;
	}
*/
	/*
	 * Validar Mail ----> EJV: Este metodo esta incompleto, momentaneamente se
	 * valida que haya al menos un dominio
	 */

/*

	public static boolean isValidEmailAddress(String aEmailAddress) {
		if (aEmailAddress == null)
			return false;

		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(aEmailAddress);
			if (!hasNameAndDomain(aEmailAddress)) {
				result = false;
			}
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	private static boolean hasNameAndDomain(String aEmailAddress) {
		String[] tokens = aEmailAddress.split("@");
		return tokens.length == 2 && textHasContent(tokens[0])
				&& textHasContent(tokens[1]);
	}

	private static boolean textHasContent(String aText) {
		return aText != null && aText.trim().length() > 0;
	}
*/
	/*
	 * <---- Validar Mail
	 */

	/*
	 * getAnios: Calcular anios a partir de dos fecha.
	 */
/*	
public static int getAnios(java.util.Date fdesde, java.util.Date fhasta) {
		int anios = 0;
		long timems = 0;

		try {

			if (fdesde == null)
				fdesde = Calendar.getInstance().getTime();

			timems = fdesde.getTime() - fhasta.getTime();

			anios = new Long(timems / 1000l / 60l / 60l / 24l / 365l)
					.intValue();

		} catch (Exception e) {

			anios = -1;
			log.error("getAnios(fdesde, fhasta): " + e);

		}

		return anios;

	}

	// SOBRECARGADO - v.0
	public static String getNumeroFormateado(float numero, int enteros,
			int decimales) {
		String salida = "";

		try {
			//log.info("NUMERO - A: " + numero);
			String mask = "";
			// EJV 20081031
			BigDecimal clearScientificNotation = new BigDecimal(0.00);
			clearScientificNotation = clearScientificNotation
					.setScale(decimales);

			for (int i = 0; i < enteros; i++)
				mask += "#";

			if (decimales > 0)
				mask += ".";
			for (int i = 0; i < decimales; i++)
				mask += "0";

			DecimalFormat df = new DecimalFormat(mask);
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			df.setDecimalFormatSymbols(dfs);
			if (numero == 0) {
				salida = "0.";
				for (int i = 0; i < decimales; i++)
					salida += "0";
			} else
				salida = df.format(numero).toString();

			//log.info("NUMERO - B: " + numero);
			//log.info("SALIDA  : " + salida);
			// EJV 20081031
			clearScientificNotation = new BigDecimal(salida);
			// clearScientificNotation =
			// clearScientificNotation.setScale(decimales);
			salida = clearScientificNotation.toString();
			//
		} catch (Exception e) {
			log.error("getNumeroFormateado( FLOAT , int , int  ): " + e);
		}

		return salida;
	}
*/
	/**
	 * @FECHA: 20081031
	 * @AUTOR: EJV
	 * @COMENTARIO: Prueba y sobrecarga de metodo getNumeroFormateado para
	 *              evitar visualizacion en formato de notacion cientifica.
	 */

	// SOBRECARGADO - v.1
/*
public static String getNumeroFormateado(double numero, int enteros,
			int decimales) {

		String salida = "-1";

		try {

			String mask = "";
			BigDecimal clearScientificNotation = new BigDecimal(0.00);
			clearScientificNotation = clearScientificNotation
					.setScale(decimales);

			for (int i = 0; i < enteros; i++)
				mask += "#";

			if (decimales > 0)
				mask += ".";
			for (int i = 0; i < decimales; i++)
				mask += "0";

			DecimalFormat df = new DecimalFormat(mask);
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			df.setDecimalFormatSymbols(dfs);
			if (numero == 0) {
				salida = "0.";
				for (int i = 0; i < decimales; i++)
					salida += "0";
			} else
				salida = df.format(numero).toString();

			clearScientificNotation = new BigDecimal(salida);
			// clearScientificNotation =
			// clearScientificNotation.setScale(decimales);
			salida = clearScientificNotation.toString();

		} catch (Exception e) {
			log.error("getNumeroFormateado( DOUBLE , int , int  ): " + e);
		}

		return salida;
	}

	public static BigDecimal setPorcentejeDescuento(BigDecimal valor,
			BigDecimal porcentaje, int decimales) {

		// valor - (valor * (porcentaje / 100.0) )

		BigDecimal factor = new BigDecimal(0);
		BigDecimal descuento = valor;
		BigDecimal valorConDescuento = valor;

		try {

			factor = porcentaje.divide(new BigDecimal(100));
			descuento = descuento.multiply(factor);
			valorConDescuento = valorConDescuento.subtract(descuento);
			valorConDescuento = valorConDescuento.setScale(decimales,
					BigDecimal.ROUND_UP);

		} catch (Exception e) {
			// TODO: handle exception

			valorConDescuento = valor;
			log
					.error("setPorcentejeDescuento(BigDecimal valor, BigDecimal porcentaje):"
							+ e);
		}

		return valorConDescuento;

	}

	public static BigDecimal getTotalHTArrayByIndex(Hashtable ht, int indice) {
		BigDecimal total = new BigDecimal(0);
		Enumeration en;
		try {
			if (ht != null) {
				en = ht.keys();
				while (en.hasMoreElements()) {
					Object clave = en.nextElement();
					String[] datos = (String[]) ht.get(clave);
					total = total.add(new BigDecimal(datos[indice]));
				}
			}
		} catch (Exception e) {

			log.error("getTotalHTArrayByIndex(Hashtable ht, int indice):");

		}

		return total;
	}

	public static String setNotNull(String str) {
		try {
			if (str == null)
				return "";
			else if (str.equalsIgnoreCase("null"))
				return "";
			return str;
		} catch (Exception e) {
			log.error("setNotNull: " + e);
			return "";
		}
	}

	public static java.sql.Timestamp getTesoFechaCaja(BigDecimal idempresa) {
		Timestamp fechatesoreria = null;
		try {

			String tesoFechaCaja = getGeneral().getValorSetupVariablesNoStatic(
					"tesoFechaCaja", idempresa);

			if (!isFormatoFecha(tesoFechaCaja)) {

				log.warn("getTesoFechaCaja(): Formato de Fecha inv�lido | "
						+ tesoFechaCaja);

			} else if (!isFechaValida(tesoFechaCaja)) {

				log.warn("getTesoFechaCaja(): Fecha inv�lida | "
						+ tesoFechaCaja);

			} else {

				fechatesoreria = (Timestamp) Common.setObjectToStrOrTime(
						tesoFechaCaja, "StrToJSTs");

			}

		} catch (Exception e) {
			log.error("getTesoFechaCaja(idempresa): " + e);
		}

		return fechatesoreria;
	}

	public static String getNivelStr(String patron, int cant) {
		String str = "";
		for (int i = 0; i < cant; i++) {
			str += patron;
		}
		return str;
	}

	/*
	 * 
	 * */
/*
	public static boolean sendMail(String subject, String body, String to) {

		boolean isSend = false;

		try {

			Properties props = new Properties();
			props.load(Common.class.getResourceAsStream("mail.properties"));
			String cMailSmtp = setNotNull(props.getProperty("mail.smtpServer"))
					.trim();
			// lo manda el robot
			String cMailFrom = setNotNull(props.getProperty("mail.from"))
					.trim();
			String cMailFromName = setNotNull(
					props.getProperty("mail.from.name")).trim();
			//
			String cMailBCC = setNotNull(props.getProperty("mail.bcc")).trim();
			String cMailUsuario = setNotNull(props.getProperty("mail.usuario"))
					.trim();
			String cMailClave = setNotNull(props.getProperty("mail.clave"))
					.trim();

			String mailer = "JavaMail API";
			props.put("mail.smtp.auth", "true");

			javax.mail.Session session = javax.mail.Session.getDefaultInstance(
					props, null);

			javax.mail.Message msg = new javax.mail.internet.MimeMessage(
					session);
			msg.setFrom(new javax.mail.internet.InternetAddress(cMailFrom,
					cMailFromName));

			msg.setRecipients(Message.RecipientType.TO,
					javax.mail.internet.InternetAddress.parse(to, false));
			msg.setSubject(subject);
			msg.setText(body);
			msg.setHeader("X-Mailer", mailer);
			msg.setSentDate(new java.util.Date());
			javax.mail.Transport transporte = session.getTransport("smtp");
			transporte.connect(cMailSmtp, cMailUsuario, cMailClave);
			msg.saveChanges();
			transporte.sendMessage(msg, msg.getAllRecipients());
			props = null;
			transporte.close();
			isSend = true;

		} catch (IOException ioEx) {
			log.error("Error IO: " + ioEx);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}

		return isSend;
	}

	public static boolean sendMail(String subject, String body, String to,
			String title) {

		boolean isSend = false;

		try {

			Properties props = new Properties();
			props.load(Common.class.getResourceAsStream("mail.properties"));
			String cMailSmtp = setNotNull(props.getProperty("mail.smtpServer"))
					.trim();
			// lo manda el robot
			String cMailFrom = setNotNull(props.getProperty("mail.from"))
					.trim();
			String cMailFromName = title;
			//
			String cMailBCC = setNotNull(props.getProperty("mail.bcc")).trim();
			String cMailUsuario = setNotNull(props.getProperty("mail.usuario"))
					.trim();
			String cMailClave = setNotNull(props.getProperty("mail.clave"))
					.trim();

			String mailer = "JavaMail API";
			props.put("mail.smtp.auth", "true");

			javax.mail.Session session = javax.mail.Session.getDefaultInstance(
					props, null);

			javax.mail.Message msg = new javax.mail.internet.MimeMessage(
					session);
			msg.setFrom(new javax.mail.internet.InternetAddress(cMailFrom,
					cMailFromName));

			msg.setRecipients(Message.RecipientType.TO,
					javax.mail.internet.InternetAddress.parse(to, false));
			msg.setSubject(subject);
			msg.setText(body);
			msg.setHeader("X-Mailer", mailer);
			msg.setSentDate(new java.util.Date());
			javax.mail.Transport transporte = session.getTransport("smtp");
			transporte.connect(cMailSmtp, cMailUsuario, cMailClave);
			msg.saveChanges();
			transporte.sendMessage(msg, msg.getAllRecipients());
			props = null;
			transporte.close();
			isSend = true;

		} catch (IOException ioEx) {
			log.error("Error IO: " + ioEx);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}

		return isSend;
	}
*/
	/*
	 *
	 * */
/*
	public static boolean sendMailRegalos(String subject, String body,
			String sector) {

		boolean isSend = false;

		try {

			String[] toSector;

			Properties props = new Properties();
			props.load(Common.class.getResourceAsStream("mail.properties"));
			String cMailSmtp = setNotNull(props.getProperty("mail.smtpServer"))
					.trim();

			// lo manda el robot
			String cMailFrom = setNotNull(
					props.getProperty("mail.regalos.from")).trim();
			String cMailFromName = setNotNull(
					props.getProperty("mail.regalos.from.name")).trim();

			//
			String cMailBCC = setNotNull(props.getProperty("mail.regalos.bcc"))
					.trim();
			String cMailUsuario = setNotNull(
					props.getProperty("mail.regalos.usuario")).trim();
			String cMailClave = setNotNull(
					props.getProperty("mail.regalos.clave")).trim();
			String authSmtp = setNotNull(props.getProperty("mail.smtp.auth"))
					.trim();

			toSector = setNotNull(
					props.getProperty("mail.sector." + sector + ".to")).trim()
					.split(";");

			InternetAddress[] addresses = new InternetAddress[toSector.length];

			String mailer = "JavaMail API";
			// Por el momento no es necesaria la autenticacion de correo SMTP
			props.put("mail.smtp.auth", authSmtp);

			javax.mail.Session session = javax.mail.Session.getDefaultInstance(
					props, null);

			javax.mail.Message msg = new javax.mail.internet.MimeMessage(
					session);
			msg.setFrom(new javax.mail.internet.InternetAddress(cMailFrom,
					cMailFromName));

			// msg.setRecipients(Message.RecipientType.TO,
			// javax.mail.internet.InternetAddress.parse(to, false));
			//
			for (int i = 0; i < toSector.length; i++) {
				log.info("I (" + i + "): " + toSector[i]);

				try {
					addresses[i] = new InternetAddress(toSector[i]);
				} catch (Exception e) {
					log.info("I (" + i + "): " + toSector[i] + " EX: " + e);
				}

			}
			msg.setRecipients(Message.RecipientType.TO, addresses);
			//
			msg.setSubject(subject);
			msg.setText(body);
			msg.setHeader("X-Mailer", mailer);
			msg.setSentDate(new java.util.Date());
			javax.mail.Transport transporte = session.getTransport("smtp");

			transporte.connect(cMailSmtp, cMailUsuario, cMailClave);

			msg.saveChanges();
			transporte.sendMessage(msg, msg.getAllRecipients());
			props = null;
			transporte.close();
			isSend = true;

		} catch (IOException ioEx) {
			log.error("Error IO: " + ioEx);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Salida por exception: " + ex);
		}

		return isSend;
	}
*/
	/**
	 * @author ejv
	 * @date 20110624
	 * 
	 *       Metodo de validacion de Clave de Identificacion Unica
	 *       [Tributaria(CUIT)-Laboral(CUIL)]
	 * 
	 * 
	 *       TIPO: Físicas (Hombres, Mujeres) o Jurídicas 27-mujer 20-hombre
	 *       23-puede ser ambos (se usa cuando hay otro número igual)
	 *       30-empresas
	 * 
	 * 
	 * */
/*
	public static boolean validarClaveIdentificacionUnica(String clave) {

		boolean cuitCuilValido = false;

		String identificador = (clave == null ? "" : clave).replaceAll("-", "")
				.trim();
		String tipo = "";
		String numero = "";
		String digitoIngresado = "";
		int digitoValido = -1;
		Hashtable htTiposValidos = new Hashtable();

		htTiposValidos.put("27", "MUJER");
		htTiposValidos.put("20", "HOMBRE");
		htTiposValidos.put("23", "AMBOS");
		// --
		htTiposValidos.put("24", "HOMBRE?");
		htTiposValidos.put("30", "EMPRESA");
		// --
		htTiposValidos.put("33", "EMPRESA?");
		htTiposValidos.put("34", "EMPRESA?");

		try {

			if (!esEntero(identificador)) {
				// Caracteres invalidos
				log
						.warn("validarClaveIdentificacionUnica: Caracteres invalidos en CUIT-CUIL - "
								+ clave);
			} else if (identificador.length() < 4) {
				// Longitud minima
				log
						.warn("validarClaveIdentificacionUnica: Longitud no alcanza la minima CUIT-CUIL - "
								+ clave);

			} else if (identificador.length() > 11) {
				// Longitud minima
				log
						.warn("validarClaveIdentificacionUnica: Longitud excede la maxima CUIT-CUIL - "
								+ clave);
			} else {

				tipo = identificador.substring(0, 2);
				numero = identificador.substring(2, identificador.length() - 1);
				digitoIngresado = identificador.substring(identificador
						.length() - 1);

				if (!htTiposValidos.containsKey(tipo)) {

					// Tipo invalido
					log
							.warn("validarClaveIdentificacionUnica: Tipo invalido CUIT-CUIL - "
									+ clave);

				} else {

					numero = tipo + strZero(numero, 8);
					int suma = 0;
					int modSumaOnce = 0;
					for (int i = 9, factor = 2; i >= 0; i--, factor++) {
						if (factor > 7)
							factor = 2;

						if (i == 9) {
							suma += Integer.parseInt(numero.substring(i))
									* factor;

						} else {
							suma += Integer
									.parseInt(numero.substring(i, i + 1))
									* factor;
						}

					}

					modSumaOnce = suma % 11;

					digitoValido = 11 - modSumaOnce;

					if (digitoValido == 11)
						digitoValido = 0;
					if (digitoValido == 10)
						digitoValido = 9;

					if (digitoValido == Integer.parseInt(digitoIngresado)) {
						cuitCuilValido = true;
						log
								.info("validarClaveIdentificacionUnica: DIGITO CORRECTO :  "
										+ digitoValido
										+ " / "
										+ digitoIngresado);

					} else
						log
								.info("validarClaveIdentificacionUnica: DIGITO INVALIDO :  "
										+ digitoValido
										+ " / "
										+ digitoIngresado);

				}

			}

		} catch (Exception e) {
			log.error("validarClaveIdentificacionUnica(): " + e);
		}

		return cuitCuilValido;

	}

	// 20120710 - EJV - Mantis 842 -->
	// Metodo sin implicancias en calculos, redondeos u alguna otra variable que
	// modifique valores. Solo se usa con el fin de manipular la cantidad de
	// decimales a VISUALIZAR desde la capa de visualizacion (paginas )
	public static String visualNumero(String numero, int decimal) {

		String nroAux = "";

		try {

			if (((numero.indexOf('.') + 1) + decimal) <= numero.length())
				nroAux = numero.substring(0,
						((numero.indexOf('.') + 1) + decimal));
			else
				nroAux = numero;

		} catch (Exception e) {
			log.error("visualNumero ( " + numero + ",  " + decimal + " ) : "
					+ e);
			return numero;
		}

		return nroAux;
	}
*/
	// <--

	// 20120718 - EJV - Mantis 860-->

	/*
	 * Metodo para verificar el que el numero de tarjeta ingresado corresponde a
	 * la marca existente en el sistema. Los valores estan HARCODE.
	 */
/*
	public static boolean isCCVSTypeMatchIDSistema(BigDecimal idtarjetacredito,
			String CCVSType) {

		boolean corresponde = false;

		Hashtable ht = new Hashtable();
		Object valorCCVSType = "";
		BigDecimal idtarjetaAuxiliar = new BigDecimal(0);

		ht.put("VISA", "1");
		ht.put("MASTERCARD", "2");
		ht.put("DINERS CLUB", "3");
		ht.put("AMERICAN EXPRESS", "4");
		ht.put("CABAL", "6");
		// ------------------------------------------------
		ht.put("JCB", "-100");
		ht.put("Carte Blanche", "-200");
		ht.put("Australian BankCard", "-300");
		ht.put("Discover/Novus", "-400");
*/
		/*
		 * -------------------------------- IdGeneradosEnSistemaDelta
		 * --------------------------------1;"VISA"
		 * --------------------------------2;"MASTERCARD"
		 * --------------------------------3;"DINERS"
		 * --------------------------------4;"AMERICAN EXPRESS"
		 * --------------------------------5;"VISA ELECTRON"
		 * --------------------------------6;"CABAL"
		 * --------------------------------7;"FUEGUINA"
		 */
/*
		try {

			valorCCVSType = ht.get(CCVSType.toUpperCase());
			idtarjetaAuxiliar = new BigDecimal(
					valorCCVSType != null ? valorCCVSType.toString() : "0");

			if (idtarjetaAuxiliar.intValue() == idtarjetacredito.intValue())
				corresponde = true;

			log.info("idtipotarjeta: " + idtarjetacredito);
			log.info("idtarjetaAuxiliar: " + idtarjetaAuxiliar);
			log.info("CCVSType: " + CCVSType);

		} catch (Exception e) {
			corresponde = false;
			log.error("isCCVSTypeMatchIDSistema: " + e);
		}

		log.info("corresponde: " + corresponde);
		return corresponde;

	}

	// <--

}
*/