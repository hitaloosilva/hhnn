package pso.problems;

public class Griewank extends Problem{

	public Griewank(int _dimension) {
		super("Griewank", _dimension, -600, 600);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double calculateFitness(double[] x) {
		double sum = 0;
		double prod = 1;

		for (int i = 0; i < x.length; i++) {
			sum += Math.pow(x[i], 2);
		}
		sum = 1/4000 * sum;
		
		for (int i = 1; i < x.length+1; i++) {
			double valueRad = x[i-1] * Math.PI / 180;
			prod *= Math.cos(valueRad / Math.sqrt(i));
		}
		
		return (sum + prod + 1);
	}

}
