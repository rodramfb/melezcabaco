package ar.com.syswarp.api;

import java.util.*;
import org.apache.log4j.*;

public class Strings {
	public Strings() {
		super();
		// TODO Auto-generated constructor stub
	}

	static Logger log = Logger.getLogger(Strings.class);

	Hashtable ht = null;

	public String esNulo(String cad) {
		try {
			if (cad == null)
				return "";
			if (cad.equalsIgnoreCase("null"))
				return "";
			return cad;
		} catch (Exception eCad) {
			System.out.println("ERROR esNulo: " + eCad);
			return "";
		}
	}

	public void wLog(String salida, int tipo) {
		switch (tipo) {
		case 1:
			log.info(salida);
			break;
		case 2:
			log.warn(salida);
			break;
		case 3:
			log.error(salida);
			break;
		default:
			log.error(salida);
			break;
		}
	}

	public String getNivelStr(String patron, int cant) {
		String str = "";
		for (int i = 0; i < cant; i++) {
			str += patron;
		}
		return str;
	}

	public String parteStr(String str, int longitud, String patron) {
		try {
			str = esNulo(str);
			str = str.length() > longitud ? str.substring(0, longitud) + patron
					: str;
			return str;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("parteStr (String str, int longitud, String patron ): "
					+ e);
			return "";
		}
	}

	public static boolean esAlfabetico(String str) {
		try {

			for (int i = 0; i < str.length(); i++) {
				if (str.toUpperCase().charAt(i) < 65
						|| str.toUpperCase().charAt(i) > 90) {
					return false;
				}
			}
		} catch (Exception e) {
			log.error("esAlfabetico(" + str + "):" + e);
			return false;
		}
		return true;
	}
}
