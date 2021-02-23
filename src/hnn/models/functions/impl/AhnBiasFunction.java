package hnn.models.functions.impl;

import hnn.models.Config;
import hnn.models.Interface;
import hnn.models.Pair;
import hnn.models.functions.BiasFunction;
import hnn.util.Util;

public class AhnBiasFunction extends BiasFunction {

	public AhnBiasFunction(Config config) {
		super(config);
	}

	public double calculate(Interface inter, Pair pair) {
		int[] u = config.getU();
		int p = inter.isEnable() ? 0 : 1;
		int gamaX = Util.gama(inter.getPair().getFrom(), pair);
		int gamaI = Util.gama(inter.getPair().getTo(), pair);

		return -0.5 * u[0] * inter.getCost() - 0.5 * u[1] * p + u[2] * gamaX
				- u[2] * gamaI - 0.5 * u[3];
	}
}
