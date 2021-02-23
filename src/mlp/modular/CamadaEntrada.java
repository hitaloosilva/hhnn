package mlp.modular;

import java.io.Serializable;

public class CamadaEntrada extends Camada implements Serializable {

	private static final long serialVersionUID = 3072622827679885867L;

	public CamadaEntrada(int neuronsCount) {
		for (int i = 0; i < neuronsCount; i++) {
			NeuronioEntrada neuron = new NeuronioEntrada();
			this.addNeuron(neuron);
		}
	}

}
