package mlp.modular;

import java.io.Serializable;
import java.util.Arrays;

public class Camada implements Serializable {

	private static final long serialVersionUID = 3L;

	protected Neuronio[] neurons;

	public Camada() {
		this.neurons = new Neuronio[0];
	}

	public Camada(int neuronsCount) {
		this();
		for (int i = 0; i < neuronsCount; i++) {
			Neuronio neuron = new Neuronio();
			this.addNeuron(neuron);
		}
	}

	public final Neuronio[] getNeurons() {
		return this.neurons;
	}

	public final void addNeuron(Neuronio neuron) {
		this.neurons = Arrays.copyOf(neurons, neurons.length + 1);
		neuron.setCamada(this);
		this.neurons[neurons.length - 1] = neuron;
	}

	public void setNeuron(int index, Neuronio neuron) {
		neuron.setCamada(this);
		this.neurons[index] = neuron;
	}

	public int getNeuronsCount() {
		return neurons.length;
	}

	public void calculate() {
		for (Neuronio neuron : this.neurons) {
			neuron.calculate();
		}
	}

	public void inicializarPesos() {
		for (Neuronio neuron : this.neurons) {
			neuron.inicializePesos();
		}
	}

}
