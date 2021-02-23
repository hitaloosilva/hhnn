package hnn.models;

import hnn.models.functions.ActivationFunction;
import hnn.models.functions.BiasFunction;
import hnn.models.functions.EnergyFunction;
import hnn.models.functions.SynapticFunction;
import hnn.models.functions.impl.AliKamounSynapticFunction;
import hnn.models.functions.impl.PadraoBiasFunction;
import hnn.models.functions.impl.SchulerEnergyFunction;
import hnn.models.functions.impl.Sigmoid;

public class Operators {

	// atributos
	private Config config;
	private SynapticFunction synapticFunction;
	private ActivationFunction activationFunction;
	private BiasFunction biasFunction;
	private EnergyFunction energyFunction;

	// construtor
	public Operators(Config _config) {
		this.setConfig(_config);
		this.synapticFunction = new AliKamounSynapticFunction(_config);
		this.activationFunction = new Sigmoid(_config.getSlope());
		this.biasFunction = new PadraoBiasFunction(_config);
		this.energyFunction = new SchulerEnergyFunction(_config);
	}

	public double calculateActivation(double net) {
		return activationFunction.calculate(net);
	}

	public double calculateActivationDerivative(double net) {
		return activationFunction.calculateDerivative(net);
	}

	public double calculateBias(Interface inter, Pair pair) {
		return this.biasFunction.calculate(inter, pair);
	}

	public double calculateSynapse(Neuron origenNeuron, Neuron destinationNeuron) {
		return synapticFunction.calculate(origenNeuron, destinationNeuron);
	}

	public double calcualteEnergy(Neuron neuron) {
		return this.energyFunction.calculate(neuron);
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

}
