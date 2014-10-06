package ar.com.syswarp.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ResultadoContacto extends AbstractEntity {

	private BigDecimal idresultadocontacto;
	private String resultadocontacto;
	private BigDecimal idtipocontacto;
	private BigDecimal idcanalcontacto;
	private BigDecimal idmotivocontacto;
	private BigDecimal idaccioncontacto;
	private String usuarioalt;
	private String usuarioact;
	private Timestamp fechaalt;
	private Timestamp fechaact;
	private BigDecimal idempresa;

	public ResultadoContacto(BigDecimal idresultadocontacto,
			BigDecimal idtipocontacto, BigDecimal idcanalcontacto,
			BigDecimal idmotivocontacto, BigDecimal idaccioncontacto,
			BigDecimal idempresa) {
		this.idresultadocontacto = idresultadocontacto;
		this.idtipocontacto = idtipocontacto;
		this.idcanalcontacto = idcanalcontacto;
		this.idmotivocontacto = idmotivocontacto;
		this.idaccioncontacto = idaccioncontacto;
		this.idempresa = idempresa;
	}

	public ResultadoContacto(BigDecimal idresultadocontacto,
			String resultadocontacto, BigDecimal idtipocontacto,
			BigDecimal idcanalcontacto, BigDecimal idmotivocontacto,
			BigDecimal idaccioncontacto, String usuarioalt, String usuarioact,
			Timestamp fechaalt, Timestamp fechaact, BigDecimal idempresa) {
		this.idresultadocontacto = idresultadocontacto;
		this.resultadocontacto = resultadocontacto;
		this.idtipocontacto = idtipocontacto;
		this.idcanalcontacto = idcanalcontacto;
		this.idmotivocontacto = idmotivocontacto;
		this.idaccioncontacto = idaccioncontacto;
		this.usuarioalt = usuarioalt;
		this.usuarioact = usuarioact;
		this.fechaalt = fechaalt;
		this.fechaact = fechaact;
		this.idempresa = idempresa;
	}

	public ResultadoContacto(BigDecimal idresultadocontacto,
			BigDecimal idempresa) {
		this.idresultadocontacto = idresultadocontacto;
		this.idempresa = idempresa;
	}

	@Override
	public String validateCreate() {

		// 1. nulidad de campos
		if (resultadocontacto == null)
			return buildNullFieldError("resultadocontacto");
		if (idtipocontacto == null)
			return buildNullFieldError("idtipocontacto");
		if (idcanalcontacto == null)
			return buildNullFieldError("idcanalcontacto");
		if (idmotivocontacto == null)
			return buildNullFieldError("idmotivocontacto");
		if (idaccioncontacto == null)
			return buildNullFieldError("idaccioncontacto");
		if (usuarioalt == null)
			return buildNullFieldError("usuarioalt");
		if (fechaalt == null)
			return buildNullFieldError("fechaalt");

		// 2. sin nada desde la pagina
		if (resultadocontacto.trim().equalsIgnoreCase(""))
			return buildEmptyFieldError("resultadocontacto");
		if (usuarioalt.trim().equalsIgnoreCase(""))
			return buildEmptyFieldError("usuarioalt");

		return null;
	}
	
	@Override
	public String validateUpdate() {

		// 1. nulidad de campos
		if (idresultadocontacto == null)
			return buildNullFieldError("idresultadocontacto");
		if (resultadocontacto == null)
			return buildNullFieldError("resultadocontacto");
		if (idtipocontacto == null)
			return buildNullFieldError("idtipocontacto");
		if (idcanalcontacto == null)
			return buildNullFieldError("idcanalcontacto");
		if (idmotivocontacto == null)
			return buildNullFieldError("idmotivocontacto"); 
		if (idaccioncontacto == null)
			return buildNullFieldError("idaccioncontacto");
		if (usuarioact == null)
			return buildNullFieldError("usuarioact");
		if (fechaact == null)
			return buildNullFieldError("fechaact");

		// 2. sin nada desde la pagina
		if (resultadocontacto.trim().equalsIgnoreCase(""))
			return buildEmptyFieldError("accioncontacto");
		if (usuarioact.trim().equalsIgnoreCase(""))
			return buildEmptyFieldError("usuarioact");

		return null;
	}
	
	public BigDecimal getIdresultadocontacto() {
		return idresultadocontacto;
	}

	public String getResultadocontacto() {
		return resultadocontacto;
	}

	public BigDecimal getIdtipocontacto() {
		return idtipocontacto;
	}

	public BigDecimal getIdcanalcontacto() {
		return idcanalcontacto;
	}

	public BigDecimal getIdmotivocontacto() {
		return idmotivocontacto;
	}

	public BigDecimal getIdaccioncontacto() {
		return idaccioncontacto;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public String getUsuarioact() {
		return usuarioact;
	}

	public Timestamp getFechaalt() {
		return fechaalt;
	}

	public Timestamp getFechaact() {
		return fechaact;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

}
