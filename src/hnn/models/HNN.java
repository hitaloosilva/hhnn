package hnn.models;

import hnn.Execution;

import java.util.ArrayList;
import java.util.List;

public class HNN {

	private Node node;

	private List<Neuron> neurons;

	public HNN(Node node) {
		this.node = node;
	}

	public HNN(Node node, List<Neuron> neurons) {
		this.node = node;
		this.neurons = neurons;
	}

	public Execution establishRoute(Pair pair) {
		Long starTime = System.nanoTime();
		Neuron neuron;
		boolean isNotStableState = true;
		int iterations = 0;
		for (int i = 0; i < neurons.size(); i++) {
			neuron = neurons.get(i);
			neuron.reset(pair);
//			System.out.println(neuron.getBias());
//			System.out.println(neuron.getNet());
		}
//		System.out.println("--------------------------------------");
		for (int i = 0; i < neurons.size(); i++) {
			neuron = neurons.get(i);
			neuron.updateOutput();
		}
		do {
			isNotStableState = true;
			for (int i = 0; i < neurons.size(); i++) {
				neuron = neurons.get(i);
				neuron.calculateNet();
//				System.out.println(neuron+": "+neuron.getNet());
			}
//			System.out.println("--------------------------------------");
			for (int i = 0; i < neurons.size(); i++) {
				neuron = neurons.get(i);
				neuron.updateOutput();
				if (neuron.getDeltaOutput() > 0.00001) {
					isNotStableState = false;
				}
			}
			iterations++;
		} while (!isNotStableState);
		return new Execution(System.nanoTime() - starTime, iterations, this);
	}

	public Pair getMutualParentByLayer(Pair pair) {
		Pair currentPair = pair.clone();
		while (currentPair.getFrom().getParent().getLevel() > this.node
				.getLevel() + 1) {
			currentPair.setFrom(currentPair.getFrom().getParent());
			currentPair.setTo(currentPair.getTo().getParent());
		}
		return currentPair;
	}

	public List<Neuron> getActivedNeurons() {
		List<Neuron> neurons = new ArrayList<Neuron>();
		for (Neuron neuron1 : this.neurons) {
			if (neuron1.isActivated()) {
				neurons.add(neuron1);
			}
		}
		return neurons;
	}

	public List<Neuron> getActivedNeurons(Pair pair) {
		List<Neuron> neurons = new ArrayList<Neuron>();
		Interface inf = null;
		for (Neuron neuron1 : this.neurons) {
			if (neuron1.isActivated()) {
				inf = neuron1.getInterfaceParent();
				if (!inf.getPair().getTo().equals(pair.getFrom())
						&& !inf.getPair().getFrom().equals(pair.getTo()))
					neurons.add(neuron1);
			}
		}
		return neurons;
	}

	public static List<Interface> getPhysicalIntefaces(Pair pair,
			List<Neuron> activedNeurons) {
		List<Interface> interfaces = new ArrayList<Interface>();
		Interface inf = null;
		for (Neuron neuron : activedNeurons) {
			// Interface do nível atual
			inf = getInterfaceByPairLevel(pair, neuron);
			interfaces.add(inf);
		}
		return interfaces;
	}

	private static Interface getInterfaceByPairLevel(Pair pair, Neuron neuron) {
		Interface inf = neuron.getInterfaceParent();
		while (inf != null && pair.getFrom().getLevel() > inf.getLevel()) {
			inf = inf.getInterfaces().get(0);
		}
		return inf;
	}

	public NeuronParent getNeuronAreaByParent(Node parent) {
		NeuronParent neuronAreaResult = null;
		// for (NeuronParent neuronArea : this.neuronAreaList) {
		// if (neuronArea.getParent().equals(parent)) {
		// neuronAreaResult = neuronArea;
		// break;
		// }
		// }
		return neuronAreaResult;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public List<Neuron> getNeurons() {
		return neurons;
	}

	public void setNeurons(List<Neuron> neurons) {
		this.neurons = neurons;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HNN other = (HNN) obj;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.node.toString();
	}

}
