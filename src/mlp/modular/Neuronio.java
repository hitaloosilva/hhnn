package mlp.modular;

import java.util.Arrays;


public class Neuronio {

	private Camada camada;

	private Conexao[] inputs;

	private Conexao[] outputs;

	protected double net;

	protected double output;

	private FuncaoTransferencia funcaoTransferencia;

	public Neuronio() {
		this.funcaoTransferencia = new Sigmoide();
	}

	public void calculate() {
		net = Math.random();
		for (int i = 0; i < inputs.length; i++) {
			net += this.inputs[i].getInputComPeso();
		}
		this.output = this.funcaoTransferencia.getOutput(this.net);
	}

	public Conexao[] getInputs() {
		return inputs;
	}

	public void setInputs(Conexao[] inputs) {
		this.inputs = inputs;
	}

	public Conexao[] getOutputs() {
		return outputs;
	}

	public void setOutputs(Conexao[] outputs) {
		this.outputs = outputs;
	}

	public double getNet() {
		return net;
	}

	public void setNet(double net) {
		this.net = net;
	}

	public Camada getCamada() {
		return camada;
	}

	public void setCamada(Camada camada) {
		this.camada = camada;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}

	public void inicializePesos() {
		for (int i = 0; i < inputs.length; i++) {
			inputs[i].gerarPeso();
		}
	}

	public void addInputConnection(Conexao conexao) {
		this.inputs = Arrays.copyOf(inputs, inputs.length + 1);
		this.inputs[inputs.length - 1] = conexao;
		Neuronio fromNeuron = conexao.getEntrada();
		fromNeuron.addOutputConnection(conexao);
	}

	protected void addOutputConnection(Conexao connection) {
		this.outputs = Arrays.copyOf(outputs, outputs.length + 1);
		this.outputs[outputs.length - 1] = connection;
	}

}
