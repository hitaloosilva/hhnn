package hnn;

import hnn.models.HNN;
import hnn.models.Interface;
import hnn.models.Neuron;
import hnn.models.Node;
import hnn.models.Operators;
import hnn.models.Pair;
import hnn.models.SynapticConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NetworkToHNNParser {

	public HNN generateHHNN(Node node, Operators operators, Map<Node, HNN> hnns) {
		HNN hnn = null;
		if (node.getNodes() != null && node.getNodes().size() > 0) {
			List<Node> nodes = node.getNodes();
			hnn = generateHNN(node, operators);;
			for (Node internalNode : nodes) {
				generateHHNN(internalNode, operators, hnns);
			}
			hnns.put(node, hnn);
		}

		return hnn;
	}

	public HNN generateHNN(Node node, Operators operators) {
		HNN hnn = new HNN(node);
		List<Neuron> neurons = createNeurons(node, hnn, operators);
		createSynapticsConnections(neurons);
		for (Neuron neuron : neurons) {
			neuron.inicializeWeight();
		}
		
		hnn.setNeurons(neurons);
		return hnn;
	}
	
	private List<Neuron> createNeurons(Node area, HNN layer,
			Operators operators) {
		List<Node> nodes = area.getNodes();
		int nodesLenth = nodes.size();
		List<Neuron> neurons = new ArrayList<Neuron>(nodesLenth
				* (nodesLenth - 1));
		Node node;
		Node destinationNode;
		Interface inf;
		Neuron neuron;
		for (int x = 0; x < nodesLenth; x++) {
			node = nodes.get(x);
			for (int i = 0; i < nodesLenth; i++) {
				if (x != i) {
					destinationNode = nodes.get(i);
					inf = node.getInterfaceByDestination(destinationNode);
					if (inf == null) {
						inf = Node.createInterfaceEmpty(new Pair(node,
								destinationNode));
					}
					neuron = new Neuron(layer, inf, operators);
					neurons.add(neuron);
				}
			}
		}
		return neurons;
	}
	
	private void createSynapticsConnections(List<Neuron> neuronios) {
		Neuron origenNeuron;
		Neuron destinationNeuron;
		SynapticConnection connection = null;
		for (int y = 0; y < neuronios.size(); y++) {
			origenNeuron = neuronios.get(y);
			for (int j = 0; j < neuronios.size(); j++) {
				destinationNeuron = neuronios.get(j);
				connection = new SynapticConnection(destinationNeuron,
						origenNeuron);
				origenNeuron.addInputConnection(connection);
			}
		}
	}

}
