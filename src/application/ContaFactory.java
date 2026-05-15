package application;

import model.Cliente;
import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;
import model.valueobjects.Dinheiro;

public class ContaFactory {
	private static ContaFactory instance;

	private ContaFactory() {
	}

	public static ContaFactory getInstance() {
		if (instance == null) {
			instance = new ContaFactory();
		}
		return instance;
	}

	public Conta criarContaCorrente(Cliente cliente, Dinheiro saldo) {
		return new ContaCorrente(cliente, saldo);
	}

	public Conta criarContaPoupanca(Cliente cliente, Dinheiro saldo) {
		return new ContaPoupanca(cliente, saldo);
	}
}
