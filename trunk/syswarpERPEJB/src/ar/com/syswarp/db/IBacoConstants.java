package ar.com.syswarp.db;

public interface IBacoConstants {

	public static interface IBacoTables {

		public String TABLE_CONTACTO = "SASCONTACTOS";
		public String TABLE_TIPO_CONTACTO = "SASTIPOSCONTACTOS";
		public String TABLE_CANAL_CONTACTO = "SASCANALESCONTACTOS";
		public String TABLE_MOTIVO_CONTACTO = "SASMOTIVOSCONTACTOS";
		public String TABLE_ACCION_CONTACTO = "SASACCIONESCONTACTOS";
		public String TABLE_RESULTADO_CONTACTO = "SASRESULTADOSCONTACTOS";

	}
	
	public static interface IBacoBaseQuerys {
		
		public String BASE_QUERY_CONTACTO = "" +
			" SELECT " +
			"   sc.idcontacto, " +
			"   sc.descripcion, " +
			"   sc.idtipocontacto, " +
			"   tc.tipocontacto, " +
			"   sc.idcanalcontacto, " + 
			"   cc.canalcontacto, " +
			"   sc.idmotivocontacto, " +
			"   mc.motivocontacto, " +
			"   sc.idcliente, " +
			"   cl.razon, " +
			"   sc.usuarioalt, " +
			"   sc.usuarioact, " +
			"   sc.fechaalt, " +
			"   sc.fechaact, " +
			"   sc.idempresa, " +
			"   sc.idaccioncontacto, " +
			"   ac.accioncontacto, " +
			"   sc.idresultadocontacto, " +
			"   rc.resultadocontacto " +
			" FROM SASCONTACTOS sc " +
			"   INNER JOIN sastiposcontactos tc on (sc.idtipocontacto = tc.idtipocontacto) " + 
			"   INNER JOIN sascanalescontactos cc on (sc.idcanalcontacto = cc.idcanalcontacto) " +
			"   INNER JOIN sasmotivoscontactos mc on (sc.idmotivocontacto = mc.idmotivocontacto)  " +
			"   INNER JOIN sasaccionescontactos ac on (sc.idaccioncontacto = ac.idaccioncontacto) " +
			"   INNER JOIN sasresultadoscontactos rc on (sc.idresultadocontacto = rc.idresultadocontacto) " +
			"   INNER JOIN clientesclientes cl on (sc.idcliente = cl.idcliente)" +
			" WHERE ac.idempresa = ?";
		
		public String BASE_QUERY_ACCION_CONTACTO = "" + 
			" SELECT" +
			"   ac.idaccioncontacto," +
			"   ac.accioncontacto," +
			"   ac.idtipocontacto," +
			"   tc.tipocontacto," +
			"   ac.idcanalcontacto," +
			"   cc.canalcontacto," +
			"   ac.idmotivocontacto," +
			"   mc.motivocontacto," +
			"   ac.usuarioalt," +
			"   ac.usuarioact," +
			"   ac.fechaalt," +
			"   ac.fechaact," +
			"   ac.idempresa" +
			" FROM " + IBacoTables.TABLE_ACCION_CONTACTO + " ac" +
			"   INNER JOIN " + IBacoTables.TABLE_MOTIVO_CONTACTO + " mc ON (ac.idmotivocontacto = mc.idmotivocontacto)" +
			"   INNER JOIN " + IBacoTables.TABLE_CANAL_CONTACTO + " cc ON (ac.idcanalcontacto = cc.idcanalcontacto)" +
			"   INNER JOIN " + IBacoTables.TABLE_TIPO_CONTACTO + " tc ON (ac.idtipocontacto = tc.idtipocontacto)" +
			" WHERE ac.idempresa = ?";

		public String BASE_QUERY_RESULTADO_CONTACTO = "" + 
			" SELECT" +
			"   rc.idresultadocontacto," +
			"   rc.resultadocontacto," +
			"   rc.idtipocontacto," +
			"   tc.tipocontacto," +
			"   rc.idcanalcontacto," +
			"   cc.canalcontacto," +
			"   rc.idmotivocontacto," +
			"   mc.motivocontacto," +
			"   rc.idaccioncontacto," +
			"   ac.accioncontacto," +
			"   rc.usuarioalt," +
			"   rc.usuarioact," +
			"   rc.fechaalt," +
			"   rc.fechaact," +
			"   rc.idempresa" +
			" FROM " + IBacoTables.TABLE_RESULTADO_CONTACTO + " rc" +
			"   INNER JOIN " + IBacoTables.TABLE_ACCION_CONTACTO + " ac ON (rc.idaccioncontacto = ac.idaccioncontacto)" +
			"   INNER JOIN " + IBacoTables.TABLE_MOTIVO_CONTACTO + " mc ON (rc.idmotivocontacto = mc.idmotivocontacto)" +
			"   INNER JOIN " + IBacoTables.TABLE_CANAL_CONTACTO + " cc ON (rc.idcanalcontacto = cc.idcanalcontacto)" +
			"   INNER JOIN " + IBacoTables.TABLE_TIPO_CONTACTO + " tc ON (rc.idtipocontacto = tc.idtipocontacto)" +
			" WHERE rc.idempresa = ?";
		
	}

}
