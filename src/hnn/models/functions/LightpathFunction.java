package hnn.models.functions;

import hnn.models.Lightpath;
import hnn.models.Node;
import hnn.models.Operators;
import hnn.models.Pair;

public abstract class LightpathFunction {

	protected Node node;

	public LightpathFunction(Node rede) {
		this.node = rede;
	}

	public abstract Lightpath genereteBestLightpath(Pair pair);

	public abstract void updateRede(Node rede, Operators operators);

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

}
