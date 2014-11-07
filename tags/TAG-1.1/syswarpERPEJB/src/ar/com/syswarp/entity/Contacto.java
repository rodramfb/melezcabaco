package ar.com.syswarp.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Contacto extends AbstractEntity {

	private BigDecimal idcontacto;
	private String descripcion;
	private BigDecimal idtipocontacto;
	private BigDecimal idcanalcontacto;
	private BigDecimal idmotivocontacto;
	private BigDecimal idaccioncontacto;
	private BigDecimal idresultadocontacto;
	private BigDecimal idcliente;
	private String usuarioalt;
	private String usuarioact;
	private Timestamp fechaalt;
	private Timestamp fechaact;
	private BigDecimal idempresa;

	@Override
	public String validateCreate() {

		// 1. nulidad de campos
		if (descripcion == null)
			return buildNullFieldError("descripcion");
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
		if (descripcion.trim().equalsIgnoreCase(""))
			return buildEmptyFieldError("descripcion");
		if (usuarioalt.trim().equalsIgnoreCase(""))
			return buildEmptyFieldError("usuarioalt");

		return null;
	}

	@Override
	public String validateUpdate() {

		// 1. nulidad de campos
		if (idcontacto == null)
			return buildNullFieldError("idcontacto");
		if (descripcion == null)
			return buildNullFieldError("descripcion");
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
		if (descripcion.trim().equalsIgnoreCase(""))
			return buildEmptyFieldError("descripcion");
		if (usuarioact.trim().equalsIgnoreCase(""))
			return buildEmptyFieldError("usuarioalt");

		return null;
	}

	public BigDecimal getIdcontacto() {
		return idcontacto;
	}

	public String getDescripcion() {
		return descripcion;
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

	public BigDecimal getIdresultadocontacto() {
		return idresultadocontacto;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
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
