package hnn.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Neuron {

	private HNN parent;

	private Interface interfaceParent;

	private double bias;

	private List<SynapticConnection> inputs;

	protected double net;

	private double oldNet;

	private double output;

	private double deltaOutput;

	private Operators operators;

	public Neuron(HNN parent, Interface interaceParent, Operators operators) {
		this.setParent(parent);
		this.interfaceParent = interaceParent;
		this.operators = operators;
		this.inputs = new ArrayList<SynapticConnection>();
	}

	public Neuron(HNN parent, Interface interaceParent,
			List<SynapticConnection> inputs, Operators operators) {
		this.setParent(parent);
		this.interfaceParent = interaceParent;
		this.inputs = inputs;
		this.operators = operators;
	}

	public void calculateNet() {
		double netTemp = operators.calcualteEnergy(this);
		this.setOldNet(net);
		this.net = netTemp;
	}

	public double calculateSumWeghtedInputs() {
		double sum = 0;
		for (int i = 0; i < this.inputs.size(); i++) {
			sum += inputs.get(i).getInputWeighted();
		}
		return sum;
	}

	public void updateOutput() {
		double oldOutput = this.getOutput();
		this.setOutput(operators.calculateActivation(net));
		this.setDeltaOutput(Math.abs(this.output - oldOutput));
	}

	public List<SynapticConnection> getInputs() {
		return inputs;
	}

	public void setInputs(List<SynapticConnection> inputs) {
		this.inputs = inputs;
	}

	public double getNet() {
		return net;
	}

	public void setNet(double net) {
		this.net = net;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}

	public void inicializeWeight() {
		for (int i = 0; i < inputs.size(); i++) {
			inputs.get(i).generateWeight(operators);
		}
	}

	public void inicializeBias(Pair pair) {
		this.setBias(this.operators.calculateBias(interfaceParent, pair));
	}

	public void addInputConnection(SynapticConnection conexao) {
		inputs.add(conexao);
	}

	public Interface getInterfaceParent() {
		return interfaceParent;
	}

	public void setInterfaceParent(Interface interfaceParent) {
		this.interfaceParent = interfaceParent;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}
	public void reset(Pair pair) {
		this.inicializeBias(pair);
		this.generateNoise();
		this.oldNet = 0.0;
		this.output = 0.0;
		this.deltaOutput = 0.0;
	}
	

	public void generateNoise() {
		Random random = new Random();
		this.net = (random.nextDouble() % Const.NOISE_THRESHOLD)
				* (random.nextBoolean() ? 1 : -1);
//		this.net = Const.NOISE_THRESHOLD;
	}

	public double getOldNet() {
		return oldNet;
	}

	public void setOldNet(double oldNet) {
		this.oldNet = oldNet;
	}

	public double getDeltaOutput() {
		return deltaOutput;
	}

	public void setDeltaOutput(double deltaOutput) {
		this.deltaOutput = deltaOutput;
	}

	public String printInputs() {
		List<SynapticConnection> connections = this.inputs;
		StringBuffer buff = new StringBuffer();
		for (SynapticConnection synapticConnection : connections) {
			buff.append(this.interfaceParent);
			buff.append(";");
			buff.append(synapticConnection.getOrigin());
			buff.append(";");
			buff.append(synapticConnection.getWeight());
			buff.append("\n");

		}
		return buff.toString();
	}

	public boolean isActivated() {
		return getOutput() >= 0.5;
	}

	@Override
	public String toString() {
		return this.interfaceParent.toString();
	}

	public HNN getParent() {
		return parent;
	}

	public void setParent(HNN parent) {
		this.parent = parent;
	}

	public Operators getOperators() {
		return operators;
	}

	public void setOperators(Operators operators) {
		this.operators = operators;
	}

}
