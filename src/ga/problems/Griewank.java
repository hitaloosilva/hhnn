package ga.problems;

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
			prod *= Math.cos(x[i-1] / Math.sqrt(i));
		}
		
		return (sum + prod + 1);
	}

}
