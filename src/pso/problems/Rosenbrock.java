package pso.problems;

public class Rosenbrock extends Problem {

	public Rosenbrock(int _dimension) {
		super("Rosenbrock", _dimension, -100, 100);
	}

	@Override
	public double calculateFitness(double[] x) {
		double sum = 0;
		
		for (int i = 0; i < x.length - 1; i++) {
			sum += + 100 * Math.pow((x[i+1] - Math.pow( x[i], 2)), 2)
					+ Math.pow(x[i] - 1, 2);
		}
		
		return sum;
	}

}
