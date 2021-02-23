package mlp.modular;

public class Sigmoide extends FuncaoTransferencia {

	private static final long serialVersionUID = 1L;
	
	private double slope = 1d;
	
	public Sigmoide() {
	}

	public Sigmoide(double slope) {
		this.slope = slope;
	}

	public double getSlope() {
		return this.slope;
	}

	public void setSlope(double slope) {
		this.slope = slope;
	}

	public double calculateDerivative(Double net) {
		double output = getOutput(net);
		double derivative = this.slope * output * (1d - output) + 0.1;
		return derivative;
	}

	public double calculate(Double net) {
		if (net > 100) {
			return 1.0;
		} else if (net < -100) {
			return 0.0;
		}

		double den = 1d + Math.exp(-this.slope * net);
		this.output = (1d / den);

		return this.output;
	}

	@Override
	public double getOutput(double net) {
		// TODO Auto-generated method stub
		return 0;
	}

}
