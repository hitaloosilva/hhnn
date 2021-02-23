package pso.models;

public class Config {

	private int topology;
	
	public Config (int _topology) {
		this.topology = _topology;
	}

	public int getTopology() {
		return topology;
	}

	public void setTopology(int topology) {
		this.topology = topology;
	}

}
