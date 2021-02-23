package hnn;

import hnn.models.Config;
import hnn.models.Operators;

public class Main {

	public static void main(String[] args) {
		// int[] u = new int[] { 2000, 2500, 2500, 475, 2500};
		// int[] u = new int[] { 1550, 2500, 2500, 195, 2500 }; //Rodou, mas
		// achou
		// um caminho sub-otimo bem próximo.
		int[] u = new int[] { 950, 2500, 2500, 195, 2500 };
		double A = 0.001, B = 0.001, C = 0.001;
		// int[] u = new int[] { 300, 1848, 1729, 195, 1848 }; // Valores Robson
		// double A = 0.00005068, B = 0.0009006, C = 0.001152; //valores Robson
		// double A = 0.0001, B = 0.0165946, C = 0.0158451; // valores shuller
		double slope = 1;
		Config config = new Config(u, A, B, C, slope, 0, 0, 0);
		Operators operators = new Operators(config);
		HNNSimulator simulator = new HNNSimulator();
		// simulator.simulateRoutingNetworkWith16Nodes(operators);
		// simulator.simulateMultiLayerRoutingInNetwork(operators);
		//
		// simulator.simulateRoutingNetworkWith64NodesCost(operators);
		// simulator.simulateMultiLayerRoutingInNetworkWith64Nodes(operators);
		// simulator.simulateMultiLayerRoutingInNetworkWith64NodesBy16(operators);
		// simulator.simulateMultiLayerRoutingInNetworkWith64NodesBy8(operators);

		// simulator.simulateMultiLayerRoutingInNetworkWith64NodesCost(operators);
		// simulator.simulateMultiLayerRoutingInNetworkWith64NodesBy16Cost(operators);
		// simulator.simulateMultiLayerRoutingInNetworkWith64NodesBy8Cost(operators);

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 4; k++) {
					if (j != k)
						System.out.print(i*4 + j + "-" + (i*4 + k) + ";");
				}
			}
			System.out.println();
		}
	}

}
