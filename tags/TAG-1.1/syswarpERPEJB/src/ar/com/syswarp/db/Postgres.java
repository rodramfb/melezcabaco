package ar.com.syswarp.db;

/*
 reconquista 1088 9 piso 
 10943.88 
 */

import java.io.IOException;
import java.sql.*;

import javax.ejb.EJBException;
import javax.sql.*;
import java.util.*;
import java.math.BigDecimal;

import org.apache.log4j.*;

import ar.com.syswarp.ejb.ContableBean;

public class Postgres {
	private Connection dbconn;

	static Logger log = Logger.getLogger(Postgres.class);

	private Connection conexion;

	private Properties props;

	private String url;

	private String clase;

	private String usuario;

	private String clave;

	public Postgres() {
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

	public Postgres(Connection dbconn) {
		this.dbconn = dbconn;
	}

	public boolean existeTabla(String tabla) {
		boolean salida = false;
		try {
			Statement statement = dbconn.createStatement();
			String cQuery = "select count(*) as total from information_schema.tables where lower(table_name)='"
					+ tabla + "'";
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
			String cQuery = "select count(*) as total from information_schema.tables where lower(table_name)='contableinfiplan"
					+ idEjercicio + "'";
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
	public String dropEjercicioPostgres(int idEjercicio, BigDecimal idempresa) {
		String salida = "NOOK";
		boolean bError = false;
		String tableName = "";
		try {
			int n = 0;
			PreparedStatement drop = null;
			/* inicia EJV saldo cuentas */
			/*
			 * verificar si se crea esta vista + contablesaldocueEJERCICIO, o
			 * solo una de las dos
			 * 
			 * 
			 * tableName = "contablesaldocue" + idEjercicio; PreparedStatement
			 * drop = dbconn.prepareStatement("DROP VIEW " + tableName); int n =
			 * drop.executeUpdate(); log.info("Se elimino la vista " +
			 * tableName);
			 */

			// esto se somento fgp 17/05/2007 debido a que al borrar la vista no
			// puedo filtrar por idempresa
			// aca hay que ver que hago por que no puedo borrar la vista por
			// empresa
			// 
			/*
			 * tableName = "contablelibromayor" + idEjercicio; drop =
			 * dbconn.prepareStatement("delete VIEW " + tableName + "where
			 * idempresa = " + idempresa.toString()); n = drop.executeUpdate();
			 * log.info("Se elimino la vista " + tableName);
			 * 
			 * tableName = "contablesaldocuexmes" + idEjercicio; drop =
			 * dbconn.prepareStatement("delete VIEW " + tableName + "where
			 * idempresa = " + idempresa.toString()); n = drop.executeUpdate();
			 * log.info("Se elimino la vista " + tableName);
			 * 
			 * tableName = "contablesumasysaldos" + idEjercicio; drop =
			 * dbconn.prepareStatement("delete VIEW " + tableName + "where
			 * idempresa = " + idempresa.toString()); n = drop.executeUpdate();
			 * log.info("Se elimino la vista " + tableName);
			 * 
			 */
			/* fin EJV saldo cuentas */

			tableName = "contableinfiplan" + idEjercicio;
			drop = dbconn.prepareStatement("delete from " + tableName
					+ " where idempresa = " + idempresa.toString());
			n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

			tableName = "contableinfimov" + idEjercicio;
			drop = dbconn.prepareStatement("delete from " + tableName
					+ " where idempresa = " + idempresa.toString());
			n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

			tableName = "contableleyendas" + idEjercicio;
			drop = dbconn.prepareStatement("delete from " + tableName
					+ " where idempresa = " + idempresa.toString());
			n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

			tableName = "contablebalance" + idEjercicio;
			drop = dbconn.prepareStatement("delete from " + tableName
					+ " where idempresa = " + idempresa.toString());
			n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

			tableName = "contablelistotal" + idEjercicio;
			drop = dbconn.prepareStatement("delete from " + tableName
					+ " where idempresa = " + idempresa.toString());
			n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

			tableName = "contablesaldocue" + idEjercicio;
			drop = dbconn.prepareStatement("delete from " + tableName
					+ " where idempresa = " + idempresa.toString());
			n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

			tableName = "contablelisnivel" + idEjercicio;
			drop = dbconn.prepareStatement("delete from " + tableName
					+ " where idempresa = " + idempresa.toString());
			n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

			tableName = "contabletipocuen" + idEjercicio;
			drop = dbconn.prepareStatement("delete from " + tableName
					+ " where idempresa = " + idempresa.toString());
			n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

			tableName = "contabledirector" + idEjercicio;
			drop = dbconn.prepareStatement("delete from " + tableName
					+ " where idempresa = " + idempresa.toString());
			n = drop.executeUpdate();
			log.info("Se elimino el ejercicio " + tableName);

			drop = dbconn.prepareStatement("DELETE FROM CONTABLEejercicios"
					+ " where ejercicio = " + idEjercicio + " and idempresa = "
					+ idempresa.toString());
			n = drop.executeUpdate();
			log.info("Se elimino la tabla " + tableName);

		} catch (SQLException sqlException) {
			log.error("dropEjercicioPostgres: Error SQL: " + sqlException);
			salida = "dropEjercicioPostgres: Error SQL: " + sqlException;
		} catch (Exception ex) {
			log.error("dropEjercicioPostgres Salida por exception: " + ex);
			salida = "dropEjercicioPostgres Salida por exception: " + ex;
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
				sqlC += "  fechaalt timestamp NOT NULL DEFAULT now(),";
				sqlC += "  fechaact timestamp,";
				sqlC += "  idempresa numeric(18),";
				sqlC += "  CONSTRAINT pk_" + tableName
						+ " PRIMARY KEY (idcuenta)";
				sqlC += ") ";
				sqlC += "WITH OIDS;";
				sqlC += "ALTER TABLE " + tableName + " OWNER TO postgres;";
				if (statement.execute(sqlC))
					bError = true;

				// movimientos
				tableName = "contableinfimov" + idEjercicio;
				log.info("Generando tabla " + tableName);
				sqlC = "";
				sqlC += "CREATE TABLE " + tableName + "";
				sqlC += "(";
				sqlC += "  idasiento  numeric(18)   not null,";
				sqlC += "  fecha      timestamp     not null,";
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
				sqlC += "  fechaalt   timestamp NOT NULL DEFAULT now(),";
				sqlC += "  fechaact   timestamp,";
				sqlC += "  idempresa numeric(18),";
				sqlC += "  CONSTRAINT pk_" + tableName
						+ " PRIMARY KEY (idasiento, renglon)";
				sqlC += ") ";
				sqlC += "WITH OIDS;";
				sqlC += "ALTER TABLE " + tableName + " OWNER TO postgres;";

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
				sqlC += "  fechaalt   timestamp NOT NULL DEFAULT now(),";
				sqlC += "  fechaact   timestamp,";
				sqlC += "  nroasiento numeric(18), ";
				sqlC += "  idempresa numeric(18),";
				sqlC += "  CONSTRAINT pk_" + tableName
						+ " PRIMARY KEY (idasiento),";
				sqlC += "  CONSTRAINT uk_" + tableName + " UNIQUE (nroasiento)";
				sqlC += ") ";
				sqlC += "WITH OIDS;";
				sqlC += "ALTER TABLE " + tableName + " OWNER TO postgres;";
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
				sqlC += "  fechaalt timestamp not null default now(),";
				sqlC += "  fechaact timestamp,";
				sqlC += "  idempresa numeric(18),";
				sqlC += " CONSTRAINT pk_" + tableName
						+ " PRIMARY KEY (idcuenta)";
				sqlC += ") ";
				sqlC += "WITH OIDS;";
				sqlC += "ALTER TABLE " + tableName + " OWNER TO postgres;";
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
				sqlC += "  fechaalt timestamp not null default now(),";
				sqlC += "  fechaact timestamp,";
				sqlC += "  idempresa numeric(18),";
				sqlC += "  CONSTRAINT pk_" + tableName
						+ " PRIMARY KEY (idcuenta)";
				sqlC += ") ";
				sqlC += " WITH OIDS;";
				sqlC += " ALTER TABLE " + tableName + " OWNER TO postgres;";
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
				sqlC += "  fechaalt timestamp not null default now(),";
				sqlC += "  fechaact timestamp,";
				sqlC += "  idempresa numeric(18),";
				sqlC += "  CONSTRAINT pk_" + tableName
						+ " PRIMARY KEY (idcuenta,anio,mes)";
				sqlC += ") ";
				sqlC += "WITH OIDS;";
				sqlC += "ALTER TABLE " + tableName + " OWNER TO postgres;";
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
				sqlC += "   fechaalt timestamp not null default now(),";
				sqlC += "   fechaact timestamp,";
				sqlC += "  idempresa numeric(18),";
				sqlC += "   CONSTRAINT pk_" + tableName
						+ " PRIMARY KEY (idcuenta)";
				sqlC += " ) ";
				sqlC += " WITH OIDS;";
				sqlC += " ALTER TABLE " + tableName + " OWNER TO postgres;";
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
				sqlC += "  fechaalt timestamp not null default now(), ";
				sqlC += "  fechaact timestamp, ";
				sqlC += "  idempresa numeric(18),";
				sqlC += "  CONSTRAINT pk_" + tableName
						+ " PRIMARY KEY (tipo,idcuenta) ";
				sqlC += ")  ";
				sqlC += "WITH OIDS; ";
				sqlC += "ALTER TABLE " + tableName + " OWNER TO postgres; ";
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
				sqlC += "  fechaalt timestamp not null default now(),";
				sqlC += "  fechaact timestamp,";
				sqlC += "  idempresa numeric(18)";
				sqlC += ") ";
				sqlC += "WITH OIDS;";
				sqlC += "ALTER TABLE " + tableName + " OWNER TO postgres;";
				if (statement.execute(sqlC))
					bError = true;

				tableName = "contablelibromayor" + idEjercicio;
				log.info("Generando VISTA " + tableName);
				sqlC = "";
				sqlC += "CREATE VIEW " + tableName + " AS ";
				sqlC += "select ";
				sqlC += "  cue.cuenta as parcuenta, ";
				sqlC += "  date_part('year',cue.fecha) as paranio, ";
				sqlC += "  date_part('month',cue.fecha) as parmes, ";
				sqlC += "  cue.cent_cost as parcc, ";
				sqlC += "  cue.cent_cost1 as parcc1, ";
				sqlC += "  to_char(cue.fecha,'DD/MM/YYYY') as fecha,  ";
				sqlC += "  cue.idasiento, cue.renglon,  ";
				sqlC += "  case cue.tipomov when 1 then cue.importe else 0 end as debe,  ";
				sqlC += "  case cue.tipomov when 2 then cue.importe else 0 end as haber,  ";
				sqlC += "  cc.descripcion as centrocosto,  ";
				sqlC += "  cc1.descripcion as subcentrocosto,  ";
				sqlC += "  cue.detalle,    ";
				sqlC += "  cue.idempresa  ";
				sqlC += "from contableinfimov" + idEjercicio + " cue  ";
				sqlC += "    left outer join contablecencosto cc on cue.cent_cost = cc.idcencosto AND cue.idempresa = cc.idempresa   ";
				sqlC += "    left outer join contablecencosto cc1 on cue.cent_cost1 = cc1.idcencosto AND cue.idempresa = cc1.idempresa     ;";
				if (statement.execute(sqlC))
					bError = true;
				/*
				 * tableName = "contablesaldocue" + idEjercicio;
				 * log.info("Generando VISTA " + tableName); sqlC = ""; sqlC +=
				 * "CREATE VIEW " + tableName + " AS "; sqlC += "select
				 * plan.idcuenta,"; sqlC += "plan.imputable,"; sqlC +=
				 * "plan.nivel,"; sqlC += "plan.ajustable,"; sqlC +=
				 * "plan.resultado,"; sqlC += "plan.cent_cost,"; sqlC +=
				 * "plan.cent_cost1,"; sqlC += "sum(CASE cue.tipomov "; sqlC +=
				 * "WHEN 1 THEN cue.importe "; sqlC += " ELSE 0 ::numeric ";
				 * sqlC += " END) AS debe, "; sqlC += " sum(CASE cue.tipomov ";
				 * sqlC += " WHEN 2 THEN cue.importe "; sqlC += " ELSE
				 * 0::numeric "; sqlC += " END) AS haber, "; sqlC += " sum(CASE
				 * cue.tipomov "; sqlC += " WHEN 1 THEN cue.importe "; sqlC += "
				 * ELSE 0 ::numeric "; sqlC += " END) - sum(CASE cue.tipomov ";
				 * sqlC += " WHEN 2 THEN cue.importe "; sqlC += " ELSE
				 * 0::numeric "; sqlC += " END) as saldo "; sqlC += " from
				 * contableinfiplan" + idEjercicio + " plan left outer join ";
				 * sqlC += " contableinfimov" + idEjercicio + " cue on
				 * plan.idcuenta = cue.cuenta "; sqlC += " group by
				 * plan.idcuenta, "; sqlC += " plan.imputable,"; sqlC += "
				 * plan.nivel,"; sqlC += " plan.ajustable, "; sqlC += "
				 * plan.resultado, "; sqlC += " plan.cent_cost, "; sqlC += "
				 * plan.cent_cost1 "; sqlC += " order by 1 "; if
				 * (statement.execute(sqlC)) bError = true;
				 */
				/* inicia EJV saldo cuentas */
				/*
				 * verificar si se crea esta vista + contablesaldocueEJERCICIO,
				 * o solo una de las dos
				 */
				tableName = "contablesaldocuexmes" + idEjercicio;
				log.info("Generando VISTA " + tableName);
				sqlC = "";

				sqlC += "CREATE VIEW contablesaldocuexmes" + idEjercicio
						+ " AS ";
				sqlC += "SELECT p.anio, p.mes, p.idcuenta, p.cuenta, p.imputable, p.nivel, p.resultado, p.cent_cost, p.cent_cost1, sum( ";
				sqlC += "        CASE cue.tipomov ";
				sqlC += "            WHEN 1 THEN cue.importe ";
				sqlC += "            ELSE 0::numeric ";
				sqlC += "        END) AS debe, sum( ";
				sqlC += "        CASE cue.tipomov ";
				sqlC += "            WHEN 2 THEN cue.importe ";
				sqlC += "            ELSE 0::numeric ";
				sqlC += "        END) AS haber, sum( ";
				sqlC += "        CASE cue.tipomov ";
				sqlC += "            WHEN 1 THEN cue.importe ";
				sqlC += "            ELSE 0::numeric ";
				sqlC += "        END) - sum( ";
				sqlC += "        CASE cue.tipomov ";
				sqlC += "            WHEN 2 THEN cue.importe ";
				sqlC += "            ELSE 0::numeric ";
				sqlC += "        END) AS saldo, ";
				sqlC += " p.idempresa ";
				sqlC += "   FROM ( SELECT eje.ejercicio AS anio, meses.idmes AS mes, plan.idcuenta, plan.cuenta, plan.imputable, plan.nivel, plan.ajustable, plan.resultado, plan.cent_cost, plan.cent_cost1,plan.idempresa ";
				sqlC += "           FROM contableejercicios eje, globalmeses meses, contableinfiplan"
						+ idEjercicio + " plan ";
				sqlC += "          WHERE eje.ejercicio = " + idEjercicio
						+ "::numeric) p ";
				sqlC += "   LEFT JOIN contableinfimov"
						+ idEjercicio
						+ " cue ON date_part('year'::text, cue.fecha)::numeric = p.anio AND date_part('month'::text, cue.fecha)::numeric = p.mes AND cue.cuenta = p.idcuenta and cue.idempresa = p.idempresa ";
				sqlC += "  GROUP BY p.anio, p.mes, p.idcuenta, p.imputable, p.nivel, p.resultado, p.cent_cost, p.cent_cost1, p.cuenta, p.idempresa ";
				sqlC += "  ORDER BY p.anio, p.mes, p.idcuenta DESC ";

				if (statement.execute(sqlC))
					bError = true;
				/* FIN EJV saldo cuentas */

				tableName = "contablesumasysaldos" + idEjercicio;
				log.info("Generando VISTA " + tableName);
				sqlC = "";
				sqlC += "CREATE VIEW " + tableName + " AS ";
				sqlC += " SELECT p.anio,p.mes,p.idcuenta,p.imputable,p.nivel,p.resultado,p.cent_cost,p.cent_cost1, ";
				sqlC += " sum(CASE cue.tipomov WHEN 1 THEN cue.importe ELSE 0 ::numeric END) AS debe,";
				sqlC += " sum(CASE cue.tipomov WHEN 2 THEN cue.importe ELSE 0 ::numeric END) AS haber, ";
				sqlC += " sum(CASE cue.tipomov WHEN 1 THEN cue.importe ELSE 0 ::numeric END) ";
				sqlC += " -sum(CASE cue.tipomov WHEN 2 THEN cue.importe ELSE 0::numeric END) AS saldo, ";
				sqlC += " p.idempresa ";
				sqlC += " FROM (";
				sqlC += " SELECT ";
				sqlC += " eje.ejercicio as anio,meses.idmes as mes,  plan.idcuenta,plan.imputable,plan.nivel, ";
				sqlC += " plan.ajustable,plan.resultado,plan.cent_cost,plan.cent_cost1, plan.idempresa ";
				sqlC += " FROM CONTABLEejercicios eje,GLOBALMeses meses,CONTABLEinfiplan"
						+ idEjercicio + " plan ";
				sqlC += "WHERE  EJE.ejercicio = " + idEjercicio + " ) P";
				sqlC += " left JOIN contableinfimov"
						+ idEjercicio
						+ " cue ON date_part('year'::text, cue.fecha)::numeric=P.anio ";
				sqlC += "  and  date_part('month'::text, cue.fecha)::numeric=p.mes and cuenta = p.idcuenta and cue.idempresa = p.idempresa ";
				// sqlC += " and date_part('month'::text,
				// cue.fecha)::numeric=p.mes and cuenta = p.idcuenta and
				// contableinfimov"+idEjercicio+".idempresa = p.idempresa " ;
				sqlC += " GROUP BY ";
				sqlC += " p.anio,p.mes,p.idcuenta,p.imputable,p.nivel,p.resultado,p.cent_cost,p.cent_cost1, p.idempresa ";
				sqlC += " order by 1,2,3 desc ";
				if (statement.execute(sqlC))
					bError = true;

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

	public ResultSet getLibroMayor(int idEjercicio, Long cuenta, int anio,
			int mes, Long cc1, Long cc2, BigDecimal idempresa) {
		ResultSet salida = null;
		boolean bError = false;
		try {
			Statement statement = dbconn.createStatement();
			String tableName = "contablelibromayor" + idEjercicio;
			String cQuery = "";
			cQuery += "select ";
			cQuery += "  fecha, idasiento ,renglon, debe, haber, centrocosto, subcentrocosto, detalle  ";
			cQuery += "from " + tableName;
			cQuery += "  where  ";
			cQuery += "     parcuenta = " + cuenta.toString();
			cQuery += " and  paranio   = " + anio + " ";
			cQuery += " and  parmes    = " + mes + "  ";
			cQuery += " and  parcc     = " + cc1.toString() + "  ";
			cQuery += " and  parcc1    = " + cc2.toString() + " ";
			cQuery += " and  idempresa = " + idempresa.toString();
			return statement.executeQuery(cQuery);
		} catch (SQLException sqlException) {
			log
					.error("ResultSet getLibroMayor(int idEjercicio, Long cuenta, int anio, int mes, Long cc1, Long cc2): "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("ResultSet getLibroMayor(int idEjercicio, Long cuenta, int anio, int mes, Long cc1, Long cc2) Salida por exception: "
							+ ex);
		}
		return salida;
	}

	public ResultSet getLibroMayorSinCC( Long cuenta, int anio,
			 BigDecimal idempresa) {
		ResultSet salida = null;
		try {
			Statement statement = dbconn.createStatement();
			String tableName = "contablelibromayor" + anio;
			String cQuery = "";
			cQuery += "select ";
			cQuery += " parcuenta, paranio, parmes, parcc, parcc1,  ";
			cQuery += " fecha, ";
			cQuery += " idasiento, renglon, ";
			cQuery += " debe::numeric(18,2) as debe, ";
			cQuery += " haber::numeric(18,2) as haber,  ";
			cQuery += " centrocosto, subcentrocosto, detalle, idempresa ";
			cQuery += "from " + tableName;
			cQuery += "  where  ";
			cQuery += "     parcuenta = " + cuenta.toString();
			cQuery += " and  paranio   = " + anio + " ";			
			cQuery += " and  idempresa = " + idempresa.toString()+ " ORDER BY idasiento ";
			return statement.executeQuery(cQuery);
		} catch (SQLException sqlException) {
			log
					.error("ResultSet getLibroMayorSinCC(....): "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("ResultSet getLibroMayor(.....) Salida por exception: "
							+ ex);
		}
		return salida;
	}

	// ------------------------------------------------------------------------------------------------------------------
	// -- manipulacion del libro diario
	public ResultSet getLibroDiario(int idEjercicio, Long cuentaDesde,
			Long cuentaHasta, BigDecimal idempresa) {
		ResultSet salida = null;
		try {
			Statement statement = dbconn.createStatement();
			String cQuery = "";
			cQuery += "select ";
			cQuery += " mov.idasiento as asiento,";
			cQuery += " mov.renglon, ";
			cQuery += " mov.cuenta, ";
			cQuery += " cue.cuenta, ";
			cQuery += " mov.detalle, ";
			cQuery += "CASE mov.tipomov ";
			cQuery += "   WHEN 1 THEN mov.importe ELSE 0::numeric END AS debe,";
			cQuery += "CASE mov.tipomov ";
			cQuery += "   WHEN 2 THEN mov.importe ELSE 0::numeric END AS haber,";
			cQuery += " ley.leyenda ";
			cQuery += "  from ";
			cQuery += " contableinfimov"
					+ idEjercicio
					+ " mov left outer join contableleyendas"
					+ idEjercicio
					+ " ley on mov.idasiento = ley.idasiento and mov.idempresa = ley.idempresa,  ";
			cQuery += "      contableinfiplan" + idEjercicio + " cue";
			cQuery += " where ";
			cQuery += "   mov.cuenta = cue.idcuenta ";
			cQuery += "   contableinfimov.idempresa = ";
			cQuery += idempresa.toString();
			cQuery += " order by 1, 2 ";
			return statement.executeQuery(cQuery);
		} catch (SQLException sqlException) {
			log
					.error("getLibroDiario(int idEjercicio, Long cuentaDesde, Long cuentaHasta): "
							+ sqlException);
		} catch (Exception ex) {
			log
					.error("getLibroDiario(int idEjercicio, Long cuentaDesde, Long cuentaHasta) Salida por exception: "
							+ ex);
		}
		return salida;
	}

	// ----------------------------------------------------------------------------------------------------------------------------

	// para poner en el bean contable

	// todas las monedas
	public List getMonedas(BigDecimal idempresa) throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "Select * from contablemonedas where idempresa = "
				+ idempresa.toString();
		List vecSalida = new ArrayList();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			ResultSetMetaData md = rsSalida.getMetaData();
			while (rsSalida.next()) {
				int totCampos = md.getColumnCount() - 1;
				String[] sSalida = new String[totCampos + 1];
				int i = 0;
				while (i <= totCampos) {
					sSalida[i] = rsSalida.getString(++i);
				}
				vecSalida.add(sSalida);
			}
		} catch (SQLException sqlException) {
			log.error("public List getMonedas() Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("public List getMonedas() Salida por exception: " + ex);
		}
		return vecSalida;
	}

	// todos las monedas por alguna ocurrencia
	public List getMonedas(String ocurrencia, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "Select * from contablemonedas where " + "idempresa = "
				+ idempresa.toString() + " upper(moneda) LIKE '%"
				+ ocurrencia.toUpperCase().trim() + "%'  ";
		List vecSalida = new ArrayList();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			ResultSetMetaData md = rsSalida.getMetaData();
			while (rsSalida.next()) {
				int totCampos = md.getColumnCount() - 1;
				String[] sSalida = new String[totCampos + 1];
				int i = 0;
				while (i <= totCampos) {
					sSalida[i] = rsSalida.getString(++i);
				}
				vecSalida.add(sSalida);
			}
		} catch (SQLException sqlException) {
			log.error("public List getMonedas(String ocurrencia) Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("public List getMonedas(String ocurrencia) Salida por exception: "
							+ ex);
		}
		return vecSalida;
	}

	// monedas por PK
	public List getMonedasPK(Integer pk, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "Select * from contablemonedas where idmoneda = "
				+ pk.toString() + " idempresa = " + idempresa.toString();
		List vecSalida = new ArrayList();
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			ResultSetMetaData md = rsSalida.getMetaData();
			while (rsSalida.next()) {
				int totCampos = md.getColumnCount() - 1;
				String[] sSalida = new String[totCampos + 1];
				int i = 0;
				while (i <= totCampos) {
					sSalida[i] = rsSalida.getString(++i);
				}
				vecSalida.add(sSalida);
			}
		} catch (SQLException sqlException) {
			log.error("public List getMonedasPK(Integer pk) Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("public List getMonedasPK(Integer pk) Salida por exception: "
							+ ex);
		}
		return vecSalida;
	}

	// Eliminar monedas por PK
	public String delMonedas(Integer pk, BigDecimal idempresa)
			throws EJBException {
		ResultSet rsSalida = null;
		String cQuery = "Select * from contablemonedas where idmoneda = "
				+ pk.toString() + " idempresa = " + idempresa.toString();
		String salida = "NOOK";
		try {
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			if (rsSalida.next()) {
				cQuery = "DELETE from contablemonedas where idmoneda = "
						+ pk.toString() + " idempresa = "
						+ idempresa.toString();
				statement.execute(cQuery);
				salida = "OK";
			} else {
				salida = "Error: Registro inexistente";
			}
		} catch (SQLException sqlException) {
			log.error("public String delMonedas(Integer pk) Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("public String delMonedas(Integer pk) Salida por exception: "
							+ ex);
		}
		return salida;
	}

	// insertar una moneda

	public String monedasSave(String moneda, Timestamp duracion_hasta,
			int cantidad_ceros, String detalle, String usuarioalt,
			BigDecimal idempresa) throws EJBException {
		String salida = "NOOK";
		// validaciones de datos:
		if (moneda.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacia la descripcion de la moneda";
		if (duracion_hasta == null)
			salida = "Error: No se puede dejar vacia la duracion hasta de la moneda";
		if (cantidad_ceros < 1)
			salida = "Error: La cantidad de ceros es erronea";
		if (detalle.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el detalle";
		if (usuarioalt.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar el usuario de alta vacio"; // fin
		// validaciones
		try {
			if (salida.equalsIgnoreCase("NOOK")) { // quiere decir que no
				// detecto ninguno de los/
				// errores de validacion
				PreparedStatement insert = dbconn
						.prepareStatement("INSERT INTO contablemonedas(moneda, hasta_mo, ceros_mo,detalle, usuarioalt,idempresa) VALUES(?,?,?,?,?,?)");
				insert.setString(1, moneda);
				insert.setTimestamp(2, duracion_hasta);
				insert.setInt(3, cantidad_ceros);
				insert.setString(4, detalle);
				insert.setString(5, usuarioalt);
				insert.setBigDecimal(6, idempresa);
				int n = insert.executeUpdate();
				if (n == 1)
					salida = "OK";
			}
		} catch (SQLException sqlException) {
			log.error("public String monedasSave Error SQL: " + sqlException);
		} catch (Exception ex) {
			log.error("public String monedasSave Salida por exception: " + ex);
		}
		return salida;
	}

	// update de una moneda
	public String monedasSaveOrUpdate(long idmoneda, String moneda,
			Timestamp duracion_hasta, int cantidad_ceros, String detalle,
			String usuarioact, BigDecimal idempresa) throws EJBException {
		Calendar hoy = new GregorianCalendar();
		Timestamp fecha = new Timestamp(hoy.getTime().getTime());
		String salida = "NOOK";
		// validaciones de datos:
		if (moneda.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacia la descripcion de la moneda";
		if (duracion_hasta == null)
			salida = "Error: No se puede dejar vacia la duracion hasta de la moneda";
		if (cantidad_ceros < 1)
			salida = "Error: La cantidad de ceros es erronea";
		if (detalle.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar vacio el detalle";
		if (usuarioact.equalsIgnoreCase(""))
			salida = "Error: No se puede dejar el usuario de actualizacion vacio"; // fin
		// validaciones
		// fin validaciones
		try {
			ResultSet rsSalida = null;
			String cQuery = "Select count(*) from contablemonedas where idmoneda = "
					+ idmoneda + " idempresa = " + idempresa.toString();
			Statement statement = dbconn.createStatement();
			rsSalida = statement.executeQuery(cQuery);
			int total = 0;
			if (rsSalida != null && rsSalida.next())
				total = rsSalida.getInt(1);
			PreparedStatement insert = null;
			if (total > 0) { // Existe asi que updeteo
				if (salida.equalsIgnoreCase("OK")) {
					insert = dbconn
							.prepareStatement("UPDATE contablemonedas  SET moneda=?, hasta_mo, ceros_mo, detalle, usuarioact=?, fechaact=? where idcencosto=? and idempresa =?");

					insert.setString(1, moneda);
					insert.setTimestamp(2, duracion_hasta);
					insert.setInt(3, cantidad_ceros);
					insert.setString(4, detalle);
					insert.setString(5, usuarioact);
					insert.setTimestamp(5, fecha);
					insert.setLong(7, idmoneda);
					insert.setBigDecimal(8, idempresa);
				} else {
					insert = dbconn
							.prepareStatement("INSERT INTO contablemonedas(moneda, hasta_mo, ceros_mo,detalle, usuarioalt, idempresa) VALUES(?,?,?,?,?,?)");
					insert.setString(1, moneda);
					insert.setTimestamp(2, duracion_hasta);
					insert.setInt(3, cantidad_ceros);
					insert.setString(4, detalle);
					insert.setString(5, usuarioact);
					insert.setBigDecimal(6, idempresa);
				}
				insert.executeUpdate();
				salida = "OK";
			}
		} catch (SQLException sqlException) {
			log.error("public String monedasSaveOrUpdate Error SQL: "
					+ sqlException);
		} catch (Exception ex) {
			log
					.error("public String monedasSaveOrUpdate Salida por exception: "
							+ ex);
		}
		return salida;
	}

	//	

}
