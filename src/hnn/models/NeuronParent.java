package hnn.models;

import java.util.List;

public class NeuronParent {

	private Node parent;

	private List<Neuron> neurons;

	public NeuronParent(Node parent, List<Neuron> neurons) {
		this.parent = parent;
		this.neurons = neurons;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public List<Neuron> getNeurons() {
		return neurons;
	}

	public void setNeurons(List<Neuron> neurons) {
		this.neurons = neurons;
	}

}
