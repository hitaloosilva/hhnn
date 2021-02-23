package hnn.models.functions;

import hnn.models.Config;
import hnn.models.Neuron;

public abstract  class EnergyFunction extends Function<Neuron>{

	public EnergyFunction(Config config) {
		super(config);
	}

	abstract public double calculate(Neuron neuron);

	
}
