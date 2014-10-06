package ar.com.syswarp.validar;

import java.util.*;
import org.apache.log4j.*;

public class Strings {

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

}
