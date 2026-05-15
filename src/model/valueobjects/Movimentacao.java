package model.valueobjects;

import java.time.LocalDateTime;

import model.TipoMovimentacao;

public class Movimentacao {
	private LocalDateTime dataHora;
	private Dinheiro valor;
	private TipoMovimentacao tipo;

	public Movimentacao(LocalDateTime dataHora, Dinheiro valor, TipoMovimentacao tipo) {
		this.dataHora = dataHora;
		this.valor = valor;
		this.tipo = tipo;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public Dinheiro getValor() {
		return valor;
	}

	public TipoMovimentacao getTipo() {
		return tipo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Movimentacao that = (Movimentacao) obj;
		if (dataHora != null ? !dataHora.equals(that.dataHora) : that.dataHora != null) return false;
		if (valor != null ? !valor.equals(that.valor) : that.valor != null) return false;
		return tipo == that.tipo;
	}
}
