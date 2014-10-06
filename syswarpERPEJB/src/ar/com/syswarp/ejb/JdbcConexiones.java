package ar.com.syswarp.ejb;
//c3pO
//import com.mchange.v2.c3p0.*; 
import javax.sql.DataSource;
//import com.mchange.v2.c3p0.DataSources;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import javax.sql.*;
import org.apache.log4j.*;

public class JdbcConexiones {
	
	static Logger log = Logger.getLogger(JdbcConexiones.class);
	
	private Properties props;
	private String url;
	private String clase;
	private String usuario;
	private String clave;
	private String pool; 
	public JdbcConexiones() {
		super();
		try {
			props = new Properties();
			props.load(JdbcConexiones.class
					.getResourceAsStream("system.properties"));

			url = props.getProperty("conn.url").trim();
			clase = props.getProperty("conn.clase").trim();
			usuario = props.getProperty("conn.usuario").trim();
			clave = props.getProperty("conn.clave").trim();
			pool = props.getProperty("conn.pool").trim();
	
	} catch (IOException IOException) {
		log.error("Error IO: " + IOException);		
	} catch (Exception ex) {
		log.error("JdbcConexiones Salida por exception: " + ex);
	}
   }
	
  public Connection getConexion(){
	  Connection conexion=null;
	  try{
		// sin citripio   
  		Class.forName(clase);
		conexion =  DriverManager.getConnection(url, usuario, clave);
	    // con citripio
		/*  
		ComboPooledDataSource cpds = new ComboPooledDataSource(); 
		cpds.setDriverClass( clase );
		cpds.setJdbcUrl( url ); 
		cpds.setUser(usuario); 
		cpds.setPassword(clave);
		cpds.setMaxPoolSize(200);
		cpds.setMaxPoolSize(1);	
        conexion = cpds.getConnection();
		*/
		
    } catch (SQLException sqlException) {
			log.error("JdbcConexiones - getConexion() Error SQL: " + sqlException);  
	} catch (Exception ex) {
		log.error("JdbcConexiones - getConexion() Salida por exception: " + ex);
	}
	return conexion;  
  }	
  
  
  public void closeConexion(Connection conexion){
	  try{
		if (conexion != null && !conexion.isClosed()){
		   conexion.close() ;
		  
		} else {
			log.info("La conexion JDBC se encontraba cerrada o bien era inexistente");
		}
    } catch (SQLException sqlException) {
			log.error("JdbcConexiones - getConexion() Error SQL: " + sqlException);  
	} catch (Exception ex) {
		log.error("JdbcConexiones - getConexion() Salida por exception: " + ex);
	}
  
  }	
  
  
}


/*
			if (pool.equalsIgnoreCase("S")){
				String jndiName = "java:/VtvDS";
				//
				Class.forName(clase);
				DataSource unpooled = DataSources.unpooledDataSource(url,usuario,clave);
                DataSource pooled = DataSources.pooledDataSource( unpooled );                
	            javax.naming.Context contextoInicial = new javax.naming.InitialContext();            
	            contextoInicial.rebind( jndiName, pooled );
	            //
	            //DataSource fuenteDatos = (DataSource) contextoInicial.lookup(jndiName);
	            //this.dbconn =  fuenteDatos.getConnection();	
	            this.dbconn =  pooled.getConnection();	
           
	            
	            if (this.dbconn != null && !this.dbconn.isClosed()){	            
	            	log.info("vtvBue. Conexion via JNDI Exitosa. ");
	            } else {
  			      Class.forName(clase);
		          conexion = DriverManager.getConnection(url, usuario, clave);
		          this.dbconn = conexion;
		          if (this.dbconn != null){	            
		           	log.info("vtvBue. Conexion via JDBC Forzada. ");
		          }	            	
	            }
			} else {
			    Class.forName(clase);
	            conexion = DriverManager.getConnection(url, usuario, clave);
	            this.dbconn = conexion;
	            if (this.dbconn != null){	            
	            	log.info("vtvBue. Conexion via JDBC Exitosa. ");
	            }	            
			}
			
		} catch (java.lang.ClassNotFoundException cnfException) {
			log.error("Error driver : " + cnfException);
		} catch (SQLException sqlException) {
			log.error("Error SQL: " + sqlException);
		} catch (IOException IOException) {
			log.error("Error SQL: " + IOException);
		} catch (Exception ex) {
			log.error("Salida por exception: " + ex);
		}
 
  
  
*/
