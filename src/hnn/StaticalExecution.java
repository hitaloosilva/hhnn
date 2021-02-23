package hnn;

import hnn.models.HNN;
import hnn.models.Neuron;

public class StaticalExecution {

	private double meanTime;
	private double standardDeviationTime;
	private double meanIterations;
	private double standardDerivationIterations;
	private HNN hnn;
	private Execution execution;

	public StaticalExecution() {
		// TODO Auto-generated constructor stub
	}
	
	public StaticalExecution(long meanTime, long standardDeviationTime,
			int meanIterations, int standardDeviationIterations, HNN hnn,
			Execution execution) {
		this.meanTime = meanTime;
		this.standardDeviationTime = standardDeviationTime;
		this.meanIterations = meanIterations;
		this.standardDerivationIterations = standardDeviationIterations;
		this.hnn = hnn;
		this.execution = execution;
	}

	
	public void createPath() {
		this.hnn.getNeurons();
		for (Neuron neuron : this.hnn.getNeurons()) {
			if (neuron.isActivated()) {
				System.out.println(neuron.getInterfaceParent().getLevel()
						+ " - " + neuron);
			}
		}
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

	public double getMeanIterations() {
		return meanIterations;
	}

	public void setMeanIterations(double meanIterations) {
		this.meanIterations = meanIterations;
	}

	public double getStandardDerivationIterations() {
		return standardDerivationIterations;
	}

	public void setStandardDerivationIterations(double standardDerivationIterations) {
		this.standardDerivationIterations = standardDerivationIterations;
	}

	public HNN getHnn() {
		return hnn;
	}

	public void setHnn(HNN hnn) {
		this.hnn = hnn;
	}

	public Execution getExecution() {
		return execution;
	}

	public void setExecution(Execution execution) {
		this.execution = execution;
	}

	public void print() {
		System.out.println("HNN "+ this.hnn);
		System.out.printf("Mean time: %.2f\n", this.meanTime);
		System.out.printf("SD time: %.2f\n", this.standardDeviationTime);
		System.out.printf("Mean Iterations: %.2f\n", this.meanIterations);
		System.out.printf("SD Iterations: %.2f\n", this.standardDerivationIterations);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hnn == null) ? 0 : hnn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StaticalExecution other = (StaticalExecution) obj;
		if (hnn == null) {
			if (other.hnn != null)
				return false;
		} else if (!hnn.equals(other.hnn))
			return false;
		return true;
	}

}
