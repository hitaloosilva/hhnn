package hnn.models.functions;

import hnn.models.Config;
import hnn.models.Interface;
import hnn.models.Pair;

public abstract class BiasFunction {

	protected Config config;

	public BiasFunction(Config config) {
		this.config = config;
	}

	abstract public double calculate(Interface inter, Pair pair);
}
