package mlp.modular;

import java.io.Serializable;

abstract public class FuncaoTransferencia implements Serializable {

	private static final long serialVersionUID = 1L;

	protected double output; 

	abstract public double getOutput(double net);

	public double getDerivative(double net) {
		return 1d;
	}

}
