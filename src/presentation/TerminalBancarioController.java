package presentation;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import application.AutorizacaoService;
import application.ContaFactory;
import application.ContaService;
import model.Cliente;
import model.Conta;
import model.valueobjects.ContaAcesso;
import model.valueobjects.Dinheiro;
import model.valueobjects.Movimentacao;

public class TerminalBancarioController {

	private static TerminalBancarioController instance;

	private ContaService contaService;
	private AutorizacaoService autorizacaoService;
	private Scanner scanner;

	private TerminalBancarioController() {
		this.scanner = new Scanner(System.in);
	}

	public static TerminalBancarioController getInstance() {
		if (instance == null) {
			instance = new TerminalBancarioController();
		}
		return instance;
	}

	public void iniciar() {
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
		this.contaService = new ContaService(conta);
		this.autorizacaoService = new AutorizacaoService(conta);

		// Iniciar o fluxo
		limparTela();
		System.out.println("--- Sistema ATM Iniciado ---");
		System.out.println("Bem-vindo, " + cliente.obterPrimeiroNome() + "!");
		exibirMenuPrincipal();

		scanner.close();
	}

	private void limparTela() {
		for (Integer i = 0; i < 50; i++) {
			System.out.println();
		}
	}

	public void exibirMenuPrincipal() {
		Integer opcao = 0;
		do {
			System.out.println("\n===============MENU===============");
			System.out.println("|  1 -> Consultar Saldo            |");
			System.out.println("|  2 -> Fazer Depósito             |");
			System.out.println("|  3 -> Fazer Saque                |");
			System.out.println("|  4 -> Histórico de Movimentações |");
			System.out.println("|  5 -> Sair                       |");
			System.out.println("====================================");
			System.out.print("-> ");

			try {
				opcao = Integer.valueOf(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Opção inválida! Selecione um valor presente no menu.");
				continue;
			}

			switch (opcao) {
				case 1:
					exibirSaldo();
					break;
				case 2:
					realizarDeposito();
					break;
				case 3:
					realizarSaque();
					break;
				case 4:
					exibirMovimentacoes();
					break;
				case 5:
					System.out.println("\nO FIAP Bank agradece sua preferência! Fechando sessão...");
					break;
				default:
					System.out.println("Valor inválido! Selecione um valor presente no menu.");
			}
		} while (!opcao.equals(5));
	}

	public void exibirSaldo() {
		if (autenticar()) {
			Dinheiro saldo = contaService.obterSaldo();
			System.out.println(String.format("Saldo atual: R$ %.02f", saldo.getValor()));
		}
	}

	public void exibirMovimentacoes() {
		if (autenticar()) {
			System.out.println("\n--- Histórico de Movimentações ---");

			List<Movimentacao> movimentacoes = contaService.obterMovimentacoes();

			if (movimentacoes.isEmpty()) {
				System.out.println("Nenhuma movimentação encontrada.");
				return;
			}

			DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

			for (Movimentacao mov : movimentacoes) {
				String data = mov.getDataHora().format(formatador);
				String tipo = mov.getTipo().name();
				String valor = mov.getValor().getValor().toString();

				System.out.println(data + " | " + tipo + " | R$ " + valor);
			}
		}
	}

	private Dinheiro inputSanitizadoDinheiro() {
		String input = scanner.nextLine()
				.replaceAll("[^0-9\\.\\-]", "")
				.replaceAll("\\.", ",")
				.replaceFirst("\\,", ".");

		try {
			String cleanInput = input.isEmpty() ? "0" : input;
			Double d = Double.valueOf(String.format("%.02f", Double.valueOf(cleanInput)).replaceFirst("\\,", "."));
			return new Dinheiro(new BigDecimal(d.toString()));
		} catch (Exception e) {
			return new Dinheiro(BigDecimal.ZERO);
		}
	}

	public void realizarSaque() {
		limparTela();
		System.out.println("=====================================================================");
		System.out.println("|                    Insira o valor do saque                        |");
		System.out.println("=====================================================================");
		if (autenticar()) {
			System.out.print("-> ");
			Dinheiro valor = inputSanitizadoDinheiro();
			try {
				contaService.realizarSaque(valor);
				System.out.println(String.format("Valor de R$ %.02f sacado!", valor.getValor()));
			} catch (Exception e) {
				System.out.println("Erro ao sacar: " + e.getMessage());
			}
		}
	}

	public void realizarDeposito() {
		limparTela();
		System.out.println("=====================================================================");
		System.out.println("|                    Insira o valor do depósito                     |");
		System.out.println("=====================================================================");
		if (autenticar()) {
			System.out.print("-> ");
			Dinheiro valor = inputSanitizadoDinheiro();
			try {
				contaService.realizarDeposito(valor);
				System.out.println(String.format("Valor de R$ %.02f depositado!", valor.getValor()));
			} catch (Exception e) {
				System.out.println("Erro ao depositar: " + e.getMessage());
			}
		}
	}

	private Boolean autenticar() {
		System.out.print("Digite sua senha para autorizar a operação: ");
		String senha = scanner.nextLine();
		Boolean autorizado = autorizacaoService.autorizar(senha);
		if (!autorizado) {
			System.out.println("Operação não autorizada: Senha incorreta ou conta bloqueada.");
		}
		return autorizado;
	}
}
