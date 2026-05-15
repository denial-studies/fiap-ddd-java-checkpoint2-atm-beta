package model.valueobjects;

import java.math.BigDecimal;

public class Dinheiro {
	private BigDecimal valor;

	public Dinheiro(BigDecimal valor) {
		this.valor = valor;
	}

	public Boolean menorQue(Dinheiro outro) {
		return this.valor.compareTo(outro.getValor()) < 0;
	}

	public Boolean maiorQue(Dinheiro outro) {
		return this.valor.compareTo(outro.getValor()) > 0;
	}

	public Dinheiro somar(Dinheiro outro) {
		return new Dinheiro(this.valor.add(outro.getValor()));
	}

	public Dinheiro subtrair(Dinheiro outro) {
		return new Dinheiro(this.valor.subtract(outro.getValor()));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Dinheiro dinheiro = (Dinheiro) obj;
		return valor != null ? valor.compareTo(dinheiro.valor) == 0 : dinheiro.valor == null;
	}

	public BigDecimal getValor() {
		return valor;
	}
}
