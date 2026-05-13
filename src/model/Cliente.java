package model;

public class Cliente extends BaseEntity {
	private String nomeCompleto;
	
	Cliente(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto.trim();
	}
	
	public String obterPrimeiroNome() {
		int primeiroEspaco = nomeCompleto.indexOf(" ");
		return nomeCompleto.substring(0, primeiroEspaco);
	}
	
	public String getNomeCompleto() {
		return nomeCompleto;
	}
}
