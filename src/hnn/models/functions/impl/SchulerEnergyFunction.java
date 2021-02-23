package hnn.models.functions.impl;

import hnn.models.Config;
import hnn.models.Neuron;
import hnn.models.functions.EnergyFunction;

public class SchulerEnergyFunction extends EnergyFunction {

	public SchulerEnergyFunction(Config config) {
		super(config);
	}

	@Override
	public double calculate(Neuron neuron) {
		double sum = neuron.calculateSumWeghtedInputs();
//		System.out.println(neuron + ": " + sum);
		Config config = getConfig();
		return neuron.getNet() - config.getA() * neuron.getOldNet()
				+ config.getB() * sum + config.getC() * neuron.getBias();
	}

}
