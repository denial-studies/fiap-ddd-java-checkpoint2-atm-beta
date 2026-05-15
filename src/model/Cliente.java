package model;

public class Cliente extends BaseEntity {
	private String nomeCompleto;
	
	public Cliente(String nomeCompleto) {
		if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) {
			throw new IllegalArgumentException("O nome completo não pode estar vazio.");
		}
		this.nomeCompleto = nomeCompleto.trim();
	}
	
	public String obterPrimeiroNome() {
		Integer primeiroEspaco = nomeCompleto.indexOf(" ");
		if (primeiroEspaco == -1) {
			return nomeCompleto;
		}
		return nomeCompleto.substring(0, primeiroEspaco);
	}
	
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) return false;
		Cliente cliente = (Cliente) obj;
		return nomeCompleto != null ? nomeCompleto.equals(cliente.nomeCompleto) : cliente.nomeCompleto == null;
	}
}
