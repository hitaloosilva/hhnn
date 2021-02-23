package pso.models;

public class Particle {
	
	private double fitness;
	private double position[];
	private double velocity[];
	private double pBest[];
	private double fitnessPBest;
	private double nBest[];
	private double fitnessNBest;
	
	public Particle (int dimension) {
		position = new double[dimension];
		velocity = new double[dimension];
		pBest = new double[dimension];
		nBest = new double[dimension];
		fitness = Double.MAX_VALUE;
		fitnessPBest = Double.MAX_VALUE;
		fitnessNBest = Double.MAX_VALUE;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public double[] getPosition() {
		return position;
	}

	public void setPosition(double[] position) {
		this.position = position;
	}
	
	public double getPosition(int index) {
		return position[index];
	}

	public void setPosition(double value, int index) {
		this.position[index] = value;
	}

	public double[] getVelocity() {
		return velocity;
	}

	public void setVelocity(double[] velocity) {
		this.velocity = velocity;
	}
	
	public double getVelocity(int index) {
		return velocity[index];
	}

	public void setVelocity(double velocity, int index) {
		this.velocity[index] = velocity;
	}

	public double[] getpBest() {
		return pBest;
	}

	public void setpBest(double pBest, int index) {
		this.pBest[index] = pBest;
	}
	
	public double getpBest(int index) {
		return pBest[index];
	}

	public void setpBest(double[] pBest) {
		this.pBest = pBest;
	}

	public double getFitnessPBest() {
		return fitnessPBest;
	}

	public void setFitnessPBest(double fitnessPBest) {
		this.fitnessPBest = fitnessPBest;
	}

	public double[] getnBest() {
		return nBest;
	}

	public void setnBest(double[] nBest) {
		this.nBest = nBest;
	}

	public double getnBest(int index) {
		return nBest[index];
	}

	public void setnBest(double nBest, int index) {
		this.nBest[index] = nBest;
	}
	
	public double getFitnessNBest() {
		return fitnessNBest;
	}

	public void setFitnessNBest(double fitnessNBest) {
		this.fitnessNBest = fitnessNBest;
	}
	
}
