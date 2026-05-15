package application;

import java.util.List;
import model.Conta;
import model.valueobjects.Dinheiro;
import model.valueobjects.Movimentacao;

public class ContaService {
	private Conta conta;

	public ContaService(Conta conta) {
		this.conta = conta;
	}

	public void realizarDeposito(Dinheiro valor) {
		this.conta.realizarDeposito(valor);
	}

	public void realizarSaque(Dinheiro valor) {
		this.conta.realizarSaque(valor);
	}

	public Dinheiro obterSaldo() {
		return this.conta.getSaldo();
	}

	public List<Movimentacao> obterMovimentacoes() {
		return this.conta.getMovimentacoes();
	}
}
