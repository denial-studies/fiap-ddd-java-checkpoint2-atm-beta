package application;

import presentation.TerminalBancarioController;

public class Main {
	public static void main(String[] args) {
		TerminalBancarioController terminal = TerminalBancarioController.getInstance();
		terminal.iniciar();
	}
}
