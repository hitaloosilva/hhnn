package hnn.models.functions.impl;

import hnn.models.functions.ActivationFunction;

public class Sigmoid extends ActivationFunction {

	private double slope = 1d;

	public Sigmoid(double slope) {
		this.slope = slope;
	}

	public double getSlope() {
		return this.slope;
	}

	public void setSlope(double slope) {
		this.slope = slope;
	}

	@Override
	public double calculate(Double net) {
		return 1.0 / (1 + Math.exp(-1 * slope * net));
	}

	@Override
	public double calculateDerivative(Double net) {
		double output = calculate(net);
		double derivative = this.slope * output * (1d - output) + 0.1;
		return derivative;
	}

}
