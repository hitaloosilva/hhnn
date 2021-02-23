package mlp.modular;

public class NeuronioEntrada extends Neuronio {

	private static final long serialVersionUID = 1L;

	public NeuronioEntrada() {
	}

	@Override
	public void calculate() {
		this.output = this.net;
	}
}
