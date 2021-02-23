package pso.problems;

import hnn.HNNSimulator;
import hnn.models.Config;
import hnn.models.Operators;

public class HNN extends Problem {

	public HNN(int _dimension, double[] max, double[] min) {
		super("HNN", 4, max, min);
	}

	@Override
	public double calculateFitness(double[] x) {
		System.out.println("\t\t" + x[0] + "," + x[1] + "," + x[2] + "," + x[3]);
		int[] u = new int[] { 950, 2500, 2500, 475, 2500 };
		Config config = new Config(u, x[0], x[1], x[2], x[3], 0, 0, 0);
		Operators operators = new Operators(config);
		HNNSimulator simulator = new HNNSimulator();
		return simulator.simulateRoutingNetworkWith64NodesPSO(operators);
	}

}
