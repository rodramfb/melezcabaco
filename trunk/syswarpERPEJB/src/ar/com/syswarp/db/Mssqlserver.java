package ar.com.syswarp.db;

import java.io.IOException;
import java.sql.*;

import javax.ejb.EJBException;
import javax.sql.*;
import java.util.*;

import org.apache.log4j.*;
import ar.com.syswarp.ejb.ContableBean;

public class Mssqlserver {
	private Connection dbconn;

	static Logger log = Logger.getLogger(Mssqlserver.class);

	private Connection conexion;

	private Properties props;

	private String url;

	private String clase;

	private String usuario;

	private String clave;

	public Mssqlserver() {
		super();
		try {
			props = new Properties();
			props.load(ContableBean.class
					.getResourceAsStream("system.properties"));

			url = props.getProperty("conn.url").trim();
			clase = props.getProperty("conn.clase").trim();
			usuario = props.getProperty("conn.usuario").trim();
			clave = props.getProperty("conn.clave").trim();

			Class.forName(clase);
			conexion = DriverManager.getConnection(url, usuario, clave);
			this.dbconn = conexion;
		} catch (java.lang.ClassNotFoundException cnfException) {
			log.error("Error driver : " + cnfException);
		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (IOException IOException) {
			log.error("Error SQL: " + IOException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
	}

	public Mssqlserver(Connection dbconn) {
		this.dbconn = dbconn;
	}

	public boolean existeTabla(String tabla) {
		boolean salida = false;
		try {
			Statement statement = dbconn.createStatement();
			String cQuery = "select count(*) as total from sysobjects where type ='U' and name =' '"
					+ tabla + "' ";
			ResultSet rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				if (rsSalida.getInt(1) > 0)
					salida = true;
			}
		} catch (SQLException sqlException) {
			log.error("existeTabla(String tabla): Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("existeTabla(String tabla) Salida por exception: " + ex);
		}
		return salida;
	}

	public boolean hasEjercicioTablas(int idEjercicio) {
		boolean salida = false;
		try {
			// TODO: discriminar para todos los driver.
			Statement statement = dbconn.createStatement();
			String cQuery = "select count(*) as total from sysobjects where type ='U' and name ='contableinfiplan'"
					+ idEjercicio;
			log.info("query para hasEjercicioTablas :" + cQuery);
			ResultSet rsSalida = statement.executeQuery(cQuery);
			if (rsSalida != null && rsSalida.next()) {
				if (rsSalida.getInt(1) > 0)
					salida = true;
			}
		} catch (SQLException sqlException) {
			log.error("hasEjercicioTablas: Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("hasEjercicioTablas Salida por exception: " + ex);
		}
		return salida;
	}

	// no se va a utilizar por el momento
	public String dropEjercicioPostgres(int idEjercicio) {
		String salida = "NOOK";
		boolean bError = false;
		String tableName = "";
		try {
			tableName = "contableinfiplan" + idEjercicio;
			PreparedStatement drop = dbconn.prepareStatement("DROP TABLE "
					+ tableName);
			int n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

			tableName = "contableinfimov" + idEjercicio;
			drop = dbconn.prepareStatement("DROP TABLE " + tableName);
			n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

			tableName = "contableleyendas" + idEjercicio;
			drop = dbconn.prepareStatement("DROP TABLE " + tableName);
			n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

			tableName = "contablebalance" + idEjercicio;
			drop = dbconn.prepareStatement("DROP TABLE " + tableName);
			n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

			tableName = "contablelistotal" + idEjercicio;
			drop = dbconn.prepareStatement("DROP TABLE " + tableName);
			n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

			tableName = "contablesaldocue" + idEjercicio;
			drop = dbconn.prepareStatement("DROP TABLE " + tableName);
			n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

			tableName = "contablelisnivel" + idEjercicio;
			drop = dbconn.prepareStatement("DROP TABLE " + tableName);
			n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

			tableName = "contabletipocuen" + idEjercicio;
			drop = dbconn.prepareStatement("DROP TABLE " + tableName);
			n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

			tableName = "contabledirector" + idEjercicio;
			drop = dbconn.prepareStatement("DROP TABLE " + tableName);
			n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

			drop = dbconn
					.prepareStatement("DELETE FROM CONTABLEejercicios where ejercicio = "
							+ idEjercicio);
			n = drop.executeUpdate();
			log.info("Se elimino el ejercicio " + tableName);

		} catch (SQLException sqlException) {
			log.error("setEjercicioTablas: Error SQL: " + sqlException);
			salida = "setEjercicioTablas: Error SQL: " + sqlException;
		} catch (Exception ex) {
			log.error("setEjercicioTablas Salida por exception: " + ex);
			salida = "setEjercicioTablas Salida por exception: " + ex;
		}
		return salida;
	}

	// setear un ejercicio= crearle todas las tablas.
	public String setEjercicioTablas(int idEjercicio) throws EJBException {
		String salida = "NOOK";
		boolean bError = false;
		try {
			// TODO: discriminar para todos los driver.
			Statement statement = dbconn.createStatement();
			// plan de cuentas contables
			if (!hasEjercicioTablas(idEjercicio)) {
				String tableName = "contableinfiplan" + idEjercicio;
				log.info("Generando tabla " + tableName);
				String sqlC = "";
				sqlC += "CREATE TABLE " + tableName;
				sqlC += "(";
				sqlC += "  idcuenta numeric(18) NOT NULL,";
				sqlC += "  cuenta varchar(50) NOT NULL,";
				sqlC += "  imputable varchar(1) NOT NULL,";
				sqlC += "  nivel numeric(2) NOT NULL,";
				sqlC += "  ajustable varchar(1) NOT NULL,";
				sqlC += "  resultado varchar(1) NOT NULL,";
				sqlC += "  cent_cost numeric(18),";
				sqlC += "  cent_cost1 numeric(18),";
				sqlC += "  usuarioalt varchar(20) NOT NULL,";
				sqlC += "  usuarioact varchar(20),";
				sqlC += "  fechaalt datetime NOT NULL DEFAULT getdate(),";
				sqlC += "  fechaact datetime,";
				sqlC += "  CONSTRAINT pk_" + tableName
						+ " PRIMARY KEY (idcuenta)";
				sqlC += ") ";
				if (statement.execute(sqlC))
					bError = true;

				// movimientos
				tableName = "contableinfimov" + idEjercicio;
				log.info("Generando tabla " + tableName);
				sqlC = "";
				sqlC += "CREATE TABLE " + tableName + "";
				sqlC += "(";
				sqlC += "  idasiento  numeric(18)   not null,";
				sqlC += "  fecha      datetime     not null,";
				sqlC += "  renglon    numeric(18)   not null,";
				sqlC += "  cuenta     numeric(18)   not null,";
				sqlC += "  tipomov    numeric(1)    not null,";
				sqlC += "  importe    numeric(18,3) not null,";
				sqlC += "  detalle    varchar(50),";
				sqlC += "  asie_nue   numeric(18),";
				sqlC += "  cent_cost  numeric(18),";
				sqlC += "  cent_cost1 numeric(18),";
				sqlC += "  usuarioalt varchar(20) not null,";
				sqlC += "  usuarioact varchar(20),";
				sqlC += "  fechaalt   datetime NOT NULL DEFAULT getdate(),";
				sqlC += "  fechaact   datetime,";
				sqlC += "  CONSTRAINT pk_" + tableName
						+ " PRIMARY KEY (idasiento, renglon)";
				sqlC += ") ";
				if (statement.execute(sqlC))
					bError = true;

				// leyendas de asientos
				tableName = "contableleyendas" + idEjercicio;
				log.info("Generando tabla " + tableName);
				sqlC = "";
				sqlC += "CREATE TABLE " + tableName + "";
				sqlC += "(";
				sqlC += "  idasiento  numeric(18)   not null,";
				sqlC += "  leyenda varchar(5000),";
				sqlC += "  tipo_asiento varchar(1),";
				sqlC += "  asiento_nuevo numeric(18),";
				sqlC += "  usuarioalt varchar(20) not null,";
				sqlC += "  usuarioact varchar(20),";
				sqlC += "  fechaalt   datetime NOT NULL DEFAULT getdate(),";
				sqlC += "  fechaact   datetime,";
				sqlC += "  CONSTRAINT pk_" + tableName
						+ " PRIMARY KEY (idasiento)";
				sqlC += ") ";
				if (statement.execute(sqlC))
					bError = true;

				// leyendas de asientos
				tableName = "contablebalance" + idEjercicio;
				log.info("Generando tabla " + tableName);
				sqlC = "";
				sqlC += "CREATE TABLE " + tableName + "";
				sqlC += "(";
				sqlC += "   idcuenta numeric(18) not null,";
				sqlC += "  anterior numeric(18,3),";
				sqlC += "  tot_debe numeric(18,3),";
				sqlC += "  tot_haber numeric(18,3),";
				sqlC += "  usuarioalt varchar(20) not null,";
				sqlC += "  usuarioact varchar(20),";
				sqlC += "  fechaalt datetime not null default getdate(),";
				sqlC += "  fechaact datetime,";
				sqlC += " CONSTRAINT pk_" + tableName
						+ " PRIMARY KEY (idcuenta)";
				sqlC += ") ";
				if (statement.execute(sqlC))
					bError = true;

				// listado de totales
				tableName = "contablelistotal" + idEjercicio;
				log.info("Generando tabla " + tableName);
				sqlC = "";
				sqlC += "CREATE TABLE " + tableName + "";
				sqlC += "(";
				sqlC += "  idcuenta numeric(18) not null,";
				sqlC += "  descripcion varchar(30),";
				sqlC += "  m1 numeric(15,3),";
				sqlC += "  m2 numeric(15,3),";
				sqlC += "  m3 numeric(15,3),";
				sqlC += "  m4 numeric(15,3),";
				sqlC += "  m5 numeric(15,3),";
				sqlC += "  m6 numeric(15,3),";
				sqlC += "  m7 numeric(15,3),";
				sqlC += "  m8 numeric(15,3),";
				sqlC += "  m9 numeric(15,3),";
				sqlC += "  m10 numeric(15,3),";
				sqlC += "  m11 numeric(15,3),";
				sqlC += "  m12 numeric(15,3),";
				sqlC += "  total_cuenta numeric(15,3),";
				sqlC += "  usuarioalt varchar(20) not null ,";
				sqlC += "  usuarioact varchar(20),";
				sqlC += "  fechaalt datetime not null default getdate(),";
				sqlC += "  fechaact datetime,";
				sqlC += "  CONSTRAINT pk_" + tableName
						+ " PRIMARY KEY (idcuenta)";
				sqlC += ") ";
				if (statement.execute(sqlC))
					bError = true;

				// saldo de cuentas contables
				tableName = "contablesaldocue" + idEjercicio;
				log.info("Generando tabla " + tableName);
				sqlC = "";
				sqlC += "CREATE TABLE " + tableName + "";
				sqlC += "(";
				sqlC += "  idcuenta numeric(18) not null,";
				sqlC += "  anio numeric(4) not null,";
				sqlC += "  mes numeric(2)  not null,";
				sqlC += "  tot_debe numeric(18,3),";
				sqlC += "  tot_haber numeric(18,3),";
				sqlC += "  usuarioalt varchar(20) not null,";
				sqlC += "  usuarioact varchar(20),";
				sqlC += "  fechaalt datetime not null default getdate(),";
				sqlC += "  fechaact datetime,";
				sqlC += "  CONSTRAINT pk_" + tableName
						+ " PRIMARY KEY (idcuenta,anio,mes)";
				sqlC += ") ";
				if (statement.execute(sqlC))
					bError = true;

				// niveles de cuentas contables
				tableName = "contablelisnivel" + idEjercicio;
				log.info("Generando tabla " + tableName);
				sqlC = "";
				sqlC += " CREATE TABLE " + tableName + "";
				sqlC += " (";
				sqlC += "   idcuenta numeric(18) not null,";
				sqlC += "   descripcion varchar(30),";
				sqlC += "   m1 numeric(15,3),";
				sqlC += "   m2 numeric(15,3),";
				sqlC += "   m3 numeric(15,3),";
				sqlC += "   m4 numeric(15,3),";
				sqlC += "   m5 numeric(15,3),";
				sqlC += "   m6 numeric(15,3),";
				sqlC += "   m7 numeric(15,3),";
				sqlC += "   m8 numeric(15,3),";
				sqlC += "   m9 numeric(15,3),";
				sqlC += "   m10 numeric(15,3),";
				sqlC += "   m11 numeric(15,3),";
				sqlC += "   m12 numeric(15,3),";
				sqlC += "   total_cuenta numeric(15,3),";
				sqlC += "   usuarioalt varchar(20) not null,";
				sqlC += "   usuarioact varchar(20),";
				sqlC += "   fechaalt datetime not null default getdate(),";
				sqlC += "   fechaact datetime,";
				sqlC += "   CONSTRAINT pk_" + tableName
						+ " PRIMARY KEY (idcuenta)";
				sqlC += " ) ";
				if (statement.execute(sqlC))
					bError = true;

				// tipos de cuentas contables
				tableName = "contabletipocuen" + idEjercicio;
				log.info("Generando tabla " + tableName);
				sqlC = "";
				sqlC += "CREATE TABLE " + tableName + " ";
				sqlC += "( ";
				sqlC += "  tipo varchar(1)    not null, ";
				sqlC += "  idcuenta numeric(18) not null, ";
				sqlC += "  rubro1 numeric(15,3), ";
				sqlC += "  rubro2 numeric(15,3), ";
				sqlC += "  rubro3 numeric(15,3), ";
				sqlC += "  rubro4 numeric(15,3), ";
				sqlC += "  rubro5 numeric(15,3), ";
				sqlC += "  rubro6 numeric(15,3), ";
				sqlC += "  rubro7 numeric(15,3), ";
				sqlC += "  rubro8 numeric(15,3), ";
				sqlC += "  rubro9 numeric(15,3), ";
				sqlC += "  historico numeric(18,3), ";
				sqlC += "  ajustado numeric(18,3), ";
				sqlC += "  rubro_list numeric(3), ";
				sqlC += "  usuarioalt varchar(20) not null, ";
				sqlC += "  usuarioact varchar(20), ";
				sqlC += "  fechaalt datetime not null default getdate(), ";
				sqlC += "  fechaact datetime, ";
				sqlC += "  CONSTRAINT pk_" + tableName
						+ " PRIMARY KEY (tipo,idcuenta) ";
				sqlC += ")  ";
				if (statement.execute(sqlC))
					bError = true;

				tableName = "contabledirector" + idEjercicio;
				log.info("Generando tabla " + tableName);
				sqlC = "";
				sqlC += "CREATE TABLE " + tableName + "";
				sqlC += "(";
				sqlC += "  cargo varchar(30)  not null,";
				sqlC += "  nombre varchar(30) not null,";
				sqlC += "  usuarioalt varchar(20) not null,";
				sqlC += "  usuarioact varchar(20),";
				sqlC += "  fechaalt datetime not null default getdate(),";
				sqlC += "  fechaact datetime";
				sqlC += ") ";
				if (statement.execute(sqlC))
					bError = true;
				/*
				 * falta la contcont pero a mi criterio no va ni a palos.
				 */
				if (!bError) {
					salida = "OK";
				}
			} else {
				salida = "Error: El ejercicio " + idEjercicio + " ya existe ";
				log.info("Error: se pretende crear un ejercicio ya existente"
						+ idEjercicio);
			}
		} catch (SQLException sqlException) {
			log.error("setEjercicioTablas: Error SQL: " + sqlException);
			salida = "setEjercicioTablas: Error SQL: " + sqlException;
		} catch (Exception ex) {
			log.error("setEjercicioTablas Salida por exception: " + ex);
			salida = "setEjercicioTablas Salida por exception: " + ex;
		}

		return salida;
	}

}
