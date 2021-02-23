package hnn.models.functions;

import hnn.models.Config;
import hnn.models.Neuron;
import hnn.models.Node;

public abstract class SynapticFunction {

	protected Config config;

	public SynapticFunction(Config config) {
		this.config = config;
	}

	public abstract double calculate(Neuron origenNeuron, Neuron destinationNeuron);
}
