package main;

import java.math.BigDecimal;
import java.util.Scanner;

import application.AutorizacaoService;
import application.ContaFactory;
import application.ContaService;
import model.Cliente;
import model.Conta;
import model.valueobjects.ContaAcesso;
import model.valueobjects.Dinheiro;
import presentation.TerminalBancarioController;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		limparTela();
		System.out.println("==================================");
		System.out.println("|   Insira o seu nome completo   |");
		System.out.println("==================================");
		System.out.print("-> ");
		String nome = scanner.nextLine().trim();
		
		String senha = "";
		limparTela();
		System.out.println("===============================SENHA================================");
		System.out.println("|       Insira uma senha válida, seguindo as regras abaixo:        |");
		System.out.println("| * 8 caracteres minimo                                            |");
		System.out.println("| * Ao menos um número                                             |");
		System.out.println("| * Ao menos uma letra maiúscula                                   |");
		System.out.println("| * Ao menos um caractere especial da lista => !@#$%^&()-_+=?>< <= |");
		System.out.println("====================================================================");
		while (true) {
			System.out.print("-> ");
			String senha_inicial = scanner.nextLine();
			if (senha_inicial.matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_+=?><]).{8,}$")) {
				System.out.println("\nSenha registrada com sucesso!");
				senha = senha_inicial;
				break;
			} else {
				System.out.println("\nSenha não atendeu a todos os critérios... Tente novamente.");
			}
		}
		
		// Setup inicial do cliente e acesso
		Cliente cliente = new Cliente(nome);
		ContaAcesso acesso = new ContaAcesso(senha);
		Dinheiro saldoInicial = new Dinheiro(new BigDecimal("0.00")); // Inicia zerado
		
		limparTela();
		System.out.println("==================================");
		System.out.println("| Qual tipo de conta quer abrir? |");
		System.out.println("| 1 -> Conta Corrente            |");
		System.out.println("| 2 -> Conta Poupança            |");
		System.out.println("==================================");
		
		Conta conta = null;
		while (conta == null) {
			System.out.print("-> ");
			String tipo = scanner.nextLine();
			if (tipo.equals("1")) {
				conta = ContaFactory.getInstance().criarContaCorrente(cliente, saldoInicial);
				System.out.println("\nConta Corrente criada com sucesso!");
			} else if (tipo.equals("2")) {
				conta = ContaFactory.getInstance().criarContaPoupanca(cliente, saldoInicial);
				System.out.println("\nConta Poupança criada com sucesso!");
			} else {
				System.out.println("\nOpção inválida. Digite 1 ou 2.");
			}
		}
		
		// Configurando os dados de acesso da conta
		conta.setContaAcesso(acesso);
		
		// Inicializar os Services
		ContaService contaService = new ContaService(conta);
		AutorizacaoService autorizacaoService = new AutorizacaoService(conta);
		
		// Configurando a camada de apresentação
		TerminalBancarioController terminal = new TerminalBancarioController(contaService, autorizacaoService);
		
		// Iniciar o fluxo
		limparTela();
		System.out.println("--- Sistema ATM Iniciado ---");
		System.out.println("Bem-vindo, " + cliente.obterPrimeiroNome() + "!");
		terminal.exibirMenuPrincipal();
		
		scanner.close();
	}
	
	private static void limparTela() {
		for (Integer i = 0; i < 50; i++) {
			System.out.println();
		}
	}
}
