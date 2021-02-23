package mlp.modular;

public class Conexao {

	private Neuronio entrada;
	private Neuronio saida;
	private double w;

	public Neuronio getEntrada() {
		return entrada;
	}

	public void setEntrada(Neuronio entrada) {
		this.entrada = entrada;
	}

	public Neuronio getSaida() {
		return saida;
	}

	public void setSaida(Neuronio saida) {
		this.saida = saida;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public double getInput() {
		return this.entrada.getOutput();
	}

	public double getInputComPeso() {
		return this.entrada.getOutput() * this.w;
	}

	public void gerarPeso() {
		this.setW(Math.random());
	}
}
