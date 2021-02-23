package hnn;

import java.util.List;

import hnn.models.Pair;

public class StaticalRoutingTest {

	private Pair pair;
	private double meanTime;
	private double standardDeviationTime;
	private double meanIteration;
	private double standardDeviationIteration;
	private List<StaticalExecution> executions;

	public Pair getPair() {
		return pair;
	}

	public void setPair(Pair pair) {
		this.pair = pair;
	}

	public double getMeanTime() {
		return meanTime;
	}

	public void setMeanTime(double meanTime) {
		this.meanTime = meanTime;
	}

	public double getStandardDeviationTime() {
		return standardDeviationTime;
	}

	public void setStandardDeviationTime(double standardDeviationTime) {
		this.standardDeviationTime = standardDeviationTime;
	}

	public List<StaticalExecution> getExecutions() {
		return executions;
	}

	public void setExecutions(List<StaticalExecution> executions) {
		this.executions = executions;
	}

	public void print() {
		System.out.printf("Pair: %s\n", this.pair);
		System.out.printf("Mean time: %.2f\n", this.meanTime);
		System.out.printf("SD time: %.2fn", this.standardDeviationTime);
		if (this.executions != null)
			for (StaticalExecution execution : this.executions) {
				execution.print();
			}
	}

	public double getMeanIteration() {
		return meanIteration;
	}

	public void setMeanIteration(double meanIteration) {
		this.meanIteration = meanIteration;
	}

	public double getStandardDeviationIteration() {
		return standardDeviationIteration;
	}

	public void setStandardDeviationIteration(double standardDeviationIteration) {
		this.standardDeviationIteration = standardDeviationIteration;
	}

}
