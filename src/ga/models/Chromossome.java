package ga.models;

import java.util.Arrays;

public class Chromossome {
	
	private double fitness;
	private double values[];
	
	public Chromossome (int dimension) {
		values = new double[dimension];
	}
	
	public Chromossome (double _values[]) {
		this.values = Arrays.copyOf(_values, _values.length);
	}
	
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public double[] getValues() {
		return values;
	}
	public void setValues(double[] values) {
		this.values = values;
	}
	public void setValue(double value, int dimension){
		this.values[dimension] = value;
	}
	public double getValue(int dimension) {
		return this.values[dimension];
	}

}
