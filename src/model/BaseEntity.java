package model;

import java.time.LocalDate;
import java.util.UUID;

public abstract class BaseEntity {
	protected UUID id;
	protected LocalDate dataCriacao;
	
	protected BaseEntity() {
		this.id = UUID.randomUUID();
		this.dataCriacao = LocalDate.now();
	}
	
	public UUID getId() {
		return id;
	}
	
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		BaseEntity that = (BaseEntity) obj;
		return id != null ? id.equals(that.id) : that.id == null;
	}
}
