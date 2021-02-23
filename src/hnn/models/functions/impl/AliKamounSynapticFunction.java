package hnn.models.functions.impl;

import hnn.models.Config;
import hnn.models.Neuron;
import hnn.models.Node;
import hnn.models.functions.SynapticFunction;
import hnn.util.Util;

public class AliKamounSynapticFunction extends SynapticFunction {

	public AliKamounSynapticFunction(Config config) {
		super(config);
	}

	public double calculate(Neuron origenNeuron, Neuron destinationNeuron) {
		int u[] = config.getU();
		Node nodeX = destinationNeuron.getInterfaceParent().getPair().getFrom();
		Node nodeI = destinationNeuron.getInterfaceParent().getPair().getTo();
		Node nodeY = origenNeuron.getInterfaceParent().getPair().getFrom();
		Node nodeJ = origenNeuron.getInterfaceParent().getPair().getTo();
		
		return u[3]
				* Util.kroneckerDelt(nodeX, nodeY)
				* Util.kroneckerDelt(nodeI, nodeJ)
				- u[2]
				* Util.kroneckerDelt(nodeX, nodeY)
				- u[2]
				* Util.kroneckerDelt(nodeI, nodeJ)				
				+ u[2]
				* Util.kroneckerDelt(nodeJ, nodeX)
				+ u[2]
				* Util.kroneckerDelt(nodeI, nodeY);				
	}

}
