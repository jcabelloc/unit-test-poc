package pe.itana.unitestpoc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
public class Cliente implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codCliente;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column
	@Enumerated(EnumType.STRING)
	private TipoDocumento tipoDocumento;
	
	@Column(unique = true, length = 20)
	private String nroDocumento;
		
	public Cliente(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}
	
	public static enum TipoDocumento {DNI, RUC}
	
	public Cliente withCodCliente(Integer codCliente) {
		setCodCliente(codCliente);
		return this;
	}

}
