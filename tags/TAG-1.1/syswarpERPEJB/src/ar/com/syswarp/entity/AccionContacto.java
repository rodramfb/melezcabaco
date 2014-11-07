package ar.com.syswarp.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AccionContacto extends AbstractEntity {

	private BigDecimal idaccioncontacto;
	private String accioncontacto;
	private BigDecimal idtipocontacto;
	private BigDecimal idcanalcontacto;
	private BigDecimal idmotivocontacto;
	private String usuarioalt;
	private String usuarioact;
	private Timestamp fechaalt;
	private Timestamp fechaact;
	private BigDecimal idempresa;

	public AccionContacto(BigDecimal idaccioncontacto,
			BigDecimal idtipocontacto, BigDecimal idcanalcontacto,
			BigDecimal idmotivocontacto, BigDecimal idempresa) {
		this.idaccioncontacto = idaccioncontacto;
		this.idtipocontacto = idtipocontacto;
		this.idcanalcontacto = idcanalcontacto;
		this.idmotivocontacto = idmotivocontacto;
		this.idempresa = idempresa;
	}

	public AccionContacto(BigDecimal idaccioncontacto, String accioncontacto,
			BigDecimal idtipocontacto, BigDecimal idcanalcontacto,
			BigDecimal idmotivocontacto, String usuarioalt, String usuarioact,
			Timestamp fechaalt, Timestamp fechaact, BigDecimal idempresa) {
		this.idaccioncontacto = idaccioncontacto;
		this.accioncontacto = accioncontacto;
		this.idtipocontacto = idtipocontacto;
		this.idcanalcontacto = idcanalcontacto;
		this.idmotivocontacto = idmotivocontacto;
		this.usuarioalt = usuarioalt;
		this.usuarioact = usuarioact;
		this.fechaalt = fechaalt;
		this.fechaact = fechaact;
		this.idempresa = idempresa;
	}

	public AccionContacto(BigDecimal idaccioncontacto, BigDecimal idempresa) {
		this.idaccioncontacto = idaccioncontacto;
		this.idempresa = idempresa;
	}

	@Override
	public String validateCreate() {

		// 1. nulidad de campos
		if (accioncontacto == null)
			return buildNullFieldError("accioncontacto");
		if (idtipocontacto == null)
			return buildNullFieldError("idtipocontacto");
		if (idcanalcontacto == null)
			return buildNullFieldError("idcanalcontacto");
		if (idmotivocontacto == null)
			return buildNullFieldError("idmotivocontacto"); 
		if (usuarioalt == null)
			return buildNullFieldError("usuarioalt");
		if (fechaalt == null)
			return buildNullFieldError("fechaalt");

		// 2. sin nada desde la pagina
		if (accioncontacto.trim().equalsIgnoreCase(""))
			return buildEmptyFieldError("accioncontacto");
		if (usuarioalt.trim().equalsIgnoreCase(""))
			return buildEmptyFieldError("usuarioalt");

		return null;
	}
	
	@Override
	public String validateUpdate() {

		// 1. nulidad de campos
		if (idaccioncontacto == null)
			return buildNullFieldError("idaccioncontacto");
		if (accioncontacto == null)
			return buildNullFieldError("accioncontacto");
		if (idtipocontacto == null)
			return buildNullFieldError("idtipocontacto");
		if (idcanalcontacto == null)
			return buildNullFieldError("idcanalcontacto");
		if (idmotivocontacto == null)
			return buildNullFieldError("idmotivocontacto"); 
		if (usuarioact == null)
			return buildNullFieldError("usuarioact");
		if (fechaact == null)
			return buildNullFieldError("fechaact");

		// 2. sin nada desde la pagina
		if (accioncontacto.trim().equalsIgnoreCase(""))
			return buildEmptyFieldError("accioncontacto");
		if (usuarioact.trim().equalsIgnoreCase(""))
			return buildEmptyFieldError("usuarioact");

		return null;
	}
	
	public BigDecimal getIdaccioncontacto() {
		return idaccioncontacto;
	}

	public String getAccioncontacto() {
		return accioncontacto;
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
