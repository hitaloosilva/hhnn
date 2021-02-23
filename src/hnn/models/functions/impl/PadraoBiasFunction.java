package hnn.models.functions.impl;

import hnn.models.Config;
import hnn.models.Interface;
import hnn.models.Pair;
import hnn.models.functions.BiasFunction;
import hnn.util.Util;

public class PadraoBiasFunction extends BiasFunction {

	public PadraoBiasFunction(Config config) {
		super(config);
	}

	public double calculate(Interface inter, Pair pair) {
		int[] u = config.getU();
		int p = inter.isEnable() ? 0 : 1;
		int kroneckerDeltaXD = Util.kroneckerDelt(inter.getPair().getFrom(),
				pair.getTo());
		int kroneckerDeltaIS = Util.kroneckerDelt(inter.getPair().getTo(),
				pair.getFrom());
		// int kroneckerDeltaAB = inter.toString().equals("x=3,i=4") ? 1 : 0;
		// // Util.KroneckerDelt(inter.getTo(),inter.getFrom());
		int kroneckerDeltaXDIS = kroneckerDeltaXD * kroneckerDeltaIS;
		return -0.5 * u[0] * inter.getCost() * (1 - kroneckerDeltaXDIS) - 0.5
				* u[1] * p * (1 - kroneckerDeltaXDIS) - 0.5 * u[3] + 0.5 * u[4]
				* kroneckerDeltaXDIS;
	}
}
