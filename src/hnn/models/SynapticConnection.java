package hnn.models;

public class SynapticConnection {

	private Neuron origin;
	private Neuron destination;
	private double weight;

	public SynapticConnection(Neuron entrada, Neuron saida) {
		this.origin = entrada;
		this.destination = saida;
	}

	public Neuron getOrigin() {
		return origin;
	}

	public void setOrigin(Neuron origin) {
		this.origin = origin;
	}

	public Neuron getDestination() {
		return destination;
	}

	public void setDestination(Neuron destination) {
		this.destination = destination;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getInputWeighted() {
		return this.origin.getOutput() * weight;
	}

	public void generateWeight(Operators operators) {
		this.setWeight(operators.calculateSynapse(origin, destination));
	}
}
