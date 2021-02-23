package hnn;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hnn.models.Interface;
import hnn.models.HNN;
import hnn.models.Lightpath;
import hnn.models.Node;
import hnn.models.Operators;
import hnn.models.Pair;
import hnn.models.functions.LightpathFunction;

public class HHnnRWA extends LightpathFunction {

	private Map<Node, HNN> hnns;

	public HHnnRWA(Node _rede, Operators operators) {
		super(_rede);
		this.hnns = new HashMap<Node, HNN>();
		NetworkToHNNParser parser = new NetworkToHNNParser();
		parser.generateHHNN(node, operators, this.hnns);
	}

	public Lightpath genereteBestLightpath(Pair pair) {
		if (pair.getFrom().getLevel() == pair.getTo().getLevel()) {
			calculateRoute(pair);
		} else {
			System.out.println("Neuronios são de níveis diferentes!");
		}
		return null;
	}

	// public Execution generateBestLightpathTest(Config config, Pair pair) {
	// Execution execution = null;
	// if (pair.getFrom().getLevel() == pair.getTo().getLevel()) {
	// Pair currentPair = getMutualParent(pair);
	// execution = calculateRoute(pair, currentPair);
	// } else {
	// System.out.println("Neuronios são de níveis diferentes!");
	// }
	// return execution;
	// }

	public Execution generateBestLightpathTest(Pair pair) {
		Execution execution = null;
		if (pair.getFrom().getLevel() == pair.getTo().getLevel()) {
			execution = calculateRoute(pair);
		} else {
			System.out.println("Neuronios são de níveis diferentes!");
		}
		return execution;
	}

	// private Execution calculateRoute(Pair pair, Pair currentPair) {
	// Execution execution = null;
	// Pair newCurrentPair = null;
	// HNN hnn = hnns.get(currentPair.getFrom().getParent());
	// if (pair.getFrom().getLevel() > hnn.getNode().getLevel()) {
	// execution = hnn.calculateRouteHNN(currentPair);
	// execution.createPath();
	// if (pair.getFrom().getLevel() > currentPair.getFrom().getLevel()) {
	// newCurrentPair = hnn.getMutualParentByLayer(pair);
	// Map<Node, Pair> map = generatePairsByNeuronsNew(newCurrentPair,
	// currentPair, hnn.getNeurons());
	// Iterator<Entry<Node, Pair>> ite = map.entrySet().iterator();
	// Entry<Node, Pair> entry = null;
	// while (ite.hasNext()) {
	// entry = ite.next();
	// execution.getExecutions().add(
	// calculateRoute(pair, entry.getValue()));
	// }
	//
	// }
	// }
	// return execution;
	// }

	private Execution calculateRoute(Pair pair) {
		Execution execution = null;
		Pair currentPair = getMutualParent(pair);
		HNN hnn = hnns.get(currentPair.getFrom().getParent());
		if (pair.getFrom().getLevel() > hnn.getNode().getLevel()) {
			execution = hnn.establishRoute(currentPair);
			execution.setPair(pair);
			execution.setInterfaces(HNN.getPhysicalIntefaces(pair,
					hnn.getActivedNeurons(currentPair)));
			// System.out.println("----------------------------------------");
			// execution.createPath();
			if (pair.getFrom().getLevel() > currentPair.getFrom().getLevel()) {

				Map<Node, Pair> map = generatePairsByNeurons(pair, currentPair,
						execution.getInterfaces());
				Iterator<Entry<Node, Pair>> ite = map.entrySet().iterator();
				Entry<Node, Pair> entry = null;
				while (ite.hasNext()) {
					entry = ite.next();
					execution.getExecutions().add(
							calculateRoute(entry.getValue()));
				}

			}
		}
		return execution;
	}

	private Pair getMutualParent(Pair pair) {
		Pair currentPair = pair.clone();
		while (!currentPair.getFrom().getParent()
				.equals(currentPair.getTo().getParent())) {
			currentPair.setFrom(currentPair.getFrom().getParent());
			currentPair.setTo(currentPair.getTo().getParent());
		}
		return currentPair;
	}

	private Map<Node, Pair> generatePairsByNeurons(Pair pair, Pair currentPair,
			List<Interface> interfaces) {
		Map<Node, Pair> map = new HashMap<Node, Pair>();
		Node nodeX;
		Node nodeI;
		Pair pairFrom;
		Pair pairTo;
		int level = 0;
		Node parent = null;

		level = currentPair.getFrom().getLevel();

		parent = pair.getFrom().getParentByLevel(level);
		pairFrom = getPairInMap(map, parent);
		pairFrom.setFrom(pair.getFrom());

		parent = pair.getTo().getParentByLevel(level);
		pairTo = getPairInMap(map, parent);
		pairTo.setTo(pair.getTo());

		for (Interface inf : interfaces) {
			// Interface do nível atual
			nodeX = inf.getPair().getFrom();
			nodeI = inf.getPair().getTo();

			parent = nodeX.getParent().getParentByLevel(level);
			pairFrom = getPairInMap(map, parent);
			pairFrom.setTo(nodeX);

			parent = nodeI.getParent().getParentByLevel(level);
			pairTo = getPairInMap(map, parent);
			pairTo.setFrom(nodeI);
		}
		return map;
	}

	private Pair getPairInMap(Map<Node, Pair> map, Node parent) {
		Pair pairFrom;
		pairFrom = map.get(parent);
		if (pairFrom == null) {
			pairFrom = new Pair();
			map.put(parent, pairFrom);
		}
		return pairFrom;
	}

	// private Map<Node, Pair> generatePairsByNeuronsNew(Pair pair, Pair
	// oldPair,
	// List<Neuron> neurons) {
	// Map<Node, Pair> map = new HashMap<Node, Pair>();
	// Node nodeX;
	// Node nodeI;
	// Interface inf;
	// Pair pairFrom;
	// Pair pairTo;
	// for (Neuron neuron1 : neurons) {
	// if (neuron1.isActivated()) {
	// inf = neuron1.getInterfaceParent(); // Interface do nível atual;
	// if (!inf.getPair().getFrom().equals(oldPair.getTo())
	// && !inf.getPair().getTo().equals(oldPair.getFrom())) // Eliminar
	// // o
	// // neuronio
	// // loop
	// if (inf.getInterfaces() != null) { // interfaces do nível
	// // abaixo
	// if (inf.getInterfaces().size() > 0) {
	// inf = inf.getInterfaces().get(0);
	// nodeX = inf.getPair().getFrom();
	// nodeI = inf.getPair().getTo();
	// if (!nodeX.getParent().equals(
	// pair.getTo().getParent())
	// || !nodeI.getParent().equals(
	// pair.getFrom().getParent())) {
	// pairFrom = map.get(nodeX.getParent());
	// if (pairFrom == null) {
	// pairFrom = new Pair();
	// map.put(nodeX.getParent(), pairFrom);
	// }
	//
	// pairFrom.setTo(nodeX);
	// if (nodeX.getParent().equals(
	// pair.getFrom().getParent())) {
	// pairFrom.setFrom(pair.getFrom());
	// }
	//
	// pairTo = map.get(nodeI.getParent());
	// if (pairTo == null) {
	// pairTo = new Pair();
	// map.put(nodeI.getParent(), pairTo);
	// }
	// pairTo.setFrom(nodeI);
	// if (nodeI.getParent().equals(
	// pair.getTo().getParent())) {
	// pairTo.setTo(pair.getTo());
	// }
	// }
	// } else {
	// throw new RuntimeException(
	// "Erro na formação da interface!");
	// }
	// }
	// }
	// }
	// return map;
	// }

	

	@Override
	public void updateRede(Node _rede, Operators operators) {
		this.node = _rede;
		this.hnns = new HashMap<Node, HNN>();
		NetworkToHNNParser parser = new NetworkToHNNParser();
		parser.generateHHNN(node, operators, this.hnns);
	}

}
