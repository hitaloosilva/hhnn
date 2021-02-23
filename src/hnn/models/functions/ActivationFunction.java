package hnn.models.functions;


public abstract class ActivationFunction {

	protected double output;
	
	public abstract double calculate(Double net);

	public abstract double calculateDerivative(Double net);

}
