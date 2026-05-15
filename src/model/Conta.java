package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.valueobjects.ContaAcesso;
import model.valueobjects.Dinheiro;
import model.valueobjects.Movimentacao;

public abstract class Conta extends BaseEntity {
	protected Cliente cliente;
	protected Dinheiro saldo;
	protected Double taxa;
	protected StatusConta status;
	protected LocalDate dataAbertura;
	protected ContaAcesso contaAcesso;
	protected List<Movimentacao> movimentacoes;

	public Conta(Cliente cliente, ContaAcesso contaAcesso, Dinheiro saldo, Double taxa) {
		super();
		this.cliente = cliente;
		this.contaAcesso = contaAcesso;
		this.saldo = saldo;
		this.taxa = taxa;

		this.status = StatusConta.ATIVA;
		this.dataAbertura = LocalDate.now();
		this.movimentacoes = new ArrayList<>();
	}

	public void realizarSaque(Dinheiro valor) {
		// Validar e debitar através do método privado (Template Method base)
		sacar(valor);
		// Aplicar a taxa específica (Template Method gancho)
		aplicarRegraDeTaxa();
	}

	public void realizarDeposito(Dinheiro valor) {
		// Validar e depositar através do método privado
		depositar(valor);
	}

	public ContaAcesso getContaAcesso() {
		return contaAcesso;
	}

	public Dinheiro getSaldo() {
		return saldo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public LocalDate getDataAbertura() {
		return dataAbertura;
	}

	public StatusConta getStatus() {
		return status;
	}

	public List<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}

	public void setContaAcesso(ContaAcesso contaAcesso) {
		this.contaAcesso = contaAcesso;
	}

	protected abstract void aplicarRegraDeTaxa();

	private void depositar(Dinheiro valor) {
		if (valor.getValor().signum() <= 0) {
			throw new IllegalArgumentException("O valor do depósito deve ser maior que zero.");
		}

		this.saldo = this.saldo.somar(valor);
		registrarMovimentacao(TipoMovimentacao.DEPOSITO, valor);
	}

	private void sacar(Dinheiro valor) {
		if (valor.getValor().signum() <= 0) {
			throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
		}
		if (this.saldo.menorQue(valor)) {
			throw new IllegalStateException("Saldo insuficiente para realizar o saque.");
		}

		this.saldo = this.saldo.subtrair(valor);
		registrarMovimentacao(TipoMovimentacao.SAQUE, valor);
	}

	private void registrarMovimentacao(TipoMovimentacao tipo, Dinheiro valor) {
		this.movimentacoes.add(new Movimentacao(LocalDateTime.now(), valor, tipo));
	}
}
