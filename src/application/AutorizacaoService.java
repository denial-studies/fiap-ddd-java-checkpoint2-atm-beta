package application;

import model.Conta;

public class AutorizacaoService {
	private Conta conta;

	public AutorizacaoService(Conta conta) {
		this.conta = conta;
	}

	public Boolean autorizar(String senha) {
		return this.conta.getContaAcesso().autorizar(senha);
	}
}
