package hnn.models.functions;

import hnn.models.Config;

public abstract class Function<Type> {
	
	private Config config;

	public Function(Config config) {
		this.setConfig(config);
	}

	abstract public double calculate(Type obj);

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

}
