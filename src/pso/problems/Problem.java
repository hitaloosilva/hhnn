package pso.problems;

import java.util.Arrays;

public abstract class Problem {

	private String name;
	private int dimension;
	private double minValue[];
	private double maxValue[];
	
	public Problem(String _name, int _dimension, double _minValue,
			double _maxValue) {
		setName(_name);
		setDimension(_dimension);
		setMinValue(_minValue);
		setMaxValue(_maxValue);
	}

	public Problem(String _name, int _dimension, double[] _minValue,
			double[] _maxValue) {
		setName(_name);
		setDimension(_dimension);
		setMinValue(_minValue);
		setMaxValue(_maxValue);
	}

	public abstract double calculateFitness(double solution[]);

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public double[] getMinValue() {
		return minValue;
	}

	public void setMinValue(double[] minValue) {
		this.minValue = minValue;
	}
	public void setMinValue(double minValue) {
		this.minValue = new double[this.dimension];
		Arrays.fill(this.minValue, minValue);
	}

	public double[] getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double[] maxValue) {
		this.maxValue = maxValue;
	}
	
	public void setMaxValue(double maxValue) {
		this.maxValue = new double[this.dimension];
		Arrays.fill(this.maxValue, maxValue);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

}
