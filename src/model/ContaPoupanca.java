package model;

import java.math.BigDecimal;

import model.valueobjects.Dinheiro;
import model.valueobjects.Movimentacao;

public class ContaPoupanca extends Conta {
	private static final Double RENDIMENTO_MENSAL = 0.01; // Representa 1.0%

	public ContaPoupanca(Cliente cliente, Dinheiro saldo) {
		super(cliente, null, saldo, RENDIMENTO_MENSAL);
	}

	public void aplicarTaxaMensal() {
		BigDecimal rendimentoValor = this.saldo.getValor().multiply(BigDecimal.valueOf(RENDIMENTO_MENSAL));
		Dinheiro rendimento = new Dinheiro(rendimentoValor);
		this.saldo = this.saldo.somar(rendimento);
		this.movimentacoes.add(new Movimentacao(java.time.LocalDateTime.now(), rendimento, TipoMovimentacao.RENDIMENTO));
	}

	@Override
	protected void aplicarRegraDeTaxa() {
		aplicarTaxaMensal();
	}
}
