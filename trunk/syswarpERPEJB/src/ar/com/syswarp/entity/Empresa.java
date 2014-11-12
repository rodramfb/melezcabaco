package ar.com.syswarp.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Empresa extends AbstractEntity {

	private BigDecimal idempresa;
	private String empresa;
	private String usuarioalt;
	private String usuarioact;
	private Timestamp fechaalt;
	private Timestamp fechaact;

	@Override
	public String validateCreate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String validateUpdate() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}

	public Timestamp getFechaalt() {
		return fechaalt;
	}

	public void setFechaalt(Timestamp fechaalt) {
		this.fechaalt = fechaalt;
	}

	public Timestamp getFechaact() {
		return fechaact;
	}

	public void setFechaact(Timestamp fechaact) {
		this.fechaact = fechaact;
	}

}
