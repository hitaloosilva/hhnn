package pso.models;

import pso.problems.HNN;
import pso.problems.Problem;
import pso.util.Const;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int dimension = 4;
		double minValue[] = new double[] { 0.1, 0.1, 0.1, 1 };
		double maxValue[] = new double[] { 0.0001, 0.0001, 0.0001, 10 };
		Problem prob = new HNN(dimension, maxValue, minValue);
		Config config = new Config(Const.T_LOCAL); // T_GLOBAL ou T_LOCAL

		PSO pso = new PSO(prob, config);

		pso.run();

	}

}
