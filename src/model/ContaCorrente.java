package model;

import java.math.BigDecimal;

import model.valueobjects.Dinheiro;
import model.valueobjects.Movimentacao;

public class ContaCorrente extends Conta {
	private static final Double TAXA_MANUTENCAO = 25.00;

	public ContaCorrente(Cliente cliente, Dinheiro saldo) {
		super(cliente, null, saldo, TAXA_MANUTENCAO);
	}

	public void aplicarTaxaMensal() {
		Dinheiro taxaDinheiro = new Dinheiro(BigDecimal.valueOf(TAXA_MANUTENCAO));
		this.saldo = this.saldo.subtrair(taxaDinheiro);
		this.movimentacoes.add(new Movimentacao(java.time.LocalDateTime.now(), taxaDinheiro, TipoMovimentacao.TAXA));
	}

	@Override
	protected void aplicarRegraDeTaxa() {
		aplicarTaxaMensal();
	}
}
