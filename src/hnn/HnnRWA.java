package hnn;

import hnn.models.HNN;
import hnn.models.Lightpath;
import hnn.models.Node;
import hnn.models.Operators;
import hnn.models.Pair;
import hnn.models.functions.LightpathFunction;

public class HnnRWA extends LightpathFunction {

	private HNN hnn;

	public HnnRWA(Node node, Operators operators) {
		super(node);
		this.updateRede(node, operators);
	}

	public Lightpath genereteBestLightpath(Pair pair) {

		// Execution execution = null;
		// execution = hnn.calculateRouteHNN(pair);

		return null;
	}

	public Execution genereteBestLightpathTest(Pair pair) {
		Execution execution = null;
		execution = hnn.establishRoute(pair);
		execution.setPair(pair);
		execution.setInterfaces(HNN.getPhysicalIntefaces(pair,
				hnn.getActivedNeurons(pair)));
		return execution;
	}

	@Override
	public void updateRede(Node node, Operators operators) {
		this.node = node;
		NetworkToHNNParser parser = new NetworkToHNNParser();
		this.hnn = parser.generateHNN(node, operators);
	}

}
