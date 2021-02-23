package ga.problems;

public class Sphere extends Problem {

	public Sphere(int _dimension) {
		super("Sphere", _dimension, -5.2, 5.2);
	}

	@Override
	public double calculateFitness(double[] solution) {
		double sum = 0;
		
		for (int i = 0; i < solution.length; i++) {
			sum += solution[i] * solution[i];
		}
		
		return sum;
	}

}
