package model.valueobjects;

import model.exceptions.AcessoBloqueadoException;
import model.interfaces.Autorizavel;

public class ContaAcesso implements Autorizavel {
	public static final Integer MAXIMO_TENTATIVAS = 3;
	private String senha;
	private Integer tentativas;
	private Boolean bloqueado;

	public ContaAcesso(String senha) {
		this.senha = senha;
		this.tentativas = 0;
		this.bloqueado = false;
	}

	public Boolean validarSenha(String senha) {
		return this.senha.equals(senha);
	}

	// Método do Checkpoint 3
	public Boolean autorizar(String senha) {
		if (isBloqueado()) {
			throw new AcessoBloqueadoException(
					"A sua conta foi bloqueada por 3 tentativas erradas na senha. Reinicie a aplicação...");
		}

		if (validarSenha(senha)) {
			resetarTentativas();
			return true;
		} else {
			tentativas++;
			if (tentativas >= MAXIMO_TENTATIVAS) {
				bloqueado = true;
			}
			return false;
		}
	}

	public Boolean isBloqueado() {
		return bloqueado;
	}

	public void resetarTentativas() {
		this.tentativas = 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		ContaAcesso that = (ContaAcesso) obj;
		return senha != null ? senha.equals(that.senha) : that.senha == null;
	}
}
