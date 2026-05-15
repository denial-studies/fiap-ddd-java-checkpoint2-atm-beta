package model.valueobjects;

public class ContaAcesso {
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
		if (bloqueado) {
			return false;
		}
		if (this.senha.equals(senha)) {
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
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		ContaAcesso that = (ContaAcesso) obj;
		return senha != null ? senha.equals(that.senha) : that.senha == null;
	}
}
