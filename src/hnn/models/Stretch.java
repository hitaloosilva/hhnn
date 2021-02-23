package hnn.models;

public class Stretch {
	
	private Path path;

	private Pair pair;
	
	private double cost;
	
	private double wavelength;
	
	public Stretch(Path path, Pair pair, double cost) {
		super();
		this.path = path;
		this.pair = pair;
		this.cost = cost;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Pair getPair() {
		return pair;
	}

	public void setPair(Pair pair) {
		this.pair = pair;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getWavelength() {
		return wavelength;
	}

	public void setWavelength(double wavelength) {
		this.wavelength = wavelength;
	}
	
	
}
