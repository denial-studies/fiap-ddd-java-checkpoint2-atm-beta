package model;

import java.time.LocalDate;
import java.util.UUID;

public abstract class BaseEntity {
	private UUID id;
	private LocalDate dataCriacao;
	
	BaseEntity() {
		this.id = UUID.randomUUID();
		this.dataCriacao = LocalDate.now(); // Doc disse que era hora do seu OS 
	}
	
	public UUID getId() {
		return id;
	}
	
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}
	
	public Boolean equals(BaseEntity obj) {
		return this
				.getId()
				.toString()
				.equals(
						obj
						.getId()
						.toString()
				);
	}
}
